package com.bolu.system.service.impl;


import com.bolu.system.bo.CusInfoSet;
import com.bolu.system.dao.ICusInfoSetDao;
import com.bolu.system.dao.impl.CusInfoSetDao;
import com.bolu.system.service.ICusInfoSetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CusInfoSetService extends BaseService<CusInfoSet> implements ICusInfoSetService {

    private ICusInfoSetDao cusInfoSetDao;

    @Autowired
    private void setDao(CusInfoSetDao cusInfoSetDao) {
        this.cusInfoSetDao = cusInfoSetDao;
        super.setBaseDao(cusInfoSetDao);
    }

    /***
     * 根据链接秘钥查询商户配置
     * @param pwd
     * @return
     */
    public CusInfoSet getCusInfoByPwd(String pwd){
        String sql="select * from CusInfoSet cus where  urlPwd=?";
        List<CusInfoSet> cusInfoSetList= cusInfoSetDao.findList(sql,new Object[]{pwd});
        if(null!=cusInfoSetList&&cusInfoSetList.size()>0){
            return  cusInfoSetList.get(0);
        }
        return null;
    }

    /***
     * 修改日访问数量为0
     * @return
     */
    public Boolean updateDayNum(){

        String sql="update CusInfoSet set usedNum=0";
        cusInfoSetDao.executeSQL(sql,null);
        return true;
    }

}
