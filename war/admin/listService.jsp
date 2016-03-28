<%@ page contentType="text/html; charset=utf-8" language="java" errorPage="" %>
<%@ page import="datastore.Customer" %>
<%@ page import="datastore.CustomerManager" %>
<%@ page import="datastore.Service" %>
<%@ page import="datastore.ServiceManager" %>
<%@ page import="datastore.ServiceRequest" %>
<%@ page import="datastore.ServiceRequestManager" %>
<%@ page import="datastore.ServiceType" %>
<%@ page import="datastore.ServiceTypeManager" %>
<%@ page import="datastore.User" %>
<%@ page import="datastore.User.UserType"%>
<%@ page import="util.Printer" %>
<%@ page import="util.Dictionary" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page import="com.google.appengine.api.datastore.Key" %>
<%@ page import="com.google.appengine.api.datastore.KeyFactory" %>

<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>慈惠之泉</title>
<link href="../css/main_list_css.css" rel="stylesheet" type="text/css">
<link href="../css/public.css" rel="stylesheet" type="text/html; charset=utf-8">
<link href="../css/donate_form.css" rel="stylesheet" type="text/css">
<script src="../javascript/jquery-1.10.1.min.js"></script>
<script src="../javascript/slideeffect.js"></script>
</head>

<%
User sessionUser = (User)session.getAttribute("user");

if (sessionUser != null) {
	if (sessionUser.getUserType() != User.UserType.ADMINISTRATOR
		&& sessionUser.getUserType() != User.UserType.CUSTOMER) {
  		response.sendError(HttpServletResponse.SC_UNAUTHORIZED);  		
  	}
}

List<ServiceType> serviceTypes = ServiceTypeManager.getAllServiceTypes();
%>

<body>
<%@include file="../header/header.jsp" %>
<div id="menu_back"><div id="menu"><%@include file="../menu/menu.jsp" %></div></div>
<div id="content_area" style="">
  <div class="title_bar title_name"><table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td height="50" valign="middle">義工服務</td>
    <td width="2%" valign="middle">&nbsp;</td>
    <%
    if (sessionUser == null) {
    %>
    	<td width="12%" valign="middle"><a  href="../loginCustomer.jsp"><span class="font_18">&nbsp;居民登入</span></a></td>
    <%
    }
    if (sessionUser != null) {
    %>
    <td width="3%" valign="middle"><a href="addService.jsp"><img src="../images/round_plus.png" width="20" height="20" alt="新增管理者"></a></td>
    <td width="12%" valign="middle"><a  href="addService.jsp"><span class="font_18">&nbsp;新增義工</span></a></td>
    <%
    }
    %>
  </tr>
</table>
  </div>
<div class="donate_need_area" style="float:left;">
  <div class="donate_need_title">義工服務</div>
  <%
  for (ServiceType serviceType : serviceTypes) {
	  List<Service> services = ServiceManager.getAllServicesFromServiceType(serviceType.getKey());
	  if (services.isEmpty()) {
		  continue;
	  }
  %>
	  <div class="listTitle_CSS form_underline_blue radiusLeft radiusRight TypeTitle"><%= serviceType.getServiceTypeName() %></div>
	  <div class="TypeContent">
	  <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" style="border:1px solid #E9E9E9">
	    <tr class="listCotent_CSS">
	      <td width="83%" align="center" bgcolor="#E0EEFC" class="font_18 form_underline_gray">描述</td>
	      <td width="17%" align="center" bgcolor="#E0EEFC" class="font_18 form_underline_gray">動作</td>
	    </tr>
	    <%
	    for (Service service : services) {
	    %>
		    <tr>
		      <td align="center" bgcolor="#FFFFFF" class="GetServiceDescription"><%= service.getServiceDescription() %></td>
		      <td align="center" bgcolor="#FFFFFF">
		      <!--jQuery use Service Id in property service_id-->
		      <img class="WantServiceClass" src="../images/taking.png" service_id="0" alt="" width="24" height="24" style="cursor:pointer;" title="我想要"/>
		      <% if (sessionUser != null) { if(service.getCustomerKey().equals(sessionUser.getKey().getParent()) || sessionUser.getUserType() == UserType.ADMINISTRATOR) { %><a href="editService.jsp?k=<%= KeyFactory.keyToString(service.getKey()) %>"><img src="../images/pencil_32.png"  alt="" width="24" height="24" title="編輯"/></a><a href="/manageCustomer?action=delete&type=service&k=<%= KeyFactory.keyToString(service.getKey()) %>"><img src="../images/close_32.png"  alt="" width="24" height="24" title="刪除"/></a><%} } %>
		      </td>
		    </tr>
	    <%
	  	}
	  	%>
	  </table>
	  </div>
  <%
  }
  %>
