package com.bolu.system.service;



import com.bolu.base.common.CurrentPage;
import com.bolu.system.bo.Intention;

import java.util.List;

public interface IIntentionService extends IBaseService<Intention> {


    /*****
     * 添加 客户意向记录
     * @param intention
     * @return
     */
    public Boolean addIntention(Intention intention);

    /****
     * 根据客户电话号码查询
     * @param phone
     * @return
     */
    public List<Intention> getIntentionByPhone(String phone);

    /***
     * 分页查询客户意向数据信息
     * @param nowPage
     * @param pageSize
     * @param intention
     * @return
     */
    public CurrentPage<Intention> getIntentionPage(int nowPage, int pageSize, Intention intention);


}
