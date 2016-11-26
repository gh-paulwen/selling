<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.lang.*" %>
<%
  String base = request.getContextPath();
  request.setAttribute("_base", base);
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>登录</title>
</head>
<body>
  <h1 id="message"></h1>
  <form>
  <table >
    <tr>
      <td><input id="cellphone" placeholder="手机号码"></td>
    </tr>
    <tr>
      <td><input id="password" type="password" placeholder="密码"></td>
      <td>初次登录密码请留空</td>
    </tr>
    <tr>
      <td><input type="text" placeholder="验证码" id="txtVerifyCode"></td>
      <td><img id="imgVerifyCode" src="${_base }/employee/imageVerifyCode"></td>
    </tr>
    <tr>
      <td><input type="button" id="btnLogin" value="登录"></td>
      <td><input type="button" id="btnForget" value="忘记密码"></td>
    </tr>
  </table>
  </form>
  <script src="http://libs.baidu.com/jquery/1.9.1/jquery.min.js" type="text/javascript"></script>
  <script type="text/javascript">
    $(document).ready(function(){
    	$("#btnLogin").click(function(){
    		$.ajax({
    			url:"${_base}/employee/submitLogin",
    			type:"POST",
    			async:true,
    			data:{"cellphone":$("#cellphone").val(),"password":$("#password").val(),"verifyCode":$("#txtVerifyCode").val()},
    			dataType:"json",
    			success:function(json){
    				$("#message").html(json.message);    		
    				if(json.redirect == 1){
    					window.location.href="${_base}/employee/smsVerify";	
    				}
    				if(json.message == "登录成功"){
    					window.location.href="${_base}/employee/index";
    				}
    				$("#imgVerifyCode").attr("src","${_base }/employee/imageVerifyCode?test="+new Date().getMilliseconds());
    			}
    		});
    	});
    	
    	$("#btnForget").click(function(){
    		if($("#cellphone").val()==""){
    			$("#message").html("如忘记密码，请正确填写手机号码，密码、验证码留空");
    			return;
    		}
    		$.ajax({
    			url : "${_base}/employee/resetPassword",
    			type : "POST",
          data : {"cellphone":$("#cellphone").val()},
    			async:true,
    			dataType : "json",
    			success : function(json){
    				$("#message").html(json.message);
    				if(json.redirect == 1){
    					window.location.href = "${_base}/employee/smsVerify";
    				}
    			}
    		});
    	});
    	
    	$("#imgVerifyCode").click(function(){
    		$("#imgVerifyCode").attr("src","${_base }/employee/imageVerifyCode?test="+new Date().getMilliseconds());
    	});
    });
  </script>
</body>
</html>