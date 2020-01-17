package com.example.utils;
import com.google.protobuf.Message;
import com.googlecode.protobuf.format.JsonFormat;
import com.hs.user.base.proto.ResultResponse;
import org.apache.http.HttpResponse;
import org.testng.Assert;
import org.testng.Reporter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CheckReponseResult {

    private static JsonFormat jsonFormat;
    private static String resultContent;
    private static Map<String, Object> map;
    private static  String result="返回值: [";

    static {
        jsonFormat = new JsonFormat();
        map = new HashMap();
    }

    //返回字符串
    public static String checkResponses(HttpResponse response, Class<? extends Message> clazz) throws IOException {
        System.out.println(clazz);
        if (response.getStatusLine().getStatusCode() == 200) {
            ResultResponse.ResultSet resp = ResultResponse.ResultSet.parseFrom(response.getEntity().getContent());
            if (resp.getCode() == ResultResponse.ResponseCode.RESP_CODE_SUCCESS && resp.getData().is(clazz)) {
                resultContent = jsonFormat.printToString(resp.getData().unpack(clazz));
            }else {
                System.out.println(resp.getCode());
            }
        }else{
            System.out.println(response.getStatusLine().getStatusCode());
        }
        return resultContent;
    }

    public static void checkResponse(HttpResponse response) throws IOException {
        if (response.getStatusLine().getStatusCode() == 200) {
            ResultResponse.ResultSet resp = ResultResponse.ResultSet.parseFrom(response.getEntity().getContent());
            if (resp.getCode() == ResultResponse.ResponseCode.RESP_CODE_SUCCESS) {
                System.out.println(resp.getCode());
            } else {
                System.out.println(resp.getCode());
            }
        } else {
            System.out.println(response.getStatusLine().getStatusCode());
        }
    }

    //返回对象和字符串
    public static Map<String, Object> checkResponseCodeAndObj(HttpResponse response, Class<? extends Message> clazz) throws Exception {
        System.out.println(clazz);
        if (response.getStatusLine().getStatusCode() == 200) {
            //ResultResponse.ResultSet.parseFrom这种格式转换返回内容
            ResultResponse.ResultSet resp = ResultResponse.ResultSet.parseFrom(response.getEntity().getContent());
            //判断返回内容
            if (resp.getCode() == ResultResponse.ResponseCode.RESP_CODE_SUCCESS && resp.getData().is(clazz)) {
                resultContent = jsonFormat.printToString(resp.getData().unpack(clazz));
                System.out.println("resultContent:" + resultContent);
                map.put("resultContent", resultContent);
                map.put("resultObject", resp);
            } else {
                System.out.println(resp.getCode());
            }
        } else {
            System.out.println(response.getStatusLine().getStatusCode());
        }
        return map;
    }

    //框架断言
    public static String AssertResponse(HttpResponse response) throws IOException {
        Assert.assertEquals(response.getStatusLine().getStatusCode(),200);
        ResultResponse.ResultSet resp = ResultResponse.ResultSet.parseFrom(response.getEntity().getContent());
        Assert.assertEquals(resp.getCode(),ResultResponse.ResponseCode.RESP_CODE_SUCCESS );
        resultContent=resp.getMsg();
        System.out.println(result+resultContent+"]");
        Reporter.log(result+resultContent+"]");
        return resultContent;
    }

    public static String AssertResponses(HttpResponse response, Class<? extends Message> clazz) throws IOException {
        System.out.println(clazz);
        Assert.assertEquals(response.getStatusLine().getStatusCode(),200);
        ResultResponse.ResultSet resp = ResultResponse.ResultSet.parseFrom(response.getEntity().getContent());
        Assert.assertEquals(ResultResponse.ResponseCode.RESP_CODE_SUCCESS,resp.getCode() );
        Assert.assertTrue(resp.getData().is(clazz));
        //将返回内容解析成json字符串形式返回
        resultContent = jsonFormat.printToString(resp.getData().unpack(clazz));
        System.out.println(resultContent);
        //记录结果
        Reporter.log(result+resultContent+"]");
        return  resultContent;
    }
}
