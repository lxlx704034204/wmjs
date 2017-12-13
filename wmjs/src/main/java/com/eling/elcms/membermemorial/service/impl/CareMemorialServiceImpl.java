package com.eling.elcms.membermemorial.service.impl;

import com.eling.elcms.core.service.impl.GenericManagerImpl;
import com.eling.elcms.membermemorial.dao.ICareMemorialDao;
import com.eling.elcms.membermemorial.model.CareMemorial;
import com.eling.elcms.membermemorial.service.ICareMemorialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Liuyp on 2017/9/11.
 */
@Service
public class CareMemorialServiceImpl extends GenericManagerImpl<CareMemorial,Long> implements ICareMemorialService{

    ICareMemorialDao careMemorialDao;

    @Autowired
    public void setDao(ICareMemorialDao dao) {
        this.dao = dao;
        this.careMemorialDao = dao;
    }

    @Override
    public void cancel(CareMemorial careMemorial) {
        careMemorialDao.remove(careMemorial);
    }
}
