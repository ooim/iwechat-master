package com.izdodo.cloud.test;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.izdodo.cloud.test.GetMacAddress.getMacAddress;

/**
 * Created by zfy on 2017/7/25.
 */
@Controller
public class Test {

    @Autowired
    RestTemplate restTemplate;

    public static Map timeout = new HashMap();

    @RequestMapping("/getcode")
    public String getCode(HttpServletRequest request, HttpServletResponse response){

        try {
            response.sendRedirect("https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxbf2edb46c0bcacd0&redirect_uri="+ URLEncoder.encode("http://icloud.izdodo.cn:8080/getopenid","UTF-8")+"&response_type=code&scope=snsapi_userinfo&state=1#wechat_redirect");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping("/getopenid")
    public String getIndex(HttpServletRequest request, HttpServletResponse response){
        String code = request.getParameter("code");
        try {
            System.err.println(code+"====code");
            ResponseEntity<Map> gettoken = restTemplate.getForEntity("https://api.weixin.qq.com/sns/oauth2/access_token?appid=wxbf2edb46c0bcacd0&secret=c9ec485ace764b397f9490f743d8e819&code="+code+"&grant_type=authorization_code",Map.class);
            Map map = (Map) JSON.toJSON(gettoken.getBody());
            System.err.println(gettoken.getBody());
            String openid = map.get("openid").toString();
            System.err.println(openid+"====openid");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "select/index";
    }
    @RequestMapping("/get")
    public String getTick(HttpServletRequest request, HttpServletResponse response){

        if (timeout.get("sign")!=null&&(System.currentTimeMillis()/1000-Long.parseLong(timeout.get("time").toString())/1000<7100)){
        }else{
            timeout.put("time",System.currentTimeMillis());
            getSing2();
        }
        return timeout.get("sign").toString();

    }

    public void getSing(){
        ResponseEntity<Map> gettoken = restTemplate.getForEntity("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wxc5b0f2991f5c6fdb&secret=d4b1381ee9e2d984965cf5a775b2006a",Map.class);
        Map map = (Map) JSON.toJSON(gettoken.getBody());
        System.err.println(gettoken.getBody());
        String access_token = map.get("access_token").toString();
        ResponseEntity<Map> getticket = restTemplate.getForEntity("https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token="+access_token+"&type=jsapi",Map.class);
        Map ticket = (Map) JSON.toJSON(getticket.getBody());
        System.err.println(getticket.getBody());
        String ticketnew = ticket.get("ticket").toString();

        String url = "http://icloud.izdodo.cn:8080/select/index.html";
        Map<String, String> ret = Sign.sign(ticketnew, url);
        timeout.put("sign",JSON.toJSONString(ret));
    }

    public void getSing2(){
        ResponseEntity<Map> gettoken = restTemplate.getForEntity("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wxbf2edb46c0bcacd0&secret=c9ec485ace764b397f9490f743d8e819",Map.class);
        Map map = (Map) JSON.toJSON(gettoken.getBody());
        System.err.println(gettoken.getBody());
        String access_token = map.get("access_token").toString();
        ResponseEntity<Map> getticket = restTemplate.getForEntity("https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token="+access_token+"&type=jsapi",Map.class);
        Map ticket = (Map) JSON.toJSON(getticket.getBody());
        System.err.println(getticket.getBody());
        String ticketnew = ticket.get("ticket").toString();

        String url = "http://icloud.izdodo.cn:8080/select/index.html";
        Map<String, String> ret = Sign.sign(ticketnew, url);
        timeout.put("sign",JSON.toJSONString(ret));
    }



}
