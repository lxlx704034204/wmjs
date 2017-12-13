package com.eling.elcms.membermemorial.dao.hibernate;

import com.eling.elcms.core.dao.hibernate.GenericDaoHibernate;
import com.eling.elcms.membermemorial.dao.ICareMemorialDao;
import com.eling.elcms.membermemorial.model.CareMemorial;
import org.springframework.stereotype.Repository;

/**
 * Created by Liuyp on 2017/9/11.
 */
@Repository
public class CareMemorialDaoHibernate extends GenericDaoHibernate<CareMemorial,Long> implements ICareMemorialDao{

    public CareMemorialDaoHibernate(){
        super(CareMemorial.class);
    }

}
