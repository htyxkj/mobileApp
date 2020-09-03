<!DOCTYPE html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="mobile.entity.Message,java.lang.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html>
<head>
<base href="<%=basePath%>">
<meta charset="utf-8" />
<meta name="viewport"
	content="width=device-width, initial-scale=1, user-scalable=yes">
<meta http-equiv="Access-Control-Allow-Origin" content="*">
<meta content="telephone=no" name="format-detection" />
<title>我的${data.title}</title>
<link href='css/bootstrap.min.css' rel="stylesheet" />
<link href='css/jilu.css' rel="stylesheet" />
<script src='js/jquery-3.0.0.js'></script>
<script src='js/bootstrap.min.js'></script>
<style type="text/css">
.tr {
	width: 100%;
	font-size: 17px;
}

html,body {
	width: 98%;
	overflow: hidden;
	margin: 0 auto;
	padding: 0;
}

.image1 {
	margin-top: 0px;
	width: 32px;
	height: 32px;
	border-radius: 200px;
}

.image2 {
	margin-left: -13px;
	margin-top: 34px;
	width: 26px;
	height: 26px;
	border-radius: 200px;
}

.image3 {
	width: 50px;
	height: 34px;
	position: absolute;
	right: 30%;
}

.image4 {
	width: 86px;
	height: 68px;
	position: absolute;
	right: 10%;
	top: 20px;
}
.image5 {
	margin-top: 0px;
	width: 42px;
	height: 42px;
	border-radius: 200px;
}

.div1 {
	background-color: #eeeeee;
	width: 100%;
	height: 100%;
	margin-bottom: 20px;
}

.div2-1 {
	background-color:#F0EFF4;
	border-left: 3px solid #E6E5EA;
	margin-left: 20px;
	padding-bottom: 13px;
}

.div2-2 {
	background-color: #F0EFF4;
	border-left: 3px solid #E6E5EA;
	margin-left: 20px;
	padding-bottom: 13px;
}

.div2-3 {
	background-color: #F0EFF4;
	border-left: 3px solid #E6E5EA;
	margin-left: 20px;
	padding-bottom: 13px;
}

.div3 {
	background-color: #FFFFFF;
	height:46px;
	width: 80%;
	margin: auto;
	margin-top: -43px;
	color: #999;
	font-size: 11px;
	padding-bottom:8px;
	padding-top: 8px;
	padding-left: 8px;
}

.div4 {
	margin-top: -5px;
	float: right;
	height: 100%;
	margin-right: 7px;
}

.sel_btn {
	height: 40px;
	width: 130px;
	line-height: 40px;
	text-align: center;
	padding: 0 11px;
	background: #02bafa;
	border: 1px #26bbdb solid;
	border-radius: 3px;
	display: inline-block;
	text-decoration: none;
	font-size: 17px;
	outline: none;
	color: white;
}
</style>
<script type="text/javascript">
	$(function() {
		$("#jilu").hide();
		$("#hidejilu").hide();
    	$(document).ready(function(){
    	    $("#showfl").show(function(){
    	    	fltf();
    	    });
    	}); 
	}); 
	
	function showjilu() {
		$("#jilu").show();
		$("#hidejilu").show();
		$("#showjilu").hide();
		$("#showfl").hide();
	}
	function hidejilu() {
		$("#showjilu").show();
		$("#showfl").show();
		$("#hidejilu").hide();
		$("#jilu").hide();
	}
	function showfl(){
		var url= "examine/fenlu.jsp?usercode=" + $("input[name='userCode']").val() + "&documentstype=" + $("input[name='documentstype']").val() + "&documentsid=" + $("input[name='documentsid']").val() + "&serverurl=" + $("input[name='serverurl']").val()+"&flname=" + $("input[name='flname']").val()+"&dbid=" + $("input[name='dbid']").val();
        window.location.href=url;		  
	}
	function  fltf(){ 
    	$.ajax({
        	type : "GET",
			contentType : "application/x-www-form-urlencoded",
			url :$("input[name='serverurl']").val()+"lxvar",
			//url :"http://127.0.0.1:9999/jd/lxvar",
			//url:"http://localhost:9999/jd/lxver",
			data :  {"VARID":"sql:select count(csysno) a,slabel from insaid where sid='"+$("input[name='documentstype']").val()+"FL' GROUP BY slabel ","DBID":""+$("input[name='dbid']").val()+"","USRCODE":""+$("input[name='userCode']").val()+"","CONT":""+$("input[name='documentsid']").val()+""},
			dataType : "JSON",
			success : function(data) { 
				console.log(data);
				if(data.values==null){
					$("#showfl").hide();
					return;
				}
				var num=data.values[0][0];
				if(num==0){
					$("#showfl").hide();
				}
				$("#flname").val(data.values[0][1]);			
			},
			error : function(err) { 
				console.log(err);
				$("#showfl").hide();
				//alert("系统错误，请联系系统管理员！416行");
			}
		});
    }
    function file(url){ 
    	url = encodeURIComponent(url);
    	window.location.href="AnnexServlet?url="+url;
    }
