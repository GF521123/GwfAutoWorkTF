package com.gwf.utils;

import com.gwf.entity.SystemInforEntity;

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
	public boolean isRunTime();

	public void ConsoleHead(String name);

	public void ConsoleEnd(String name);
}
