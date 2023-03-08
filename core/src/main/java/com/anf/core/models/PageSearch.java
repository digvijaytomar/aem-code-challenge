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


import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;
import org.apache.commons.lang.StringUtils;
import org.osgi.service.component.annotations.Reference;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.anf.core.bean.PageSearchBean;
import com.anf.core.services.PageSearchService;

/**
 * This model will generate list of top 10 pages based on query type.
 */
@Model(adaptables = Resource.class)
public class PageSearch {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PageSearch.class);

	@Inject @Named("searchType") @Optional
	private String searchType;

	private List<PageSearchBean> pageSearchItems;
	
    @Inject
    private PageSearchService pageSearchService;
	
    /**
	 * This method will call populatePageSearchList method to populate list of
	 * searched pages based on anfCodeChallenge property.
	 */
	@PostConstruct
	protected void init() {
		if(StringUtils.isNotBlank(this.searchType)) {
			populatePageSearchList(this.searchType);
		}
	}
	
	/**
	 * This method is used to populate page list items based on
	 * anfCodeChallenge property
	 * 
	 * @param searchType
	 */
	public List<PageSearchBean> populatePageSearchList(String searchType) {
		try {
			pageSearchItems = pageSearchService.getSerachResults(searchType);
		} catch (Exception ex) {
			LOGGER.error("error in search type in populatePageSearchList :: {}. ", searchType, ex);
		}
		return pageSearchItems;
	}
	
	/**
	 * This method is used to return page search list to iterate in
	 * page search component
	 * 
	 * @return pageSearchItems
	 */
	public List<PageSearchBean> getPageSearchItems() {
		return pageSearchItems;
	}
}
