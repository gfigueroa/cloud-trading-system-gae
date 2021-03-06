<%@ page contentType="text/html; charset=utf-8" language="java" errorPage="" %>
<%@ page import="datastore.User" %>
<%@ page import="util.Printer" %>
<%@ page import="util.Dictionary" %>

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
%>
<!doctype html>
<html>
<head>
<link href="../css/main_list_css.css" rel="stylesheet" type="text/css">
<link href="../css/public.css" rel="stylesheet" type="text/css">
<script src="../javascript/jquery-1.10.1.min.js"></script>
<script src="../javascript/addAdmin.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>慈惠之泉</title>
</head>

<body>
<%@include file="../header/header.jsp" %>
<div id="menu_back">
<div id="menu">
<%@include file="../menu/menu.jsp" %>
</div></div>
<div id="content_area">
<div class="title_bar title_name">新增管理者</div>
	<form id="addAdminform" name="addAdminform" method="post" action="/manageUser?action=add&type=administrator&keep_adding=true">
  
  <table width="100%" border="0" align="center" cellpadding="0" cellspacing="1">
    <tr>
      <td height="73" align="right" class="form_underline" scope="row">&nbsp;</td>
      <td height="73" colspan="2" align="left" class="form_underline" scope="row">請輸入欲新增的管理者資訊:</td>
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
      <td height="60" align="right" scope="row"><label for="userType">*使用者類別：</label></td>
      <td width="354" height="60"><select name="userType" class="textfield_style" id="userType">
        <option value="Administrator" selected="selected">管理者</option>
        <option value="Customer">居民</option>
      </select></td>
      <td width="303" height="60"></td>
    </tr>
    
    <tr>
      <td width="130" height="60" align="right" scope="row">&nbsp;</td>
      <td height="60" align="right" scope="row"><label
       for="administratorName">  *使用者名稱：</label></td>
      <td width="354" height="60"><input type="text" name="administratorName" id="administratorName"class="textfield_style"></td>
      <td width="303" height="60" align="left"><div id="empty_administratorName" class="errorText">*Enter Administrator name	</div></td>
    </tr>
    <tr>
      <td width="130" height="60" align="right" scope="row">&nbsp;</td>
      <td height="60" align="right" scope="row"><label for="userEmail">*E-mail：</label></td>
      <td width="354" height="60">
        <input name="userEmail" type="text" class="textfield_style" id="userEmail"></td>
        <td width="303" height="60" align="left"><div id="empty_userEmail" class="errorText">*Invalid e-mail address</div></td>
    </tr>
    <tr>
      <td width="130" height="60" align="right" scope="row">&nbsp;</td>
      <td height="60" align="right" scope="row"><label for="userPassword">*密碼：</label></td>
      <td width="354" height="60">
        <input name="userPassword" type="password" class="textfield_style" id="userPassword"></td>
        <td width="303" height="60" align="left"><div id="empty_userPassword" class="errorText">*Enter password</div></td>
    </tr>
    <tr>
      <td width="130" height="60" align="right" scope="row">&nbsp;</td>
      <td height="60" align="right" scope="row"><label for="passwordCheck">*密碼確認：</label></td>
      <td width="354" height="60">
        <input name="passwordCheck" type="password" class="textfield_style" id="passwordCheck">
      </td>
        <td width="303" height="60" align="left"><div id="empty_passwordCheck" class="errorText">*Password didn't match</div></td>
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
    <tr><td colspan="4" align="center"><input type="submit" name="NewAdminSubmit" id="NewAdminSubmit" class="css_btn_class" value="儲存">&nbsp;<input type="button" name="buttonClose" id="buttonClose" class="css_btn_class" onclick="window.location.href='../admin/listAdmin.jsp'" value="關閉"></td>
      </tr>
</table>
  </form>
</div>
<%@include file="../footer/footer.jsp" %>
</body>
</html>