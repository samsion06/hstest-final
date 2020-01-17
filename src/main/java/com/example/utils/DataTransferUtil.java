package com.example.utils;

import com.example.domain.UserTeamInfo;
import com.google.common.collect.Lists;
import com.hs.productservice.api.proto.getdetailbyid.ProductServiceApiGetDetailById;
import com.hs.productservice.api.proto.getdetailbyidlist.ProductServiceApiGetDetailByIdList;
import com.hs.productservice.api.proto.getlistbypage.ProductServiceApiGetListByPage;
import com.hs.productservice.api.proto.lockuserstock.ProductServiceApiStockService;
import com.hs.user.base.proto.*;
import com.hs.user.rel.proto.UserRelationProto;
import org.apache.http.entity.ByteArrayEntity;
import org.testng.Reporter;

import java.util.ArrayList;
import java.util.List;


public class DataTransferUtil {

    private static String incomeMessage="传入参数:"+" {";

    /**
     * 用户中心
     */

    //花生日记登录
    public static ByteArrayEntity userInfoPdLoginRequestConvertBuilder(Integer channelId, String mobile, String pwd, String mobileAreaCode ){
        UserBaseServiceProto.UserInfoPdLoginRequest.Builder builder= UserBaseServiceProto.UserInfoPdLoginRequest.newBuilder();
        builder.setChannelId(channelId);
        builder.setMobile(mobile);
        builder.setPwd(pwd);
        builder.setMobileAreaCode(mobileAreaCode);
        ByteArrayEntity bytes=new ByteArrayEntity(builder.build().toByteArray());
        System.out.println(builder);
        Reporter.log("花生日记用户登录_"+incomeMessage+builder+ "}");
        return bytes;
    }
    //修改昵称
    public static ByteArrayEntity userNickNameUpdateRequestConvertBuilder(Integer channelId, String channelUserId, String nickName){
        UserBaseServiceProto.UserNickNameUpdateRequest.Builder builder = UserBaseServiceProto.UserNickNameUpdateRequest.newBuilder();
        builder.setChannelId(channelId);
        builder.setChannelUserId(channelUserId);
        builder.setNickName(nickName);
        System.out.println(builder);
        ByteArrayEntity bytes=new ByteArrayEntity(builder.build().toByteArray());
        Reporter.log("修改昵称_"+incomeMessage+builder+ "}");
        return bytes;
    }
    //用户注册
    public static ByteArrayEntity userBaseRegisterRequestConvertBuilder(String mobile) {

        UserBaseServiceProto.UserBaseRegisterRequest.Builder builder = UserBaseServiceProto.UserBaseRegisterRequest.newBuilder();
        UserBaseServiceProto.UserBaseRegister.Builder  userBaseRegisterBuild = UserBaseServiceProto.UserBaseRegister.newBuilder();
        UserBaseServiceProto.UserLoginRegister.Builder  UserLoginRegister = UserBaseServiceProto.UserLoginRegister.newBuilder();
        UserBaseServiceProto.UserZyyxRegister.Builder  userZyyxRegister = UserBaseServiceProto.UserZyyxRegister.newBuilder();
        userBaseRegisterBuild.setMobileAreaCode("68");
        userBaseRegisterBuild.setMobile(mobile);
        userBaseRegisterBuild.setChannelUserId(Math.round((int)(Math.random()*100))+""+System.currentTimeMillis());
        userBaseRegisterBuild.setHeadImgUrl("/sdf/sdf/sdfsdf");
        userBaseRegisterBuild.setBirthday("2019-06-04");
        userBaseRegisterBuild.setSex(0);
        userBaseRegisterBuild.setNickName("小周直邮");
        userBaseRegisterBuild.setSource(1);
        userBaseRegisterBuild.setRealName("小周直邮");
        userBaseRegisterBuild.setChannelId(2);
        userBaseRegisterBuild.setIdentityCard("634233425567868");
        userBaseRegisterBuild.setUserRole(1);

        userZyyxRegister.setRegisterRecommendUserId("661571043571580");

        UserLoginRegister.setLoginPwd(userBaseRegisterBuild.getMobile());
        UserLoginRegister.setLoginName(userBaseRegisterBuild.getMobile());

        builder.setUserBaseRegister(userBaseRegisterBuild);
        builder.setUserLoginRegister(UserLoginRegister);
        builder.setUserZyyxRegister(userZyyxRegister);
        ByteArrayEntity bytes=new ByteArrayEntity(builder.build().toByteArray());

        System.out.println(builder);
//        userBasePdRegisterBuild.setRegisterInviteCode("Mw2mABG");
//        userBasePdRegisterBuild.setInviteCode("jkdsfwe");
//        userBasePdRegisterBuild.setCompanyId(1);
//        userBasePdRegisterBuild.setOperator(1);
//        userBasePdRegisterBuild.setIsOfflineOperator(1);
//        userBasePdRegisterBuild.setIsOfflineUser(1);
//        userBasePdRegisterBuild.setUserTagStatus(1);
        //UserBaseServiceProto.UserWeChatRegister.Builder  UserWeChatRegister = UserBaseServiceProto.UserWeChatRegister.newBuilder();
        //UserBaseServiceProto.UserBasePdRegister.Builder  userBasePdRegisterBuild = UserBaseServiceProto.UserBasePdRegister.newBuilder();

        //UserWeChatRegister.setOpenId("");
        //UserWeChatRegister.setUnionId("");

        //builder.setUserWeChatRegister(UserWeChatRegister);
        //builder.setUserBasePdRegister(userBasePdRegisterBuild);
        return bytes;
    }

