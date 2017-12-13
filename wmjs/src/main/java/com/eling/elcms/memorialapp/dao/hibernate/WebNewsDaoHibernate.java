package com.eling.elcms.memorialapp.dao.hibernate;

import com.eling.elcms.memorialapp.dao.IWebNewsDao;
import com.eling.elcms.app.model.AppNews.ReleasedStatus;
import com.eling.elcms.memorialapp.model.WebNews;
import com.eling.elcms.core.dao.hibernate.GenericDaoHibernate;
import org.apache.commons.lang.StringUtils;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public class WebNewsDaoHibernate extends GenericDaoHibernate<WebNews, Long> implements IWebNewsDao {

	public WebNewsDaoHibernate() {
		super(WebNews.class);
	}

	@SuppressWarnings({ "rawtypes" })
	@Override
	public List pageQueryWebNewsWithoutContent(WebNews cond, Integer firstResult, Integer maxResults,
                                               Boolean queryCount, List<Long> orgPks) {

		String orgPk = StringUtils.join(orgPks, ",");
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT");
		if (queryCount != null && queryCount) {
			sql.append("     	count(1)  as count");
		} else {
			sql.append("     pkWebNews,");
			sql.append("     pkOrganization,");
			sql.append("     title,");
			sql.append("     coverPicUrl,");
			sql.append("     previewUrl,");
			sql.append("     releaseDate,");
			sql.append("     createDate,");
			sql.append("     pkCreator,");
			sql.append("     top,");
			sql.append("     seal,");
			sql.append("     likeNum,");
			sql.append("     shareNum,");
			sql.append("     releasedStatus,");
			sql.append("     classify");
		}
		sql.append(" FROM");
		sql.append("     app_news");
		sql.append(" WHERE");
		sql.append(" 1 = 1 ");
		sql.append(" AND pkOrganization in (" + orgPk + ")");
		sql.append(" AND seal = FALSE");
		sql.append(" AND top = FALSE");
		sql.append(" AND releasedStatus = '" + ReleasedStatus.Released + "'");
		sql.append(" AND classify != '" + WebNews.Classify.Law + "'");
		sql.append(" ORDER BY");
		sql.append("     releaseDate DESC");
		if (firstResult != null && maxResults != null & queryCount != null && !queryCount) {
			sql.append("     LIMIT " + firstResult + "," + maxResults + "  ");
		}

		NativeQuery query = getSession().createNativeQuery(sql.toString());

		return query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<WebNews> queryByPkAndCreateDate(Long pkWebNews, Date createDate, Date createDateEnd) {
		StringBuffer hql = new StringBuffer();
		hql.append(
				"FROM WebNews WHERE pkWebNews = :pkWebNews AND createDate BETWEEN :createDate And :createDateEnd");

		Query query = getSession().createQuery(hql.toString());

		query.setParameter("pkWebNews", pkWebNews);
		query.setParameter("createDate", createDate);
		query.setParameter("createDateEnd", createDateEnd);
		return query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> queryTopWithoutContent(List<Long> orgPks) {
		String orgPk = StringUtils.join(orgPks, ",");

		StringBuffer sql = new StringBuffer();
		sql.append("SELECT");
		sql.append("     pkWebNews,");
		sql.append("     pkOrganization,");
		sql.append("     title,");
		sql.append("     coverPicUrl,");
		sql.append("     previewUrl,");
		sql.append("     releaseDate,");
		sql.append("     createDate,");
		sql.append("     pkCreator,");
		sql.append("     top,");
		sql.append("     seal,");
		sql.append("     likeNum,");
		sql.append("     shareNum,");
		sql.append("     releasedStatus,");
		sql.append("     content,classify");
		sql.append(" FROM");
		sql.append("     app_news");
		sql.append(" WHERE");
		sql.append(" 1=1 ");
		sql.append(" AND pkOrganization in (" + orgPk + ")");
		sql.append(" AND seal = FALSE");
		sql.append(" AND top = TRUE");
		sql.append(" AND releasedStatus = '" + ReleasedStatus.Released + "'");
		sql.append(" AND classify != '" + WebNews.Classify.Law + "'");
		sql.append(" ORDER BY");
		sql.append("     releaseDate DESC");

		NativeQuery query = getSession().createNativeQuery(sql.toString());

		return query.list();
	}
}
