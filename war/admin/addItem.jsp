<%@ page contentType="text/html; charset=utf-8" language="java" errorPage="" %>
<%@ page import="datastore.Customer" %>
<%@ page import="datastore.CustomerManager" %>
<%@ page import="datastore.User" %>
<%@ page import="datastore.ItemCategory" %>
<%@ page import="datastore.ItemCategoryManager" %>
<%@ page import="datastore.ItemType" %>
<%@ page import="datastore.ItemTypeManager" %>
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
<link href="../css/addItem.css" rel="stylesheet" type="text/css">
<script src="../javascript/jquery-1.10.1.min.js"></script>
<script src="../javascript/jquery.datetimepicker.js" type="text/javascript"></script>
<script src="../javascript/addItem.js"></script>
<script type="text/javascript">
function MM_jumpMenu(targ,selObj,restore){ //v3.0
  eval(targ+".location='"+location.search+"&itemCategoryId="+selObj.options[selObj.selectedIndex].value+"'");
  if (restore) selObj.selectedIndex=0;
}
</script>
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

String newItemType = request.getParameter("newItemType");
if (newItemType == null) {
	newItemType = "create";
}

List<ItemCategory> itemCategories = ItemCategoryManager.getAllItemCategories();
String itemCategoryKeyString = request.getParameter("itemCategoryId");
Long itemCategoryKey = itemCategoryKeyString != null ? Long.parseLong(itemCategoryKeyString) : (!itemCategories.isEmpty() ? itemCategories.get(0).getKey() : null);
ItemCategory thisItemCategory = itemCategoryKey != null ? ItemCategoryManager.getItemCategory(itemCategoryKey) : (!itemCategories.isEmpty() ? itemCategories.get(0) : null);

List<ItemType> itemTypes = itemCategoryKey != null ? ItemTypeManager.getItemTypes(itemCategoryKey) : ItemTypeManager.getAllItemTypes();
%>

<body>
<%@include file="../header/header.jsp" %>
<div id="menu_back">
<div id="menu">
<%@include file="../menu/menu.jsp" %>
</div></div>
<div id="content_area">
<div class="title_bar title_name">新增物品</div>
	<form id="addStorageform" name="addStorageform" method="post" action="/manageCustomer?action=add&type=<%= newItemType.equalsIgnoreCase("create") ? "item" : "itemRequest" %>&keep_adding=true">
  <table width="800" border="0" align="center" cellpadding="0" cellspacing="1">
    <tr>
      <td width="78" height="73" align="right" class="form_underline" scope="row">&nbsp;</td>
      <td height="73" colspan="3" align="left" class="form_underline" scope="row">請輸入欲新增的物品資料：</td>
      <td height="73" class="form_underline"></td>
    </tr>
    <tr>
      <td height="60" colspan="2" align="right" scope="row">*新增分類：</td>
      <td height="60" colspan="2">
        <input name="newItemType" type="radio" id="newItemCreate" value="newItemType=create" <%= newItemType.equalsIgnoreCase("create") ? "checked" : "" %>><label for="newItemCreate">物品捐贈</label>
        <input name="newItemType" type="radio" id="newItemRequest" value="newItemType=request" <%= newItemType.equalsIgnoreCase("request") ? "checked" : "" %>><label for="newItemRequest">徵求物品 </label></td>
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
      <td width="282" height="60" colspan="2" align="right" scope="row"><label for="itemCategoryId">*物品目錄：</label></td>
      <td height="60" colspan="2"><select name="itemCategoryId" class="textfield_style" id="itemCategoryId" onChange="MM_jumpMenu('parent',this,0)">
        <%
          for (ItemCategory itemCategory : itemCategories) {
          %>
        <option value="<%= itemCategory.getKey() %>" <% if(request.getParameter("itemCategoryId") != null && request.getParameter("itemCategoryId").equals(String.valueOf(itemCategory.getKey())) ){ out.print("selected"); } %>><%= itemCategory.getItemCategoryName() %></option>
        <%
          }
          %>
      </select></td>
      <td height="60" align="left">&nbsp;</td>
    </tr>
    <tr>
      <td height="60" colspan="2" align="right" scope="row"><label for="itemTypeId">*物品類型：</label></td>
      <td height="60" colspan="2"><select name="itemTypeId" class="textfield_style" id="itemTypeId">
        <%
          for (ItemType itemType : itemTypes) {
          %>
        <option value="<%= itemType.getKey() %>" <% if(request.getParameter("itemTypeId") != null && request.getParameter("itemTypeId").equals(String.valueOf(itemType.getKey())) ){ out.print("selected"); } %>><%= itemType.getItemTypeName() %></option>
        <%
          }
          %>
      </select></td>
      <td height="60" align="left">&nbsp;</td>
    </tr>
    <%
    if (newItemType.equalsIgnoreCase("create")) {
    %>
    <tr>
      <td height="60" colspan="2" align="right" scope="row"><label for="itemName">*物品名稱：</label></td>
      <td height="60" colspan="2">
        <input type="text" name="itemName" class="textfield_style" id="itemName"></td>
      <td width="54" height="60">&nbsp;</td>
    </tr>
    <%
    }
    %>
    <tr>
      <td height="60" colspan="2" align="right" scope="row"><label for="itemDescription">物品描述：</label></td>
      <td height="60" colspan="2">
        <input type="text" name="itemDescription" class="textfield_style" id="itemDescription"></td>
      <td width="54" height="60">&nbsp;</td>
    </tr>
    <%
    if (newItemType.equalsIgnoreCase("create")) {
    %>
    <tr>
      <td height="60" colspan="2" align="right" scope="row"><label for="itemPrice">物品價格：</label></td>
      <td height="60" colspan="2">
        <input name="itemPrice" type="text" class="textfield_style" id="itemPrice"></td>
      <td width="54" height="60" align="left">&nbsp;</td>
    </tr>
    <tr>
      <td height="60" colspan="2" align="right" scope="row">*捐/交換：</td>
      <td height="60" colspan="2">
        <input name="itemIsForWhat" type="radio" id="itemIsForWhat" value="donation" checked/><label for="itemIsForDonation">捐</label>
        <input name="itemIsForWhat" type="radio" id="itemIsForWhat" value="exchange"><label for="itemIsForExchange">交換</label></td>
      <td width="54" height="60" align="left">&nbsp;</td>
    </tr>
    <tr>
      <td height="60" colspan="2" align="right" scope="row"><label for="itemExpirationTime">有效天數：</label></td>
      <td height="60" colspan="2">
        <input name="itemExpirationTime" type="text" class="textfield_style" id="itemExpirationTime"></td>
      <td width="54" height="60" align="left">&nbsp;</td>
    </tr>  
    <%
    }
    %>
    </table>
    <table width="800" border="0" align="center" cellpadding="0" cellspacing="1">
    <tr>
      <td width="282" align="center">&nbsp;</td>
    </tr>
    <tr><td align="center"><input type="submit" name="NewStorageSubmit" id="NewStorageSubmit" class="css_btn_class" value="儲存">&nbsp;<input type="button" name="buttonClose" id="buttonClose" class="css_btn_class" onclick="window.location.href='listItem.jsp'" value="關閉"></td>
      </tr>
</table>

  </form>
</div>
<%@include file="../footer/footer.jsp" %>
</body>
</html>