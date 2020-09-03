<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>注册</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=yes">
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script src='js/jquery-3.0.0.js'></script>
	<script type="text/javascript">
		$(function(){
			$('#zc').click(function () {
                    $.ajax({
                        type: "POST",
                        url: "ExpiredServlet",
                        data: {
                        	wxscmid: $("input[name='wxscmid']").val(),
                        	regInfo: $("textarea[name='regInfo']").val()
                        },
                        dataType: "JSON",
                        success: function (data) {
                            if (data.success == 'ok') {
                            	alert("注册成功,请重新进入应用!");
                            	WeixinJSBridge.call('closeWindow');
                            } else {
                            	alert("注册失败,请联系系统管理员!");
                            }
                        },
                        error: function(jqXHR, textStatus, errorThrown) {
                            alert("网络超时，请稍后重试");
                        }
                    });
                });
		});
	</script>
  </head>
  <body>
 	<input type="hidden" value="${wxscmid}" name="wxscmid"/>
  	<div style="text-align:center;margin-top:25%">
  		<h2>${errorInfo}</h2>
  		<textarea rows="10" cols="30" name="regInfo">${regInfo}</textarea>
  		<div  style="text-align:center;margin-top:15%">
  		<input type="button" value="注册" id="zc" style="width:100px;height:30px;">
  		</div>
  	</div>
  </body>
</html>
