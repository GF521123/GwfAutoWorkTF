package com.gwf.service.Imp;

import com.alibaba.fastjson.JSONObject;
import com.gwf.controller.Imp.ShopPendingControllerImp;
import com.gwf.service.ShopPendingService;
import com.gwf.utils.HttpClientUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author gwf
 * @version 1.0
 * @date 2021/6/13 16:59
 */
@Service
public class ShopPendingServiceImp implements ShopPendingService {
    private static final Logger log = LoggerFactory.getLogger(ShopPendingServiceImp.class);

    private String url = "https://www.tf0914.com/Shop/selectByKeyWorld";
    private String params = "";

    @Autowired
    private HttpClientUtils httpClientUtils;

    @Override
    public Map<String, String> getShopPending_Method() {
        params = "{\"entity\":{\"keyWord\":\"\",\"isNotExamineKey\":\"未审核\",\"infoIsNotExamineKey\":\"企业店\"},\"pageNum\":1,\"pageSize\":10,\"jcls\":\"Shop\"}";
        String res = httpClientUtils.Http_Reptile_Json(url, params);
        return getShopPending_Data(res);
    }

    public Map<String, String> getShopPending_Data(String res) {
        Map<String, String> resMap = new HashMap<>();
        JSONObject json_Data = (JSONObject) JSONObject.parse(res);
        String totalRows = json_Data.getString("totalRows");
        resMap.put("totalRows", totalRows);
        resMap.put("status", "0");
        return resMap;
    }
}
