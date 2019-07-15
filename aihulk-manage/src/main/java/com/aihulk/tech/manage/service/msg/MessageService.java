package com.aihulk.tech.manage.service.msg;

import com.aihulk.tech.common.util.JsonUtil;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
public class MessageService {

    @Value("${ali_msg.access_key}")
    private String accessKey;

    @Value("${ali_msg.secret}")
    private String secret;

    @Value("${ali_msg.url}")
    private String url;

    public static final String DEFAULT_ACTION = "SendSms";

    public static final String DEFAULT_API_VERSION = "2017-05-25";

    public static final String DEFAULT_RESGION_ID = "cn-hangzhou";

    public SendMsgResponse send(String phone, String signName, String templateCode, Map<String, String> templateParam) {
        DefaultProfile profile = DefaultProfile.getProfile(DEFAULT_RESGION_ID, accessKey, secret);
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain(url);
        request.setVersion(DEFAULT_API_VERSION);
        request.setAction(DEFAULT_ACTION);
        request.putQueryParameter("RegionId", DEFAULT_RESGION_ID);
        request.putQueryParameter("PhoneNumbers", phone);
        request.putQueryParameter("SignName", signName);
        request.putQueryParameter("TemplateCode", templateCode);
        request.putQueryParameter("TemplateParam", JsonUtil.toJsonString(templateParam));
        SendMsgResponse sendMsgResponse = new SendMsgResponse();
        try {
            CommonResponse response = client.getCommonResponse(request);
            int httpStatus = response.getHttpStatus();
            if (httpStatus == HttpStatus.SC_OK) {
                String data = response.getData();
                Map map = JsonUtil.parseObject(data, Map.class);
                sendMsgResponse.setMessage((String) map.get("Message"));
                String code = (String) map.get("Code");
                if ("OK".equalsIgnoreCase(code)) {
                    sendMsgResponse.setStatus(SendMsgResponse.STATUS_SUCCESS);
                } else {
                    sendMsgResponse.setStatus(SendMsgResponse.STATUS_FAIL);
                }
            }
        } catch (ServerException e) {
            sendMsgResponse.setMessage(e.getMessage());
            sendMsgResponse.setStatus(SendMsgResponse.STATUS_FAIL);
            log.error("aliyun msg service exception:", e);
        } catch (ClientException e) {
            sendMsgResponse.setMessage(e.getMessage());
            sendMsgResponse.setStatus(SendMsgResponse.STATUS_FAIL);
            log.error("msg client invoke exception", e);
        }
        return sendMsgResponse;
    }

    @Data
    private static class SendMsgResponse {

        public static final Integer STATUS_SUCCESS = 0;
        public static final Integer STATUS_FAIL = -1;

        private String message;

        private Integer status;
    }
}
