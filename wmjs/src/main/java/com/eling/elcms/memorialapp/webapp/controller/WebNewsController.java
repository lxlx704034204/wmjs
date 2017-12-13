package com.eling.elcms.memorialapp.webapp.controller;


import com.eling.elcms.core.dao.hibernate.search.SearchCondition;
import com.eling.elcms.core.util.PropertyUtils;
import com.eling.elcms.core.webapp.controller.BaseFormController;
import com.eling.elcms.core.webapp.controller.Msg;
import com.eling.elcms.core.webapp.spring.ModelFilling;
import com.eling.elcms.memorialapp.model.WebNews;
import com.eling.elcms.memorialapp.model.view.WebNewsView;
import com.eling.elcms.memorialapp.service.IWebNewsManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/api/webnews")
public class WebNewsController extends BaseFormController{
	@Autowired
	private IWebNewsManager webNewsManager;
	
	@RequestMapping("/search*")
	@ResponseBody
	public List<WebNews> search(@ModelAttribute SearchCondition sc, @ModelAttribute WebNews cond) {
		sc.setQueryCondition(cond);
		return webNewsManager.search(sc);
	}
	
	@RequestMapping("/query")
    @ResponseBody
    public List<WebNews> query(@ModelAttribute WebNews cond){
        List<WebNews> appNewsList = webNewsManager.query(cond);
        WebNews appNews = new WebNews();
        if(cond.getClassify() != null){
            appNews.setClassify(cond.getClassify());
        }
        List<WebNews> newsList = webNewsManager.query(appNews);
        if(appNewsList != null && !appNewsList.isEmpty() && newsList != null && !newsList.isEmpty()){
            for (WebNews news:appNewsList) {
                PropertyUtils.setProperty(news,"countResults",newsList.size());
            }
        }
        return appNewsList;
    }
	
	@RequestMapping("/querywithoutcontent")
    @ResponseBody
    public List<WebNewsView> queryWithoutContent(@ModelAttribute WebNews cond){
    	return webNewsManager.queryWithoutContent(cond);
    }
	
	@RequestMapping("/querytopwithoutcontent")
    @ResponseBody
    public List<WebNewsView> queryTopWithoutContent(){
    	return webNewsManager.queryTopWithoutContent();
    }
	
	@RequestMapping("/save")
    @ResponseBody
    public WebNews save(@ModelAttribute@ModelFilling WebNews WebNews) {
		return webNewsManager.save(WebNews);
    }
	
	@RequestMapping("/savewithimg")
    @ResponseBody
    public WebNews saveWithImg(@ModelAttribute@ModelFilling WebNews WebNews) {
		return webNewsManager.saveWithImg(WebNews);
    }
	
	@RequestMapping("/{pkWebNews}/delete")
	@ResponseBody
	public Msg delete(@PathVariable Long pkWebNews) {
		webNewsManager.remove(pkWebNews);
	    return new Msg("删除成功");
	}
	
	@RequestMapping("/{pkWebNews}/close")
	@ResponseBody
	public WebNews close(@PathVariable Long pkWebNews) {
		return webNewsManager.close(pkWebNews);
	}
	
	@RequestMapping("/{pkWebNews}/open")
	@ResponseBody
	public WebNews open(@PathVariable Long pkWebNews) {
		return webNewsManager.open(pkWebNews);
	}
	
	@RequestMapping("/{pkWebNews}/sharenumadd1")
	@ResponseBody
	public Boolean shareNumAdd1(@PathVariable Long pkWebNews) {
		return webNewsManager.shareNumAdd1(pkWebNews);
	}
	
	@RequestMapping("/{pkWebNews}/likenumadd1")
	@ResponseBody
	public Boolean likeNumAdd1(@PathVariable Long pkWebNews) {
		return webNewsManager.likeNumAdd1(pkWebNews);
	}
	
	@RequestMapping("/{pkWebNews}/release")
	@ResponseBody
	public WebNews release(@PathVariable Long pkWebNews) {
		return webNewsManager.release(pkWebNews);
	}

	@RequestMapping("/{pkWebNews}/querycontent")
	@ResponseBody
	public WebNews queryContent(@PathVariable Long pkWebNews){
    	return webNewsManager.get(pkWebNews);
	}
}
