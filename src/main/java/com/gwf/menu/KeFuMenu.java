package com.gwf.menu;

import com.gwf.entity.ToEmailEntity;
import com.gwf.menu.son.*;
import com.gwf.utils.EmailUtils;
import com.gwf.utils.GwfUtils;
import com.gwf.utils.HttpClientUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.gwf.entity.SystemInforEntity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


/**
 * @author gwf
 * @version 1.0
 * @date 2021/4/15 17:55
 * 订单启动控制菜单
 */
@Component
@Order(value=8)
public class KeFuMenu  implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(CoreMenu.class);

    @Autowired
    private SystemInforMenu SystemInforMenu;
    @Autowired
    private SystemInforEntity systemInforEntity;
    @Autowired
    private GwfUtils gwfUtils;
    @Autowired
    private HttpClientUtils httpClientUtils;
    @Autowired
    private EmailUtils emailUtils;


    @Autowired
    private CoreMenu coreMenu;


    @Value("${ToEmail.topKeFu}")
    private String[] topKeFu;

    @Override
    public void run(String... args) throws Exception {
        int second = 0;
        new Thread() {
            public void run() {
                synchronized (systemInforEntity) {
                    if (null == systemInforEntity.getGwfTime()||null == systemInforEntity.getKefuTime()) {
                        try {
                            systemInforEntity.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
                customerService(second);

            }
        }.start();
    }

    /**
     * 客服部使用方法
     * 实现商城监控
     */

    public void customerService( int timesCount ) {
        ToEmailEntity toEmailEntity = new ToEmailEntity ();
        int tempNum = 0;

        String emailSubject="";
        String memberResString="";
        if (gwfUtils.isRunTime()) {
            systemInforEntity.setCookie(httpClientUtils.getCookies());
            String timeStr1 = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"));
            timesCount++;
            emailSubject = timeStr1 + " 第" + timesCount + "次检索";
            log.info("------------------"+timeStr1 + " 执行第" + timesCount	 + "次检索"+"-------------------");

            try {
                synchronized (systemInforEntity) {
                    memberResString = coreMenu.getRestValue(systemInforEntity,"待审会员");
                    tempNum++;
                }

                synchronized (systemInforEntity) {
                    if (tempNum != 1) {
                        systemInforEntity.wait();
                    }
                    String endstr = "<hr><div style='font-size:12px;'>GWF个人开发用于检测同芙会员审核</div>"
                            + "<div style='font-size:12px;'>若出现异常，请与本人联系</div>"
                            + "<div style='font-size:12px;'>QQ：2278417331</div>"
                            + "<div style='font-size:12px;'>微信：gwf521123</div>"
                            + "<div style='font-size:12px;'>系统暂停维护，非异常请勿扰</div>";
                    String resValue = memberResString ;
                    if (!"".equals(resValue)) {
                        resValue += endstr;
                        coreMenu.SendEmail(emailSubject,resValue,topKeFu,"KeFu");
                    }
                }

                Thread.sleep(Integer.parseInt(systemInforEntity.getKefuTime()) * 60 * 1000);
                customerService(timesCount);
            }catch (InterruptedException e) {
                try {
                    Thread.sleep(Integer.parseInt(systemInforEntity.getKefuTime()) * 60 * 1000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                customerService(timesCount);
            }

        }

    }


}
