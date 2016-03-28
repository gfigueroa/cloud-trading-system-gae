<%@ page contentType="text/html; charset=utf-8" language="java" errorPage="" %>
<%@ page import="datastore.City" %>
<%@ page import="datastore.CityManager" %>
<%@ page import="datastore.District" %>
<%@ page import="datastore.DistrictManager" %>
<%@ page import="datastore.Village" %>
<%@ page import="datastore.VillageManager" %>
<%@ page import="datastore.User" %>
<%@ page import="util.Printer" %>
<%@ page import="util.Dictionary" %>
<%@ page import="java.util.List" %>
<%@ page import="com.google.appengine.api.datastore.Key" %>
<%@ page import="com.google.appengine.api.datastore.KeyFactory" %>
<%@ page import="com.google.appengine.api.blobstore.BlobstoreService" %>
<%@ page import="com.google.appengine.api.blobstore.BlobstoreServiceFactory" %>

<!doctype html>
<html>
<head>
<link href="../css/main_list_css.css" rel="stylesheet" type="text/css">
<link href="../css/public.css" rel="stylesheet" type="text/css">
<link href="../css/addCustomer.css" rel="stylesheet" type="text/css">
<script src="../javascript/jquery-1.10.1.min.js"></script>
<script src="../javascript/addCustomer.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>慈惠之泉</title>
</head>

<%
	User sessionUser = (User)session.getAttribute("user");

if (sessionUser == null) {
	response.sendRedirect("../login.jsp");
}
else {
	if (sessionUser.getUserType() != User.UserType.ADMINISTRATOR
		&& sessionUser.getUserType() != User.UserType.CUSTOMER) {
  		response.sendError(HttpServletResponse.SC_UNAUTHORIZED);  		
  	}
}

BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();

List<City> cities = CityManager.getAllCities();
String cityKeyString = request.getParameter("cityId");
Key cityKey = cityKeyString != null ? KeyFactory.stringToKey(cityKeyString) : null;
City thisCity = cityKey != null ? CityManager.getCity(cityKey) : (!cities.isEmpty() ? cities.get(0) : null);

List<District> districts = cityKey != null ? DistrictManager.getAllDistrictsFromCity(cityKey) : DistrictManager.getAllDistricts();
String districtKeyString = request.getParameter("districtId");
Key districtKey = districtKeyString != null ? KeyFactory.stringToKey(districtKeyString) : null;
District thisDistrict = districtKey != null ? DistrictManager.getDistrict(districtKey) : (!districts.isEmpty() ? districts.get(0): null);

List<Village> villages = districtKey != null ? VillageManager.getAllVillagesFromDistrict(districtKey) : VillageManager.getAllVillages();
String villageKeyString = request.getParameter("villageId");
Key villageKey = villageKeyString != null ? KeyFactory.stringToKey(villageKeyString) : null;
Village thisVillage = villageKey != null ? VillageManager.getVillage(villageKey) : (!villages.isEmpty() ? villages.get(0): null);
%>

<body>
<%@include file="../header/header.jsp" %>
<div id="menu_back">
<div id="menu">
<%@include file="../menu/menu.jsp" %>
</div></div>
<div id="content_area">
<div class="title_bar title_name">新增居民<span class="form_underline"></span></div>
<form id="addCustomerform" name="addCustomerform" method="post" action="/manageUser?action=add&type=customer&keep_adding=true">

