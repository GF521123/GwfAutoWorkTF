package com.gwf.utils.Imp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.*;

import com.gwf.entity.SystemInforEntity;
import com.gwf.utils.GwfUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONObject;
import com.gwf.utils.HttpClientUtils;
import org.springframework.stereotype.Repository;

/**
 * @author gwf
 * @version 1.0
 * @date 2021/4/15 17:55
 *
 */
@Repository
public class HttpClientUtilsImp implements HttpClientUtils {
	
	@Autowired
	private SystemInforEntity systemInforEntity;
	
	@Override
	public String getCookies() {
		//从配置文件中拼接测试的URL
        HttpPost httpPost = new HttpPost("https://www.tf0914.com/adminuser/login");
        String param = "{\"code\":\"18238621523\",\"password\":\"gwf18238621523.\",\"accountname\":\"\"}";

        // 获取cookies信息
//        CookieStore BasicCookieStore
        CookieStore store= new BasicCookieStore();
        CloseableHttpClient httpclient = HttpClients.custom().setDefaultCookieStore(store).build();



        // 建立一个NameValuePair数组，用于存储欲传送的参数
		httpPost.addHeader("Content-type","application/json; charset=utf-8");
		httpPost.setHeader("Accept", "application/json");
		httpPost.setEntity(new StringEntity(param, Charset.forName("UTF-8")));
		
		//逻辑
        try {
			CloseableHttpResponse response = httpclient.execute(httpPost);
			//登录返回值
	        HttpEntity httpEntity = response.getEntity();	
	        String loginResult = EntityUtils.toString(httpEntity, "utf8");
			JSONObject JsonRest = (JSONObject) JSONObject.parse(loginResult);

			String cookieUser =   JsonRest.getString("data");
//            System.out.println(cookieUser);
			cookieUser = URLEncoder.encode(cookieUser, "utf-8");
	      //登录cookie信息
	        List<Cookie> cookielist = store.getCookies();
	        String JSESSIONID = "";
	        String str1="",str2="",str3="";
	        List<String> strList = new ArrayList<String>();
	        if(cookielist.size()<2){

            }else{
                str1=""+cookielist.get(0);
                str2=""+cookielist.get(1);
                str3=""+cookielist.get(2);

                for(Cookie c: cookielist){
//                    System.out.println(cookielist.size()+" "+c.getValue());
//                    JESSIONID=c.getValue();
                    strList.add(c.getValue());
                }
            }
            Collections.sort(strList, new SortByLengthComparator());
            JSESSIONID = strList.get(0);

//	        for(Cookie c: cookielist){
//                System.out.println(cookielist.size()+" "+c.getValue());
//	            JSESSIONID=c.getValue();
//	        }
	       
	        cookieUser = cookieUser.replaceAll("%7B", "{");
	        cookieUser = cookieUser.replaceAll("%7D", "}");
	        cookieUser = cookieUser.replaceAll("%5D", "]");
	        cookieUser = cookieUser.replaceAll("%5B", "[");
	        cookieUser = cookieUser.replaceAll("%3A", ":");
	        
	        String resCookie = "positionUser=%22%u5E7F%u4E1C%22; "+
                    "SESSION="+ strList.get(2)+
                    "; JSESSIONID=" + JSESSIONID + "; sessionUser=" + cookieUser + "";
//            System.out.println(resCookie);
//            System.out.println(JESSIONID+" = "+GwfUtils.decryptBASE64(JESSIONID));

            return resCookie;
		} catch (IOException e) {
			e.printStackTrace();
			return "";
		}finally {
			try {
				httpclient.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block	
				e.printStackTrace();
			}
		}
		
	}
	@Override	
	public String HttpClient_Reptile_Default (String strURL, String params) {
        BufferedReader reader = null;
        try {
            URL url = new URL(strURL);// 创建连接
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setUseCaches(false);
            connection.setInstanceFollowRedirects(true);
            connection.setRequestMethod("POST"); // 设置请求方式
            connection.setRequestProperty("Cookie", systemInforEntity.getCookie());
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded"); // 设置发送数据的格式
            connection.setRequestProperty("User-Agent",
                    "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/87.0.4280.141 Safari/537.36 Edg/87.0.664.75"); // 设置接收数据的格式<br>、
            connection.connect();
            // 一定要用BufferedReader 来接收响应， 使用字节来接收响应的方法是接收不到内容的
            OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream(), "UTF-8"); // utf-8编码
            out.append(params);
            out.flush();
            out.close();
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
            String line;
            String res = "";
            while ((line = reader.readLine()) != null) {
                res += line;
            }
            reader.close();
            return res;
        } catch (IOException e) {
            e.printStackTrace();
            return "error";
        }
    }

    /*
     * application/json请求
     * 获取待发订单店铺信息
     */
	@Override
    public String Http_Reptile_Json(String strURL, String params) {
        BufferedReader reader = null;
        try {
            URL url = new URL(strURL);// 创建连接
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setUseCaches(false);
            connection.setInstanceFollowRedirects(true);
            connection.setRequestMethod("POST"); // 设置请求方式
            connection.setRequestProperty("Cookie", systemInforEntity.getCookie());
            connection.setRequestProperty("Content-Type", "application/json"); // 设置发送数据的格式
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/87.0.4280.141 Safari/537.36 Edg/87.0.664.75"); // 设置接收数据的格式<br>、
            connection.connect();
            OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream(), "UTF-8"); // utf-8编码
            out.append(params);
            out.flush();
            out.close();
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
            String line1;
            String res = "";
            while ((line1 = reader.readLine()) != null) {
                res += line1;
            }
            reader.close();
            return res;
        } catch (IOException e) {
            e.printStackTrace();
            return "error";
        }
    }
}