    //修改头像
    public static ByteArrayEntity userHeadImgUpdateRequestConvertBuilder(Integer channelId, String channelUserId, String headImageUrl){
        UserBaseServiceProto.UserHeadImgUpdateRequest.Builder builder= UserBaseServiceProto.UserHeadImgUpdateRequest.newBuilder();
        builder.setChannelId(channelId);
        builder.setChannelUserId(channelUserId);
        builder.setHeadImageUrl(headImageUrl);
        System.out.println(builder);
        ByteArrayEntity bytes=new ByteArrayEntity(builder.build().toByteArray());
        Reporter.log("修改头像_"+incomeMessage+builder+ "}");
        return bytes;
    }
    //根据微信ID和OPENID获取用户信息
    public static ByteArrayEntity userInfoUnionIdOpenIdRequestConvertBuilder(Integer channelId, String unionId, String openId){
        UserBaseServiceProto.UserInfoUnionIdOpenIdRequest.Builder builder= UserBaseServiceProto.UserInfoUnionIdOpenIdRequest.newBuilder();
        builder.setChannelId(channelId);
        builder.setOpenId(openId);
        builder.setUnionId(unionId);
        //System.out.println(builder);
        ByteArrayEntity bytes=new ByteArrayEntity(builder.build().toByteArray());
        Reporter.log("获取用户信息_"+incomeMessage+builder+ "}");
        return bytes;
    }

    //修改用户身份状态
    public static ByteArrayEntity UserStatusUpdateRequest(Integer channelId, String channelUserId, Integer userStatus){
        UserBaseServiceProto.UserStatusUpdateRequest.Builder builder= UserBaseServiceProto.UserStatusUpdateRequest.newBuilder();
        builder.setChannelId(channelId);
        builder.setChannelUserId(channelUserId);
        builder.setUserStatus(userStatus);
        System.out.println(builder);
        ByteArrayEntity bytes=new ByteArrayEntity(builder.build().toByteArray());
        Reporter.log("修改用户身份状态_"+incomeMessage+builder+ "}");
        return bytes;
    }

    //修改用户邀请码(幂等)
    public static ByteArrayEntity UserInviteCodeUpdateRequest(Integer channelId, String channelUserId, String inviteCode){
        UserBaseServiceProto.UserInviteCodeUpdateRequest.Builder builder= UserBaseServiceProto.UserInviteCodeUpdateRequest.newBuilder();
        builder.setChannelId(channelId);
        builder.setChannelUserId(channelUserId);
        builder.setInviteCode(inviteCode);
        System.out.println(builder);
        ByteArrayEntity bytes=new ByteArrayEntity(builder.build().toByteArray());
        Reporter.log("修改用户邀请码"+incomeMessage+builder+ "}");
        return bytes;
    }

    //修改用户标签(幂等)
    public static ByteArrayEntity UserInfoTagRequest( String channelUserId,Integer userTag ){
        UserBaseServiceProto.UserInfoTagRequest.Builder builder = UserBaseServiceProto.UserInfoTagRequest.newBuilder();
        builder.setChannelUserId(channelUserId);
        builder.setUserTag(userTag);
        System.out.println(builder);
        ByteArrayEntity bytes=new ByteArrayEntity(builder.build().toByteArray());
        Reporter.log("修改用户标签_"+incomeMessage+builder+ "}");
        return bytes;
    }

