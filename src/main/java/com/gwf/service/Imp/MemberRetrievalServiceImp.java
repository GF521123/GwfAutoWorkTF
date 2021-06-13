package com.gwf.service.Imp;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.gwf.service.MemberRetrievalService;
import com.gwf.utils.HttpClientUtils;

/**
 * @author gwf
 * @version 1.0
 * @date 2021/4/15 17:55
 *
 */
@Service
public class MemberRetrievalServiceImp implements MemberRetrievalService {
	
	@Autowired
	private  HttpClientUtils httpClientUtils;
	
	/**
	 * 获得检索结果
	 */
	public Map<String ,String> getMemberRetrievalResult(){
		Map<String, String> restMap = new HashMap<String, String>(); 
		//设置参数
		String url = "https://www.tf0914.com/Vip/vipOrderSearch";
		String params = "registername=&registerphone=&recomname=&recommend=&organname=&orstatus=%E5%AE%A1%E6%A0%B8%E4%B8%AD&pagenum=1";

		//请求
		String res = httpClientUtils.HttpClient_Reptile_Default(url, params);
		JSONObject json_Data = (JSONObject) JSONObject.parse(res);
		String status =   json_Data.getString("status");
		if("success".equals(status)) {
			String totalRows =   json_Data.getString("totalRows");
			restMap.put("status", "0");
			restMap.put("totalRows", totalRows);
//			restMap.put("html", "<div style='color:red;font-size:20px;'>【待审会员】检索："+totalRows+"</div>");
		}else {
			restMap.put("status", "-1");
			restMap.put("totalRows", "-1");
//			restMap.put("html", "<div style='color:red;font-size:20px;'>【待审会员】检索："+totalRows+"</div>");
		}
		return restMap;
		
	}
}
