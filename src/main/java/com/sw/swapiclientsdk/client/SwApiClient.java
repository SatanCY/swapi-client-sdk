package com.sw.swapiclientsdk.client;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.sw.swapiclientsdk.common.SignGenerator;
import com.sw.swapiclientsdk.model.User;

import java.util.HashMap;
import java.util.Map;

/**
 * 调用第三方接口的客户端
 *
 * @Author：SatanCY
 * @Date：2024/10/1 16:51
 */
public class SwApiClient {

    private String accessKey;
    private String secretKey;
    private String url;

    public SwApiClient(String accessKey, String secretKey, String url) {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
        this.url = url;
    }

    public String getNameByGet(String name) {
        //可以单独传入http参数，这样参数会自动做URL编码，拼接在URL中
        HashMap<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("name", name);

        String result = HttpUtil.get(url + "/api/name/", paramMap);
        return result;
    }

    public String getNameByPost(String name) {
        HashMap<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("name", name);

        String result = HttpUtil.post(url + "/api/name/", paramMap);
        return result;
    }

    // 创建一个私有方法，用于构造请求头
    private Map<String, String> getHeaderMap(String body) {
        // 创建一个新的 HashMap 对象
        Map<String,String> map = new HashMap<String,String>();
        // 将 "accessKey" 和其对应的值放入 map 中
        map.put("accessKey",accessKey);
        // 注意，不能直接发送密钥
        // 将 "secretKey" 和其对应的值放入 map 中
//        map.put("secretKey",secretKey);
        // 生成随机数（生成一个包含4个随机数字的字符串）
        map.put("nonce", RandomUtil.randomNumbers(4));
        // 请求体内容
//        map.put("body",body);
        // 当前时间戳
        map.put("timestamp",String.valueOf(System.currentTimeMillis() / 1000));
        // 签名
        map.put("sign", SignGenerator.generateSign(body,secretKey));
        // 返回构造的请求头 map
        return map;
    }

    public String getNameByRestful(User user) {
        String userJson = JSONUtil.toJsonStr(user);
        HttpResponse response = HttpRequest.post(url + "/api/name/json")
                .addHeaders(getHeaderMap(userJson))
                .body(userJson)
                .execute();
        System.out.println(response.getStatus());
        String result = response.body();
        return result;
    }
}
