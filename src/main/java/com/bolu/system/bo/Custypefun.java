package com.bolu.system.bo;

/***
 * 商户类型权限表
 * @author lenovo
 *
 */
public class Custypefun {
	
	private  String id ;//主键id 
	private String custypeid;//商户类型
	private Integer funid;//菜单id
	private Integer entire;//子项是否全部选中 1 全部选中， 2未全部选中
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCustypeid() {
		return custypeid;
	}
	public void setCustypeid(String custypeid) {
		this.custypeid = custypeid;
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
