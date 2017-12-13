package com.eling.elcms.memorialapp.service;

import com.eling.elcms.memorialapp.model.WebNews;
import com.eling.elcms.memorialapp.model.view.WebNewsView;
import com.eling.elcms.core.service.IGenericManager;

import java.util.Date;
import java.util.List;

public interface IWebNewsManager extends IGenericManager<WebNews, Long> {

	WebNews open(Long pkWebNews);

	WebNews close(Long pkWebNews);

	WebNews release(Long pkWebNews);
	
	WebNews saveWithImg(WebNews WebMsg);
	
	List<WebNewsView> queryWithoutContent(WebNews WebMsg);
	
	List<WebNewsView> queryTopWithoutContent();

	List<WebNews> queryByPkAndCreateDate(Long pkWebNews, Date createDate, Date createDateEnd);
	
	Boolean likeNumAdd1(Long pkWebNews);
	
	Boolean shareNumAdd1(Long pkWebNews);
}
