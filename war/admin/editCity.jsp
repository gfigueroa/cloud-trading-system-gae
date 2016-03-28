<%@ page contentType="text/html; charset=utf-8" language="java" errorPage="" %>
<%@ page import="datastore.User" %>
<%@ page import="util.Printer" %>
<%@ page import="util.Dictionary" %>
<%@ page import="datastore.CityManager" %>
<%@ page import="datastore.City" %>
<%@ page import="com.google.appengine.api.datastore.KeyFactory" %>

<!doctype html>
<html >
<head>
<link href="../css/main_list_css.css" rel="stylesheet" type="text/css">
<link href="../css/public.css" rel="stylesheet" type="text/css">
<script src="../javascript/jquery-1.10.1.min.js"></script>
<script src="../javascript/addCity.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>慈惠之泉</title>
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

String cityKeyStr = request.getParameter("k");
City city = CityManager.getCity(KeyFactory.stringToKey(cityKeyStr));
%>

<body>
<%@include file="../header/header.jsp" %>
<div id="menu_back">
<div id="menu">
<%@include file="../menu/menu.jsp" %>
</div></div>
<div id="content_area">
<div class="title_bar title_name">編輯城市</div>
	<form id="addCityform" name="addCityform" method="post" action="/manageGlobalObject?action=update&type=city&k=<%= cityKeyStr %>">
  
  <table width="100%" border="0" align="center" cellpadding="0" cellspacing="1">
    <tr>
      <td height="73" align="right" class="form_underline" scope="row">&nbsp;</td>
      <td width="153" height="73" align="right" class="form_underline" scope="row">請修改城市名稱：</td>
      <td height="73" class="form_underline">&nbsp;</td>
      <td height="73" class="form_underline"></td>
    </tr>
    <tr>
      <td class="form_underline" width="316" height="120" align="right" scope="row">&nbsp;</td>
      <td class="form_underline" height="120" align="right" scope="row">*城市名稱：</td>
      <td class="form_underline" width="354" height="120">
        <input name="cityName" type="text" class="textfield_style" id="cityName" value="<%= city.getCityName() %>">
        </td>
      <td class="form_underline" width="172" height="120"><div id="empty_CityName" class="errorText">*Enter city name</div></td>
    </tr>
    <tr>
      <td></td>
      <td colspan="2" align="center" scope="row">&nbsp;</td>
      <td></td>
    </tr>
    <tr>
      <td colspan="4" align="center"><input type="submit" name="NewCityNameSubmit" id="NewCityNameSubmit" class="css_btn_class" value="儲存">&nbsp;<input type="button" name="buttonClose" id="buttonClose" class="css_btn_class" onclick="window.location.href='listCity.jsp'" value="關閉"></td>
      </tr>
</table>
  </form>
</div>
<%@include file="../footer/footer.jsp" %>
</body>
</html>