package com.example.UserbasePublic;

import com.example.domain.UserAliPayInfo;
import com.example.mapper.UserBaseInfoMapper;
import com.example.utils.*;
import com.hs.user.base.proto.UserAliPayAuthServiceProto;
import com.hs.user.base.proto.UserIdCardIdentifyServiceProto;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;

import org.testng.Reporter;
import org.testng.annotations.Test;

import java.io.IOException;
import java.net.URI;

@SpringBootTest
public class UseraliPayTest extends AbstractTestNGSpringContextTests {

    //支付宝中心 4个接口

    @Autowired
    private UserBaseInfoMapper userBaseInfoMapper;

    private static Integer channelId=1;
    private static CloseableHttpClient httpClient;
    private static URI uri ;
    private static HttpPost post ;
    private static HttpResponse response ;
    private static ByteArrayEntity byteArrayEntity ;

    @org.testng.annotations.Test(description = "1.绑定支付宝" +
            "                                   2.用户支付宝授权" +
            "                                   3.支付宝授权查询" +
            "                                   4.取消支付包授权 OK")
    public void BindAndAuthAndCancelTest(){

        //生成随机数字
        UserAliPayInfo userAliPayInfo =new UserAliPayInfo();
        userAliPayInfo.setChannelUserId(DataUtils.getRandomChannelUserId(6));
        userAliPayInfo.setAlipayUserId(DataUtils.getRandomChannelUserId(6));
        userAliPayInfo.setAlipayAccount("177"+(int)((Math.random()*9+1)*10000000));
        userAliPayInfo.setIsDelete(1);
        //生成随机字符串
        userAliPayInfo.setAlipayRealName( DataUtils.getRandomString(9));

        try {
            httpClient= HttpClients.createDefault();
            //绑定支付宝
            uri = new URI(HttpConfigUtil.scheme, HttpConfigUtil.url, "/aliPay/binding","");
            post = new HttpPost(uri);
            byteArrayEntity = DataTransferUtil.userAliPayBidingRequest(userAliPayInfo.getChannelUserId(),channelId,userAliPayInfo.getAlipayRealName(),
                    userAliPayInfo.getAlipayAccount(),userAliPayInfo.getAlipayUserId());
            post.setEntity(byteArrayEntity);
            post.setHeader("Content-Type", "application/x-protobuf");
            HttpResponse response = httpClient.execute(post);
            String bindResponseMsg = CheckReponseResult.AssertResponse(response);
            Assert.assertEquals("RESP_CODE_SUCCESS",bindResponseMsg);//expected  actual
            CheckDatabase.CheckDatabaseUserAliPayInfo(userBaseInfoMapper,"AliPayBind",userAliPayInfo);

            userAliPayInfo.setStatus(1);
            //用户支付宝授权
            uri = new URI(HttpConfigUtil.scheme, HttpConfigUtil.url, "/aliPay/auth","");
            post = new HttpPost(uri);
            byteArrayEntity = DataTransferUtil.userAliPayAuthRequest(userAliPayInfo.getChannelUserId(),channelId,userAliPayInfo.getAlipayUserId());
            post.setEntity(byteArrayEntity);
            post.setHeader("Content-Type", "application/x-protobuf");
            response = httpClient.execute(post);
            String authResponse = CheckReponseResult.AssertResponse(response);
            Assert.assertEquals("RESP_CODE_SUCCESS",authResponse);
            CheckDatabase.CheckDatabaseUserAliPayInfo(userBaseInfoMapper,"AliPayAuth",userAliPayInfo);

            //用户支付宝授权信息查询
            httpClient= HttpClients.createDefault();
            uri = new URI(HttpConfigUtil.scheme, HttpConfigUtil.url, "/aliPay/auth/info","");
            post = new HttpPost(uri);
            byteArrayEntity = DataTransferUtil.userAliPayAuthInfoRequest(userAliPayInfo.getChannelUserId(), channelId);
            post.setEntity(byteArrayEntity);
            post.setHeader("Content-Type", "application/x-protobuf");
            response = httpClient.execute(post);
            Assert.assertEquals(200,response.getStatusLine().getStatusCode());
            UserAliPayAuthServiceProto.UserAliPayAuthInfoResponse resp=  UserAliPayAuthServiceProto.UserAliPayAuthInfoResponse.parseFrom(response.getEntity().getContent());
            System.out.println(resp.toString());
            DataUtils.logResponse(resp.toString());

            //将delete 改为2 作为取消授权判断
            userAliPayInfo.setStatus(2);
            //用户取消授权
            uri = new URI(HttpConfigUtil.scheme, HttpConfigUtil.url, "/aliPay/auth/cancel","");
            post = new HttpPost(uri);
            byteArrayEntity = DataTransferUtil.userAliPayAuthCancelRequest(userAliPayInfo.getChannelUserId(),channelId);
            post.setEntity(byteArrayEntity);
            post.setHeader("Content-Type", "application/x-protobuf");
            response = httpClient.execute(post);
            String unbindResponseMsg = CheckReponseResult.AssertResponse(response);
            Assert.assertEquals("RESP_CODE_SUCCESS",unbindResponseMsg);
            CheckDatabase.CheckDatabaseUserAliPayInfo(userBaseInfoMapper,"AliPayCancel",userAliPayInfo);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
            }
        }
    }
}





