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
%>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=yes">
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta http-equiv="Expires" content="0" />
<title>员工列表</title>
<script src='js/jquery-3.0.0.js'></script>
<style type="text/css">
	td{width:100px;height:35px;}
</style>
<script type="text/javascript">
	function delet(userid) {
		if(!confirm("确认要删除？")){
			
		} else{
		$.ajax({
			type : "POST",
			url :	"DeleteUserServlet" ,
			data : {
				userid : userid,
				wxscmid:$("input[name='wxscmid']").val(),
			},
			dataType : "JSON",
			success : function(data) {
				if(data.zt==0){
					alert("删除成功！");
					location.assign(location) ;
				}
			},
			error : function(err) {
				alert("系统错误，请联系系统管理员！");
			}
		});
		}
	}
</script>
</head>
<body>
<table>
<input type="hidden" value="${wxscmid}" name="wxscmid" /> 
	<c:forEach var="list" items="${ul}">
		<tr>
		    <td><img src="${list.imgurl}" style="width:32px;"/></td>
			<td>${list.userid}</td>
			<td>${list.username}</td>
			<td>${list.tel}</td>
			<td>${list.email}</td>
			<td>
				<c:if test="${list.status==1}">已关注</c:if>
				<c:if test="${list.status==2}">已冻结</c:if>
				<c:if test="${list.status==4}"><span style="color:red">未关注</span></c:if>
			</td>
			<td><a onclick="javascript:delet('${list.userid}')">删除</a></td>
		</tr>
	</c:forEach>
</table>
</body>
</html>