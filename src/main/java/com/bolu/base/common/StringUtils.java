package com.bolu.base.common;


import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.net.URL;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class StringUtils extends org.apache.commons.lang.StringUtils {

    public final static String ELLIPSIS = "...";

    public final static String EMPTY_STRING = "";

    private static final Logger logger = Logger.getLogger(StringUtils.class);

    private StringUtils() {
        throw new AssertionError(getClass() + "不需要实例化!");
    }

    /**
     * 产生4位随机数(0000-9999)
     *
     * @return 4位随机数
     */
    public static String getFourRandom() {
        Random random = new Random();
        String fourRandom = random.nextInt(10000) + "";
        int randLength = fourRandom.length();
        if (randLength < 4) {
            for (int i = 1; i <= 4 - randLength; i++)
                fourRandom = "0" + fourRandom;
        }
        return fourRandom;
    }


    /**
     * 产生6位随机数(000000-999999)
     * 验证码
     * @return 6位随机数
     */
    public static String getSixRandom() {
        Random random = new Random();
        String sixRandom = random.nextInt(1000000) + "";
        int randLength = sixRandom.length();
        if (randLength < 6) {
            for (int i = 1; i <= 6 - randLength; i++)
                sixRandom = "0" + sixRandom;
        }
        return sixRandom;
    }



    public static String getRandomString(int length){
        String str="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random=new Random();
        StringBuffer sb=new StringBuffer();
        for(int i=0;i<length;i++){
            int number=random.nextInt(62);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }

    /**
     * 生成三十二位随机字符串
     *
     * @author pc
     * @return
     * @ctime 2018/12/4
     */
    public static String uuid() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * 判断字符串是否有实际意义
     *
     * @param str
     * @return
     */
    public static boolean isMeaningFul(String str) {
        return (null != str) && (0 < str.trim().length());
    }

    /**
     * 判断两字符串是否有实际意义且相等
     *
     * @param str1
     * @param str2
     * @return boolean
     */
    public static boolean isEqual(String str1, String str2) {
        if (!isMeaningFul(str1))
            return false;
        if (!isMeaningFul(str2))
            return false;
        return str1.equals(str2);
    }


    /**
     * 随机字符串
     * @return
     */
    public static String getNonceStr() {
        Random random = new Random();
        return MD5Util.MD5Encode(String.valueOf(random.nextInt(10000)), "UTF-8");
    }

    /***
     * 判断str1是否大于str2
     *
     * @param str1
     * @param str2
     * @return boolean
     */
    public static boolean isAfter(String str1, String str2) {
        if (!isMeaningFul(str1) || !isMeaningFul(str2))
            return false;
        return str1.compareToIgnoreCase(str2) > 0;
    }

    /**
     * 返回 一段字符串的缩略表达形式，如果字符串为空时不处理
     *
     * @param str
     * @param lengthLimit
     * @param suffix
     * @return
     */
    public static String forShort(String str, int lengthLimit, String suffix) {
        if (null != str && str.length() > lengthLimit) {
            str = str.substring(0, lengthLimit);
            if (isMeaningFul(suffix)) {
                str += suffix;
            }
        }
        return str;
    }

    /**
     * @param str
     * @param candidate
     * @return
     */
    public static String notNull(String str, String candidate) {
        return (null != str) ? str : StringUtils.notNull(candidate, "");
    }

    /**
     * @param str
     * @return
     */
    public static String notNullAndTrim(String str) {
        return StringUtils.notNull(str, "").trim();
    }

    /**
     * @param str
     * @return
     */
    public static String trim(String str) {
        return (null == str) ? str : str.trim();
    }

    /**
     * 下划线命名法转驼峰记名法
     *
     * @param underScoreCase
     * @return
     */
    public static String underScoreCaseToCamelCase(String underScoreCase) {
        if (null == underScoreCase) {
            throw new IllegalArgumentException("要转换的字符串不能为null");
        }
        if (-1 == underScoreCase.indexOf("_")) {
            return underScoreCase;
        }
        underScoreCase = underScoreCase.toLowerCase();
        Pattern p = Pattern.compile("_([a-z])");
        Matcher m = p.matcher(underScoreCase);
        StringBuffer sb = new StringBuffer();
        while (m.find()) {
            m.appendReplacement(sb, m.group(1).toUpperCase());
        }
        m.appendTail(sb);
        return sb.toString();
    }


    /**
     * 生成订单编码
     *
     * @author pc
     * @param
     * @return
     * @ctime 2018/12/4
     */
    public static String getPwd() {
        String date = DateFormatUtils.format(new Date(), "HHmmssSSS");
        String rand = RandomStringUtils.randomNumeric(7);
        return  date + rand;
    }


    /**
     * 生成订单编码
     *
     * @author pc
     * @param
     * @return
     * @ctime 2018/12/4
     */
    public static String getNonceSixStr() {
        String rand = RandomStringUtils.randomNumeric(6);
        return rand;
    }


    /**
     * 驼峰记名法转下划线命名法
     *
     * @param camelCase
     * @return
     */
    public static String camelCaseToUnderScoreCase(String camelCase) {
        if (null == camelCase) {
            throw new IllegalArgumentException("要转换的字符串不能为null");
        }
        Pattern p = Pattern.compile("([A-Z])");
        Matcher m = p.matcher(camelCase);
        StringBuffer sb = new StringBuffer();
        while (m.find()) {
            m.appendReplacement(sb, "_" + m.group(1).toLowerCase());
        }
        m.appendTail(sb);
        return sb.toString();
    }

    /**
     * 将字符串首字符转大写返回
     *
     * @param fieldName
     * @return
     * @throws Exception
     */
    public static String firstCharToUpperCase(String fieldName) throws Exception {
        String a = fieldName.substring(1);
        char b = fieldName.charAt(0);
        if (b > 96 && b < 123) {
            return (char) (b - 32) + a;
        }
        return fieldName;
    }

    public static String toUtf8Str(String utf8Code) {
        StringBuilder sb = new StringBuilder();
        int i = -1;
        int pos = 0;
        while ((i = utf8Code.indexOf("\\u", pos)) != -1) {
            sb.append(utf8Code.substring(pos, i));
            if (i + 5 < utf8Code.length()) {
                pos = i + 6;
                sb.append((char) Integer.parseInt(utf8Code.substring(i + 2, i + 6), 16));
            }
        }
        return sb.toString();
    }

    public static boolean isMeaningFul(Date date) {
        return date != null;
    }

    /**
     * 检查密码是否被加密
     *
     * @param password 密码
     * @return
     */
    public static Boolean checkPassWordEncoded(String password) {
        if (isBlank(password)) {
            return false;
        }
        if (password.length() != 24) {
            return false;
        }
        return true;
    }

    /**
     * 判断字符序列是否为空
     *
     * @param cs
     * @return
     */
    public static boolean isBlank(final CharSequence cs) {
        int strLen;
        if (cs == null || (strLen = cs.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (Character.isWhitespace(cs.charAt(i)) == false) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断字符序列是否非空
     *
     * @param cs
     * @return
     */
    public static boolean isNotBlank(final CharSequence cs) {
        return !StringUtils.isBlank(cs);
    }

    public static String getInSql(String str) {
        String[] split = str.split(",");
        String result = " (";
        for (String ss : split) {
            result += "'" + ss + "',";
        }
        result = result.substring(0, result.length() - 1);
        result += ")";
        return result;
    }

    /**
     * "file:/home/whf/cn/fh" -> "/home/whf/cn/fh"
     * "jar:file:/home/whf/foo.jar!cn/fh" -> "/home/whf/foo.jar"
     */
    public static String getRootPath(URL url) {
        String fileUrl = url.getFile();
        int pos = fileUrl.indexOf('!');

        if (-1 == pos) {
            return fileUrl;
        }

        return fileUrl.substring(5, pos);
    }

    /**
     * "cn.fh.lightning" -> "cn/fh/lightning"
     *
     * @param name
     * @return
     */
    public static String dotToSplash(String name) {
        return name.replaceAll("\\.", "/");
    }

    /**
     * "Apple.class" -> "Apple"
     */
    public static String trimExtension(String name) {
        int pos = name.indexOf('.');
        if (-1 != pos) {
            return name.substring(0, pos);
        }

        return name;
    }

    /**
     * /application/home -> /home
     *
     * @param uri
     * @return
     */
    public static String trimURI(String uri) {
        String trimmed = uri.substring(1);
        int splashIndex = trimmed.indexOf('/');
        return trimmed.substring(splashIndex);
    }

    /**
     * 从request中取出参数填充字符串${参数名}的表达方式
     *
     * @param request
     * @param template
     * @return
     */
    public static String requestParamsToTemplate(HttpServletRequest request, String template) {
        Pattern p = Pattern.compile("\\$\\{.+?\\}");
        Matcher m = p.matcher(template);
        StringBuffer sb = new StringBuffer();
        while (m.find()) {
            String paramName = template.substring(m.start() + 2, m.end() - 1);
            String paramValue = StringUtils.notNullAndTrim(request.getParameter(paramName));
            if (!StringUtils.isMeaningFul(paramValue) && request.getAttribute(paramName) instanceof String) {
                paramValue = StringUtils.notNullAndTrim((String) request.getAttribute(paramName));
            }
            m.appendReplacement(sb, paramValue);
        }
        m.appendTail(sb);
        return sb.toString();
    }

    public static String joinWithDouhao(Collection<Object> collection) {
        return join(collection, ",");
    }

    /**
     * 将元为单位的转换为分 替换小数点，支持以逗号区分的金额
     *
     * @param amount
     * @return
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

    /**
     * 获取随机字符串 Nonce Str
     *
     * @return String 随机字符串
     */
    public static String generateNonceStr() {
        return UUID.randomUUID().toString().replaceAll("-", "").substring(0, 32);
    }

    /***
     * 字符串转成map
     *
     * @param paramsStr
     *            openid=134,sendType=slh
     * @param split1
     *            分隔符1 ","
     * @param split2
     *            分隔符1 "="
     * @return
     */
    public static Map<String, String> strToMap(String paramsStr, String split1, String split2) {
        if (StringUtils.isMeaningFul(paramsStr)) {
            String[] parmas = paramsStr.split(split1);
            Map<String, String> map = new HashMap<String, String>();
            for (String str : parmas) {
                String[] p = str.split(split2);
                if (p.length == 2 && null != p[0] && null != p[1]) {
                    map.put(p[0].trim(), p[1].trim());
                }
            }
            return map;
        } else {
            return null;
        }
    }





    //方法三：
    public static boolean isNumeric(String str){
        Pattern pattern = Pattern.compile("^(\\-|\\+)?\\d+(\\.\\d+)?$");
        return pattern.matcher(str).matches();
    }


}
