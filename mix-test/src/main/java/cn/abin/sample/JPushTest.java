package cn.abin.sample;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author : YiJiuBin
 * @createDate : 2023/05/18 11:32
 * @since : 1.0
 */
public class JPushTest {

    private final static Logger log = LoggerFactory.getLogger(JPushTest.class);

    // t
    private final static JPushClient jpushClient = new JPushClient("7bdd9a99b0915d1adae71dce", "39d8ee4d4c9d97a314c091bb");

    public static void main(String[] args) throws APIConnectionException, APIRequestException {
        List<String> rids = new ArrayList<>();
        rids.add("170976fa8b9b7c4fc69");
        String[] registrationIdArray = rids.toArray(new String[0]);

        Map<String, String> msgMap = new HashMap<>();
        msgMap.put("propA", "1");
        msgMap.put("propB", "2");
        msgMap.put("propC", "3");

        String msgStr = JSONObject.toJSONString(msgMap);

        PushPayload pushPayload = PushPayload.newBuilder()
                .setPlatform(Platform.all())
                .setAudience(Audience.registrationId(registrationIdArray))
                .setMessage(Message.newBuilder().setMsgContent(msgStr).build())
                .build();

        resolvePush(pushPayload);

//        ReceivedsResult reportReceiveds = jpushClient.getReceivedsDetail("18100763050146469");
//        ReceivedsResult reportReceiveds = jpushClient.getReceivedsDetail("18100761643194126");
//        String reportJson = JSONObject.toJSONString(reportReceiveds);
//        System.out.println(reportJson);
    }

    private static void resolvePush(PushPayload pushPayload) {
        log.info(JSON.toJSONString(pushPayload));
        if (pushPayload == null) return;
        try {
            PushResult pushResult = jpushClient.sendPush(pushPayload);
            log.info(JSONObject.toJSONString(pushResult));
//            boolean c = false;
//            if (c) {
//                throw new Exception();
//            }
        } catch (APIConnectionException e) {
            // Connection error, should retry later
            log.error("Connection error, should retry later", e);

        } catch (APIRequestException e) {
            // Should review the error, and fix the request
            log.error("Should review the error, and fix the request", e);
            log.info("HTTP Status: " + e.getStatus());
            log.info("Error Code: " + e.getErrorCode());
            log.info("Error Message: " + e.getErrorMessage());
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    private static void resolveReport(Map<String, String> paramMap) {

    }
}