    //获取用户基础信息
    public static ByteArrayEntity UserInfoRequest(Integer channelId,String channelUserId){
        UserBaseServiceProto.UserInfoRequest.Builder builder=UserBaseServiceProto.UserInfoRequest.newBuilder();
        builder.setChannelId(channelId);
        builder.setChannelUserId(channelUserId);
        ByteArrayEntity bytes=new ByteArrayEntity(builder.build().toByteArray());
        System.out.println(builder);
        Reporter.log("获取用户基础信息_"+incomeMessage+builder+ "}");
        return bytes;
    }
    //根据手机号获取用户信息
    public static ByteArrayEntity UserInfoByMobileRequest(String mobile,String mobileAreaCode,Integer channelId,String responseClass){
        UserBaseServiceProto.UserInfoByMobileRequest.Builder builder=UserBaseServiceProto.UserInfoByMobileRequest.newBuilder();
        builder.setChannelId(channelId);
        builder.setMobile(mobile);
        //builder.setMobileAreaCode(mobileAreaCode);
        ByteArrayEntity bytes=new ByteArrayEntity(builder.build().toByteArray());
        System.out.println(builder);
        if(responseClass.equals("userInfoPdCombine")){
            Reporter.log("根据手机号获取用户信息(聚合)_"+incomeMessage+builder+ "}");
        }else{
            Reporter.log("根据手机号获取用户信息_"+incomeMessage+builder+ "}");
        }
        return bytes;
    }
    //修改手机号码
    public static ByteArrayEntity userMobileUpdateRequestConvertBuilder(Integer channelId, String mobileNew, String channelUserId, String mobileAreaCodeNew){
        UserBaseServiceProto.UserMobileUpdateRequest.Builder builder=  UserBaseServiceProto.UserMobileUpdateRequest.newBuilder();
        builder.setChannelId(channelId);
        builder.setMobileNew(mobileNew);
        builder.setChannelUserId(channelUserId);
        builder.setMobileAreaCodeNew(mobileAreaCodeNew);
        System.out.println(builder);
        ByteArrayEntity bytes=new ByteArrayEntity(builder.build().toByteArray());
        Reporter.log("修改手机号码_"+incomeMessage+builder+ "}");
        return bytes;
    }
    //修改密码
    public static ByteArrayEntity userPwdUpdateRequestConvertBuilder(String channelUserId, Integer channelId, String loginPwdNew){
        UserLoginInfoServiceProto.UserPwdUpdateRequest.Builder builder=UserLoginInfoServiceProto.UserPwdUpdateRequest.newBuilder();
        builder.setChannelUserId(channelUserId);
        builder.setChannelId(channelId);
        builder.setLoginPwdNew(loginPwdNew);
        System.out.println(builder);
        ByteArrayEntity bytes=new ByteArrayEntity(builder.build().toByteArray());
        Reporter.log("修改密码_"+incomeMessage+builder+ "}");
        return bytes;
    }
    //忘记密码 index 表要看有没有区号映射
    public static ByteArrayEntity userForgetPwdRequest(Integer channelId, String loginPwd, String mobile, String mobileAreaCode){
        UserLoginInfoServiceProto.UserForgetPwdRequest.Builder builder= UserLoginInfoServiceProto.UserForgetPwdRequest.newBuilder();
        builder.setChannelId(channelId);
        builder.setLoginPwd(loginPwd);
        builder.setMobile(mobile);
        builder.setMobileAreaCode(mobileAreaCode);
        ByteArrayEntity bytes=new ByteArrayEntity(builder.build().toByteArray());
        System.out.println(builder);
        Reporter.log("忘记密码_"+incomeMessage+builder+ "}");
        return bytes;
    }
    //用户实名认证
    public static ByteArrayEntity UserIdCardIdentifyRequestConvertBuilder(String channelUserId, Integer channelId, String realName, String idCardNum, String attachmentUrl){
        UserIdCardIdentifyServiceProto.UserIdCardIdentifyRequest.Builder builder = UserIdCardIdentifyServiceProto.UserIdCardIdentifyRequest.newBuilder();
        builder.setChannelUserId(channelUserId);
        builder.setChannelId(channelId);
        builder.setRealName(realName);
        builder.setIdCardNum(idCardNum);
        builder.setAttachmentUrl(attachmentUrl);
        System.out.println(builder);
        ByteArrayEntity bytes=new ByteArrayEntity(builder.build().toByteArray());
        Reporter.log("用户实名认证_"+incomeMessage+builder+ "}");
        return bytes;
    }
    //用户实名认证查询
    public static ByteArrayEntity userIdCardStatusQueryRequestConvertBuilder(String channelUserId, Integer channelId){
        UserIdCardIdentifyServiceProto.UserIdCardStatusQueryRequest.Builder builder=UserIdCardIdentifyServiceProto.UserIdCardStatusQueryRequest.newBuilder();
        builder.setChannelUserId(channelUserId);
        builder.setChannelId(channelId);
        System.out.println(builder);
        ByteArrayEntity bytes=new ByteArrayEntity(builder.build().toByteArray());
        Reporter.log("用户实名认证查询_"+incomeMessage+builder+ "}");
        return bytes;
    }
    //根据邀请码获取用户信息
    public static ByteArrayEntity userInviteCodeQueryRequest(String inviteCode, Integer channelId,String responseClass){
        UserBaseServiceProto.UserInviteCodeQueryRequest.Builder builder = UserBaseServiceProto.UserInviteCodeQueryRequest.newBuilder();
        builder.setChannelId(channelId);
        builder.setInviteCode(inviteCode);
        ByteArrayEntity bytes=new ByteArrayEntity(builder.build().toByteArray());
        if(responseClass.equals("userInfoPdCombine")){
            Reporter.log("邀请码获取用户信息(幂等)_"+incomeMessage+builder+ "}");
        }else{
            Reporter.log("邀请码获取用户信息_"+incomeMessage+builder+ "}");
        }
        return bytes;
    }
    //用户忘记登录密码
    public static ByteArrayEntity userForgetPwdRequest(Integer channelId, String loginPwd, String mobile){
        UserLoginInfoServiceProto.UserForgetPwdRequest.Builder builder=UserLoginInfoServiceProto.UserForgetPwdRequest.newBuilder();
        builder.setChannelId(channelId);
        builder.setLoginPwd(loginPwd);
        builder.setMobile(mobile);
        ByteArrayEntity bytes=new ByteArrayEntity(builder.build().toByteArray());
        return bytes;
    }

