layui.define([ 'layer', 'form' ], function(exports) {
	var layer = layui.layer, form = layui.form;
	initPageScrollBar($('body'), 'inset');

	form.verify({
		userNamePass : function(value) {
			if (!value) {
				return '昵称不能为空';
			}
		},
		userMobilePass : function(value) {
			if (!value) {
				return '手机号不能为空';
			}
		}
	});

	form.on('submit(userAddForm)', function(data) {
		var options = {
			type : 'post',
			success : function(data) {
				if (data == "ajaxSuccess") {
					parent.layer.msg('操作成功');
					parent.parent.loadMainWorkSpance("toUserManager");
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

	exports('userAdd', {});
});
