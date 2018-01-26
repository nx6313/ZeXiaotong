<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/top/top_main_1.jsp"%>
<script type="text/javascript">
	layui.config({
		base : '${pageContext.request.contextPath}/js/modules/'
	}).use('userAdd');
</script>
</head>

<body class="page-pop">
	<form id="form" class="layui-form layui-form-pane"
		action="doUserAdd.action">
		<input type="hidden" name="uInfo.type" value="${addUserType }" />
		<div class="layui-form-item">
			<label class="layui-form-label">用户名</label>
			<div class="layui-input-block">
				<input type="text" name="uInfo.userName" required
					lay-verify="userNamePass" placeholder="请输入您的昵称（可用于登录）"
					autocomplete="off" class="layui-input" />
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">手机号</label>
			<div class="layui-input-block">
				<input type="text" name="uInfo.mobile" required
					lay-verify="userMobilePass" placeholder="请输入您的手机号（可用于登录）"
					autocomplete="off" class="layui-input" />
			</div>
		</div>
		<div class="layui-form-item">
			<div class="layui-input-block">
				<button class="layui-btn" lay-submit="" lay-filter="userAddForm">立即提交</button>
				<button type="reset" class="layui-btn layui-btn-primary">重置</button>
			</div>
		</div>
	</form>
</body>
</html>
