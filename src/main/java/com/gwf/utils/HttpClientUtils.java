package com.gwf.utils;

/**
 * @author gwf
 * @version 1.0
 * @date 2021/4/15 17:55
 *
 */

public interface HttpClientUtils {
	/**
	 * 获取cookie
	 * @return
	 */
	public String getCookies();
	/**
	 * 获取application/x-www-form-urlencoded
	 * @param strURL
	 * @param params
	 * @return
	 */
	public String HttpClient_Reptile_Default(String strURL, String params);
	/**
	 * 获取json爬虫
	 * @param strURL
	 * @param params
	 * @return
	 */
	public String Http_Reptile_Json(String strURL, String params);
}
