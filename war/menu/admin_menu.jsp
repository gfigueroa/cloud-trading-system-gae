
<%@ page import="datastore.User" %>
<%@ page import="util.Printer" %>
<%@ page import="util.Dictionary" %>

<!doctype html>

<meta charset="utf-8">
<title></title><%@ page contentType="text/html; charset=utf-8" language="java" errorPage="" %>
<style type="text/css">
</style>
<link href="../css/main_list_css.css" rel="stylesheet" type="text/css">
<link href="../css/public.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="../javascript/main_list.js"></script>

<div id="main_list">
      <div class="main_list_font_size" id="main_list_1"><a href="../admin/listAdmin.jsp" class="main_list_font_size">系統管理者</a></div>
      <div class="main_list_font_size" id="main_list_2"><a href="../admin/listCustomer.jsp" class="main_list_font_size">居民</a></div>
      <!--<div class="main_list_font_size" id="main_list_3">Storage equipment</div>-->
      <div class="main_list_font_size" id="main_list_4"><a href="listItem.jsp" class="main_list_font_size">物品捐贈</a></div>
      <div class="main_list_font_size" id="main_list_5"><a href="listService.jsp" class="main_list_font_size">義工服務</a></div>
      <!--<div class="main_list_font_size" id="main_list_6">Marketing</div>-->
      <div class="main_list_font_size" id="main_list_7">設定</div>
      
</div>
<div id="main_list_1_content">
  <div id="main_list_1_area"></div>
</div>
<!--<div id="main_list_2_content">
  <div id="main_list_2_area">
    <table width="100%" height="80" border="0" align="center" cellpadding="0" cellspacing="0">
      <tr>
        <td height="40" align="left" class="font_18 form_underline"> &nbsp;<a href="../admin/listCustomer.jsp" class="font_18">Customer</a></td>
      </tr>
      <tr>
        <td height="40" align="left" class="font_18 form_underline">&nbsp;<a href="listEmployee.jsp" class="font_18">Operator</a></td>
      </tr>
    </table>
  </div>
</div>-->
<div id="main_list_3_content">
  <div id="main_list_3_area"><table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0">
      <tr>
        <td height="40" align="left" class="font_18 form_underline"> &nbsp;<a href="../admin/listEquipmentType.jsp" class="font_18">Type</a></td>
      </tr>
      <tr>
        <td height="40" align="left" class="font_18 form_underline">&nbsp;<a href="../admin/listEquipmentModel.jsp" class="font_18">Model</a></td>
      </tr>
      <tr>
        <td height="40" align="left" class="font_18 form_underline">&nbsp;<a href="../admin/listEquipmentStorage.jsp" class="font_18">Equipment list</a></td>
      </tr>
      <tr>
        <td height="40" align="left" class="font_18 form_underline">&nbsp;<a href="../admin/listSensor.jsp" class="font_18">Sensor</a></td>
      </tr>
      
      
      </table>
  </div>
</div>
<!--<div id="main_list_4_content">
  <div id="main_list_4_area"><table width="100%" height="40" border="0" align="center" cellpadding="0" cellspacing="0">
      <tr>
        <td height="40" align="left" class="font_18 form_underline"> &nbsp;<a href="../admin/listItem.jsp" class="font_18">Item</a></td>
      </tr>
      <tr>
        <td height="40" align="left" class="font_18 form_underline"> &nbsp;<a href="../admin/listStorage.jsp" class="font_18">Storage</a></td>
      </tr>
  </table></div>
</div>
<div id="main_list_5_content">
  <div id="main_list_5_area"><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
  	  <tr>
        <td height="40" align="left" class="font_18 form_underline"> &nbsp;<a class="font_18" href="../admin/listAlarmMessage.jsp">Alarm Message</a></td>
      </tr>
      <tr>
        <td height="40" align="left" class="font_18 form_underline"> &nbsp;<a class="font_18" href="../admin/listAlarmMessageManage.jsp">Alarm Warning Rule</a></td>
      </tr>
      <tr>
        <td height="40" align="left" class="font_18 form_underline"> &nbsp;<a class="font_18" href="../admin/listAlarmRule.jsp">Alarm Trigger Rule</a></td>
      </tr>
    </table></div>
</div>
--><div id="main_list_6_content">
  <div id="main_list_6_area"><table width="100%" height="40" border="0" align="center" cellpadding="0" cellspacing="0">
      <tr>
        <td height="40" align="left" class="font_18 form_underline"> &nbsp;<a class="font_18" href="listNews.jsp">Marketing news</a></td>
      </tr>
  </table></div>
</div>
<div id="main_list_7_content">
  <div id="main_list_7_area"><table width="100%" hesight="120" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
        <td height="40" align="left" class="font_18 form_underline">&nbsp;<a href="../admin/listCity.jsp" class="font_18">都市</a></td>
      </tr>
      <tr>
        <td height="40" align="left" class="font_18 form_underline"> &nbsp;<a href="../admin/listDistrict.jsp" class="font_18">鄉鎮區</a></td>
      </tr>      
      <tr>
        <td height="40" align="left" class="font_18 form_underline">&nbsp;<a class="font_18" href="listVillage.jsp">村里</a></td>
      </tr>
      <tr>
        <td height="40" align="left" class="font_18 form_underline">&nbsp;<a class="font_18" href="listCategory.jsp">物品目錄</a></td>
      </tr>
      <tr>
        <td height="40" align="left" class="font_18 form_underline">&nbsp;<a class="font_18" href="listType.jsp">物品類別</a></td>
      </tr>
      <tr>
        <td height="40" align="left" class="font_18 form_underline">&nbsp;<a class="font_18" href="listServiceType.jsp">義工類別</a></td>
      </tr>
  </table></div>
</div>

</div>







