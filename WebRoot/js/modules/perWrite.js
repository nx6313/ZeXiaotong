layui.define([ 'layer', 'form' ], function(exports) {
	var layer = layui.layer, form = layui.form;
	initPageScrollBar($('body'), 'inset');

	form.verify({
		perNamePass : function(value) {
			if (!value) {
				return '权限名称不能为空';
			} else if (value.length > 8) {
				return '权限名称不能大于8个字符';
			} else if (value.indexOf('#') >= 0) {
				return '权限名称不能含有特殊字符 \'#\'';
			} else if (value.indexOf('&') >= 0) {
				return '权限名称不能含有特殊字符 \'&\'';
			} else if (value.indexOf('|') >= 0) {
				return '权限名称不能含有特殊字符 \'|\'';
			} else if (value.indexOf(',') >= 0) {
				return '权限名称不能含有特殊字符 \',\'';
			} else if (value.indexOf('，') >= 0) {
				return '权限名称不能含有特殊字符 \'，\'';
			}
		},
		perLinkPass : function(value) {
			if (!value) {
				return '权限链接不能为空';
			}
		}
	});

	form.on('submit(perWriteForm)', function(data) {
		var options = {
			type : 'post',
			success : function(data) {
				if (data == "ajaxSuccess") {
					parent.layer.msg('操作成功');
					parent.parent.loadMainWorkSpance("toPermissionManager");
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

	exports('perWrite', {});
});