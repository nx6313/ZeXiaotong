<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/top/top_main_1.jsp"%>
<script type="text/javascript">
	layui.config({
		base : '${pageContext.request.contextPath}/js/modules/'
	}).use('studentCardActive');
</script>
</head>

<body class="page-pop">
	<form id="cardActiveForm" class="layui-form layui-form-pane"
		action="doStudentCardActive.action">
		<input type="hidden" name="activeCardId" value="${loginUserCardId }" />
		<div class="layui-form-item">
			<label class="layui-form-label">卡号</label>
			<div class="layui-input-block">
				<input type="text" disabled class="layui-input"
					value="${userCardNum }" />
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">修改密码</label>
			<div class="layui-input-block">
				<input type="text" name="studentCardIn.passWord" required
					lay-verify="studentCardActivePwdPass" placeholder="请修改您的登录密码"
					autocomplete="off" class="layui-input" />
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">昵称</label>
			<div class="layui-input-block">
				<input type="text" name="studentCardIn.userName" required
					lay-verify="studentCardActiveNamePass" placeholder="请输入您的昵称"
					autocomplete="off" class="layui-input" />
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">手机号绑定</label>
			<div class="layui-input-block">
				<input type="text" name="studentCardIn.phoneNum" required
					lay-verify="studentCardActivePhonePass" placeholder="可用于登录，或者密码找回"
					autocomplete="off" class="layui-input" />
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">文理科类型</label>
			<div class="layui-input-block">
				<select name="studentCardIn.familyType" required
					lay-verify="studentCardActiveXueKePass">
					<option value="">请选择文理科类型</option>
					<option value="1">文科</option>
					<option value="2">理科</option>
				</select>
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">地区</label>
			<div class="layui-input-block">
				<input type="text" name="studentCardIn.area" required
					lay-verify="studentCardActiveAreaPass" placeholder="您的所属地区"
					autocomplete="off" class="layui-input" />
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">学校</label>
			<div class="layui-input-block">
				<input type="text" name="studentCardIn.schoolName" required
					lay-verify="studentCardActiveSchoolPass" placeholder="您的学校"
					autocomplete="off" class="layui-input" />
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">班级</label>
			<div class="layui-input-block">
				<input type="text" name="studentCardIn.classGrade" required
					lay-verify="studentCardActiveClassPass" placeholder="您的班级"
					autocomplete="off" class="layui-input" />
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">邮箱</label>
			<div class="layui-input-block">
				<input type="text" name="studentCardIn.email"
					placeholder="可用于密码找回（选填）" autocomplete="off" class="layui-input" />
			</div>
		</div>
		<div class="layui-form-item">
			<div class="layui-input-block">
				<button class="layui-btn" lay-submit="" lay-filter="studentCardForm">确认激活卡片</button>
				<button type="reset" class="layui-btn layui-btn-primary">重置</button>
			</div>
		</div>
	</form>
</body>
</html>
