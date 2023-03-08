package com.anf.core.constants;

/**
 * Constants variable used across different components.
 */
public final class ApplicationConstants {

	/*
	 * CONSTANTS FOR SERVICES & SERVLETS
	 */
	public static final String USER_DETAIL_PARAM = "userDetails";
	
	public static final String USER_DETAIL_PARENT_NODE  = "/var/anf-code-challenge";
	
	public static final String USER_AGE_NODE = "/etc/age";
	
	public static final String PAGE_SEARCH_PATH = "/content/anf-code-challenge/us/en";
	
	public static final String QUERY_BUILDER_SEARCH_TYPE = "qb";
	
	public static final String URL_EXTENSION = ".html";
	
	public static final String QUERY_STATEMENT = "SELECT page.* FROM [cq:Page] AS page INNER JOIN [cq:PageContent] AS jcrContentNode ON ISCHILDNODE(jcrContentNode, page) WHERE ISDESCENDANTNODE(page, '/content/anf-code-challenge/us/en') AND jcrContentNode.[anfCodeChallenge] = CAST('true' AS BOOLEAN) ORDER BY jcrContentNode.[jcr:created]";

	/**
     * The Private Constructor.
     */
    private ApplicationConstants() {    }

}