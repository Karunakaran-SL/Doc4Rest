package org.k4java.rest.tool.doc.generator;

import java.util.LinkedHashMap;
import java.util.Map;

public class GServicesPage {
	private String serviceName;
	private String path;
	private String desc;
	private String link;
	private Map<String, String> sampleRequestMap = new LinkedHashMap<String, String>();
	private Map<String, String> serviceAttributeMap = new LinkedHashMap<String, String>();
	private Map<String, GApiPage> apiMap;
	public GServicesPage(String service, String path, String desc, String link) {
		this.serviceName = service;
		this.path = path;
		this.desc = desc;
		this.link = link;
	}
	
	
	public String getServiceName() {
		return serviceName;
	}


	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}


	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}

	public Map<String, String> getServiceAttributeMap() {
		return serviceAttributeMap;
	}


	public void setServiceAttributeMap(Map<String, String> serviceAttributeMap) {
		this.serviceAttributeMap = serviceAttributeMap;
	}


	public Map<String, String> getSampleRequestMap() {
		return sampleRequestMap;
	}


	public void setSampleRequestMap(Map<String, String> sampleRequestMap) {
		this.sampleRequestMap = sampleRequestMap;
	}


	public Map<String, GApiPage> getApiMap() {
		return apiMap;
	}


	public void setApiMap(Map<String, GApiPage> apiMap) {
		this.apiMap = apiMap;
	}
	
}
