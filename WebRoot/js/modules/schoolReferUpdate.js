layui.define([ 'layer', 'form' ], function(exports) {
	var layer = layui.layer, form = layui.form;
	initPageScrollBar($('body'), 'inset');

	layui.use('element', function() {
		var element = layui.element;
	});

	exports('schoolReferUpdate', {});
});
