<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/top/top_main_1.jsp"%>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/permission_set.css">
<script type="text/javascript">
	var userData = JSON.parse('${userTreeData}').userData;
	layui.config({
		base : '${pageContext.request.contextPath}/js/modules/'
	}).use('perSet');
</script>
</head>

<body class="page-main">
	<div class="bread-ceil">
		<span class="layui-breadcrumb"> <a
			href="javascript:void(0);" onclick="parent.ceilToIndex()">首页</a> <a><cite>系统设置</cite></a>
			<a><cite>用户权限设置</cite></a>
		</span>
		<div class="bread-ceil-footer"></div>
	</div>
	<div class="content-main">
		<div style="display: inline-block; width: 27%; padding-right: 15px;">
			<ul id="userTree"></ul>
		</div>
		<div style="display: inline-block; width: 70%; vertical-align: top;">
			<blockquote id="perSetExplain" class="layui-elem-quote">选择用户，对其进行权限的分配设置</blockquote>
			<div id="user-group-permission-tab" class="layui-tab"
				style="display: none;">
				<ul class="layui-tab-title">
					<li class="layui-this">为用户组分配权限</li>
				</ul>
				<div class="layui-tab-content">
					<div class="layui-tab-item layui-show">
						<form class="layui-form" id="groupPerSetForm" action="changeGroupOrUserPer.action">
							<div class="formSubmitValues">
								<div class="formUserValues"></div>
								<div class="formPerValues"></div>
							</div>
							<div class="perPSetArea"></div>
							<div class="layui-form-item"
								style="text-align: center; margin-top: 10px;">
								<div class="layui-input-block" style="margin-left: 0px;">
									<button class="layui-btn" lay-submit lay-filter="groupFormSubmit">立即提交</button>
									<button type="reset" class="layui-btn layui-btn-primary">重置</button>
								</div>
							</div>
						</form>
					</div>
				</div>
			</div>
			<div id="user-permission-tab" class="layui-tab"
				style="display: none;">
				<ul class="layui-tab-title">
					<li class="layui-this">为用户分配权限</li>
				</ul>
				<div class="layui-tab-content">
					<div class="layui-tab-item layui-show">
						<form class="layui-form" id="userPerSetForm" action="changeGroupOrUserPer.action">
							<div class="formSubmitValues">
								<div class="formUserValues"></div>
								<div class="formPerValues"></div>
							</div>
							<div class="perPSetArea"></div>
							<div class="layui-form-item"
								style="text-align: center; margin-top: 10px;">
								<div class="layui-input-block" style="margin-left: 0px;">
									<button class="layui-btn" lay-submit lay-filter="userFormSubmit">立即提交</button>
									<button type="reset" class="layui-btn layui-btn-primary">重置</button>
								</div>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
