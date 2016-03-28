<%@ page contentType="text/html; charset=utf-8" language="java" errorPage="" %>
<%@ page import="datastore.Customer" %>
<%@ page import="datastore.CustomerManager" %>
<%@ page import="datastore.User" %>
<%@ page import="datastore.ServiceType" %>
<%@ page import="datastore.ServiceTypeManager" %>
<%@ page import="util.Printer" %>
<%@ page import="util.Dictionary" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page import="com.google.appengine.api.datastore.Key" %>
<%@ page import="com.google.appengine.api.datastore.KeyFactory" %>

<!doctype html>
<html>
<head>
<link href="../css/main_list_css.css" rel="stylesheet" type="text/css">
<link href="../css/public.css" rel="stylesheet" type="text/css">
<link href="../css/jquery.datetimepicker.css" rel="stylesheet" type="text/css">
<link href="../css/addService.css" rel="stylesheet" type="text/css">
<script src="../javascript/jquery-1.10.1.min.js"></script>
<script src="../javascript/jquery.datetimepicker.js" type="text/javascript"></script>
<script src="../javascript/addService.js"></script>
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

String newServiceType = request.getParameter("newServiceType");
if (newServiceType == null) {
	newServiceType = "create";
}

List<ServiceType> serviceTypes = ServiceTypeManager.getAllServiceTypes();
%>

<body>
<%@include file="../header/header.jsp" %>
<div id="menu_back">
<div id="menu">
<%@include file="../menu/menu.jsp" %>
</div></div>
<div id="content_area">
<div class="title_bar title_name">新增義工</div>
	<form id="addStorageform" name="addStorageform" method="post" action="/manageCustomer?action=add&type=<%= newServiceType.equalsIgnoreCase("create") ? "service" : "serviceRequest" %>&keep_adding=true">
  
  <table width="800" border="0" align="center" cellpadding="0" cellspacing="1">
    <tr>
      <td width="78" height="73" align="right" class="form_underline" scope="row">&nbsp;</td>
      <td height="73" colspan="3" align="left" class="form_underline" scope="row">請輸入欲新增的義工資料：</td>
      <td height="73" class="form_underline"></td>
    </tr>
    <tr>
      <td height="60" colspan="2" align="right" scope="row">*新增分類：</td>
      <td height="60" colspan="2">
        <input name="newServiceType" type="radio" id="newServiceCreate" value="newServiceType=create" <%= newServiceType.equalsIgnoreCase("create") ? "checked" : "" %>><label for="newServiceCreate">義工捐贈</label>
        <input name="newServiceType" type="radio" id="newServiceRequest" value="newServiceType=request" <%= newServiceType.equalsIgnoreCase("request") ? "checked" : "" %>><label for="newServiceRequest">徵求義工 </label></td>
      <td width="54" height="60" align="left">&nbsp;</td>
    </tr>
    <tr>
      <td height="60" colspan="2" align="right" scope="row"><label for="customerId">*會員編號：</label></td>
      <td height="60" colspan="2"><select name="customerId" class="textfield_style" id="customerId">
        <%
          for (Customer customer : customers) {
          %>
        <option value="<%= KeyFactory.keyToString(customer.getKey()) %>"><%= customer.getCustomerSerialNumber() %></option>
        <%
          }
          %>
      </select></td>
      <td height="60" align="left">&nbsp;</td>
    </tr>
    <tr>
      <td height="60" colspan="2" align="right" scope="row"><label for="serviceTypeId">*義工類型：</label></td>
      <td height="60" colspan="2"><select name="serviceTypeId" class="textfield_style" id="serviceTypeId">
        <%
          for (ServiceType serviceType : serviceTypes) {
          %>
        <option value="<%= serviceType.getKey() %>" <% if(request.getParameter("serviceTypeId") != null && request.getParameter("serviceTypeId").equals(String.valueOf(serviceType.getKey())) ){ out.print("selected"); } %>><%= serviceType.getServiceTypeName() %></option>
        <%
          }
          %>
      </select></td>
      <td height="60" align="left">&nbsp;</td>
    </tr>
    <tr>
      <td height="60" colspan="2" align="right" scope="row"><label for="serviceDescription">*義工描述：</label></td>
      <td height="60" colspan="2">
        <input type="text" name="serviceDescription" class="textfield_style" id="serviceDescription"></td>
      <td width="54" height="60">&nbsp;</td>
    </tr>
    </table>
    <table width="800" border="0" align="center" cellpadding="0" cellspacing="1">
    <tr>
      <td width="282" align="center">&nbsp;</td>
    </tr>
    <tr><td align="center"><input type="submit" name="NewStorageSubmit" id="NewStorageSubmit" class="css_btn_class" value="儲存">&nbsp;<input type="button" name="buttonClose" id="buttonClose" class="css_btn_class" onclick="window.location.href='listService.jsp'" value="關閉"></td>
      </tr>
</table>

  </form>
</div>
<%@include file="../footer/footer.jsp" %>
</body>
</html>