package com.bolu.system.ctrl;

import com.bolu.base.common.JSonResponseHelper;
import com.bolu.base.common.StringUtils;
import com.bolu.system.bo.CusInfo;
import com.bolu.system.bo.CusInfoSet;
import com.bolu.system.service.ICusInfoService;
import com.bolu.system.service.ICusInfoSetService;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("/cusInfo")
public class CusInfoCtrl {
    private static final Logger logger = Logger.getLogger(CusInfoCtrl.class);
    @Autowired
    private ICusInfoService cusInfoService;
    @Autowired
    private ICusInfoSetService cusInfoSetService;


    @ResponseBody
    @RequestMapping("/saveCusInfo")
    public String saveCusInfo(CusInfo cusInfo) {
        JSONObject json = new JSONObject();
        try {
            cusInfoService.saveCusInfo(cusInfo);
            json.put("code", "1");
            json.put("msg", "保存成功！");
        } catch (Exception e) {
            json.put("code", "4004");
            json.put("msg", "保存失败" + e.getMessage());
        }
        return json.toString();
    }


    @ResponseBody
    @RequestMapping("/saveCusInfoSet")
    public String saveCusInfoSet(CusInfoSet cusInfoSet) {
        JSONObject json = new JSONObject();
        try {
            CusInfoSet old=cusInfoSetService.get(cusInfoSet.getId());
            if(null==old){
                String urlpwd=StringUtils.getPwd();
                String inviteCode=StringUtils.getNonceSixStr();
                cusInfoSet.setUrlPwd(urlpwd);
                cusInfoSet.setDayNum(0);
                cusInfoSet.setWarnimgNum(0);
                cusInfoSet.setUsedNum(0);
                cusInfoSet.setInviteCode(inviteCode);
                cusInfoSet.setInviteSta(0);
                cusInfoSetService.add(cusInfoSet);
            }else {
                cusInfoSetService.edit(cusInfoSet);
            }
            json.put("code", "1");
            json.put("msg", "保存成功！");
        } catch (Exception e) {
            json.put("code", "4004");
            json.put("msg", "保存失败" + e.getMessage());
        }
        return json.toString();
    }

    @ResponseBody
    @RequestMapping("/updateUrlPwd")
    public String updateUrlPwd(CusInfoSet cusInfoSet) {
        JSONObject json = new JSONObject();
        try {
            String urlpwd=StringUtils.getPwd();
            cusInfoSet.setUrlPwd(urlpwd);
            cusInfoSetService.save(cusInfoSet);
            json.put("code", "1");
            json.put("msg", "保存成功！");
            json.put("urlpwd",urlpwd);
        } catch (Exception e) {
            json.put("code", "4004");
            json.put("msg", "保存失败" + e.getMessage());
        }
        return json.toString();
    }


    /**
     * 验证 商户编码唯一
     *
     * @param rsp
     * @param code
     * @return
     * @author pc
     * @ctime 2018/12/4
     */
    @RequestMapping(value = "getCusInfoByCode")
    public void getCusInfoByCode(HttpServletResponse rsp, String code) {
        JSONObject json = new JSONObject();
        try {
            List<CusInfo> list = cusInfoService.getCusInfoByCode(code);
            if (null != list && list.size() > 0) {
                json.put("valid", false);
                json.put("msg", "编码已存在");
                logger.info("编码已存在");
            } else {
                json.put("valid", true);
                json.put("msg", "已通过");
            }
        } catch (Exception e) {
            json.put("valid", false);
            json.put("msg", e.getMessage());
            logger.error(e.getMessage());
        }
        JSonResponseHelper.JSonResponse(rsp, json);
    }



}
