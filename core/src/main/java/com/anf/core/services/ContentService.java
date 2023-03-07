package com.anf.core.services;

import org.apache.sling.api.resource.ResourceResolver;
import java.util.Map;

public interface ContentService {
	Map commitUserDetails(ResourceResolver resourceResolver,String userDetails);
	
	boolean validateUserAge(ResourceResolver resourceResolver,String userDetails);
}
