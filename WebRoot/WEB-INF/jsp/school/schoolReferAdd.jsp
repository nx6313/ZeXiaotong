<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/top/top_main_1.jsp"%>
<script type="text/javascript">
	layui.config({
		base : '${pageContext.request.contextPath}/js/modules/'
	}).use('schoolReferAdd');
</script>
</head>

<body class="page-pop">
	<div class="layui-tab layui-tab-brief" lay-filter="schoolInfoAddTab">
		<ul class="layui-tab-title">
			<li class="layui-this">数据添加</li>
			<li>数据批量导入</li>
		</ul>
		<div class="layui-tab-content">
			<div class="layui-tab-item layui-show">
				<form id="form" class="layui-form" action="doSchoolInfoAdd.action">
					<div class="layui-fluid">
						<div class="layui-row">
							<div class="layui-col-xs9 layui-col-sm9 layui-col-md9">
								<div class="layui-row">
									<div class="layui-col-xs12 layui-col-sm12 layui-col-md12">
										<div class="layui-form-item">
											<label class="layui-form-label formRequired">*院校名</label>
											<div class="layui-input-block">
												<input type="text" name="academy.schoolName" required
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
													lay-verify="schoolTypePass" placeholder="请选择院校类型"
													lay-search>
													<option value="" selected>请选择院校类型</option>
													<c:forEach items="${schoolTypeMap }" var="schoolTy">
														<option value="${schoolTy.value }">${schoolTy.value }</option>
													</c:forEach>
												</select>
											</div>
										</div>
									</div>
								</div>
							</div>
							<div class="layui-col-xs3 layui-col-sm3 layui-col-md3">
								<div class="schoolInfoLogoWrap">
									<input id="uploadSchoolLogoFileId" type="hidden" />
									<input id="uploadSchoolLogoFilePath" name="academy.schoolLogo"
										type="hidden" lay-verify="schoolLogoPass" />
									<div class="schoolInfoLogo" id="uploadSchoolLogo">
										<span>上传院校<br>LOGO
										</span>
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
											<option value="" selected>请选择院校所在省份</option>
											<c:forEach items="${schoolLocationMap }" var="schoolLo">
												<option value="${schoolLo.value }">${schoolLo.value }</option>
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
											<option value="" selected>请选择院校学历层次</option>
											<c:forEach items="${schoolEducationMap }" var="schoolEd">
												<option value="${schoolEd.value }">${schoolEd.value }</option>
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
											lay-verify="educationTypePass" placeholder="请选择院校学历类型" lay-search>
											<option value="" selected>请选择院校学历类型</option>
											<c:forEach items="${schoolEducationTypeMap }" var="schoolEdTy">
												<option value="${schoolEdTy.value }">${schoolEdTy.value }</option>
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
										<input type="text" name="academy.admissionOfficePhone"
											required lay-verify="admissionOfficePhonePass"
											placeholder="请输入招生办电话" autocomplete="off" class="layui-input" />
									</div>
								</div>
							</div>
							<div class="layui-col-xs6 layui-col-sm6 layui-col-md6">
								<div class="layui-form-item">
									<label class="layui-form-label">电子邮箱</label>
									<div class="layui-input-block">
										<input type="text" name="academy.email"
											lay-verify="emailPass" placeholder="请输入电子邮箱"
											autocomplete="off" class="layui-input" />
									</div>
								</div>
							</div>
						</div>
						<div class="layui-row">
							<div class="layui-col-xs6 layui-col-sm6 layui-col-md6">
								<div class="layui-form-item">
									<label class="layui-form-label formRequired">*通讯地址</label>
									<div class="layui-input-block">
										<input type="text" name="academy.address" required
											lay-verify="addressPass" placeholder="请输入通讯地址"
											autocomplete="off" class="layui-input" />
									</div>
								</div>
							</div>
							<div class="layui-col-xs6 layui-col-sm6 layui-col-md6">
								<div class="layui-form-item">
									<label class="layui-form-label formRequired">*招生网址</label>
									<div class="layui-input-block">
										<input type="text" name="academy.admissionNet" required
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
										<input type="text" name="academy.globalHeat" required
											lay-verify="globalHeatPass" placeholder="请输入全球热度排名"
											autocomplete="off" class="layui-input" />
									</div>
								</div>
							</div>
							<div class="layui-col-xs6 layui-col-sm6 layui-col-md6">
								<div class="layui-form-item">
									<label class="layui-form-label formRequired">*类别排名</label>
									<div class="layui-input-block">
										<input type="text" name="academy.classHeat" required
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
											class="layui-textarea"></textarea>
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
											class="layui-textarea"></textarea>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="layui-form-item">
						<div class="layui-input-block">
							<button class="layui-btn" lay-submit=""
								lay-filter="schoolInfoAddForm">立即提交</button>
							<button type="reset" class="layui-btn layui-btn-primary">重置</button>
						</div>
					</div>
				</form>
			</div>
			<div class="layui-tab-item">
				<div class="layui-fluid">
					<div class="layui-row">
						<div class="layui-col-xs8 layui-col-sm8 layui-col-md8">
							<div class="excelFileWrap">
								<i style="color: #158977;" class="layui-icon">&#xe623;</i>
								<button type="button"
									class="layui-btn layui-btn-primary layui-btn-small"
									id="uploadExcelData">
									<i class="layui-icon">&#xe60a;</i>选择Excel数据文件
								</button>
							</div>
						</div>
						<div class="layui-col-xs4 layui-col-sm4 layui-col-md4"
							style="text-align: right;">
							<button id="btnStartUploadExcelData"
								class="layui-btn layui-btn-danger layui-btn-small">开始导入</button>
						</div>
					</div>
				</div>
				<input id="requestContextPath" type="hidden" value="${pageContext.request.contextPath}" />
				<blockquote class="layui-elem-quote" style="margin-top: 20px;">
					<ul>
						<li>1、仅支持Excel文件导入（文件格式为：xls、xlsx）</li>
						<li>2、选择固定格式规范的Excel文件，格式规范详见<a href="javascript: void(0);" onclick="downloadFile('院校导入规范模板.xls');">《院校数据批量导入规范模板》</a></li>
						<li>3、如果选择错误需要重新选择，点击按钮<img src="images/data_upload.png" />重新选择即可
						</li>
						<li>4、选好文件后，点击按钮<img src="images/start_upload.png" />开始导入数据
						</li>
					</ul>
				</blockquote>
			</div>
		</div>
	</div>
</body>
</html>
