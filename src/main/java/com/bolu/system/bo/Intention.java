package com.bolu.system.bo;

/***
 * 客户意向表
 */
public class Intention {

    private  String  id;//主键
    private String name;//客户名称
    private String phone;//电话
    private String industry;//行业
    private String options;// 意向 套餐  /使用产品
    private String email;//邮箱
    private String address;//地址
    private String remark;//备注/留言/描述
    private  Integer type;//类型 1 意向客户  2 产品使用  3 需求定制

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getOptions() {
        return options;
    }

    public void setOptions(String options) {
        this.options = options;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
