layui.define([ 'layer' ], function(exports) {
	parent.closeLoading();
	var layer = layui.layer;
	initPageScrollBar($('body'), 'inset');
	initSuperManagerDoWrap(isManagerLoginFlag, 'skipSchoolReferAdd');
	initSearchBtnWrap({
		'schoolName%' : '院校名称',
		'#schoolType' : '院校类型',
		'#location' : '所在地（省）',
		'#education' : '学历层次',
		'address%' : '通讯地址',
		'specialAttr' : '特殊属性'
	}, [ schoolTypeMap, schoolLocationMap, schoolEducationMap ]);
	bindTableLineHoverTip({
		tipDataArr : [ 'img|schoolLogo', '院校名:schoolName', '地址:address',
				'招生办电话:admissionOfficePhone' ],
		composeStyle : 1
	});
	getDataByPage({
		actionUri : 'getSchoolDataByPage.action',
		tableLineNum : (isManagerLoginFlag ? 7 : 6),
		dataTrArr : [ 'tbIndexNum', [ 'link:toSchoolDetail', 'schoolName' ],
				'schoolType', 'location', 'globalHeat', 'classHeat' ],
		activeAreaJson : (isManagerLoginFlag ? {
			"&#xe640:删除" : "deleteSchoolInfo",
			"&#xe6b2:修改" : "updateSchoolInfo"
		} : null)
	});

	layui.use('element', function() {
		var element = layui.element;
	});

	exports('schoolRefer', function(type, thiz) {
		if (type == 'dataSearch') {
			zeXiaoDataSearch({
				actionUri : 'getSchoolDataByPage.action',
				tableLineNum : (isManagerLoginFlag ? 7 : 6),
				dataTrArr : [ 'tbIndexNum',
						[ 'link:toSchoolDetail', 'schoolName' ], 'schoolType',
						'location', 'globalHeat', 'classHeat' ],
				activeAreaJson : (isManagerLoginFlag ? {
					"&#xe640:删除" : "deleteSchoolInfo",
					"&#xe6b2:修改" : "updateSchoolInfo"
				} : null)
			});
		}
	});
});

// 跳转到数据详情
var toSchoolDetail = function(schoolDataFullObj) {
	let schoolId = schoolDataFullObj.id;
	let schoolName = schoolDataFullObj.schoolName;
	layerNewPop('skipSchoolDetail.action?schoolId=' + schoolId, '院校『'
			+ schoolName + '』详情');
}

// 管理员操作 --> 删除
var deleteSchoolInfo = function(schoolDataFullObj) {
	let schoolId = schoolDataFullObj.id;
	let schoolName = schoolDataFullObj.schoolName;
	parent.layer.confirm('确定删除院校『' + schoolName + '』吗？', {
		title : '删除院校提示',
		btn : [ '确定删除', '取消删除' ]
	}, function(index) {
		$.ajax({
			type : "post",
			url : "deleteSchoolInfo.action",
			data : {
				schoolId : schoolId
			},
			dataType : "html",
			async : true,
			success : function(data) {
				parent.layer.close(index);
				layer.msg('院校『' + schoolName + '』删除成功', function() {
					parent.loadMainWorkSpance("toSchoolRefer");
				});
			}
		});
	}, function(index) {
		parent.layer.close(index);
	});
}

// 管理员操作 --> 修改
var updateSchoolInfo = function(schoolDataFullObj) {
	let schoolId = schoolDataFullObj.id;
	let schoolName = schoolDataFullObj.schoolName;
	layerNewPop('skipSchoolInfoUpdate?schoolId=' + schoolId, '修改『' + schoolName
			+ '』信息');
}
