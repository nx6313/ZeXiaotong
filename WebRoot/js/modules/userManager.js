layui.define([ 'layer', 'form' ], function(exports) {
	parent.closeLoading();
	var layer = layui.layer, form = layui.form;
	initPageScrollBar($('body'), 'inset');

	layui.use('element', function() {
		var element = layui.element;
	});

	exports('userManager', function(type, thiz) {
		if (type == 'addUser') {
			var aboutId = $('#selectTreeGroupId').val();
			var aboutData = $('#selectTreeGroupName').val();
			parent.layer.open({
				type : 2,
				title : '添加『' + aboutData + '』下的新用户',
				area : [ '640px', '460px' ],
				offset : '20%',
				shadeClose : true,
				anim : 2,
				closeBtn : 0,
				move : false,
				content : [ 'skipUserAdd?aboutId=' + aboutId, 'no' ]
			});
		} else if (type == 'clearUser') {
			var aboutId = $('#selectTreeGroupId').val();
			var aboutData = $('#selectTreeGroupName').val();
			parent.layer.msg('你确定要清除『' + aboutData
					+ '』下的所有用户吗？<br/><br/>注意：请谨慎操作', {
				time : 0,
				shade : 0.7,
				shadeClose : true,
				btn : [ '确定清除' ],
				yes : function(index) {
					parent.layer.close(index);
					$.ajax({
						type : "post",
						url : "deleteUser.action",
						data : {
							aboutId : aboutId,
							type : 'all'
						},
						dataType : "html",
						async : true,
						success : function(data) {
							layer.msg('清除成功', function() {
								parent.loadMainWorkSpance("toUserManager");
							});
						}
					});
				}
			});
		} else if (type == 'userSearch') {
			var searchVal = $(thiz).parent().find('#userSearchInput').val();
			if (searchVal) {
				searchUser('userSearch', null, searchVal);
			}
		}
	});
});

var zTreeObj;
var setting = {
	edit : {
		enable : true,
		showRemoveBtn : setRemoveBtn,
		showRenameBtn : false,
		removeTitle : "删除该用户",
		drag : {
			autoOpenTime : 100,
			prev : false,
			next : false,
			isCopy : false,
			inner : canInner
		}
	},
	callback : {
		beforeDrag : zTreeBeforeDrag,
		beforeDrop : zTreeBeforeDrop,
		beforeRemove : zTreeBeforeRemove,
		onRemove : zTreeOnRemove,
		onDragMove : zTreeOnDragMove,
		onDrop : zTreeOnDrop,
		onClick : zTreeOnClick
	}
};
$(document).ready(function() {
	zTreeObj = $.fn.zTree.init($("#userTree"), setting, userData);
});

// 设置父节点不显示删除按钮
function setRemoveBtn(treeId, treeNode) {
	if (treeNode.type == 'group') {
		return false;
	}
	return true;
}

function zTreeBeforeRemove(treeId, treeNode) {
	parent.layer.confirm('确定删除用户【' + treeNode.name + '】吗？', {
		title : '删除用户提示',
		btn : [ '确定删除', '取消删除' ]
	}, function(index) {
		deleteTreeNode(treeNode);
		parent.layer.close(index);
	}, function(index) {
		parent.layer.close(index);
	});
	return false;
}

function zTreeOnRemove(event, treeId, treeNode) {
	alert(treeNode.tId + ", " + treeNode.name);
}

// 设置是否可以拖拽到该节点内
function canInner(treeId, nodes, targetNode) {
	if (targetNode && targetNode.type == 'group') {
		return true;
	}
	return false;
}

function zTreeBeforeDrag(treeId, treeNodes) {
	if (treeNodes.length == 1 && treeNodes[0].type == 'user') {
		return true;
	}
	return false;
}

function zTreeBeforeDrop(treeId, treeNodes, targetNode, moveType) {
	if (!targetNode)
		return false;
	var treeNode = treeNodes[0];
	parent.layer.confirm('确定移动用户『' + treeNode.name + '』到组【' + targetNode.name
			+ '】中吗？', {
		title : '移动用户提示',
		btn : [ '确定移动', '取消移动' ]
	}, function(index) {
		optionTreeNode(treeNode, targetNode, moveType);
		parent.layer.close(index);
	}, function(index) {
		parent.layer.close(index);
	});
	return false;
}

// 确定拖拽
function optionTreeNode(treeNode, targetNode, moveType) {
	moveToTarget(targetNode, treeNode, 'inner');
	// var thisProjectId = treeNode.projectId;
	// var moveToSuperProjectId = targetNode.projectId;
	// $.ajax({
	// url : 'removeServerTreeNode.action',
	// type : 'post',
	// data : {
	// thisProjectId : thisProjectId,
	// moveToSuperProjectId : moveToSuperProjectId
	// },
	// dataType : 'html',
	// success : function(result) {
	// if (result == "success") {
	// parent.layer.msg('移动用户【' + treeNode.name + '】成功！');
	// moveToTarget(targetNode, treeNode, 'inner');
	// parent.layer.close();
	// } else {
	// parent.layer.msg('移动用户【' + treeNode.name + '】失败！');
	// }
	// }
	// });
}

function moveToTarget(targetNode, treeNode, moveType) {
	var beforeMoveChildCount = treeNode.getParentNode().children.length;
	if (beforeMoveChildCount == 1) {
		zTreeObj.expandNode(treeNode.getParentNode(), false);
	}
	zTreeObj.moveNode(targetNode, treeNode, moveType, false);
}

