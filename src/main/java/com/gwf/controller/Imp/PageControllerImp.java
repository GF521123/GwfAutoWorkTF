package com.gwf.controller.Imp;

import com.alibaba.fastjson.JSONObject;
import com.gwf.controller.PageController;
import com.gwf.entity.SystemInforEntity;
import com.gwf.service.MemberRetrievalService;
import com.gwf.utils.HttpClientUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * @author gwf
 * @version 1.0
 * @date 2021/6/15 11:24
 */
@Controller
public class PageControllerImp implements PageController {

    @Autowired
    private MemberRetrievalService memberRetrievalService;
    @Autowired
    private HttpClientUtils httpClientUtils;
    @Autowired
    private SystemInforEntity systemInforEntity;

//    @ResponseBody
    @RequestMapping("/")
    public String loading(){

        return "index";
    }
    @ResponseBody
    @RequestMapping("/CheckPendingMember")
    public JSONObject CheckPendingMember(){
        systemInforEntity.setCookie(httpClientUtils.getCookies());
        Map<String ,String> result = memberRetrievalService.getMemberRetrievalResult();
//        restMap.put("status", "0");
//        restMap.put("totalRows", totalRows);
//        model.addAttribute ("totalRows",result.get("totalRows"));
//        model.addAttribute("status",result.get("status").equals("0")?"查询完毕":"接口异常");
        JSONObject json = new JSONObject();
        json.put("totalRows",result.get("totalRows"));
        json.put("status",result.get("status"));
        return json;
    }




}
