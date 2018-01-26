layui.define([ 'layer', 'form' ], function(exports) {
	var layer = layui.layer, form = layui.form;
	initPageScrollBar($('body'), 'inset');

	form.verify({
		studentCardActivePwdPass : function(value) {
			if (!value) {
				return '密码不能为空';
			}
		},
		studentCardActiveNamePass : function(value) {
			if (!value) {
				return '昵称不能为空';
			}
		},
		studentCardActivePhonePass : function(value) {
			if (!value) {
				return '绑定手机号不能为空';
			}
		},
		studentCardActiveXueKePass : function(value) {
			if (!value) {
				return '请选择文理科类型';
			}
		},
		studentCardActiveAreaPass : function(value) {
			if (!value) {
				return '请填写您所在的地区';
			}
		},
		studentCardActiveSchoolPass : function(value) {
			if (!value) {
				return '请填写您所在学校';
			}
		},
		studentCardActiveClassPass : function(value) {
			if (!value) {
				return '请填写您的班级';
			}
		}
	});

	form.on('submit(studentCardForm)', function(data) {
		var options = {
			type : 'post',
			success : function(cardActiveData) {
				if (cardActiveData == "ajaxSuccess") {
					parent.layer.msg('操作成功');
					var index = parent.layer.getFrameIndex(window.name);
					parent.layer.close(index);
					// 刷新頁面
				} else if (cardActiveData == 'ajaxExist') {
					parent.layer.msg('您填写的手机号已被使用，请更换手机号吧');
				} else {
					parent.layer.msg('操作失败，请重试');
				}
			}
		};
		$("#cardActiveForm").ajaxForm(options).submit();

		return false;
	});

	exports('studentCardActive', {});
});
