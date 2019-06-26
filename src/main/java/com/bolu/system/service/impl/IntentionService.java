package com.bolu.system.service.impl;


import com.bolu.base.common.CurrentPage;
import com.bolu.base.common.StringUtils;
import com.bolu.system.bo.Intention;
import com.bolu.system.dao.IIntentionDao;
import com.bolu.system.service.IIntentionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class IntentionService extends BaseService<Intention> implements IIntentionService {

    private IIntentionDao intentionDao;

    @Autowired
    private void setDao(IIntentionDao intentionDao) {
        this.intentionDao = intentionDao;
        super.setBaseDao(intentionDao);
    }

    /*****
     * 添加 客户意向记录
     * @author pc
     * @param intention
     * @return
     * @ctime 2018/12/4
     */
    public Boolean addIntention(Intention intention){
        Map<String,Object> map= new HashMap<String,Object>();
        if(StringUtils.isBlank(intention.getId())){
            intention.setId(StringUtils.uuid());
        }

        if(StringUtils.isBlank(intention.getName())){
            throw  new RuntimeException("名称不能为空！");
        }
        if(StringUtils.isBlank(intention.getPhone())){
            throw  new RuntimeException("电话不能为空！");
        }

        if(intention.getType()==1){
            if(StringUtils.isBlank(intention.getIndustry())){
                throw  new RuntimeException("请选择行业！");
            }
            if(StringUtils.isBlank(intention.getOptions())){
                throw  new RuntimeException("请选择感兴趣的 合作套餐！");
            }
        }

        if(intention.getType()==2){
            if(StringUtils.isBlank(intention.getOptions())){
                throw  new RuntimeException("请选择试用产品！");
            }
        }

        map.put("id",intention.getId());
        map.put("name",intention.getName());
        map.put("phone",intention.getPhone());
        map.put("industry",intention.getIndustry());
        map.put("options",intention.getOptions());
        map.put("email",intention.getEmail());
        map.put("address",intention.getAddress());
        map.put("remark",intention.getRemark());
        map.put("type",intention.getType());

        return intentionDao.add(map);

    }


    /****
     * 根据客户电话号码查询
     * @author pc
     * @param phone
     * @return
     * @ctime 2018/12/4
     */
    public List<Intention> getIntentionByPhone(String phone){
        String sql="select * from intention  where 1=1 where phone=?";
        return intentionDao.findList(sql,new Object[]{phone});
    }


    /***
     * 分页查询客户意向数据信息
     * @author pc
     * @param nowPage
     * @param pageSize
     * @param intention
     * @return
     * @ctime 2018/12/4
     */
    public CurrentPage<Intention> getIntentionPage(int nowPage, int pageSize, Intention intention){
        List<Object> params= new ArrayList<Object>();
        String strQ ="select inten.* from intention inten where 1=1";
        String strC="select count(inten.id) from intention inten where 1=1";
        if(StringUtils.isMeaningFul(intention.getName())){
            strQ+=" and inten.name like '%"+intention.getName()+"%'";
            strC+=" and inten.name like '%"+intention.getName()+"%'";
        }
        if(null!=intention.getType()&&intention.getType()!=0){
            strQ+=" and inten.type=?";
            strC+=" and inten.type=?";
            params.add(intention.getType());
        }
        strQ+=" order by type ";

        CurrentPage<Intention> currentPage = intentionDao.getPage(strC, strQ, params.toArray(), nowPage, pageSize);
        return currentPage;
    }
}
