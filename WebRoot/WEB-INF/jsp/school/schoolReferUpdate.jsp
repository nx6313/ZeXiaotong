<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/top/top_main_1.jsp"%>
<script type="text/javascript">
	layui.config({
		base : '${pageContext.request.contextPath}/js/modules/'
	}).use('schoolReferUpdate');
</script>
</head>

<body class="page-pop">
	<form id="form" class="layui-form" action="doSchoolInfoAdd.action">
		<div class="layui-fluid">
			<div class="layui-row">
				<div class="layui-col-xs9 layui-col-sm9 layui-col-md9">
					<div class="layui-row">
						<div class="layui-col-xs12 layui-col-sm12 layui-col-md12">
							<div class="layui-form-item">
								<label class="layui-form-label formRequired">*院校名</label>
								<div class="layui-input-block">
									<input type="text" name="academy.schoolName" required value="${academy.schoolName }"
										lay-verify="schoolNamePass" placeholder="请输入院校名称"
										autocomplete="off" class="layui-input" />
								</div>
							</div>
						</div>
					</div>
					<div class="layui-row">
						<div class="layui-col-xs12 layui-col-sm12 layui-col-md12">
							<div class="layui-form-item">
								<label class="layui-form-label formRequired">*院校类型</label>
								<div class="layui-input-block">
									<select name="academy.schoolType" required
										lay-verify="schoolTypePass" placeholder="请选择院校类型" lay-search>
										<option value="">请选择院校类型</option>
										<c:forEach items="${schoolTypeMap }" var="schoolTy">
											<c:if test="${schoolTy.value == academy.schoolType }">
												<option value="${schoolTy.value }" selected>${schoolTy.value }</option>
											</c:if>
											<c:if test="${schoolTy.value != academy.schoolType }">
												<option value="${schoolTy.value }">${schoolTy.value }</option>
											</c:if>
										</c:forEach>
									</select>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="layui-col-xs3 layui-col-sm3 layui-col-md3">
					<div class="schoolInfoLogoWrap">
						<input id="uploadSchoolLogoFileId" type="hidden" /> <input
							id="uploadSchoolLogoFilePath" name="academy.schoolLogo"
							type="hidden" lay-verify="schoolLogoPass" />
						<div class="schoolInfoLogo" id="uploadSchoolLogo">
							<img draggable="false" src="..${academy.schoolLogo }" title="点击重新上传" />
						</div>
					</div>
				</div>
			</div>
			<div class="layui-row">
				<div class="layui-col-xs12 layui-col-sm12 layui-col-md12">
					<div class="layui-form-item">
						<label class="layui-form-label formRequired">*所在地</label>
						<div class="layui-input-block">
							<select name="academy.location" required
								lay-verify="locationPass" placeholder="请选择院校所在省份" lay-search>
								<option value="">请选择院校所在省份</option>
								<c:forEach items="${schoolLocationMap }" var="schoolLo">
									<c:if test="${schoolLo.value == academy.location }">
										<option value="${schoolLo.value }" selected>${schoolLo.value }</option>
									</c:if>
									<c:if test="${schoolLo.value != academy.location }">
										<option value="${schoolLo.value }">${schoolLo.value }</option>
									</c:if>
								</c:forEach>
							</select>
						</div>
					</div>
				</div>
			</div>
			<div class="layui-row">
				<div class="layui-col-xs6 layui-col-sm6 layui-col-md6">
					<div class="layui-form-item">
						<label class="layui-form-label formRequired">*学历层次</label>
						<div class="layui-input-block">
							<select name="academy.education" required
								lay-verify="educationPass" placeholder="请选择院校学历层次" lay-search>
								<option value="">请选择院校学历层次</option>
								<c:forEach items="${schoolEducationMap }" var="schoolEd">
									<c:if test="${schoolEd.value == academy.education }">
										<option value="${schoolEd.value }" selected>${schoolEd.value }</option>
									</c:if>
									<c:if test="${schoolEd.value != academy.education }">
										<option value="${schoolEd.value }">${schoolEd.value }</option>
									</c:if>
								</c:forEach>
							</select>
						</div>
					</div>
				</div>
				<div class="layui-col-xs6 layui-col-sm6 layui-col-md6">
					<div class="layui-form-item">
						<label class="layui-form-label formRequired">*学历类型</label>
						<div class="layui-input-block">
							<select name="academy.educationType" required
								lay-verify="educationTypePass" placeholder="请选择院校学历类型"
								lay-search>
								<option value="">请选择院校学历类型</option>
								<c:forEach items="${schoolEducationTypeMap }" var="schoolEdTy">
									<c:if test="${schoolEdTy.value == academy.educationType }">
										<option value="${schoolEdTy.value }" selected>${schoolEdTy.value }</option>
									</c:if>
									<c:if test="${schoolEdTy.value != academy.educationType }">
										<option value="${schoolEdTy.value }">${schoolEdTy.value }</option>
									</c:if>
								</c:forEach>
							</select>
						</div>
					</div>
				</div>
			</div>
			<div class="layui-row">
				<div class="layui-col-xs6 layui-col-sm6 layui-col-md6">
					<div class="layui-form-item">
						<label class="layui-form-label formRequired">*招生办电话</label>
						<div class="layui-input-block">
							<input type="text" name="academy.admissionOfficePhone" required value="${academy.admissionOfficePhone }"
								lay-verify="admissionOfficePhonePass" placeholder="请输入招生办电话"
								autocomplete="off" class="layui-input" />
						</div>
					</div>
				</div>
				<div class="layui-col-xs6 layui-col-sm6 layui-col-md6">
					<div class="layui-form-item">
						<label class="layui-form-label">电子邮箱</label>
						<div class="layui-input-block">
							<input type="text" name="academy.email" lay-verify="emailPass" value="${academy.email }"
								placeholder="请输入电子邮箱" autocomplete="off" class="layui-input" />
						</div>
					</div>
				</div>
			</div>
			<div class="layui-row">
				<div class="layui-col-xs6 layui-col-sm6 layui-col-md6">
					<div class="layui-form-item">
						<label class="layui-form-label formRequired">*通讯地址</label>
						<div class="layui-input-block">
							<input type="text" name="academy.address" required value="${academy.address }"
								lay-verify="addressPass" placeholder="请输入通讯地址"
								autocomplete="off" class="layui-input" />
						</div>
					</div>
				</div>
				<div class="layui-col-xs6 layui-col-sm6 layui-col-md6">
					<div class="layui-form-item">
						<label class="layui-form-label formRequired">*招生网址</label>
						<div class="layui-input-block">
							<input type="text" name="academy.admissionNet" required value="${academy.admissionNet }"
								lay-verify="admissionNetPass" placeholder="请输入招生网址"
								autocomplete="off" class="layui-input" />
						</div>
					</div>
				</div>
			</div>
			<div class="layui-row">
				<div class="layui-col-xs6 layui-col-sm6 layui-col-md6">
					<div class="layui-form-item">
						<label class="layui-form-label formRequired">*全球排名</label>
						<div class="layui-input-block">
							<input type="text" name="academy.globalHeat" required value="${academy.globalHeat }"
								lay-verify="globalHeatPass" placeholder="请输入全球热度排名"
								autocomplete="off" class="layui-input" />
						</div>
					</div>
				</div>
				<div class="layui-col-xs6 layui-col-sm6 layui-col-md6">
					<div class="layui-form-item">
						<label class="layui-form-label formRequired">*类别排名</label>
						<div class="layui-input-block">
							<input type="text" name="academy.classHeat" required value="${academy.classHeat }"
								lay-verify="classHeatPass" placeholder="请输入类别热度排名"
								autocomplete="off" class="layui-input" />
						</div>
					</div>
				</div>
			</div>
			<div class="layui-row">
				<div class="layui-col-xs12 layui-col-sm12 layui-col-md12">
					<div class="layui-form-item">
						<label class="layui-form-label">院校简介</label>
						<div class="layui-input-block">
							<textarea style="resize: none;" name="academy.intro"
								lay-verify="introPass" placeholder="请输入院校简介"
								class="layui-textarea">${academy.intro }</textarea>
						</div>
					</div>
				</div>
			</div>
			<div class="layui-row">
				<div class="layui-col-xs12 layui-col-sm12 layui-col-md12">
					<div class="layui-form-item">
						<label class="layui-form-label">就业情况</label>
						<div class="layui-input-block">
							<textarea style="resize: none;" name="academy.employment"
								lay-verify="employmentPass" placeholder="请输入院校就业情况"
								class="layui-textarea">${academy.employment }</textarea>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="layui-form-item">
			<div class="layui-input-block">
				<button class="layui-btn" lay-submit=""
					lay-filter="schoolInfoAddForm">提交修改</button>
				<button type="reset" class="layui-btn layui-btn-primary">重置</button>
			</div>
		</div>
	</form>
</body>
</html>
