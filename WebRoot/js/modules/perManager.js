layui.define([ 'layer', 'form' ], function(exports) {
	parent.closeLoading();
	var layer = layui.layer, form = layui.form;
	initPageScrollBar($('body'), 'inset');
	
	layui.use('element', function() {
		var element = layui.element;
		// AJAX获取父级权限之下子级权限数据
		getPerPAboutCData();
		element.on('collapse(perAllWarp)', function(data) {
			if (data.show) {
				var isLoadingFlag = $(data.content).find('div.perPAboutCDataMain').is(':hidden');
				if (!isLoadingFlag) {
					var $container = $(data.content).find('div.perPAboutCDataMain');
					$container.masonry({
						itemSelector : '.permissionCeilDiv'
					});
				}
			}
		});
	});
	
	exports('perManager', function(type, thiz, perTipData) {
		if (type == 'addPerP') {
			parent.layer.open({
				type : 2,
				title : '添加新的父级权限模块',
				area : [ '640px', '460px' ],
				offset : '20%',
				shadeClose : true,
				anim : 2,
				closeBtn : 0,
				move : false,
				content : ['skipPermissionWrite?type=1', 'no' ]
			});
		} else if (type == 'addPerC') {
			parent.layer.open({
				type : 2,
				title : '添加【' + thiz + '】的子级权限',
				area : [ '640px', '460px' ],
				offset : '20%',
				shadeClose : true,
				anim : 2,
				closeBtn : 0,
				move : false,
				content : ['skipPermissionWrite?type=2&perPId=' + perTipData, 'no' ]
			});
			layui.stope();
		} else if (type == 'updateP') {
			var nameCanUpdateFlag = '1';
			if (thiz == '系统设置') {
				nameCanUpdateFlag = '0';
			}
			parent.layer.open({
				type : 2,
				title : '修改父级权限模块【' + thiz + '】的信息',
				area : [ '640px', '460px' ],
				offset : '20%',
				shadeClose : true,
				anim : 2,
				closeBtn : 0,
				move : false,
				content : ['skipPermissionWrite?type=3&perPId=' + perTipData + '&nameCanUpdateFlag=' + nameCanUpdateFlag, 'no' ]
			});
			layui.stope();
		} else if (type == 'updateC') {
			var nameCanUpdateFlag = '1';
			if (thiz == '系统设置 - 导航管理' || thiz == '系统设置 - 用户权限设置') {
				nameCanUpdateFlag = '0';
			}
			parent.layer.open({
				type : 2,
				title : '修改子级权限【' + thiz + '】的信息',
				area : [ '640px', '460px' ],
				offset : '20%',
				shadeClose : true,
				anim : 2,
				closeBtn : 0,
				move : false,
				content : ['skipPermissionWrite?type=4&perCId=' + perTipData + '&nameCanUpdateFlag=' + nameCanUpdateFlag, 'no' ]
			});
			layui.stope();
		} else if (type == 'deleteP') {
			if (thiz == '系统设置') {
				parent.layer.msg('该权限为系统内置权限，不可删除');
			} else {
				parent.layer.msg('你确定要删除父级权限<br/>【' + thiz
						+ '】吗？<br/><br/>注意：如果父级权限模块下仍然存在子级权限，则将会直接删除其下的所有子级权限，'
						+ '为了数据避免丢失，你可以选择修改删除其下的子级权限，或者修改父级权限的信息<br/><br/>如果需要删除该父级权限，点击下面删除按钮即可', {
					time : 0,
					shade : 0.7,
					shadeClose : true,
					btn : [ '删除' ],
					yes : function(index) {
						parent.layer.close(index);
						$.ajax({
							type : "post",
							url : "permissionDelete.action?type=1&id=" + perTipData,
							dataType : "html",
							async : true,
							success : function(data) {
								layer.msg('删除成功', function() {
									parent.loadMainWorkSpance("toPermissionManager");
								});
							}
						});
					}
				});
			}
			layui.stope();
		} else if (type == 'deleteC') {
			if (thiz == '系统设置 - 导航模块管理' || thiz == '系统设置 - 用户权限设置') {
				parent.layer.msg('该权限为系统内置权限，不可删除');
			} else {
				parent.layer.msg('你确定要删除子级权限<br/>【' + thiz + '】吗？', {
					time : 0,
					shade : 0.7,
					shadeClose : true,
					btn : [ '删除' ],
					yes : function(index) {
						parent.layer.close(index);
						$.ajax({
							type : "post",
							url : "permissionDelete.action?type=2&id=" + perTipData,
							dataType : "html",
							async : true,
							success : function(data) {
								layer.msg('删除成功', function() {
									parent.loadMainWorkSpance("toPermissionManager");
								});
							}
						});
					}
				});
			}
		} else if (type == 'perUp') {
			parent.layer.msg('你确定要将' + thiz + '上架吗？', {
				time : 0,
				shade : 0.7,
				shadeClose : true,
				btn : [ '上架' ],
				yes : function(index) {
					parent.layer.close(index);
					$.ajax({
						type : "post",
						url : "permissionUpDown.action?type=1&id=" + perTipData,
						dataType : "html",
						async : true,
						success : function(data) {
							layer.msg('上架成功', function() {
								parent.loadMainWorkSpance("toPermissionManager");
							});
						}
					});
				}
			});
			layui.stope();
		} else if (type == 'perDown') {
			parent.layer.msg('你确定要将' + thiz + '下架吗？', {
				time : 0,
				shade : 0.7,
				shadeClose : true,
				btn : [ '下架' ],
				yes : function(index) {
					parent.layer.close(index);
					$.ajax({
						type : "post",
						url : "permissionUpDown.action?type=2&id=" + perTipData,
						dataType : "html",
						async : true,
						success : function(data) {
							layer.msg('下架成功', function() {
								parent.loadMainWorkSpance("toPermissionManager");
							});
						}
					});
				}
			});
			layui.stope();
		} else if (type == 'ref') {
			parent.loadMainWorkSpance("toPermissionManager");
		} else if (type == 'dragItem') {
			layer.msg('鼠标按住上下拖动进行排序');
			layui.stope();
		}
	});
});