    //根据查询条件查询用户列表
    public static ByteArrayEntity UserBaseInfoByConditionRequest(String channelUserId,Integer channelId){
        UserBaseServiceProto.UserBaseInfoByConditionRequest.Builder builder= UserBaseServiceProto.UserBaseInfoByConditionRequest.newBuilder();
        builder.setChannelUserId(channelUserId);
        builder.setChannelId(channelId);
        ByteArrayEntity bytes=new ByteArrayEntity(builder.build().toByteArray());
        System.out.println(builder);
        Reporter.log("根据openId获取用户微信信息_"+incomeMessage+builder+ "}");
        return bytes;
    }

    /**
     * 支付宝中心
     */
    //绑定支付宝
    public static ByteArrayEntity userAliPayBidingRequest(String channelUserId, Integer channelId, String alipayRealname, String alipayAccount, String  alipayUserId){
        UserAliPayAuthServiceProto.UserAliPayBidingRequest.Builder builder=UserAliPayAuthServiceProto.UserAliPayBidingRequest.newBuilder();
        builder.setChannelUserId(channelUserId);
        builder.setChannelId(channelId);
        builder.setAlipayAccount(alipayAccount);
        builder.setAlipayRealname(alipayRealname);
        builder.setAlipayUserId(alipayUserId);
        ByteArrayEntity bytes=new ByteArrayEntity(builder.build().toByteArray());
        System.out.println(builder);
        Reporter.log("绑定支付宝_"+incomeMessage+builder+ "}");
        return bytes;
    }

    //支付宝授权
    public static ByteArrayEntity userAliPayAuthRequest(String channelUserId, Integer channelId, String alipayUserId){
        UserAliPayAuthServiceProto.UserAliPayAuthRequest.Builder builder=UserAliPayAuthServiceProto.UserAliPayAuthRequest.newBuilder();
        builder.setChannelUserId(channelUserId);
        builder.setChannelId(channelId);
        builder.setAlipayUserId(alipayUserId);
        ByteArrayEntity bytes=new ByteArrayEntity(builder.build().toByteArray());
        System.out.println(builder);
        Reporter.log("支付宝授权_"+incomeMessage+builder+ "}");
        return bytes;
    }
    //用户支付宝取消授权
    public static ByteArrayEntity userAliPayAuthCancelRequest(String channelUserId, Integer channelId){
        UserAliPayAuthServiceProto.UserAliPayAuthCancelRequest.Builder builder=UserAliPayAuthServiceProto.UserAliPayAuthCancelRequest.newBuilder();
        builder.setChannelId(channelId);
        builder.setChannelUserId(channelUserId);
        ByteArrayEntity bytes=new ByteArrayEntity(builder.build().toByteArray());
        System.out.println(builder);
        Reporter.log("取消支付宝授权_"+incomeMessage+builder+ "}");
        return bytes;
    }
    //用户支付宝授权查询
    public static ByteArrayEntity userAliPayAuthInfoRequest(String channelUserId, Integer channelId){
        UserAliPayAuthServiceProto.UserAliPayAuthInfoRequest.Builder builder =  UserAliPayAuthServiceProto.UserAliPayAuthInfoRequest.newBuilder();
        builder.setChannelUserId(channelUserId);
        builder.setChannelId(channelId);
        System.out.println(builder);
        ByteArrayEntity bytes=new ByteArrayEntity(builder.build().toByteArray());
        Reporter.log("支付宝授权查询_"+incomeMessage+builder+ "}");
        return bytes;
    }

    /**
     * 用户收货地址
     */

