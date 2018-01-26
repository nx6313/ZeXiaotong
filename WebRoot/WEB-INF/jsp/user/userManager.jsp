<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/top/top_main_1.jsp"%>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/metroStyle/metroStyle.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/user_manager.css">
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery.ztree.all.min.js"></script>
<script type="text/javascript">
	var userData = JSON.parse('${userTreeData}').userData;
	layui.config({
		base : '${pageContext.request.contextPath}/js/modules/'
	}).use('userManager');
</script>
</head>

<body class="page-main">
	<div class="bread-ceil">
		<span class="layui-breadcrumb"> <a
			href="javascript:void(0);" onclick="parent.ceilToIndex()">首页</a> <a><cite>用户管理</cite></a>
			<a><cite>用户管理</cite></a>
		</span>
		<div class="bread-ceil-footer"></div>
	</div>
	<div class="content-main">
		<div style="display: inline-block; width: 27%; padding-right: 15px;">
			<ul id="userTree" class="ztree"></ul>
		</div>
		<div style="display: inline-block; width: 70%; vertical-align: top;">
			<blockquote id="userManagerExplain" class="layui-elem-quote">选择用户组，搜索用户；或者直接点击某用户查看修改其信息（拖拽某用户过来这里也可以哦
				٩(๑&gt;◡&lt;๑)۶ ）</blockquote>
			<div id="userManagerListWarp">
				<input id="selectTreeGroupId" type="hidden" value="" /> <input
					id="selectTreeGroupName" type="hidden" value="" />
				<fieldset class="layui-elem-field layui-field-title">
					<legend class="doAddClearLegWarp">
						<span class="layui-btn-group">
							<button class="layui-btn layui-btn-small"
								style="background: #1A85A0;"
								onclick="layui.userManager('addUser')">
								<i class="layui-icon">&#xe654;</i> 添加『<span
									id="userManagerAddBtnSpan"></span>』下的新用户
							</button>
							<button class="layui-btn layui-btn-small clearGroupUserBtn"
								style="background: #C0351E;"
								onclick="layui.userManager('clearUser')">
								<i class="layui-icon">&#xe62c;</i> 清除『<span
									id="userManagerClearBtnSpan"></span>』下的所有用户
							</button>
						</span>
					</legend>
					<div class="layui-field-box">
						<div style="position: relative;">
							<input type="text" name="title" lay-verify="title"
								id="userSearchInput" autocomplete="off"
								placeholder="输入用户名或电话进行搜索，或者拖拽用户到此处释放后进行搜索" class="layui-input">
							<button style="position: absolute; top: 0px; right: 0px;"
								class="layui-btn"
								onclick="layui.userManager('userSearch', this)">
								<i class="layui-icon">&#xe615;</i> 搜索
							</button>
						</div>
						<table class="layui-table">
							<colgroup>
								<col width="150">
								<col width="200">
								<col>
							</colgroup>
							<thead>
								<tr>
									<th>用户名（/ 卡号）</th>
									<th>用户类型</th>
									<th>签名</th>
								</tr>
							</thead>
							<tbody id="userManagerMainWarp"></tbody>
						</table>
						<div id="pageElement"></div>
					</div>
				</fieldset>
			</div>
			<div id="userManagerInfoWarp">
				<table class="layui-table">
					<colgroup>
						<col width="150">
						<col>
						<col width="150">
						<col>
					</colgroup>
					<tbody id="userManagerInfoContent"></tbody>
				</table>
			</div>
		</div>
	</div>
</body>
</html>
