package com.bolu.system.bo;

import java.util.Date;

public class Order {

    private String id;//主键
    private String cusid;//商户id
    private String userId;//用户id
    private String tcId;//套餐id
    private Integer tcNum;//套餐数量
    private Integer tcDay;//套餐有效天数
    private Integer tcType;//套餐类型
    private Double price;//单价
    private Integer num;//购买数量
    private String totalPrice;//订单金额
    private Double disPrice;//优惠金额
    private Double nPrice;//应付款金额
    private Integer status;//订单状态
    private Date createTime;//创建时间
    private Date updateTime;//更新时间
    private Date payTime;//支付时间
    private Date cencleTime;//取消时间


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCusid() {
        return cusid;
    }

    public void setCusid(String cusid) {
        this.cusid = cusid;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTcId() {
        return tcId;
    }

    public void setTcId(String tcId) {
        this.tcId = tcId;
    }

    public Integer getTcNum() {
        return tcNum;
    }

    public void setTcNum(Integer tcNum) {
        this.tcNum = tcNum;
    }

    public Integer getTcDay() {
        return tcDay;
    }

    public void setTcDay(Integer tcDay) {
        this.tcDay = tcDay;
    }

    public Integer getTcType() {
        return tcType;
    }

    public void setTcType(Integer tcType) {
        this.tcType = tcType;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Double getDisPrice() {
        return disPrice;
    }

    public void setDisPrice(Double disPrice) {
        this.disPrice = disPrice;
    }

    public Double getnPrice() {
        return nPrice;
    }

    public void setnPrice(Double nPrice) {
        this.nPrice = nPrice;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public Date getCencleTime() {
        return cencleTime;
    }

    public void setCencleTime(Date cencleTime) {
        this.cencleTime = cencleTime;
    }
}