    //添加用户收货地址
    public static ByteArrayEntity userAddressInfoAddRequest(String channelUserId, Integer channelId, String address){
        UserAddressServiceProto.UserAddressInfoAddRequest.Builder builder=UserAddressServiceProto.UserAddressInfoAddRequest.newBuilder();
        builder.setChannelUserId(channelUserId);
        builder.setChannelId(channelId);
        builder.setAddress(address);
        ByteArrayEntity bytes=new ByteArrayEntity(builder.build().toByteArray());
        Reporter.log("添加用户收货地址_"+incomeMessage+builder+ "}");
        return bytes;
    }
    //获取用户收货地址
    public static ByteArrayEntity userAddressRequest(String channelUserId, Integer channelId, String addressId){
        UserAddressServiceProto.UserAddressRequest.Builder builder=UserAddressServiceProto.UserAddressRequest.newBuilder();
        builder.setChannelUserId(channelUserId);
        builder.setChannelId(channelId);
        builder.setAddressId(addressId);
        System.out.println(builder);
        ByteArrayEntity bytes=new ByteArrayEntity(builder.build().toByteArray());
        Reporter.log("获取用户收货地址_"+incomeMessage+builder+ "}");
        return bytes;
    }
    //删除收货地址
    public static ByteArrayEntity userAddressDelete(String channelUserId, Integer channelId, String addressId){
        UserAddressServiceProto.UserAddressRequest.Builder builder=UserAddressServiceProto.UserAddressRequest.newBuilder();
        builder.setChannelUserId(channelUserId);
        builder.setChannelId(channelId);
        builder.setAddressId(addressId);
        System.out.println(builder);
        ByteArrayEntity bytes=new ByteArrayEntity(builder.build().toByteArray());
        Reporter.log("删除用户收货地址_"+incomeMessage+builder+ "}");
        return bytes;
    }
    //更新用户收货地址
    public static ByteArrayEntity userAddressInfoUpdateRequest(String channelUserId, Integer channelId, String addressId, String username){
        UserAddressServiceProto.UserAddressInfoUpdateRequest.Builder builder=UserAddressServiceProto.UserAddressInfoUpdateRequest.newBuilder();
        builder.setChannelUserId(channelUserId);
        builder.setChannelId(channelId);
        builder.setAddressId(addressId);
        builder.setUserName(username);
        System.out.println(builder);
        ByteArrayEntity bytes=new ByteArrayEntity(builder.build().toByteArray());
        Reporter.log("更新用户收货地址_"+incomeMessage+builder+ "}");
        return bytes;
    }
    //更新用户收货地址标签
    public static ByteArrayEntity UserAddressTagRequest(String channelUserId,Integer channelId,String addressId,Integer addressTag){
        UserAddressServiceProto.UserAddressTagRequest.Builder builder= UserAddressServiceProto.UserAddressTagRequest.newBuilder();
        builder.setChannelUserId(channelUserId);
        builder.setChannelId(channelId);
        builder.setAddressId(addressId);
        builder.setAddressTag(addressTag);
        ByteArrayEntity bytes=new ByteArrayEntity(builder.build().toByteArray());
        System.out.println(builder);
        Reporter.log("更新用户收货地址标签_"+incomeMessage+builder+ "}");
        return bytes;
    }
    //分页查询户收货地址列表
    public static ByteArrayEntity userAddressPageRequest(String channelUserId, Integer channelId, Integer pageSize, Integer pageNum){
        UserAddressServiceProto.UserAddressPageRequest.Builder builder=UserAddressServiceProto.UserAddressPageRequest.newBuilder();
        builder.setChannelUserId(channelUserId);
        builder.setChannelId(channelId);
        builder.setPageNum(pageNum);
        builder.setPageSize(pageSize);
        ByteArrayEntity bytes=new ByteArrayEntity(builder.build().toByteArray());
        Reporter.log("分页查询户收货地址列表_"+incomeMessage+builder+ "}");
        return bytes;
    }
    //获取省市区域树
    public static ByteArrayEntity UserSysSubAreaRequest(String parentId){
        UserAddressServiceProto.UserSysSubAreaRequest.Builder builder=UserAddressServiceProto.UserSysSubAreaRequest.newBuilder();
        builder.setParentId(parentId);
        ByteArrayEntity bytes=new ByteArrayEntity(builder.build().toByteArray());
        Reporter.log("获取省市区域树_"+incomeMessage+builder+ "}");
        return bytes;
    }

    /**
     *  淘宝中心
     */

    //淘宝授权
    public static ByteArrayEntity HsrjUserTaobaoAuthRequest(String channelUserId,Long relationId,Long specialId,Long companyId,Long tbAccountId,String tbAccount){
        HsrjUserTaobaoAuthInfoServiceProto.HsrjUserTaobaoAuthRequest.Builder builder = HsrjUserTaobaoAuthInfoServiceProto.HsrjUserTaobaoAuthRequest.newBuilder();
        builder.setChannelUserId(channelUserId);
        builder.setRelationId(relationId);
        builder.setSpecialId(specialId);
        builder.setCompanyId(companyId);
        builder.setTbAccountId(tbAccountId);
        builder.setTbAccount(tbAccount);
        ByteArrayEntity bytes=new ByteArrayEntity(builder.build().toByteArray());
        System.out.println(builder);
        Reporter.log("淘宝授权_"+incomeMessage+builder+ "}");
        return  bytes;
    }
    //用户淘宝取消授权
    public static ByteArrayEntity HsrjUserTaobaoAuthCancelRequest(String channelUserId){
        HsrjUserTaobaoAuthInfoServiceProto.HsrjUserTaobaoAuthCancelRequest.Builder builder= HsrjUserTaobaoAuthInfoServiceProto.HsrjUserTaobaoAuthCancelRequest.newBuilder();
        builder.setChannelUserId(channelUserId);
        ByteArrayEntity bytes=new ByteArrayEntity(builder.build().toByteArray());
        System.out.println(builder);
        Reporter.log("用户淘宝取消授权_"+incomeMessage+builder+ "}");
        return  bytes;
    }
    //用户淘宝授权信息查询(
    public static ByteArrayEntity HsrjUserTaobaoAuthQueryRequest(String channelUserId,String tbAccount,Long companyId,Long tbAccountId){
        HsrjUserTaobaoAuthInfoServiceProto.HsrjUserTaobaoAuthQueryRequest.Builder builder=HsrjUserTaobaoAuthInfoServiceProto.HsrjUserTaobaoAuthQueryRequest.newBuilder();
        builder.setChannelUserId(channelUserId);
        builder.setTbAccount(tbAccount);
        builder.setCompanyId(companyId);
        builder.setTbAccountId(tbAccountId);
        ByteArrayEntity bytes=new ByteArrayEntity(builder.build().toByteArray());
        System.out.println(builder);
        Reporter.log("用户淘宝授权信息查询_"+incomeMessage+builder+ "}");
        return  bytes;
    }

