package com.bolu.system.bo;

public class CheckUrl {

    private String id;//主键
    private String url;//检测的url
    private String cusid;//顾客id
    private Integer status;//状态
    private Integer prevent;//是否被封

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCusid() {
        return cusid;
    }

    public void setCusid(String cusid) {
        this.cusid = cusid;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getPrevent() {
        return prevent;
    }

    public void setPrevent(Integer prevent) {
        this.prevent = prevent;
    }
}
