layui.define([ 'layer', 'form' ], function(exports) {
	var layer = layui.layer, form = layui.form;
	initPageScrollBar($('body'), 'inset');

	layui.use('element', function() {
		var element = layui.element;
	});

	layui.use('upload', function() {
		var upload = layui.upload;

		// 执行上传院校Logo实例
		var uploadInst = upload.render({
			elem : '#uploadSchoolLogo',
			url : 'uploadExcelFile',
			field : 'uploadFile',
			data : {
				doType : 'img'
			},
			before : function(obj) {
				layer.load();
			},
			done : function(res) {
				console.log("院校LOGO上传完成", res);
				layer.closeAll('loading');
				if (res.code == 0) {
					var logoImgHtml = '<img draggable="false" src="..'
							+ res.filePath + '" title="点击重新上传" />';
					$('#uploadSchoolLogo').html(logoImgHtml);
					$('#uploadSchoolLogoFileId').val(res.fileRecordId);
					$('#uploadSchoolLogoFilePath').val(res.filePath);
				} else {
					var logoTipHtml = '<span>上传院校<br>LOGO</span>';
					$('#uploadSchoolLogo').html(logoTipHtml);
					$('#uploadSchoolLogoFileId').val('');
					$('#uploadSchoolLogoFilePath').val('');
					parent.layer.msg('院校LOGO上传失败');
				}
			},
			error : function() {
				layer.closeAll('loading');
				var logoTipHtml = '<span>上传院校<br>LOGO</span>';
				$('#uploadSchoolLogo').html(logoTipHtml);
				$('#uploadSchoolLogoFileId').val('');
				$('#uploadSchoolLogoFilePath').val('');
				parent.layer.msg('院校LOGO上传失败');
			}
		});

		// 执行数据批量导入实例
		var uploadInst = upload.render({
			elem : '#uploadExcelData',
			url : 'uploadExcelFile',
			accept : 'file',
			exts : 'xls|xlsx',
			auto : false,
			bindAction : '#btnStartUploadExcelData',
			field : 'uploadFile',
			data : {
				doType : 'excel',
				excelType : 'schoolInfoExcel'
			},
			before : function(obj) {
				layer.load();
			},
			done : function(res) {
				console.log("上传完成", res);
				layer.closeAll('loading');
				if (res.code == 0) {
					if (res.msg) {
						parent.layer.msg(res.msg);
					} else {
						parent.layer.msg('数据导入成功');
					}
					parent.parent.loadMainWorkSpance("toSchoolRefer");
					var index = parent.layer.getFrameIndex(window.name);
					parent.layer.close(index);
				} else {
					if (res.msg) {
						parent.layer.msg(res.msg);
					} else {
						parent.layer.msg('数据导入失败');
					}
					window.location.reload();
				}
			},
			error : function() {
				layer.closeAll('loading');
				parent.layer.msg('数据导入失败');
				window.location.reload();
			}
		});
	});

	form.verify({
		schoolNamePass : function(value) {
			if (!value) {
				return '院校名称不能为空';
			}
		},
		schoolTypePass : function(value) {
			if (!value) {
				return '院校类型不能为空';
			}
		},
		schoolLogoPass : function(value) {
			if (!value) {
				return '请上传院校Logo';
			}
		},
		locationPass : function(value) {
			if (!value) {
				return '院校所在省不能为空';
			}
		},
		educationPass : function(value) {
			if (!value) {
				return '院校学历层次不能为空';
			}
		},
		educationTypePass : function(value) {
			if (!value) {
				return '院校学历类型不能为空';
			}
		},
		admissionOfficePhonePass : function(value) {
			if (!value) {
				return '招生办电话不能为空';
			}
		},
		addressPass : function(value) {
			if (!value) {
				return '院校通讯地址不能为空';
			}
		},
		admissionNetPass : function(value) {
			if (!value) {
				return '院校招生网址不能为空';
			}
		},
		globalHeatPass : function(value) {
			if (!value) {
				return '院校全球排名不能为空';
			} else if (!$.isNumeric(value)) {
				return '院校全球排名应该是数字格式';
			}
		},
		classHeatPass : function(value) {
			if (!value) {
				return '院校类别排名不能为空';
			} else if (!$.isNumeric(value)) {
				return '院校类别排名应该是数字格式';
			}
		}
	});

	form.on('submit(schoolInfoAddForm)', function(data) {
		var options = {
			type : 'post',
			dataType : 'jsonp',
			jsonp : 'callback',
			jsonpCallback : 'success_jsonpCallback',
			success : function(data) {
				if (data == "ajaxSuccess") {
					parent.layer.msg('操作成功');
					parent.parent.loadMainWorkSpance("toSchoolRefer");
					var index = parent.layer.getFrameIndex(window.name);
					parent.layer.close(index);
				} else {
					layer.msg('操作失败，请重试');
				}
			}
		};
		$("#form").ajaxForm(options).submit();

		return false;
	});

	exports('schoolReferAdd', {});
});
