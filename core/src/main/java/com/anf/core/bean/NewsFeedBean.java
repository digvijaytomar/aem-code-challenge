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
 * This class will set/get value of news feed items.
 */
public class NewsFeedBean {

	private String title;
	private String author;
	private String currentDate;
	private String description;
	private String imagePath;
    
	/**
	 * This will get title
	 * @return title
	 */
	public String getTitle() {
		return title;
	}
    
	/**
	 * This will set title 
	 */
	public void setTitle(String title) {
		this.title = title;
	}
    
	/**
	 * This will return the author name
	 * @return author
	 */
	public String getAuthor() {
		return author;
	}
    
	/**
	 * This will set author name
	 */
	public void setAuthor(String author) {
		this.author = author;
	}
	
	/**
	 * This will put current date
	 * @return currentDate
	 */
	public String getCurrentDate() {
		return currentDate;
	}
	
	/**
	 * This will set current date
	 * 
	 */
	public void setCurrentDate(String currentDate) {
		this.currentDate = currentDate;
	}

	/**
	 * This will set  description
	 * 
	 */
	public String getDescription() {
		return description;
	}
	
	/**
	 * This will set  description
	 * 
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	/**
	 * This will return the news list image path
	 * @return imagePath
	 */
	public String getImagePath() {
		return imagePath;
	}
     
	/**
	 * This will set image path
	 */
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
}
