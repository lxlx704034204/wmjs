package com.eling.elcms.membermemorial.service.impl;

import com.eling.elcms.core.AppContext;
import com.eling.elcms.core.service.impl.GenericManagerImpl;
import com.eling.elcms.membermemorial.dao.IMemorialObituaryDao;
import com.eling.elcms.membermemorial.model.MemorialObituary;
import com.eling.elcms.membermemorial.service.IMemorialObituaryService;
import com.eling.elcms.system.model.AppUser;
import com.eling.elcms.system.model.CommonUser;
import com.eling.elcms.system.service.IAppUserManager;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class MemorialObituaryServiceImpl extends GenericManagerImpl<MemorialObituary, Long> implements IMemorialObituaryService {

    IMemorialObituaryDao memorialObituaryDao;
    @Autowired
    private IAppUserManager appUserManager;

    @Autowired
    public void setDao(IMemorialObituaryDao dao) {
        this.dao = dao;
        this.memorialObituaryDao = dao;
    }

    @Override
    public MemorialObituary save(MemorialObituary cond){
        CommonUser user = (CommonUser) AppContext.curUser();
        AppUser appUser = new AppUser();
        appUser.setCommonUser(user);
        List<AppUser> appUserList = appUserManager.query(appUser);

        // 保存祭奠人
        if (CollectionUtils.isNotEmpty(appUserList)) {
            appUser = appUserList.get(0);
            cond.setPersonalInfo(appUser.getPersonalInfo());
        }
        cond.setMemorialDate(new Date());

        return super.save(cond);
    }
}
