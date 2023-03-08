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
package com.anf.core.models;



import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.anf.core.bean.NewsFeedBean;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Calendar;
import java.text.SimpleDateFormat;

/**
 * This model will generate map to generate news feed.
 */
@Model(adaptables = Resource.class)
public class NewsFeed {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(NewsFeed.class);

	@Inject @Named("newsFeedPath") @Optional
	private Resource newsFeedResource;

	private List<NewsFeedBean> newsFeedItem;
	
	private static final String DATE_FORMAT_NOW = "MM.dd.yyyy";
	
    /**
	 * This method will call populateNewsList method to populate list of
	 * news.
	 */
	@PostConstruct
	protected void init() {
		if (newsFeedResource != null) {
			populateNewsList(newsFeedResource);
		}
	}
	
	/**
	 * This method is used to populate news list items based on
	 * provided path
	 * 
	 * @param newsFeedResource
	 */
	public List<NewsFeedBean> populateNewsList(Resource newsFeedResource) {
		try {
			if (newsFeedResource != null) {
				newsFeedItem = new ArrayList<>();
				Iterator<Resource> newsListResources = newsFeedResource.listChildren();

				while (newsListResources.hasNext()) {
					ValueMap valueMap = newsListResources.next().adaptTo(ValueMap.class);

					if (valueMap != null) {
						NewsFeedBean newsFeedBean = new NewsFeedBean();
						
						/*
						 * Creating & Setting bean for all required items.
						 */
						newsFeedBean.setTitle(valueMap.get("title", String.class));
						newsFeedBean.setAuthor(valueMap.get("author", String.class));
						newsFeedBean.setCurrentDate(getNewsCurrentDate());
						newsFeedBean.setDescription(valueMap.get("description", String.class));
						newsFeedBean.setImagePath(valueMap.get("urlImage", String.class));

						newsFeedItem.add(newsFeedBean);
					}
				}
			}
		} catch (Exception ex) {
			LOGGER.error("Invalid resource in populateNewsList :: {}. ", newsFeedResource, ex);
		}
		return newsFeedItem;
	}
	
	/**
	 * This method is used to return news list to iterate in
	 * news feed component
	 * 
	 * @return newsFeedItem
	 */
	public List<NewsFeedBean> getNewsFeedItem() {
		return newsFeedItem;
	}
	
	/**
	 * This method is used to current date to display 
	 * for news list items
	 * 
	 * @return currentDate
	 */
	private String getNewsCurrentDate() {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
		return sdf.format(cal.getTime());
	}
}
