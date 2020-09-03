<!DOCTYPE html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ page import="mobile.entity.Message,java.lang.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<html>
<head>
    <base href="<%=basePath%>">
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=yes">
    <meta http-equiv="Access-Control-Allow-Origin" content="*">
    <meta content="telephone=no" name="format-detection"/>
    <title>公告内容</title>
    <link href='css/bootstrap.min.css' rel="stylesheet"/>
    <script src='js/jquery-3.0.0.js'></script>
    <script src='js/bootstrap.min.js'></script>
    <style type="text/css">
    	ul{width:100%;background-color: #FFFFFF;height:auto;padding-left:10px;}
    	li{list-style-type:none;width:96%;line-height:40px;border-bottom:solid 0px #CCC; overflow:hidden;font-size:15px;}
    	.li1{display:block;text-align:center;width:96%;font-size:24px;}
        html, body {
            width: 100%;
            overflow: hidden;
            margin: 0 auto;
            padding: 0;
        }
    </style>
    <script type="text/javascript">
    $(function(){
    	setTimeout(function() {  
    		 $.ajax({
    	            url:"UpOaggtzServlet",
    	            type:"post",
    	            data:{
    	                id:  $("#id").val()
    	            },dataType: "json",
    	            success: function (res) {
    	            },
    	            error: function (error) {
    	            }
    	        });
        }, 2000);
    });
    function file(url){  
    	url = encodeURIComponent(url);
    	window.location.href="AnnexServlet?url="+url;
    }
    </script>
</head>
	<body>
		<input type="hidden" id ="id" value="${data.sid}">
		<ul>
			<li class="li1">${data.title}</li>
			<li>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${data.content}</li>
			<li>
				<c:forEach var="fujian" items="${fujians}" varStatus="status">
	               附件${status.index+1}:<a onclick="javascript:file('${fujian.fullPath}')">${fujian.fileName}</a><br/>
	               <!-- 附件${status.index+1}:<a href='${fujian.fullPath}'>${fujian.fileName}</a><br/> -->
	            </c:forEach>
	        </li>
			<li><span style="float:right;"><fmt:formatDate value="${data.mkdate}" pattern="yyyy-MM-dd HH:mm:ss"/></span></li>
			<li><span style="float:right;">${data.smaker}</span></li>
		</ul>
	</body>
</html>