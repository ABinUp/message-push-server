package cn.abin.sample;

import com.alibaba.fastjson.JSONObject;
import okhttp3.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author : YiJiuBin
 * @createDate : 2023/05/29 11:16
 * @since : 1.0
 */
public class HuanXinPushTest {

    public static void main(String[] args) throws IOException {

//        getToken();
        // {"application":"46380c29-df52-4dc8-bfbe-8fb2632a3c83","access_token":"YWMtzZanSP3SEe2GAB2X736uHENZd0HXqjkIsvipgHAmjSRGOAwp31JNyL--j7JjKjyDAgMAAAGIZZly0zeeSAAgS_tGwWnZ4kahK_m670me-ArxxeEUl1aJmpI26JdhyA","expires_in":933120000}

//        registerUser("test1234", "123", "test1234");

        pushMsg();
    }

    private static void getToken() throws IOException {
        Map<String, Object> tokenMap = new HashMap<>();
        tokenMap.put("grant_type", "client_credentials");
        tokenMap.put("client_id", "YXA6RjgMKd9STci_vo-yYyo8gw");
        tokenMap.put("client_secret", "YXA6IlKvfKC8Jy_wWoyG1X8-6xjNtCQ");
        String tokenMapJsonStr = JSONObject.toJSONString(tokenMap);
        // 获取token
//        OkHttpUtils.builder()
//                .url("https://a1.easecdn.com/1182230517160206/testhx1234/token")
////                .addHeader("Content-Type", "application/json")
//                .postJSONStr(tokenMapJsonStr)
//                .sync();

        OkHttpClient okHttpClient = new OkHttpClient();
        Request.Builder requestBuilder = new Request.Builder();
        requestBuilder.url("https://a1.easecdn.com/1182230517160206/testhx1234/token")
                .post(RequestBody.create(MediaType.parse("application/json; charset=utf-8"), tokenMapJsonStr));

        Response response = okHttpClient.newCall(requestBuilder.build())
                .execute();
        String tokenResultJsonStr = response.body().string();
        System.out.println(tokenResultJsonStr);

        response.close();
    }

    private static void registerUser(String userName, String password, String nickName) throws IOException {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("username", userName);
        paramMap.put("password", password);
        paramMap.put("nickname", nickName);
        String paramMapJsonStr = JSONObject.toJSONString(paramMap);
        OkHttpClient okHttpClient = new OkHttpClient();
        Request.Builder requestBuilder = new Request.Builder();

        requestBuilder.url("https://a1.easecdn.com/1182230517160206/testhx1234/users")
                .addHeader("Accept", "application/json")
                .addHeader("Authorization", "YWMtzZanSP3SEe2GAB2X736uHENZd0HXqjkIsvipgHAmjSRGOAwp31JNyL--j7JjKjyDAgMAAAGIZZly0zeeSAAgS_tGwWnZ4kahK_m670me-ArxxeEUl1aJmpI26JdhyA")
                .post(RequestBody.create(MediaType.parse("application/json; charset=utf-8"), paramMapJsonStr));

        Response response = okHttpClient.newCall(requestBuilder.build())
                .execute();
        String responseBodyJsonStr = response.body().string();
        System.out.println(responseBodyJsonStr);
        // {"path":"/users","uri":"https://a1.easemob.com/1182230517160206/testhx1234/users","timestamp":1685332441605,"organization":"1182230517160206","application":"46380c29-df52-4dc8-bfbe-8fb2632a3c83","entities":[{"uuid":"72a28120-fdd4-11ed-b698-b1b76ec2b07f","type":"user","created":1685332441656,"modified":1685332441656,"username":"test1234","activated":true,"nickname":"test1234"}],"action":"post","data":[],"duration":73,"applicationName":"testhx1234"}

        response.close();
    }

    private static void pushMsg() throws IOException {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("targets", new String[]{"test1234"});
        paramMap.put("async", true);
        paramMap.put("strategy", 1);

        // {
        //  //基本推送配置。
        //  "title": "您有一条新消息",
        //  "subTitle": "",
        //  "content": "请及时查看",
        //  "ext": {},
        //  "config": {
        //    "clickAction": {
        //      "url":"",
        //      "action":"",
        //      "activity":""
        //    },
        //    "badge": {
        //      "addNum": 0,
        //      "setNum": 0
        //      }
        //  },
        //
        //  //各厂商推送配置。
        //  "easemob":{},
        //  "apns": {},
        //  "fcm": {},
        //  "huawei": {},
        //  "meizu": {},
        //  "oppo": {},
        //  "vivo": {},
        //  "xiaomi": {}
        //}
        Map<String, Object> notificationMap = new HashMap<>();
        notificationMap.put("title", "测试推送title");
        notificationMap.put("subTitle", "测试推送subTitle");
        notificationMap.put("content", "这是内容");
        Map<String, Object> extMap = new HashMap<>();
        extMap.put("propA", "A");
        extMap.put("propB", "B");
        notificationMap.put("ext", extMap);

        paramMap.put("pushMessage", notificationMap);
        String paramMapJsonStr = JSONObject.toJSONString(paramMap);
        OkHttpClient okHttpClient = new OkHttpClient();
        Request.Builder requestBuilder = new Request.Builder();

        requestBuilder.url("https://a1.easecdn.com/1182230517160206/testhx1234/push/single")
                .addHeader("Authorization", "Bearer YWMtzZanSP3SEe2GAB2X736uHENZd0HXqjkIsvipgHAmjSRGOAwp31JNyL--j7JjKjyDAgMAAAGIZZly0zeeSAAgS_tGwWnZ4kahK_m670me-ArxxeEUl1aJmpI26JdhyA")
                .post(RequestBody.create(MediaType.parse("application/json; charset=utf-8"), paramMapJsonStr));

        Response response = okHttpClient.newCall(requestBuilder.build())
                .execute();
        String responseBodyJsonStr = response.body().string();
        System.out.println(responseBodyJsonStr);

        response.close();
    }
}
