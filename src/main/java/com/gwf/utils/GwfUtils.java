package com.gwf.utils;

import com.gwf.entity.SystemInforEntity;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.IOException;

/**
 * @author gwf
 * @version 1.0
 * @date 2021/4/15 17:55
 *
 */
public interface GwfUtils {
	/**
	 * 文件读取判断 
	 * readCokies 开发环境
	 * readFileOnce 运行环境
	 */
	public String fileReadSelect(String fileName) ;
	public String readCokies(String fileName) ;
	public String readFileOnce(String strFile);
	public boolean isRunTime(int numStatic);

	public void ConsoleHead(String name);

	public void ConsoleEnd(String name);


	/**
	 * BASE64解密
	 * @throws Exception
	 */
//	public static  byte[] decryptBASE64(String key) ;

	/**
	 * BASE64加密
	 */
//	public static  String encryptBASE64(byte[] key) ;

	/**
	 * BASE64解密
	 * @throws Exception
	 */
	public static  byte[] decryptBASE64(String key) {

//		String data = (new BASE64Decoder()).decodeBuffer("OWJkMTJiMTAtZTgyZS00M2M3LTkxMmYtNjkyZDRjM2Y4ZmNk");
		try {
			return (new BASE64Decoder()).decodeBuffer(key);
		} catch (IOException e) {
//			log.info("解密异常");
			return null;
		}
	}

	/**
	 * BASE64加密
	 */
	public static  String encryptBASE64(byte[] key)  {
		return (new BASE64Encoder()).encodeBuffer(key);
	}
	public  boolean isPing();
}