</div>

<div class="donate_need_area" style="float:left;">
  <div class="donate_need_title">徵求義工</div>
  
  <%
  for (ServiceType serviceType : serviceTypes) {
	  List<ServiceRequest> serviceRequests = ServiceRequestManager.getAllServiceRequestsFromServiceType(serviceType.getKey());
	  if (serviceRequests.isEmpty()) {
		  continue;
	  }
  %>
  <div class="listTitle_CSS form_underline_blue radiusLeft radiusRight TypeTitle"><%= serviceType.getServiceTypeName() %></div>
  <div class="TypeContent">
  <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" style="border:1px solid #E9E9E9">
    <tr class="listCotent_CSS">
      <td width="85%" align="center" bgcolor="#E0EEFC" class="font_18 form_underline_gray">徵求描述</td>
      <td width="15%" align="center" bgcolor="#E0EEFC" class="font_18 form_underline_gray">動作</td>
      </tr>
      <%
	  for (ServiceRequest serviceRequest : serviceRequests) {
	  %>
	      <tr>
	      <td align="center" bgcolor="#FFFFFF"><%= serviceRequest.getServiceRequestDescription() %></td>
	      <td align="center" bgcolor="#FFFFFF">
	      <% if (sessionUser != null) { if(serviceRequest.getCustomerKey().equals(sessionUser.getKey().getParent()) || sessionUser.getUserType() == UserType.ADMINISTRATOR) { %><a href="editServiceRequest.jsp?k=<%= KeyFactory.keyToString(serviceRequest.getKey()) %>"><img src="../images/pencil_32.png"  alt="" width="24" height="24" title="編輯"/></a><a href="/manageCustomer?action=delete&type=serviceRequest&k=<%= KeyFactory.keyToString(serviceRequest.getKey()) %>"><img src="../images/close_32.png"  alt="" width="24" height="24" title="刪除"/></a> <%} } %>
	      </td>
	      </tr>
      <%
	  }
      %>
  </table>
  </div>
    <%
  }
  %>
  
</div>
</div>

<div id="inputMemberId">
<form action="" method="post" id="AcceptToGetServiceForm">
<table width="100%" border="0">
  <tr>
    <td height="30" colspan="4" align="center" bgcolor="#999999" style="color: #FFF">接受捐贈</td>
    </tr>
  <tr>
    <td height="47" colspan="4" align="center" id="ShowServiceName"></td>
    </tr>
  <tr>
    <td width="33%" height="62" align="right">會員編號：</td>
    <td width="67%" height="62" colspan="3" align="left">
      <input type="text" name="serviceName" class="textfield_style" id="serviceName" style="width:180px;"></td>
  </tr>
  <tr>
    <td height="40" colspan="4" align="center"><input type="submit" name="NewStorageSubmit" id="NewStorageSubmit" class="css_btn_class" value="確認">
      &nbsp;
      <input type="button" name="buttonClose" id="buttonClose" class="css_btn_class" value="關閉"></td>
    </tr>
</table>
<input name="acceptServiceId" type="hidden" id="acceptServiceId">
</form>
</div>
<%@include file="../footer/footer.jsp" %>
</body>
</html>