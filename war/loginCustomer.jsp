<%@ page contentType="text/html; charset=utf-8" language="java" errorPage="" %>
<%@ page import="datastore.User" %>
<%@ page import="util.Printer" %>
<%@ page import="util.Dictionary" %>

<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>慈惠之泉</title>
<link href="css/public.css" rel="stylesheet" type="text/css">
<link href="css/login.css" rel="stylesheet" type="text/css">
</head>

<% 
	Printer printer = new Printer(Dictionary.Language.CHINESE);
	String language = request.getParameter("lang") != null ? request.getParameter("lang") : "CH";
	if(language.equals("EN")) {
			printer.setLanguage(Dictionary.Language.ENGLISH);
	}
	if(language.equals("CH")) {
			printer.setLanguage(Dictionary.Language.CHINESE);
	}

	// If user is logged in, don't allow this page to open
	User sessionUser = (User) session.getAttribute("user");
	if (sessionUser != null) {
		// we check the user type to send him to his/her own main page
		/*
    	switch(sessionUser.getUserType()) {
        	case ADMINISTRATOR:
                response.sendRedirect("/admin/listAdmin.jsp");
                break;
            case CUSTOMER:
                response.sendRedirect("/admin/listCustomer.jsp");
                break;
            case CUSTOMER_USER:
                response.sendRedirect("/admin/listEmployee.jsp");
                break;
            default:
                // there should be no other
                // type of user
                assert(false);
        }
		*/
	}
%>


<body>
<%@include file="./header/header.jsp" %>
<div id="content_1"><table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="68%" height="502"><div id="login_area">
      <div id="login_logo">
        <table width="100%" height="48" border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td width="20%" height="48" align="center" valign="middle" class="list_font">登入</td>
            <td width="11%">&nbsp;</td>
            <td width="23%">&nbsp;</td>
            <td width="23%">&nbsp;</td>
            <td width="23%">&nbsp;</td>
          </tr>
        </table>
      </div>
      <form name="form1" method="post" action="/attemptLogin">
      <table width="100%"  border="0" cellpadding="0" cellspacing="0">
        <tr class="form_underline">
          <td height="72" colspan="3" align="center" class="form_underline">請輸入您的會員編號：</td>
          <td width="40%" height="72" class="form_underline">&nbsp;</td>
          <td height="72" colspan="2" class="form_underline">&nbsp;</td>
        </tr>
        <tr>
          <td height="55" colspan="2" align="center"></td>
          <td width="13%" height="55" valign="bottom"></td>
          <td width="40%" height="55"></td>
          <td height="55" colspan="2">&nbsp;</td>
        </tr>
        <tr>
        <td height="100" colspan="3" align="right" class="list_font"><label for="userName">會員編號：</label></td>
          <td height="100" colspan="3"><table width="393" height="39" border="0" align="left" cellpadding="0" cellspacing="0" class="form_line">
            <tr height="39" class="form_line form_hight">
              <td id='account_png' width="39" valign="middle"></td>
              <td width="352">
                <input type="text" name="userName" class="textfield_login" id="login_account"></td>
              </tr>
          </table></td>
          </tr>
        <tr>
          <td height="36" colspan="6" align="center" class="form_underline">
            
            <div class="login_failed">
              <%	if(request.getParameter("etype") != null && request.getParameter("etype").equals("InvalidInfo")) {
					out.print("Either the account or the password don't match.");
				}
				else if(request.getParameter("etype") != null && request.getParameter("etype").equals("CustomerNotActive")) {
					out.print("This customer is not active in the system.");
				}
				else if(request.getParameter("action") != null && request.getParameter("action").equals("destroy"))
				{
					out.print("Logout successful!");
				}
			%>
              
            </div>          </td>
          </tr>
        <tr>
          <td width="3%" height="20" >&nbsp;</td>
          <td width="12%" height="20" >&nbsp;</td>
          <td height="20" >&nbsp;</td>
          <td height="20" >&nbsp;</td>
          <td height="20" colspan="2" >&nbsp;</td>
        </tr>
        
        <tr>
          <td height="48" colspan="6" align="center" valign="bottom" >
            <p>
              <input name="button" type="submit" class="css_btn_class" id="button" value="登入">
              &nbsp;<input name="button" type="reset" class="css_btn_class" id="button" value="重置">
              <br>
              </p></td>
          </tr>
        <tr>
          <td height="20" colspan="3" align="right" valign="bottom" >&nbsp;</td>
          <td height="20" colspan="2" align="right" valign="middle" ><a href="/createCustomer.jsp"><span style="color:#262626">註冊新帳號</span></a></td>
          <td width="3%" height="20" align="center" valign="bottom" ><a href="/createCustomer.jsp"><img src="images/round_plus.png" width="20" height="20" alt=""/></a></td>
        </tr>
      </table>
      </form>
    </div></td>
  </tr>
</table>
</div>
<%@include file="./footer/footer.jsp" %>
</body>
</html>
