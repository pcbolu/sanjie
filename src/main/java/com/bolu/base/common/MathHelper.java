package com.bolu.base.common;
import java.util.Random;

public class MathHelper {
	
	/**
	 * 获取范围内的随机值
	 * @param min 最小值
	 * @param max 最大值
	 * @return
	 */
	public static int getRandomNumberInRange(int min,int max) {
		Random r = new Random();
		return r.ints(min,(max+1)).findFirst().getAsInt();
	}
	
	/**
	 * 生成随机字符串
	 * @param length 生成字符串的长度
	 * @return
	 */
	public static String getRandomString(int length) {
		String base = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";     
	    Random random = new Random();     
	    StringBuffer sb = new StringBuffer();     
	    for (int i = 0; i < length; i++) {     
	        int number = random.nextInt(base.length());     
	        sb.append(base.charAt(number));     
	    }     
	    return sb.toString();   
	}
}
