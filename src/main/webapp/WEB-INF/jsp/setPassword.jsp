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
<title>设置密码</title>
</head>
<body>
  <h3>${cellphone },您现在可以设置密码</h3>
  <form >
    密码：<input type="text" id="password"><br>
    重复密码:<input type="text" id="repeatPassword"><br>
    <input type="button" id="btnSubmit" value="提交">
  </form>
  <script src="http://libs.baidu.com/jquery/1.9.1/jquery.min.js" type="text/javascript"></script>
  <script type="text/javascript">
    $(document).ready(function(){
    	$("#btnSubmit").click(function(){
    		$.ajax({
    			url:"${_base}/employee/submitSetPassword",
    			type:"POST",
    			async:true,
    			data:{"password":$("#password").val(),"repeatPassword":$("#repeatPassword").val()},
    			dataType:"json",
    			success:function(json){
    				alert(json.message);
    				if(json.redirect == 1){
    					window.location.href="${_base}/employee/login";
    				}
    			}
    		});
    	});
    });
  </script>
</body>
</html>