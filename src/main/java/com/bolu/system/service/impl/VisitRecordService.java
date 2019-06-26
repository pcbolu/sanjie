package com.bolu.system.service.impl;


import com.bolu.base.common.CurrentPage;
import com.bolu.base.common.DateHelper;
import com.bolu.base.common.StringUtils;
import com.bolu.system.bo.CusInfoSet;
import com.bolu.system.bo.VisitRecord;
import com.bolu.system.dao.IVisitRecordDao;
import com.bolu.system.dao.impl.VisitRecordDao;
import com.bolu.system.service.ICusInfoSetService;
import com.bolu.system.service.IVisitRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class VisitRecordService extends BaseService<VisitRecord> implements IVisitRecordService {

    @Autowired
    private ICusInfoSetService cusInfoSetService;


    private IVisitRecordDao visitRecordDao;


    @Autowired
    private void setDao(VisitRecordDao visitRecordDao) {
        this.visitRecordDao = visitRecordDao;
        super.setBaseDao(visitRecordDao);
    }


    /***
     * 分分页查询访问记录
     * @param nowPage
     * @param pageSize
     * @param visitRecord
     * @return
     */
    public CurrentPage<VisitRecord>  getVisitRecordPage(int nowPage, int pageSize, VisitRecord visitRecord){
        List<Object> params = new ArrayList<Object>();

        String strQ = "select v.*,left(v.url, 80) url2 from VisitRecord v "
                + " where 1=1 and  binary taowords regexp '[A-Z]' ";
        String strC = "select count(v.id) from VisitRecord v "
                + " where 1=1 and  binary taowords regexp '[A-Z]' ";
        if(StringUtils.isMeaningFul(visitRecord.getCusid())){
            strQ+=" and v.cusid=?";
            strC+=" and v.cusid=?";
            params.add(visitRecord.getCusid());
        }
        strQ+="order by v.visitTime desc";

        CurrentPage<VisitRecord> currentPage = visitRecordDao.getPage(strC, strQ, params.toArray(), nowPage, pageSize);
        return currentPage;
    }

    /***
     * 查看查询访问记录
     * @param visitRecord
     * @return
     */
    public VisitRecord getvisitRecord(VisitRecord  visitRecord){

        List<Object> params = new ArrayList<Object>();
        String sql="select v.* from VisitRecord  v where 1=1";

        if(StringUtils.isMeaningFul(visitRecord.getCusid())){
            sql+=" and v.cusid=?";
            params.add(visitRecord.getCusid());
        }
        if(StringUtils.isMeaningFul(visitRecord.getUrl())){
            sql+=" and v.url=?";
            params.add(visitRecord.getUrl());
        }
        if(StringUtils.isMeaningFul(visitRecord.getTaowords())){
            sql+=" and v.taowords=?";
            params.add(visitRecord.getTaowords());
        }
        if(StringUtils.isMeaningFul(visitRecord.getImageUrl())){
            sql+=" and v.imageUrl=?";
            params.add(visitRecord.getImageUrl());
        }
        List<VisitRecord> visitRecordList=visitRecordDao.findList(sql,params.toArray());
        if(null!=visitRecordList&&visitRecordList.size()>0){
            return  visitRecordList.get(0);
        }
        return null;
    }


    /***
     * 根据 商户号查询 最近 day 访问统计
     *
     * @author pc
     * @param day 最近多少天
     * @param cusid 商户号
     * @return
     * @ctime 2018/12/18
     */
    public List<Map<String,Object>> getVisitRecordByDay(Integer day, String cusid){
        List<Map<String,Object>>  mapList= new ArrayList<Map<String,Object>>();
        for (Integer i = day; i>=0; i--) {
            List<Object> params= new ArrayList<Object>();
            String  data= DateHelper.getNowAddDate(-i);
            String stime=data+" 00:00:00";
            String etime=data+" 23:59:59";
            String sql="select sum(number) countnum  from VisitRecord where  cusid=? and visitTime>? and visitTime<? ";
            params.add(cusid);
            params.add(stime);
            params.add(etime);
            List<VisitRecord> visitRecordList=visitRecordDao.findList(sql,params.toArray());
            if(null!=visitRecordList&&visitRecordList.size()>0){
                VisitRecord kpinfo= visitRecordList.get(0);
                Map<String,Object> map= new HashMap<String,Object>();
                map.put("time",data);
                map.put("count",kpinfo.getCountnum()==null?0:kpinfo.getCountnum());
                mapList.add(map);
            }
        }
        return mapList;
    }


    /***
     * 访问链接是添加、更新记录
     * @param visitRecord
     * @param cusInfoSet
     * @return
     */
    @Transactional
    public Boolean  saveVisitRecord(VisitRecord visitRecord,CusInfoSet cusInfoSet){
        VisitRecord visit = getvisitRecord(visitRecord);
        if (null == visit) {
            add(visitRecord);
        } else {
            Integer number = (visit.getNumber() + 1);
            visit.setVisitTime(new Date());
            visit.setNumber(number);
            edit(visit);
        }
        cusInfoSet.setUsedNum(cusInfoSet.getUsedNum()+1);
        cusInfoSetService.edit(cusInfoSet);
        return  true;
    }

}
