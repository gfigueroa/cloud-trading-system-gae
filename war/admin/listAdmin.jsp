<%@page import="java.net.URLEncoder"%>
<%@ page contentType="text/html; charset=utf-8" language="java" errorPage="" %>
<%@ page import="datastore.Administrator" %>
<%@ page import="datastore.AdministratorManager" %>
<%@ page import="datastore.User" %>
<%@ page import="util.Printer" %>
<%@ page import="util.Dictionary" %>
<%@ page import="java.util.List" %>
<%@ page import="com.google.appengine.api.datastore.KeyFactory" %>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>慈惠之泉</title>
<link href="../css/public.css" rel="stylesheet" type="text/css">
<link href="../css/main_list_css.css" rel="stylesheet" type="text/css">
<script src="../javascript/jquery-1.10.1.min.js"></script>
</head>

<%
User sessionUser = (User)session.getAttribute("user");
if (sessionUser == null) {
	response.sendRedirect("../login.jsp");
}
else {
	if (sessionUser.getUserType() != User.UserType.ADMINISTRATOR) {
  		response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
  	}
}

List<Administrator> administrators = AdministratorManager.getAllAdministrators();
%>

<body>
<%@include file="../header/header.jsp" %>

<div id="menu_back"><div id="menu"><%@include file="../menu/menu.jsp" %></div></div>
<div id="content_area">
  <div class="title_bar title_name"><table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="83%" height="50" align="left" valign="middle">管理者列表</td>
    <td width="3%" valign="middle"><a href="addAdmin.jsp"><img src="../images/round_plus.png" width="20" height="20" alt="新增管理者"></a></td>
    <td width="14%" valign="middle"><a href="addAdmin.jsp"><span class="font_18">新增管理者</span></a></td>
  </tr>
</table>
  </div>
  <%
String msg = request.getParameter("msg");
String action = request.getParameter("action");
if (msg != null && msg.equalsIgnoreCase("success") && action != null && action.equals("add")) {
%>
	<table width="384" border="0" align="center" cellpadding="0" cellspacing="0">
  <tbody>
    <tr>
      <td height="30" align="center" valign="top" class="warn_font_green">管理者新增成功 !</td>
      </tr>
  </tbody>
</table>
<% }
else if(msg != null && msg.equalsIgnoreCase("success") && action != null && action.equals("delete")) {
%>
	<table width="384" border="0" align="center" cellpadding="0" cellspacing="0">
  <tbody>
    <tr>
      <td height="30" align="center" valign="top" class="warn_font_red">管理者刪除成功 !</td>
      </tr>
  </tbody>
</table>
<% } 



%>

  <table width="800" border="0" align="center" cellpadding="0" cellspacing="0">
    <tr>
      <td width="28%" height="40" align="left" class="listTitle_CSS form_underline_blue radiusLeft">管理者名稱</td>
      <td width="48%" height="40" align="left" class="listTitle_CSS form_underline_blue">E-mail</td>
      <td width="15%" height="40" align="center" class="listTitle_CSS form_underline_blue">編輯</td>
      <td width="9%" height="40" align="center" class="listTitle_CSS form_underline_blue radiusRight">刪除</td>
    </tr>
    <%
    for (Administrator administrator : administrators) {
    %>
	    <tr class="listCotent_CSS">
	      <td height="30" align="left" class="font_18 form_underline_gray">&nbsp;<%= administrator.getAdministratorName() %></td>
	      <td height="30" align="left" class="font_18 form_underline_gray"><%= administrator.getUser().getUserEmail().getEmail() %></td>
	      <td height="30" align="center" class="font_18 form_underline_gray"><a href="editAdmin.jsp?k=<%=URLEncoder.encode(KeyFactory.keyToString(administrator.getKey()), "UTF8") %>"><img src="../images/pencil_32.png" alt="" width="24" height="24"></a></td>
	      <td height="30" align="center" class="font_18 form_underline_gray"><a href="/manageUser?action=delete&type=administrator&k=<%= KeyFactory.keyToString(administrator.getKey()) %>"><img src="../images/close_32.png" alt="" width="24" height="24"></a></td>
	    </tr>
	<%
	} 
	%>
  </table>
</div>
<%@include file="../footer/footer.jsp" %>
</body>
</html>