package com.bolu.system.bo;

/***
 * 商户分类
 * @author lenovo
 *
 */
public class Custom {
	
		private String  id ;//主键id
		private String typename;//类型名称
		private Integer isdz;//是否定制 1定制 0常规
		private Integer sta;//状态 1有效 0无效
		private Integer isorg;
		
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getTypename() {
			return typename;
		}
		public void setTypename(String typename) {
			this.typename = typename;
		}
		public Integer getIsdz() {
			return isdz;
		}
		public void setIsdz(Integer isdz) {
			this.isdz = isdz;
		}
		public Integer getSta() {
			return sta;
		}
		public void setSta(Integer sta) {
			this.sta = sta;
		}
		public Integer getIsorg() {
			return isorg;
		}
		public void setIsorg(Integer isorg) {
			this.isorg = isorg;
		}
}