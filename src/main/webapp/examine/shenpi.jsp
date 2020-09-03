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
    <title>${dateks.name}的${dateks.title}</title>
    <link href='css/bootstrap.min.css' rel="stylesheet"/>
    <link href='css/jilu.css' rel="stylesheet"/>
    <link href='css/reset.css' rel="stylesheet"/>
    <script src='js/jquery-3.0.0.js'></script>
    <script src='js/bootstrap.min.js'></script>
    <style type="text/css">
        .tr {
            width: 100%;
            font-size: 17px;
        }

        html, body {
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
            background-color: #F0EFF4;
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
            height: 46px;
            width: 80%;
            margin: auto;
            margin-top: -43px;
            color: #999;
            font-size: 11px;
            padding-bottom: 8px;
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
        $(function () {
        	
        	//divID 这个div加载的时候去判断  是否有这个辅助
        	$(document).ready(function(){
        	    $("#showfl").show(function(){
        	    	fltf();
        	    });
        	});
            $("#jilu").hide();
            $("#hidejilu").hide();
            //33获得下一审批人
            $('#ty')
                .click(
                    function () {
                        loading();
                        
                        $.ajax({
                            type: "POST",
                            //contentType: "application/json;charset=utf-8",   //内容类型
                            contentType: "application/x-www-form-urlencoded",
                            url: $("input[name='serverurl']")
                                .val()
                            + "weixinInf",
                            data: {
                                tip: "33",
                                dbid: $("input[name='dbid']")
                                    .val(),
                                state: "1",
                                state1: $("input[name='state1']")
                                    .val(),
                                tbname: $(
                                    "input[name='tablename']")
                                    .val(),
                                usrcode: $("input[name='spname']")
                                    .val(),
                                scm: $("input[name='scm']").val(),
                                orgcode: $(
                                    "input[name='department']")
                                    .val(),
                                sid: $("input[name='documentsid']")
                                    .val(),
                                sbuid: $("input[name='sbuId']")
                                    .val(),
                                spname: $("input[name='spname']")
                                    .val(),
                                smake: $("input[name='smake']")
                                    .val()
                            },
                            dataType: "JSON",
                            success: function (data) {
                                removeloading();
                                var trstr = "";
                                for (var i = 0; i < data.nextusers.length; i++) {
                                	var item = data.nextusers[i];
                                    if (item.state == 6) {
                                        document.getElementById("state2").value = item.state;
                                        ty();
                                    } else if (item.state == -1) {
                                        alert("未定义下一审批人！");
                                    } else {
                                        if(data.nextusers.length==1){
                                            var usrcode = "'"
                                                    + item.usrcode
                                                    + "'";
                                            xuanName(item.state,item.usrcode);
                                            trstr += '<tr  style="width:100%;text-align: center;font-size:20px; height:40px"><td><input type="radio" name="nextName" checked value="">'
                                                    + item.name
                                                    + '</td></tr>';
                                        }else {
                                            var usrcode = "'"
                                                    + item.usrcode
                                                    + "'";
                                            trstr += '<tr  style="width:100%;text-align: center;font-size:20px; height:40px"><td><input type="radio" name="nextName" onclick="javascript:xuanName('
                                                    + item.state
                                                    + ","
                                                    + usrcode
                                                    + ')" value="">'
                                                    + item.name
                                                    + '</td></tr>';
                                        }
                                    }
                                }
                                trstr += '<tr  style="width:100%;text-align: center;font-size:20px; height:40px"><td><a onclick="javascript:ty()" class="sel_btn">确定</a></td> </tr>';
                                if (data.nextusers[0].state == 6) {
                                } else if (data.nextusers[0].state == -1) {
                                } else {
                                    trstr += '<tr  style="width:100%;text-align: center;font-size:20px; height:40px"><td>   　　</td> </tr>';
                                    document
                                        .getElementById('new_table').innerHTML = trstr;
                                    document
                                        .getElementsByTagName('BODY')[0].scrollTop = document
                                        .getElementsByTagName('BODY')[0].scrollHeight;
                                }
                            },
                            error: function (err) {
                                removeloading();
                                alert("系统错误，请联系系统管理员！获取下级审批人错误：weixinInf" + $("input[name='serverurl']").val());
                            }
                        });
                    });
        });
        function loading(){
            var body = $("body");
            var str = "<div class='det_anibox'>";
            str += "<div class='spinner det_lod_ani'>";
            str += "<div class='rect1'></div>";
            str += "<div class='rect2'></div>";
            str += "<div class='rect3'></div>";
            str += "<div class='rect4'></div>";
            str += "<div class='rect5'></div>";
            str += "</div>";
            str += "</div>";
            body.prepend(str);
        }

        function removeloading(){
            var body = $("body");
            if(body.find(".det_anibox").is(":visible")){
                body.find(".det_anibox").remove();
            }
        }
        function ty() {
            loading();
            if ($("input[name='state2']").val() == '') {
                alert("请选择下一审批人");
                return;
            }
            document.getElementById("results").value = "1";
            getId("1");
        }
        function bh() {
            loading();
            document.getElementById("results").value = "2";
            if ($("textarea[name='yjcontent']").val() == '') {
//			document.getElementById("yjcontent").value = "驳回";
            }
            getId("2");
        }

        function xuanName(state, usercode) {
            document.getElementById("state2").value = state;
            document.getElementById("spname2").value = usercode;
        }

        //1.查询此条数据 是否被审批过
        function getId(state) {
            $.ajax({
                type: "POST",
                url: "SelectOneMessage",
                data: {
                    id: $("input[name='id']").val()
                },
                dataType: "JSON",
                success: function (data) {
                    if (data.success == 'ok') {
                        submit(state);
                    } else {
                        removeloading();
                        alert("此消息已被 其他审批人审批！");
                    }
                },
                error: function(jqXHR, textStatus, errorThrown) {
                    removeloading();
                    alert("网络超时，请稍后重试");
                }
            });
        }
        //2.没有被其他人审批过   进行审批并其他人的消息记录删除
        function sp(state) {
            $.ajax({
                type: "POST",
                url: "SpServlet",
                data: {
                    yjcontent: $("textarea[name='yjcontent']").val(),
                    id: $("input[name='id']").val(),
                    results: state,
                    documentsid: $("input[name='documentsid']").val(),
                    spname: $("input[name='spname']").val(),
                    w_corpid: $("input[name='w_corpid']").val(),
                    state0: $("input[name='state0']").val(),
                    state1: $("input[name='state1']").val(),
                },
                dataType: "JSON",
                success: function (data) {
                    if (data.success == 'ok') {
                    	alert("审批成功！");
                        window.location.href = 'ButtonServlet?state=0';
                    } else {
                    	alert("审批失败！");
                    	window.location.href = 'ButtonServlet?state=0';
                    }
                },
                error: function (err) {
                    removeloading();
                    alert("网络超时，请稍后重试");
                }
            });
        }
        var send = 0;
        //3.34提交或者退回
        function submit(state) {
            $.ajax({
                type: "POST",
                contentType: "application/x-www-form-urlencoded",
                url: $("input[name='serverurl']").val() + "weixinInf",
                data: {
                    tip: "34",
                    dbid: $("input[name='dbid']").val(),
                    content: encodeURI($("input[name='content']").val(), "UTF-8"),
                    dbid: $("input[name='dbid']").val(),
                    department: $("input[name='department']").val(),
                    documentsid: $("input[name='documentsid']").val(),
                    documentstype: $("input[name='documentstype']").val(),
                    gs: $("input[name='scm']").val(),
                    name: $("input[name='spweixinid']").val(),
                    sbuid: $("input[name='sbuId']").val(),
                    scm: $("input[name='scm']").val(),
                    spname: $("input[name='spname']").val(),
                    spname2: $("input[name='spname2']").val(),
                    spweixinid: $("input[name='spweixinid']").val(),
                    state: state,
                    state0: $("input[name='state0']").val(),
                    state1: $("input[name='state1']").val(),
                    state2: $("input[name='state2']").val(),
                    tablename: $("input[name='tablename']").val(),
                    wapno: $("input[name='wapno']").val(),
                    appid: $("input[name='appid']").val(),
                    w_corpid: $("input[name='w_corpid']").val(),
                    title: encodeURI($("input[name='title']").val(), "UTF-8"),
                    yjcontent: encodeURI($("textarea[name='yjcontent']").val(), "UTF-8"),
                    smake: $("input[name='smake']").val(),
                },
                dataType: "JSON",
                success: function (data) {
                    if (data.success == 'ok') {
                        sp(state);
                    } else {
                    	alert("审批失败！");
                    }
                },
                error: function (err) {
                    removeloading();
                    alert("网络超时，请稍后重试");
                }
            });
        }

        function showjilu() {
            $("#jilu").show();
            $("#hidejilu").show();
            $("#showjilu").hide();
            $("#showfl").hide();
        }
        function hidejilu() {
            $("#showjilu").show();
            $("#showfl").show(function(){
		fltf();
		});
            $("#hidejilu").hide();
            $("#jilu").hide();
        }
        function showfl() {
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
	<input type="hidden" value="" name="flname" id="flname"/>
	<input type="hidden" value="${data.documentstype}" name="documentstype">
    <input type="hidden" value="${data.smake}" name="smake"/>
    <input type="hidden" value="${data.title}" name="title"/> 
    <input type="hidden" value="${data.content}" name="content"/> 
    <input type="hidden" value="${data.dbid}" name="dbid"/> 
    <input type="hidden" value="${data.department}" name="department"> 
    <input type="hidden" value="${data.documentsid}" name="documentsid"/>
    <input type="hidden" value="${gs}"  name="gs"/> 
    <input type="hidden" value="${data.name}" name="name"/>
    <input type="hidden" value="${data.spweixinid}" name="spweixinid"/>
    <input type="hidden" value="${data.sbuId}" name="sbuId"/> 
    <input type="hidden" value="${data.scm}" name="scm"/> 
    <input type="hidden" value="${data.spname}" name="spname"/> 
    <input type="hidden" value="${data.spweixinid}" name="spweixinid"/> 
    <input type="hidden" value="${data.state}" name="state"/> 
    <input type="hidden" value="${data.state0}" name="state0"/> 
    <input type="hidden" value="${data.state1}" name="state1"/> 
    <input type="hidden" value="${data.tablename}" name="tablename"/> 
    <input type="hidden" value="${data.id}" name="id"/> 
    <input type="hidden" value="${data.serverurl}" name="serverurl"/> 
    <input id="date" name="date" type="hidden"/> 
    <input id="results" name="results" type="hidden"/> 
    <input id="state2" name="state2" type="hidden"/>
    <input id="spname2" name="spname2" type="hidden"/>
    <input type="hidden" value="${usercode}" id="userCode" name="userCode"/>
    <c:if test="${_right==1}">
        <img class="image4" src="img/wximg/jg-ty.png"/>
    </c:if>
    <c:if test="${_right==2}">
        <img class="image4" src="img/wximg/jt-bh.png"/>
    </c:if>
    <div>
        <table style="background-color:white;width:100%;margin-top:15px;">
            <tr style="font-size:15px;color:#999; height:36px;width:100%;">
                <td>
                    <div style="float:left">
                        <img class="image5" src="${dateks.tuUrl}"></img>
                    </div>
                    <div style="float:left;"><!-- 制单人:${data.smake} ${dateks.name}的${dateks.title} -->
                        <span style="font-size: 12px">&nbsp;&nbsp;提交人:${dateks.name}</span><br/>&nbsp;&nbsp;
                        <span style="color:#8FB95C;font-size: 11px">
							<c:if test="${_right==1}">
	                            	已完成
	                        </c:if>
			 				<c:if test="${_right==2}">
	                          		已驳回
	                        </c:if>
			 				<c:if test="${_right==0}">
	                          		 待审
	                        </c:if>
					    </span>
					</div>
                </td>
            </tr>
            <tr>
                <td>
                    <hr style="height:1px;border:none;border-top:1px solid #ddd;margin-top:10px;padding:0px"/>
                </td>
            </tr>
            <tr class="tr" style="color:#999;margin: 0px;padding:0px;">
                <td valign="top">
                    <p>${data.contenthuanhang}
                        <c:forEach var="fujian" items="${fujians}" varStatus="status">
                        	附件${status.index+1}:<a onclick="javascript:file('${fujian.fullPath}')">${fujian.fileName}</a><br/> 
                       <!-- 附件${status.index+1}:<a href='${fujian.fullPath}'>${fujian.fileName}</a><br/>-->
                        </c:forEach>
                    </p>
                    <%--<p style="font-size:14px;">${data.contenthuanhang}</p>--%>
                </td>
            </tr>
            <tr class="tr" style="color:#999;font-size:14px;">
                <td valign="top">提交时间:<fmt:formatDate value="${data.tjtime}"
                                                      pattern="yyyy-MM-dd HH:mm:ss"/></td>
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
            <img class="image2" src="img/wximg/ty.png"/>
            <div class="div3">
                <div style="float:left">
                    <img class="image1" src="${dateks.tuUrl}"></img>
                </div>
                <div style="float:left;">&nbsp;&nbsp;${dateks.name}<br/>&nbsp;&nbsp;<span
                        style="color:#8FB95C;font-size: 10px">发起申请</span></div>
                <div class="div4">
						<span style="font-size:8px">
						<fmt:formatDate value="${dateks.tjtime}" pattern="yyyy.MM.dd HH:mm"/>
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
                    <img class="image2" src="img/wximg/ds.png"/>
                </c:if>
                <c:if test="${list.state==1}">
                    <img class="image2" src="img/wximg/ty.png"/>
                </c:if>
                <c:if test="${list.state==2}">
                    <img class="image2" src="img/wximg/bh.png"/>
                </c:if>
                <div class="div3">
                    <div style="float:left">
                        <img class="image1" src="${list.tuUrl}"/></img>
                    </div>
                    <div style="float:left;">&nbsp;&nbsp;${list.spname}<br/>&nbsp;&nbsp;<span
                            style="color:#8FB95C;font-size: 10px">
					<c:if test="${list.yjcontent!=''&&list.yjcontent!='null'}">${list.yjcontent}</c:if>
							<c:if test="${list.yjcontent==''||list.yjcontent=='null'}">
                                <c:if test="${list.state==1}">同意</c:if>
                                <c:if test="${list.state==2}">驳回</c:if>
                                <c:if test="${list.state==0}">待审</c:if>
                            </c:if>
							</span></div>
                    <div class="div4">
						<span style="font-size:8px">
						<fmt:formatDate value="${list.sptime}" pattern="yyyy.MM.dd HH:mm"/>
						</span>
                    </div>
                    <div class="div4">
                        <c:if test="${list.state==0}">
                            <img class="image3" src="img/wximg/ds1.png"/>
                        </c:if>
                        <c:if test="${list.state==1}">
                            <img class="image3" src="img/wximg/ty1.png"/>
                        </c:if>
                        <c:if test="${list.state==2}">
                            <img class="image3" src="img/wximg/bh1.png"/>
                        </c:if>
                    </div>

                </div>
            </div>
        </c:forEach>
    </div>

    <table style="width:100%;font-size:13px;">
        <c:if test="${data.state !=0}">
            <tr class="tr">
                <td>审批意见:<textarea disabled="disabled" style="width: 100%;"
                                   name="yjcontent" rows="5"
                                   placeholder="审批意见:">${data.yjcontent eq 'null'?"":data.yjcontent}</textarea></td>
            </tr>
            <tr class="tr">
                <td style="width:100%;">审核时间:<fmt:formatDate
                        value="${data.sptime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                </td>
            </tr>
            <tr class="tr">
                <td style="width:100%;"><c:if test="${data.state ==1}">审批完成</c:if>
                    <c:if test="${data.state ==2}">已驳回！</c:if></td>
            </tr>
        </c:if>
        <c:if test="${data.state ==0}">
            <tr class="tr" style="text-align: center;">
                <td><textarea id="yjcontent" style="width: 100%;"
                              name="yjcontent" rows="5" placeholder="审批意见:"></textarea></td>
            </tr>
            <tr style="height:10px;"></tr>
            <tr class="tr" style="text-align: center;">
                <td style="width:100%;text-align:center;"><a id="ty"
                                                             class="sel_btn">同意</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a
                        class="sel_btn" onclick="javascript:bh();">驳回</a></td>
            </tr>
            <tr style="height:30px;"></tr>
        </c:if>
    </table>
</div>
<table id="new_table" style="width:100%;height:100%"></table>
</body>
</html>