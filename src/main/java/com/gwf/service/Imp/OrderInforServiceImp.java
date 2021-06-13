package com.gwf.service.Imp;

import com.alibaba.fastjson.JSONObject;
import com.gwf.service.OrderInforService;
import com.gwf.utils.HttpClientUtils;
import com.gwf.utils.OrderUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author gwf
 * @version 1.0
 * @date 2021/6/13 15:48
 */
@Service
public class OrderInforServiceImp implements OrderInforService {
    private static final Logger log = LoggerFactory.getLogger(OrderInforServiceImp.class);

    @Autowired
    private HttpClientUtils httpClientUtils;
    @Autowired
    private OrderUtils orderUtils;

    private String url = "https://www.tf0914.com/dingdan/findShopKeyWord";
    private String params = "";


    public Map<String, String> getOrderinfor_Method(){
        Map<String, String> errMap = new HashMap<String, String>();
        params = "keyword=&pagestart=1&status=f30&price1=&price2=&PageSize=30";
        String res = httpClientUtils.HttpClient_Reptile_Default(url, params);
        JSONObject json_Data = (JSONObject) JSONObject.parse(res);
        String json_status = json_Data.getString("status");
        if ("fail".equals(json_status)) {
            errMap.put("status", "200");
            errMap.put("erInfor", "cookies无效");
            return errMap;
        }
        return getOrderinfor_Date(res);
    }

    public Map<String, String> getOrderinfor_Date(String res) {
        Map<String, String> resultMap = new HashMap<String, String>();

        int tongjuNum = 0, all_order = 0;
        String resultString = orderUtils.getOrderList_menthod(res);
        try {
            tongjuNum = tongjuNum + orderUtils.getClass().getField("tongjuNum").getInt(orderUtils);
            JSONObject json_Data = (JSONObject) JSONObject.parse(res);
            JSONObject json_Order_Infor = (JSONObject) JSONObject.parse(json_Data.getString("data"));

            JSONObject json_Order_count = (JSONObject) JSONObject.parse(json_Order_Infor.getString("count"));
            all_order = (int) json_Order_count.get("f30");
            int pageNums = (int) json_Order_Infor.get("allPagesNum");
            for (int i = 2; i <= pageNums; i++) {
                params = "keyword=&pagestart=" + i + "&status=f30&price1=&price2=&PageSize=30";
                res = httpClientUtils.HttpClient_Reptile_Default(url, params);
                resultString += orderUtils.getOrderList_menthod(res);
                tongjuNum = tongjuNum + orderUtils.getClass().getField("tongjuNum").getInt(orderUtils);
            }
        } catch (Exception e) {
            resultMap.put("status", "200");
            resultMap.put("erInfor", "运行错误");
            e.printStackTrace();
            return resultMap;
        }
        resultString = "<div style='color:red;'>【待发订单】汇总：" + tongjuNum + "/" + all_order + "(当前无效数据55条)" + "</div><br>" + resultString;
        resultMap.put("searchValue", tongjuNum + "/" + all_order);
        resultMap.put("tongjuNum", "" + tongjuNum);
        resultMap.put("htmlEmailValue", resultString);
        resultMap.put("status", "0");
        resultMap.put("erInfor", "");
        return resultMap;
    }

}
