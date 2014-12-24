package org.k4java.rest.tool.doc.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.k4java.rest.tool.doc.generator.GApiPage;
import org.k4java.rest.tool.doc.generator.GServicesPage;
import org.k4java.rest.tool.doc.handlers.config.Doc4RestConfiguration;
import org.k4java.rest.tool.doc.util.Doc4RestUtil;

public class Doc4RestModelManager {
	private static Doc4RestModelManager m_instance = new Doc4RestModelManager();
	private List<RestServiceModel> modelList = new ArrayList<RestServiceModel>();
	private Doc4RestModelManager(){}
	
	public static Doc4RestModelManager getInstance()
	{
		return m_instance;
	}

	/**
	 * @return the modelList
	 */
	public List<RestServiceModel> getModelList() {
		return modelList;
	}
	
	public void addModelList(RestServiceModel model)
	{
		modelList.add(model);
	}
	
	public List<GServicesPage> getServicePages()
	{
		List<GServicesPage> list = new ArrayList<GServicesPage>();
		for(RestServiceModel serviceModel : getModelList())
		{
			String serviceName = serviceModel.getJavaDoc().containsKey(Doc4RestConfiguration.getServiceName())? serviceModel.getJavaDoc().get(Doc4RestConfiguration.getServiceName()) : "";
			String path = serviceModel.getSingleAttriMap().containsKey(Doc4RestConfiguration.getMainRestDecider())? serviceModel.getSingleAttriMap().get(Doc4RestConfiguration.getMainRestDecider()) : "";
			if(serviceModel.getSingleAttriMap().containsKey("ApplicationPath"))
			{
				path = serviceModel.getSingleAttriMap().get("ApplicationPath") + path;
			}
			if(path.trim().isEmpty())
			{
				path = "/";
			}
			String desc = serviceModel.getJavaDoc().containsKey(Doc4RestConfiguration.getSummaryKey())? serviceModel.getJavaDoc().get(Doc4RestConfiguration.getSummaryKey()) : "";
			String link = Doc4RestUtil.getServiceRootPath()+Doc4RestConfiguration.getFileSeparator()+"Service"+serviceModel.getId()+".html";
			GServicesPage page = new GServicesPage(serviceName, path, desc, link);
			for(String summary : Doc4RestConfiguration.getJavaDocComments())
			{
				//Skip known summary like desc and serviceName
				if(summary.equalsIgnoreCase(Doc4RestConfiguration.getSummaryKey()) ||
						summary.equalsIgnoreCase(Doc4RestConfiguration.getServiceName()))
				{
					continue;
				}
				if(serviceModel.getJavaDoc().containsKey(summary))
				{
					page.getSampleRequestMap().put(Doc4RestUtil.formatSummary(summary), serviceModel.getJavaDoc().get(summary));
				}
			}
			
			for(RestDoc restDoc : Doc4RestConfiguration.getServiceAnnotations())
			{
				//Skip known summary like desc and serviceName
				if(restDoc.getName().equalsIgnoreCase(Doc4RestConfiguration.getMainRestDecider()))
				{
					continue;
				}
				if(serviceModel.getSingleAttriMap().containsKey(restDoc.getName()))
				{
					page.getServiceAttributeMap().put(restDoc.getName(), serviceModel.getSingleAttriMap().get(restDoc.getName()));
				}
			}
			page.setApiMap(createAPIPage(serviceModel));
			list.add(page);
		}
		return list;
	}

	private Map<String, GApiPage> createAPIPage(RestServiceModel serviceModel) {
		Map<String, GApiPage> map = new TreeMap<String, GApiPage>();
		for(RestAPIModel model : serviceModel.getApiModels())
		{
			String path = model.getSingleAttriMap().containsKey(Doc4RestConfiguration.getMainRestDecider())? model.getSingleAttriMap().get(Doc4RestConfiguration.getMainRestDecider()) : "";
			if(path.trim().isEmpty())
			{
				path = "/";
			}
			String desc = model.getJavaDoc().containsKey(Doc4RestConfiguration.getSummaryKey())? model.getJavaDoc().get(Doc4RestConfiguration.getSummaryKey()) : "";
			String link = Doc4RestUtil.getApiRootPath()+Doc4RestConfiguration.getFileSeparator()+"Api"+model.getId()+".html";
			GApiPage apiPage = new GApiPage(path, desc, link);
			for(String operation: Doc4RestConfiguration.getOpertaionsList())
			{
				if(model.getSingleAttriMap().containsKey(operation))
				{
					apiPage.getOperation().add(operation); 
				}
				
			}
			
			for(String summary : Doc4RestConfiguration.getJavaDocComments())
			{
				//Skip known summary like desc and serviceName
				if(summary.equalsIgnoreCase(Doc4RestConfiguration.getSummaryKey()))
				{
					continue;
				}
				if(model.getJavaDoc().containsKey(summary))
				{
					apiPage.getSampleRequestMap().put(Doc4RestUtil.formatSummary(summary), model.getJavaDoc().get(summary));
				}
				for(RestDoc restDoc : Doc4RestConfiguration.getApiAnnotations())
				{
					//Skip known summary like desc and serviceName
					if(restDoc.getName().equalsIgnoreCase(Doc4RestConfiguration.getMainRestDecider()) || Doc4RestConfiguration.getOpertaionsList().contains(restDoc.getName()))
					{
						continue;
					}
					if(restDoc.getType().equals("string"))
					{
						if(model.getSingleAttriMap().containsKey(restDoc.getName()))
						{
								apiPage.getServiceAttributeMap().put(restDoc.getName(), model.getSingleAttriMap().get(restDoc.getName()));
						}
					}else {
						if(model.getListAttriMap().containsKey(restDoc.getName()))
						{
							StringBuilder str = new StringBuilder();
							for(String attr: model.getListAttriMap().get(restDoc.getName()))
							{
								str.append(attr).append(",");
							}
							apiPage.getServiceAttributeMap().put(restDoc.getName(), str.toString());
						}
					}
				}
			}
			apiPage.setParamsMap(model.getParamsMap());
			map.put(path, apiPage);
		}
		return map;
	}

	public void initModel() {
		modelList = new ArrayList<RestServiceModel>();
	}
}
