<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/top/top_main_1.jsp"%>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/school_refer.css">
<script type="text/javascript">
	var schoolTypeMap = '${schoolTypeMap }';
	var schoolLocationMap = '${schoolLocationMap }';
	var schoolEducationMap = '${schoolEducationMap }';
	var isManagerLoginFlag = '${isManagerLogin }';
	layui.config({
		base : '${pageContext.request.contextPath}/js/modules/'
	}).use('schoolRefer');
</script>
</head>

<body class="page-main">
	<div class="bread-ceil">
		<span class="layui-breadcrumb"> <a href="javascript:void(0);"
			onclick="parent.ceilToIndex()">首页</a> <a><cite>院校信息</cite></a> <a><cite>院校信息查看</cite></a>
		</span>
		<div class="bread-ceil-footer"></div>
	</div>
	<div class="content-main animated fadeIn">
		<div class="searchWrap">
			<div id="searchBtnWrap"
				style="display: inline-block; width: calc(100% - 100px);"></div>
			<button
				style="display: inline-block; width: 86px; vertical-align: bottom;"
				class="layui-btn" onclick="layui.schoolRefer('dataSearch', this)">
				<i class="layui-icon">&#xe615;</i> 搜索
			</button>
		</div>
		<table id="mainDataTable" class="layui-table"
			lay-filter="mainDataTable">
			<colgroup>
				<col width="60">
				<col width="200">
				<col width="200">
				<col width="200">
				<col width="120">
				<col width="120">
				<c:if test="${isManagerLogin }">
					<col>
				</c:if>
			</colgroup>
			<thead>
				<tr>
					<th rowspan="2">序号</th>
					<th rowspan="2">院校名称</th>
					<th rowspan="2">院校类型</th>
					<th rowspan="2">所在省份</th>
					<th colspan="2" style="text-align: center;">报名热度排名</th>
					<c:if test="${isManagerLogin }">
						<th rowspan="2">操作</th>
					</c:if>
				</tr>
				<tr>
					<th>全国热度排名</th>
					<th>类别热度排名</th>
				</tr>
			</thead>
			<tbody id="mainDataWarp"></tbody>
		</table>
		<div id="pageElement"></div>
	</div>
</body>
</html>
