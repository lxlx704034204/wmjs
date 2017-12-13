package com.eling.elcms.membermemorial.dao.hibernate;

import com.eling.elcms.core.dao.hibernate.GenericDaoHibernate;
import com.eling.elcms.membermemorial.dao.IMemorialObituaryDao;
import com.eling.elcms.membermemorial.model.MemorialObituary;
import org.springframework.stereotype.Repository;

@Repository
public class MemorialObituaryDaoHibernate extends GenericDaoHibernate<MemorialObituary, Long> implements IMemorialObituaryDao {

	public MemorialObituaryDaoHibernate() {
		super(MemorialObituary.class);
		
	}
}
