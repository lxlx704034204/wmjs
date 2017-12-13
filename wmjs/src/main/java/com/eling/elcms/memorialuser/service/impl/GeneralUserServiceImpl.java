package com.eling.elcms.memorialuser.service.impl;

import com.eling.elcms.basedoc.model.PersonalInfo;
import com.eling.elcms.basedoc.service.IPersonalInfoManager;
import com.eling.elcms.community.service.IOrganizationManager;
import com.eling.elcms.core.exception.BusinessException;
import com.eling.elcms.core.service.IGenericManager;
import com.eling.elcms.core.service.impl.GenericManagerImpl;
import com.eling.elcms.core.webapp.controller.Msg;
import com.eling.elcms.membermemorial.dao.ICareMemorialDao;
import com.eling.elcms.memorialuser.dao.IGeneralUserDao;
import com.eling.elcms.memorialuser.model.GeneralUser;
import com.eling.elcms.memorialuser.service.IGeneralUserService;
import com.eling.elcms.privilege.model.Role;
import com.eling.elcms.privilege.model.User;
import com.eling.elcms.privilege.service.IRbacService;
import com.eling.elcms.privilege.service.IUserManager;
import com.eling.elcms.system.model.CommonUser;
import com.eling.elcms.system.service.IAppUserManager;
import com.eling.elcms.system.service.ICaptchaService;
import com.eling.elcms.system.service.ICommonUserManager;
import com.eling.elcms.system.service.IMobileValidateService;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * Created by Liuyp on 2017/9/8.
 */
@Service
public class GeneralUserServiceImpl extends GenericManagerImpl<GeneralUser, Long> implements IGeneralUserService {

    IGeneralUserDao generalUserDao;
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
    private IAppUserManager appUserManager;
    @Autowired
    private ICaptchaService captchaService;
    @Autowired
    private IUserManager userManager;
    @Autowired
    private IMobileValidateService mobileValidateService;


    @Autowired
    public void setDao(IGeneralUserDao dao) {
        this.dao = dao;
        this.generalUserDao = dao;
    }


    @Override
    public void submitMemorial(String phone, String password, CommonUser cu, Long pkOrg, String code) {
        PersonalInfo pi=new PersonalInfo();
        pi.setMobilePhone(phone);
//        if(personalInfoManager.query(pi) == null && !personalInfoManager.query(pi).isEmpty()){
//            pi = personalInfoManager.query(pi).get(0);
//        }
        cu.setCode(phone);
        cu.setUniqueRange(pkOrg.toString());
        List<User> users = userManager.query(cu);
        if(users != null&&!users.isEmpty()){
            throw new BusinessException("该账户已被注册");
        }
        //if(personalInfoManager.query(pi) == null && !personalInfoManager.query(pi).isEmpty()){
            cu.setPassword(passwordEncoder.encode(password));
            if(code != null && code != ""){
                cu.setCode(code);
                cu.setName(code);
                pi.setName(code);
            }else {
                cu.setCode(phone);
                cu.setName(phone);
                pi.setName(phone);
            }
            pi = personalInfoManager.save(pi);
            cu.setPhone1(phone);
            cu.setCreateTime(new Date());
            cu.setStatus(CommonUser.Status.Setting);
            cu.setOrganization(organizationManager.get(pkOrg));
            cu.setVersion(0);
            List<Long> orgPks = new ArrayList<>();
            orgPks.add(pkOrg);
            List<Long> uniqueRanges = appUserManager.getUniqueRange(orgPks);
            if(uniqueRanges != null && uniqueRanges.size() > 0){
                cu.setUniqueRange(String.valueOf(uniqueRanges.get(0)));
            }
            cu = commonUserManager.save(cu);
            GeneralUser generalUser = new GeneralUser();
            generalUser.setCommonUser(cu);
            generalUser.setPersonalInfo(pi);
            generalUser.setVersion(0);
            generalUserDao.save(generalUser);
            List<Long> organizationPks = new ArrayList<>();
            organizationPks.add(pkOrg);
            rbacService.assignToUser(cu.getPkUser(), "organization", organizationPks);
            List<Long> rolePks = new ArrayList<>();
            rolePks.add(90100L);
            rbacService.assignToUser(cu.getPkUser(), "role",rolePks);
//        }else {
//              throw new BusinessException("该账户已被注册");
//        }
    }

    @Override
    public Msg sendValidateCode(HttpServletRequest request, HttpServletResponse response) throws Exception {

        //发送验证码之前先校验验证码
        String register = request.getParameter("register");
        if(!StringUtils.isEmpty(register)){
            String err = captchaService.validate(request.getSession().getId(), register, "register");
            if (err != null) {
                return new Msg(err);
            }
        }
        //String username = request.getParameter("username");
        String phone = request.getParameter("phone");
        String csid = request.getParameter("csid");
        String pkOrganization= request.getParameter("pkOrganization");

        User user = new User();
        List<User> list = new ArrayList<>();
        user.setCode(phone);
        user.setUniqueRange(pkOrganization);
        list=userManager.query(user);
        if(list != null && !list.isEmpty()){
            return new Msg("AccountAlreadyExists");
        }
        String vcode = mobileValidateService.getValidateCode(phone);
        String message = "验证码：" + vcode + "; 您正在申请注册,请在2分钟内进行验证。";
        mobileValidateService.sendOnce(phone, message,StringUtils.isNotBlank(csid)?Integer.valueOf(csid):null);
        return new Msg("success");
    }

    @Override
    public Msg sendValidateCodeByFindpwd(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //发送验证码之前先校验验证码
        String updatePwd = request.getParameter("register");
        if(!StringUtils.isEmpty(updatePwd)){
            String err = captchaService.validate(request.getSession().getId(), updatePwd, "register");
            if (err != null) {
                return new Msg(err);
            }
        }
        String phone = request.getParameter("phone");
        String csid = request.getParameter("csid");
        String vcode = mobileValidateService.getValidateCode(phone);
        String message = "验证码：" + vcode + "; 您正在申请修改密码,请在2分钟内进行验证。";
        mobileValidateService.sendOnce(phone, message,StringUtils.isNotBlank(csid)?Integer.valueOf(csid):null);
        return new Msg("success");
    }

    @Override
    public GeneralUser saveAll(GeneralUser generalUser) {

        try{

            CommonUser commonUser = generalUser.getCommonUser();
            PersonalInfo personalInfo = generalUser.getPersonalInfo();

            generalUser = this.get(generalUser.getPkGeneralUser());

            CommonUser tmpCuser = commonUserManager.get(generalUser.getCommonUser().getPkUser());
            tmpCuser.setName(commonUser.getName());
            tmpCuser.setPhone1(commonUser.getPhone1());

            PersonalInfo pi = personalInfoManager.get(generalUser.getPersonalInfo().getPkPersonalInfo());
            pi.setName(personalInfo.getName());
            pi.setSex(personalInfo.getSex());
            pi.setHouseRegisterDetail(personalInfo.getHouseRegisterDetail());

            generalUser.setCommonUser(tmpCuser);
            generalUser.setPersonalInfo(personalInfo);

            return this.save(generalUser);

        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Msg phoneExists(HttpServletRequest request) {

        String pkOrganization = request.getParameter("pkOrganization");
        String phone = request.getParameter("phone");
        User user = new User();
        user.setCode(phone);
        user.setUniqueRange(pkOrganization);
        List<User> users = userManager.query(user);
        if(users != null && !users.isEmpty()){
            return new Msg("error");
        }
        return new Msg("success");
    }

}
