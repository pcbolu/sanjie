package com.bolu.system.ctrl;



import com.bolu.base.common.CurrentPage;
import com.bolu.base.common.JSonResponseHelper;
import com.bolu.base.common.ReqParUtil;
import com.bolu.base.common.StringUtils;
import com.bolu.system.bo.Intention;
import com.bolu.system.bo.News;
import com.bolu.system.service.IIntentionService;
import com.bolu.system.service.INewsService;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("/news")
public class NewsCtrl {

	@Autowired
	public INewsService newsService;
	@Autowired
	public IIntentionService intentionService;

	private static final Logger logger = Logger.getLogger(NewsCtrl.class);

	/***
	 * 保存新闻资讯
	 * @author: pc
	 * @param req
	 * @param rsp
	 * @param news
	 * @return
	 * @ctime 2018/12/4
	 */
	@RequestMapping(value = "saveNews")
	public void saveNews(HttpServletRequest req, HttpServletResponse rsp, News news) {
		JSONObject json = new JSONObject();
		try {
			if (StringUtils.isMeaningFul(news.getId())) {
				if (newsService.updateNews(news)) {
					json.put("status", true);
					json.put("msg", "修改成功");
				} else {
					json.put("status", false);
					json.put("msg", "修改失败！");
					logger.error("修改失败！");
				}
			} else {
				if (newsService.addNews(news)) {
					json.put("status", true);
					json.put("msg", "添加成功");
				} else {
					json.put("status", false);
					json.put("msg", "添加失败！");
					logger.error("添加失败！");
				}
			}
		} catch (Exception e) {
			json.put("status", false);
			json.put("msg", "保存失败！" + e.getMessage());
			logger.error("保存失败！" + e.getMessage());
		}
		JSonResponseHelper.JSonResponse(rsp, json);
	}

	/***
	 * 异步加载资讯内容
	 * @author: pc
	 * @param req
	 * @param rsp
	 * @return
	 * @ctime 2018/12/4
	 */
	@RequestMapping(value = "getNews")
	public void getNews(HttpServletRequest req, HttpServletResponse rsp) {
		// 查询 公司动态
		List<News> list1 = newsService.getListNewsByStatusAndTop(1, 6);
		// 行业资讯
		List<News> list2 = newsService.getListNewsByStatusAndTop(2, 6);
		// 财税知识
		List<News> list3 = newsService.getListNewsByStatusAndTop(3, 6);

		JSONObject json = new JSONObject();
		json.put("list1", list1);
		json.put("list2", list2);
		json.put("list3", list3);

		JSonResponseHelper.JSonPRspFlagMsg(req, rsp, json, 1, "");
	}

	/***
	 * 分页查询 新闻资讯信息
	 *  异步加载资讯内容 分页
	 * @author: pc
	 * @param req
	 * @param rsp
	 * @param page
	 * @param news
	 * @return
	 * @ctime 2018/12/4
	 */
	@RequestMapping(value = "getNewsPage")
	public void getNewsPage(HttpServletRequest req, HttpServletResponse rsp, CurrentPage<News> page, News news) {
		JSONObject json = new JSONObject();
		news.setStatus(1);
		CurrentPage<News> currentPage = newsService.getNewsPage(page.getPageNumber(), page.getPageSize(), news);
		json.put("currentPage", currentPage);
		JSonResponseHelper.JSonPRspFlagMsg(req, rsp, json, 1, "");
	}

	@RequestMapping(value = "getNewsById")
	/**
	 *新闻id查询新闻详细信息
	 * 异步加载资讯内容
	 * @author pc
	 * @param req
	 * @param rsp
	 * @param id
	 * @return
	 * @ctime 2018/12/4
	 */
	public void getNewsById(HttpServletRequest req, HttpServletResponse rsp, String id) {
		JSONObject json = new JSONObject();
		News news = (News) newsService.get(id);
		json.put("news", news);
		JSonResponseHelper.JSonPRspFlagMsg(req, rsp, json, 1, "");
	}



