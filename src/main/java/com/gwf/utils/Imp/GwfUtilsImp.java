package com.gwf.utils.Imp;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Calendar;

import com.gwf.entity.SystemInforEntity;
import org.apache.logging.log4j.core.config.plugins.util.ResolverUtil.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Repository;

import com.gwf.utils.GwfUtils;

/**
 * @author gwf
 * @version 1.0
 * @date 2021/4/15 17:55
 *
 */
@Repository
public class GwfUtilsImp implements GwfUtils{
	private static final Logger log =  LoggerFactory.getLogger(GwfUtilsImp.class);
	/**
	 * 文件读取判断 
	 * readCokies 开发环境
	 * readFileOnce 运行环境
	 */
	@Override
	public String fileReadSelect(String fileName) {
		File file = new File(Test.class.getClassLoader().getResource(fileName).getPath());
		if (file.exists()) {
			return readCokies(fileName);
		} else {
			return readFileOnce(fileName);
		}
	}

//	文件操作
	@Override
	public String readCokies(String fileName) {
		try {
			String path = Test.class.getClassLoader().getResource(fileName).getPath();
			FileInputStream fis = new FileInputStream(path);
			BufferedInputStream bis = new BufferedInputStream(fis);
			String content = "";
			// 自定义缓冲区
			byte[] buffer = new byte[10240];
			int flag = 0;
			while ((flag = bis.read(buffer)) != -1) {
				content += new String(buffer, 0, flag, "utf-8");
			}
			// 关闭的时候只需要关闭最外层的流就行了
			bis.close();
			return content;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * 一次性读取全部文件数据
	 *
	 * @param strFile
	 */
	@Override
	public String readFileOnce(String strFile) {
		String resStr = "";
		try {
			InputStream is = new ClassPathResource("classpath:" + strFile).getInputStream();
			int iAvail = is.available();
			byte[] bytes = new byte[iAvail];
			is.read(bytes);
//            logger.info("文件内容:\n" + new String(bytes));
			resStr += new String(bytes, "utf-8");
			is.close();
			return resStr;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}



	public boolean isRunTime(){
//		return true;
		Calendar cal = Calendar.getInstance();
		int hour=cal.get(Calendar.HOUR_OF_DAY);//获取日
		int week = cal.get(Calendar.DAY_OF_WEEK)-1;
		log.info("星期:"+week);
		if(week == 0) {
			log.info("不在检索时间范围（星期日）内.....暂停检索");
			return false;
		}
		if ((hour >= 9 && hour < 12 )||((hour >= 14 )&&hour <= 17)) {
			return true;
		}
		if((hour >= 13 &&hour <= 14)&&cal.get(Calendar.MINUTE)>30) {
			return true;
		}
		log.info("不在检索时间范围（9~12 1.5~18）内.....暂停检索");
		return false;
	}

	public void ConsoleHead(String name){
		log.info("*************************【"+name+"】**********************************");
//		log.info("                                                 ");
//		log.info("                         【"+name+"】                                  ");
		log.info("【"+name+"】检索激活，待审商品监控模块进入检索状态");
	}

	public void ConsoleEnd(String name){
		log.info("【"+name+"】检索结束，待审商品监控模块进入休眠状态");
//		log.info("-----------------------------------------------------------------------");
	}


}
