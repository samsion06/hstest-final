package com.example.UserbasePublic;

import com.example.domain.UserAddressInfo;
import com.example.mapper.UserBaseInfoMapper;
import com.example.utils.*;
import com.hs.user.base.proto.UserAddressServiceProto;
import com.jayway.jsonpath.JsonPath;
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
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.net.URI;

@SpringBootTest
public class UserAddressTest extends AbstractTestNGSpringContextTests {

    //地址8个接口
    @Autowired
    private UserBaseInfoMapper userBaseInfoMapper;

    private static Integer channelId=1;
    private static CloseableHttpClient httpClient ;
    private static ByteArrayEntity byteArrayEntity;
    private static URI uri;
    private static HttpPost post;
    private static HttpResponse response;


//    @Test(description = "1.添加收货地址" +
//            "            2.获取收货地址" +
//            "            3.更新收货地址"+
//            "            4.删除收货地址" )
    public void addressCURDTest(){
        UserAddressInfo userAddressInfo=new UserAddressInfo();
        userAddressInfo.setAddress(DataUtils.getRandomString(9));//随机地址
        userAddressInfo.setChannelUserId(DataUtils.getRandomString(6));
        userAddressInfo.setName(DataUtils.getRandomString(9));//随机用户名
        try{
            httpClient=HttpClients.createDefault();
            //添加收货地址
            uri = new URI(HttpConfigUtil.scheme, HttpConfigUtil.url, "/address/add","");
            post = new HttpPost(uri);
            byteArrayEntity = DataTransferUtil.userAddressInfoAddRequest(userAddressInfo.getChannelUserId(),channelId,userAddressInfo.getAddress());
            post.setEntity(byteArrayEntity);
            post.setHeader("Content-Type", "application/x-protobuf");
            HttpResponse response = httpClient.execute(post);
            //json path截取返回值传入下一个字段
            String addressResponseMsg = CheckReponseResult.AssertResponses(response, UserAddressServiceProto.UserAddressInfoResponse.class);
            //addressResponseMsg返回json的字符串： com.jayway.jsonpath.PathNotFoundException: No results for path: $['list'][1]['addressId']
            String addressId=JsonPath.read(addressResponseMsg,"$.addressId"); //$.list[0].字段名字
            //获取收货地址
            httpClient = HttpClients.createDefault();
            uri = new URI(HttpConfigUtil.scheme, HttpConfigUtil.url, "/address/getByAddressId","");
            post = new HttpPost(uri);
            byteArrayEntity = DataTransferUtil.userAddressRequest(userAddressInfo.getChannelUserId(),channelId,addressId);
            post.setEntity(byteArrayEntity);
            post.setHeader("Content-Type", "application/x-protobuf");
            response = httpClient.execute(post);
            CheckReponseResult.AssertResponses(response,UserAddressServiceProto.UserAddressInfoResponse.class);

            //更新收货地址
            uri = new URI(HttpConfigUtil.scheme, HttpConfigUtil.url, "/address/update","");
            post = new HttpPost(uri);
            byteArrayEntity = DataTransferUtil.userAddressInfoUpdateRequest(userAddressInfo.getChannelUserId(),channelId,addressId,userAddressInfo.getName());
            post.setEntity(byteArrayEntity);
            post.setHeader("Content-Type", "application/x-protobuf");
            response = httpClient.execute(post);
            String updateResponseMsg = CheckReponseResult.AssertResponse(response);
            Assert.assertEquals("RESP_CODE_SUCCESS",updateResponseMsg);
            CheckDatabase.CheckDatabaseUserUserAddressInfo(userBaseInfoMapper,"AddressUpadate",userAddressInfo);

            userAddressInfo.setIsDelete(1);
            //删除收货地址
            uri = new URI(HttpConfigUtil.scheme, HttpConfigUtil.url, "/address/delete","");
            post = new HttpPost(uri);
            byteArrayEntity = DataTransferUtil.userAddressDelete(userAddressInfo.getChannelUserId(),channelId,addressId);
            post.setEntity(byteArrayEntity);
            post.setHeader("Content-Type", "application/x-protobuf");
            response = httpClient.execute(post);
            String deleteResponseMsg = CheckReponseResult.AssertResponse(response);
            Assert.assertEquals("RESP_CODE_SUCCESS",deleteResponseMsg);
            CheckDatabase.CheckDatabaseUserUserAddressInfo(userBaseInfoMapper,"AddressDelete",userAddressInfo);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test(description ="5.分页查询用户收货地址列表")
    public void queryUserAddressByPageTest(){
        try{
            httpClient = HttpClients.createDefault();
            uri = new URI(HttpConfigUtil.scheme, HttpConfigUtil.url, "/address/query", null);
            post = new HttpPost(uri);
            byteArrayEntity = DataTransferUtil.userAddressPageRequest("17702015334",channelId,1,1);
            post.setEntity(byteArrayEntity);
            post.setHeader("Content-Type", "application/x-protobuf");
            response = httpClient.execute(post);
            String result = CheckReponseResult.AssertResponses(response, UserAddressServiceProto.UserAddressPage.class);
//            System.out.println("截取的字符串有："+JsonPath.read(result,"$.list[1].addressId"));
//            boolean flag=true;
//            try{
//                JsonPath.read(result,"$.list[1].addressId");
//            }catch (Exception e){
//                 flag=false;
//                e.printStackTrace();
//            }
//            Assert.assertTrue(flag,"无法获取字段");


        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //@Test(description ="6.获取省市区域树area")
    public void  getSysSubAreaTest(){
        try{
            httpClient = HttpClients.createDefault();
            uri = new URI(HttpConfigUtil.scheme, HttpConfigUtil.url, "/address/sys/sub/area", null);
            post = new HttpPost(uri);
            byteArrayEntity = DataTransferUtil.UserSysSubAreaRequest("0");
            post.setEntity(byteArrayEntity);
            post.setHeader("Content-Type", "application/x-protobuf");
            response = httpClient.execute(post);
            CheckReponseResult.AssertResponses(response,UserAddressServiceProto.SysAreaNodeTreeResponse.class);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //@Test(description ="7.获取省市区域树tree")
    public void  getSysAreaTreeTest(){
        try{
            httpClient = HttpClients.createDefault();
            uri = new URI(HttpConfigUtil.scheme, HttpConfigUtil.url, "/address/sys/area/tree", null);
            post = new HttpPost(uri);
            post.setHeader("Content-Type", "application/x-protobuf");
            response = httpClient.execute(post);
            CheckReponseResult.AssertResponses(response,UserAddressServiceProto.SysAreaNodeTreeResponse.class);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //@Test(description ="8.更新用户收货地址标签(幂等 x")
    public void tagUpdateTest(){
        try{
            httpClient = HttpClients.createDefault();
            uri = new URI(HttpConfigUtil.scheme, HttpConfigUtil.url, "/address/tag/update", null);
            post = new HttpPost(uri);
            byteArrayEntity =  DataTransferUtil.UserAddressTagRequest("17702015334",1,"774195ceb7ce455b95c69d2beb1f5723",2);
            post.setHeader("Content-Type", "application/x-protobuf");
            response = httpClient.execute(post);
            CheckReponseResult.AssertResponse(response);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


































    }



























