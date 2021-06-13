package com.gwf.service.Imp;

import com.alibaba.fastjson.JSONObject;
import com.gwf.service.NeedPendingService;
import com.gwf.utils.HttpClientUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author gwf
 * @version 1.0
 * @date 2021/6/13 17:11
 */
@Service
public class NeedPendingServiceImp implements NeedPendingService {
    private static final Logger log = LoggerFactory.getLogger(NeedPendingServiceImp.class);

    private String url = "https://www.tf0914.com/goods/selectByExampleAndPageDetail";
    private String params = "{\"entity\":{\"status\":\"待审核\"},\"pageNum\":1,\"pageSize\":10,\"jcls\":\"Goods\"}";
    private int totalRows = 0;

    @Autowired
    private HttpClientUtils httpClientUtils;

    public String getNeedPending_method() {
        boolean sta = CommodityJson(httpClientUtils.Http_Reptile_Json(url, params));
        if (sta) {
            return "" + totalRows;
        } else {
            return "";

        }
    }

    public boolean CommodityJson(String httpClientJson) {
        JSONObject json_Data = (JSONObject) JSONObject.parse(httpClientJson);
        totalRows = (int) JSONObject.parse(json_Data.getString("totalRows"));
        if (totalRows == 0) {
            return false;
        } else {
            return true;
        }
    }

}
