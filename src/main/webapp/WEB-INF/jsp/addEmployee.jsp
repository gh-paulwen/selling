<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String base = request.getContextPath();
	request.setAttribute("_base", base);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>添加职员</title>
</head>
<body>
<h3 id="curEmp"></h3>
<table>
  <tr>
    <td>员工编号</td>
    <td><input id="code" type="text"></td>
  </tr>
  <tr>
    <td>员工姓名</td>
    <td><input id="name" type="text"></td>
  </tr>
  <tr>
    <td>员工手机号码</td>
    <td><input id="cellphone" type="text"></td>
  </tr>
  <tr>
    <td>员工类型</td>
    <td><select id="type"></select></td>
  </tr>
  <tr>
    <td>员工描述</td>
    <td><textarea rows="3" cols="40" id="description"></textarea></td>
  </tr>
  <tr>
    <td><input type="button" id="submit" value="添加"></td>
  </tr>
</table>
<script src="http://libs.baidu.com/jquery/1.9.1/jquery.min.js" type="text/javascript"></script>
<script type="text/javascript">
  $(document).ready(function(){
	  
	  var curEmp;
	  //获取当前在线的员工
	  $.ajax({
		  url:"${_base}/employee/getCurrentEmployee",
		  type:"GET",
		  async:true,
		  dataType:"json",
		  success:function(json){
			  curEmp = json;
			  $("#curEmp").html("welcome " + curEmp.name);
		  }
	  });
	  
	  //加载员工的类型
	  $.ajax({
		  url:"${_base}/employee/getType",
		  type:"GET",
		  async:true,
		  dataType:"json",
		  success:function(types){
			  var i = 0;
			  for(i = 0;i < types.length;i++){
				  var type = types[i];
				  var option = document.createElement("option");
			   	  option.setAttribute("value",type.code);
			   	  option.appendChild(document.createTextNode(type.name));
			   	  $("#type").append(option);
			  }
		  }
	  });
	  
	  //提交
	  $("#submit").click(function(){
		  $.ajax({
			  url:"${_base}/employee/submitSave",
			  type:"POST",
			  async:true,
			  data:{"code":$("#code").val(),"name":$("#name").val(),"cellphone":$("#cellphone").val(),"type":$("#type").val(),"description":$("#description").val()},
			  dataType:"json",
			  success:function(json){
				  alert(json.message);
			  }
		  });
	  });
  });
</script>
</body>
</html>