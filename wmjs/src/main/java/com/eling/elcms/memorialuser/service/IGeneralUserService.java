package com.eling.elcms.memorialuser.service;

import com.eling.elcms.core.service.IGenericManager;
import com.eling.elcms.core.webapp.controller.Msg;
import com.eling.elcms.memorialuser.model.GeneralUser;
import com.eling.elcms.system.model.CommonUser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Liuyp on 2017/9/8.
 */
public interface IGeneralUserService extends IGenericManager<GeneralUser, Long> {
    void submitMemorial(String phone, String password, CommonUser cu, Long pkOrg,String code);

    Msg sendValidateCode(HttpServletRequest request, HttpServletResponse response) throws Exception;

    Msg phoneExists(HttpServletRequest request);

    Msg sendValidateCodeByFindpwd(HttpServletRequest request, HttpServletResponse response) throws Exception;

    GeneralUser saveAll(GeneralUser generalUser);

}
