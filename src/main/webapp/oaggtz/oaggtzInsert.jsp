<!DOCTYPE html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="mobile.entity.Message,java.lang.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fun"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=yes">
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta http-equiv="Expires" content="0" />
<title>起草公告</title>
<style type="text/css">
ul {
	padding-left: 20px;
	padding-top: 2px;
	margin: 10px;
}

li {
	list-style-type: none;
	font-size: 17px;
	padding: 0px;
	margin: 0px;
	margin-top: 10px;
}

.liheight {
	height: 25px;
}

.fb {
	margin-top: 20px;
}

.sel_btn {
	height: 30px;
	width: 100px;
	line-height: 30px;
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
<script src='js/jquery-2.1.4.js'></script>
<script type="text/javascript">
	$(function() {
		$("#sbumit").click(function() {
			//标题
			var title=$("input[name='title']").val();
			//内容
			var content=$("textarea[name='content']").val();
			//提交数据
			var formData = new FormData($("#uploadForm")[0]);
			//附件
			var obj_file = document.getElementById("suri");
			if(title==""){
				alert("标题不能为空,请填写标题后进行发布!");
				return;
			}
			if(obj_file.value==""&&content==""){
				alert("内容和附件二者必须填一个,谢谢!");
				return;
			}
			if(obj_file.value!=""){
				if(obj_file.files[0].size>41943040){
					alert("附件文件大小不符合规定,请上传小于40M的文件。");
					return;
				}
			}
			$.ajax({
				url : 'WXInsertOaggtzServlet',
				type : 'POST',
				data : formData,
				async : false,
				cache : false,
				contentType : false,
				processData : false,
				success : function(returndata) {
					alert("成功");
				},
				error : function(returndata) {
					alert("失败");
				}
			});
		});
		$("#username").click(function() {
			var checkboxs = document.getElementsByName("username");
			for (var i = 0; i < checkboxs.length; i += 1) {
				checkboxs[i].checked = this.checked;
			}
		});
		$("#sorgto").click(function() {
			var checkboxs = document.getElementsByName("sorgto");
			for (var i = 0; i < checkboxs.length; i += 1) {
				checkboxs[i].checked = this.checked;
			}
		});
	});
</script>
</head>
<body>
	<form id="uploadForm">
		<input type="hidden" value="${scm}" name="scm" /> <input
			type="hidden" value="${userId}" name="UserId" />
		<ul id="top">
			<li class="liheight">标&nbsp;&nbsp;&nbsp;&nbsp;题&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;:&nbsp;<input
				name="title" type="text" style="width:196px;font-size: 17px;" /></li>
			<li class="liheight">信息归属&nbsp;:&nbsp;<select name="xxgs"
				style="width:200px;font-size: 17px;">
					<option value="0">人事</option>
					<option value="1">行政</option>
			</select>
			</li>
			<li class="liheight">信息类别&nbsp;:&nbsp;<select name="slb"
				style="width:200px;font-size: 17px;">
					<c:forEach var="name" items="${listO}">
						<option value="${name.sbm}"
							<c:if test="${name.sbm==404}">selected="selected"</c:if>>${name.smc}</option>
					</c:forEach>
			</select>
			</li>
			<li>内容&nbsp;:<textarea name="content"
					style="width:280px;height:150px;font-size: 17px;"></textarea></li>
			<!-- 
			<li style="display:block">
				<div>
					<input id="sorgto" type="checkbox" style="vertical-align:middle;" /><span
						style="vertical-align:middle;">所有部门</span>
				</div> 
				<c:forEach var="Department" items="${listD}">
					<div>
						<input type="checkbox" name="sorgto" value="${Department.orgcode}"
							style="vertical-align:middle;" /> <span
							style="width:70px;vertical-align:middle;">${Department.orgname}</span>
					</div>
				</c:forEach>
			</li>
			<li style="display:block">
				<div>
					<input id="username" type="checkbox" style="vertical-align:middle;" /><span
						style="vertical-align:middle;">所有人</span>
				</div> 
				<c:forEach var="user" items="${listU}">
					<div style="inline-block;float:left;width:80px;">
						<input type="checkbox" name="username" value="${user.userid}"
							style="vertical-align:middle;" /><span
							style="vertical-align:middle;">${user.username}</span>
					</div>
				</c:forEach>
			</li>
			 -->
			<li style="clear:both;padding-top:10px;">附&nbsp;&nbsp;&nbsp;&nbsp;件&nbsp;&nbsp;&nbsp;&nbsp;:
				<input id="suri" name="suri" type="file"></input>
			</li>
			<li class="fb">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<input id="sbumit" type="button" value="发布" class="sel_btn">
			</li>
		</ul>
	</form>
</body>
</html>