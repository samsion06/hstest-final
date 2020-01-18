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
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

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
            post = new HttpPost(uri);                                    //这里传一个obj
            byteArrayEntity = DataTransferUtil.userAddressInfoAddRequest(userAddressInfo);
            post.setEntity(byteArrayEntity);
            post.setHeader("Content-Type", "application/x-protobuf");
            HttpResponse response = httpClient.execute(post);
            //json path截取返回值传入下一个字段
            String addressResponseMsg = CheckReponseResult.AssertResponses(response, UserAddressServiceProto.UserAddressInfoResponse.class);
            //1.先比对长度，再比对细节。 list的话可以比较length
            //addressResponseMsg返回json的字符串： com.jayway.jsonpath.PathNotFoundException: No results for path: $['list'][1]['addressId']
            //可以参考分页列表的方法
            String addressId=JsonPath.read(addressResponseMsg,"$.addressId"); //$.list[0].字段名字

            //获取收货地址
            httpClient = HttpClients.createDefault();
            uri = new URI(HttpConfigUtil.scheme, HttpConfigUtil.url, "/address/getByAddressId","");
            post = new HttpPost(uri);
            byteArrayEntity = DataTransferUtil.userAddressRequest(userAddressInfo);
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
        UserAddressInfo userAddressInfo=new UserAddressInfo();
        userAddressInfo.setChannelUserId("17702015334");
        try{
            httpClient = HttpClients.createDefault();
            uri = new URI(HttpConfigUtil.scheme, HttpConfigUtil.url, "/address/query", null);
            post = new HttpPost(uri);
            byteArrayEntity = DataTransferUtil.userAddressPageRequest(userAddressInfo);
            post.setEntity(byteArrayEntity);
            post.setHeader("Content-Type", "application/x-protobuf");
            response = httpClient.execute(post);
            String result = CheckReponseResult.AssertResponses(response, UserAddressServiceProto.UserAddressPage.class);
            //2.预期结果和实际结果对比,查的需要自定义预期结果进行比对
            Map map=new HashMap();
            map.put("fuck","fuck");
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("total",1);
            jsonObject.put("pageSize",1);
            jsonObject.put("pageNum",1);
            jsonObject.put("list",map);
            System.out.println(jsonObject.length());
            //先比对长度，再比对每一个值是否相同
            System.out.println(jsonObject.length());

            //result="{\"total\": 1,\"pageSize\": 1,\"pageNum\": 1,\"pages\": 1,\"list\": [{\"addressId\": \"774195ceb7ce455b95c69d2beb1f5723\",\"userName\": \"xiaoming\",\"address\": \"广州海珠区你老母2号\",\"createTime\": 1575447369000,\"updateTime\": 1575531730000}]}";
//          System.out.println("截取的字符串有："+JsonPath.read(result,"$.list[1].addressId"));
//          boolean flag=true;
//          try{
//              JsonPath.read(result,"$.list[1].addressId");
//              JsonPath.read(result,"$.list[1].addressId");
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



























