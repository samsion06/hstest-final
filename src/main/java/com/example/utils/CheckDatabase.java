package com.example.utils;
import com.example.domain.*;
import com.example.mapper.TeamRealtionInfoMapper;
import com.example.mapper.UserBaseInfoMapper;
import org.testng.Assert;
import org.testng.Reporter;

public class CheckDatabase {

    private static UserAliPayInfo dataBaseUserAliPayInfo;
    private static UserWeChatInfo dataBaseUserWeChatInfos;
    private static UserAddressInfo dataBaseUserAddressInfo;
    private static UserBaseInfo dataBaseUserBaseInfo;
    private static UserLoginInfo dataBaseUserLoginInfo;
    private static UserTaobaoInfo dataBaseUserTaobaoInfo;
    private static UserTeamInfo dataBaseUserTeamInfo;
    private static UserRleationInfo dataBaseUserRleationInfo;
    private static HsrjUserInfo dataBaseHsrjUserInfo;

    //数据库检查
    public static void CheckDatabaseUserUserWeChatInfo(UserBaseInfoMapper userBaseInfoMapper,String method, UserWeChatInfo userWeChatInfo) {
        switch (method) {
            //微信解绑
            case "WeChatInfoUnbind"://微信解除绑定
                dataBaseUserWeChatInfos = userBaseInfoMapper.queryWeChatInfo(userWeChatInfo.getChannelUserId());
                Assert.assertEquals(userWeChatInfo.getIsDelete(), dataBaseUserWeChatInfos.getIsDelete());
                DataUtils.logDatabase(1,"is_delete",dataBaseUserWeChatInfos.getIsDelete().toString());
                break;
            case "WeChatInfoBind": //微信绑定
                dataBaseUserWeChatInfos = userBaseInfoMapper.queryWeChatInfo(userWeChatInfo.getChannelUserId());
                Assert.assertEquals(userWeChatInfo.getChannelUserId(), dataBaseUserWeChatInfos.getChannelUserId());
                DataUtils.logDatabase(2, null, dataBaseUserWeChatInfos.toString());
                break;
        }
    }

    public static void CheckDatabaseUserUserAddressInfo(UserBaseInfoMapper userBaseInfoMapper,String method,UserAddressInfo userAddressInfo){
        switch (method) {
            case "AddressUpadate"://更新收货地址
                dataBaseUserAddressInfo = userBaseInfoMapper.queryUserAddressInfo(userAddressInfo.getChannelUserId());
                String name = userAddressInfo.getName();
                Assert.assertEquals(userAddressInfo.getName(), dataBaseUserAddressInfo.getName()); //名称是否有更新
                DataUtils.logDatabase(1,"name",dataBaseUserAddressInfo.getName());
                break;
            case "AddressDelete"://删除收货地址
                dataBaseUserAddressInfo = userBaseInfoMapper.queryUserAddressInfo(userAddressInfo.getChannelUserId());
                Assert.assertEquals(userAddressInfo.getIsDelete(), dataBaseUserAddressInfo.getIsDelete());
                DataUtils.logDatabase(1,"is_delete", dataBaseUserAddressInfo.getIsDelete().toString());
                break;
        }
    }

    //支付宝信息
    public static void CheckDatabaseUserAliPayInfo(UserBaseInfoMapper userBaseInfoMapper,String method,UserAliPayInfo userAliPayInfo){
        switch (method) {
            case "AliPayBind"://支付宝绑定
                dataBaseUserAliPayInfo = userBaseInfoMapper.queryAliPayInfo(userAliPayInfo.getChannelUserId());
                Assert.assertEquals(userAliPayInfo.getChannelUserId(),dataBaseUserAliPayInfo.getChannelUserId());
                DataUtils.logDatabase(2, null, dataBaseUserAliPayInfo.toString());
                break;
            case "AliPayAuth"://支付宝授权
                dataBaseUserAliPayInfo = userBaseInfoMapper.queryAliPayInfo(userAliPayInfo.getChannelUserId());
                Assert.assertEquals(userAliPayInfo.getStatus(),dataBaseUserAliPayInfo.getStatus());
                DataUtils.logDatabase(1,"Status",dataBaseUserAliPayInfo.getStatus()+"");
                break;
            case "AliPayCancel"://取消授权
                dataBaseUserAliPayInfo = userBaseInfoMapper.queryAliPayInfo(userAliPayInfo.getChannelUserId());
                int unbindStatus = userAliPayInfo.getStatus();
                Assert.assertEquals(userAliPayInfo.getStatus(),dataBaseUserAliPayInfo.getStatus());
                DataUtils.logDatabase(1,"Status",dataBaseUserAliPayInfo.getStatus().toString());
                break;
        }
    }

