package com.bolu.system.service;


import com.bolu.system.bo.CusInfoSet;

public interface ICusInfoSetService extends IBaseService<CusInfoSet> {

    /***
     * 根据链接秘钥查询商户配置
     * @param pwd
     * @return
     */
    public CusInfoSet getCusInfoByPwd(String pwd);

    /***
     * 修改日访问数量为0
     * @return
     */
    public Boolean updateDayNum();
}