function getPerPAboutCData() {
	var perPIds = perPIdList.substr(1, perPIdList.length - 2).split(',');
	$.each(perPIds, function(index, id) {
		// setTimeout(function(){
		getPerPAboutCDataByPId(id, index);
		// }, (index - 1) * 400);
	});
}

function getPerPAboutCDataByPId(pId, index) {
	$.ajax({
		type : "post",
		url : "getPerCList.action",
		data : {
			perPId : pId,
			slipDisabledFlag : false
		},
		dataType : "html",
		async : true,
		success : function(data) {
			var perCDetailDataHtml = "";
			if (data != "") {
				var pcdJson = JSON.parse(data)[pId.trim()];
				var perCDownCount = pcdJson[0];
				$('span.perAboutCSizeSpan_' + pId.trim()).find('.perPAboutCSizeLoading').fadeOut(function() {
					if (perCDownCount > 0) {
						$('span.perAboutCSizeSpan_' + pId.trim()).append("- ( 包含 " + pcdJson[1].length
								+ " 个子权限，其中有 " + perCDownCount + " 个已下架 )");
						} else {
							$('span.perAboutCSizeSpan_' + pId.trim()).append("- ( 包含 " + pcdJson[1].length + " 个子权限 )");
						}
					});
				$.each(pcdJson[1], function(perCIndex, perCData) {
					perCDetailDataHtml += '<div class="perCeilChild permissionCeilDiv">'
													+ '<span>'
													+ perCData[1]
													+ '</span>'
													+ '<div class="layui-btn-group">';
					if (perCData[3] == 0) {
						perCDetailDataHtml += '<button class="layui-btn layui-btn-mini" '
														+ 'style="background: #D76F37;" disabled="disabled">'
														+ '<i class="layui-icon">&#xe617;</i> 父级下架'
														+ '</button>';
					} else {
						if (perCData[4] == 0) {
							perCDetailDataHtml += '<button class="layui-btn layui-btn-mini" '
															+ 'style="background: #CC2028;" '
															+ ' onclick="layui.perManager(\'perUp\', \'子级权限&lt;br/&gt;【'
															+ perCData[5]
															+ ' - '
															+ perCData[1]
															+ '】\', \'c_'
															+ perCData[0]
															+ '\')"> '
															+ '<i class="layui-icon">&#x1007;</i> <i class="layui-icon">&#xe609;</i> 上架 '
															+ '</button>';
						} else {
							perCDetailDataHtml += '<button class="layui-btn layui-btn-mini" '
															+ ' style="background: #3DA97A;" '
															+ ' onclick="layui.perManager(\'perDown\', \'子级权限&lt;br/&gt;【'
															+ perCData[5]
															+ ' - '
															+ perCData[1]
															+ '】\', \'c_'
															+ perCData[0]
															+ '\')"> '
															+ '<i class="layui-icon">&#xe616;</i> <i class="layui-icon">&#xe64d;</i> 下架 '
															+ '</button>';
						}
					}
					perCDetailDataHtml += '<button class="layui-btn layui-btn-mini" '
													+ ' onclick="layui.perManager(\'updateC\', \''
													+ perCData[5]
													+ ' - '
													+ perCData[1]
													+ '\', \''
													+ perCData[0]
													+ '\')"> '
													+ ' <i class="layui-icon">&#xe642;</i> '
													+ '</button> '
													+ '<button class="layui-btn layui-btn-mini" '
													+ ' onclick="layui.perManager(\'deleteC\', \''
													+ perCData[5]
													+ ' - '
													+ perCData[1]
													+ '\', \''
													+ perCData[0]
													+ '\')"> '
													+ '<i class="layui-icon">&#xe640;</i> '
													+ '</button> ' + '</div>';
					if ((perCData[2] && perCData[2] != 'null' && perCData[2] != 'NULL') || (perCData[6]
															&& perCData[6] != 'null' && perCData[6] != 'NULL')) {
						perCDetailDataHtml += '<div style="margin-top: 10px;"></div>';
					}
					if (perCData[2] && perCData[2] != 'null' && perCData[2] != 'NULL') {
						perCDetailDataHtml += '<div>' + perCData[2] + '</div>';
					}
					if (perCData[6] && perCData[6] != 'null' && perCData[6] != 'NULL') {
						perCDetailDataHtml += '<div>权限链接：' + perCData[6] + '</div>';
					}
					perCDetailDataHtml += '</div>';
				});
			} else {
				$('span.perAboutCSizeSpan_' + pId.trim()).find('.perPAboutCSizeLoading').fadeOut(function() {
					$('span.perAboutCSizeSpan_' + pId.trim()).append("- ( 不包含子权限，请添加 )");
				});
				perCDetailDataHtml = '<div class="perCeilChildNo permissionCeilDiv">'
								+ '<blockquote class="layui-elem-quote perCNoneData">该父级权限下无子级权限，点击父级权限名后的添加按钮进行添加</blockquote>'
								+ '</div>';
			}
			$('div.perAboutCDataDiv_' + pId.trim()).find('.perPAboutCDataLoading').fadeOut(function() {
				$('div.perAboutCDataDiv_' + pId.trim()).find('.perPAboutCDataMain').html(perCDetailDataHtml);
				$('div.perAboutCDataDiv_' + pId.trim()).find('.perPAboutCDataMain').fadeIn();
				var element = layui.element;
				element.init();
			});
		}
	});
}

Sortable.create(document.getElementById('perMainDataElem'), {
	handle : '.drag-handle',
	animation : 150,
	onEnd : function(evt) {
		if (evt.oldIndex != evt.newIndex) {
			parent.showLoading();
			$.ajax({
				type : "post",
				url : "sortPermission.action",
				data : {
					from : evt.oldIndex,
					to : evt.newIndex
				},
				dataType : "html",
				async : true,
				success : function(data) {
					parent.closeLoading();
				}
			});
		}
	}
});