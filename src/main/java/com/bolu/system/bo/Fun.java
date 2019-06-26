package com.bolu.system.bo;

import javax.persistence.Id;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.List;

/**
 * 系统功能菜单信息表
 * @author lenovo
 *
 */
public class Fun implements Serializable {

	@Id
	private String id;            //主键id
	private String name;// 功能名称
	private String control;//控制器
	private String action;//方法名
	private String subitem;//l
	private String logo;
	private Integer num;//排序
	private Integer level;//层次
	private String parentid;//父级id
	private String fcode; //功能编码
	private Integer funtype;//功能类型  0 共有 1 集团专用 2分店专用
	private String apptype;//应用类型

	//辅助字段
	@Transient
	private List<Fun> subfun = null; //子节点
	@Transient
	private Integer checked;

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

	public String getControl() {
		return control;
	}

	public void setControl(String control) {
		this.control = control;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getSubitem() {
		return subitem;
	}

	public void setSubitem(String subitem) {
		this.subitem = subitem;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public String getParentid() {
		return parentid;
	}

	public void setParentid(String parentid) {
		this.parentid = parentid;
	}

	public String getFcode() {
		return fcode;
	}

	public void setFcode(String fcode) {
		this.fcode = fcode;
	}

	public Integer getFuntype() {
		return funtype;
	}

	public void setFuntype(Integer funtype) {
		this.funtype = funtype;
	}

	public String getApptype() {
		return apptype;
	}

	public void setApptype(String apptype) {
		this.apptype = apptype;
	}

	public List<Fun> getSubfun() {
		return subfun;
	}

	public void setSubfun(List<Fun> subfun) {
		this.subfun = subfun;
	}

	public Integer getChecked() {
		return checked;
	}

	public void setChecked(Integer checked) {
		this.checked = checked;
	}
}
