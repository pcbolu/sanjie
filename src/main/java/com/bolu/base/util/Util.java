package com.bolu.base.util;

import net.sf.json.JSONObject;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Util {

    public static String cusid = "";
    public static Logger logger = LoggerFactory.getLogger(Util.class);
    /***
     *  获取HttpServletRequest里面的参数，并decode
     * @param request
     * @return
     */
    public static Map<String, String> getRequestParams(HttpServletRequest request) {
        Map<String, String[]> params = request.getParameterMap();
        Map<String, String> params2 = new HashMap<String, String>();
        for (String key : params.keySet()) {
            String[] values = params.get(key);
            if (values.length > 0) {
                params2.put(key, values[0]);
            }
        }
        return params2;
    }

    /***
     * 生成签名
     * @author pc
     * @param md5Key
     * @param params
     * @return
     * @ctime 2018/12/4
     */
    public static String makeSign(String md5Key, Map<String, String> params) {
        String preStr = buildSignString(params); // 把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
        String text = preStr + md5Key;
        String md5 = DigestUtils.md5Hex(getContentBytes(text)).toUpperCase();
        logger.info("text====" + text);
        logger.info("md5====" + md5);
        return md5;
    }


    /***
     * 判断 params 中 sign  与  生成的 signV 是否相等
     * @param md5Key
     * @param params
     * @return
     */
    public static boolean checkSign(String md5Key, Map<String, String> params) {
        String sign = params.get("sign");
        if (StringUtils.isBlank(sign)) {
            return false;
        }
        String signV = makeSign(md5Key, params);
        System.out.println(signV + "------------------------" + sign);
        return StringUtils.equalsIgnoreCase(sign, signV);
    }



    /**
     * 验签
     * */
    public static boolean checkSign2(String md5Key,Map<String,String> params){

        //获取params中的sign
        String originalSign = params.get("sign");
        System.out.println("原有签名sign："+originalSign);
        System.out.println("获取请求中的原有map为:"+params);

        //生成待签字串 和  sign
        String preStrNew = buildSignString(params); // 把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
        String preStrNew_md5Key = preStrNew + md5Key;
        System.out.println("生成待签字串为："+preStrNew_md5Key);

        String sign = DigestUtils.md5Hex(getContentBytes(preStrNew_md5Key)).toUpperCase();
        System.out.println("计算签名sign为："+sign);

        //返回结果
        return originalSign.equals(sign);
    }


    /***
     * 生成订单请求 url
     * @author pc
     * @param json
     * @param md5Key
     * @param apiUrl
     * @return
     * @ctime 2018/12/4
     */
    public static String makeOrderRequest(JSONObject json, String md5Key, String apiUrl) {
        Map<String, String> params = jsonToMap(json);
        params.put("sign", makeSign(md5Key, params));
        return apiUrl + "?" + buildUrlParametersStr(params);
    }

    /****
     * 将参数map  转 成 参数字符串 key1=value1&key2=value2&key3=value3
     * @author pc
     * @param paramMap
     * @return
     * @ctime 2018/12/4
     */
    private static String buildUrlParametersStr(Map<String, String> paramMap) {
        Map.Entry entry;
        StringBuffer buffer = new StringBuffer();
        for (Iterator iterator = paramMap.entrySet().iterator(); iterator.hasNext(); ) {
            entry = (Map.Entry) iterator.next();
            buffer.append(entry.getKey().toString()).append("=");
            try {
                if (entry.getValue() != null && com.bolu.base.common.StringUtils.isMeaningFul((entry.getValue().toString()))) {
                    buffer.append(URLEncoder.encode(entry.getValue().toString(), "UTF-8"));
                }
            } catch (UnsupportedEncodingException e) {
            }
            buffer.append(iterator.hasNext() ? "&" : "");
        }
        return buffer.toString();
    }

    /**
     * 构建签名字符串
     *
     * @param params
     * @return
     */
    public static String buildSignString(Map<String, String> params) {

        if (params == null || params.size() == 0) {
            return "";
        }
        List<String> keys = new ArrayList<String>(params.size());
        for (String key : params.keySet()) {
            if ("sign".equals(key))
                continue;
            if (StringUtils.isEmpty(params.get(key)))
                continue;
            keys.add(key);
        }
        Collections.sort(keys);
        StringBuilder buf = new StringBuilder();
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = params.get(key);
            if (i == keys.size() - 1) {// 拼接时，不包括最后一个&字符
                buf.append(key + "=" + value);
            } else {
                buf.append(key + "=" + value + "&");
            }
        }
        return buf.toString();
    }

    /***
     * 根据编码类型获得签名内容byte[]
     * @param content
     * @return
     */
    private static byte[] getContentBytes(String content) {
        try {
            return content.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("签名过程中出现错误");
        }
    }


    /**
     * 使json-lib来进行json到map的转换，fastjson有排序问题，不能用
     *
     * @param json
     * @return
     */
    public static Map<String, String> jsonToMap(JSONObject json) {
        Map<String, String> map = new HashMap<String, String>();
        for (Object key : json.keySet()) {
            String value = json.optString((String) key);
            map.put((String) key, value);
        }
        return map;
    }


    /**
     * 生成订单编码
     *
     * @author pc
     * @param msgId
     * @return
     * @ctime 2018/12/4
     */
    public static String genMerOrderId(String msgId) {
        String date = DateFormatUtils.format(new Date(), "yyyyMMddHHmmssSSS");
        String rand = RandomStringUtils.randomNumeric(7);
        return msgId + date + rand;
    }

    /**
     * 生成 开票订单id
     *
     * @author pc
     * @param billid
     * @return
     * @ctime 2018/12/4
     */
    public static String getKpidId(String billid) {
        String date = DateFormatUtils.format(new Date(), "HHmmssSSS");
        String rand = RandomStringUtils.randomNumeric(4);
        return billid +"-"+ date + rand;
    }


    /**
     * 生成销售单据
     *
     * @author pc
     * @param
     * @return
     * @ctime 2018/12/4
     */
    public static String getXsdjbh() {
        String date = DateFormatUtils.format(new Date(), "yyMMddHHmm");
        String rand = RandomStringUtils.randomNumeric(5);
        return date + rand;
    }

    /***
     * 判断订单支付时间是否有效
     * @param merorderId  订单id
     * @param overTime 支付超出的时间 单位为分钟
     * @return true 有效  false无效
     */
    public static Boolean payOvertime(String merorderId, Long overTime) {
        //获取订单中的时间段 yyyyMMddHHmmss
        String datastr = merorderId.substring(4, 18);
        //设置时间格式
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        try {
            //将 字符串 格式化成日期
            Date orderTime = simpleDateFormat.parse(datastr);
            //将日期转成 long
            Long orderTimeLong = orderTime.getTime();
            //当前时间转成 long
            Long nowLong = new Date().getTime();
            //计算超出时间 （分钟）
            Long Minute = (nowLong - orderTimeLong) / 1000 / 60;
            if (Minute > overTime) {
                return false;
            } else {
                return true;
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }


    /***
     * 处理包含, ￥ 或者$的金额
     * @author pc
     * @param amount
     * @return
     * @ctime 2018/12/4
     */
    public static String changeY2F(String amount) {
        String currency = amount.replaceAll("\\$|\\￥|\\,", ""); // 处理包含, ￥ 或者$的金额
        int index = currency.indexOf(".");
        int length = currency.length();
        Long amLong = 0l;
        if (index == -1) {
            amLong = Long.valueOf(currency + "00");
        } else if (length - index >= 3) {
            amLong = Long.valueOf((currency.substring(0, index + 3)).replace(".", ""));
        } else if (length - index == 2) {
            amLong = Long.valueOf((currency.substring(0, index + 2)).replace(".", "") + 0);
        } else {
            amLong = Long.valueOf((currency.substring(0, index + 1)).replace(".", "") + "00");
        }
        return amLong.toString();
    }

}
