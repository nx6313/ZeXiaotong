<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/top/top_main_1.jsp"%>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/student_card_manager.css">
<script type="text/javascript">
	var agencyId = '${defaultShowAgency}';
	var allStudentCardNum = '${allStudentCardNum}';
	var saleStudentCardNum = '${saleStudentCardNum}';
	var unsaleStudentCardNum = '${unsaleStudentCardNum}';
	var managerSeeAgencyList = '${agencyList}';
	var seeStudentCardManagerUserType = '${seeStudentCardManagerUserType}';
	layui.config({
		base : '${pageContext.request.contextPath}/js/modules/'
	}).use('studentCardManager');
</script>
</head>

<body class="page-main">
	<div class="bread-ceil">
		<span class="layui-breadcrumb"> <a href="javascript:void(0);"
			onclick="parent.ceilToIndex()">首页</a> <a><cite>用户管理</cite></a> <a><cite>卡管理</cite></a>
		</span>
		<div class="bread-ceil-footer"></div>
	</div>
	<div class="content-main studentCardManagerMainWarp">
		<c:if test="${agencyList != null }">
			<div class="agencyListWrap">
				<c:forEach items="${agencyList }" var="item">
					<c:if test="${item.id == defaultShowAgency }">
						<button class="layui-btn layui-btn-mini" value="${item.id }"
							onclick="layui.studentCardManager('managerClickDl', this)">
							<i class="layui-icon">&#xe62e;</i> ${item.userName }【代理】
						</button>
					</c:if>
					<c:if test="${item.id != defaultShowAgency }">
						<button class="layui-btn layui-btn-mini layui-btn-primary"
							value="${item.id }"
							onclick="layui.studentCardManager('managerClickDl', this)">
							<i class="layui-icon">&#xe62e;</i> ${item.userName }【代理】
						</button>
					</c:if>
				</c:forEach>
			</div>
		</c:if>
		<blockquote class="layui-elem-quote">
			当前已有学生卡 <span id="allCardCountSpan">${allStudentCardNum }</span> 张，其中
			<span id="saleCardCountSpan">${saleStudentCardNum }</span> 张已被购买使用，还有
			<span id="unsaleCardCountSpan">${unsaleStudentCardNum }</span>
			张未出售；被购买部分有 <span id="dongCardCountSpan">${dongStudentCardNum }</span>
			张被冻结
		</blockquote>
		<div class="layui-tab" lay-filter="studentCardManagerTabFilter">
			<ul class="layui-tab-title">
				<li class="layui-this" lay-id="allCardTab">已生成的学生会员卡信息（包含已出售和未出售）</li>
				<li lay-id="saleCardTab">已出售的学生会员卡信息</li>
				<li lay-id="unsaleCardTab">未出售的学生会员卡信息</li>
				<li lay-id="addNewCardTab">新增学生会员卡</li>
			</ul>
			<div class="layui-tab-content">
				<div class="layui-tab-item layui-show">
					<div style="position: relative;">
						<input type="text" name="title" lay-verify="title"
							id="userSearchInput" autocomplete="off"
							placeholder="输入卡号或用户名称进行搜索" class="layui-input">
						<button style="position: absolute; top: 0px; right: 0px;"
							class="layui-btn"
							onclick="layui.studentCardManager('userSearch', this)">
							<i class="layui-icon">&#xe615;</i> 搜索
						</button>
					</div>
					<table class="layui-table">
						<colgroup>
							<col width="80">
							<col width="200">
							<col width="170">
							<col width="80">
							<col width="180">
							<col width="140">
							<col width="160">
							<col>
						</colgroup>
						<thead>
							<tr>
								<th>序号</th>
								<th>卡号</th>
								<th>生成卡时间</th>
								<th>卡状态</th>
								<th>卡所属用户</th>
								<th>当前卡余额</th>
								<th>已被使用时间</th>
								<th>操作</th>
							</tr>
						</thead>
						<tbody id="studentCardAllTbady"></tbody>
					</table>
					<div id="studentCardInfoPage"></div>
				</div>
				<div class="layui-tab-item">
					<div style="position: relative;">
						<input type="text" name="title" lay-verify="title"
							id="userSearchInput" autocomplete="off"
							placeholder="输入卡号或用户名称进行搜索" class="layui-input">
						<button style="position: absolute; top: 0px; right: 0px;"
							class="layui-btn"
							onclick="layui.studentCardManager('userSearch', this)">
							<i class="layui-icon">&#xe615;</i> 搜索
						</button>
					</div>
					<table class="layui-table">
						<colgroup>
							<col width="80">
							<col width="200">
							<col width="170">
							<col width="80">
							<col width="180">
							<col width="140">
							<col width="160">
							<col>
						</colgroup>
						<thead>
							<tr>
								<th>序号</th>
								<th>卡号</th>
								<th>生成卡时间</th>
								<th>卡状态</th>
								<th>卡所属用户</th>
								<th>当前卡余额</th>
								<th>已被使用时间</th>
								<th>操作</th>
							</tr>
						</thead>
						<tbody id="studentCardSaleTbady"></tbody>
					</table>
					<div id="studentCardOutInfoPage"></div>
				</div>
				<div class="layui-tab-item">
					<div style="position: relative;">
						<input type="text" name="title" lay-verify="title"
							id="userSearchInput" autocomplete="off"
							placeholder="输入卡号或用户名称进行搜索" class="layui-input">
						<button style="position: absolute; top: 0px; right: 0px;"
							class="layui-btn"
							onclick="layui.studentCardManager('userSearch', this)">
							<i class="layui-icon">&#xe615;</i> 搜索
						</button>
					</div>
					<table class="layui-table">
						<colgroup>
							<col width="80">
							<col width="200">
							<col width="170">
							<col width="80">
							<col width="180">
							<col width="140">
							<col width="160">
							<col>
						</colgroup>
						<thead>
							<tr>
								<th>序号</th>
								<th>卡号</th>
								<th>生成卡时间</th>
								<th>卡状态</th>
								<th>卡所属用户</th>
								<th>当前卡余额</th>
								<th>已被使用时间</th>
								<th>操作</th>
							</tr>
						</thead>
						<tbody id="studentCardUnsaleTbady"></tbody>
					</table>
					<div id="studentCardUnOutInfoPage"></div>
				</div>
				<div class="layui-tab-item">
					<form id="form" class="layui-form layui-form-pane"
						action="doStudentCardAdd.action">
						<input type="hidden" id="agencyIdInput" name="agencyId"
							value="${defaultShowAgency }" />
						<div class="layui-form-item">
							<label class="layui-form-label">生成卡数量</label>
							<div class="layui-input-block">
								<input type="text" name="createStudentCardNum" required
									lay-verify="studentCardNumPass" placeholder="请输入您想要生成的学生卡数量"
									autocomplete="off" class="layui-input" />
							</div>
						</div>
						<div class="layui-form-item">
							<div class="layui-input-block">
								<button class="layui-btn" lay-submit=""
									lay-filter="studentCardForm">生成学生卡</button>
								<button type="reset" class="layui-btn layui-btn-primary">重置</button>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
	<div id="managerSeeNoAgency" class="content-main"></div>
</body>
</html>
