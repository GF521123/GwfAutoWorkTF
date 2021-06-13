package com.gwf.menu.son;

import com.gwf.entity.SystemInforEntity;
import com.gwf.entity.SystemInforEntity;
import com.gwf.utils.GwfUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;


/**
 * @author gwf
 * @version 1.0
 * @date 2021/4/15 17:55
 */
@Component
@Repository
public class SystemInforMenu{
	private static final Logger log = LoggerFactory.getLogger(SystemInforMenu.class);

	@Autowired
	private GwfUtils gwfUtils;
	@Autowired
	private SystemInforEntity systemInforEntity;

	public void showSystemInfor() {
		log.info("系统初始化.....");

		setSysteInfor();
		String sysInforString=getSystemInforByfile()+"\n\n--------------当前运行环境信息----------------\n";
		sysInforString=sysInforString + "获取系统运行环境信息...";
		sysInforString=sysInforString + "操作系统：" + System.getProperty("os.name")+"\n";// 操作系统的名称
		sysInforString=sysInforString + "系统版本：" + System.getProperty("os.version")+"\n";//  操作系统的版本号
		sysInforString=sysInforString + "系统架构：" + System.getProperty("os.arch")+"\n";//  操作系统的架构
		sysInforString=sysInforString + "账户名称：" + System.getProperty("user.name")+"\n";// user.name 用户的账户名称
		sysInforString=sysInforString + "jre版本：" + System.getProperty("java.version")+"\n";// 环境版本号
		sysInforString=sysInforString + "最大内存：" + Runtime.getRuntime().maxMemory()/1024/1024+"M\n";
		sysInforString=sysInforString + "可用内存：" + Runtime.getRuntime().totalMemory()/1024/1024+"M\n";
		sysInforString=sysInforString + "剩余内存：" + Runtime.getRuntime().freeMemory()/1024/1024+"M\n";
		sysInforString +=     "------------------------------------------\n";
		sysInforString=sysInforString + "设置参数.......\n";
		sysInforString=sysInforString +"设定客服待审会员检测时间间隔：" + systemInforEntity.getKefuTime() + "分钟\n";
		sysInforString=sysInforString +"设定商城信息检测时间间隔：" + systemInforEntity.getGwfTime()+ "分钟\n";
		sysInforString=sysInforString +"设定参数对象可用：" + systemInforEntity.getStatus()+ "\n";
		sysInforString=sysInforString + "设置参数完毕\n";
		log.info(sysInforString);
		log.info("系统初始化结束");
	}
	public String getSystemInforByfile(){
		return ("\n" + gwfUtils.fileReadSelect("static/about.txt"));
	}
	public void setSysteInfor(){
		synchronized(systemInforEntity) {
			systemInforEntity.setKefuTime("10");
			systemInforEntity.setGwfTime("60");
			systemInforEntity.setStatus(true);
			systemInforEntity.notifyAll();
		}
	}

}
