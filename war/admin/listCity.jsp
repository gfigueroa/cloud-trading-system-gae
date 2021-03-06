<%@ page contentType="text/html; charset=utf-8" language="java" errorPage="" %>
<%@ page import="datastore.City" %>
<%@ page import="datastore.CityManager" %>
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

List<City> cities = CityManager.getAllCities();
%>

<body>
<%@include file="../header/header.jsp" %>


<div id="menu_back"><div id="menu"><%@include file="../menu/menu.jsp" %></div></div>
<div id="content_area">
  <div class="title_bar title_name"><table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="84%" height="50" align="left" valign="middle">城市列表</td>
    <td width="3%" valign="middle"><a href="addCity.jsp"><img src="../images/round_plus.png" width="20" height="20" alt="新增管理者"></a></td>
    <td width="13%" valign="middle"><a  href="addCity.jsp"><span class="font_18">&nbsp;新增城市</span></a></td>
  </tr>
</table>
  </div>
  <table width="800" border="0" align="center" cellpadding="0" cellspacing="0">
    <tr>
      <td height="40" align="left" class="listTitle_CSS form_underline_blue radiusLeft">城市名稱</td>
      <td width="16%" height="40" align="center" class="listTitle_CSS form_underline_blue">編輯</td>
      <td width="11%" height="40" align="center" class="listTitle_CSS form_underline_blue radiusRight">刪除</td>
    </tr>
    <%
    	for (City city : cities) {
    %>
    <tr class="listCotent_CSS">
      <td height="30" align="left" class="font_18 form_underline_gray">&nbsp;<%= city.getCityName() %></td>
      <td height="30" align="center" class="font_18 form_underline_gray"><a href="editCity.jsp?k=<%= KeyFactory.keyToString(city.getKey()) %>"><img src="../images/pencil_32.png" alt="" width="24" height="24"></a></td>
      <td height="30" align="center" class="font_18 form_underline_gray"><a href="/manageGlobalObject?action=delete&type=city&k=<%= KeyFactory.keyToString(city.getKey()) %>"><img src="../images/close_32.png" alt="" width="24" height="24"></a></td>
    </tr>
    <%
    }
    %>
  </table>
</div>
<%@include file="../footer/footer.jsp" %>
</body>
</html>