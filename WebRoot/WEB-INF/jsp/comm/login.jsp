<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/top/top_main_0.jsp"%>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/supersized.css" />
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/login.css" />
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/bootstrap.min.css" />

<style type="text/css">
.loginErr {
	font-weight: bold;
	background: #D76767;
	color: #FAFAFA;
	border: 1px solid #D14B4B;
	border-radius: 5px;
	box-shadow: 1px 2px 8px rgba(135, 33, 33, 0.8);
}
</style>
<script type="text/javascript">
	var loadIndex;
	$(function() {
		var options = {
			beforeSubmit : validate,
			success : function(data) {
				layer.close(loadIndex);
				if (data == "ajaxSuccess") {
					window.top.location = "skipIndex";
				} else {
					layer.msg('登录失败，请检查卡号、密码后重新登录', {
						anim : 6,
						time : 3000,
						shade : 0.5,
						shadeClose : true,
						skin : 'loginErr'
					});
				}
			}
		};
		$("#login_form").ajaxForm(options);
	});

	// 提交前表单验证
	function validate() {
		if (CheckUtil.isNull($("#idLogin").val())) {
			layer.close(loadIndex);
			$("#idLogin").focus();
			layer.tips("登录账号不能为空！", "#idLogin", {
				tips : [ 1, "#FF6868" ],
				time : 1600
			});
			return false;
		} else if (CheckUtil.isNull($("#idPassword").val())) {
			layer.close(loadIndex);
			$("#idPassword").focus();
			layer.tips("登录密码不能为空！", "#idPassword", {
				tips : [ 1, "#FF6868" ],
				time : 1600
			});
			return false;
		}
		return true;
	}

	// 触发表单提交事件
	function submitForm() {
		loadIndex = layer.load(1);
		$("#login_form").submit();
	}

	// 显示使用教程
	function showUseFun() {
		layer
				.open({
					type : 1,
					title : false,
					closeBtn : false,
					area : [ '700px', '600px' ],
					shade : 0.8,
					shadeClose : true,
					id : 'LAY_layuipro', //设定一个id，防止重复弹出
					resize : false,
					btn : [ '我明白了', '不是很明白' ],
					btnAlign : 'c',
					moveType : 1, //拖拽模式，0或者1
					content : '<div style="height: 100%; padding: 50px; line-height: 22px; background-color: #393D49; color: #fff; font-weight: 300;">内容<br>内容</div>',
					success : function(layero) {
						var btn = layero.find('.layui-layer-btn');
						btn.find('.layui-layer-btn1').attr({
							href : 'http://www.baidu.com/',
							target : '_blank'
						});
					}
				});
	}

	// 确定按钮绑定回车事件
	function bindEnter(obj) {
		//使用document.getElementById获取到按钮对象
		var sure_btn = $('#submit_btn');
		if (obj.keyCode == 13) {
			sure_btn.click();
			obj.returnValue = false;
		}
	}
</script>
</head>

<body onkeydown="bindEnter(event);">
	<input type="hidden" id="hasShownMsg" value="0" />

	<div class="page-container">
		<div class="main_box">
			<div class="login_box">
				<div class="login_logo">
					<img src="images/logo.png">
				</div>

				<div class="login_form">
					<s:form name="logonForm" id="login_form" action="login.action"
						method="post">
						<s:token />
						<div class="form-group">
							<label for="j_username" class="t">账 号：</label> <input
								id="idLogin" type="text" name="userID"
								placeholder="卡号 / 账号 / 手机号 / 邮箱" value="${rememberLoginName }"
								class="form-control x319 in" autocomplete="off">
						</div>
						<div class="form-group">
							<label for="j_password" class="t">密 码：</label> <input
								id="idPassword" name="password" value="zxt_Administrator" type="password"
								class="password form-control x319 in">
						</div>
						<div class="form-group">
							<label class="t"></label> <label for="j_remember" class="m">
								<c:if test="${rememberLoginName != null }">
									<input id="j_remember" name="remember" type="checkbox"
										checked="checked" />
								</c:if> <c:if test="${rememberLoginName == null }">
									<input id="j_remember" name="remember" type="checkbox" />
								</c:if>&nbsp;记住登陆账号
							</label>
						</div>
						<div class="form-group space">
							<button type="button" id="submit_btn" onclick="submitForm();"
								class="btn btn-primary btn-lg">&nbsp;登&nbsp;录&nbsp;</button>
							<input type="reset" value="&nbsp;重&nbsp;置&nbsp;"
								class="btn btn-default btn-lg">
						</div>
					</s:form>
				</div>
			</div>
			<div class="bottom">
				Copyright &copy; 2017 - 2020 <a href="javascript:void(0);"
					onclick="showUseFun();">使用教程</a>
			</div>
		</div>
	</div>

	<script
		src="${pageContext.request.contextPath}/js/supersized.3.2.7.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/supersized-init.js"></script>
</body>
</html>
