package com.eling.elcms.membermemorial.webapp.controller;

import com.eling.elcms.basedoc.model.PersonalInfo;
import com.eling.elcms.core.dao.hibernate.search.SearchCondition;
import com.eling.elcms.core.webapp.controller.BaseFormController;
import com.eling.elcms.core.webapp.controller.Msg;
import com.eling.elcms.core.webapp.spring.ModelFilling;
import com.eling.elcms.member.model.Member;
import com.eling.elcms.membermemorial.model.MemberMemorial;
import com.eling.elcms.membermemorial.model.view.MemMessageView;
import com.eling.elcms.membermemorial.service.IMemberMemorialService;
import com.eling.elcms.util.sensitiveword.SensitivewordFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
public class MemberMemorialController extends BaseFormController{
	@Autowired
	private IMemberMemorialService memberMemorialService;
	
	@RequestMapping("/api/membermemorial/search*")
	@ResponseBody
	public List<MemberMemorial> sreach(@ModelAttribute SearchCondition sc, @ModelAttribute MemberMemorial cond) {
		sc.setQueryCondition(cond);
		return memberMemorialService.search(sc);
	}

	@RequestMapping("/api/membermemorial/query")
    @ResponseBody
    public List<MemberMemorial> query(@ModelAttribute MemberMemorial cond){
    	return memberMemorialService.query(cond); 
    }
	
	@RequestMapping("/api/membermemorial/save")
    @ResponseBody
    public MemberMemorial save(@ModelAttribute@ModelFilling MemberMemorial cond) {
        if(cond.getPkMemberMemorial() == null){
            cond.setCliffordCounts(0);
            cond.setWorshipCounts(0);
            cond.setFlowerCounts(0);
        }

        SensitivewordFilter filter = new SensitivewordFilter();
        cond.setIntroduction(filter.replaceSensitiveWord(cond.getIntroduction(),1,"*"));
        cond.setIntroduction(filter.replaceSensitiveWord(cond.getMemorialName(),1,"*"));
        if(cond.getPersonalInfo() != null && cond.getPersonalInfo().getName()!=null){
            cond.getPersonalInfo().setName(filter.replaceSensitiveWord(cond.getPersonalInfo().getName(),1,"*"));
        }

    	return memberMemorialService.save(cond);
    }

	@RequestMapping("/api/membermemorial/{pkMemberMemorial}/delete")
	@ResponseBody
	public Msg delete(@PathVariable Long pkMemberMemorial) {
		memberMemorialService.remove(pkMemberMemorial);
	    return new Msg("删除成功");
	}

    /**
     *  获取家人档案列表
     * @param member
     * @return
     * @author zhoulin
     */
    @RequestMapping("api/membermemorial/querymember")
    @ResponseBody
    public List<MemMessageView> queryMember(@ModelAttribute Member member,
                                            @RequestParam(value="pkOrg",required=false)Long pkOrg) {
        return memberMemorialService.queryMember(member,pkOrg);
    }

    /**
     *  保存祭奠档案
     * @param file
     * @param cond
     * @return
     * @author zhoulin
     */
    @RequestMapping("/api/membermemorial/savememorial")
    @ResponseBody
    public MemberMemorial saveMemorial(@RequestParam(value="file",required=false) CommonsMultipartFile file,
                                       @ModelAttribute@ModelFilling MemberMemorial cond,
                                       @ModelAttribute PersonalInfo personalInfo) throws IOException {
        return memberMemorialService.saveMemorial(file, cond, personalInfo);
    }

    /**
     *  保存祭奠档案(廊坊平台)
     * @param file
     * @param cond
     * @return
     */
    @RequestMapping("/api/membermemorial/savemembermemorial")
    @ResponseBody
    public MemberMemorial saveMemberMemorial(@RequestParam(value="file",required=false) CommonsMultipartFile file,
                                       @ModelAttribute@ModelFilling MemberMemorial cond,
                                       @ModelAttribute PersonalInfo personalInfo) throws IOException {

        return memberMemorialService.saveMemberMemorial(file, cond,personalInfo);
    }

}
