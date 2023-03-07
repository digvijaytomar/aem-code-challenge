package com.anf.core.models;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Iterator;
import java.util.List;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.anf.core.bean.NewsFeedBean;

import junitx.util.PrivateAccessor;

@RunWith(MockitoJUnitRunner.class)
public class NewsFeedTest {

	private NewsFeed newsFeed;

	@Mock
	private Resource resource;

	@Before
	public void setUp() throws Exception {
		newsFeed = new NewsFeed();
	}

	@Test
	public void whenpopulateNewsList_thenSuccess_withEmptyNewsFeedResource() throws Exception {
		PrivateAccessor.setField(newsFeed, "newsFeedResource", null);
		newsFeed.init();
		List<NewsFeedBean> newslList = newsFeed.getNewsFeedItem();
		assertNull(newslList);
	}

	@Test
	public void whenpopulateNewsList_thenSuccess_withValidNewsListResource() throws Exception {
		PrivateAccessor.setField(newsFeed, "newsFeedResource", resource);
		ValueMap valueMap = Mockito.mock(ValueMap.class);

		@SuppressWarnings("unchecked")
		Iterator<Resource> iterator = Mockito.mock(Iterator.class);

		Mockito.when(resource.adaptTo(ValueMap.class)).thenReturn(valueMap);

		Mockito.when(resource.listChildren()).thenReturn(iterator);
		Mockito.when(iterator.next()).thenReturn(resource);
		Mockito.when((iterator.hasNext())).thenReturn(true).thenReturn(true).thenReturn(false);
		Mockito.when(valueMap.get("title", String.class)).thenReturn("Foxes and D.C. Politicians Don’t Mix, As This Week and History Show");
		Mockito.when(valueMap.get("author", String.class)).thenReturn("Leah Askarinam");
		Mockito.when(valueMap.get("description", String.class)).thenReturn("The nation’s founding fathers hunted foxes for sport. This week, a rabid fox that bit nine people near the U.S. Capitol led to a very different type of hunt.");
		Mockito.when(valueMap.get("urlImage", String.class)).thenReturn("https://static01.nyt.com/images/2022/04/06/us/politics/-06onpolitics-pm-newsletter-fox/-06onpolitics-pm-newsletter-fox-facebookJumbo-v2.jpg");
		
		newsFeed.init();
		List<NewsFeedBean> newsList = newsFeed.getNewsFeedItem();
		assertNotNull(newsList);
		assertTrue(newsList.size() > 0);

	}

}
