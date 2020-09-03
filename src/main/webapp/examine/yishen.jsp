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
    <title>${title}</title>
    <link href='css/basics.css' rel="stylesheet"/>
    <link href='css/Audit_center20170306.css' rel="stylesheet"/>
    <script src='js/jquery-2.1.4.js'></script>
    <script src='js/Audit_center20170306.js'></script>
    <script src="js/iscroll.js"></script>
    <script src="js/pullToRefresh.js"></script>
</head>
<script type="text/javascript">
    var loading = false;  //状态标记
    var headPage = 0;
    $(function(){
        loading = true;
        $.ajax({
            url:"PageDataServlet",
            type:"post",
            data:{
                pageType: "yishen",
                offset: headPage
            },dataType: "json",
            success: function (res) {
                addItemsByFirst(res);
                loading = false;
            },
            error: function (error) {
                loading = false;
//                alert("error"+JSON.stringify(error));
            }
        })
    });
    function addItemsByFirst(data) {
        var sateurl = "&state=1"; //详情url 拼接串
        var xqurl = $("#xqurl").val(); //详情url 拼接串
        var url2 = $("#url2").val();  //详情url 拼接串
        var dataLength = data.length;
        var html = '';
        if (data && dataLength > 0) {
            for (var i = 0; i < dataLength; i++) {
                if (i == 0) {
                    $("#offset").val(data[i].offset);
                }
                var state = data[i].state;
                var stateStr;
                if (state == 0) {
                    stateStr = "待审批";
                }
                if (state == 1) {
                    stateStr = "审批通过";
                }
                if (state == 2) {
                    stateStr = "驳回";
                }
                html = html + ['<li><div class="audit_list_box"><img src="img/point.png"/>',
                            '<div class="audit_dashed_box">',
                            '<div class="audit_dsd_lbox">',
                            '<div>' + data[i].title + "  " + data[i].documentsid + '</div>',
                            '<div>' + data[i].tjtimeStr + '</div>',
                            '<div>' + stateStr + '</div>',
                            '<div>提交人:' + data[i].submit + '</div>',
                            '</div>',
                            '<div class="audit_dsd_rbox">',
                            '<a href="' + xqurl + data[i].id + sateurl + url2 + '" class="audit_btn">详情</a></div>',
                            '</div></div></li>'].join("");
            }
            refresher.init({
                id:"audit_list",
                pullDownAction:Refresh,
                pullUpAction:Load
            });
            $("#audit_list_container").append($(html));
        } else {
            $("#audit_list_container").append("<div style='text-align: center;'>暂无数据</div>");
        }
        return dataLength;
    }
    function addItems(data) {
        var sateurl = "&state=1"; //详情url 拼接串
        var xqurl = $("#xqurl").val(); //详情url 拼接串
        var url2 = $("#url2").val();  //详情url 拼接串
        var dataLength = data.length;
        var html = '';
        if (data && dataLength > 0) {
            for (var i = 0; i < dataLength; i++) {
                if (i == 0) {
                    $("#offset").val(data[i].offset);
                }
                var state = data[i].state;
                var stateStr;
                if (state == 0) {
                    stateStr = "待审批";
                }
                if (state == 1) {
                    stateStr = "审批通过";
                }
                if (state == 2) {
                    stateStr = "驳回";
                }
                html = html + ['<li><div class="audit_list_box"><img src="img/point.png"/>',
                        '<div class="audit_dashed_box">',
                        '<div class="audit_dsd_lbox">',
                        '<div>' + data[i].title + "  " + data[i].documentsid + '</div>',
                        '<div>' + data[i].tjtimeStr + '</div>',
                        '<div>' + stateStr + '</div>',
                        '<div>提交人:' + data[i].submit + '</div>',
                        '</div>',
                        '<div class="audit_dsd_rbox">',
                        '<a href="' + xqurl + data[i].id + sateurl + url2 + '" class="audit_btn">详情</a></div>',
                        '</div></div></li>'].join("");
            }
            refresher.init({
                id:"audit_list",
                pullDownAction:Refresh,
                pullUpAction:Load
            });
            $("#audit_list_container").append($(html));
        } else {
        }
        return dataLength;
    }
</script>
<body ontouchstart>
<input id="basePath" type="hidden" value="<%=basePath%>">
<input id="path" type="hidden" value="<%=path%>">
<input id="offset" name="offset" type="hidden" value="${offset}">
<input id="pageType" name="pageType" type="hidden" value="yishen">
<input id="url2" name="url2" type="hidden" value="${url2}">
<input id="xqurl" name="xqurl" type="hidden" value="${xqurl}">
<div class="audit_body">
    <!--内容盒子-->
    <div class="audit_content">
        <!--选项卡-->
        <ul class="audit_tab_control">
            <li><a href="${bu_url0}0${bu_url2}" class="">待审</a></li>
            <li><a href="${bu_url0}1${bu_url2}" class="audit_tabcon_click">已审</a>
            </li>
            <!--<li><a href="${bu_url0}2${bu_url2}" class="">驳回</a></li>-->
            <li><a href="${bu_url0}3${bu_url2}" class="">我发起的</a></li>
        </ul>
        <!--选项卡 END-->
        <!--列表盒子-->
        <div id="audit_list" class="audit_list">
            <!--待审-->
            <ul id="audit_list_container" class="audit_list_container" style="display: block;">
                <!--列表-->
                <%--<c:forEach var="list" items="${listM}">--%>
                    <%--<div class="audit_list_box">--%>
                        <%--<img src="img/point.png"/>--%>
                        <%--<div class="audit_dashed_box">--%>
                            <%--<div class="audit_dsd_lbox">--%>
                                <%--<div>${list.title}</div>--%>
                                <%--<div>--%>
                                        <%--${list.tjtimeStr}--%>
                                <%--</div>--%>
                                <%--<div>审核状态:--%>
                                    <%--<c:if test="${list.lastState eq 0}">--%>
                                        <%--待审批--%>
                                    <%--</c:if>--%>
                                    <%--<c:if test="${list.lastState eq 1}">--%>
                                        <%--审批通过--%>
                                    <%--</c:if>--%>
                                    <%--<c:if test="${list.lastState eq 2}">--%>
                                        <%--驳回--%>
                                    <%--</c:if>--%>
                                <%--</div>--%>
                                <%--<div>提交人:${list.submit}</div>--%>
                                <%--<!--<div>制单人编号:${list.smake}</div>-->--%>
                            <%--</div>--%>
                            <%--<div class="audit_dsd_rbox">--%>
                                <%--<a href="${xqurl}${list.id}${url2}" class="audit_btn"> 详情 </a>--%>
                            <%--</div>--%>
                        <%--</div>--%>
                    <%--</div>--%>
                <%--</c:forEach>--%>
            </ul>
            <!--待审 END-->
        </div>
        <!--列表盒子 END-->
    </div>
    <!--内容盒子 END-->
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
                url: "PageDataServlet",
                type: "POST",
                data: {
                    pageType: pageType,
                    offset: offset
                },
                dataType: "json",
                beforeSend: function () {

                },
                success: function (res) {
//                alert("res"+JSON.stringify(res));
                    addItems(res);
                    myScroll.refresh();
                    loading = false;
                },
                error: function (error) {
                    loading = false;
//                alert("error"+JSON.stringify(error));
                }
            });
        }, 100);
    }
</script>
</body>
</html>