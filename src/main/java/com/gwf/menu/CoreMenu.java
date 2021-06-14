package com.gwf.menu;

import com.gwf.controller.*;
import com.gwf.entity.ToEmailEntity;
import com.gwf.menu.son.*;
import com.gwf.utils.EmailUtils;
import com.gwf.utils.GwfUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.gwf.entity.SystemInforEntity;



/**
 * @author gwf
 * @version 1.0
 * @date 2021/4/15 17:55
 * 订单启动控制菜单
 */
@Component
@Order(value=1)
public class CoreMenu implements CommandLineRunner  {

	private static final Logger log = LoggerFactory.getLogger(CoreMenu.class);

	@Autowired
	private GwfUtils gwfUtils;
	@Autowired
	private MemberRetrievalMenu memberRetrievalMenu;
	@Autowired
	private EmailUtils emailUtils;
	@Autowired
	private SystemInforMenu SystemInforMenu;


	@Autowired
    private AfterSalesMenu afterSalesMenu;
    @Autowired
    private NeedPendingMenu needPendingMenu;
    @Autowired
    private OrderInforMenu orderInforMenu;
    @Autowired
    private ShopPendingMenu shopPendingMenu;
    @Autowired
    private UpdateShopNameMenu updateShopNameMenu;



	@Override
	public void run(String... args) throws Exception {
		new Thread() {
			public void run() {
				SystemInforMenu.showSystemInfor();
			}
		}.start();
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
                resValue = orderInforMenu.OrderInforMenu();
            } else if ("待审商品".equals(name)) {
                resValue = needPendingMenu.startMenu();
            } else if ("改名店名".equals(name)) {
                resValue = updateShopNameMenu.UpdateShopNameMenu();
            } else if ("待审店铺".equals(name)) {
                resValue = shopPendingMenu.ShopPendingMenu();
            } else if ("售后订单".equals(name)) {
                resValue = afterSalesMenu.startMenu();

            }
			gwfUtils.ConsoleEnd(name);
			systemInforEntity.setStatus(true);
			return resValue;
		}
	}
	public void SendEmail(String emailSubject,String resValue,String[] EmailUrl,String name){
		ToEmailEntity toEmailEntity = new ToEmailEntity ();
		log.info("+++++++++++++++++++++++++++++++【邮件】++++++++-+++++++++++++++++++++");
		log.info(name+"【邮件】检索完毕，开始发送邮件");
		toEmailEntity.setSubject(emailSubject);
		toEmailEntity.setContent(resValue);
		toEmailEntity.setTos(EmailUrl);
		log.info(emailUtils.htmlEmail(toEmailEntity));
		log.info("---------------------------------END--------------------------------");
	}

}
