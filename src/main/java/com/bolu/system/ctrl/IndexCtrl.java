package com.bolu.system.ctrl;


import com.bolu.base.common.JSonResponseHelper;
import com.bolu.system.bo.User;
import com.bolu.system.service.IVisitLinkService;
import com.bolu.system.service.IVisitRecordService;
import com.bolu.system.util.PubDataManager;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @author pc
 * @date 2018/12/20
 */
@Controller
@RequestMapping("/index")
public class IndexCtrl {
    private static final Logger logger = Logger.getLogger(IndexCtrl.class);

    @Autowired
    private IVisitRecordService visitRecordService;

    /***
     * 首页数据
     * @param req
     * @param rsp
     * @return
     */
    @ResponseBody
    @RequestMapping("/getIndex")
    public String getIndex(HttpServletRequest req, HttpServletResponse rsp) {
        JSONObject json = new JSONObject();
        try {
            User curruser = PubDataManager.getCurrUser(req);
            List<Map<String, Object>> data = visitRecordService.getVisitRecordByDay(10, curruser.getCusid());
            json.put("code","1");
            json.put("msg","ok");
            json.put("data",data);
        } catch (Exception e) {
            json.put("code", "4001");
            json.put("msg", "出现异常" + e.getMessage());
            json.put("data", "");
        }
        return json.toString();
    }
}
