package org.k4java.rest.tool.doc.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.k4java.rest.tool.doc.generator.GResult;
import org.k4java.rest.tool.doc.generator.RestDocGeneratorManager;
import org.k4java.rest.tool.doc.handlers.config.Doc4RestConfiguration;
import org.k4java.rest.tool.doc.model.Doc4RestModelManager;
import org.k4java.rest.tool.doc.model.RestAPIModel;
import org.k4java.rest.tool.doc.model.RestServiceModel;
import org.k4java.rest.tool.doc.util.Doc4RestUtil;

/**
 * Our sample handler extends AbstractHandler, an IHandler base class.
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 */
public class Doc4RestHandler extends AbstractHandler {

	private static final String JDT_NATURE = "org.eclipse.jdt.core.javanature";
	private Doc4RestConfiguration config = Doc4RestConfiguration.getInstance();
	private Doc4RestModelManager manager = Doc4RestModelManager.getInstance();
	  public Object execute(ExecutionEvent event) throws ExecutionException {
		  cleanPerviousInfo();
		  buildRestModel();
		  generateRestDoc();
		  return null;
	  }

	private void cleanPerviousInfo() {
		manager.initModel();
	}

	private void generateRestDoc() {
		Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
		DirectoryDialog dirDialog = new DirectoryDialog(shell);
	    dirDialog.setText("Select Output directory");
	    String selectedDir = dirDialog.open();
	    Doc4RestConfiguration.getInstance().setFileLocation(selectedDir);

		GResult result = RestDocGeneratorManager.getInstance().generateRestDoc();
		if(result.isStatus())
		{
			MessageDialog.openInformation(shell, "Doc4Rest Info", "Generated Rest Document Successfully");
		} else {
			StringBuilder str = new StringBuilder();
			for(String error: result.getErrorMessage())
			{
				str.append(error).append("\\n");
			}
			MessageDialog.openError(shell, "Doc4Rest", str.toString());
		}
	}

	private void buildRestModel()
	  {
		  // Get the root of the workspace
		  IWorkspace workspace = ResourcesPlugin.getWorkspace();
		  IWorkspaceRoot root = workspace.getRoot();
		  // Get all projects in the workspace
		  IProject[] projects = root.getProjects();
		  // Loop over all projects
		  for (IProject project : projects) {
			  try {
				  scanProjectInfo(project);
			  } catch (CoreException e) {
				  e.printStackTrace();
			  }
		  }
	  }
	  
	  private void scanProjectInfo(IProject project) throws CoreException,
	      JavaModelException {
	    // check if we have a Java project
	    if (project.isNatureEnabled(JDT_NATURE)) {
	      IJavaProject javaProject = JavaCore.create(project);
	      scanPackageInfos(javaProject);
	    }
	  }

	  private void scanPackageInfos(IJavaProject javaProject)
	      throws JavaModelException {
	    IPackageFragment[] packages = javaProject.getPackageFragments();
	    for (IPackageFragment mypackage : packages) {
	      if (mypackage.getKind() == IPackageFragmentRoot.K_SOURCE) {
	        scanICompilationUnitInfo(mypackage);
	      }
	    }
	  }

	  private void scanICompilationUnitInfo(IPackageFragment mypackage)
			  throws JavaModelException 
	  {
		  for (ICompilationUnit unit : mypackage.getCompilationUnits()) {
			  IType  iType = unit.findPrimaryType();
			  if(iType !=null )
			  {
				  if((iType.isInterface() && config.isInterfaceScanRequired()) || 
						  (iType.isClass() && config.isClassScanRequired()))
				  {
					  IAnnotation anno = iType.getAnnotation(Doc4RestConfiguration.getMainRestDecider());
					  if(anno != null && anno.exists())
					  {
						  RestServiceModel model = new RestServiceModel(Doc4RestUtil.generateUniqueId());
						  manager.addModelList(model);
						  Doc4RestUtil.extractServiceInfo(iType, model);
						  Doc4RestUtil.extractServiceJavaDoc(unit, model);
						  scanIMethods(iType, model);
						  //System.out.println(model);
					  }
				  }
			  }	
		  }
	  }
	  
	  private void scanIMethods(IType type, RestServiceModel model) throws JavaModelException {
		  IMethod[] methods = type.getMethods();
		  for (IMethod method : methods) {
			  IAnnotation anno = method.getAnnotation(Doc4RestConfiguration.getMainRestDecider());
			  if(anno != null && anno.exists())
			  {
				  RestAPIModel apiModel = new RestAPIModel(Doc4RestUtil.generateUniqueId());
				  model.getApiModels().add(apiModel);
				  Doc4RestUtil.extractApiInfo(method, apiModel);
				  Doc4RestUtil.extractParamInfo(method, apiModel);
				  Doc4RestUtil.extractApiJavaDoc(method, apiModel);
			  }
		  }
	  }
	} 