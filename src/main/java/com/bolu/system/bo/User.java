package com.bolu.system.bo;


import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;

/***
 * 用户信息表
 * @author lenovo
 *
 */
@Table(name = "user")
public class User {

	@Id
	private String id;//主键id
	private String roleid;//角色id
	private String cusid;//商户id
	private String username;//用户名称
	private String password;//用户密码
	private String phone;//联系电话
	private Integer  sta;//状态 1有效 0无效 默认是1
	private Date createTime;//创建时间
	private Date updateTime;//修改时间
	private Integer isdefault;//是否是默认用户 自动生成的角色
	
	//辅助字段 用于查询
	@Transient
	private String  cusName; //商户名称
	@Transient
	private String rolename;//角色名称
	@Transient
	private String  pone; //密码1 新密码
	@Transient
	private String ptwo;//密码2 确认密码
	@Transient
	private String ctime;//格式化后的创建时间
	@Transient
	private String utime;//格式化后的修改时间
	@Transient
	private String custype;	//商户的类型id
	@Transient
	private String parid;	//上级的id
	@Transient
	private String level;		//级别
	@Transient
	private Integer isorg;	//是否集团
	

	public String getParid() {
		return parid;
	}

	public void setParid(String parid) {
		this.parid = parid;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public Integer getIsorg() {
		return isorg;
	}

	public void setIsorg(Integer isorg) {
		this.isorg = isorg;
	}

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

	public String getCusid() {
		return cusid;
	}

	public void setCusid(String cusid) {
		this.cusid = cusid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Integer getSta() {
		return sta;
	}

	public void setSta(Integer sta) {
		this.sta = sta;
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

	public String getCusName() {
		return cusName;
	}

	public void setCusName(String cusName) {
		this.cusName = cusName;
	}

	public String getRolename() {
		return rolename;
	}

	public void setRolename(String rolename) {
		this.rolename = rolename;
	}

	public String getPone() {
		return pone;
	}

	public String getPtwo() {
		return ptwo;
	}

	public void setPone(String pone) {
		this.pone = pone;
	}

	public void setPtwo(String ptwo) {
		this.ptwo = ptwo;
	}

	public String getCtime() {
		return ctime;
	}

	public String getUtime() {
		return utime;
	}

	public void setCtime(String ctime) {
		this.ctime = ctime;
	}

	public void setUtime(String utime) {
		this.utime = utime;
	}

	public Integer getIsdefault() {
		return isdefault;
	}

	public void setIsdefault(Integer isdefault) {
		this.isdefault = isdefault;
	}

	public String getCustype() {
		return custype;
	}

	public void setCustype(String custype) {
		this.custype = custype;
	}
}
