package com.eling.elcms.membermemorial.webapp.controller;

import com.eling.elcms.core.dao.hibernate.search.SearchCondition;
import com.eling.elcms.core.webapp.controller.BaseFormController;
import com.eling.elcms.core.webapp.controller.Msg;
import com.eling.elcms.core.webapp.spring.ModelFilling;
import com.eling.elcms.membermemorial.model.MemorialObituary;
import com.eling.elcms.membermemorial.service.IMemorialObituaryService;
import com.eling.elcms.util.sensitiveword.SensitivewordFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class MemorialObituaryController extends BaseFormController{
	@Autowired
	private IMemorialObituaryService memorialObituaryService;
	
	@RequestMapping("/api/memorialobituary/search*")
	@ResponseBody
	public List<MemorialObituary> sreach(@ModelAttribute SearchCondition sc, @ModelAttribute MemorialObituary cond) {
		sc.setQueryCondition(cond);
		return memorialObituaryService.search(sc);
	}

	@RequestMapping("/api/memorialobituary/query")
    @ResponseBody
    public List<MemorialObituary> query(@ModelAttribute MemorialObituary cond){
    	return memorialObituaryService.query(cond); 
    }
	
	@RequestMapping("/api/memorialobituary/save")
    @ResponseBody
    public MemorialObituary save(@ModelAttribute@ModelFilling MemorialObituary cond) {

		SensitivewordFilter filter = new SensitivewordFilter();
		String content = filter.replaceSensitiveWord(cond.getContent(),1,"*");
		cond.setContent(content);
    	return memorialObituaryService.save(cond);
    }

	@RequestMapping("/api/memorialobituary/{pkMemorialObituary}/delete")
	@ResponseBody
	public Msg delete(@PathVariable Long pkMemorialObituary) {
		memorialObituaryService.remove(pkMemorialObituary);
	    return new Msg("删除成功");
	}

}
