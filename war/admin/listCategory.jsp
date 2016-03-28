<%@ page contentType="text/html; charset=utf-8" language="java" errorPage="" %>
<%@ page import="datastore.ItemCategory" %>
<%@ page import="datastore.ItemCategoryManager" %>
<%@ page import="datastore.User" %>
<%@ page import="util.Printer" %>
<%@ page import="util.Dictionary" %>
<%@ page import="java.util.List" %>
<%@ page import="com.google.appengine.api.datastore.KeyFactory" %>

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

List<ItemCategory> itemCategories = ItemCategoryManager.getAllItemCategories();
%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>慈惠之泉</title>
<link href="../css/public.css" rel="stylesheet" type="text/css">
<link href="../css/main_list_css.css" rel="stylesheet" type="text/css">
<script src="../javascript/jquery-1.10.1.min.js"></script>
</head>

<body>
<%@include file="../header/header.jsp" %>


<div id="menu_back"><div id="menu"><%@include file="../menu/menu.jsp" %></div></div>
<div id="content_area">
  <div class="title_bar title_name"><table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="85%" height="50" valign="middle">物品目錄</td>
    <td width="3%" valign="middle"><a href="addCategory.jsp"><img src="../images/round_plus.png" width="20" height="20" alt="新增管理者"></a></td>
    <td width="12%" valign="middle"><a href="addCategory.jsp"><span class="font_18">&nbsp;新增目錄</span></a></td>
  </tr>
</table>
  </div>
  <table width="800" border="0" align="center" cellpadding="0" cellspacing="0">
    	<tr class="listCotent_CSS">
	      <th width="165" height="30" align="center" bgcolor="#E0EEFC" class="font_18 form_underline_gray">目錄名稱</th>
	      <th width="13%" align="center" bgcolor="#E0EEFC" class="font_18 form_underline_gray">編輯</th>
	      <th width="11%" height="30" align="center" bgcolor="#E0EEFC" class="font_18 form_underline_gray">刪除</th>
	    </tr>
	    <%
	    for (ItemCategory itemCategory : itemCategories) {
	    %>
	    <tr class="listCotent_CSS">
	      <td height="30" align="left" class="font_18 form_underline_gray">&nbsp;<%= itemCategory.getItemCategoryName() %></td>
	      <td height="30" align="center" class="font_18 form_underline_gray"><a href="editCategory.jsp?k=<%= itemCategory.getKey() %>"><img src="../images/pencil_32.png" alt="" width="24" height="24"></a></td>
	      <td height="30" align="center" class="font_18 form_underline_gray"><a href="/manageGlobalObject?action=delete&type=itemCategory&k=<%= itemCategory.getKey() %>"><img src="../images/close_32.png" alt="" width="24" height="24"></a></td>
	    </tr>
		<%
	    	}
		%>
  </table>
</div>
<%@include file="../footer/footer.jsp" %>
</body>
</html>