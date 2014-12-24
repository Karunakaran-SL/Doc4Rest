package org.k4java.rest.tool.doc.generator;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.k4java.rest.tool.doc.model.RestParamsModel;

public class GApiPage {
	private String path;
	private String desc;
	private String link;
	private String link4Index;
	private String link4Service;
	private String link4Api;
	private List<String> operation = new ArrayList<String>();
	private Map<String, String> sampleRequestMap = new LinkedHashMap<String, String>();
	private Map<String, String> serviceAttributeMap = new LinkedHashMap<String, String>();
	private Map<String, RestParamsModel> paramsMap = new LinkedHashMap<String, RestParamsModel>();
	
	public GApiPage(String path, String desc, String link) {
		this.path = path;
		this.desc = desc;
		this.link = link;
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

	public List<String> getOperation() {
		return operation;
	}

	public void setOperation(List<String> operation) {
		this.operation = operation;
	}

	public Map<String, RestParamsModel> getParamsMap() {
		return paramsMap;
	}

	public void setParamsMap(Map<String, RestParamsModel> paramsMap) {
		this.paramsMap = paramsMap;
	}

	public String getLink4Index() {
		return link4Index;
	}

	public void setLink4Index(String link4Index) {
		this.link4Index = link4Index;
	}

	public String getLink4Service() {
		return link4Service;
	}

	public void setLink4Service(String link4Service) {
		this.link4Service = link4Service;
	}

	public String getLink4Api() {
		return link4Api;
	}

	public void setLink4Api(String link4Api) {
		this.link4Api = link4Api;
	}
	
}
