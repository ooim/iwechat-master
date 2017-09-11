package com.izdodo.cloud.test;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

class Sign {
    public static void main(String[] args) {
        String jsapi_ticket = "sM4AOVdWfPE4DxkXGEs8VN3Q00Ib5TDk6AVKP9zhTYnqvrJDRbnI8Tha9oyTTh2q9SJqb2gvCUAMtx19P4tsnQ";
      //  https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=zgclANsY-IQoDzUMMKaybJ2aeZn7yMhdXI0PLlCsY2tCLmuBa1z-1NrFFke6JUjnCGmZho-Z6_RfDYby5ud14SwH1se2EEg7QlsTa6iKMURYJ6Wnj2eJ4Q6uqu9INjM2RVJeAAAVZN&type=jsapi

        //https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wxc5b0f2991f5c6fdb&secret=d4b1381ee9e2d984965cf5a775b2006a
        /*access_token:*/
/*
        KT8PhMls_gHZ9yiiAag-U5sCHi2m601x7DS-n9A3K0i2jtEXtNhAXx3OloD-zWGXYwfXW0G7CW3dLYpNJ2EeeGXovztOTOrZ5hxco7g-7QD6mTcnz0Ka5r2DhCWi3T4kOYFcACAJTA
*/
        /*d4b1381ee9e2d984965cf5a775b2006a*/
        // 注意 URL 一定要动态获取，不能 hardcode
        String url = "http://icloud.izdodo.cn:8080/select/index.html";
        Map<String, String> ret = sign(jsapi_ticket, url);
        for (Map.Entry entry : ret.entrySet()) {
            System.out.println(entry.getKey() + ", " + entry.getValue());
        }
    };

    public static Map<String, String> sign(String jsapi_ticket, String url) {
        Map<String, String> ret = new HashMap<String, String>();
        String nonce_str = create_nonce_str();
        String timestamp = create_timestamp();
        String string1;
        String signature = "";

        //注意这里参数名必须全部小写，且必须有序
        string1 = "jsapi_ticket=" + jsapi_ticket +
                  "&noncestr=" + nonce_str +
                  "&timestamp=" + timestamp +
                  "&url=" + url;
        System.out.println(string1);

        try
        {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(string1.getBytes("UTF-8"));
            signature = byteToHex(crypt.digest());
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }

        ret.put("url", url);
        ret.put("jsapi_ticket", jsapi_ticket);
        ret.put("nonceStr", nonce_str);
        ret.put("timestamp", timestamp);
        ret.put("signature", signature);

        return ret;
    }

    private static String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash)
        {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }

    private static String create_nonce_str() {
        return UUID.randomUUID().toString();
    }

    private static String create_timestamp() {
        return Long.toString(System.currentTimeMillis() / 1000);
    }
}