    //淘宝信息
    public static void CheckDatabaseUserTaobaoInfo(UserBaseInfoMapper userBaseInfoMapper,String method,UserTaobaoInfo userTaobaoInfo){
        switch (method) {
            case "TaoBaoAuth"://淘宝授权
                dataBaseUserTaobaoInfo = userBaseInfoMapper.queryUserTaobaoInfo(userTaobaoInfo.getChannelUserId());
                Assert.assertEquals(userTaobaoInfo.getChannelUserId(),dataBaseUserTaobaoInfo.getChannelUserId());
                DataUtils.logDatabase(2, null, dataBaseUserTaobaoInfo.toString());
                break;
            case "TaoBaoCancel"://取消授权
                dataBaseUserTaobaoInfo = userBaseInfoMapper.queryUserTaobaoInfo(userTaobaoInfo.getChannelUserId());
                Assert.assertEquals(userTaobaoInfo.getCreateTime(),null);
                DataUtils.logDatabase(2, null, dataBaseUserTaobaoInfo.toString());
                break;
        }
    }

    //用户信息
    public static void CheckDatabaseUserBaseInfo(UserBaseInfoMapper userBaseInfoMapper,String method,
                                                 UserBaseInfo userBaseInfo,UserLoginInfo userLoginInfo){
        switch (method) {
            case "NickNameUpdate": //昵称更新
                System.out.println(userBaseInfoMapper);
                dataBaseUserBaseInfo = userBaseInfoMapper.queryUserBaseInfo(userLoginInfo.getChannelUserId());
                Assert.assertEquals(userBaseInfo.getNickName(), dataBaseUserBaseInfo.getNickName());
                DataUtils.logDatabase(1,"nick_name",dataBaseUserBaseInfo.getNickName());
                break;
            case "HeadUrlImg": //修改头像
                dataBaseUserBaseInfo = userBaseInfoMapper.queryUserBaseInfo(userLoginInfo.getChannelUserId());
                Assert.assertEquals(userBaseInfo.getHeadImg(), dataBaseUserBaseInfo.getHeadImg());
                DataUtils.logDatabase(1,"head_img",dataBaseUserBaseInfo.getHeadImg());
                break;
            case "MobileUpdate"://修改手机
                dataBaseUserLoginInfo = userBaseInfoMapper.queryUserLoginInfo(userLoginInfo.getChannelUserId());
                System.out.println(dataBaseUserLoginInfo.getLoginName());
                Assert.assertEquals(userLoginInfo.getLoginName(), dataBaseUserLoginInfo.getLoginName());
                DataUtils.logDatabase(1,"login_name",dataBaseUserLoginInfo.getLoginName());
                break;
            case "PwdUpdate"://修改密码
                dataBaseUserLoginInfo = userBaseInfoMapper.queryUserLoginInfo(userLoginInfo.getChannelUserId());
                Assert.assertEquals(userLoginInfo.getLoginPwd(), dataBaseUserLoginInfo.getLoginPwd());
                DataUtils.logDatabase(1,"login_pwd",dataBaseUserLoginInfo.getLoginPwd());
                break;
            case "StatusUpdate"://修改用户身份状态
                dataBaseUserBaseInfo = userBaseInfoMapper.queryUserBaseInfo(userLoginInfo.getChannelUserId());
                Assert.assertEquals(userBaseInfo.getStatus(), dataBaseUserBaseInfo.getStatus());
                DataUtils.logDatabase(1,"status",dataBaseUserBaseInfo.getStatus().toString());
                break;
        }
    }

