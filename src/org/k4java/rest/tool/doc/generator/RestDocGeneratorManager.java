package org.k4java.rest.tool.doc.generator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.net.URL;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.io.FileUtils;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.k4java.rest.tool.doc.handlers.config.Doc4RestConfiguration;
import org.k4java.rest.tool.doc.model.Doc4RestModelManager;
import org.osgi.framework.Bundle;

import freemarker.cache.ClassTemplateLoader;
import freemarker.cache.FileTemplateLoader;
import freemarker.cache.MultiTemplateLoader;
import freemarker.cache.TemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import freemarker.template.Version;

public class RestDocGeneratorManager {
	private static RestDocGeneratorManager m_instance = new RestDocGeneratorManager();
	
	private RestDocGeneratorManager() {
	}
	
	public static RestDocGeneratorManager getInstance()
	{
		return m_instance;
	}
	
	public GResult generateRestDoc()
	{
		GResult result = new GResult();
		if(!initLocation(result))
		{
			return result;
		}
		if(!createHomePage(result))
		{
			return result;
		}
		if(!createServicePage(result))
		{
			return result;
		}
		if(!createApiPage(result))
		{
			return result;
		}
		return result;
	}

	private boolean createHomePage(GResult result) {
		boolean returnVal = false;
		try {
			File file = new File(Doc4RestConfiguration.getInstance().getTemplateLocation()+Doc4RestConfiguration.getFileSeparator()+"freemarker");
			FileTemplateLoader ftl1 = new FileTemplateLoader(file);
			ClassTemplateLoader ctl = new ClassTemplateLoader(getClass(), "");
			TemplateLoader[] loaders = new TemplateLoader[] { ftl1, ctl };
			MultiTemplateLoader mtl = new MultiTemplateLoader(loaders);
			@SuppressWarnings("deprecation")
			Configuration cfg = new Configuration();
			//cfg.setClassForTemplateLoading(RestDocGeneratorManager.class, file.getAbsolutePath());
			// Some other recommended settings:
			cfg.setIncompatibleImprovements(new Version(2, 3, 20));
			cfg.setDefaultEncoding("UTF-8");
			cfg.setLocale(Locale.US);
			cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
			cfg.setTemplateLoader(mtl);
			Map<String, Object> input = new HashMap<String, Object>();
			input.put("doc4rest", new Doc4RestModel("https://github.com/Karunakaran-SL/Doc4Rest","Doc4Rest","icons/doc4rest.png"));
			input.put("services", Doc4RestModelManager.getInstance().getServicePages());
			Template template = cfg.getTemplate("Services.ftl");
			Writer fileWriter = new FileWriter(new File(Doc4RestConfiguration.getInstance().getFileLocation()+Doc4RestConfiguration.getFileSeparator()+Doc4RestConfiguration.getFolderName()+Doc4RestConfiguration.getFileSeparator()+"index.html"));
			try {
			  template.process(input, fileWriter);
			} finally {
			  fileWriter.close();
			}
			result.setStatus(true);
			returnVal = true;
		} catch (IOException e) {
			e.printStackTrace();
			result.getErrorMessage().add(e.getMessage());
			result.setStatus(false);
		} catch (TemplateException e) {
			e.printStackTrace();
			result.getErrorMessage().add(e.getMessage());
			result.setStatus(false);
		}
		return returnVal;
	}

	private boolean createServicePage(GResult result) {
		boolean returnVal = false;
		try {
			File file = new File(Doc4RestConfiguration.getInstance().getTemplateLocation()+Doc4RestConfiguration.getFileSeparator()+"freemarker");
			FileTemplateLoader ftl1 = new FileTemplateLoader(file);
			ClassTemplateLoader ctl = new ClassTemplateLoader(getClass(), "");
			TemplateLoader[] loaders = new TemplateLoader[] { ftl1, ctl };
			MultiTemplateLoader mtl = new MultiTemplateLoader(loaders);
			@SuppressWarnings("deprecation")
			Configuration cfg = new Configuration();
			//cfg.setClassForTemplateLoading(RestDocGeneratorManager.class, file.getAbsolutePath());
			// Some other recommended settings:
			cfg.setIncompatibleImprovements(new Version(2, 3, 20));
			cfg.setDefaultEncoding("UTF-8");
			cfg.setLocale(Locale.US);
			cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
			cfg.setTemplateLoader(mtl);
			for(GServicesPage page : Doc4RestModelManager.getInstance().getServicePages())
			{
				Map<String, Object> input = new HashMap<String, Object>();
				input.put("doc4rest", new Doc4RestModel("https://github.com/Karunakaran-SL/Doc4Rest","Doc4Rest","../icons/doc4rest.png"));
				input.put("service", page);
				input.put("sampleMap", page.getSampleRequestMap());
				input.put("attrMap", page.getServiceAttributeMap());
				input.put("api", page.getApiMap());
				Template template = cfg.getTemplate("ServicePage.ftl");
				Writer fileWriter = new FileWriter(new File(page.getLink()));
				try {
				  template.process(input, fileWriter);
				} finally {
				  fileWriter.close();
				}
			}
			result.setStatus(true);
			returnVal =  true;
		} catch (IOException e) {
			result.getErrorMessage().add(e.getMessage());
			result.setStatus(false);
		} catch (TemplateException e) {
			result.getErrorMessage().add(e.getMessage());
			result.setStatus(false);
		}
		return returnVal;
	}


