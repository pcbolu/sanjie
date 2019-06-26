package com.bolu.system.bo;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 角色信息表
 * @author lenovo
 *
 */
@Table(name = "Role")
public class Role {

	@Id
	private String  id;//主键id
	private String rolename;//角色名称
	private String cusid;//商户id
	private Integer sta;//状态
	private Integer isdefault;//是否是默认角色 自动生成的角色
	
	//辅助字段
	@Transient
	private String funIds;//功能id
	@Transient
	private String cusname;//商户名称

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getRolename() {
		return rolename;
	}
	public void setRolename(String rolename) {
		this.rolename = rolename;
	}
	public String getCusid() {
		return cusid;
	}
	public void setCusid(String cusid) {
		this.cusid = cusid;
	}
	public Integer getSta() {
		return sta;
	}
	public void setSta(Integer sta) {
		this.sta = sta;
	}

	public Integer getIsdefault() {
		return isdefault;
	}
	public void setIsdefault(Integer isdefault) {
		this.isdefault = isdefault;
	}
	public String getFunIds() {
		return funIds;
	}
	public String getCusname() {
		return cusname;
	}
	public void setFunIds(String funIds) {
		this.funIds = funIds;
	}
	public void setCusname(String cusname) {
		this.cusname = cusname;
	}
	
	
	
}
