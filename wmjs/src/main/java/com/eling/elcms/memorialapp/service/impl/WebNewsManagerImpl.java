package com.eling.elcms.memorialapp.service.impl;

import com.eling.elcms.memorialapp.dao.IWebNewsDao;
import com.eling.elcms.memorialapp.model.WebNews;
import com.eling.elcms.memorialapp.model.view.WebNewsView;
import com.eling.elcms.memorialapp.service.IWebNewsManager;
import com.eling.elcms.core.AppContext;
import com.eling.elcms.core.exception.BusinessException;
import com.eling.elcms.core.service.impl.GenericManagerImpl;
import com.eling.elcms.core.util.PropertyUtils;
import com.eling.elcms.privilege.service.IRbacService;
import com.eling.elcms.system.model.CommonUser;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class WebNewsManagerImpl extends GenericManagerImpl<WebNews, Long> implements IWebNewsManager {

	IWebNewsDao webNewsDao;


	@Autowired
	private IRbacService rbacService;

	@Autowired
	public void setDao(IWebNewsDao dao) {
		this.dao = dao;
		this.webNewsDao = dao;
	}

	@Override
	public WebNews open(Long pkWebNews) {
		WebNews am = get(pkWebNews);
		am.setSeal(Boolean.FALSE);
		return save(am);
	}

	@Override
	public WebNews close(Long pkWebNews) {
		WebNews am = get(pkWebNews);
		am.setSeal(Boolean.TRUE);
		am.setReleasedStatus(WebNews.ReleasedStatus.UnReleased);
		return save(am);
	}

	@Override
	public WebNews release(Long pkWebNews) {
		WebNews am = get(pkWebNews);
		am.setReleasedStatus(WebNews.ReleasedStatus.Released);
		am.setReleaseDate(new Date());
		return save(am);
	}

	@Override
	public WebNews saveWithImg(WebNews webNews) {
		if(webNews.getTop()){//校验是否置顶超过三条
			WebNews cond = new WebNews();
			cond.setTop(Boolean.TRUE);
			PropertyUtils.setProperty(cond, "organization.pkOrganization", ((CommonUser) AppContext.curUser()).getOrganization().getPkOrganization());
			List<WebNews> list = query(cond);
			if(list.size() >= 3){
				throw new BusinessException("每个机构最多可以有3条置顶资讯！");
			}
		}
		webNews = save(webNews);
		webNews.setCoverPicUrl("api/attachment/webnews/webnews_" + webNews.getPkWebNews());
		return save(webNews);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<WebNewsView> queryWithoutContent(WebNews webMsg) {
		// 获取分页相关条件
		Integer firstResult = PropertyUtils.getProperty(webMsg, "firstResult");
		Integer maxResults = PropertyUtils.getProperty(webMsg, "maxResults");
		Integer countResults = 0;
		List<Long> orgPks = rbacService.getUserAssignment(AppContext.curUser().getPkUser(), "organization");
		orgPks.add(1L);// 拼上平台
		List<WebNewsView> newsList = new ArrayList<WebNewsView>();

		countResults = Integer.parseInt(webNewsDao
				.pageQueryWebNewsWithoutContent(webMsg, firstResult, maxResults, true, orgPks).get(0).toString());

		List<Object[]> list = webNewsDao.pageQueryWebNewsWithoutContent(webMsg, firstResult, maxResults, false, orgPks);
		for (Object[] objects : list) {
			WebNewsView webNews = new WebNewsView();
			webNews.setPkWebNews(((BigInteger) objects[0]).longValue());
			webNews.setPkOrganization(((BigInteger) objects[1]).longValue());
			webNews.setTitle(objects[2] == null ? "" : objects[2].toString());
			webNews.setCoverPicUrl(objects[3] == null ? "" : objects[3].toString());
			webNews.setPreviewUrl(objects[4] == null ? "" : objects[4].toString());
			webNews.setReleaseDate(((Timestamp) objects[5]));
			webNews.setCreateDate(((Timestamp) objects[6]));
			webNews.setPkCreator(((BigInteger) objects[7]).longValue());
			webNews.setTop((Boolean) objects[8]);
			webNews.setSeal((Boolean) objects[9]);
			webNews.setLikeNum((Integer) objects[10]);
			webNews.setShareNum((Integer) objects[11]);
			webNews.setReleasedStatus(WebNews.ReleasedStatus.valueOf(objects[12].toString()));
			webNews.setClassify(WebNews.Classify.valueOf(objects[13].toString()));
			PropertyUtils.setProperty(webNews, "countResults", countResults);
			newsList.add(webNews);
		}
		return newsList;
	}

	@Override
	public List<WebNews> queryByPkAndCreateDate(Long pkWebNews, Date createDate, Date createDateEnd) {
		return webNewsDao.queryByPkAndCreateDate(pkWebNews, createDate, createDateEnd);
	}

	@Override
	public List<WebNewsView> queryTopWithoutContent() {
		List<WebNewsView> newsList = new ArrayList<WebNewsView>();

		List<Long> orgPks = rbacService.getUserAssignment(AppContext.curUser().getPkUser(), "organization");
		orgPks.add(1L);// 拼上平台

		List<Object[]> list = webNewsDao.queryTopWithoutContent(orgPks);

		for (Object[] objects : list) {
			WebNewsView webNews = new WebNewsView();
			webNews.setPkWebNews(((BigInteger) objects[0]).longValue());
			webNews.setPkOrganization(((BigInteger) objects[1]).longValue());
			webNews.setTitle(objects[2] == null ? "" : objects[2].toString());
			webNews.setCoverPicUrl(objects[3] == null ? "" : objects[3].toString());
			webNews.setPreviewUrl(objects[4] == null ? "" : objects[4].toString());
			webNews.setReleaseDate(((Timestamp) objects[5]));
			webNews.setCreateDate(((Timestamp) objects[6]));
			webNews.setPkCreator(((BigInteger) objects[7]).longValue());
			webNews.setTop((Boolean) objects[8]);
			webNews.setSeal((Boolean) objects[9]);
			webNews.setLikeNum((Integer) objects[10]);
			webNews.setShareNum((Integer) objects[11]);
			webNews.setReleasedStatus(WebNews.ReleasedStatus.valueOf(objects[12].toString()));
			if(objects[13] != null){
				//过滤html标签 截取30个字
				webNews.setSummary(StringUtils.substring(StringUtils.replacePattern(objects[13].toString(), "</?[^<]+>", ""), 0, 30));
			}
			newsList.add(webNews);
		}
		return newsList;
	}

	@Override
	public Boolean likeNumAdd1(Long pkWebNews) {
		WebNews webNews = get(pkWebNews);
		if(webNews.getLikeNum() != null){
			int likeNum = webNews.getLikeNum() + 1;
			webNews.setLikeNum(likeNum);
		}else{
			webNews.setLikeNum(1);
		}
		save(webNews);
		return Boolean.TRUE;
	}

	@Override
	public Boolean shareNumAdd1(Long pkWebNews) {
		WebNews webNews = get(pkWebNews);
		if(webNews.getShareNum() != null){
			int shareNum = webNews.getShareNum() + 1;
			webNews.setShareNum(shareNum);
		}else{
			webNews.setLikeNum(1);
		}
		save(webNews);
		return Boolean.TRUE;
	}
}