	private boolean createApiPage(GResult result) {
		boolean returnVal = false;
		try {
			File file = new File(Doc4RestConfiguration.getInstance().getTemplateLocation()+Doc4RestConfiguration.getFileSeparator()+"freemarker");
			FileTemplateLoader ftl1 = new FileTemplateLoader(file);
			ClassTemplateLoader ctl = new ClassTemplateLoader(getClass(), "");
			TemplateLoader[] loaders = new TemplateLoader[] { ftl1, ctl };
			MultiTemplateLoader mtl = new MultiTemplateLoader(loaders);
			@SuppressWarnings("deprecation")
			Configuration cfg = new Configuration();
			//cfg.setClassForTemplateLoading(RestDocGeneratorManager.class, file.getAbsolutePath());
			// Some other recommended settings:
			cfg.setIncompatibleImprovements(new Version(2, 3, 20));
			cfg.setDefaultEncoding("UTF-8");
			cfg.setLocale(Locale.US);
			cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
			cfg.setTemplateLoader(mtl);
			for(GServicesPage page : Doc4RestModelManager.getInstance().getServicePages())
			{
				Map<String, Object> input = new HashMap<String, Object>();
				input.put("doc4rest", new Doc4RestModel("https://github.com/Karunakaran-SL/Doc4Rest","Doc4Rest","../../icons/doc4rest.png"));
				input.put("service", page);
				input.put("allApi", page.getApiMap());
				for(Entry<String, GApiPage> entry : page.getApiMap().entrySet())
				{
					input.put("api", entry.getValue());
					input.put("sampleMap", entry.getValue().getSampleRequestMap());
					input.put("attrMap", entry.getValue().getServiceAttributeMap());
					input.put("operations", entry.getValue().getOperation());
					input.put("paramsMap", entry.getValue().getParamsMap());
					Template template = cfg.getTemplate("ApiPage.ftl");
					Writer fileWriter = new FileWriter(new File(entry.getValue().getLink()));
					try {
					  template.process(input, fileWriter);
					} finally {
					  fileWriter.close();
					}
				}
			}
			result.setStatus(true);
			returnVal =  true;
		} catch (IOException e) {
			result.getErrorMessage().add(e.getMessage());
			result.setStatus(false);
		} catch (TemplateException e) {
			result.getErrorMessage().add(e.getMessage());
			result.setStatus(false);
		}
		return returnVal;
	}
	
	private boolean initLocation(GResult result) {
		boolean returnVal = false;
		try {
			//Clean existing folder
			File existingFolder = new File(Doc4RestConfiguration.getInstance().getFileLocation()+Doc4RestConfiguration.getFileSeparator()+Doc4RestConfiguration.getFolderName());
			if(existingFolder.exists())
			{
				existingFolder.delete();
			}
			File file = new File(Doc4RestConfiguration.getInstance().getFileLocation()+Doc4RestConfiguration.getFileSeparator()+Doc4RestConfiguration.getFolderName()+Doc4RestConfiguration.getFileSeparator()+Doc4RestConfiguration.getServiceFolderName()+Doc4RestConfiguration.getFileSeparator()+Doc4RestConfiguration.getAPIFolderName()); 
			file.mkdirs();
			copyTemplateFolder();
			returnVal = true;
		} catch (Exception e) {
			e.printStackTrace();
			result.getErrorMessage().add(e.getMessage());
			result.setStatus(false);
		}
		return returnVal;
	}

	private void copyTemplateFolder() throws Exception{
		File file = new File(Doc4RestConfiguration.getInstance().getTemplateLocation());
		FileUtils.copyDirectory(file, new File(Doc4RestConfiguration.getInstance().getFileLocation()+Doc4RestConfiguration.getFileSeparator()+Doc4RestConfiguration.getFolderName()));
	}
}
