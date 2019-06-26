package com.bolu.base.common;

public class ExceptionTool {
	
	/**
	 *获取指定类型的错误
	 * @param e
	 * @param c
	 * @return
	 */
	public static Throwable getSpecialException(Exception e,Class<?> c){
		if(e==null) return e;
		Throwable t=e.getCause();
		while (t!=null&&!t.getClass().isAssignableFrom(c)) {
			t=t.getCause();
		}
		return t;
	}

}
