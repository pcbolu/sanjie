package com.bolu.base.common;

import java.util.ArrayList;
import java.util.List;

public class CurrentPage<E> {

    private Integer rowCount=0; //总记录数
    private Integer pageNumber=1; //当前页
    private Integer pageSize=20; //每页显示条数
    private Integer pagesAvailable=1; //总页数

    private List<E> pageItems = new ArrayList<E>();

    public Integer getRowCount() {
        return rowCount;
    }

    public void setRowCount(Integer rowCount) {
        this.rowCount = rowCount;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPagesAvailable() {
        return pagesAvailable;
    }

    public void setPagesAvailable(Integer pagesAvailable) {
        this.pagesAvailable = pagesAvailable;
    }

    public List<E> getPageItems() {
        return pageItems;
    }

    public void setPageItems(List<E> pageItems) {
        this.pageItems = pageItems;
    }
}
