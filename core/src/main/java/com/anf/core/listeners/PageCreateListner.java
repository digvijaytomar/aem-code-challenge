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
package com.anf.core.listeners;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

import org.apache.sling.api.resource.LoginException;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.propertytypes.ServiceDescription;
import org.apache.sling.api.resource.observation.ResourceChangeListener;
import org.apache.sling.api.resource.observation.ResourceChange;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import com.day.cq.commons.jcr.JcrConstants;
import javax.jcr.Node;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This listener will listen for page creation and it will add
 * boolean pageCreation property in jcr:content node of the page.
 */
@Component(service = ResourceChangeListener.class, immediate = true, property = {
		ResourceChangeListener.PATHS + "=" + "/content/anf-code-challenge/us/en",
		ResourceChangeListener.CHANGES + "=" + "ADDED", 
		ResourceChangeListener.CHANGES + "=" + "CHANGED" })
@ServiceDescription("Listner for page creation events to set page properties.")
public class PageCreateListner implements ResourceChangeListener {

	private final Logger LOGGER = LoggerFactory.getLogger(getClass());

	@Reference
	private ResourceResolverFactory resourceResolverFactory;
	
	private ResourceResolver resourceResolver;

	@Override
    public void onChange(List<ResourceChange> resourceChangeList) {
		
		/*
		 * Service user mapping for perform operation on specific resource
		 */
		Map<String, Object> pageEventService = new HashMap<String, Object>();
		pageEventService.put(ResourceResolverFactory.SUBSERVICE, "pageEventService");
		
	    String createdPageJcrPath = "";
	    try {
	    	this.resourceResolver = resourceResolverFactory.getServiceResourceResolver(pageEventService);
	        for (ResourceChange resourceChange : resourceChangeList) {
				String resourcePath = resourceChange.getPath();
				if(resourcePath.endsWith(JcrConstants.JCR_CONTENT)){
					createdPageJcrPath = resourcePath;
				}
	        }
	        /*
	         * adapting page resource through node to set boolean
	         * property in jcr:content node of the page
	         */
	        Resource resource = this.resourceResolver.getResource(createdPageJcrPath);
	        if(null != resource){
				Node adaptCreatedPageNode = resource.adaptTo(Node.class);
				boolean pageCreated = true;
				adaptCreatedPageNode.setProperty("pageCreated",pageCreated);
				
				adaptCreatedPageNode.save();
	        }
	    }
	    catch(LoginException ex){
	    	LOGGER.error("Exception occurred while getting resource resolver for page creation listener:: ", ex.getMessage(), ex);
	    }
	    catch(Exception ex){
	    	LOGGER.error("Exception occurred while setting property on page creation:: ", ex.getMessage(), ex);
	    }
	    finally {
			if (this.resourceResolver != null && this.resourceResolver.isLive()) {
				this.resourceResolver.close();
			}
		}
	}
}