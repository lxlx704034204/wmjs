package com.eling.elcms.memorialapp.dao;

import com.eling.elcms.memorialapp.model.WebNews;
import com.eling.elcms.core.dao.IGenericDao;

import java.util.Date;
import java.util.List;

public interface IWebNewsDao extends IGenericDao<WebNews, Long> {

	@SuppressWarnings("rawtypes")
	List pageQueryWebNewsWithoutContent(WebNews cond, Integer firstResult, Integer maxResults, Boolean queryCount,
                                        List<Long> orgPks);

	List<Object[]> queryTopWithoutContent(List<Long> orgPks);

	List<WebNews> queryByPkAndCreateDate(Long pkWebNews, Date createDate, Date createDateEnd);
}