    /**
     * 用户微信中心
     */

    //微信绑定
    public static ByteArrayEntity userWeChatAuthRequest(String appId, Integer channelId, String channelUserId, String openId){
        UserWeChatAuthServiceProto.UserWeChatAuthRequest.Builder builder=UserWeChatAuthServiceProto.UserWeChatAuthRequest.newBuilder();
        builder.setAppId(appId);
        builder.setChannelId(channelId);
        builder.setChannelUserId(channelUserId);
        builder.setOpenId(openId);
        //System.out.println("入参 \n："+builder);
        ByteArrayEntity bytes=new ByteArrayEntity(builder.build().toByteArray());
        Reporter.log("微信绑定_"+incomeMessage+builder+ "}");
        return bytes;
    }
    //微信解绑
    public static ByteArrayEntity userWeChatAuthUnBindRequest(String openId, Integer channelId, String channelUserId, String appId ){
        UserWeChatAuthServiceProto.UserWeChatAuthUnBindRequest.Builder builder=UserWeChatAuthServiceProto.UserWeChatAuthUnBindRequest.newBuilder();
        builder.setOpenId(openId);
        builder.setChannelId(channelId);
        builder.setChannelUserId(channelUserId);
        builder.setAppId(appId);
        ByteArrayEntity bytes=new ByteArrayEntity(builder.build().toByteArray());
        System.out.println(builder);
        Reporter.log("微信绑定解绑_"+incomeMessage+builder+ "}");
        return bytes;
    }
    //微信一键登录
    public static ByteArrayEntity userWeChatOneKeyLoginRequest(Integer channelId, String mobile, String inviteChannelUserId, String mobileAreaCode){
        UserWeChatAuthServiceProto.UserWeChatOneKeyLoginRequest.Builder builder=UserWeChatAuthServiceProto.UserWeChatOneKeyLoginRequest.newBuilder();
        builder.setChannelId(channelId);
        builder.setMobile(mobile);
        builder.setInviteChannelUserId(inviteChannelUserId);
        builder.setMobileAreaCode(mobileAreaCode);
        //System.out.println(builder);
        ByteArrayEntity bytes=new ByteArrayEntity(builder.build().toByteArray());
        Reporter.log("微信一键登录_"+incomeMessage+builder+ "}");
        return bytes;
    }
    //用户微信登录(幂等)
    public static ByteArrayEntity UserWeChatAuthLoginRequest(Integer channelId,String channelUserId,String openId,String appId,String method){
        UserWeChatAuthServiceProto.UserWeChatAuthLoginRequest.Builder builder =UserWeChatAuthServiceProto.UserWeChatAuthLoginRequest.newBuilder();

        builder.setChannelId(channelId);
        builder.setChannelUserId(channelUserId);
        builder.setOpenId(openId);
        //builder.setAppId(appId);
        ByteArrayEntity bytes=new ByteArrayEntity(builder.build().toByteArray());
        System.out.println(builder);

        if(method.equals("getWeChatUserByOpenIdOrUnionId")){
            Reporter.log("根据chanelId和openId查询用户微信信息_"+incomeMessage+builder+ "}");
        }else {
            Reporter.log("用户微信登录(幂等)_"+incomeMessage+builder+ "}");
        }
        return bytes;
    }
    //根据渠道用户Id查询用户微信列表信息(
    public static ByteArrayEntity  getUserWeChatAuthByChannelUserIdRequest(Integer channelId,String channelUserId,String appId){
        UserWeChatAuthServiceProto.getUserWeChatAuthByChannelUserIdRequest.Builder builder= UserWeChatAuthServiceProto.getUserWeChatAuthByChannelUserIdRequest.newBuilder();
        builder.setChannelId(channelId);
        builder.setChannelUserId(channelUserId);
        builder.setAppId(appId);
        ByteArrayEntity bytes=new ByteArrayEntity(builder.build().toByteArray());
        System.out.println(builder);
        Reporter.log("根据渠道用户Id查询用户微信列表信息_"+incomeMessage+builder+ "}");
        return bytes;
    }
    //根据openId获取用户微信信息
    public static ByteArrayEntity getUserWeChatAuthByOpenIdRequest(Integer channelId,String openId,String appId){
        UserWeChatAuthServiceProto.getUserWeChatAuthByOpenIdRequest.Builder builder= UserWeChatAuthServiceProto.getUserWeChatAuthByOpenIdRequest.newBuilder();
        builder.setChannelId(channelId);
        builder.setOpenId(openId);
        builder.setAppId(appId);
        ByteArrayEntity bytes=new ByteArrayEntity(builder.build().toByteArray());
        System.out.println(builder);
        Reporter.log("根据openId获取用户微信信息_"+incomeMessage+builder+ "}");
        return bytes;
    }
    //根据chaanelid和openid查询用户微信信息
//    public static ByteArrayEntity UserWeChatAuthLoginRequest(Integer channelId,String channelUserId,String openId,String appId){
//        UserWeChatAuthServiceProto.UserWeChatAuthLoginRequest.Builder builder= UserWeChatAuthServiceProto.UserWeChatAuthLoginRequest.newBuilder();
//        builder.setAppId(appId);
//        builder.setChannelId(channelId);
//        builder.setChannelUserId(channelUserId);
//        builder.setOpenId(openId);
//        ByteArrayEntity bytes=new ByteArrayEntity(builder.build().toByteArray());
//        System.out.println(builder);
//        Reporter.log("根据openId获取用户微信信息_"+incomeMessage+builder+ "}");
//        return bytes;
//    }

