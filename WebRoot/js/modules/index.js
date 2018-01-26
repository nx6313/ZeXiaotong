layui.define([ 'layer', 'form', 'element' ], function(exports) {
	var layer = layui.layer, form = layui.form, elem = layui.element;
	
	initUserTypeForStudentCardActive();

	exports('index', {});
});

function initUserTypeForStudentCardActive() {
	if(loginUserType == 10){
		if(!loginUserName){
			showCardJiActive(loginUserCardNum);
		}
	}
}