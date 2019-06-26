package com.bolu.system.service.impl;


import com.bolu.base.common.CurrentPage;
import com.bolu.base.common.MD5Util;
import com.bolu.base.common.StringUtils;
import com.bolu.system.bo.*;
import com.bolu.system.dao.ICusInfoDao;
import com.bolu.system.dao.impl.CusInfoDao;
import com.bolu.system.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CusInfoService extends BaseService<CusInfo> implements ICusInfoService {

    @Autowired
    private IRoleService roleService;
    @Autowired
    private IRolefunService rolefunService;
    @Autowired
    private IUserService userService;
    @Autowired
    private ICustypefunService custypefunService;
    @Autowired
    private ICusInfoSetService cusInfoSetService;

    private ICusInfoDao cusInfoDao;

    @Autowired
    private void setDao(CusInfoDao cusInfoDao) {
        this.cusInfoDao = cusInfoDao;
        super.setBaseDao(cusInfoDao);
    }

    /***
     * 添加商户
     * @param cusInfo
     * @return
     */
    @Transactional
    public Boolean saveCusInfo(CusInfo cusInfo){
        if(StringUtils.isMeaningFul(cusInfo.getId())){
            edit(cusInfo);
        }else {
            Date now=new Date();
            cusInfo.setId(StringUtils.uuid());
            cusInfo.setCreateTime(now);
            cusInfo.setUpdateTime(now);
            add(cusInfo);
            addCusinfoHandle(cusInfo);
        }
        return true;
    }


    /**
     * 商户添加完成 创建默认角色 和用户
     * @param cusInfo
     * @return
     */
    public Boolean addCusinfoHandle(CusInfo cusInfo){

        String cusid=cusInfo.getId();
        String cusType=cusInfo.getCusType();
        Role role= new Role();
        String roleid=StringUtils.uuid();
        role.setId(roleid);
        role.setCusid(cusid);
        role.setIsdefault(1);
        role.setRolename(cusInfo.getCusName()+"(管理员)");
        role.setSta(1);
        roleService.add(role);

        User user=new User();
        String userId=StringUtils.uuid();
        user.setId(userId);
        user.setCusid(cusid);
        user.setUsername(cusInfo.getPerson());
        user.setRoleid(roleid);
        user.setPassword(MD5Util.MD5Encode("123","UTF-8"));
        user.setSta(1);
        user.setPhone(cusInfo.getTel());
        user.setCreateTime(cusInfo.getCreateTime());
        user.setUpdateTime(cusInfo.getUpdateTime());
        user.setIsdefault(1);
        userService.add(user);


        CusInfoSet cusInfoSet= new CusInfoSet();
        cusInfoSet.setId(cusInfo.getId());
        cusInfoSet.setAppid("");
        cusInfoSet.setSecret("");
        cusInfoSet.setDayNum(10);
        cusInfoSet.setWarnimgNum(0);
        String urlpwd=StringUtils.getPwd();
        cusInfoSet.setUrlPwd(urlpwd);
        String inviteCode=StringUtils.getNonceSixStr();
        cusInfoSet.setInviteCode(inviteCode);
        cusInfoSet.setInviteSta(0);

        cusInfoSetService.add(cusInfoSet);
        List<Custypefun>  custypefunList=custypefunService.getCustypefunByCustomId(cusType);
        for (Custypefun custypefun : custypefunList) {
            RoleFun roleFun=new RoleFun();
            roleFun.setId(StringUtils.uuid());
            roleFun.setRoleid(roleid);
            roleFun.setFunid(custypefun.getFunid());
            roleFun.setEntire(custypefun.getEntire());
            rolefunService.add(roleFun);
        }

        return true;
    }

    /***
     * 根据商户类型查询商户信息
     * @param cusType
     * @return
     */
    public List<CusInfo> getCusinfoByCustype(String cusType){

        String sql="select * from CusInfo where cusType=?";
       return  cusInfoDao.findList(sql,new Object[]{cusType});
    }







    /**
     * 分页查询
     *
     * @return
     * @author pc
     * @ctime 2018/12/4
     */
    public CurrentPage<CusInfo> getCusInfoPage(int nowPage, int pageSize, CusInfo cusInfo) {
        List<Object> param = new ArrayList<Object>();
        String strQ = "select ci.*,cs.typename from CusInfo ci "
                + " left join custom cs on ci.custype=cs.id "
                + " where 1=1 ";

        String strC = "select count(ci.id) from CusInfo ci "
                + " left join custom cs on ci.custype=cs.id "
                + " where 1=1  ";

        // 查询条件
        if (StringUtils.isMeaningFul(cusInfo.getCusName())) {
            strC += " and ci.cusName like '%" + cusInfo.getCusName() + "%' ";
            strQ += " and ci.cusName like '%" + cusInfo.getCusName() + "%' ";
        }
        strQ += " order by custype , createTime desc ";

        CurrentPage<CusInfo> currentPage = cusInfoDao.getPage(strC, strQ, param.toArray(), nowPage, pageSize);
        return currentPage;
    }


    /***
     * 根据编码查询
     * @param code
     * @return
     */
    public List<CusInfo> getCusInfoByCode(String code){
        String sql="select * from cusinfo where code=?";
        return  cusInfoDao.findList(sql,new Object[]{code});
    }



}
