package com.bolu.system.service.impl;


import com.bolu.system.bo.PwdRecord;
import com.bolu.system.dao.IPwdRecordDao;
import com.bolu.system.dao.impl.PwdRecordDao;
import com.bolu.system.service.IPwdRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PwdRecordService extends BaseService<PwdRecord> implements IPwdRecordService {
    private IPwdRecordDao pwdRecordDao;

    @Autowired
    private void setDao(PwdRecordDao pwdRecordDao) {
        this.pwdRecordDao = pwdRecordDao;
        super.setBaseDao(pwdRecordDao);
    }
}
