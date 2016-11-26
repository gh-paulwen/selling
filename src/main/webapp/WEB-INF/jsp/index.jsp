<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
  String base = request.getContextPath();
  request.setAttribute("_base",base);
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>首页</title>
</head>
<body>

<script src="http://libs.baidu.com/jquery/1.9.1/jquery.min.js" type="text/javascript"></script>
<script type="text/javascript">
  $(document).ready(function(){
	  $.ajax({
		  url:"${_base}/employee/getFunctionality",
		  type:"GET",
		  dataType:"json",
		  success:function(funcs){
			  for(var i = 0;i < funcs.length;i++){
				  var func = funcs[i];
				  var a = document.createElement("a");
				  a.appendChild(document.createTextNode(func.name));
				  a.setAttribute("href","${_base}" + func.url);
				  $("body").append(a);
			  }
		  }
	  });
  });
</script>
</body>
</html>