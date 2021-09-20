package com.gwf.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;

/**
 * @author gwf
 * @version 1.0
 * @date 2021/6/15 11:24
 */
public interface PageController {
//    public String loading();
    public JSONObject CheckPendingMember();
}
