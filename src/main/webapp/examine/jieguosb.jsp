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
<meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0">
<meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate" />
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
					<h2 class="weui-msg__title">
						<i class="weui-icon-warn weui-icon_msg"></i>
					</h2>
			</div>
			<div class="weui-msg__text-area">
					<h2 class="weui-msg__title">系统运行中出现错误</h2>
			</div>
			<div class="weui-msg__opr-area">
				<p class="weui-btn-area">
						<a href="javascript:closeie();" class="weui-btn weui-btn_primary">返回</a>
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