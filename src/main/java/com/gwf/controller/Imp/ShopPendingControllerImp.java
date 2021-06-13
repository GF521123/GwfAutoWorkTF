package com.gwf.controller.Imp;

import com.alibaba.fastjson.JSONObject;
import com.gwf.controller.ShopPendingController;
import com.gwf.service.ShopPendingService;
import com.gwf.utils.HttpClientUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.HashMap;
import java.util.Map;

/**
 * @author gwf
 * @version 1.0
 * @date 2021/6/13 16:51
 */
@Controller
public class ShopPendingControllerImp implements ShopPendingController {

    @Autowired
    private ShopPendingService shopPendingService;

    @Override
    public Map<String, String> getShopPending() {
        return shopPendingService.getShopPending_Method();
    }
}
