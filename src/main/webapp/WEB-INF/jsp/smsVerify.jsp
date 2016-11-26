<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
  String base = request.getContextPath();
  request.setAttribute("_base", base);
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>短信验证</title>
</head>
<body>
  <h3>当前职员手机号码：${cellphone }</h3>
  <form action="${_base }/employee/submitSMSVerify" method="POST">
    <input type="text" placeholder="短信验证码" id="txtVerifyCode">
    <input type="button" value="发送验证码" id="btnSendCode"><br>
    <input type="button" value="提交" id="btnSubmit">
  </form>
  
  <script src="http://libs.baidu.com/jquery/1.9.1/jquery.min.js" type="text/javascript"></script>
  <script type="text/javascript">
    $(document).ready(function(){
    	$("#btnSendCode").click(function(){
    		$.ajax({
    			url:"${_base}/employee/sendCode",
    			type:"GET",
    			dataType:"text",
    			success:function(result){
    				if(result=="error"){
    					alert("手机号有误");
    				}
    				alert("发送成功");
    			}
    		});
    	});
    	
    	$("#btnSubmit").click(function(){
    		$.ajax({
    			url:"${_base}/employee/submitSMSVerify",
    			type:"POST",
    			data:{"verifyCode":$("#txtVerifyCode").val()},
    			dataType:"json",
    			success:function(json){
    				alert(json.message);
    				if(json.redirect==1){
    					window.location.href="${_base}/employee/setPassword";
    				}
    			}
    		});
    	});
    });
  </script>
</body>
</html>