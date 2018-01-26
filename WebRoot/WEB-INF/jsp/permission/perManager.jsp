<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/top/top_main_1.jsp"%>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/permission.css">
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery.masonry.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/Sortable.js"></script>
<script type="text/javascript">
	var perPIdList = '${perPIdList}';
	layui.config({
		base : '${pageContext.request.contextPath}/js/modules/'
	}).use('perManager');
</script>
</head>

<body class="page-main">
	<div class="bread-ceil">
		<span class="layui-breadcrumb"> <a
			href="javascript:void(0);" onclick="parent.ceilToIndex()">首页</a> <a><cite>系统设置</cite></a>
			<a><cite>导航模块管理</cite></a>
		</span>
		<div class="bread-ceil-footer"></div>
	</div>
	<div class="content-main">
		<div class="layui-btn-group" style="float: right;">
			<button class="layui-btn layui-btn-small addPerPBtn"
				onclick="layui.perManager('addPerP')">
				<i class="layui-icon">&#xe654;</i> 添加新的父级权限模块
			</button>
			<button
				class="layui-btn layui-btn-small layui-btn layui-btn-warm refPerDataBtn"
				onclick="layui.perManager('ref')">
				<i class="layui-icon">&#x1002;</i> 刷新数据列表
			</button>
		</div>
		<fieldset class="layui-elem-field layui-field-title perMainContent"
			style="clear: both;">
			<legend> 所有权限模块 </legend>
			<div class="layui-field-box">
				<c:if test="${permissionData.size() == 0 }">
					<blockquote class="layui-elem-quote">当前无权限模块，点击右上角进行添加</blockquote>
				</c:if>
				<c:if test="${permissionData.size() > 0 }">
					<div id="perMainDataElem" class="layui-collapse" lay-accordion
						lay-filter="perAllWarp">
						<c:forEach items="${permissionData }" var="permission"
							varStatus="i">
							<div class="layui-colla-item" id="perItemWarp${i.index + 1 }">
								<div class="layui-colla-title">
									<span class="perAboutCSizeSpan_${permission.key.id }">
										${permission.key.permissionName } <span
										class="perPAboutCSizeLoading"> <img
											src="images/loading_2.gif" /> 获取子权限数据中...
									</span>
									</span>
									<div class="layui-btn-group"
										style="margin-left: 30px; float: right;">
										<button class="layui-btn layui-btn-mini addPerCBtn"
											onclick="layui.perManager('addPerC', '${permission.key.permissionName }', '${permission.key.id }')">
											<i class="layui-icon">&#xe654;</i>
											添加【${permission.key.permissionName }】下的子级权限
										</button>
										<c:if test="${permission.key.permissionStatus == 0 }">
											<button class="layui-btn layui-btn-mini upDownPerPBtn"
												style="background: #CC2028;"
												onclick="layui.perManager('perUp', '父级权限&lt;br/&gt;【${permission.key.permissionName }】', 'p_${permission.key.id }')">
												<i class="layui-icon">&#x1007;</i> <i class="layui-icon">&#xe609;</i>
												上架
											</button>
										</c:if>
										<c:if test="${permission.key.permissionStatus == 1 }">
											<button class="layui-btn layui-btn-mini upDownPerPBtn"
												style="background: #3DA97A;"
												onclick="layui.perManager('perDown', '父级权限&lt;br/&gt;【${permission.key.permissionName }】', 'p_${permission.key.id }')">
												<i class="layui-icon">&#xe616;</i> <i class="layui-icon">&#xe64d;</i>
												下架
											</button>
										</c:if>
										<button class="layui-btn layui-btn-mini updatePerPBtn"
											onclick="layui.perManager('updateP', '${permission.key.permissionName }', '${permission.key.id }')">
											<i class="layui-icon">&#xe642;</i>
										</button>
										<button class="layui-btn layui-btn-mini deletePerPBtn"
											onclick="layui.perManager('deleteP', '${permission.key.permissionName }', '${permission.key.id }')">
											<i class="layui-icon">&#xe640;</i>
										</button>
										<button class="layui-btn layui-btn-mini drag-handle"
											title="按住拖动" style="background: #EA7E4E; cursor: n-resize;"
											onclick="layui.perManager('dragItem')">
											<i class="layui-icon">&#xe630;</i> 拖动排序
										</button>
									</div>
								</div>
								<div
									class="layui-colla-content perCeilWarp perAboutCDataDiv_${permission.key.id }">
									<div class="perPAboutCDataLoading">
										<img src="images/loading_1.gif" /> <span>正在加载子权限数据，请稍后...</span>
									</div>
									<div class="perPAboutCDataMain"></div>
								</div>
							</div>
						</c:forEach>
					</div>
				</c:if>
			</div>
		</fieldset>
	</div>
</body>
</html>
