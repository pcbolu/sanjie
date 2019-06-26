package com.bolu.system.bo;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Table(name="CusInfoSet")
public class CusInfoSet {

    @Id
    private String id;//主键
    private String appid;//appid
    private String secret;//开发秘钥
    private Integer dayNum;//日限制数量
    private Integer warnimgNum;//报警数量
    private String  urlPwd;//链接秘钥
    private Integer usedNum;//今日访问次数
    private String  inviteCode;//邀请码
    private Integer inviteSta;//是否修改过邀请码

    @Transient
    private Integer num;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public Integer getDayNum() {
        return dayNum;
    }

    public void setDayNum(Integer dayNum) {
        this.dayNum = dayNum;
    }

    public Integer getWarnimgNum() {
        return warnimgNum;
    }

    public void setWarnimgNum(Integer warnimgNum) {
        this.warnimgNum = warnimgNum;
    }

    public String getUrlPwd() {
        return urlPwd;
    }

    public void setUrlPwd(String urlPwd) {
        this.urlPwd = urlPwd;
    }

    public Integer getUsedNum() {
        return usedNum;
    }

    public void setUsedNum(Integer usedNum) {
        this.usedNum = usedNum;
    }

    public String getInviteCode() {
        return inviteCode;
    }

    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
    }

    public Integer getInviteSta() {
        return inviteSta;
    }

    public void setInviteSta(Integer inviteSta) {
        this.inviteSta = inviteSta;
    }


    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }
}
