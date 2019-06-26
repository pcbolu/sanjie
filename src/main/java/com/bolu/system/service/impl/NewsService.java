package com.bolu.system.service.impl;


import com.bolu.base.common.CurrentPage;
import com.bolu.base.common.StringUtils;
import com.bolu.system.bo.News;
import com.bolu.system.dao.INewsDao;
import com.bolu.system.service.INewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class NewsService extends BaseService implements INewsService {
    private INewsDao newsDao;

    @Autowired
    private void setDao(INewsDao newsDao) {
        this.newsDao =newsDao;
        super.setBaseDao(newsDao);
    }

    /**
     * 添加新闻资讯
     * @author pc
     * @param news
     * @return
     * @ctime 2018/12/4
     */
    public Boolean   addNews(News news ) {
        Map<String, Object> map = new HashMap<String, Object>();
        if(StringUtils.isBlank(news.getId())){
            news.setId(StringUtils.uuid());
        }
        if(StringUtils.isBlank(news.getTitle())) {
            throw new RuntimeException("标题不能为空！");
        }
        if(StringUtils.isBlank(news.getWebcon())) {
            throw new RuntimeException("内容不能为空！");
        }
        if(StringUtils.isBlank(news.getBriefly())) {
            throw new RuntimeException("摘要不能为空！");
        }
        if(null==news.getZcat()) {
            throw new RuntimeException("类别不能为空！");
        }

        if(null==news.getStatus()){
            news.setStatus(1);
        }
        news.setCtime(new Date());
        map.put("briefly", news.getBriefly());
        map.put("id", news.getId());
        map.put("title", news.getTitle());
        map.put("webcon", news.getWebcon());
        map.put("zcat", news.getZcat());
        map.put("ctime", news.getCtime());
        map.put("status", news.getStatus());
        return newsDao.add(map);
    }

    /***
     * 修改新闻资讯
     * @author pc
     * @param news
     * @return
     * @ctime 2018/12/4
     */
    public Boolean updateNews(News news) {
        Map<String, Object> map = new HashMap<String, Object>();
        if(StringUtils.isBlank(news.getId())){
            throw new RuntimeException("新闻资讯id为空！无法修改");
        }
        if(StringUtils.isMeaningFul(news.getTitle())) {
            map.put("title", news.getTitle());
        }
        if(StringUtils.isMeaningFul(news.getBriefly())) {
            map.put("briefly", news.getBriefly());
        }
        if(StringUtils.isMeaningFul(news.getWebcon())) {
            map.put("webcon", news.getWebcon());
        }
        if(null!=news.getZcat()) {
            map.put("zcat", news.getZcat());
        }
        if(null!=news.getStatus()){
            map.put("status", news.getStatus());
        }
        if(null!=news.getCtime()){
            map.put("ctime", news.getCtime());
        }
        return newsDao.edit(map, news.getId());
    }

    /***
     * 根据资讯类型 查询 前topNum条纪录
     * @author pc
     * @param
     * @param topNum 显示前几条数据
     * @return
     * @ctime 2018/12/4
     */
    public List<News> getListNewsByStatusAndTop(Integer zcat , Integer topNum){
        List<Object> params= new ArrayList<Object>();
        String sql=" select id,title,webcon,zcat, DATE_FORMAT(ctime,'%Y-%m-%d %H:%i') createtime, ctime,briefly,status from news  where  status=1  ";
        if(null!=zcat) {
            sql+=" and zcat=? ";
            params.add(zcat);
        }
        sql+=" order by ctime desc ";
        if(null!=topNum) {
            sql+="  limit  ? ";
            params.add(topNum);
        }
        List<News> list=	newsDao.findList(sql, params.toArray());

        return list;
    }

    /***
     * 分页 新闻资讯
     * @author pc
     * nowPage 当前页码
     * pageSize 每页显示多少条
     * @return
     * @ctime 2018/12/4
     */
    public CurrentPage<News> getNewsPage(int nowPage, int pageSize, News news) {
        List<Object> params = new ArrayList<Object>();
        String strQ="select id,title,webcon,zcat,DATE_FORMAT(ctime,'%Y-%m-%d %H:%i') createtime, ctime,briefly,status from news  where 1=1  ";
        String strC="select count(id) from news  where 1=1 ";
        if(null!=news.getZcat()) {
            strQ+=" and zcat=?";
            strC+=" and zcat=?";
            params.add(news.getZcat());
        }
        if(null!=news.getStatus()) {
            strQ+=" and status=?";
            strC+=" and status=?";
            params.add(news.getStatus());
        }
        strQ +=" order by ctime desc";
        // 查询条件
        CurrentPage<News> currentPage = newsDao.getPage(strC, strQ, params.toArray(), nowPage, pageSize);
        return currentPage;
    }

}

