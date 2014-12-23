package org.k4java.rest.tool.doc.generator;

public class Doc4RestModel {
	private String logoLink;
	private String logoName;
	private String logoImageLink;
	
	public Doc4RestModel(String link, String name, String image) {
		this.logoName = name;
		this.logoImageLink = image;
		this.logoLink = link;
	}
	
	public String getLogoLink() {
		return logoLink;
	}
	public void setLogoLink(String logoLink) {
		this.logoLink = logoLink;
	}
	public String getLogoName() {
		return logoName;
	}
	public void setLogoName(String logoName) {
		this.logoName = logoName;
	}
	public String getLogoImageLink() {
		return logoImageLink;
	}
	public void setLogoImageLink(String logoImageLink) {
		this.logoImageLink = logoImageLink;
	}
	
	
}
