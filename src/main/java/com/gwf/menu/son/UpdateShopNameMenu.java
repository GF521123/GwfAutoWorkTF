package com.gwf.menu.son;

import com.gwf.controller.UpdateShopNameController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

/**
 * @author gwf
 * @version 1.0
 * @date 2021/6/13 16:57
 */
public class UpdateShopNameMenu {
    private static final Logger log = LoggerFactory.getLogger(UpdateShopNameMenu.class);

    @Autowired
    private UpdateShopNameController updateShopNameController;

    public String UpdateShopNameMenu() {

        Map<String, String> resultMap = updateShopNameController.getUpdateShop();
        if (resultMap.get("status").equals("200")) {
            log.info("【改名店名】检索：" + resultMap.get("erInfor") + ",系统将自动退出");
            return resultMap.get("erInfor") + ",系统将自动退出";
        } else if ("0".equals(resultMap.get("totalRows"))) {
            log.info("【改名店名】检索：无改名店铺,不发送邮件通知");
            return "";
        } else {
            log.info("【改名店名】检索：" + resultMap.get("totalRows"));
            return "<div style='color:red'>【改名店名】汇总：" + resultMap.get("totalRows") + "</div>";
        }
    }
}
