<!DOCTYPE html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ page import="mobile.entity.Message,java.lang.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fun" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=yes">
    <meta http-equiv="Pragma" content="no-cache"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta http-equiv="Expires" content="0"/>
    <title>公告列表</title>
    <link href='css/basics.css' rel="stylesheet"/>
    <link href='css/Audit_center20170306.css' rel="stylesheet"/>
    <script src='js/jquery-2.1.4.js'></script>
    <script src='js/Audit_center20170306.js'></script>
    <script src="js/iscroll.js"></script>
    <script src="js/pullToRefresh.js"></script>
     <style type="text/css">
    	body {
			background-color:#EBEBEB;
		}
    </style>
</head>
<script type="text/javascript">
    var loading = false;  //状态标记
    var headPage = 0;
    $(function(){
        loading = true;
        $.ajax({
            url:"PageOaggtzServlet",
            type:"post",
//            async:false,
            data:{
                pageType: "yidu",
                offset: headPage
            },dataType: "json",
            success: function (res) {
                addItemsByFirst(res);
                loading = false;
            },
            error: function (error) {
                loading = false;
            }
        });
    });
    function addItemsByFirst(data) {
        var sateurl = "&read=1"; //详情url 拼接串
        var xqurl = $("#xqurl").val(); //详情url 拼接串
        var dataLength = data.length;
        var html = '';
        if (data && dataLength > 0) {
            for (var i = 0; i < dataLength; i++) {
                if (i == 0) {
                    $("#offset").val(data[i].offset);
                }
                html = html + ['<li><div style="background-color:#FFFFFF;color:#000000;width:88%;margin-left:5%;padding-left:10px;padding-top:14px;padding-bottom:16px;margin-bottom:25px">'+
           						'<a href="' + xqurl + data[i].id + sateurl  + '">'+
           	                    '<div>'+
								'<div style="color:#000000;font-size:20px">' + data[i].title + '</div>'+
           	                    '<div style="color:#888888;padding-top:4px;">'+ChangeDateFormat(data[i].mkdate)+'</div>'+
           	                    '<div style="color:#000000;padding-top:4px;">' + data[i].content.slice(0,15) + '......</div>'+
         	                    '<div style="margin-bottom:9px;color:#000000;font-size:16px;padding-top:4px;">发布人:' + data[i].smaker + '<br/>　</div>'+
           	                    '</div>'+
           	                    '<div style="font-size:14px;color:#000000;border-top: 1px dashed  #888888;width:98%;"><br/>&nbsp;查看详情<span style="margin-left:70%">></span></div>'+
           	                    '</a> '+
                                '</div>'+
                            	'</li>'].join("");
            }
            $("#audit_list_container").append($(html));
            refresher.init({
                id:"audit_list",
                pullDownAction:Refresh,
                pullUpAction:Load
            });
        } else {
            loading = false;
            $("#audit_list_container").append("<div style='text-align: center;'>暂无数据</div>");
        }
        return dataLength;
    }
    function addItems(data) {
        var sateurl = "&read=1"; //详情url 拼接串
        var xqurl = $("#xqurl").val(); //详情url 拼接串
        var dataLength = data.length;
        var html = '';
        if (data && dataLength > 0) {
            for (var i = 0; i < dataLength; i++) {
                if (i == 0) {
                    $("#offset").val(data[i].offset);
                }
                html = html + ['<li><div style="background-color:#FFFFFF;color:#000000;width:88%;margin-left:5%;padding-left:10px;padding-top:14px;padding-bottom:16px;margin-bottom:25px">'+
           						'<a href="' + xqurl + data[i].id + sateurl + '">'+
           	                    '<div>'+
								'<div style="color:#000000;font-size:20px">' + data[i].title + '</div>'+
           	                    '<div style="color:#888888;padding-top:4px;">'+ChangeDateFormat(data[i].mkdate)+'</div>'+
           	                    '<div style="color:#000000;padding-top:4px;">' + data[i].content.slice(0,15) + '......</div>'+
         	                    '<div style="margin-bottom:9px;color:#000000;font-size:16px;padding-top:4px;">发布人:' + data[i].smaker + '<br/>　</div>'+
           	                    '</div>'+
           	                    '<div style="font-size:14px;color:#000000;border-top: 1px dashed  #888888;width:98%;"><br/>&nbsp;查看详情<span style="margin-left:70%">></span></div>'+
           	                    '</a> '+
                                '</div>'+
                            	'</li>'].join("");
            }
            $("#audit_list_container").append($(html));
            /**refresher.init({
                id:"audit_list",
                pullDownAction:Refresh,
                pullUpAction:Load
            });**/
        } else {
        }
        return dataLength;
    }
    function ChangeDateFormat(d) {
	  	//将时间戳转为int类型，构造Date类型（这里存疑:时间戳这一大串数字是表示的毫秒数吗？）
	  	var date = new Date(parseInt(d.time, 10));
	    //月份得+1，且只有个位数时在前面+0
		var month= date.getMonth() + 1 < 10 ?"0" +(date.getMonth() + 1) : date.getMonth() + 1;
		//日期为个位数时在前面+0
		var currentDate= date.getDate() < 10 ?"0" +date.getDate() : date.getDate();
		
	    //getFullYear得到4位数的年份 ，返回一串字符串
	    return date.getFullYear() +"-" +month +"-" +currentDate;
    }

</script>
<body>
<input id="basePath" type="hidden" value="<%=basePath%>">
<input id="path" type="hidden" value="<%=path%>">
<input id="offset" name="offset" type="hidden" value="${offset}">
<input id="pageType" name="pageType" type="hidden" value="yidu">
<input id="xqurl" name="xqurl" type="hidden" value="${xqurl}">
<div class="audit_body">
    <!--内容盒子-->
    <div class="audit_content">
        <!--选项卡-->
        <ul class="audit_tab_control">
            <li><a href="${bu_url0}0" class="">未读</a></li>
            <li><a href="${bu_url0}1" class="audit_tabcon_click">已读</a></li>
            <li><a href="${bu_url0}2" class="">我发布的</a></li>
        </ul>
        <div id="audit_list" class="audit_list">
            <ul id="audit_list_container" class="audit_list_container" style="display: block;">
                <!--列表-->
            </ul>
        </div>
    </div>
</div>
<script>
    function Refresh() {
        setTimeout(function () {
            myScroll.refresh();
        }, 1000);
    }

    function Load() {
        if(loading) return
        loading = true;
        setTimeout(function () {
            var pageType = $("#pageType").val();
            var offset = $("#offset").val();
            $.ajax({
                url: "PageOaggtzServlet",
                type: "POST",
                data: {
                    pageType: pageType,
                    offset: offset
                },
                dataType: "json",
                beforeSend: function () {

                },
                success: function (res) {
                    addItems(res);
                    myScroll.refresh();
                    loading = false;
                },
                error: function (error) {
                    loading = false;
                }
            });
        }, 100);
    }
</script>
</body>
</html>