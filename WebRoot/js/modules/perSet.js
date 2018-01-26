layui.define([ 'layer', 'form' ], function(exports) {
	parent.closeLoading();
	var layer = layui.layer, form = layui.form;
	initPageScrollBar($('body'), 'inset');

	layui.use('element', function() {
		var element = layui.element;
	});

	form.on('switch(perItemSwitch)', function(data) {
		// 实现全选或全不选的功能
		var perPId = data.value;
		if(data.elem.checked){
			var perCInputArr = $('.perCArea_' + perPId).find('.perCSetContent').find('input');
			$.each(perCInputArr, function(index, inputObj) {
				$(inputObj).prop("checked", true);
				$(inputObj).next().addClass('layui-form-checked');
			});
		}else{
			var perCInputArr = $('.perCArea_' + perPId).find('.perCSetContent').find('input');
			$.each(perCInputArr, function(index, inputObj) {
				$(inputObj).prop("checked", false);
				$(inputObj).next().removeClass('layui-form-checked');
			});
		}
		return false;
	});

	form.on('checkbox(perCItemSwitch)', function(data) {
		// 判断该子权限组是否有被选中的和是否为全未选中的，来影响父级是否选中
		var thisGroupEle = $(data.elem).parent();
		var perCInputArr = $(thisGroupEle).find('input');
		var hasSelectedFlag = false;
		$.each(perCInputArr, function(index, inputObj) {
			if($(inputObj).is(":checked")){
				hasSelectedFlag = true;
				return false;
			}
		});
		var parentPerInputEle = $(data.elem).parent().parent().prev().prev();
		if(hasSelectedFlag){
			// 将父级选中
			if(!$(parentPerInputEle).is(":checked")){
				$(parentPerInputEle).prop("checked", true);
				$(parentPerInputEle).next().addClass('layui-form-onswitch');
			}
		}else{
			// 将父级取消选中
			if($(parentPerInputEle).is(":checked")){
				$(parentPerInputEle).prop("checked", false);
				$(parentPerInputEle).next().removeClass('layui-form-onswitch');
			}
		}
		return false;
	});

	layui.use('tree', function() {
		layui.tree({
			elem : '#userTree',
			nodes : userData,
			click : function(node) {
				$('#user-permission-tab').hide();
				$('#user-group-permission-tab').hide();
				$('#userPerSetForm').find('.perCSetArea').html("");
				$('#groupPerSetForm').find('.perPSetArea').html("");
				if(node.type == 'user'){
					$('#userPerSetForm').find('.formSubmitValues').find('.formUserValues').html('<input type="hidden" name="aboutId" value="' + node.type + "#" + node.aboutId + '" />');
					$('#userPerSetForm').find('.formSubmitValues').find('.formPerValues').html('');
				}else{
					$('#groupPerSetForm').find('.formSubmitValues').find('.formUserValues').html('<input type="hidden" name="aboutId" value="' + node.type + "#" + node.aboutId + '" />');
					$('#groupPerSetForm').find('.formSubmitValues').find('.formPerValues').html('');
				}
				parent.showLoading();
				$.ajax({
					url : 'getUserOrGroupPerData.action',
					type : 'POST',
					cache : false,
					data : {
						type : node.type,
						aboutId : node.aboutId
					},
					dataType : "html",
					//成功后的操作
					success : function(userOrGroupPerData) {
						parent.closeLoading();
						var perSetExplainHtml = '';
						if (node.type == 'user') {
							if(userOrGroupPerData != ""){
								var userPerDataJson = JSON.parse(userOrGroupPerData);
								var userPerAllHtml = "";
								$.each(userPerDataJson, function(index, obj) {
									var userPerPHtml = '<span class="mr10">┠┉┉┉┉</span><input class="perSetInputItem" type="checkbox" name="" '+
										' value="' + obj[0] + '" lay-skin="switch" lay-filter="perItemSwitch" '+
										' lay-text="' + obj[1] + '|' + obj[1] + '"';
									if(obj[5] == '1'){
										userPerPHtml += ' checked /> ';
									}else{
										userPerPHtml += ' /> ';
									}
									var userPerCHtml = '<div class="perCSetArea perCArea_' + obj[0] + '">'+
											'<div class="perCSetTitle">『' + obj[1] + '』子权限设置</div>'+
											'<div class="perCSetContent"></div>'+
											'<img class="perCSetAreaLoading" src="images/get_loading_2.gif" />'+
										'</div>';
									userPerAllHtml += userPerPHtml + userPerCHtml;
									// 获取用户子权限数据
									getPerCDataForUserOrGroup(obj[0], true, 'user', node.aboutId);
								});
								$('#userPerSetForm').find('.perPSetArea').html(userPerAllHtml);
								form.render('checkbox');
								$('#user-permission-tab').show();
								perSetExplainHtml = '当前选择用户：【' + node.parentName + ' -> '
									+ node.name + '】，点击下列的选项卡设置该用户信息';
							}else{
								perSetExplainHtml = '无父级权限数据，请联系管理员添加';
							}
						} else {
							if(userOrGroupPerData != ""){
								var groupPerDataJson = JSON.parse(userOrGroupPerData);
								var groupPerAllHtml = "";
								$.each(groupPerDataJson, function(index, obj) {
									var groupPerPHtml = '<span class="mr10">┠┉┉┉┉</span><input class="perSetInputItem" type="checkbox" name="" '+
										' value="' + obj[0] + '" lay-skin="switch" lay-filter="perItemSwitch" '+
										' lay-text="' + obj[1] + '|' + obj[1] + '"';
									if(obj[5] == '1'){
										groupPerPHtml += ' checked /> ';
									}else{
										groupPerPHtml += ' /> ';
									}
									var groupPerCHtml = '<div class="perCSetArea perCArea_' + obj[0] + '">'+
											'<div class="perCSetTitle">『' + obj[1] + '』子权限设置</div>'+
											'<div class="perCSetContent"></div>'+
											'<img class="perCSetAreaLoading" src="images/get_loading_2.gif" />'+
										'</div>';
									groupPerAllHtml += groupPerPHtml + groupPerCHtml;
									// 获取用户子权限数据
									getPerCDataForUserOrGroup(obj[0], true, 'group', node.aboutId);
								});
								$('#groupPerSetForm').find('.perPSetArea').html(groupPerAllHtml);
								form.render('checkbox');
								perSetExplainHtml += '当前选择用户组：【' + node.name + '】';
								if(node.userCount > 0){
									$('#user-group-permission-tab').show();
									perSetExplainHtml += '，其下共有 ' + node.userCount + ' 个用户';
									perSetExplainHtml += '，点击下列的选项卡设置该用户组信息';
								}else{
									perSetExplainHtml += '，其下还没有任何用户，请前往【用户管理 - <a class="main_link" href="javascript:void(0);" onclick="window.location.href=\'toUserManager.action\'">用户管理</a>】添加用户';
								}
							}else{
								perSetExplainHtml = '无父级权限数据，请联系管理员添加';
							}
						}
						$('#perSetExplain').html(perSetExplainHtml);
					}
				});
			}
		});
	});
	
	form.on('submit(groupFormSubmit)', function(data) {
		parent.layer.msg('对组进行权限设置，会将组内的所有用户权限重置，确认操作吗？', {
			time : 0,
			shade : 0.7,
			shadeClose : true,
			btn : [ '确认修改', '取消操作' ],
			cancle : function(index) {
				parent.layer.close(index);
			},
			yes : function(index) {
				parent.layer.close(index);
				// 获取选中项目，编辑权限字符串
				$(data.form).find('.formSubmitValues').find('.formPerValues').html('');
				var perPAreaArr = $(data.form).find('.perCSetArea');
				$.each(perPAreaArr, function(areaIndex, areaObj) {
					var perCSetContentObj = $(areaObj).find('.perCSetContent');
					var perCInputArr = $(perCSetContentObj).find('input');
					var hasSelectedFlag = false;
					var selectedPerCIds = '';
					var selectedNum = 0;
					$.each(perCInputArr, function(perCInputIndex, perCInputObj) {
						if($(perCInputObj).is(':checked')){
							selectedNum++;
							hasSelectedFlag = true;
							selectedPerCIds += $(perCInputObj).val() + ',';
						}
					});
					if(selectedPerCIds != ''){
						selectedPerCIds = selectedPerCIds.substring(0, selectedPerCIds.length - 1);
					}
					if(hasSelectedFlag) {
						var perPId = $(areaObj).prev().prev().val();
						// 创建DOM对象存放
						var submitInput = document.createElement('input');
						submitInput.type = 'hidden';
						submitInput.value = perPId + '#' + (selectedNum == perCInputArr.length) + '#' + selectedPerCIds;
						submitInput.name = 'groupPerSetValues';
						$(data.form).find('.formSubmitValues').find('.formPerValues').append(submitInput);
					}
				});
				var options = {
					type : 'post',
					success : function(data) {
						if (data == "ajaxSuccess") {
							parent.layer.msg('权限保存成功');
							parent.parent.loadMainWorkSpance("toPermissionSet");
							var index = parent.layer.getFrameIndex(window.name);
							parent.layer.close(index);
						} else {
							layer.msg('操作失败，请重试');
						}
					}
				};
				$("#groupPerSetForm").ajaxForm(options).submit();
			}
		});

		return false;
	});
	
	form.on('submit(userFormSubmit)', function(data) {
		// 获取选中项目，编辑权限字符串
		$(data.form).find('.formSubmitValues').find('.formPerValues').html('');
		var perPAreaArr = $(data.form).find('.perCSetArea');
		$.each(perPAreaArr, function(areaIndex, areaObj) {
			var perCSetContentObj = $(areaObj).find('.perCSetContent');
			var perCInputArr = $(perCSetContentObj).find('input');
			var hasSelectedFlag = false;
			var selectedPerCIds = '';
			var selectedNum = 0;
			$.each(perCInputArr, function(perCInputIndex, perCInputObj) {
				if($(perCInputObj).is(':checked')){
					selectedNum++;
					hasSelectedFlag = true;
					selectedPerCIds += $(perCInputObj).val() + ',';
				}
			});
			if(selectedPerCIds != ''){
				selectedPerCIds = selectedPerCIds.substring(0, selectedPerCIds.length - 1);
			}
			if(hasSelectedFlag) {
				var perPId = $(areaObj).prev().prev().val();
				// 创建DOM对象存放
				var submitInput = document.createElement('input');
				submitInput.type = 'hidden';
				submitInput.value = perPId + '#' + (selectedNum == perCInputArr.length) + '#' + selectedPerCIds;
				submitInput.name = 'userPerSetValues';
				$(data.form).find('.formSubmitValues').find('.formPerValues').append(submitInput);
			}
		});
		var options = {
			type : 'post',
			success : function(data) {
				if (data == "ajaxSuccess") {
					parent.layer.msg('权限保存成功');
					parent.parent.loadMainWorkSpance("toPermissionSet");
					var index = parent.layer.getFrameIndex(window.name);
					parent.layer.close(index);
				} else {
					layer.msg('操作失败，请重试');
				}
			}
		};
		$("#userPerSetForm").ajaxForm(options).submit();

		return false;
	});
	
	exports('perSet', function(type, thiz) {

	});
});

