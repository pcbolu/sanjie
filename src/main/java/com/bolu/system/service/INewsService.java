package com.bolu.system.service;


import com.bolu.base.common.CurrentPage;
import com.bolu.system.bo.News;

import java.util.List;

public interface INewsService   extends IBaseService {



    /**
     * 添加新闻资讯
     * @param news
     * @return
     */
    public Boolean   addNews(News news);

    /***
     * 修改新闻资讯
     * @param news
     * @return
     */
    public Boolean updateNews(News news);

    /***
     * 根据资讯类型 查询 前topNum条纪录
     * @param
     * @param topNum 显示前几条数据
     * @return
     */
    public List<News> getListNewsByStatusAndTop(Integer zcat, Integer topNum);

    /***
     * 分页 新闻资讯
     * nowPage 当前页码
     * pageSize 每页显示多少条
     * @return
     */
    public CurrentPage<News> getNewsPage(int nowPage, int pageSize, News news);
}
