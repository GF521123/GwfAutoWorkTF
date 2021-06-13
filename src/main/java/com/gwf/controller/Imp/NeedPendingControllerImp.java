package com.gwf.controller.Imp;

import com.gwf.controller.NeedPendingController;
import com.gwf.service.NeedPendingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * @author gwf
 * @version 1.0
 * @date 2021/6/13 17:08
 */
@Controller
public class NeedPendingControllerImp implements NeedPendingController {
    @Autowired
    private NeedPendingService needPendingService;
    @Override
    public String getNeedPending() {
        return needPendingService.getNeedPending_method();
    }
}
