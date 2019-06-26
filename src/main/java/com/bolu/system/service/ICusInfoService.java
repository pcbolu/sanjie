package com.bolu.system.service;


import com.bolu.base.common.CurrentPage;
import com.bolu.system.bo.CusInfo;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ICusInfoService extends IBaseService<CusInfo>  {


    @Transactional
    public Boolean saveCusInfo(CusInfo cusInfo);

    /**
     * 分页查询
     * @author pc
     * @return
     * @ctime 2018/12/4
     */
    public CurrentPage<CusInfo> getCusInfoPage(int nowPage, int pageSize, CusInfo cusInfo);


    /***
     * 根据商户类型查询商户信息
     * @param cusType
     * @return
     */
    public List<CusInfo> getCusinfoByCustype(String cusType);

    /***
     * 根据编码查询
     * @param code
     * @return
     */
    public List<CusInfo> getCusInfoByCode(String code);
}
