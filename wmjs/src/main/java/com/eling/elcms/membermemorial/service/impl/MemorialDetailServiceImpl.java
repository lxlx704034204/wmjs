package com.eling.elcms.membermemorial.service.impl;

import com.eling.elcms.basedoc.model.PersonalInfo;
import com.eling.elcms.basedoc.service.IPersonalInfoManager;
import com.eling.elcms.community.service.IOrganizationManager;
import com.eling.elcms.core.AppContext;
import com.eling.elcms.core.exception.BusinessException;
import com.eling.elcms.core.service.impl.GenericManagerImpl;
import com.eling.elcms.core.webapp.controller.Msg;
import com.eling.elcms.membermemorial.dao.IMemorialDetailDao;
import com.eling.elcms.membermemorial.model.MemberMemorial;
import com.eling.elcms.membermemorial.model.MemorialDetail;
import com.eling.elcms.membermemorial.service.IMemberMemorialService;
import com.eling.elcms.membermemorial.service.IMemorialDetailService;
import com.eling.elcms.privilege.service.IRbacService;
import com.eling.elcms.system.model.AppUser;
import com.eling.elcms.system.model.CommonUser;
import com.eling.elcms.system.service.IAppUserManager;
import com.eling.elcms.system.service.ICommonUserManager;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class MemorialDetailServiceImpl extends GenericManagerImpl<MemorialDetail, Long> implements IMemorialDetailService {

    IMemorialDetailDao memorialDetailDao;
    @Autowired
    private IAppUserManager appUserManager;
    @Autowired
    private IMemberMemorialService memberMemorialService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private IPersonalInfoManager personalInfoManager;
    @Autowired
    private IOrganizationManager organizationManager;
    @Autowired
    public IRbacService rbacService;
    @Autowired
    private ICommonUserManager commonUserManager;

    @Autowired
    public void setDao(IMemorialDetailDao dao) {
        this.dao = dao;
        this.memorialDetailDao = dao;
    }

    @Override
    public MemorialDetail save(MemorialDetail cond) {
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

        // 回写各类数量
        MemberMemorial memberMemorial = memberMemorialService.get(cond.getMemberMemorial().getPkMemberMemorial());
        if (cond.getMemorialType().equals(MemorialDetail.MemorialType.Clifford)) {
            memberMemorial.setCliffordCounts(memberMemorial.getCliffordCounts() + cond.getCounts());
        } else if (cond.getMemorialType().equals(MemorialDetail.MemorialType.Flower)) {
            memberMemorial.setFlowerCounts(memberMemorial.getFlowerCounts() + cond.getCounts());
        } else {
            memberMemorial.setWorshipCounts(memberMemorial.getWorshipCounts() + cond.getCounts());
        }
        cond.setMemberMemorial(memberMemorial);

        return super.save(cond);
    }

    @Override
    public MemorialDetail saveDetail(MemorialDetail cond) {
        //祭拜人
        CommonUser commonUser = (CommonUser) AppContext.curUser();
        cond.setCommonUser(commonUser);
        //被祭拜人
        MemberMemorial memberMemorial = memberMemorialService.get(cond.getMemberMemorial().getPkMemberMemorial());

        cond.setMemorialDate(new Date());
        if (cond.getMemorialType().equals(MemorialDetail.MemorialType.Clifford)) {
            memberMemorial.setCliffordCounts(memberMemorial.getCliffordCounts() + cond.getCounts());
        } else if (cond.getMemorialType().equals(MemorialDetail.MemorialType.Flower)) {
            memberMemorial.setFlowerCounts(memberMemorial.getFlowerCounts() + cond.getCounts());
        } else {
            memberMemorial.setWorshipCounts(memberMemorial.getWorshipCounts() + cond.getCounts());
        }
        memberMemorial = memberMemorialService.save(memberMemorial);
        cond.setMemberMemorial(memberMemorial);
        return super.save(cond);
    }
}
