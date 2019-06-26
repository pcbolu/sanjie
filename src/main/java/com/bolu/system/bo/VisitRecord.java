package com.bolu.system.bo;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;

@Table(name = "VisitRecord")
public class VisitRecord {

    @Id
    private String id;//主键
    private Date visitTime;//访问时间
    private String cusid;//顾客id
    private String taowords;//淘宝口令
    private String url;//宝贝链接
    private String imageUrl;//图片链接
    private String pwd;//访问秘钥
    private Integer number;//访问次数

    @Transient
    private String url2;//

    private Integer countnum;

    private Double jshj;



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getVisitTime() {
        return visitTime;
    }

    public void setVisitTime(Date visitTime) {
        this.visitTime = visitTime;
    }

    public String getCusid() {
        return cusid;
    }

    public void setCusid(String cusid) {
        this.cusid = cusid;
    }

    public String getTaowords() {
        return taowords;
    }

    public void setTaowords(String taowords) {
        this.taowords = taowords;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getUrl2() {
        return url2;
    }

    public void setUrl2(String url2) {
        this.url2 = url2;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }


    public Integer getCountnum() {
        return countnum;
    }

    public void setCountnum(Integer countnum) {
        this.countnum = countnum;
    }

    public Double getJshj() {
        return jshj;
    }

    public void setJshj(Double jshj) {
        this.jshj = jshj;
    }
}
