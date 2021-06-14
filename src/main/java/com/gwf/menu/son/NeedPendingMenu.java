package com.gwf.menu.son;

import com.gwf.controller.NeedPendingController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author gwf
 * @version 1.0
 * @date 2021/6/14 10:08
 */
@Repository
public class NeedPendingMenu {
    private static final Logger log = LoggerFactory.getLogger(NeedPendingMenu.class);

    @Autowired
    private NeedPendingController needPendingController;

    public String startMenu() {
        String needPendingInforNum = needPendingController.getNeedPending();
        if ("".equals(needPendingInforNum)) {
            return "";
        } else {
            log.info("【待审商品】检索：" + needPendingInforNum);
            return "<div style='color:red'>【待审商品】汇总：" + needPendingInforNum + "</div>";
        }
    }
}
