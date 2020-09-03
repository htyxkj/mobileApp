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
    <script src="../js/dingtalk.open.js"></script>
    <script type="text/javascript">
        $(function() {  
            if(getCookie("out")!="yes"){
            	setCookie("out","yes",1);
            }else{
				delCookie("out");
				dd.biz.navigation.goBack({
					onSuccess : function(result) {
						/*result结构
						{}
						*/
					},
					onFail : function(err) {}
				})
            } 
        	var appId = getQueryVariable('appId');
        	var corpId = getQueryVariable('corpId');
        	var bipAppId = getQueryVariable('bipAppId');
        	var appkey =  getQueryVariable('appkey');
        	dd.ready(function() {
        	    dd.runtime.permission.requestAuthCode({
        	        corpId: corpId,
        	        onSuccess: function(info) {
        	        	document.getElementById("code").value = info.code;
        	        	document.getElementById("appId").value = appId;
        	        	document.getElementById("corpId").value = corpId;
        	        	document.getElementById("bipAppId").value = bipAppId;
        	        	document.getElementById("appkey").value = appkey;
        	        	document.getElementById('form').submit(); 
        	        },
        	        onFail : function(err) {}
        	  
        	    });
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
        
    function setCookie(name,value,days){
        var exp=new Date();
        exp.setTime(exp.getTime() + days*24*60*60*1000);
        var arr=document.cookie.match(new RegExp("(^| )"+name+"=([^;]*)(;|$)"));
        document.cookie=name+"="+escape(value)+";expires="+exp.toGMTString();
    }
    function getCookie(name){
        var arr=document.cookie.match(new RegExp("(^| )"+name+"=([^;]*)(;|$)"));
        if(arr!=null){
           return unescape(arr[2]); 
        }
    }
    function delCookie(name){
       var exp=new Date();
       exp.setTime(exp.getTime()-1);
       var cval=getCookie(name);
       if(cval!=null){
           document.cookie=name+"="+cval+";expires="+exp.toGMTString();
       }
    } 
    </script>
</head>
<body>
<form action="../DLoginServlet" id = "form" method="post" name="form">
	<input type="hidden" name="code" id ="code" value=""></input>
	<input type="hidden" name="appkey" id ="appkey" value=""></input>
	<input type="hidden" name="appId" id ="appId" value=""></input>
	<input type="hidden" name="corpId" id ="corpId" value=""></input>
	<input type="hidden" name="bipAppId" id ="bipAppId" value=""></input>
</form>
<div style="text-align: center; padding-top: 150px; font-size: 2.4rem;">页面正在努力加载中......</div>
</body>
</html>
