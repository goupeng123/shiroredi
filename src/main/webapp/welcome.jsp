<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<%@include file="/default.jsp" %>
<body>
<h2>欢迎页面</h2>
<div style="width:200px;height:50px;background-color:grren;margin:auto;">
    <div style="width:100%;height:10px;"></div>
    <div style="width:100%;height:30px;">
          <a href="${base }/logout.html" style="text-decoration: none;color:red;font-size:18px;">退出</a>
    </div>
</div>

<shiro:hasPermission name="user:abc">
<a href="javascript:void('0')" style="text-decoration: none;">这是测试权限链接</a>
<a href="javascript:void('0')" style="text-decoration: none;">add</a>
</shiro:hasPermission><br/>
<shiro:hasPermission name="user:add">
<a href="javascript:void('0')" style="text-decoration: none;">add</a>
</shiro:hasPermission><br/>
<shiro:hasPermission name="user:update">
<a href="javascript:void('0')" style="text-decoration: none;">update</a>
</shiro:hasPermission><br/>
<shiro:hasPermission name="user:delete">
<a href="javascript:void('0')" style="text-decoration: none;">delete</a>
</shiro:hasPermission><br/>
<shiro:hasPermission name="user:find">
<a href="${base }/tst/find.html" style="text-decoration: none;">find</a>
</shiro:hasPermission><br/>

<a href="${base }/tst/find.html" style="text-decoration: none;">find_no_quanxian</a>

<a href="${base }/tst/noqx.html" style="text-decoration: none;">不需要权限</a>

</body>
</html>