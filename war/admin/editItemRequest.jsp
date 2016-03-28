<%@ page contentType="text/html; charset=utf-8" language="java" errorPage="" %>
<%@ page import="datastore.Customer" %>
<%@ page import="datastore.CustomerManager" %>
<%@ page import="datastore.ItemRequest" %>
<%@ page import="datastore.ItemRequestManager" %>
<%@ page import="datastore.ItemCategory" %>
<%@ page import="datastore.ItemCategoryManager" %>
<%@ page import="datastore.ItemType" %>
<%@ page import="datastore.ItemTypeManager" %>
<%@ page import="datastore.City" %>
<%@ page import="datastore.CityManager" %>
<%@ page import="datastore.District" %>
<%@ page import="datastore.DistrictManager" %>
<%@ page import="datastore.Village" %>
<%@ page import="datastore.VillageManager" %>
<%@ page import="datastore.User" %>
<%@ page import="util.DateManager" %>
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
<link href="../css/addItemRequest.css" rel="stylesheet" type="text/css">
<script src="../javascript/jquery-1.10.1.min.js"></script>
<script src="../javascript/jquery.datetimepicker.js" type="text/javascript"></script>
<script src="../javascript/addItemRequest.js"></script>
<script type="text/javascript">
function MM_jumpMenu(targ,selObj,restore){ //v3.0
  eval(targ+".location='?<%= "k=" + request.getParameter("k") %>&itemCategoryId="+selObj.options[selObj.selectedIndex].value+"'");
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

String itemRequestKeyString = request.getParameter("k");
Key itemRequestKey = KeyFactory.stringToKey(itemRequestKeyString);
ItemRequest itemRequest = ItemRequestManager.getItemRequest(itemRequestKey);

ItemType thisItemType = ItemTypeManager.getItemType(itemRequest.getItemType());

List<ItemCategory> itemCategories = ItemCategoryManager.getAllItemCategories();
String itemCategoryKeyString = request.getParameter("itemCategoryId");
Long itemCategoryKey = itemCategoryKeyString != null ? Long.parseLong(itemCategoryKeyString) : thisItemType.getItemCategory();
ItemCategory thisItemCategory = ItemCategoryManager.getItemCategory(itemCategoryKey);

List<ItemType> itemTypes = ItemTypeManager.getItemTypes(itemCategoryKey);

Customer customer = CustomerManager.getCustomer(itemRequest.getCustomerKey());
%>

<body>
<%@include file="../header/header.jsp" %>
<div id="menu_back">
<div id="menu">
<%@include file="../menu/menu.jsp" %>
</div></div>
<div id="content_area">
<div class="title_bar title_name">編輯物品徵求</div>
	<form id="addStorageform" name="addStorageform" method="post" action="/manageCustomer?k=<%= itemRequestKeyString %>&action=update&type=itemRequest">
  
  <table width="800" border="0" align="center" cellpadding="0" cellspacing="1">
    <tr>
      <td width="282" height="73" align="right" class="form_underline" scope="row">&nbsp;</td>
      <td height="73" colspan="3" align="left" class="form_underline" scope="row">請輸入欲修改的物品徵求資料：</td>
      <td height="73" class="form_underline"></td>
    </tr>
    <tr>
      <td height="60" colspan="2" align="right" scope="row"><label for="customerId">會員編號：</label></td>
      <td height="60" colspan="2">
        <input type="text" name="customerId" class="textfield_style" id="customerId" value="<%= customer.getCustomerSerialNumber() %>" disabled></td>
      <td width="54" height="60">&nbsp;</td>
    </tr>
    <tr>
      <td width="282" height="60" colspan="2" align="right" scope="row"><label for="itemCategoryId">*物品目錄：</label></td>
      <td height="60" colspan="2"><select name="itemCategoryId" class="textfield_style" id="itemCategoryId" onChange="MM_jumpMenu('parent',this,0)">
        <%
        for (ItemCategory itemCategory : itemCategories) {
        %>
        <option value="<%= itemCategory.getKey() %>" <% if(itemCategoryKey.equals(itemCategory.getKey())){ out.print("selected"); } %>><%= itemCategory.getItemCategoryName() %></option>
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
        <option value="<%= itemType.getKey() %>" <%= itemType.getKey().equals(thisItemType.getKey()) ? "selected" : "" %>><%= itemType.getItemTypeName() %></option>
        <%
        }
        %>
      </select></td>
      <td height="60" align="left">&nbsp;</td>
    </tr>
    <tr>
      <td height="60" colspan="2" align="right" scope="row"><label for="itemDescription">物品徵求描述：</label></td>
      <td height="60" colspan="2">
        <input type="text" name="itemDescription" class="textfield_style" id="itemDescription" value="<%= itemRequest.getItemRequestDescription() %>"></td>
      <td width="54" height="60">&nbsp;</td>
    </tr>
    </table>
  <table width="800" border="0" align="center" cellpadding="0" cellspacing="1">
    <tr>
      <td></td>
      <td colspan="3" align="center" scope="row">&nbsp;</td>
      <td></td>
    </tr>
    <tr>
      <td width="282" height="10" align="right" class="form_underline" scope="row">&nbsp;</td>
      <td height="10" colspan="3" align="left" class="form_underline" scope="row">&nbsp;</td>
      <td height="10" class="form_underline"></td>
    </tr>
    <tr>
      <td height="30" colspan="5" align="center" scope="row"></td>
    </tr>
    <tr>
      <td height="60" colspan="5" align="center" scope="row">
      <table width="100%" border="0">
        <tr>
          <td height="50" colspan="4" align="center" class="listTitle_CSS form_underline_blue radiusLeft radiusRight TypeTitle">徵求人資料</td>
          </tr>
        <tr>
          <td width="19%" height="40" align="right" bgcolor="#DDDDDD">姓名：</td>
          <td width="29%" height="40" align="center" bgcolor="#DDDDDD"><%= customer.getCustomerName() %></td>
          <td width="16%" height="40" align="right" bgcolor="#DDDDDD">身分證字號：</td>
          <td width="36%" height="40" align="center" bgcolor="#DDDDDD"><%= customer.getCustomerIdentityNumber() %></td>
        </tr>
        <tr>
          <td height="40" align="right" bgcolor="#EEEEEE">綽號：</td>
          <td height="40" align="center" bgcolor="#EEEEEE"><%= customer.getCustomerNickname() %></td>
          <td height="40" align="right" bgcolor="#EEEEEE">會員編號：</td>
          <td height="40" align="center" bgcolor="#EEEEEE"><%= customer.getCustomerSerialNumber() %></td>
        </tr>
        <%
        Village village = VillageManager.getVillage(customer.getVillage());
        District district = DistrictManager.getDistrict(customer.getVillage().getParent());
        City city = CityManager.getCity(customer.getVillage().getParent().getParent());
        %>
        <tr>
          <td height="40" align="right" bgcolor="#DDDDDD">城市：</td>
          <td height="40" align="center" bgcolor="#DDDDDD"><%= city.getCityName() %></td>
          <td height="40" align="right" bgcolor="#DDDDDD">鄉鎮區：</td>
          <td height="40" align="center" bgcolor="#DDDDDD"><%= district.getDistrictName() %></td>
        </tr>
        <tr>
          <td height="40" align="right" bgcolor="#DDDDDD">村里：</td>
          <td height="40" align="center" bgcolor="#DDDDDD"><%= village.getVillageName() %></td>
          <td height="40" align="right" bgcolor="#DDDDDD"></td>
          <td height="40" align="center" bgcolor="#DDDDDD"></td>
        </tr>
      </table>
      </td>
      </tr>
    <tr>
      <td colspan="5" align="center">&nbsp;</td>
    </tr>
    <tr><td colspan="5" align="center"><input type="submit" name="NewStorageSubmit" id="NewStorageSubmit" class="css_btn_class" value="儲存">&nbsp;<input type="button" name="buttonClose" id="buttonClose" class="css_btn_class" onclick="window.location.href='listItem.jsp'" value="關閉"></td>
      </tr>
</table>

  </form>
</div>
<%@include file="../footer/footer.jsp" %>
</body>
</html>