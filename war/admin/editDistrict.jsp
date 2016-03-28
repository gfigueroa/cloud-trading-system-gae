<%@ page contentType="text/html; charset=utf-8" language="java" errorPage="" %>
<%@ page import="datastore.User" %>
<%@ page import="util.Printer" %>
<%@ page import="util.Dictionary" %>
<%@ page import="datastore.District" %>
<%@ page import="datastore.DistrictManager" %>
<%@ page import="datastore.City" %>
<%@ page import="datastore.CityManager" %>
<%@ page import="java.util.List" %>
<%@ page import="com.google.appengine.api.datastore.KeyFactory" %>

<!doctype html>
<html >
<head>
<link href="../css/main_list_css.css" rel="stylesheet" type="text/css">
<link href="../css/public.css" rel="stylesheet" type="text/css">
<script src="../javascript/jquery-1.10.1.min.js"></script>
<script src="../javascript/addDistrict.js"></script>
<script src="../javascript/selectDate.js" type="text/javascript"></script>
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

String districtKey = request.getParameter("k");
District district = DistrictManager.getDistrict(KeyFactory.stringToKey(districtKey));

City city = CityManager.getCity(district.getKey().getParent());
%>

<body>
<%@include file="../header/header.jsp" %>
<div id="menu_back">
<div id="menu">
<%@include file="../menu/menu.jsp" %>
</div></div>
<div id="content_area">
<div class="title_bar title_name">編輯鄉鎮區</div>
	<form id="addDistrictform" name="addDistrictform" method="post" action="/manageGlobalObject?action=update&type=district&k=<%= districtKey %>">
  
  <table width="100%" border="0" align="center" cellpadding="0" cellspacing="1">
    <tr>
      <td height="73" colspan="2" align="right" class="form_underline" scope="row">請輸入欲編輯的鄉鎮區資料：</td>
      <td height="73" class="form_underline">&nbsp;</td>
      <td height="73" class="form_underline"></td>
    </tr>
    <tr>
      <td height="40" align="right" scope="row">&nbsp;</td>
      <td width="208" height="40" align="right" scope="row">&nbsp;</td>
      <td height="40">&nbsp;</td>
      <td height="40"></td>
    </tr>
    <tr>
      <td width="130" height="60" align="right" scope="row">&nbsp;</td>
      <td height="60" align="right" scope="row"><label for="cityId">城市名稱：</label></td>
      <td width="354" height="60">
        <input name="cityId" type="text" class="textfield_style" id="cityId" value="<%= city.getCityName() %>" disabled></td>
    </tr>
    <tr>
      <td width="130" height="60" align="right" scope="row">&nbsp;</td>
      <td height="60" align="right" scope="row"><label for="districtName">*鄉鎮區名稱：</label></td>
      <td width="354" height="60">
        <input name="districtName" type="text" class="textfield_style" id="districtName" value="<%= district.getDistrictName() %>"></td>
      <td width="303" height="60" align="left"><div id="empty_DistrictName" class="errorText">*Enter district name</div></td>
    </tr>
    <tr>
      <td></td>
      <td colspan="2" align="center" scope="row">&nbsp;</td>
      <td></td>
    </tr>
    <tr>
      <td class="form_underline"></td>
      <td colspan="2" align="center" class="form_underline" scope="row">&nbsp;</td>
      <td class="form_underline"></td>
    </tr>
    <tr>
      <td></td>
      <td colspan="2" align="center" scope="row">&nbsp;</td>
      <td></td>
    </tr>
    <tr><td colspan="4" align="center"><input type="submit" name="AddDistrictSubmit" id="AddDistrictSubmit" class="css_btn_class" value="儲存">&nbsp;<input type="button" name="buttonClose" id="buttonClose" class="css_btn_class" onclick="window.location.href='listDistrict.jsp'" value="關閉"></td>
      </tr>
</table>
  </form>
</div>
<%@include file="../footer/footer.jsp" %>
</body>
</html>