    //是否绑定过手机号(幂等)
    public static ByteArrayEntity UserWeChatAuthCheckPhoneRequest(Integer channelId,String channelUserId,String openId){
        UserWeChatAuthServiceProto.UserWeChatAuthCheckPhoneRequest.Builder builder = UserWeChatAuthServiceProto.UserWeChatAuthCheckPhoneRequest.newBuilder();
        builder.setChannelId(channelId);
        builder.setChannelUserId(channelUserId);
        builder.setOpenId(openId);
        ByteArrayEntity bytes=new ByteArrayEntity(builder.build().toByteArray());
        System.out.println(builder);
        Reporter.log("是否绑定过手机号(幂等)_"+incomeMessage+builder+ "}");
        return bytes;
    }
    //设置微信号
    public static ByteArrayEntity UserWeChatWxNoRequest(){
        //openid oBrt31epHkTX4M9lpH12mIOxsdm4
        //unionid oBrt31epHkTX4M9lpH12mIOxsdmUnqq
        //wxno 9358324293
        //channeluserid 9358324293
        //channelid 0
        UserWeChatAuthServiceProto.UserWeChatWxNoRequest.Builder builder=UserWeChatAuthServiceProto.UserWeChatWxNoRequest.newBuilder();
        builder.setOpenId("oBrt31epHkTX4M9lpH12mIOxsdm4");
        builder.setUnionId("oBrt31epHkTX4M9lpH12mIOxsdmUnqq");
        builder.setWxNo("9358324293");
        builder.setChannelUserId("176879");
        builder.setChannelId(0);
        //builder.setMobileAreaCode("86");
        ByteArrayEntity bytes=new ByteArrayEntity(builder.build().toByteArray());
        Reporter.log("设置微信号_"+incomeMessage+builder+ "}");
        return null;
    }

    /**
     * 手机国际区号
     */
    //根据手机国际区号查询手机国际区号信息查询(幂等)
    public static ByteArrayEntity UserMobileAreaCodeRequest(String mobileAreaCode,String ccIso){
        UserMobileAreaServiceProto.UserMobileAreaCodeRequest.Builder builder =UserMobileAreaServiceProto.UserMobileAreaCodeRequest.newBuilder();
        builder.setMobileAreaCode(mobileAreaCode);
        builder.setCcIso(ccIso);
        ByteArrayEntity bytes=new ByteArrayEntity(builder.build().toByteArray());
        System.out.println(builder);
        Reporter.log("根据手机国际区号查询手机国际区号信息查询(幂等)_"+incomeMessage+builder+ "}");
        return bytes;
    }


    /**
     * 团长关系信息
     */
    //绑定(新增)团长关系(幂等)
    public static ByteArrayEntity UserTeamRelationRegisterRequest(String channelUserId,Integer channelId,Integer appType,String teamUserId){
        UserTeamRelationServiceProto.UserTeamRelationRegisterRequest.Builder builder=UserTeamRelationServiceProto.UserTeamRelationRegisterRequest.newBuilder();
        builder.setChannelUserId(channelUserId);
        builder.setChannelId(channelId);
        builder.setAppType(appType);
        builder.setTeamUserId(teamUserId);
        ByteArrayEntity bytes=new ByteArrayEntity(builder.build().toByteArray());
        System.out.println(builder);
        Reporter.log("绑定(新增)团长关系(幂等)_"+incomeMessage+builder+ "}");
        return bytes;
    }

