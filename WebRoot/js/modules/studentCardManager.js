layui.define([ 'layer', 'form' ], function(exports) {
	parent.closeLoading();
	var layer = layui.layer, form = layui.form;
	initPageScrollBar($('body'), 'inset');
	
	initPageData();

	layui.use('element', function() {
		var element = layui.element;
	});

	layui.use('laypage', function() {
		var laypage = layui.laypage;
		laypage.render({
			elem : 'studentCardInfoPage',
			count : Number(allStudentCardNum),
			jump : function(obj, first) {
				getStudentCardPageData(first, agencyId, '-1', obj.curr);
			}
		});
		laypage.render({
			elem : 'studentCardOutInfoPage',
			count : Number(saleStudentCardNum),
			jump : function(obj, first) {
				getStudentCardPageData(first, agencyId, '1,2', obj.curr);
			}
		});
		laypage.render({
			elem : 'studentCardUnOutInfoPage',
			count : Number(unsaleStudentCardNum),
			jump : function(obj, first) {
				getStudentCardPageData(first, agencyId, '0', obj.curr);
			}
		});
	});

	form.verify({
		studentCardNumPass : function(value) {
			var reg = new RegExp("^[0-9]*$");
			if (!value) {
				return '生成卡数量不能为空';
			} else if (!reg.test(value)) {
				return '生成卡数量只能是整数';
			} else if (value == 0) {
				return '生成卡数量需要大于0';
			}
		}
	});

	form.on('submit(studentCardForm)', function(data) {
		var options = {
			type : 'post',
			success : function(data) {
				if (data == "ajaxSuccess") {
					parent.layer.msg('操作成功');
					parent.parent.loadMainWorkSpance("toStudentCardManager");
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

	exports('studentCardManager', function(type, thiz) {
		if (type == 'managerClickDl') {
			// 先清除所有代理按钮的选中样式
			var agencyArr = $('.agencyListWrap').find('button.layui-btn');
			$.each(agencyArr, function(index, agencyObj) {
				$(agencyObj).addClass('layui-btn-primary');
			});
			// 选中样式
			$(thiz).removeClass('layui-btn-primary');
			// 重置数据
			$('#studentCardAllTbady').html("");
			$('#studentCardSaleTbady').html("");
			$('#studentCardUnsaleTbady').html("");
			// 请求服务器，获取该代理商的数据
			parent.showLoading();
			$.ajax({
				type : "post",
				url : "getStudentCardManagerData.action",
				data : {
					agencyId : $(thiz).val()
				},
				dataType : "html",
				async : true,
				success : function(data) {
					parent.closeLoading();
					var resultDataJson = JSON.parse(data);
					for ( var prop in resultDataJson) {
						var agencyIdNew = prop;
						var stadentCardDataNew = resultDataJson[prop];
						$("#form").resetForm();
						$('#agencyIdInput').attr('value', agencyIdNew);
						$('#allCardCountSpan').text(stadentCardDataNew[0]);
						$('#saleCardCountSpan').text(stadentCardDataNew[1]);
						$('#unsaleCardCountSpan').text(stadentCardDataNew[2]);
						$('#dongCardCountSpan').text(stadentCardDataNew[3]);
						layui.use('element', function() {
							var element = layui.element;
							element.tabChange('studentCardManagerTabFilter',
									'allCardTab');
						});
						layui.use('laypage', function() {
							var laypage = layui.laypage;
							laypage.render({
								elem : 'studentCardInfoPage',
								count : Number(stadentCardDataNew[0]),
								jump : function(obj, first) {
									getStudentCardPageData(first, agencyIdNew,
											'-1', obj.curr);
								}
							});
							laypage.render({
								elem : 'studentCardOutInfoPage',
								count : Number(stadentCardDataNew[1]),
								jump : function(obj, first) {
									getStudentCardPageData(first, agencyIdNew,
											'1,2', obj.curr);
								}
							});
							laypage.render({
								elem : 'studentCardUnOutInfoPage',
								count : Number(stadentCardDataNew[2]),
								jump : function(obj, first) {
									getStudentCardPageData(first, agencyIdNew,
											'0', obj.curr);
								}
							});
						});
					}
				}
			});
		} else if (type == 'userSearch') {
			var searchUserVal = $('#userSearchInput').val();
			if (searchUserVal) {
				searchUser(searchUserVal);
			}
		}
	});
});

function initPageData() {
	if(seeStudentCardManagerUserType == 'manager'){
		if(!managerSeeAgencyList){
			$('div.studentCardManagerMainWarp').hide();
			$('#managerSeeNoAgency').html("<blockquote class='layui-elem-quote'>没有代理用户，请先添加代理</blockquote>");
			$('#managerSeeNoAgency').show();
		}else{
			$('div.studentCardManagerMainWarp').show();
		}
	}else{
		$('div.studentCardManagerMainWarp').show();
	}
}

function getStudentCardPageData(isFirst, agencyId, type, pageIndex) {
	parent.showLoading();
	$.ajax({
		type : "post",
		url : "getStudentCardPageData.action",
		data : {
			agencyId : agencyId,
			type : type,
			pageIndex : pageIndex
		},
		dataType : "html",
		async : true,
		success : function(data) {
			parent.closeLoading();
			var jsonData = JSON.parse(data);
			for ( var prod in jsonData) {
				var cardType = prod.split('|')[0];
				var currentPage = prod.split('|')[1];
				var cardData = jsonData[prod];
				var cardContentHtml = getCardContentHtml(cardData);
				if (cardType == 'all') {
					$('#studentCardAllTbady').html(cardContentHtml);
				} else if (cardType == 'sale') {
					$('#studentCardSaleTbady').html(cardContentHtml);
				} else if (cardType == 'unsale') {
					$('#studentCardUnsaleTbady').html(cardContentHtml);
				}
			}
		}
	});
}

function getCardContentHtml(cardData) {
	var cardContentHtml = "";
	$
			.each(
					cardData,
					function(index, obj) {
						cardContentHtml += "<tr><td>"
								+ (index + 1)
								+ "</td>"
								+ "<td>"
								+ obj.cardNum
								+ "</td>"
								+ "<td>"
								+ new Date(obj.createDate)
										.format("yyyy-MM-dd hh:mm:ss")
								+ "</td>" + "<td>";
						if (obj.cardStatus == 0) {
							cardContentHtml += "<button class='layui-btn layui-btn layui-btn-mini'>"
									+ "<i class='layui-icon'>&#xe63f;</i> 未出售";
						} else if (obj.cardStatus == 1) {
							cardContentHtml += "<button class='layui-btn layui-btn layui-btn-mini' style='background: #CC4024;'>"
									+ "<i class='layui-icon'>&#xe643;</i> 已使用";
						} else if (obj.cardStatus == 2) {
							cardContentHtml += "<button class='layui-btn layui-btn layui-btn-mini' style='background: #CC1A9C;'>"
									+ "<i class='layui-icon'>&#xe64d;</i> 冻结中";
						}
						cardContentHtml += "</button>"
								+ "</td>"
								+ "<td>"
								+ (obj.userName ? obj.userName : "")
								+ "&nbsp;</td>"
								+ "<td>"
								+ (obj.userName ? obj.balance : "")
								+ "&nbsp;</td>"
								+ "<td>"
								+ (obj.userName ? (getDateTimeDiff(
										obj.cardActiveTime, null)) : "")
								+ "&nbsp;</td>";
						if (obj.cardStatus == 0) {
							cardContentHtml += "<td>"
									+ "<span class='layui-btn-group'>"
									+ "<button class='layui-btn layui-btn-small' "
									+ "style='background: #1A85A0;' "
									+ "onclick=\"saleStudentCardOut('"
									+ obj.cardNum + "', '" + obj.passWord
									+ "');\">"
									+ "<i class='layui-icon'>&#xe639;</i> 出卡 "
									+ "</button>" + "</span>" + "</td>";
						} else if (obj.cardStatus == 1) {
							cardContentHtml += "<td>&nbsp;</td>";
						} else if (obj.cardStatus == 2) {
							cardContentHtml += "<td>&nbsp;</td>";
						}
						cardContentHtml += "</tr>";
					});
	return cardContentHtml;
}

function searchUser(searchUserVal) {
	parent.showLoading();
	$
			.ajax({
				type : "post",
				url : "searchStudentCardData.action",
				data : {
					searchVal : searchUserVal.trim()
				},
				dataType : "html",
				async : true,
				success : function(data) {
					parent.closeLoading();
					var searchMainContentHtml = "";
					var searchGetContentHtml = "<table id='studentCardSearchTb' class='layui-table'>"
							+ "<thead>"
							+ "<tr>"
							+ "<th width='80'>序号</th>"
							+ "<th width='200'>卡号</th>"
							+ "<th width='170'>生成卡时间</th>"
							+ "<th width='80'>卡状态</th>"
							+ "<th width='180'>卡所属用户</th>"
							+ "<th width='140'>当前卡余额</th>"
							+ "<th width='160'>已被使用时间</th>"
							+ "<th>操作</th>"
							+ "</tr>" + "</thead>";
					if (data != 'ajaxNone') {
						var jsonData = JSON.parse(data);
						$
								.each(
										jsonData,
										function(searchIndex, searchObj) {
											console.log(searchObj);
											searchMainContentHtml += "<tr><td>"
													+ (searchIndex + 1)
													+ "</td>"
													+ "<td>"
													+ searchObj.cardNum
													+ "</td>"
													+ "<td>"
													+ new Date(
															searchObj.createDate)
															.format("yyyy-MM-dd hh:mm:ss")
													+ "</td>" + "<td>";
											if (searchObj.cardStatus == 0) {
												searchMainContentHtml += "<button class='layui-btn layui-btn layui-btn-mini'>"
														+ "<i class='layui-icon'>&#xe63f;</i> 未出售";
											} else if (searchObj.cardStatus == 1) {
												searchMainContentHtml += "<button class='layui-btn layui-btn layui-btn-mini' style='background: #CC4024;'>"
														+ "<i class='layui-icon'>&#xe643;</i> 已使用";
											} else if (searchObj.cardStatus == 2) {
												searchMainContentHtml += "<button class='layui-btn layui-btn layui-btn-mini' style='background: #CC1A9C;'>"
														+ "<i class='layui-icon'>&#xe64d;</i> 冻结中";
											}
											searchMainContentHtml += "</button>"
													+ "</td>"
													+ "<td>"
													+ (searchObj.userName ? searchObj.userName
															: "")
													+ "&nbsp;</td>"
													+ "<td>"
													+ (searchObj.userName ? searchObj.balance
															: "")
													+ "&nbsp;</td>"
													+ "<td>"
													+ (searchObj.userName ? (getDateTimeDiff(
															searchObj.cardActiveTime,
															null))
															: "")
													+ "&nbsp;</td><td>&nbsp;</td></tr>";
										});
						searchGetContentHtml += "<tbody>"
								+ searchMainContentHtml + "</tbody>"
								+ "</table>";
					} else {
						searchMainContentHtml = "<tr>"
								+ "<td colspan='8'>"
								+ "<blockquote class='layui-elem-quote'>查询无结果</blockquote>"
								+ "</td>" + "</tr>";
						searchGetContentHtml += "<tbody>"
								+ searchMainContentHtml + "</tbody>"
								+ "</table>";
					}
					parent.layer.open({
						type : 1,
						title : false,
						closeBtn : 0,
						shadeClose : true,
						skin : 'searchPop',
						anim : 5,
						move : false,
						content : searchGetContentHtml
					});
				}
			});
}

function saleStudentCardOut(cardNum, cardPwd) {
	var saleOutCardHtml = "<span style='padding: 8px; background: rgba(250, 250, 250, .8);'>卡号："
			+ cardNum
			+ "</span><br/>"
			+ "<span style='padding: 8px; background: rgba(250, 250, 250, .8);'>密码："
			+ cardPwd
			+ "</span>"
			+ "<p style='margin-top: 40px; color: #F23434; padding: 4px; background: rgba(250, 250, 250, .8);'>"
			+ "温馨提示：请牢记您的密码或登陆后进行密码修改</p>";
	parent.layer.open({
		type : 1,
		title : false,
		closeBtn : 0,
		shadeClose : true,
		skin : 'saleStudentCardPop',
		anim : 2,
		move : false,
		content : saleOutCardHtml
	});
}
