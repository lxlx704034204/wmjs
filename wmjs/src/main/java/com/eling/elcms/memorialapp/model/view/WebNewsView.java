package com.eling.elcms.memorialapp.model.view;

import com.eling.elcms.memorialapp.model.WebNews;

public class WebNewsView extends WebNews {

	private static final long serialVersionUID = 4178440567597617361L;
	
	private Long pkOrganization;
	
	private Long pkCreator;
	
	private String summary;

	public Long getPkOrganization() {
		return pkOrganization;
	}

	public void setPkOrganization(Long pkOrganization) {
		this.pkOrganization = pkOrganization;
	}

	public Long getPkCreator() {
		return pkCreator;
	}

	public void setPkCreator(Long pkCreator) {
		this.pkCreator = pkCreator;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

}
