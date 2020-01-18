package com.example.utils;

import com.hs.user.base.proto.UserBaseServiceProto;
import org.testng.Reporter;

import java.util.Random;

public class DataUtils {

    //length用户要求产生字符串的长度
    public static String getRandomString(int length){
        String str="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random=new Random();
        StringBuffer sb=new StringBuffer();
        for(int i=0;i<length;i++){
            int number=random.nextInt(62);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }

    //截取返回内容  10  1 "userId" ","
    public static String substring(String result,String beginString,Integer beginindex,String endString,Integer endindex){
        int begin = result.indexOf(beginString) + beginindex;
        int end = result.indexOf(endString) - endindex;
        String element = result.substring(begin, end);
        return  element;
    }

    //builder记录
    public static void logBuilder(Object object,String interfaceName){
        System.out.println("object is:"+object);
        Reporter.log(interfaceName+"传入参数:{"+object+"}");
    }

    //数据库记录 1是部分匹配  2是全部匹配
    public static void logDatabase(Integer code,String fieldName,String targetName){
        if (code==1){
            Reporter.log("数据库部分匹配："+fieldName+"的值得变更为："+targetName);
        }else{
            Reporter.log("数据库全部匹配："+targetName);
        }
    }

    public static void logResponse(String response){
        Reporter.log("返回值:["+response+"]");
    }

    public static void lopParam(String interfaceName, com.google.protobuf.GeneratedMessageV3.Builder<?> builder){
        Reporter.log(interfaceName+"_传入参数：{"+builder+"}");
    }

    public static String getRandomChannelUserId(Integer length){
        String str="0123456789";
        Random random=new Random();
        StringBuffer sb=new StringBuffer();
        for(int i=0;i<length;i++){
            int number=random.nextInt(9);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }
}
