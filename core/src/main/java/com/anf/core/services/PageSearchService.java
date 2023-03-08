package com.anf.core.services;

import java.util.List;
import com.anf.core.bean.PageSearchBean;

public interface PageSearchService {
	List<PageSearchBean> getSerachResults(String searchType);
}
