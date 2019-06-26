package com.bolu.base.util;


import com.bolu.base.common.StringUtils;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class JsonDateValueProcessor implements JsonValueProcessor {

    private String def_format = "yyyy-MM-dd HH:mm:ss";

    private DateFormat dateFormat;

    private Integer isNull=0;



    public JsonDateValueProcessor(String format) {
        try {
            if (StringUtils.isMeaningFul(format)) {
                dateFormat = new SimpleDateFormat(format);
            } else {
                dateFormat = new SimpleDateFormat(def_format);
            }
        } catch (Exception e) {
            dateFormat = new SimpleDateFormat(def_format);
        }
    }

    /**
     *
     *
     * @author pc
     * @param format
     * @param isNull
     * @return
     * @ctime 2019/5/7
     */
    public JsonDateValueProcessor(String format,Integer isNull) {

        try {
            if (StringUtils.isMeaningFul(format)) {
                dateFormat = new SimpleDateFormat(format);
                this.isNull=isNull;
            } else {
                dateFormat = new SimpleDateFormat(def_format);
                this.isNull=isNull;
            }
        } catch (Exception e) {
            dateFormat = new SimpleDateFormat(def_format);
            this.isNull=isNull;
        }
    }


    public JsonDateValueProcessor() {
        dateFormat = new SimpleDateFormat(def_format);
    }

    public Object processArrayValue(Object value, JsonConfig config) {
        return process(value);
    }

    public Object processObjectValue(String key, Object value, JsonConfig config) {
        return process(value);
    }

    private Object process(Object value) {
        try {
            if (value instanceof Date) {
                // SimpleDateFormat sdf = new SimpleDateFormat(def_format, Locale.UK);
                return  dateFormat.format(value);
                //return sdf.format(value);
            }
            if(isNull==1){
                return null;
            }else {
                return "";
            }
        }catch (Exception e){
            if(isNull==1){
                return null;
            }else {
                return "";
            }
        }
    }
}
