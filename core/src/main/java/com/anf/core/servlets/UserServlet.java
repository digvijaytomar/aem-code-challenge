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
package com.anf.core.servlets;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.anf.core.services.ContentService;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.servlets.annotations.SlingServletPaths;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.apache.sling.api.resource.ResourceResolver;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.anf.core.constants.ApplicationConstants;

@Component(service = { Servlet.class })
@SlingServletPaths(
        value = "/bin/saveUserDetails"
)
public class UserServlet extends SlingAllMethodsServlet {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UserServlet.class);

    private static final long serialVersionUID = 1L;

    @Reference
    private ContentService contentService;

    @Override
    protected void doGet(final SlingHttpServletRequest req,
            final SlingHttpServletResponse resp) throws ServletException, IOException {
        // Make use of ContentService to write the business logic
    	
    	/***Begin Code - Digvijay Singh Tomar ***/
    	try {
    		resp.setContentType("application/json");
			
			final ResourceResolver resourceResolver = req.getResourceResolver();
			Gson gson = new GsonBuilder().disableHtmlEscaping().create();
			if(null != req.getParameter(ApplicationConstants.USER_DETAIL_PARAM)) {
				String userDetails = req.getParameter(ApplicationConstants.USER_DETAIL_PARAM);
				if(contentService.validateUserAge(resourceResolver,userDetails)){
					Map<String, String> userDetailSubmitted = contentService.commitUserDetails(resourceResolver,userDetails);
					resp.setStatus(SlingHttpServletResponse.SC_OK);
					resp.getWriter().write(gson.toJson(userDetailSubmitted));
					resp.getWriter().flush();
					resp.getWriter().close();
				}else{
					Map<String, String> userAgeValidationResponse = new HashMap<>();
					userAgeValidationResponse.put("success", "false");
					userAgeValidationResponse.put("userValidationMsg", "You are not eligible!");
					
					resp.getWriter().write(gson.toJson(userAgeValidationResponse));
					resp.getWriter().flush();
					resp.getWriter().close();
				}
			}
			
		} catch (Exception ex) {
			LOGGER.error("Exception occurred during process POST request for save user detail :: ", ex.getMessage(), ex);
		}
    	
    	/***END Code*****/
    }
}
