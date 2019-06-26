package com.bolu.base.common;

public class LocationUtil {
	private static double EARTH_RADIUS = 6378.137;

	private static double rad(double d) {
        return d * Math.PI / 180.0;  
    }
	
	/***
	 * 两点距离的计算 (单位：米)
	 * @param lat1	坐标1的经度
	 * @param lng1 坐标1的纬度
	 * @param lat2 坐标2的经度
	 * @param lng2 坐标2的纬度
	 * @return
	 */
	public static double getDistance(double lat1, double lng1, double lat2, double lng2) {
		double radLat1 = rad(lat1);
		double radLat2 = rad(lat2);
		double a = radLat1 - radLat2;
		double b = rad(lng1) - rad(lng2);
		double s = 2 * Math.asin(Math.sqrt(
				Math.pow(Math.sin(a / 2), 2) + Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
		s = s * EARTH_RADIUS;
		s = Math.round(s * 10000d) / 10000d;
		s = s * 1000;
		
		//只取整数
		int rr = (int)s;
		
		return (double)rr;
	}
}
