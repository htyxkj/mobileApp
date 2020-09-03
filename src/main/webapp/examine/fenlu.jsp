<!DOCTYPE html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="mobile.entity.Message,java.lang.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fun"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String usercode = request.getParameter("usercode");
	String documentstype = request.getParameter("documentstype");
	String serverurl = request.getParameter("serverurl");
	String documentsid = request.getParameter("documentsid");
	String dbid = request.getParameter("dbid");
	String flname = request.getParameter("flname");
%>
<head>
	<meta charset="UTF-8">
	<title></title> <!-- %=flname %> -->
	<script src='<%=basePath %>js/jquery-2.1.4.js'></script>
	<link rel="stylesheet" href="<%=basePath %>css/basics_fl.css">
    <link rel="stylesheet" href="<%=basePath %>css/Block-list.css">
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <meta content="telephone=no" name="format-detection" />
    <style type="text/css">
		.mydiv{
			margin-top:3px;
			margin-bottom:-10px;
			margin-left:2px;
			width:10%;
			clear:both;
			text-align:center;
			height:auto;
			background:#fff;color:#333;
			box-shadow:0px -1px 5px #909090;/*opera或ie9*/
			border-top-left-radius:1em;
			border-top-right-radius:1em;
		}
		.span1{color:#8B8E95;font-size:13px}
    </style>
	<script type="text/javascript">
    $(function(){
	  var html = "<div class='list-aa'><span class='list-bt'> </span></div>";
	  $.ajax({
			type : "POST",
			contentType : "application/x-www-form-urlencoded",
			url : "<%=serverurl%>lxvar",
			data :  {"VARID":"{&<%=documentstype%>FL}","DBID":"<%=dbid %>","USRCODE":"<%=usercode%>","CONT":"<%=documentsid%>","BINI":"1"},
			dataType : "JSON",
			success : function(data) { 
				for(var value = 0; value < data.values.length;value++){
					var va = data.values[value];
					html += "<div class=\"mydiv\"><span class=\"span1\">"+(value+1)+"</span></div>"
					+"<div class='list-a'>";  
					for(var i=0;i<data.showCols.length;i++){
						if(data.allCols[i]=="fjname"){
							//html += "<div class='list-aa'><span class='list-bt'>"+data.labers[oneValue]+"：</span><span class='list-hk'></span></div>";
							html += "<div class='list-aa'><span class='list-bt'>"+data.labers[i]+"：</span><span class='list-hk'>"+getUrl(va[data.allCols[data.showCols[i]]],va[data.allCols[data.showCols[i+1]]])+"</span></div>";
						}else if(data.allCols[i]=="fj_root"){
							
						}else{
						    var baseValue = va[data.allCols[data.showCols[i]]];
						    var checkValue = '';
						    if(baseValue){
                              checkValue = baseValue;
							}
							html += "<div class='list-aa'><span class='list-bt'>"+data.labers[i]+"：</span><span class='list-hk'>"+checkValue+"</span></div>";
						}
					} 
					html += "</div>";
				}
				$("#list").append(html);
			},
			error : function(err) {
				alert("系统错误，请联系系统管理员！");
			}
		});
    });

	function getUrl(names,url){
		var Str = "";
		if(names=="null" || names==null)return "无";
		var namearr = names.split(";");
		for(var name=0;name<namearr.length;name++){
			var file="";
			file ='<%=serverurl%>fileupdown?fud=1&rid=4&isweb=1&dbid=<%=dbid %>&filepath='+url+"/"+namearr[name];
			var url1 ="";
			url1 = encodeURIComponent(file);
			Str += "<a href='../AnnexServlet?url="+url1+"'>"+namearr[name]+"</a><br/>";
		}
		return Str;
	}
    </script>
</head>
<body>
	<div class="list-body">
		<div id="list" class="list-con"></div>
	</div>
</body>
</html>