// 确定删除
function deleteTreeNode(treeNode) {
	$.ajax({
		type : "post",
		url : "deleteUser.action",
		data : {
			aboutId : treeNode.aboutId
		},
		dataType : "html",
		async : true,
		success : function(data) {
			layer.msg('用户删除成功', function() {
				parent.loadMainWorkSpance("toUserManager");
			});
		}
	});
}

// 拖拽用户节点到DOM，进行该用户信息查询
function zTreeOnDragMove(event, treeId, treeNodes) {
	if (event.target.id == 'userManagerExplain') {
		$(event.target).addClass('dragToBlockquoteCss');
	} else if (event.target.id == 'userSearchInput') {
		$(event.target).addClass('dragToInputCss');
	}
	if (event.target.id != 'userManagerExplain') {
		$('#userManagerExplain').removeClass('dragToBlockquoteCss');
	}
	if (event.target.id != 'userSearchInput') {
		$('#userSearchInput').removeClass('dragToInputCss');
	}
}

function zTreeOnDrop(event, treeId, treeNodes, targetNode, moveType) {
	if (event.target.id == 'userSearchInput') {
		$('#userSearchInput').val(
				'【' + treeNodes[0].getParentNode().name + '】'
						+ treeNodes[0].name);
	}
	if (event.target.id == 'userManagerExplain'
			|| event.target.id == 'userSearchInput') {
		$('#userManagerExplain').removeClass('dragToBlockquoteCss');
		$('#userSearchInput').removeClass('dragToInputCss');
		// 进行查询操作
		searchUser('user', null, treeNodes[0].aboutId);
	}
}

function zTreeOnClick(event, treeId, treeNode) {
	searchUser(treeNode.type, null, treeNode.aboutId, treeNode.name, treeNode.aboutId);
}

function searchUser(type, cur, aboutId, aboutGroupName, aboutGroupId) {
	parent.showLoading();
	$
			.ajax({
				type : "post",
				url : "searchUserInfoOrList.action",
				data : {
					type : type,
					aboutId : aboutId,
					currentPage : cur
				},
				dataType : "html",
				async : true,
				success : function(data) {
					parent.closeLoading();
					if (type == 'group') {
						if(aboutGroupId == 10){
							$('.doAddClearLegWarp').hide();
						}else{
							$('.doAddClearLegWarp').show();
						}
						$('#selectTreeGroupId').val(aboutGroupId);
						$('#selectTreeGroupName').val(aboutGroupName);
						$('#userManagerAddBtnSpan').text(aboutGroupName);
						$('#userManagerClearBtnSpan').text(aboutGroupName);
						$('#userSearchInput').val("");
						$('#userManagerInfoWarp').hide();
						$('#userManagerListWarp').fadeIn();
						if (data) {
							$("#userManagerMainWarp").html("");
							var userDataJson = JSON.parse(data);
							if (!userDataJson.superLoginFlag) {
								$('.clearGroupUserBtn').hide();
							}
							layui.use([ 'laypage' ], function() {
								var laypage = layui.laypage;
								laypage.render({
									elem : 'pageElement',
									curr : userDataJson.currentPage,
									count : Number(userDataJson.dataCount),
									jump: function(obj, first) {
										if(!first){
											searchUser(type, obj.curr, aboutId, aboutGroupName, aboutGroupId);
										}
									}
								});
							});
							if (userDataJson.child) {
								$.each(userDataJson.child,
										function(index, obj) {
											var dataTrHtml = "<tr>";
											dataTrHtml += "<td>" + obj.userName
													+ "&nbsp;</td>"
											if (obj.type == 0) {
												dataTrHtml += "<td>超级管理员</td>"
											} else if (obj.type == 1) {
												dataTrHtml += "<td>普通管理员</td>"
											} else if (obj.type == 2) {
												dataTrHtml += "<td>代理用户</td>"
											} else if (obj.type == 3) {
												dataTrHtml += "<td>学生会员用户</td>"
											} else if (obj.type == 4) {
												dataTrHtml += "<td>专家用户</td>"
											}
											dataTrHtml += "<td>" + obj.userName
													+ "&nbsp;</td>"
											dataTrHtml += "</tr>"
											$("#userManagerMainWarp").append(
													dataTrHtml);
										});
							}
							// 补全单元格
							var currentDataLength = 0;
							if (userDataJson.child) {
								currentDataLength = userDataJson.child.length;
							}
							var replenishCount = Number(userDataJson.pageSize)
									- currentDataLength;
							var trHtml = getReplenishTr(replenishCount, 3);
							$("#userManagerMainWarp").append(trHtml);
						}
					} else {
						$('#userManagerListWarp').hide();
						$('#userManagerInfoWarp').fadeIn();
						if (data) {
							$('#userManagerInfoContent').html("");
							var userDataJson = JSON.parse(data);
							if (userDataJson.child) {
								$
										.each(
												userDataJson.child,
												function(index, obj) {
													var dataTrHtml = "<tr>";
													dataTrHtml += "<th class=\"tableLeftTh\">用户名（/ 卡号）</th>"
													dataTrHtml += "<td>"
															+ obj.userName
															+ "&nbsp;</td>"
													dataTrHtml += "<th class=\"tableLeftTh\">用户类型</th>"
													if (obj.type == 0) {
														dataTrHtml += "<td>超级管理员</td>"
													} else if (obj.type == 1) {
														dataTrHtml += "<td>普通管理员</td>"
													} else if (obj.type == 2) {
														dataTrHtml += "<td>代理用户</td>"
													} else if (obj.type == 3) {
														dataTrHtml += "<td>学生会员用户</td>"
													} else if (obj.type == 4) {
														dataTrHtml += "<td>专家用户</td>"
													}
													dataTrHtml += "</tr>"
													$("#userManagerInfoContent")
															.append(dataTrHtml);
												});
							}
						}
					}
				}
			});
}