    public static void CheckDatabaseHsrjUserInfo(UserBaseInfoMapper userBaseInfoMapper,String method,HsrjUserInfo hsrjUserInfo){
        switch (method) {
            case "pushNoUpdate"://更新邀请码
                dataBaseHsrjUserInfo=userBaseInfoMapper.queryHsrjUserInfo(hsrjUserInfo.getChannelUserId());
                System.out.println("数据库取出来的"+dataBaseHsrjUserInfo.getChannelUserId());
                Assert.assertEquals(hsrjUserInfo.getPushNo(), dataBaseHsrjUserInfo.getPushNo());
                DataUtils.logDatabase(1,"pushNo",dataBaseHsrjUserInfo.getPushNo());
                break;
            case "userTagStatusUpdate": //更新用户标签
                dataBaseHsrjUserInfo=userBaseInfoMapper.queryHsrjUserInfo(hsrjUserInfo.getChannelUserId());
                System.out.println("database获取的值："+dataBaseHsrjUserInfo.getUserTagStatus());
                Assert.assertEquals(hsrjUserInfo.getUserTagStatus(), dataBaseHsrjUserInfo.getUserTagStatus());
                DataUtils.logDatabase(1,"user_tag_update",dataBaseHsrjUserInfo.getUserTagStatus().toString());
                break;
        }
    }

    //团长信息
    public static void CheckDatabaseUserTeamInfo(TeamRealtionInfoMapper teamRealtionInfoMapper, String method, UserTeamInfo userTeamInfo) {
        switch (method) {
            case "teamRegister":
                dataBaseUserTeamInfo = teamRealtionInfoMapper.queryUserTeamInfo(userTeamInfo.getChannelUserId());
                Assert.assertEquals(userTeamInfo.getChannelUserId(),dataBaseUserTeamInfo.getChannelUserId());
                break;
            case "teamUpdate":
                dataBaseUserTeamInfo = teamRealtionInfoMapper.queryUserTeamInfo(userTeamInfo.getChannelUserId());
                //读库  预期 ：目标
                Assert.assertEquals(userTeamInfo.getRealName(),dataBaseUserTeamInfo.getRealName());
                //记录
                DataUtils.logDatabase(1, "real_name", dataBaseUserTeamInfo.getRealName());
                break;
            case "teamDelete":
                dataBaseUserTeamInfo = teamRealtionInfoMapper.queryUserTeamInfo(userTeamInfo.getChannelUserId());
                Assert.assertEquals(userTeamInfo.getIsDelete(), dataBaseUserTeamInfo.getIsDelete());
                DataUtils.logDatabase(1, "is_delete", dataBaseUserTeamInfo.getIsDelete() + "");
                break;
        }
    }

    //团长关系
    public static void CheckDatabaseUserUserRleationInfo(TeamRealtionInfoMapper teamRealtionInfoMapper,String method,UserRleationInfo userRleationInfo){
        switch (method) {
            case "relationRegister": //绑定团长关系
                dataBaseUserRleationInfo = teamRealtionInfoMapper.queryUserRleationInfo(userRleationInfo.getChannelUserId());
                Assert.assertEquals(userRleationInfo.getChannelUserId(), dataBaseUserRleationInfo.getChannelUserId());
                DataUtils.logDatabase(2, null, dataBaseUserRleationInfo.toString());
                break;
            case "relationDelete": //解除绑定团长关系
                dataBaseUserRleationInfo = teamRealtionInfoMapper.queryUserRleationInfo(userRleationInfo.getChannelUserId());
                Assert.assertEquals(userRleationInfo.getIsDelete(), dataBaseUserRleationInfo.getIsDelete());
                DataUtils.logDatabase(1, "is_delete", dataBaseUserRleationInfo.getIsDelete() + "");
                break;
        }
    }
}