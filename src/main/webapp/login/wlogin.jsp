<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fun" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title></title>
    <script src='../js/jquery-3.0.0.js'></script>
    <script type="text/javascript">
        $(function() {
        	//http://192.168.0.200:8080/weixinweb/login/wlogin.jsp?url=http%3A%2F%2F192.168.0.200%3A8080%2Fweixinweb%2FWLoginServlet%3FcorpId%3Dwx33a1a7296219a4fb%26appId%3D1000002%26bipAppId%3D04
        	//https://open.weixin.qq.com/connect/oauth2/authorize?appid=CORPID
        	//&redirect_uri=REDIRECT_URI&response_type=code&scope=snsapi_base&state=556as4df56as4gd6as#wechat_redirect
       		var url = getQueryVariable('url');
        	var corpid = getQueryVariable('corpid');
        	var codeURL = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=";
        	codeURL = codeURL + corpid+"&redirect_uri="+url+"&response_type=code&scope=snsapi_base&state=556as4df56as4gd6as#wechat_redirect";
	    	$.ajax({
	            type: "post",
	            url: codeURL, 
	            success: function (data) {
	            	var obj = eval('(' + data + ')');
	             	if(obj.code == 0){
	             		location.href = obj.toUrl;
	             	}
	            }
	        }); 
        });
        //获取浏览器地址栏参数
        function getQueryVariable(variable){
               var query = window.location.search.substring(1);
               var vars = query.split("&");
               for (var i=0;i<vars.length;i++) {
                       var pair = vars[i].split("=");
                       if(pair[0] == variable){return pair[1];}
               }
               return(false);
        }
    </script>
</head>
<body>
<div style="text-align: center; padding-top: 100px; font-size: 2.4rem;">页面正在努力加载中......</div>
</body>
</html>
