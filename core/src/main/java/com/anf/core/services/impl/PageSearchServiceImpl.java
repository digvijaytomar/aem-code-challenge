/***Begin Code - Digvijay Singh Tomar ***/
package com.anf.core.services.impl;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

import javax.jcr.Session;
import javax.jcr.NodeIterator;
import javax.jcr.Node;
import javax.jcr.RepositoryException;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.SearchResult;
import com.day.cq.search.result.Hit;

import javax.jcr.query.QueryManager;
import javax.jcr.query.QueryResult;

import com.anf.core.services.PageSearchService;
import com.anf.core.constants.ApplicationConstants;
import com.anf.core.bean.PageSearchBean;

@Component(immediate = true, service = PageSearchService.class)
public class PageSearchServiceImpl implements PageSearchService {

	private static final Logger LOGGER = LoggerFactory.getLogger(PageSearchServiceImpl.class);
	
	@Reference
	private ResourceResolverFactory resourceResolverFactory;
	
	@Reference
	private QueryBuilder queryBuilder;
	
	private ResourceResolver resourceResolver;
	
	private Session session;
	
	private List<PageSearchBean> pageSearchResultItems;
	
    /*
	 * getSerachResults method will search within respository based
	 * on search type
	 * 
	 * @param searchType
	 * 
	 * @return pageSearchItems
	 */
    @Override
    public List<PageSearchBean> getSerachResults(String searchType) {
    	/*
		 * Service user mapping for perform operation on specific resource
		 */
		Map<String, Object> pageSearchService = new HashMap<String, Object>();
		pageSearchService.put(ResourceResolverFactory.SUBSERVICE, "pageEventService");
		
		List<PageSearchBean> pageSearchItems = null;
	    try{
        	this.resourceResolver = resourceResolverFactory.getServiceResourceResolver(pageSearchService);
        	this.session = this.resourceResolver.adaptTo(Session.class);
        	if(searchType.equals(ApplicationConstants.QUERY_BUILDER_SEARCH_TYPE)){
        		pageSearchItems = getQueryBuilderResults();
        	}
        	else{
        		pageSearchItems = getJcrQueryResults();
        	}
    	}
	    catch(LoginException ex){
	    	LOGGER.error("Exception occurred while getting resource resolver getSerachResults for page search:: ", ex.getMessage(), ex);
	    }
		catch (Exception ex) {
			LOGGER.error("Exception occurred while fetching results for page search:: ", ex.getMessage(), ex);
		}
		return pageSearchItems;
    }
    
	/**
	 * This method is used to return page search list based on query
	 * builder api
	 * 
	 * @return pageSearchResultItems
	 */
	public List<PageSearchBean> getQueryBuilderResults() {
		try{
			this.pageSearchResultItems = new ArrayList<>();
			HashMap<String, String> pageSearchQueryMap = new HashMap<String, String>();
        	
        	pageSearchQueryMap.put("path", ApplicationConstants.PAGE_SEARCH_PATH);
        	//pageSearchQueryMap.put("path.self", "true");
        	pageSearchQueryMap.put("type", "cq:Page");
        	pageSearchQueryMap.put("property", "jcr:content/anfCodeChallenge");
        	pageSearchQueryMap.put("property.value", "true");
        	pageSearchQueryMap.put("1_orderby", "@jcr:content/jcr:created");
        	pageSearchQueryMap.put("1_orderby.sort", "asc");
        	pageSearchQueryMap.put("p.offset", "0");
        	pageSearchQueryMap.put("p.limit", "10");
        	
			/*
			 * Serching pages based on page property flag anfCodeChallenge
			 */
			if (null != this.resourceResolver) {
				PredicateGroup predicate = PredicateGroup.create(pageSearchQueryMap);
				Query query = queryBuilder.createQuery(predicate, this.session);
				SearchResult searchResults = query.getResult();
				for (Hit hit : searchResults.getHits()) {
					String pageTitle = hit.getTitle();
					String pagePath = hit.getPath() + ApplicationConstants.URL_EXTENSION;
                    
                    PageSearchBean pageSearchBean = new PageSearchBean();
					
					/*
					 * Creating & Setting bean for all result items.
					 */
                    pageSearchBean.setPageTitle(pageTitle);
                    pageSearchBean.setPageUrl(pagePath);
					
                    this.pageSearchResultItems.add(pageSearchBean);
				}
			}
		}catch(RepositoryException re){
			LOGGER.error("Error in query builder create query in getQueryBuilderResults" + re);
		}
		catch(Exception ex){
			LOGGER.error("Exception occurred in getQueryBuilderResults"+ ex);
		}
		finally {
			if (this.session != null && this.session.isLive()) {
				this.session.logout(); 
			}
			if (this.resourceResolver != null && this.resourceResolver.isLive()) {
				this.resourceResolver.close();
			}
		}
		return this.pageSearchResultItems;
	}
	
	/**
	 * This method is used to return page search list based on
	 * JCR-SQL2
	 * 
	 * @return pageSearchResultItems
	 */
	public List<PageSearchBean> getJcrQueryResults() {
		try{
			this.pageSearchResultItems = new ArrayList<>();

			/*
			 * Serching pages based on page property flag anfCodeChallenge
			 */
			if (null != this.resourceResolver) {
				
				QueryManager queryManager = this.session.getWorkspace().getQueryManager();
				javax.jcr.query.Query query = queryManager.createQuery(ApplicationConstants.QUERY_STATEMENT, javax.jcr.query.Query.JCR_SQL2);
				query.setOffset(0);
				query.setLimit(10);

				QueryResult searchResult = query.execute();
				NodeIterator resultNodesItr = searchResult.getNodes();
				/*
				 * Iterating over search result nodes
				 */
				while (resultNodesItr.hasNext()) {
					Node pageNode = resultNodesItr.nextNode();
					PageSearchBean pageSearchBean = new PageSearchBean();
					/*
					 * Creating & Setting bean for all result items.
					 */
                    pageSearchBean.setPageTitle(pageNode.getName());
                    pageSearchBean.setPageUrl(pageNode.getPath()+ApplicationConstants.URL_EXTENSION);
					
                    this.pageSearchResultItems.add(pageSearchBean);
				}
			}
		}catch(RepositoryException re){
			LOGGER.error("Error in query builder create query in getJcrQueryResults" + re);
		}
		catch(Exception ex){
			LOGGER.error("Exception occurred in getQueryBuilderResults"+ ex);
		}
		finally {
			if (this.session != null && this.session.isLive()) {
				this.session.logout(); 
			}
			if (this.resourceResolver != null && this.resourceResolver.isLive()) {
				this.resourceResolver.close();
			}
		}
		return this.pageSearchResultItems;
	}
}
/***END Code*****/
