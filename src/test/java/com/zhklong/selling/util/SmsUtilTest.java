package com.zhklong.selling.util;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.zhklong.selling.BaseTest;

public class SmsUtilTest extends BaseTest{
	
	@Autowired
	private SMSUtil smsUtil ;
	
	@Test
	public void testSend(){
		smsUtil.sendVerifyCode("15622730295", "123456");
	}

}
