package org.k4java.rest.tool.doc.util;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.ILocalVariable;
import org.eclipse.jdt.core.IMemberValuePair;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.Signature;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.Javadoc;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.TagElement;
import org.k4java.rest.tool.doc.handlers.MethodVisitor;
import org.k4java.rest.tool.doc.handlers.RestTypeVisitor;
import org.k4java.rest.tool.doc.handlers.config.Doc4RestConfiguration;
import org.k4java.rest.tool.doc.model.RestAPIModel;
import org.k4java.rest.tool.doc.model.RestDoc;
import org.k4java.rest.tool.doc.model.RestParamsModel;
import org.k4java.rest.tool.doc.model.RestServiceModel;

public class Doc4RestUtil {
	
	public static String generateUniqueId() {
	  UUID uuid = UUID.randomUUID();
	  return uuid.toString().replaceAll("-", "").toUpperCase();
	}
	
	public static void extractServiceInfo(IType type, RestServiceModel model)
	{
		for(RestDoc doc: Doc4RestConfiguration.getServiceAnnotations())
		{
			try {
				IAnnotation anno = type.getAnnotation(doc.getName());
				if(anno != null && anno.exists())
				{
					if(anno.getMemberValuePairs().length > 0)
					{
						for(IMemberValuePair pair : anno.getMemberValuePairs())
						{
							if(doc.getType().equalsIgnoreCase("string"))
							{
								model.getSingleAttriMap().put(doc.getName(), pair.getValue().toString());
							} else if(doc.getType().equalsIgnoreCase("list"))
							{
								if(model.getListAttriMap().containsKey(doc.getName()))
								{
									model.getListAttriMap().get(doc.getName()).add(pair.getValue().toString());
								}
							}
						}
					}else
					{
						model.getSingleAttriMap().put(doc.getName(), Doc4RestConfiguration.getEmptyParam());
					}
				}
			} catch (JavaModelException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	public static void extractApiInfo(IMethod method, RestAPIModel model)
	{
		for(RestDoc doc: Doc4RestConfiguration.getApiAnnotations())
		{
			try {
				IAnnotation anno = method.getAnnotation(doc.getName());
				if(anno != null && anno.exists())
				{
					if(anno.getMemberValuePairs().length > 0)
					{
						for(IMemberValuePair pair : anno.getMemberValuePairs())
						{
							if(doc.getType().equalsIgnoreCase("string"))
							{
								if(pair.getValue() instanceof Object[])
								{
									StringBuilder str = new StringBuilder();
									for(Object obj : (Object[])pair.getValue())
									{
										str.append(obj.toString()).append("\n");
									}
									model.getSingleAttriMap().put(doc.getName(), str.toString());
								}else{
									model.getSingleAttriMap().put(doc.getName(), pair.getValue().toString());
								}
							} else if(doc.getType().equalsIgnoreCase("list"))
							{
								if(model.getListAttriMap().containsKey(doc.getName()))
								{
									model.getListAttriMap().get(doc.getName()).add(pair.getValue().toString());
								}
							}
						}
					}else
					{
						model.getSingleAttriMap().put(doc.getName(), Doc4RestConfiguration.getEmptyParam());
					}
				}
			} catch (JavaModelException e) {
				e.printStackTrace();
			}
		}
	}


	public static void extractServiceJavaDoc(ICompilationUnit unit,
			RestServiceModel model) {
		CompilationUnit parse = parse(unit);
		RestTypeVisitor visitor = new RestTypeVisitor();
		parse.accept(visitor);
		if(visitor.getType() != null)
		{
			Javadoc doc = visitor.getType().getJavadoc();
			if(doc != null)
			{
				for(Object obj: doc.tags())
				{
					if(obj instanceof TagElement)
					{
						TagElement tag = (TagElement) obj;
						if(tag.getTagName() == null)
						{
							//Description will not have any Tag Name mapping to Summary
							model.getJavaDoc().put(Doc4RestConfiguration.getSummaryKey(),stripKeyForJavaDoc("\\*",tag.toString()));
						}else
						{
							if(Doc4RestConfiguration.getJavaDocComments().contains(tag.getTagName()))
							{
								model.getJavaDoc().put(tag.getTagName(), stripKeyForJavaDoc(tag.getTagName(),tag.toString()));
							}
						}
					}
				}
			}
		}
		//Fix if the @serviceName not provided, then take name from interface name 
		if(!model.getJavaDoc().containsKey(Doc4RestConfiguration.getServiceName()))
		{
			model.getJavaDoc().put(Doc4RestConfiguration.getServiceName(), unCamelCase(unit.getElementName().substring(0, unit.getElementName().indexOf("."))));
		}
	}
	
	private static String stripKeyForJavaDoc(String key, String text)
	{
		String returnValue = "";
		String pattern = key+"\\s*(.*$)";
		Pattern r = Pattern.compile(pattern);
		Matcher m = r.matcher(text);
		if (m.find( )) {
			returnValue = m.group(1);
		} else {
			returnValue = text;
		}
		return returnValue;
	}
	
	private static CompilationUnit parse(ICompilationUnit unit) {
	    ASTParser parser = ASTParser.newParser(AST.JLS8);
	    parser.setKind(ASTParser.K_COMPILATION_UNIT);
	    parser.setSource(unit);
	    parser.setResolveBindings(true);
	    return (CompilationUnit) parser.createAST(null); // parse
	  }


	public static void extractParamInfo(IMethod method, RestAPIModel apiModel) throws JavaModelException {
		  for(ILocalVariable variable : method.getParameters())
		  {
			  RestParamsModel model = new RestParamsModel();
			  model.setName(variable.getElementName());
			  model.setType(Signature.toString(Signature.getTypeErasure(variable.getTypeSignature())));
			  //TODO If needed param annotation needs to parsed then below code will set in method level
			  model.setAnnotation(parseAnnotations(variable.getSource()));
			  apiModel.getParamsMap().put(variable.getElementName(), model);
		  }
	}


	private static String parseAnnotations(String source) {
		for(RestDoc doc : Doc4RestConfiguration.getApiAnnotations())
		{
			Pattern pattern = Pattern.compile(doc.getName()+"(\\s+|\\()");
			Matcher match = pattern.matcher(source);
			if(match.find())
			{
				return doc.getName();
			}
		}
		return null;
	}


	public static void extractApiJavaDoc(IMethod method,
			RestAPIModel apiModel) throws JavaModelException {
		ASTParser parser = ASTParser.newParser(AST.JLS8);
		parser.setSource(new String("public class A {\n"+method.getSource()+"\n}").toCharArray());
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
		parser.setResolveBindings(true);
		CompilationUnit cu = (CompilationUnit) parser.createAST(null);
		MethodVisitor visitor = new MethodVisitor();
	    cu.accept(visitor);
	    MethodDeclaration dec = visitor.getMethod();
		if(dec != null)
		{
			Javadoc doc = dec.getJavadoc();
			if(doc != null)
			{
				for(Object obj: doc.tags())
				{
					if(obj instanceof TagElement)
					{
						TagElement tag = (TagElement) obj;
						if(tag.getTagName() == null)
						{
							//Description will not have any Tag Name mapping to Summary
							apiModel.getJavaDoc().put(Doc4RestConfiguration.getSummaryKey(),stripKeyForJavaDoc("\\*",tag.toString()));
						}else
						{
							if(Doc4RestConfiguration.getJavaDocComments().contains(tag.getTagName()))
							{
								apiModel.getJavaDoc().put(tag.getTagName(), stripKeyForJavaDoc(tag.getTagName(),tag.toString()));
							} else if(Doc4RestConfiguration.getParamsKey().equalsIgnoreCase(tag.getTagName()))
							{
								String paramsValue = stripKeyForJavaDoc(tag.getTagName(),tag.toString());
								String[] paramsPart = paramsValue.split(Doc4RestConfiguration.getParamsSplitChar());
								if(paramsPart != null && paramsPart.length > 1)
								{
									String param = paramsPart[0].trim();
									if(apiModel.getParamsMap().containsKey(param))
									{
										apiModel.getParamsMap().get(param).setDesc(paramsPart[1]);
									}
								}
							}
						}
					}
				}
			}
		}
	}
	
	public static String getServiceRootPath()
	{
		return Doc4RestConfiguration.getInstance().getFileLocation()+Doc4RestConfiguration.getFileSeparator()+Doc4RestConfiguration.getFolderName()+Doc4RestConfiguration.getFileSeparator()+Doc4RestConfiguration.getServiceFolderName();
	}
	
	public static String getApiRootPath()
	{
		return getServiceRootPath()+Doc4RestConfiguration.getFileSeparator()+Doc4RestConfiguration.getAPIFolderName();
	}

	public static String formatSummary(String summary) {
		String returnString = summary;
		if(summary.startsWith("@"))
		{
			returnString = summary.replace("@", "");
			String startChar = returnString.charAt(0)+"";
			returnString = startChar.toUpperCase()+returnString.substring(1, returnString.length());
			returnString = unCamelCase(returnString);
		}
		return returnString;
	}
	
	public static String unCamelCase(String value)
	{
		return value.replaceAll(
			      String.format("%s|%s|%s",
					         "(?<=[A-Z])(?=[A-Z][a-z])",
					         "(?<=[^A-Z])(?=[A-Z])",
					         "(?<=[A-Za-z])(?=[^A-Za-z])"
					      ),
					      " "
					   );
	}
}
