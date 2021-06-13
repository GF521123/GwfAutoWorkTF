package com.gwf.utils.Imp;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gwf.utils.GwfUtils;
import com.gwf.utils.HttpClientUtils;
import com.gwf.utils.OrderUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author gwf
 * @version 1.0
 * @date 2021/6/13 16:24
 */
@Repository
public class OrderUtilsImp implements OrderUtils {
    public int tongjuNum;
    private String TempTime = "";
    @Autowired
    private HttpClientUtils httpClientUtils;
    @Autowired
    private GwfUtils gwfUtils;

    /*
     * 格式化数据
     */
    @Override
    public String getOrderList_menthod(String res) {
        tongjuNum = 0;
        JSONObject json_Data = (JSONObject) JSONObject.parse(res);
        JSONObject json_Order_Infor = (JSONObject) JSONObject.parse(json_Data.getString("data"));
        JSONArray json_Order_List = json_Order_Infor.getJSONArray("list");
        if ("".equals(json_Order_List) || json_Order_List == null) {
            return "";
        }
        return getOrderList_Data(json_Order_List);
    }

    /*
     * 遍历处理
     */
    public String getOrderList_Data(JSONArray json_Order_List) {
        if ("".equals(json_Order_List) || json_Order_List == null) {
            return "";
        } else {
            String emailValueString = "";
            for (int i = 0; i < json_Order_List.size(); i++) {
                JSONObject order_infor = (JSONObject) JSONObject.parse(json_Order_List.getString(i));
                String date_time = ((String) order_infor.get("stringCreateTime"));
                if (!timeSelect(date_time)) {
                    tongjuNum++;
                    JSONObject order_shop = (JSONObject) JSONObject
                            .parse(order_infor.getJSONArray("goodlist").getString(0));
                    JSONObject order_buy = (JSONObject) JSONObject.parse(order_infor.getString("address"));
                    emailValueString = emailValueString + "<div><div><span style='color:red'>距今：" + TempTime + " </span>下单时间:" + date_time + "</div>\n";
                    emailValueString = emailValueString + "<div style='color:darkcyan'>"
                            + order_shop.get("aidname") + "(" + getOrderJsonByshopIphone((String) order_shop.get("aidname")) + ")</div> ";
                    emailValueString = emailValueString + "<div style='color:black'>"
                            + order_buy.get("consignee") + "(" + order_buy.get("telephone") + ")" + order_buy.get("province") + order_buy.get("city")
                            + order_buy.get("district") + order_buy.get("street") + order_buy.get("detailedAddress") + "</div> ";
                    emailValueString = emailValueString + "\n\n<br></div>";
                }
            }
            return emailValueString;
        }
    }

    public String getOrderJsonByshopIphone(String shopName) {
        String urlString = "https://www.tf0914.com/Shop/selectByKeyWorld";
        String params = "{\"entity\":{\"keyWord\":\"" + shopName
                + "\",\"isNotExamineKey\":null,\"infoIsNotExamineKey\":null},\"pageNum\":1,\"pageSize\":10,\"jcls\":\"Shop\"}";
        String resByShopName = httpClientUtils.Http_Reptile_Json(urlString, params);
        return getOrderIphoneByJson(resByShopName);
    }

    public String getOrderIphoneByJson(String orderJson) {
        JSONObject json_Data = (JSONObject) JSONObject.parse(orderJson);
        JSONArray shopByName = json_Data.getJSONArray("data");
        if (shopByName.size() == 1) {
            JSONObject shop_info = (JSONObject) shopByName.get(0);
            JSONObject userEntity = (JSONObject) JSONObject.parse(shop_info.getString("userEntity"));
            return userEntity.getString("phone");
        } else if (shopByName.size() < 1) {
            return "未找到";
        } else {
            return "找到多个，无法确定";
        }
    }

    /*
     * 订单条件处理
     */
    public boolean timeSelect(String time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date systemTimeDate = new Date();
            Date order_timeDate = format.parse(time);
            int order_Hours = order_timeDate.getHours();
            if (order_Hours >= 6 && order_Hours < 16) {
//				每日早上6点至16点之间产生（完成支付）的订单,6小时内发货
                return JoinTimeSelect(1, order_timeDate, systemTimeDate);
            } else {
                // 16点至次日6点产生的订单18小时内发货
                return JoinTimeSelect(2, order_timeDate, systemTimeDate);
            }
        } catch (ParseException e) {
            return false;
        }
    }

    public boolean JoinTimeSelect(int select, Date date1, Date date2) {
        TempTime = "";
        int days = (int) ((date2.getTime() - date1.getTime()) / (1000 * 3600 * 24));
        int temp = (int) ((date2.getTime() - date1.getTime()) / 1000 / 60);// 分
        int fen = (int) temp % 60;
        temp /= 60;// 时
        int shi = (int) temp % 24;
        if (select == 1) {
            if (days == 0 && shi < 6) {
                return true;
            } else {
                TempTime = days + "天" + shi + "时" + fen + "分 ";
                return false;
            }
        } else {
            if (days == 0 && shi < 18) {
                return true;
            } else {
                TempTime = days + "天" + shi + "时" + fen + "分 ";
                return false;
            }
        }
    }

    /**
     * 通过时间秒毫秒数判断两个时间的间隔
     *
     * @param date1
     * @param date2
     * @return
     */
    public String differentDaysByMillisecond(Date date1, Date date2) {
        int temp = (int) ((date2.getTime() - date1.getTime()) / 1000 / 60);// 分
        int days = (int) ((date2.getTime() - date1.getTime()) / (1000 * 3600 * 24));

        int fen = (int) temp % 60;
        temp /= 60;// 时
        int shi = (int) temp % 24;
        return (days + " 天 " + shi + " 时 " + fen + " 分");
    }


}