function getPerCDataForUserOrGroup(perPId, slipDisabledFlag, type, aboutId) {
	$.ajax({
		url : 'getPerCList.action',
		type : 'POST',
		cache : false,
		data : {
			perPId : perPId,
			slipDisabledFlag: slipDisabledFlag,
			type: type,
			aboutId: aboutId
		},
		dataType : "html",
		//成功后的操作
		success : function(perCData) {
			$('.perCArea_' + perPId).find('.perCSetAreaLoading').fadeOut(function(){
				if(perCData != ""){
					var pcdJson = JSON.parse(perCData)[perPId.trim()];
					var perCDataHtml = "";
					$.each(pcdJson[1], function(index, perCObj) {
						perCDataHtml += '<input class="percSetInputItem" type="checkbox" name="" value="' + perCObj[0] + '" ' +
							' lay-filter="perCItemSwitch" title="' + perCObj[1] + '"';
						if(perCObj[7]){
							perCDataHtml += ' checked /> ';
						}else{
							perCDataHtml += ' /> ';
						}
					});
					$('.perCArea_' + perPId).find('.perCSetContent').html(perCDataHtml);
					layui.form.render('checkbox');
				}else{
					$('.perCArea_' + perPId).find('.perCSetContent').html('<i class="layui-icon" style="margin-right: 14px;">&#xe629;</i>该父级权限下无子级权限数据，请联系管理员添加');
				}
			});
		}
	});
}
