package org.k4java.rest.tool.doc.generator;

import java.util.ArrayList;
import java.util.List;

public class GResult {
	private boolean status = true;
	private List<String> errorMessage = new ArrayList<String>();
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public List<String> getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(List<String> errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	
}
