package com.gwf.menu.son;

import com.gwf.controller.OrderInforController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

/**
 * @author gwf
 * @version 1.0
 * @date 2021/6/13 16:35
 * 订单启动控制菜单
 */
public class OrderInforMenu {
    private static final Logger log = LoggerFactory.getLogger(OrderInforMenu.class);

    @Autowired
    private OrderInforController orderInforController;

    public String OrderInforMenu(){

        Map<String, String> resultMap = orderInforController.getOrderinfor_Method();
        if (resultMap.get("status").equals("200")) {
            log.info("【待发订单】检索：" + resultMap.get("erInfor") + ",系统将自动退出");
            return resultMap.get("erInfor") + ",系统将自动退出";
        } else if ("0".equals(resultMap.get("tongjuNum"))) {
            log.info("【待发订单】检索：无待发商品,不发送邮件通知");
            return "";
        } else {
            log.info("【待发订单】检索：" + resultMap.get("searchValue") + ",");
            return resultMap.get("htmlEmailValue");
        }
    }

}