	/**
	 * 添加客户 意向信息
	 * @author pc
	 * @param req
	 * @param rsp
	 * @param intention
	 * @return
	 * @ctime 2018/12/4
	 */
	@RequestMapping(value = "addIntention")
	public void addIntention(HttpServletRequest req, HttpServletResponse rsp,Intention intention){
		JSONObject json = new JSONObject();
		try{
			if(StringUtils.isBlank(intention.getPhone())){
				json.put("status", false);
				json.put("msg", "电话号码不能为空");
				JSonResponseHelper.JSonResponse(rsp,json);
				return;
			}
			if(intentionService.addIntention(intention)){
				json.put("status", true);
				json.put("msg", "提交成功，请待客服联系您！");

			}else{
				json.put("status", false);
				json.put("msg", "提交失败！");
			}
		}
		catch (Exception e){
			json.put("status", false);
			json.put("msg", "提交失败！"+e.getMessage());
		}
		JSonResponseHelper.JSonResponse(rsp,json);
	}


	/**
	 *添加客户 意向信息
	 * @author pc
	 * @param req
	 * @param rsp
	 * @param intention
	 * @return
	 * @ctime 2018/12/4
	 */
	@RequestMapping(value = "saveIntention")
	public void saveIntention(HttpServletRequest req, HttpServletResponse rsp,Intention intention){
		JSONObject json = new JSONObject();
		try{
			if(StringUtils.isBlank(intention.getPhone())){
				json.put("status", false);
				json.put("msg", "电话号码不能为空");
				JSonResponseHelper.JSonResponse(rsp,json);
				return;
			}
			//客户名称
			String name = ReqParUtil.getReqParameterDecode(req, "name");
			//试用产品
			String options = ReqParUtil.getReqParameterDecode(req, "options");
			//地址
			String address = ReqParUtil.getReqParameterDecode(req, "address");
			//备注留言
			String remark = ReqParUtil.getReqParameterDecode(req, "remark");
			intention.setName(name);
			intention.setOptions(options);
			intention.setAddress(address);
			intention.setRemark(remark);
			if(intentionService.addIntention(intention)){
				json.put("status", true);
				json.put("msg", "提交成功，请待客服联系您！");
			}else{
				json.put("status", false);
				json.put("msg", "提交失败！");

			}
		}
		catch (Exception e){
			json.put("status", false);
			json.put("msg", "提交失败！"+e.getMessage());
		}
		JSonResponseHelper.JSonResponse(rsp,json);
	}


	/**
	 *添加客户 意向信息
	 * @author pc
	 * @param req
	 * @param rsp
	 * @param intention
	 * @return
	 * @ctime 2018/12/4
	 */
	@RequestMapping(value = "insertIntention")
	public void insertIntention(HttpServletRequest req, HttpServletResponse rsp,Intention intention){
		JSONObject json = new JSONObject();
		try{
			if(StringUtils.isBlank(intention.getPhone())){
				json.put("status", false);
				json.put("msg", "电话号码不能为空");
				JSonResponseHelper.JSonResponse(rsp,json);
				return;
			}
			//客户名称
			String name = ReqParUtil.getReqParameterDecode(req, "name");

			//试用产品
			String options = ReqParUtil.getReqParameterDecode(req, "options");
			//地址
			String address = ReqParUtil.getReqParameterDecode(req, "address");
			//备注留言
			String remark = ReqParUtil.getReqParameterDecode(req, "remark");
			intention.setName(name);
			intention.setOptions(options);
			intention.setAddress(address);
			intention.setRemark(remark);
			if(intentionService.addIntention(intention)){
				json.put("status", true);
				json.put("msg", "提交成功，请待客服联系您！");
			}else{
				json.put("status", false);
				json.put("msg", "提交失败！");
			}
		}
		catch (Exception e){
			json.put("status", false);
			json.put("msg", "提交失败！"+e.getMessage());
		}
		JSonResponseHelper.JSonPRspFlagMsg(req,rsp,json);
	}


	/***
	 *意向客户填写页面
	 * @author: pc
	 * @return
	 */
	@RequestMapping(value = "intention")

	public String intention(){
		return "pub/admin/intention";
	}



}
