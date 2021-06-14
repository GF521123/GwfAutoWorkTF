package com.gwf.menu;

import com.gwf.entity.SystemInforEntity;
import com.gwf.entity.ToEmailEntity;
import com.gwf.menu.son.SystemInforMenu;
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

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


/**
 * @author gwf
 * @version 1.0
 * @date 2021/4/15 17:55
 * 订单启动控制菜单
 */
@Component
@Order(value=2)
public class GwfMenu implements CommandLineRunner {

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



    @Value("${ToEmail.gwfREmail}")
    private String[] gwfREmail;

    @Override
    public void run(String... args) throws Exception {
        int gwfsecond = 0;
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
                PersionMethod(gwfsecond);

            }
        }.start();
    }



    /**
     * 客服部使用方法
     * 实现商城监控
     */


    public void PersionMethod(int second) {
        ToEmailEntity toEmailEntity = new ToEmailEntity ();
        int tempNum = 0;
        String emailSubject="";
        String afterResString = "";
        String orderResString = "";
        String NeedPendResString = "";
        String ShopResString = "";
        String UpdateNameResString = "";
        if (gwfUtils.isRunTime()) {
            systemInforEntity.setCookie(httpClientUtils.getCookies());
            second++;
            String timeStr1 = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"));
            emailSubject = timeStr1 + " 第" + second + "次检索";
            log.info("------------------" + timeStr1 + " 执行第" + second + "次检索" + "-------------------");
            try {
                //待发订单
                synchronized (systemInforEntity) {
                    orderResString = coreMenu.getRestValue(systemInforEntity, "待发订单");
                    tempNum++;
                }
                //待审商品
                synchronized (systemInforEntity) {
                    NeedPendResString = coreMenu.getRestValue(systemInforEntity, "待审商品");
                    tempNum++;
                }

                //待改名字
                synchronized (systemInforEntity) {
                    UpdateNameResString = coreMenu.getRestValue(systemInforEntity, "改名店名");
                    tempNum++;
                }
                synchronized (systemInforEntity) {
                    ShopResString = coreMenu.getRestValue(systemInforEntity, "待审店铺");
                    tempNum++;
                }

                synchronized (systemInforEntity) {
                    afterResString = coreMenu.getRestValue(systemInforEntity, "售后订单");
                    tempNum++;
                }

                synchronized (systemInforEntity) {
                    if (tempNum != 5) {
                        systemInforEntity.wait();
                    }
                    String resValue = ShopResString + UpdateNameResString + NeedPendResString + afterResString + orderResString;
                    if (!"".equals(resValue)) {
                        coreMenu.SendEmail(emailSubject,resValue,gwfREmail,"GWF");
                    }
                }
                Thread.sleep(Integer.parseInt(systemInforEntity.getGwfTime()) * 60 * 1000);
                PersionMethod(second);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            try {
                Thread.sleep(Integer.parseInt(systemInforEntity.getGwfTime()) * 60 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            PersionMethod(second);

        }
    }


}
