package com.eling.elcms.membermemorial.dao.hibernate;

import com.eling.elcms.core.dao.hibernate.GenericDaoHibernate;
import com.eling.elcms.membermemorial.dao.IMemorialDetailDao;
import com.eling.elcms.membermemorial.model.MemorialDetail;
import org.springframework.stereotype.Repository;

@Repository
public class MemorialDetailDaoHibernate extends GenericDaoHibernate<MemorialDetail, Long>implements IMemorialDetailDao {

	public MemorialDetailDaoHibernate() {
		super(MemorialDetail.class);
		
	}
}
