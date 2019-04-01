package com.tiny.utils;

public class Disclamer {
	/*
	 * Map<String, String> priorities = new HashMap<String, String>();
	 * 
	 * public Map<String, String> getPriorities() { return priorities; }
	 * 
	 * public void setPriorities(Map<String, String> priorities) { this.priorities =
	 * priorities; } 
	 */
	public Disclamer(String priorities,String tinyURL) {
		this.priorities = priorities;
		this.tinyURL = tinyURL;
	}
	private String priorities;
	private String tinyURL;
	/**
	 * @return the priorities
	 */
	public String getPriorities() {
		return priorities;
	}
	/**
	 * @param priorities the priorities to set
	 */
	public void setPriorities(String priorities) {
		this.priorities = priorities;
	}
	/**
	 * @return the tinyURL
	 */
	public String getTinyURL() {
		return tinyURL;
	}
	/**
	 * @param tinyURL the tinyURL to set
	 */
	public void setTinyURL(String tinyURL) {
		this.tinyURL = tinyURL;
	}

	

}
