package com.baihui.core.encrypt.util;

/**
 * ClassName: ClassUtil
 * 
 * @Description: 加密解密程序，可以自己定义算法
 * @author feifei.liu
 * @date 2016年11月29日 下午3:17:03
 */
public class ClassUtil {

	/**
	 * 加密程序，可以自己修改
	 * 
	 * @param ch
	 * @return
	 */
	public static byte encrypt(int ch) {
		return (byte) (ch + 2);
	}

	/**
	 * 解密程序，可以自己修改
	 * 
	 * @param ch
	 * @return
	 */
	public static byte decrypt(int ch) {
		return (byte) (ch - 2);
	}

}
