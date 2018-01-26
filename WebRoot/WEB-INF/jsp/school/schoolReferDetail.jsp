<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/top/top_main_1.jsp"%>
<script type="text/javascript">
	layui.config({
		base : '${pageContext.request.contextPath}/js/modules/'
	}).use('schoolReferDetail');
</script>
</head>

<body class="page-pop animated fadeIn">
	<div class="layui-tab layui-tab-brief" lay-filter="schoolDetailTab">
		<ul class="layui-tab-title">
			<li class="layui-this">学校主页</li>
			<li>学校简介</li>
			<li>招生专业</li>
			<li>招生计划</li>
			<li>招生章程</li>
			<li>各省录取线</li>
			<li>专业录取线</li>
			<li>校园风光</li>
			<li>招生访谈</li>
			<li>就业情况</li>
		</ul>
		<div class="layui-tab-content">
			<div class="layui-tab-item layui-show">内容1</div>
			<div class="layui-tab-item">内容2</div>
			<div class="layui-tab-item">内容3</div>
			<div class="layui-tab-item">内容4</div>
			<div class="layui-tab-item">内容5</div>
			<div class="layui-tab-item">内容6</div>
			<div class="layui-tab-item">内容7</div>
			<div class="layui-tab-item">内容8</div>
			<div class="layui-tab-item">内容9</div>
			<div class="layui-tab-item">内容10</div>
		</div>
	</div>
</body>
</html>
