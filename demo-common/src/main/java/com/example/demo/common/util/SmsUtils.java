package com.example.demo.common.util;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wuhengzhen
 * @date 2019/8/8 16:06
 */
@Slf4j
public class SmsUtils {

    /**
     * 设置超时时间-可自行调整
     */
    private static final String DEFAULT_CONNECT_TIMEOUT = "sun.net.client.DEFAULT_CONNECT_TIMEOUT";
    private static final String DEFAULT_READ_TIMEOUT = "sun.net.client.DEFAULT_READ_TIMEOUT";
    private static final String TIMEOUT = "10000";

    /**
     * 短信API产品域名（接口地址固定，无需修改）
     */
    private static final String DOMAIN = "dysmsapi.aliyuncs.com";

    private static final String ACCESS_KEY_ID = "LTAI5qDyhMIa88yJ";
    private static final String ACCESS_KEY_SECRET = "EqntPEnHpGuX4L4LPQCQgPUwMtPHdk";
    private static final String SIGN_NAME = "demo";
    private static final String TEMPLATE_CODE = "SMS_181856087";
    private static final String REGION_ID = "default";
    /**
     * 发送成功状态
     */
    public static final String SMS_SEND_SUCCESS_STATUS = "OK";

    /**
     * 短信发送接口信息  支持批量发送 ps--目前签名信息仅设置一个
     *
     * @param phone         需要发送的电话号码，支持多个电话号码 格式为"13600000000,15000000000"
     * @param templateCode  明确需要使用哪个模板，可以从阿里云控制台查看
     * @param templateParam 模板内需要填充的字段及字段值 格式为("{\"name\":\"Tom\", \"code\":\"123\"}")
     * @return true 代表发送成功  false 代表发送失败
     */
    public static boolean sendSms(String phone, String templateCode, String templateParam) {
        boolean bool = false;
        // 设置超时时间-可自行调整
        System.setProperty(DEFAULT_CONNECT_TIMEOUT, TIMEOUT);
        System.setProperty(DEFAULT_READ_TIMEOUT, TIMEOUT);
        DefaultProfile profile = DefaultProfile.getProfile(REGION_ID, ACCESS_KEY_ID, ACCESS_KEY_SECRET);
        IAcsClient client = new DefaultAcsClient(profile);
        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        // 短信API产品域名（接口地址固定，无需修改）
        request.setDomain(DOMAIN);
        // 版本信息  已经固定  不能进行更改
        request.setVersion("2017-05-25");
        //必填:待发送手机号。支持以逗号分隔的形式进行批量调用，批量上限为1000个手机号码,批量调用相对于单条调用及时性稍有延迟,验证码类型的短信推荐使用单条调用的方式；发送国际/港澳台消息时，接收号码格式为国际区号+号码，如“85200000000”
        request.putQueryParameter("PhoneNumbers", phone);
        //  阿里云控制台签名
        request.putQueryParameter("SignName", SIGN_NAME);
        // 阿里云控制台模板编号
        request.putQueryParameter("TemplateCode", templateCode);
        // 系统规定参数
        request.setAction("SendSms");
        // 模板内需要填充参数信息
        request.putQueryParameter("TemplateParam", templateParam);

        try {
            log.info("调用阿里云短信服务请求 phone={}，templateCode={},templateParam={}", phone, templateCode, templateParam);
            CommonResponse response = client.getCommonResponse(request);
            log.info("调用阿里云短信服务请求 返回：{}", response.getData());

            // String 转换为map
            Map map = JSONObject.parseObject(response.getData(), Map.class);
            if (SMS_SEND_SUCCESS_STATUS.equals(map.get("Code"))) {
                bool = true;
            }
        } catch (ServerException e) {
            log.error("阿里云短信服务异常:{}", e);
        } catch (ClientException e) {
            log.error("连接阿里云短信异常:{}", e);
        } catch (Exception e) {
            log.error("json转换异常:{}", e);
        }
        return bool;
    }


    public static void main(String[] args) {
        Map<String, Object> param = new HashMap<>();
        param.put("code", CommonsUtils.getCode());
        boolean f = sendSms("18353238798", TEMPLATE_CODE, JSONUtil.toJsonStr(param));
        System.out.println(f);
    }

}