</script>
</head>
<body>
		<div class="a2">
			<input type="hidden" value="${data.smake}" name="smake" /> 
		<input type="hidden" value="${data.title}" name="title" /> <input
			type="hidden" value="${data.content}" name="content" /> <input
			type="hidden" value="${data.dbid}" name="dbid"> <input
			type="hidden" value="${data.department}" name="department"> <input
			type="hidden" value="${data.documentsid}" name="documentsid">
		<input type="hidden" value="${data.documentstype}"
			name="documentstype"> <input type="hidden" value="${gs}"
			name="gs" /> <input type="hidden" value="${data.name}" name="name">
		<input type="hidden" value="${data.spweixinid}" name="spweixinid">
		<input type="hidden" value="${data.sbuId}" name="sbuId"> <input
			type="hidden" value="${data.scm}" name="scm"> <input
			type="hidden" value="${data.spname}" name="spname"> <input
			type="hidden" value="${data.spweixinid}" name="spweixinid" /> <input
			type="hidden" value="${data.state}" name="state" /> <input
			type="hidden" value="${data.state0}" name="state0" /> <input
			type="hidden" value="${data.state1}" name="state1"> <input
			type="hidden" value="${data.tablename}" name="tablename"> <input
			type="hidden" value="${data.id}" name="id">  <input
			type="hidden" value="${data.serverurl}" name="serverurl"> <input
			id="date" name="date" type="hidden" /> <input id="results"
			name="results" type="hidden" /> <input id="state2" name="state2"
			type="hidden" /> <input id="spname2" name="spname2" type="hidden" />
		 <input type="hidden" value="${usercode}" id="userCode" name="userCode" />
         <!--<c:if test="${_right==0}">
		 	<img class="image4" src="img/wximg/jt-bh.png" />
		 </c:if>
		 -->
         <c:if test="${_right==1}">
		 	<img class="image4" src="img/wximg/jg-ty.png" />
		 </c:if>
		 <c:if test="${_right==2}">
		 	<img class="image4" src="img/wximg/jt-bh.png" />
		 </c:if>
			<div>
				<table style="background-color:white;width:100%;margin-top:15px;">
				<tr style="font-size:15px;color:#999; height:36px;width:100%;">
					<td> 					
					<div style="float:left">
					<img class="image5" src="${dateks.tuUrl}" ></img>
					</div>
					<div style="float:left;">
						<span style="font-size: 12px">&nbsp;&nbsp;${dateks.name}</span><br/>&nbsp;&nbsp;<span style="color:#8FB95C;font-size: 11px">
					
						<c:if test="${_right==1}">
						已完成
		 				</c:if>
		 				<c:if test="${_right==2}">
		 				已驳回
		 				</c:if>
		 				<c:if test="${_right==0}">
		 				待审
		 				</c:if>
					 </span></div>
					</td>
				</tr>
				<tr>
					<td>
						<hr
							style="height:1px;border:none;border-top:1px solid #ddd;margin-top:10px;padding:0px" />
					</td>
				</tr>
				<tr class="tr" style="color:#999;margin: 0px;padding:0px;">
					<td valign="top">
						<p>${data.contenthuanhang}
							<c:forEach var="fujian" items="${fujians}" varStatus="status">
							<!-- 附件${status.index+1}:<a onclick="javascript:file('${fujian.fullPath}')">${fujian.fileName}</a><br/> -->
								附件${status.index+1}:<a href='${fujian.fullPath}'>${fujian.fileName}</a><br/>
							</c:forEach>
						</p>
					</td>
				</tr>
				<tr class="tr" style="color:#999;">
					<td valign="top">提交时间:<fmt:formatDate value="${data.tjtime}" pattern="yyyy.MM.dd HH:mm" /></td>
				</tr>
				<tr class="tr" style="color:#999;">
					<td valign="top"></td>
				</tr>
			</table>
			</div>
			<p style="color:#999;font-size:15px;">
				<c:if test="${fn:length(listM)>0}">
					<a id="showjilu" onclick="showjilu()">查看流程</a>
				</c:if>
				
				<a id="showfl" onclick="showfl();">查看详情</a>
				
				<a id="hidejilu" onclick="hidejilu()">收起</a>
			</p>
			<div id="jilu" class="div1">
			<div class="div2-1">
				<img class="image2" src="img/wximg/ty.png" />
				<div class="div3">
					<div style="float:left">
					<img class="image1" src="${dateks.tuUrl}" ></img>
					</div>
					<div style="float:left;">&nbsp;&nbsp;${dateks.name}<br/>&nbsp;&nbsp;<span style="color:#8FB95C;font-size: 10px">发起申请</span></div>
					<div class="div4">
						<span style="font-size:8px"> 
						<fmt:formatDate value="${dateks.tjtime}" pattern="yyyy.MM.dd HH:mm" />
						</span>
					</div>
				</div>
			</div>
			<c:forEach var="list" items="${listM}" varStatus="status">
				<div
					<c:if test="${list.state==0}">
							class="div2-2"
						</c:if>
					<c:if test="${list.state==1}">
							class="div2-1"
						</c:if>
					<c:if test="${list.state==2}">
							class="div2-3"
						</c:if>
					>
					<c:if test="${list.state==0}">
						<img class="image2" src="img/wximg/ds.png" />
					</c:if>
					<c:if test="${list.state==1}">
						<img class="image2" src="img/wximg/ty.png" />
					</c:if>
					<c:if test="${list.state==2}">
						<img class="image2" src="img/wximg/bh.png" />
					</c:if>
					<div class="div3">
					<div style="float:left">
					<img class="image1" src="${list.tuUrl}" /></img>
					</div>
					<div style="float:left;">&nbsp;&nbsp;${list.spname}<br/>&nbsp;&nbsp;<span style="color:#8FB95C;font-size: 10px">
					<c:if test="${list.yjcontent!=''&&list.yjcontent!='null'}">${list.yjcontent}</c:if>
							<c:if test="${list.yjcontent==''||list.yjcontent=='null'}">
								<c:if test="${list.state==1}">同意</c:if>
								<c:if test="${list.state==2}">驳回</c:if>
								<c:if test="${list.state==0}">待审</c:if>
							</c:if>
							</span></div>
					<div class="div4">
						<span style="font-size:8px"> 
						<fmt:formatDate value="${list.sptime}" pattern="yyyy.MM.dd HH:mm" />
						</span>
					</div>					
						<div class="div4">
							<c:if test="${list.state==0}">
								<img class="image3" src="img/wximg/ds1.png" />
							</c:if>
							<c:if test="${list.state==1}">
								<img class="image3" src="img/wximg/ty1.png" />
							</c:if>
							<c:if test="${list.state==2}">
								<img class="image3" src="img/wximg/bh1.png" />
							</c:if>
						</div>
					</div>
				</div>
			</c:forEach>
		</div>
			<!-- 
			<table style="width:100%;">
				<c:if test="${data.state !=0}">
					<tr class="tr">
						<td>审批意见:<textarea disabled="disabled" style="width: 100%;"
								name="yjcontent" rows="5" placeholder="审批意见:">${data.yjcontent}</textarea></td>
					</tr>
					<tr class="tr">
						<td style="width:100%;">审核时间:<fmt:formatDate
								value="${data.sptime}" pattern="yyyy/MM/dd" />
						</td>
					</tr>
					<tr class="tr">
						<td style="width:100%;"><c:if test="${data.state ==1}">审批完成</c:if>
							<c:if test="${data.state ==2}">已驳回！</c:if></td>
					</tr>
				</c:if>
			</table>-->
		</div>
		<table id="new_table" style="width:100%;height:100%"></table>
</body>
</html>