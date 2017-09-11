package com.izdodo.cloud.test;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zfy on 2017/7/25.
 */
@RestController
public class Test {

    @Autowired
    RestTemplate restTemplate;

    public static Map timeout = new HashMap();

    @RequestMapping("/get")
    public String getTick(){
        if (timeout.get("sign")!=null&&(System.currentTimeMillis()/1000-Long.parseLong(timeout.get("time").toString())/1000<7100)){
        }else{
            timeout.put("time",System.currentTimeMillis());
            getSing();
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

}
