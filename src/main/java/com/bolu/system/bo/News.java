package com.bolu.system.bo;

import java.util.Date;

public class News {
	
	private String id;//主键
	private String title;//标题
	private String webcon;//内容
	private Integer zcat;//分类
	private Date ctime;//创建时间
	private Integer status;// 1 正常显示  0 隐藏不显示
	private String briefly;//简要 摘要
	
	//辅助字段
	private String createtime; //格式化后 创建时间
	
	public String getId() {
		return id;
	}
	public String getTitle() {
		return title;
	}
	public String getWebcon() {
		return webcon;
	}
	public Integer getZcat() {
		return zcat;
	}
	public Date getCtime() {
		return ctime;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public void setWebcon(String webcon) {
		this.webcon = webcon;
	}
	public void setZcat(Integer zcat) {
		this.zcat = zcat;
	}
	public void setCtime(Date ctime) {
		this.ctime = ctime;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getBriefly() {
		return briefly;
	}
	public void setBriefly(String briefly) {
		this.briefly = briefly;
	}
	public String getCreatetime() {
		return createtime;
	}
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}
}
