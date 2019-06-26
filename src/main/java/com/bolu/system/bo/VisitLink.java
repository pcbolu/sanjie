package com.bolu.system.bo;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name="VisitLink")
public class VisitLink {

    @Id
    private String id;//主键
    private String value;//值
    private String QRCode;//二维码链接
    private String url;//预览链接
    private Date createTime;//创建时间
    private Date endTime;//失效时间

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getQRCode() {
        return QRCode;
    }

    public void setQRCode(String QRCode) {
        this.QRCode = QRCode;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}