    //解绑(删除)团长关系(幂等)
    public static ByteArrayEntity UserTeamRelationUntyingRequest(String channelUserId){
        //传对象
        UserTeamRelationServiceProto.UserTeamRelationCommonRequest.Builder userTeamRelationCommonRequest=UserTeamRelationServiceProto.UserTeamRelationCommonRequest.newBuilder();
        userTeamRelationCommonRequest.setChannelId(1);
        userTeamRelationCommonRequest.setChannelUserId(channelUserId);
        userTeamRelationCommonRequest.setAppType(1);

        UserTeamRelationServiceProto.UserTeamRelationUntyingRequest.Builder builder=UserTeamRelationServiceProto.UserTeamRelationUntyingRequest.newBuilder();
        builder.setCommonRequest(userTeamRelationCommonRequest);
        builder.setTeamUserId("5201314");
        ByteArrayEntity bytes=new ByteArrayEntity(builder.build().toByteArray());
        System.out.println(builder);
        Reporter.log("解绑(删除)团长关系(幂等)_"+incomeMessage+builder+ "}");
        return bytes;
    }

    /**
     * 团长信息
     */
    //团长注册
    public static ByteArrayEntity UserTeamInfoRegisterRequest(UserTeamInfo userTeamInfo){
        UserTeamInfoServiceProto.UserTeamInfoRegisterRequest.Builder registerBuilder = UserTeamInfoServiceProto.UserTeamInfoRegisterRequest.newBuilder();
        registerBuilder.setAppType(userTeamInfo.getAppType());
        registerBuilder.setRealName(userTeamInfo.getRealName());
        registerBuilder.setChannelId(userTeamInfo.getChannelId());
        registerBuilder.setChannelUserId(userTeamInfo.getChannelUserId());
        registerBuilder.setAuditorName(userTeamInfo.getAuditorName());
        registerBuilder.setDeposit(userTeamInfo.getDeposit());
        registerBuilder.setEmergencyNumber(userTeamInfo.getEmergencyNum());
        registerBuilder.setGender(userTeamInfo.getGender());
        registerBuilder.setHeadNum(userTeamInfo.getHeadNum());
        registerBuilder.setIsShowCommission(userTeamInfo.getIsShownCommission());
        registerBuilder.setIsVirtual(userTeamInfo.getIsVirtual());
        registerBuilder.setLicenseImg(userTeamInfo.getLicenseImg());
        registerBuilder.setMobile(userTeamInfo.getMobile());
        registerBuilder.setOperatorId(userTeamInfo.getOperatorId());
        registerBuilder.setOperatorLongId(userTeamInfo.getOperatorLongId());
        registerBuilder.setOperatorTel(userTeamInfo.getOperatorTel());
        registerBuilder.setRecommend(userTeamInfo.getRecommend());
        registerBuilder.setSource(userTeamInfo.getSource());
        registerBuilder.setStatus(userTeamInfo.getStatus());
        registerBuilder.setStopReason(userTeamInfo.getStopReason());
        registerBuilder.setCompanyId(userTeamInfo.getCompanyId());
        registerBuilder.setWeixin(userTeamInfo.getWeixin());
        registerBuilder.setCompanyName(userTeamInfo.getCompanyName());
        registerBuilder.setStartTime(userTeamInfo.getStartTime());
        registerBuilder.setStopEndTime(userTeamInfo.getStopEndTime());
        registerBuilder.setAuditTime(userTeamInfo.getAuditTime());
        registerBuilder.setAuditorName(userTeamInfo.getAuditorName());

        UserTeamInfoServiceProto.UserTeamAddressRegister.Builder userTeamAddressRegisteruilder = UserTeamInfoServiceProto.UserTeamAddressRegister.newBuilder();
        userTeamAddressRegisteruilder.setChannelUserId(userTeamInfo.getChannelUserId());
        userTeamAddressRegisteruilder.setChannelId(userTeamInfo.getChannelId());
        userTeamAddressRegisteruilder.setAppType(1);
        userTeamAddressRegisteruilder.setProvinceCode(86);
        userTeamAddressRegisteruilder.setCityCode(86);
        userTeamAddressRegisteruilder.setDistrictCode(86);
        userTeamAddressRegisteruilder.setAdcode(86);
        userTeamAddressRegisteruilder.setProvince("广东");
        userTeamAddressRegisteruilder.setCity("广州");
        userTeamAddressRegisteruilder.setDistrict("海珠区");
        userTeamAddressRegisteruilder.setAddress("天意酒店");
        userTeamAddressRegisteruilder.setCommunity("幸坛小区");
        userTeamAddressRegisteruilder.setAddressDetail("520");
        userTeamAddressRegisteruilder.setMemberNumber(20);
        userTeamAddressRegisteruilder.setLongitude(5201314);
        userTeamAddressRegisteruilder.setLatitude(5201314);
        userTeamAddressRegisteruilder.setGeoHash("fuck");
        userTeamAddressRegisteruilder.setGeoHash5Km("fuck");

        registerBuilder.setAddress(userTeamAddressRegisteruilder);
        ByteArrayEntity bytes=new ByteArrayEntity(registerBuilder.build().toByteArray());
        DataUtils.logBuilder(registerBuilder, "注册团长信息(幂等)_");
        return bytes;
    }




}
