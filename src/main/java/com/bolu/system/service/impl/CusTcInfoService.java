package com.bolu.system.service.impl;


import com.bolu.system.bo.CusTcInfo;
import com.bolu.system.dao.ICusTcInfoDao;
import com.bolu.system.dao.impl.CusTcInfoDao;
import com.bolu.system.service.ICusTcInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CusTcInfoService extends BaseService<CusTcInfo> implements ICusTcInfoService {

    private ICusTcInfoDao cusTcInfoDao;

    @Autowired
    private void setDao(CusTcInfoDao cusTcInfoDao) {
        this.cusTcInfoDao = cusTcInfoDao;
        super.setBaseDao(cusTcInfoDao);
    }
}
