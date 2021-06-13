package com.gwf.controller.Imp;

import com.gwf.controller.AfterSalesController;
import com.gwf.service.AfterSalesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.Map;

/**
 * @author gwf
 * @version 1.0
 * @date 2021/6/13 17:08
 */
@Controller
public class AfterSalesControllerImp implements AfterSalesController {
    @Autowired
    private AfterSalesService afterSalesService;

    @Override
    public Map<String, String> getAfterSales() {
        return afterSalesService.getafterSales_method();
    }
}
