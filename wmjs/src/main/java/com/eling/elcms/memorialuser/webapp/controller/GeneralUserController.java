package com.eling.elcms.memorialuser.webapp.controller;

import com.eling.elcms.core.exception.AppException;
import com.eling.elcms.core.exception.BusinessException;
import com.eling.elcms.core.webapp.controller.BaseFormController;
import com.eling.elcms.core.webapp.controller.Msg;
import com.eling.elcms.core.webapp.spring.ModelFilling;
import com.eling.elcms.memorialuser.model.GeneralUser;
import com.eling.elcms.memorialuser.service.IGeneralUserService;
import com.eling.elcms.privilege.service.IUserFindPwdService;
import com.eling.elcms.system.model.CommonUser;
import com.eling.elcms.system.service.IAppUserManager;
import com.eling.elcms.system.service.ICaptchaService;
import com.eling.elcms.system.service.IMobileValidateService;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by Liuyp on 2017/9/8.
 */
@Controller
public class GeneralUserController extends BaseFormController{
    @Autowired
    IGeneralUserService generalUserService;
    @Autowired
    private IMobileValidateService mobileValidateService;
    @Autowired
    private IAppUserManager appUserManager;
    @Autowired
    private ICaptchaService captchaService;
    @Autowired
    private IUserFindPwdService userFindPwdService;

    @RequestMapping("/api/generaluser/query")
    @ResponseBody
    public List<GeneralUser> query(@ModelAttribute GeneralUser cond){
        return generalUserService.query(cond);
    }

    @RequestMapping("/api/generaluser/save")
    @ResponseBody
    public GeneralUser save(@ModelAttribute GeneralUser cond) {
        return generalUserService.saveAll(cond);
    }

    @RequestMapping("/api/generaluser/{pkGeneralUser}/delete")
    @ResponseBody
    public Msg delete(@PathVariable Long pkGeneralUser) {
        generalUserService.remove(pkGeneralUser);
        return new Msg("删除成功");
    }

    /**
     *
     */
    @RequestMapping("/api/generaluser/phoneexists")
    @ResponseBody
    public Msg phoneExists(HttpServletRequest request){
        return  generalUserService.phoneExists(request);
    }

    /**
     * 返回图形验证码
     *
     * @param request
     * @throws
     */
    @RequestMapping("/api/generaluser/getpicture")
    @ResponseBody
    public Msg getCaptcha(HttpServletRequest request) {
        byte[] buffer = captchaService.genCode(request.getSession().getId(), "register");
        String base64 = Base64.encodeBase64String(buffer);
        return new Msg(base64);
    }

    /**
     * 验证图片验证码，再手机验证码
     */
    @RequestMapping("/api/generaluser/getvalidatecode")
    @ResponseBody
    public Msg sendValidateCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return generalUserService.sendValidateCode(request, response);

    }

    /**
     * 找回密码发送验证码前先验证
     */
    @RequestMapping("/api/generaluser/findpwdvalidatepic")
    @ResponseBody
    public Msg findPwdValidatePic(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return generalUserService.sendValidateCodeByFindpwd(request, response);

    }

    /**
     * 找回密码时，验证手机验证码
     */
    @RequestMapping("/api/generaluser/findpwdvalidatephone")
    @ResponseBody
    public Msg findPwdValidatePhone(@RequestParam("phone") String phone, @RequestParam("validatecode") String vcode){
        return new Msg("success");
    }

    /**
     * 修改密码
     */
    @RequestMapping("/api/generaluser/updatepwd")
    @ResponseBody
    public Msg updatePwd(@RequestParam("phone") String phone, @RequestParam("password") String password){
        CommonUser cu=new CommonUser();
        try {
            cu = appUserManager.forget(phone, password,phone);
        }catch (AppException e) {
            return new Msg("UpdateError");//修改失败
        }
        return new Msg("UpdateSuccess");//修改成功！
    }

    /**
     * 注册
     */
    @RequestMapping("/api/generaluser/submitmemorial")
    @ResponseBody
    public Msg submitMemorial(@RequestParam("source") String source,
                              @RequestParam("phone") String phone, @RequestParam("validatecode") String vcode,
                              @RequestParam("password") String password, @RequestParam(value="pkOrg",required=false) Long pkOrg,
                              @RequestParam(value="code",required=false)String code, HttpServletResponse response){
        try {
            CommonUser cu=new CommonUser();
            if (source.equals("forget")){
                try {
                    cu = appUserManager.forget(phone, password,code);
                }catch (AppException e) {
                    return new Msg("UpdateError");//修改失败
                }
                return new Msg("UpdateSuccess");//修改成功！
            }
            generalUserService.submitMemorial(phone, password, cu, pkOrg,code);
        }catch (AppException e) {
            return new Msg("RegisterError");//注册失败!
        }catch (BusinessException e){
            return new Msg("AccountAlreadyExists");//该用户已被注册！
        }
        return new Msg("RegisterSuccess");//注册成功！
    }


}
