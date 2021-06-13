package com.gwf.service.Imp;

import com.alibaba.fastjson.JSONObject;
import com.gwf.service.UpdateShopNameService;
import com.gwf.utils.HttpClientUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author gwf
 * @version 1.0
 * @date 2021/6/13 17:03
 */
@Service
public class UpdateShopNameServiceImp implements UpdateShopNameService {
    private String url = "https://www.tf0914.com/shopMsgUpdateLog/searchShopMsgUpdateLogVo";
    private String params = "";

    @Autowired
    private HttpClientUtils httpClientUtils;

    public Map<String, String> getUpdateShop_Menthod() {
        params = "{\"entity\":{\"keyWord\":\"\",\"isNotExamineKey\":\"未审核\",\"infoIsNotExamineKey\":\"企业店\"},\"pageNum\":1,\"pageSize\":10,\"jcls\":\"Shop\"}";
        String res = httpClientUtils.Http_Reptile_Json(url, params);
        return getUpdateShop_Date(res);
    }

    public Map<String, String> getUpdateShop_Date(String res) {
        Map<String, String> resMap = new HashMap<>();
        JSONObject json_Data = (JSONObject) JSONObject.parse(res);
        String totalRows = json_Data.getString("totalRows");
        resMap.put("status", "0");
        resMap.put("totalRows", totalRows);
        return resMap;
    }
}
