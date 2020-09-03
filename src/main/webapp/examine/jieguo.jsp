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
<meta name="viewport"
	content="width=device-width,initial-scale=1,user-scalable=0">
<meta http-equiv="Cache-Control"
	content="no-cache, no-store, must-revalidate" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Expires" content="0" />
<title>提示</title>
<link rel="stylesheet" href="css/weui.css" />
<link rel="stylesheet" href="css/example.css" />
<script type="text/javascript">
	function closeie() {
		WeixinJSBridge.call('closeWindow');
	}
</script>
</head>
<body>
	<div class="page msg_warn js_show">
		<div class="weui-msg">
			<div class="weui-msg__icon-area">
				<c:if test="${jg==0}">
					<h2 class="weui-msg__title">
						<i class="weui-icon-success weui-icon_msg"></i>
					</h2>
				</c:if>
				<c:if test="${jg==1}">
					<h2 class="weui-msg__title">
						<i class="weui-icon-warn weui-icon_msg"></i>
					</h2>
				</c:if>
				<c:if test="${jg==2}">
					<h2 class="weui-msg__title">
						<i class="weui-icon-warn weui-icon_msg"></i>
					</h2>
				</c:if>
			</div>
			<div class="weui-msg__text-area">
				<c:if test="${jg==0}">
					<h2 class="weui-msg__title">操作成功！</h2>
				</c:if>
				<c:if test="${jg==1}">
					<h2 class="weui-msg__title">操作失败！</h2>
				</c:if>
				<c:if test="${jg==2}">
					<h2 class="weui-msg__title">对不起，您无权访问此条信息！</h2>
				</c:if>
			</div>
			<div class="weui-msg__opr-area">
				<p class="weui-btn-area">
					<c:if test="${jg==0}">
						<a onclick="window.location.href ='https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx33a1a7296219a4fb&redirect_uri=http%3a%2f%2fanjili.bip-soft.com%2fweixinweb%2fButtonServlet%3fstate%3d0%26wxscmid%3dwx33a1a7296219a4fb&response_type=code&scope=snsapi_base#wechat_redirect'" class="weui-btn weui-btn_primary">完成</a>
					</c:if>
					<c:if test="${jg==1}">
						<a href="javascript:closeie();" class="weui-btn weui-btn_primary">返回</a>
					</c:if>
					<c:if test="${jg==2}">
						<a href="javascript:closeie();" class="weui-btn weui-btn_primary">返回</a>
					</c:if>
				</p>
			</div>
			<div class="weui-msg__extra-area">
				<div class="weui-footer">
					<p class="weui-footer__links">
						<a href="javascript:void(0);" class="weui-footer__link"></a>
					</p>
					<p class="weui-footer__text">BIP-2016</p>
				</div>
			</div>
		</div>
	</div>
</body>
</html>