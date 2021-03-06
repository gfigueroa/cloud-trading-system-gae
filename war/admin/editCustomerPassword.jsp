<%@ page contentType="text/html; charset=utf-8" language="java" errorPage="" %>
<%@ page import="datastore.Customer" %>
<%@ page import="datastore.CustomerManager" %>
<%@ page import="datastore.User" %>
<%@ page import="com.google.appengine.api.datastore.Key" %>
<%@ page import="com.google.appengine.api.datastore.KeyFactory" %>
<%@ page import="util.Printer" %>
<%@ page import="util.Dictionary" %>

<%
User sessionUser = (User)session.getAttribute("user");

if (sessionUser == null) {
	response.sendRedirect("../login.jsp");
}
else {
	if (sessionUser.getUserType() != User.UserType.ADMINISTRATOR
		&&sessionUser.getUserType() != User.UserType.CUSTOMER) {
  		response.sendError(HttpServletResponse.SC_UNAUTHORIZED);  		
  	}
}

String customerKeyString = request.getParameter("k");
Key customerKey = KeyFactory.stringToKey(customerKeyString);
Customer customer = CustomerManager.getCustomer(customerKey);
%>
<!doctype html>
<html>
<head>
<link href="../css/main_list_css.css" rel="stylesheet" type="text/css">
<link href="../css/public.css" rel="stylesheet" type="text/css">
<script src="../javascript/jquery-1.10.1.min.js"></script>
<script src="../javascript/changePasswordCheck.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>慈惠之泉</title>
</head>

<body>
<div id="content_area">
<div class="title_bar title_name">編輯居民密碼</div>
	<form id="modifyPasswordForm" name="modifyPasswordForm" method="post" action="/manageUser?action=update&type=customer&update_type=password&k=<%= customerKeyString %>">
  
  <table width="100%" border="0" align="center" cellpadding="0" cellspacing="1">
    <tr>
      <td height="40" align="right" scope="row">&nbsp;</td>
      <td width="208" height="40" align="right" scope="row">&nbsp;</td>
      <td height="40">&nbsp;</td>
      <td height="40"></td>
    </tr>
    <%
    if (sessionUser.getUserType() == User.UserType.CUSTOMER) {
    %>
	    <tr>
	      <td height="60" align="right" scope="row">&nbsp;</td>
	      <td height="60" align="right" scope="row"><label for="userPassword">*舊密碼：</label></td>
	      <td height="60"><input name="userPassword" type="password" class="textfield_style" id="userPassword"></td>
	      <td height="60" align="left">&nbsp;</td>
	    </tr>
    <%
    }
    %>
    <tr>
      <td width="130" height="60" align="right" scope="row">&nbsp;</td>
      <td height="60" align="right" scope="row"><label for="userNewPassword">*新密碼：</label></td>
      <td width="354" height="60">
        <input name="userNewPassword" type="password" class="textfield_style" id="userNewPassword"></td>
      <td width="303" height="60" align="left"><div id="empty_newPassword" class="errorText">*enter new password</div></td>
    </tr>
    <tr>
      <td height="60" colspan="2" align="right" scope="row"><label for="userPasswordCheck">*密碼確認：</label></td>
      <td width="354" height="60">
        <input name="userPasswordCheck" type="password" class="textfield_style" id="userPasswordCheck">
      </td>
        <td width="303" height="60" align="left"><div id="empty_passwordCheck" class="errorText">*Password didn't match</div></td>
    </tr>
    <tr>
      <td colspan="4" align="center"><%
String msg = request.getParameter("msg");
String action = request.getParameter("action");
if (msg != null && msg.equalsIgnoreCase("success") && action != null && action.equals("update")) {
%>
        <span class="warn_font_green">密碼已經成功被修改 !</span>
        <% } 

%></td>
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
    <tr><td colspan="4" align="center"><input type="submit" name="newPasswordSubmit" id="newPasswordSubmit" class="css_btn_class" value="儲存">&nbsp;<input type="button" name="buttonClose" id="buttonClose" class="css_btn_class" onclick="window.close();" value="關閉"></td>
      </tr>
</table>
  </form>
</div>
</body>
</html>