<!--"/manageUser?action=add&type=customer&keep_adding=true">-->
  
  <table width="100%" border="0" align="center" cellpadding="0" cellspacing="1">
    <tr>
      <td height="60" align="right" class="form_underline" scope="row">&nbsp;</td>
      <td height="60" colspan="2" align="left" class="form_underline" scope="row">請輸入欲新增的居民資訊：</td>
      <td height="60" class="form_underline"></td>
    </tr>
    <tr>
      <td height="20" align="right" scope="row">&nbsp;</td>
      <td width="208" height="20" align="right" scope="row">&nbsp;</td>
      <td height="20">&nbsp;</td>
      <td height="20"></td>
    </tr>  
    <tr>
      <td width="130" height="50" align="right" scope="row">&nbsp;</td>
      <td height="50" align="right" scope="row"><label for="userEmail">*Email：</label></td>
      <td width="354" height="50">
        <input name="userEmail" type="text" class="textfield_style" id="userEmail"></td>
      <td width="303" height="50"><div id="empty_userEmail" class="errorText">*Invalid e-mail address</div></td>
    </tr>
    <tr>
      <td width="130" height="50" align="right" scope="row">&nbsp;</td>
      <td height="50" align="right" scope="row"><label for="userPassword">*密碼：</label></td>
      <td width="354" height="50">
        <input name="userPassword" type="password" class="textfield_style" id="userPassword"></td>
      <td width="303" height="50"><div id="empty_userPassword" class="errorText">*Enter password</div></td>
    </tr>
    <tr>
      <td width="130" height="50" align="right" scope="row">&nbsp;</td>
      <td height="50" align="right" scope="row"><label for="passwordCheck">*密碼確認：</label></td>
      <td width="354" height="50">
        <input name="passwordCheck" type="password" class="textfield_style" id="passwordCheck"></td>
      <td width="303" height="50"><div id="empty_passwordCheck" class="errorText">*Password didn't match</div></td>
    </tr>  
    <tr>
      <td width="130" height="50" align="right" scope="row">&nbsp;</td>
      <td height="50" align="right" scope="row"><label for="customerName">*使用者姓名：</label></td>
      <td width="354" height="50"><input type="text" name="customerName" id="customerName" class="textfield_style"></td>
      <td width="303" height="50" align="left"><div id="empty_customerName" class="errorText">*Enter customer name</div></td>
    </tr>
    <tr>
      <td width="130" height="50" align="right" scope="row">&nbsp;</td>
      <td height="50" align="right" scope="row"><label for="customerIdentityNumber">身份證號碼：</label></td>
      <td width="354" height="50"><input type="text" name="customerIdentityNumber" id="customerIdentityNumber" class="textfield_style"></td>
      <td width="303" height="50" align="left"><div id="empty_customerIdentityNumber" class="errorText">*Enter customer ID number</div></td>
    </tr>
    <tr>
      <td height="50" align="right" scope="row">&nbsp;</td>
      <td height="50" align="right" scope="row"><label for="customerNickname">*綽號：</label></td>
      <td height="50"><input type="text" name="customerNickname" id="customerNickname" class="textfield_style"></td>
      <td height="50">&nbsp;</td>
    </tr>
    <tr>
      <td width="130" height="50" align="right" scope="row">&nbsp;</td>
      <td height="50" align="right" scope="row"><label for="customerSerialNumber">*會員編號：</label></td>
      <td width="354" height="50">
        <input name="customerSerialNumber" type="text" class="textfield_style" id="customerSerialNumber"></td>
      <td width="303" height="50" align="left">&nbsp;</td>
    </tr>
    <tr>
      <td width="130" height="50" align="right" scope="row">&nbsp;</td>
      <td height="50" align="right" scope="row"><label for="customerPhoneNumber">電話號碼：</label></td>
      <td width="354" height="50">
        <input name="customerPhoneNumber" type="text" class="textfield_style" id="customerPhoneNumber"></td>
      <td width="303" height="50" align="left">&nbsp;</td>
    </tr>
    <tr>
      <td width="130" height="50" align="right" scope="row">&nbsp;</td>
      <td height="50" align="right" scope="row"><label for="villageId">*村里：</label></td>
      <td width="354" height="50"><select name="villageId" class="textfield_style" id="villageId">
        <%
        	for (Village village : villages) {
        %>
        <option value="<%= KeyFactory.keyToString(village.getKey()) %>" <%= thisVillage != null ? (thisVillage.getKey().equals(village.getKey()) ? "selected" : "") : "" %>><%= village.getVillageName() %></option>
        <%
	    }
        %>
      </select></td>
      <td width="303" height="50" align="left">&nbsp;</td>
    </tr>
    <tr>
      <td width="130" height="50" align="right" scope="row">&nbsp;</td>
      <td height="50" align="right" scope="row"><label for="customerAddress1">地址：</label></td>
      <td width="354" height="50">
        <input name="customerAddress1" type="text" class="textfield_style" id="customerAddress1"></td>
      <td width="303" height="50" align="left">&nbsp;</td>
    </tr>
    <tr>
      <td width="130" height="50" align="right" scope="row">&nbsp;</td>
      <td height="50" align="right" scope="row"><label for="customerAddress2"></label></td>
      <td width="354" height="50">
        <input name="customerAddress2" type="text" class="textfield_style" id="customerAddress2"></td>
      <td width="303" height="50" align="left">&nbsp;</td>
    </tr>
    
    <!-- 
    <tr>
      <td width="130" height="50" align="right" scope="row">&nbsp;</td>
      <td height="50" align="right" scope="row"><label for="customerLogo">Logo：</label></td>
      <td width="354" height="50">
		<input type="file" name="customerLogo" class="textfield_style" value="" id="customerLogo" /></td>
	  <td width="303" height="50" align="left">&nbsp;</td>
	</tr>
    -->

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
    <tr><td colspan="4" align="center"><input type="submit" name="NewCustomerSubmit" id="NewCustomerSubmit" class="css_btn_class" value="儲存">&nbsp;<input type="button" name="buttonClose" id="buttonClose" class="css_btn_class" onclick="window.location.href='listCustomer.jsp'" value="關閉"></td>
      </tr>
</table>
  </form>
</div>
<%@include file="../footer/footer.jsp" %>
</body>
</html>