package com.bolu.system.bo;

import java.util.Date;

public class TcInfo {

    private String id;//主键
    private Integer tcNum;//套餐数量
    private Integer tcday;//套餐有效天数
    private Double costPrice;//原价
    private Double sellPrice;//售价
    private Date createTime;//创建时间
    private Date updateTime;//更新时间
    private Integer status;//状态

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getTcNum() {
        return tcNum;
    }

    public void setTcNum(Integer tcNum) {
        this.tcNum = tcNum;
    }

    public Integer getTcday() {
        return tcday;
    }

    public void setTcday(Integer tcday) {
        this.tcday = tcday;
    }

    public Double getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(Double costPrice) {
        this.costPrice = costPrice;
    }

    public Double getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(Double sellPrice) {
        this.sellPrice = sellPrice;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
