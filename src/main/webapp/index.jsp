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
<form action="${base }/login.html" method="post">
  user:<input type="text" name="name"/>
  pasw:<input type="password" name="password"/><br/>
  yzm:<input type="text" name="yyzm">
      <img src="${base }/getYzm.html"/><br/>
      <input type="submit" value="submit"/>
</form>
</body>
</html>