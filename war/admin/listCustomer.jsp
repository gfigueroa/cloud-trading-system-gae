<%@ page contentType="text/html; charset=utf-8" language="java" errorPage="" %>
<%@ page import="datastore.Customer" %>
<%@ page import="datastore.CustomerManager" %>
<%@ page import="datastore.User" %>
<%@ page import="java.net.URLEncoder" %>
<%@ page import="util.Printer" %>
<%@ page import="util.Dictionary" %>
<%@ page import="java.util.ArrayList" %>
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
	if (sessionUser.getUserType() != User.UserType.ADMINISTRATOR
		&& sessionUser.getUserType() != User.UserType.CUSTOMER) {
  		response.sendError(HttpServletResponse.SC_UNAUTHORIZED);  		
  	}
}

List<Customer> customers;
switch (sessionUser.getUserType()) {
	case ADMINISTRATOR:
		customers = CustomerManager.getAllCustomers();
		break;
	case CUSTOMER:
		Customer customer = CustomerManager.getCustomer(sessionUser);
		customers = new ArrayList<Customer>();
		customers.add(customer);
		break;
	default:
		customers = null;
		break;
}
%>

<body>
<%@include file="../header/header.jsp" %>


<div id="menu_back"><div id="menu"><%@include file="../menu/menu.jsp" %></div></div>
<div id="content_area">
  <div class="title_bar title_name"><table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="84%" height="50" align="left" valign="middle">居民列表</td>
    <% if (sessionUser.getUserType() == User.UserType.ADMINISTRATOR) { %>
    <td width="3%" valign="middle"><a href="addCustomer.jsp"><img src="../images/round_plus.png" width="20" height="20" alt="新增管理者"></a></td>
    <td width="13%" valign="middle"><a  href="addCustomer.jsp"><span class="font_18">新增居民</span></a></td>
    <% } %>
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
      <td height="30" align="center" valign="top" class="warn_font_green">居民已經新增成功 !</td>
      </tr>
  </tbody>
</table>
<% }
else if(msg != null && msg.equalsIgnoreCase("success") && action != null && action.equals("delete")) {
%>
	<table width="384" border="0" align="center" cellpadding="0" cellspacing="0">
  <tbody>
    <tr>
      <td height="30" align="center" valign="top" class="warn_font_red">居民已經刪除成功 !</td>
      </tr>
  </tbody>
</table>
<% } 

%>

  <table width="800" border="0" align="center" cellpadding="0" cellspacing="0">
    <tr>
      <td width="17%" align="left" class="listTitle_CSS form_underline_blue radiusLeft">會員編號</td>
      <td width="25%" height="40" align="left" class="listTitle_CSS form_underline_blue radiusLeft">居民姓名</td>
      <td width="37%" height="40" align="left" class="listTitle_CSS form_underline_blue">E-mail</td>
      <td width="11%" height="40" align="center" class="listTitle_CSS form_underline_blue">編輯</td>
      <%
	      if (sessionUser.getUserType() == User.UserType.ADMINISTRATOR) {
	      %>
      <td width="10%" height="40" align="center" class="listTitle_CSS form_underline_blue radiusRight">刪除</td>
      <%
	      }
	      %>
      </tr>
      
    <%
    for (Customer customer : customers) {
    %>
	    <tr class="listCotent_CSS">
	      <td align="left" class="font_18 form_underline_gray">&nbsp;<%= customer.getCustomerSerialNumber() %></td>
	      <td height="30" align="left" class="font_18 form_underline_gray">&nbsp;<%= customer.getCustomerName() %></td>
	      <td height="30" align="left" class="font_18 form_underline_gray"><%= customer.getUser().getUserEmail().getEmail() %></td>
	      <td height="30" align="center" class="font_18 form_underline_gray"><a href="editCustomer.jsp?k=<%= URLEncoder.encode(KeyFactory.keyToString(customer.getKey()),"UTF8") %>"><img src="../images/pencil_32.png" alt="" width="24" height="24"></a></td>
          <%
	      if (sessionUser.getUserType() == User.UserType.ADMINISTRATOR) {
	      %>
	      <td height="30" align="center" class="font_18 form_underline_gray"><a href="/manageUser?action=delete&type=customer&k=<%=KeyFactory.keyToString(customer.getKey()) %>"><img src="../images/close_32.png" alt="" width="24" height="24"></a></td><%
	      }
	      %>
    </tr>
    <%
    }
    %>
  </table>
  
</div>
<%@include file="../footer/footer.jsp" %>
</body>
</html>