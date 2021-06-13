package com.gwf.menu;

import com.gwf.entity.ToEmailEntity;
import com.gwf.menu.son.MemberRetrievalMenu;
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

import com.gwf.menu.son.SystemInforMenu;
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
@Order(value=1)
public class CoreMenu  implements CommandLineRunner {

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
	private MemberRetrievalMenu memberRetrievalMenu;
	@Autowired
	private EmailUtils emailUtils;




    @Value("${ToEmail.topKeFu}")
	private String[] topKeFu;
	@Value("${ToEmail.gwfREmail}")
	private String[] gwfREmail;

	@Override
	public void run(String... args) throws Exception {

		new Thread() {
			public void run() {
				SystemInforMenu.showSystemInfor();
				synchronized (systemInforEntity) {
					if (null == systemInforEntity.getGwfTime()||null == systemInforEntity.getKefuTime()) {
						try {
							systemInforEntity.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
//				PersonalUse( );
				customerService(  );

			}
		}.start();
	}


	/**
	 * 个人自用方法
	 * 实现商城监控
	 */

	public void PersonalUse(  ) {
        ToEmailEntity toEmailEntity = new ToEmailEntity ();

        String emailSubject="";
        String afterResString = "";
        String orderResString = "";
        String NeedPendResString = "";
        String ShopResString = "";
        String UpdateNameResString = "";
        int timesCount = 0;
        if (gwfUtils.isRunTime()) {
            systemInforEntity.setCookie(httpClientUtils.getCookies());
            systemInforEntity.setSyncNum(0);
            timesCount++;
            String timeStr1 = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"));
            emailSubject = timeStr1 + " 第" + timesCount + "次检索";
            log.info("------------------"+timeStr1 + " 执行第" + timesCount	 + "次检索"+"-------------------");
            try {
                synchronized (systemInforEntity) {
                    orderResString = getRestValue(systemInforEntity, "待发订单");
                }
                //待审商品
                synchronized (systemInforEntity) {
                    NeedPendResString = getRestValue(systemInforEntity, "待审商品");
                }

                //待改名字
                synchronized (systemInforEntity) {
                    UpdateNameResString = getRestValue(systemInforEntity, "改名店名");
                }
                synchronized (systemInforEntity) {
                    ShopResString = getRestValue(systemInforEntity, "待审店铺");
                }

                synchronized (systemInforEntity) {
                    afterResString = getRestValue(systemInforEntity, "售后订单");
                }

                synchronized (systemInforEntity) {
                    if (systemInforEntity.getSyncNum() != 5) {
                        systemInforEntity.wait();
                    }
                    String resValue = ShopResString + UpdateNameResString + NeedPendResString + afterResString + orderResString;
                    if (!"".equals(resValue)) {
                        log.info("+++++++++++++++++++++++++++++++【邮件】++++++++-+++++++++++++++++++++");
                        log.info("【邮件】检索完毕，开始发送邮件");
                        toEmailEntity.setSubject(emailSubject);
                        toEmailEntity.setContent(resValue);
                        toEmailEntity.setTos(gwfREmail);
                        log.info(emailUtils.htmlEmail(toEmailEntity));
                        log.info("---------------------------------END--------------------------------");
                    }
                }
                Thread.sleep(Integer.parseInt(systemInforEntity.getGwfTime()) * 60 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

	}


	/**
	 * 客服部使用方法
	 * 实现商城监控
	 */

	public void customerService(  ) {
        ToEmailEntity toEmailEntity = new ToEmailEntity ();

                String emailSubject="";
        int timesCount = 0;
        String memberResString="";
        if (gwfUtils.isRunTime()) {
            systemInforEntity.setCookie(httpClientUtils.getCookies());
            systemInforEntity.setSyncNum(0);
            String timeStr1 = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"));
            timesCount++;
            emailSubject = timeStr1 + " 第" + timesCount + "次检索";
            log.info("------------------"+timeStr1 + " 执行第" + timesCount	 + "次检索"+"-------------------");

            try {
                synchronized (systemInforEntity) {
                    memberResString = getRestValue(systemInforEntity,"待审会员");
                }


                synchronized (systemInforEntity) {
                    if (systemInforEntity.getSyncNum() != 1) {
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
                        log.info("+++++++++++++++++++++++++++++++【邮件】++++++++-+++++++++++++++++++++");
                        log.info("【邮件】检索完毕，开始发送邮件");
                        toEmailEntity.setSubject(emailSubject);
                        toEmailEntity.setContent(resValue);
                        toEmailEntity.setTos(topKeFu);
                        log.info(emailUtils.htmlEmail(toEmailEntity));
                        log.info("---------------------------------END--------------------------------");
                    }
                }

                Thread.sleep(Integer.parseInt(systemInforEntity.getKefuTime()) * 60 * 1000);
                customerService();
            }catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

	}


	public String getRestValue(SystemInforEntity systemInforEntity,String name)  {
		synchronized (systemInforEntity){

			if (!systemInforEntity.getStatus()) {
				try {
					systemInforEntity.wait();
				}catch (Exception e){

				}
			}
			String resValue ="";
			systemInforEntity.setStatus(false);
			gwfUtils.ConsoleHead(name);
			if("待审会员".equals(name)) {
				resValue = memberRetrievalMenu.MemberRetrievalMenu();
			}else if ("待发订单".equals(name)) {
                resValue = orderInforMenuStart.startMenu();
            } else if ("待审商品".equals(name)) {
                resValue = needPendingMenuStart.startMenu();
            } else if ("改名店名".equals(name)) {
                resValue = updateShopNameMenuStart.startMenu();
            } else if ("待审店铺".equals(name)) {
                resValue = shopPendingMenuStart.startMenu();
            } else if ("售后订单".equals(name)) {
                resValue = afterSalesMenuStart.startMenu();

            }
			systemInforEntity.setSyncNum(systemInforEntity.getSyncNum() + 1);
			gwfUtils.ConsoleEnd(name);
			systemInforEntity.setStatus(true);
			return resValue;
		}
	}
    
}
