package com.example.Controller;

import com.example.domain.UserAddressInfo;
import com.example.utils.CheckReponseResult;
import com.example.utils.DataTransferUtil;
import com.example.utils.HttpConfigUtil;
import com.hs.user.base.proto.UserAddressServiceProto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.testng.Reporter;

import java.net.URI;

@RestController
@Api(value = "/",description ="这是我所有的方法")
public class MyController {

    private static Integer channelId=1;
    private static CloseableHttpClient httpClient ;
    private static ByteArrayEntity byteArrayEntity;
    private static URI uri;
    private static HttpPost post;
    private static HttpResponse response;

    @RequestMapping(value = "/queryUserAddressByPage",method = RequestMethod.POST)
    @ApiOperation(value = "queryUserAddressByPage",httpMethod = "POST")
    public String queryUserAddressByPageTest(UserAddressInfo userAddressInfo){
        String result = null;
        try{
            httpClient = HttpClients.createDefault();
            uri = new URI(HttpConfigUtil.scheme, HttpConfigUtil.url, "/address/query", null);
            post = new HttpPost(uri);
           //byteArrayEntity = DataTransferUtil.userAddressPageRequest(userAddressInfo.getChannelUserId(),channelId,1,1);
            post.setEntity(byteArrayEntity);
            post.setHeader("Content-Type", "application/x-protobuf");
            response = httpClient.execute(post);
            result = CheckReponseResult.AssertResponses(response, UserAddressServiceProto.UserAddressPage.class);
            Reporter.log("fuck");
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
        return result;
    }
}
