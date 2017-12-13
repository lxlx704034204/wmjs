package com.eling.elcms.membermemorial.dao.hibernate;

import com.eling.elcms.core.dao.hibernate.GenericDaoHibernate;
import com.eling.elcms.membermemorial.dao.IMemberMemorialDao;
import com.eling.elcms.membermemorial.model.MemberMemorial;
import org.springframework.stereotype.Repository;

@Repository
public class MemberMemorialDaoHibernate extends GenericDaoHibernate<MemberMemorial, Long>implements IMemberMemorialDao {

	public MemberMemorialDaoHibernate() {
		super(MemberMemorial.class);
		
	}
}
