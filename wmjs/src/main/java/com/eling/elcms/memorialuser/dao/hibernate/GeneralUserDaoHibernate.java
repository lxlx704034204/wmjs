package com.eling.elcms.memorialuser.dao.hibernate;

import com.eling.elcms.core.dao.hibernate.GenericDaoHibernate;
import com.eling.elcms.memorialuser.dao.IGeneralUserDao;
import com.eling.elcms.memorialuser.model.GeneralUser;
import org.springframework.stereotype.Repository;

/**
 * Created by Liuyp on 2017/9/8.
 */
@Repository
public class GeneralUserDaoHibernate extends GenericDaoHibernate<GeneralUser,Long> implements IGeneralUserDao {

    public GeneralUserDaoHibernate() {
        super(GeneralUser.class);

    }
}
