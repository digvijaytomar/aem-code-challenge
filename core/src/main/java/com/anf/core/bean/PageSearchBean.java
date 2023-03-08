/*
 *  Copyright 2015 Adobe Systems Incorporated
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.anf.core.bean;

/**
 * This class will set/get value for page search results.
 */
public class PageSearchBean {

	private String pageTitle;
	private String pageUrl;
    
	/**
	 * This will get page title
	 * @return pageTitle
	 */
	public String getPageTitle() {
		return pageTitle;
	}
    
	/**
	 * This will set page title 
	 */
	public void setPageTitle(String pageTitle) {
		this.pageTitle = pageTitle;
	}
	
	/**
	 * This will return the page url
	 * @return pageUrl
	 */
	public String getPageUrl() {
		return pageUrl;
	}
     
	/**
	 * This will set page url
	 */
	public void setPageUrl(String pageUrl) {
		this.pageUrl = pageUrl;
	}
}
