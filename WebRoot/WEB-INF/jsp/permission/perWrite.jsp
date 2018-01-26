<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/top/top_main_1.jsp"%>
<script type="text/javascript">
	layui.config({
		base : '${pageContext.request.contextPath}/js/modules/'
	}).use('perWrite');
</script>
</head>

<body class="page-pop">
	<form id="form" class="layui-form layui-form-pane"
		action="doPermissionWrite.action">
		<input type="hidden" name="perPid" value="${perPId }" /> <input
			type="hidden" name="updatePerPid" value="${updatePerP.id }" /> <input
			type="hidden" name="updatePerCid" value="${updatePerC.id }" />
		<div class="layui-form-item" pane="">
			<label class="layui-form-label">权限级别</label>
			<div class="layui-input-block">
				<c:if test="${type == 1 || type == 3 }">
					<input type="radio" name="perGrade" value="p" title="父级权限" checked>
					<input type="radio" name="perGrade" value="c" title="子级权限" disabled>
				</c:if>
				<c:if test="${type == 2 || type == 4 }">
					<input type="radio" name="perGrade" value="p" title="父级权限" disabled>
					<input type="radio" name="perGrade" value="c" title="子级权限" checked>
				</c:if>
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">权限名</label>
			<div class="layui-input-block">
				<c:if test="${nameCanUpdateFlag == '0' }">
					<input type="text" name="perName" required lay-verify="perNamePass"
						placeholder="请输入权限名称（保持8个字之内）" autocomplete="off"
						class="layui-input" readonly="readonly"
						value="${updatePerP.permissionName }${updatePerC.permissionName }">
				</c:if>
				<c:if test="${nameCanUpdateFlag != '0' }">
					<input type="text" name="perName" required lay-verify="perNamePass"
						placeholder="请输入权限名称（保持8个字之内）" autocomplete="off"
						class="layui-input"
						value="${updatePerP.permissionName }${updatePerC.permissionName }">
				</c:if>
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">权限说明</label>
			<div class="layui-input-block">
				<input type="text" name="perIntro" required placeholder="请输入权限说明文字"
					autocomplete="off" class="layui-input"
					value="${updatePerP.permissionIntro }${updatePerC.permissionIntro }">
			</div>
		</div>
		<c:if test="${type == 2 || type == 4 }">
			<div class="layui-form-item">
				<label class="layui-form-label">权限链接</label>
				<div class="layui-input-block">
					<input type="text" name="perLink" required lay-verify="perLinkPass"
						placeholder="请输入该权限点击后跳转的链接地址（请确保链接可用）" autocomplete="off"
						class="layui-input" value="${updatePerC.permissionLink }">
				</div>
			</div>
		</c:if>
		<div class="layui-form-item" pane="">
			<label class="layui-form-label">是否可用</label>
			<div class="layui-input-block">
				<c:if test="${type == 1 || type == 2 }">
					<input type="checkbox" name="perStatus" lay-skin="switch"
						lay-text="可用|停用" checked>
				</c:if>
				<c:if test="${type == 3 || type == 4 }">
					<c:if
						test="${updatePerP.permissionStatus == 0 || updatePerC.permissionStatus == 0 }">
						<input type="checkbox" name="perStatus" lay-skin="switch"
							lay-text="可用|停用">
					</c:if>
					<c:if
						test="${updatePerP.permissionStatus == 1 || updatePerC.permissionStatus == 1 }">
						<input type="checkbox" name="perStatus" lay-skin="switch"
							lay-text="可用|停用" checked>
					</c:if>
				</c:if>
			</div>
		</div>
		<div class="layui-form-item">
			<div class="layui-input-block">
				<button class="layui-btn" lay-submit="" lay-filter="perWriteForm">立即提交</button>
				<button type="reset" class="layui-btn layui-btn-primary">重置</button>
			</div>
		</div>
	</form>
</body>
</html>
