package com.bolu.system.bo;


import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 角色权限表
 * @author lenovo
 *
 */
@Table(name = "roleFun")
public class RoleFun {

	@Id
	private String id;//主键id
	private String roleid;//角色id
	private Integer funid;//菜单id
	private Integer entire;//子项是否全部选中 1 全部选中， 2未全部选中
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getRoleid() {
		return roleid;
	}
	public void setRoleid(String roleid) {
		this.roleid = roleid;
	}
	public Integer getFunid() {
		return funid;
	}
	public void setFunid(Integer funid) {
		this.funid = funid;
	}
	public Integer getEntire() {
		return entire;
	}
	public void setEntire(Integer entire) {
		this.entire = entire;
	}
}

