package com.gwf.menu.son;

import com.gwf.controller.ShopPendingController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

/**
 * @author gwf
 * @version 1.0
 * @date 2021/6/13 16:49
 */
public class ShopPendingMenu {
    private static final Logger log = LoggerFactory.getLogger(ShopPendingMenu.class);


    @Autowired
    private ShopPendingController shopPendingController;

    public String ShopPendingMenu(){
        Map<String, String> resultMap = shopPendingController.getShopPending();
        if (resultMap.get("status").equals("200")) {

            log.info("【待审店铺】检索：" + resultMap.get("erInfor") + ",系统将自动退出");
            return resultMap.get("erInfor") + ",系统将自动退出";
        } else if ("0".equals(resultMap.get("totalRows"))) {
            log.info("【待审店铺】检索：无待审店铺,不发送邮件通知");
            return "";
        } else {
            log.info("【待审店铺】检索：" + resultMap.get("totalRows"));
            return "<div style='color:red'>【待审店铺】汇总：" + resultMap.get("totalRows") + "</div>";
        }
    }
}
