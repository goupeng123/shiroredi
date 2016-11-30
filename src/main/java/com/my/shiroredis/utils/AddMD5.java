package com.my.shiroredis.utils;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.junit.Test;

/**
 * 
 * @ClassName: AddMD5
 * @Description: 为密码加密
 * @author: goupeng
 * @date: 2016-2-17 下午4:10:28
 */
public class AddMD5 {
	  private String password;
      public AddMD5(String password){
    	  this.password = password ;
      }
      /**
       * 
       * @Title: getMD5
       * @Description: 获取加密密码
       * @return
       * @return: String
       */
      public String getMD5(){
    	  String salt = "hello";
    	  int k =1;
    	  SimpleHash hash = new SimpleHash("md5", password, salt,k);
    	  return hash.toString();
      }
      
      //测试使用
      
      public  void tst(){
    	  String pasw = "goupeng";
    	  SimpleHash hash = new SimpleHash("md5",pasw,"hello",1);
    	  System.out.println(hash.toString());
      }
}
