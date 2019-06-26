package com.bolu.system.quartz;

import com.bolu.base.util.SpringContextHolder;
import com.bolu.system.service.ICusInfoSetService;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.log4j.Logger;

import java.util.Date;

public class QuartzService {

    private static final Logger logger = Logger.getLogger(QuartzService.class);

    private ICusInfoSetService cusInfoSetService;

    // 调用的方法
    public void execute() {
        clearCuinfosetNum();

    }


    public void clearCuinfosetNum(){
        logger.info( DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss")+ " :执行 QuartzService.clearCuinfosetNum 方法，开始！");
        if(cusInfoSetService==null){
            cusInfoSetService = SpringContextHolder.getBean("cusInfoSetService");
            cusInfoSetService.updateDayNum();
        }
        logger.info( DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss")+ " :执行 QuartzService.clearCuinfosetNum 方法，结束！");
    }


}
