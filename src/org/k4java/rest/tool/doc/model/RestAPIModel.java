package org.k4java.rest.tool.doc.model;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class RestAPIModel
{
	private Map<String, String> singleAttriMap = new HashMap<String, String>();
	private Map<String, List<String>> listAttriMap = new HashMap<String, List<String>>();
	private Map<String, String> javaDoc = new HashMap<String, String>();
	private Map<String, RestParamsModel> paramsMap = new LinkedHashMap<String, RestParamsModel>();
	private String id;
	public RestAPIModel(String id) {
		this.id = id;
	}
	/**
	 * @return the singleAttriMap
	 */
	public Map<String, String> getSingleAttriMap() {
		return singleAttriMap;
	}
	/**
	 * @return the listAttriMap
	 */
	public Map<String, List<String>> getListAttriMap() {
		return listAttriMap;
	}
	
	/**
	 * @return the javaDoc
	 */
	public Map<String, String> getJavaDoc() {
		return javaDoc;
	}
	
	/**
	 * @return the paramsMap
	 */
	public Map<String, RestParamsModel> getParamsMap() {
		return paramsMap;
	}
	
	public String getId() {
		return id;
	}
	
	public String toString()
	{
		String line = System.getProperty("line.separator");
		StringBuilder str = new StringBuilder();
		str.append("----------------------------------------------------------------------------------------------------").append(line);
		str.append("Single Attribute").append(line);
		for(Entry<String, String> entry : singleAttriMap.entrySet())
		{
			str.append("Name : "+entry.getKey()).append("  ").append("Value : "+entry.getValue()).append(line);
		}
		str.append("List Attribute").append(line);
		for(Entry<String, List<String>> entry : listAttriMap.entrySet())
		{
			str.append("Name : "+entry.getKey()).append(line);
			for(String value: entry.getValue())
			{
				str.append("    Value: "+value).append(line);
			}
		}
		str.append("Java Doc").append(line);
		for(Entry<String, String> entry : javaDoc.entrySet())
		{
			str.append("Name : "+ entry.getKey()).append(line);
			str.append("    Value: "+entry.getValue()).append(line);
		}
		str.append("Params").append(line);
		for(Entry<String, RestParamsModel> entry : paramsMap.entrySet())
		{
			str.append("Name : "+ entry.getKey()).append(line);
			str.append("    Desc: "+entry.getValue().getDesc()).append(line);
			str.append("    Type: "+entry.getValue().getType()).append(line);
			str.append("    Annotation: "+entry.getValue().getAnnotation()).append(line);
		}
		return str.toString();
	}
	
}
