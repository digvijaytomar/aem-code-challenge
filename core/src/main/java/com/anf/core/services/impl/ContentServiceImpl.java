package com.anf.core.services.impl;

import java.util.Map;
import java.util.HashMap;
import com.anf.core.services.ContentService;
import org.osgi.service.component.annotations.Component;

import javax.jcr.Session;
import javax.jcr.Node;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.commons.json.JSONArray;
import org.apache.sling.commons.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.commons.jcr.JcrUtil;
import com.day.cq.commons.jcr.JcrConstants;

import com.anf.core.constants.ApplicationConstants;

@Component(immediate = true, service = ContentService.class)
public class ContentServiceImpl implements ContentService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ContentServiceImpl.class);
	
	/***Begin Code - Digvijay Singh Tomar ***/
	
    /*
	 * commitUserDetails method will commit user detail creating
	 * node in repository 
	 * 
	 * @param resourceResolver
	 * @param userDetails
	 * 
	 * @return jsonResponse
	 */
    @Override
    public Map commitUserDetails(ResourceResolver resourceResolver,String userDetails) {
        // Add your logic. Modify method signature as per need.
    	Map<String, String> successResponse = null;
    	try{
	    	JSONObject userDetailJson = new JSONObject(userDetails);
			JSONArray userDetailValues = userDetailJson.getJSONArray(ApplicationConstants.USER_DETAIL_PARAM);
			if(userDetailValues.length() > 0){
				successResponse = new HashMap<>();
				for(int i=0; i<userDetailValues.length(); i++){
		            JSONObject jsonObj = userDetailValues.getJSONObject(i);
		            String firstName = jsonObj.getString("firstName");
		            String lastName = jsonObj.getString("lastName");
		            String age = jsonObj.getString("age");
		            String country = jsonObj.getString("country");
		            
		            int min = 1; 
		            int max = 999999999; 
		            int userUniqueId = (int)Math.floor(Math.random() * (max - min + 1) + min);
		            String userDetailPath = ApplicationConstants.USER_DETAIL_PARENT_NODE.concat("/")+String.valueOf(userUniqueId).concat("-").concat(firstName.concat(lastName));
		            Session session = (Session) resourceResolver.adaptTo(Session.class);
		            Node userDetailNode = JcrUtil.createPath(userDetailPath, "nt:unstructured", "nt:unstructured", session, true);	
		            
		            if(null != userDetailNode) {
						
						userDetailNode.setProperty("firstName",firstName);
						userDetailNode.setProperty("lastName",lastName);
						userDetailNode.setProperty("age",age);
						userDetailNode.setProperty("country",country);
						
						userDetailNode.save();
						
						successResponse.put("success", "true");
						successResponse.put("successMsg", "Form has been submitted successfully!");
					}
				}
			}
    	}
		catch (Exception ex) {
			LOGGER.error("Exception occurred while saving user detail:: ", ex.getMessage(), ex);
		}
		return successResponse;
    }
    
    
    /*
	 * validateUserAge() method will validate user age 
	 * 
	 * @param resourceResolver
	 * @param userDetails
	 * 
	 * @return true/false
	 */
    @Override
    public boolean validateUserAge(ResourceResolver resourceResolver,String userDetails) {
        // Add your logic. Modify method signature as per need.
    	boolean userValidated = false;
    	try{
	    	JSONObject userDetailJson = new JSONObject(userDetails);
			JSONArray userDetailValues = userDetailJson.getJSONArray(ApplicationConstants.USER_DETAIL_PARAM);
			if(userDetailValues.length() > 0){
				
				Resource userAgeRes = resourceResolver.getResource(ApplicationConstants.USER_AGE_NODE);
				ValueMap valueMap = userAgeRes.getValueMap();
				int maxAge = Integer.parseInt(valueMap.get("maxAge", String.class));
				int minAge = Integer.parseInt(valueMap.get("minAge", String.class));
				
				JSONObject jsonObj = userDetailValues.getJSONObject(0);
				int userAge = Integer.parseInt(jsonObj.getString("age"));
				
				if(userAge > minAge && userAge < maxAge){
					userValidated = true;
				}
				
			}
    	}
		catch (Exception ex) {
			LOGGER.error("Exception occurred while validating user detail:: ", ex.getMessage(), ex);
		}
		return userValidated;
    }
    /***END Code*****/
}
