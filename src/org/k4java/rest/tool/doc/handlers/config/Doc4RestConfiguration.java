package org.k4java.rest.tool.doc.handlers.config;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.k4java.rest.tool.doc.model.RestDoc;

public class Doc4RestConfiguration {

	private static Doc4RestConfiguration m_instanse = new Doc4RestConfiguration();
	private static List<String> javaDocComments = new ArrayList<String>();
	private static List<RestDoc> serviceAnnotations = new ArrayList<RestDoc>();
	private static List<RestDoc> apiAnnotations = new ArrayList<RestDoc>();
	private static List<String> opertaionsList = new ArrayList<String>();
	private boolean isClassScanRequired = false;
	private String fileLocation;
	private boolean isInterfaceScanRequired = true;
	static
	{
		javaDocComments.add(getSummaryKey());
		javaDocComments.add(getServiceName());
		javaDocComments.add("@sampleRequest");
		javaDocComments.add("@sampleResponse");
		javaDocComments.add("@sampleRequestXML");
		javaDocComments.add("@sampleResponseXML");
		javaDocComments.add("@sampleRequestJSON");
		javaDocComments.add("@sampleResponseJSON");
		
		opertaionsList.add("GET");
		opertaionsList.add("PUT");
		opertaionsList.add("POST");
		opertaionsList.add("DELETE");
	}
	
	static
	{
		serviceAnnotations.add(new RestDoc(getMainRestDecider(), "string"));
		serviceAnnotations.add(new RestDoc("CookieParam", "string"));
		serviceAnnotations.add(new RestDoc("Encoded", "string"));
		serviceAnnotations.add(new RestDoc("ApplicationPath", "string"));
	}
	
	static
	{
		apiAnnotations.add(new RestDoc(getMainRestDecider(), "string"));
		apiAnnotations.add(new RestDoc("CookieParam", "string"));
		apiAnnotations.add(new RestDoc("Encoded", "string"));
		apiAnnotations.add(new RestDoc("Produces", "string"));
		apiAnnotations.add(new RestDoc("Consumes", "string"));
		apiAnnotations.add(new RestDoc("Head", "string"));
		apiAnnotations.add(new RestDoc("HttpMethod", "string"));
		apiAnnotations.add(new RestDoc("Options", "string"));
				
		apiAnnotations.add(new RestDoc("FormParam", "list"));
		apiAnnotations.add(new RestDoc("HeaderParam", "list"));
		apiAnnotations.add(new RestDoc("MatrixParam", "list"));
		apiAnnotations.add(new RestDoc("PathParam", "list"));
		apiAnnotations.add(new RestDoc("QueryParam", "list"));
		apiAnnotations.add(new RestDoc("GET", "list"));
		apiAnnotations.add(new RestDoc("PUT", "list"));
		apiAnnotations.add(new RestDoc("POST", "list"));
		apiAnnotations.add(new RestDoc("DELETE", "list"));
	}
	private Doc4RestConfiguration(){};
	
	
	public static Doc4RestConfiguration getInstance()
	{
		return m_instanse;
	}

	public boolean isClassScanRequired() {
		return isClassScanRequired;
	}

	/**
	 * 
	 * @param isClassScanRequired
	 */
	public void setClassScanRequired(boolean isClassScanRequired) {
		this.isClassScanRequired = isClassScanRequired;
	}

	public boolean isInterfaceScanRequired() {
		return isInterfaceScanRequired;
	}

	public void setInterfaceScanRequired(boolean isInterfaceScanRequired) {
		this.isInterfaceScanRequired = isInterfaceScanRequired;
	}

	/**
	 * @return the javaDocComments
	 */
	public static List<String> getJavaDocComments() {
		return javaDocComments;
	}

	/**
	 * @return the serviceAnnotations
	 */
	public static List<RestDoc> getServiceAnnotations() {
		return serviceAnnotations;
	}

	
	public static List<String> getOpertaionsList() {
		return opertaionsList;
	}


	/**
	 * @return the apiAnnotations
	 */
	public static List<RestDoc> getApiAnnotations() {
		return apiAnnotations;
	}
	
	public static String getMainRestDecider()
	{
		return "Path";
	}
	
	public static String getEmptyParam()
	{
		return "";
	}
	
	public static String getSummaryKey()
	{
		return "@summary";
	}
	
	public static String getServiceName()
	{
		return "@serviceName";
	}
	
	public static String getParamsKey()
	{
		return "@param";
	}
	
	public static String getParamsSplitChar()
	{
		return "-";
	}
	
	public static String getApplicationPath()
	{
		return "";
	}
	
	public static String getFileSeparator()
	{
		return File.separator;
	}
	
	public static String getServiceFolderName()
	{
		return "services";
	}
	
	public static String getAPIFolderName()
	{
		return "apis";
	}
	
	public String getFileLocation()
	{
		return fileLocation;
	}
	
	
	public void setFileLocation(String fileLocation) {
		this.fileLocation = fileLocation;
	}


	public static String getFolderName()
	{
		return "Doc4Rest";
	}
}
