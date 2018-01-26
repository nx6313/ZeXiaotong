<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/top/top_main_0.jsp"%>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/common.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/style.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/index.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/suinav.css" />
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/index.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery.SuperSlide.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/index_time.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/index_menu.js"></script>
<script type="text/javascript">
	var superModelSelect = '${loginUserType }';

	var loginUserCardId = '${loginUserCardId }';
	var loginUserType = '${loginUserType }';
	var loginUserName = '${loginUserName }';
	var loginUserCardNum = '${loginUserCardNum }';
	var loginUserCardLevel = '${loginUserCardLevel }';
	var navigationJson = '${navigationJson}';

	var hasLimitsCount = '${hasLimitsCount}';
	var allLimitsCount = '${allLimitsCount}';
	showLoading();
	layui.config({
		base : '${pageContext.request.contextPath}/js/modules/'
	}).use('index');
</script>
</head>

<body>
	<div class="indexTop">
		<div class="pageHeader">
			<div class="top">
				<div class="topBtnItem" onclick="exitLogin();">安全退出登录</div>
				<c:if test="${superLoginFlag }">
					<div class="topSpanItem borR">
						当前为超级管理员权限登录，<span id="superChangeMode"></span>进入 <a
							href="javascript:void(0);" onclick="showSuperModelSelect();">超级管理员操作模式</a>
					</div>
				</c:if>
				<c:if
					test="${loginUserType == 10 && (loginUserName == '' || loginUserName == null) }">
					<div class="topSpanItem borR">您好，您的卡片尚未激活，请先激活后使用</div>
				</c:if>
				<c:if
					test="${loginUserType == 10 && loginUserName != '' && loginUserName != null }">
					<div class="topSpanItem borR">您好，${loginUserName }</div>
				</c:if>
				<c:if test="${loginUserType != 10 }">
					<div class="topSpanItem borR">您好，${loginUserName }</div>
				</c:if>
			</div>
			<div class="middle">
				<div class="logo">
					<img src="images/logo1.jpg" height="60" title="择校通"
						onclick="javascript:location.reload();">
				</div>
				<div class="daoJiTime">
					<input id="gaoKaoYear" type="hidden" value="${gkYear }" />
					<div class="daoJiLable">距 ${gkYear } 年高考剩：</div>
					<div id="gaoKaoTime">
						<img src="images/loading.gif" style="margin: 4px;" />
					</div>
				</div>
			</div>
			<script type="text/javascript"
				src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
			<script type="text/javascript"
				src="${pageContext.request.contextPath}/js/sui.nav.min.js"></script>
			<div class="foot">
				<div id="sui_nav" class="sui-nav horizontal">
					<div class="sui-nav-wrapper nav-border nav-line">
						<ul id="tTree"></ul>
					</div>
				</div>
			</div>
		</div>
	</div>

	<div class="main">
		<div class="mainBg">
			<iframe name="workspace" id="workspace" frameborder="0"
				scrolling="auto" width="100%" height="auto" allowtransparency="true"></iframe>
		</div>
	</div>
	<script type="text/javascript">
		$('#workspace').attr("src", '${pageDefaultAction}');
		loadNavigationMenuData('${superLoginFlag}');
	</script>
</body>
</html>
