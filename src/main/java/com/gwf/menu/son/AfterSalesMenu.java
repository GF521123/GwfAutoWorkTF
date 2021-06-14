package com.gwf.menu.son;

import com.gwf.controller.AfterSalesController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * @author gwf
 * @version 1.0
 * @date 2021/6/14 10:07
 */
@Repository
public class AfterSalesMenu {
    private static final Logger log = LoggerFactory.getLogger(AfterSalesMenu.class);

    @Autowired
    private AfterSalesController afterSalesController;

    public String startMenu() {
        Map<String, String> ShopAfterInfor = afterSalesController.getAfterSales();
        if ("0".equals(ShopAfterInfor.get("tongjuNum"))) {
            log.info("【售后商品】检索：无需售后订单需要操作");
            return "";
        } else {
            log.info("【售后商品】检索：" + ShopAfterInfor.get("searchValue"));
            return ShopAfterInfor.get("htmlEmailValue");
        }
    }
}
