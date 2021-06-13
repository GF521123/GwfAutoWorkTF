package com.gwf.controller.Imp;

import com.gwf.controller.OrderInforController;
import com.gwf.service.OrderInforService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.Map;

/**
 * @author gwf
 * @version 1.0
 * @date 2021/6/13 15:47
 */
@Controller
public class OrderInforControllerImp implements OrderInforController {
    @Autowired
    private OrderInforService orderInforService;
    public Map<String, String> getOrderinfor_Method(){
        return orderInforService.getOrderinfor_Method();
    }
}
