package com.eling.elcms.membermemorial.webapp.controller;

import com.eling.elcms.core.dao.hibernate.search.SearchCondition;
import com.eling.elcms.core.exception.AppException;
import com.eling.elcms.core.exception.BusinessException;
import com.eling.elcms.core.webapp.controller.BaseFormController;
import com.eling.elcms.core.webapp.controller.Msg;
import com.eling.elcms.core.webapp.spring.ModelFilling;
import com.eling.elcms.membermemorial.model.MemorialDetail;
import com.eling.elcms.membermemorial.service.IMemorialDetailService;
import com.eling.elcms.system.model.CommonUser;
import com.eling.elcms.system.service.IAppUserManager;
import com.eling.elcms.system.service.IMobileValidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
public class MemorialDetailController extends BaseFormController{
	@Autowired
	private IMemorialDetailService memorialDetailService;
	@Autowired
	private IMobileValidateService mobileValidateService;
	@Autowired
	private IAppUserManager appUserManager;
	
	@RequestMapping("/api/memorialdetail/search*")
	@ResponseBody
	public List<MemorialDetail> sreach(@ModelAttribute SearchCondition sc, @ModelAttribute MemorialDetail cond) {
		sc.setQueryCondition(cond);
		return memorialDetailService.search(sc);
	}

	@RequestMapping("/api/memorialdetail/query")
    @ResponseBody
    public List<MemorialDetail> query(@ModelAttribute MemorialDetail cond){
    	return memorialDetailService.query(cond); 
    }
	
	@RequestMapping("/api/memorialdetail/save")
    @ResponseBody
    public MemorialDetail save(@ModelAttribute@ModelFilling MemorialDetail cond) {
    	return memorialDetailService.save(cond);
    }

    @RequestMapping("/api/memorialdetail/savedetail")
    @ResponseBody
    public MemorialDetail saveDetail(@ModelAttribute@ModelFilling MemorialDetail cond){
        return memorialDetailService.saveDetail(cond);
    }

	@RequestMapping("/api/memorialdetail/{pkMemorialDetail}/delete")
	@ResponseBody
	public Msg delete(@PathVariable Long pkMemorialDetail) {
		memorialDetailService.remove(pkMemorialDetail);
	    return new Msg("删除成功");
	}


	@RequestMapping("/api/memorialdetail/clear")
	@ResponseBody
	public Msg clear(@ModelAttribute MemorialDetail cond) {
		memorialDetailService.removeByCond(cond);
		return new Msg("删除成功");
	}



//	/**
//	 * 查询登录人祭拜记录
//	 */
//	@RequestMapping("/api/memorialdetail/queryRecords")
//	@ResponseBody
//	public List<MemorialDetail> queryRecords(@RequestParam(value = "")){
//        MemorialDetail memorialDetail = new MemorialDetail();
//        memorialDetail.setPersonalInfo();
//    }

}
