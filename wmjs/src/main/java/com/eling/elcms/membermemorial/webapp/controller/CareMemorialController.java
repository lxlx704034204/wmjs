package com.eling.elcms.membermemorial.webapp.controller;

import com.eling.elcms.core.dao.hibernate.search.SearchCondition;
import com.eling.elcms.core.webapp.controller.BaseFormController;
import com.eling.elcms.core.webapp.controller.Msg;
import com.eling.elcms.core.webapp.spring.ModelFilling;
import com.eling.elcms.membermemorial.model.CareMemorial;
import com.eling.elcms.membermemorial.service.ICareMemorialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.InputStream;
import java.util.List;

/**
 * Created by Liuyp on 2017/9/11.
 */
@Controller
public class CareMemorialController extends BaseFormController{

    @Autowired
    private ICareMemorialService careMemorialService;


    @RequestMapping("/api/carememorial/search*")
    @ResponseBody
    public List<CareMemorial> sreach(@ModelAttribute SearchCondition sc, @ModelAttribute CareMemorial cond) {
        sc.setQueryCondition(cond);
        return careMemorialService.search(sc);
    }

    @RequestMapping("/api/carememorial/query")
    @ResponseBody
    public List<CareMemorial> query(@ModelAttribute CareMemorial cond){
        return careMemorialService.query(cond);
    }

    @RequestMapping("/api/carememorial/save")
    @ResponseBody
    public CareMemorial save(@ModelAttribute@ModelFilling CareMemorial cond) {
        return careMemorialService.save(cond);
    }


    @RequestMapping("/api/carememorial/cancel")
    @ResponseBody
    public Msg cancelCare(@ModelAttribute CareMemorial cond){
        careMemorialService.cancel(cond);
        return new Msg("取消关注成功");
    }


    @RequestMapping("/api/carememorial/{pkCareMemorial}/delete")
    @ResponseBody
    public Msg delete(@PathVariable Long pkCareMemorial) {
        careMemorialService.remove(pkCareMemorial);
        return new Msg("删除成功");
    }

}
