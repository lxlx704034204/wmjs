package com.eling.elcms.memorialapp.webapp.controller;

import com.eling.elcms.core.exception.BusinessException;
import com.eling.elcms.core.webapp.controller.BaseFormController;
import com.eling.elcms.memorialapp.model.WebNews;
import com.eling.elcms.memorialapp.service.IWebNewsManager;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Controller
public class JidianNewsController extends BaseFormController {

	private static final Logger logger = LoggerFactory.getLogger(JidianNewsController.class);

	@Autowired
	private IWebNewsManager webNewsManager;

	@RequestMapping(value = "/webnews/{date:[\\d]+}/{pkWebNews:[\\d]+}.html", method = RequestMethod.GET)
	@ResponseBody
	public void generate(HttpServletRequest request, HttpServletResponse response, @PathVariable String date,
                         @PathVariable Long pkWebNews) {
		try {
			File tpl = new File(request.getSession().getServletContext().getRealPath("/WEB-INF/templates/news.html"));
			Date createDate = parseDate(date);
			WebNews appNews = getWebNewsByPKAndCreateDate(pkWebNews, createDate);

			Document doc = Jsoup.parse(tpl, "UTF-8");
			setContentFromModel(doc, appNews);

			Writer out = response.getWriter();
			response.setContentType("text/html; charset=UTF-8");
			out.write(doc.toString());
		} catch (IOException e) {
			logger.error("获取不到模板，出现异常，异常堆栈:{}", ExceptionUtils.getStackTrace(e));
			response.setStatus(HttpStatus.NOT_FOUND.value());
		} catch (IllegalArgumentException e) {
			logger.error("路径不正确，出现异常，异常堆栈:{}", ExceptionUtils.getStackTrace(e));
			response.setStatus(HttpStatus.NOT_FOUND.value());
		} catch (ParseException e) {
			logger.error("日期转换失败，异常堆栈:{}", ExceptionUtils.getStackTrace(e));
			response.setStatus(HttpStatus.NOT_FOUND.value());
		} catch (Exception e) {
			logger.error("出现异常，异常堆栈:{}", ExceptionUtils.getStackTrace(e));
			response.setStatus(HttpStatus.NOT_FOUND.value());
		}
	}

	/**
	 * 解析日期
	 * 
	 * @param unFormattedDate
	 * @return
	 * @throws ParseException
	 */
	private Date parseDate(String unFormattedDate) throws ParseException {
		Date formattedDate = null;
		formattedDate = DateUtils.parseDate(unFormattedDate, "yyMMddHHmm");
		return formattedDate;
	}

	/**
	 * 通过pk和发布日期获取App资讯
	 * 
	 * @param pkWebNews
	 * @param createDate
	 * @return
	 */
	private WebNews getWebNewsByPKAndCreateDate(Long pkWebNews, Date createDate) {
		Date createDateEnd = DateUtils.addMilliseconds(DateUtils.ceiling(createDate, Calendar.MINUTE), -1);
		List<WebNews> list = webNewsManager.queryByPkAndCreateDate(pkWebNews, createDate, createDateEnd);
		if (list.isEmpty()) {
			throw new BusinessException("找不到资讯！");
		} else {
			return list.get(0);
		}
	}

	/**
	 * 为Dom元素设置内容
	 * 
	 * @param doc
	 * @param appNews
	 */
	private void setContentFromModel(Document doc, WebNews appNews) {
		Element title = doc.select("#title").first();
		Element releaseDate = doc.select("#releaseDate").first();
		Element content = doc.select("#content").first();
		title.html(appNews.getTitle());
		if(null != appNews.getReleaseDate()){
			releaseDate.html(DateFormatUtils.format(appNews.getReleaseDate(), "yyyy-MM-dd HH:mm:ss"));
		}
		content.html(appNews.getContent());
	}
}
