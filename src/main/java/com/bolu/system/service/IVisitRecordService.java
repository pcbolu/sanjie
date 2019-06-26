package com.bolu.system.service;

import com.bolu.base.common.CurrentPage;
import com.bolu.system.bo.CusInfoSet;
import com.bolu.system.bo.VisitRecord;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

public interface IVisitRecordService extends IBaseService<VisitRecord> {


    /***
     * 分分页查询访问记录
     * @param nowPage
     * @param pageSize
     * @param visitRecord
     * @return
     */
    public CurrentPage<VisitRecord> getVisitRecordPage(int nowPage, int pageSize, VisitRecord visitRecord);


    /***
     * 查看查询访问记录
     * @param visitRecord
     * @return
     */
    public VisitRecord getvisitRecord(VisitRecord visitRecord);

    /***
     * 根据 商户号查询 最近 day 访问统计
     *
     * @author pc
     * @param day 最近多少天
     * @param cusid 商户号
     * @return
     * @ctime 2018/12/18
     */
    public List<Map<String,Object>> getVisitRecordByDay(Integer day, String cusid);

    /***
     * 访问链接是添加、更新记录
     * @param visitRecord
     * @param cusInfoSet
     * @return
     */
    @Transactional
    public Boolean  saveVisitRecord(VisitRecord visitRecord,CusInfoSet cusInfoSet);
}
