package com.zhklong.selling.util;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.aliyuncs.sms.model.v20160927.SingleSendSmsRequest;

/**
 * 发送短信验证码的工具
 * @author paul
 * @since 2016-11-17 
 * 
 * */
public final class SMSUtil {

	public SMSUtil() {
	}

	private IAcsClient client;
	
	private boolean inited;

	public void init(){
		IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou",
				access, secret);
		try {
			DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", "Sms",
					"sms.aliyuncs.com");
		} catch (ClientException e) {
			e.printStackTrace();
		}
		client = new DefaultAcsClient(profile);
		inited = true;
	}

	private String signName;

	private String template;

	private String access;

	private String secret;

	public String getSignName() {
		return signName;
	}

	public void setSignName(String signName) {
		this.signName = signName;
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	public String getAccess() {
		return access;
	}

	public void setAccess(String access) {
		this.access = access;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	/**
	 * 发送短信验证码
	 * @param phone
	 *            手机号码
	 * @param code
	 *            验证码
	 * 
	 * */
	public void sendVerifyCode(String phone, String code) {
		
		if(inited == false){
			synchronized (this) {
				if(inited == false){
					init();
				}
			}
		}				
		SingleSendSmsRequest request = new SingleSendSmsRequest();
		request.setSignName(signName);
		request.setTemplateCode(template);
		request.setParamString(String.format(
				"{\"name\":\"%s\",\"code\":\"%s\"}", phone, code));
		request.setRecNum(phone);

		try {
			client.getAcsResponse(request);
		} catch (ServerException e) {
			e.printStackTrace();
		} catch (ClientException e) {
			e.printStackTrace();
		}
	}

}
