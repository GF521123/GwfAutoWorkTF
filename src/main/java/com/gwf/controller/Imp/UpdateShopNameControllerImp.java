package com.gwf.controller.Imp;

import com.gwf.controller.UpdateShopNameController;
import com.gwf.service.UpdateShopNameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.Map;

/**
 * @author gwf
 * @version 1.0
 * @date 2021/6/13 16:58
 */
@Controller
public class UpdateShopNameControllerImp implements UpdateShopNameController {

    @Autowired
    private UpdateShopNameService updateShopNameService;

    @Override
    public Map<String, String> getUpdateShop() {
        return updateShopNameService.getUpdateShop_Menthod();
    }
}
