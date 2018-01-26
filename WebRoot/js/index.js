function exitLogin() {
	layer.msg('你确定要退出当前登录？', {
		time : 0,
		shade : 0.7,
		shadeClose : true,
		btn : [ '退出' ],
		yes : function(index) {
			layer.close(index);
			// 执行退出
			$.ajax({
				type : "post",
				url : "exitLogin.action",
				dataType : "html",
				async : true,
				success : function(data) {
					window.top.location = 'skipLogin';
				}
			});
		}
	});
}

// 超级管理员登陆选择显示模式
function showSuperModelSelect() {
	var modeSelectHtml = "<h4>请选择一种权限模式进行模拟查看：</h4><br/>"
			+ "<div style=\"text-align: left;\">"
			+ "<label style=\"cursor: pointer;\"><input style=\"margin: 0 5px;\" name=\"mode\" type=\"radio\" value=\"1\" ";
	if (superModelSelect == 1) {
		modeSelectHtml += "checked=\"checked\"";
	}
	modeSelectHtml += "/>以所有权限查看</label><br/>"
			+ "<label style=\"cursor: pointer;\"><input style=\"margin: 0 5px;\" name=\"mode\" type=\"radio\" value=\"3\" ";
	if (superModelSelect == 3) {
		modeSelectHtml += "checked=\"checked\"";
	}
	modeSelectHtml += "/>以代理商权限查看</label><br/>"
			+ "<label style=\"cursor: pointer;\"><input style=\"margin: 0 5px;\" name=\"mode\" type=\"radio\" value=\"4\" ";
	if (superModelSelect == 4) {
		modeSelectHtml += "checked=\"checked\"";
	}
	modeSelectHtml += "/>以专家权限查看</label><br/>"
			+ "<label style=\"cursor: pointer;\"><input style=\"margin: 0 5px;\" name=\"mode\" type=\"radio\" value=\"5\" ";
	if (superModelSelect == 5) {
		modeSelectHtml += "checked=\"checked\"";
	}
	modeSelectHtml += "/>以会员权限查看</label><br/></div>";
	var superManagerLimitsProgressHtml = '<div>系统必须权限当前完善度：</div><div class="layui-progress layui-progress-big" lay-filter="limitProgress" lay-showPercent="yes">'
			+ '<div class="layui-progress-bar" lay-percent="0%"></div>'
			+ '</div><div style="margin-top: 10px;"><a id="limitFillA" class="btnSpan" href="javascript:doLimitsPerfect();">必要权限补全</a></div>';
	var superModeManagerHtml = '<div class="cantSelected"><div class="layui-tab layui-tab-brief" lay-filter="superManagerTag">'
			+ '<ul class="layui-tab-title">'
			+ '<li class="layui-this">模拟用户查看</li>'
			+ '<li>生成程序必须的权限模块</li>'
			+ '</ul>'
			+ '<div class="layui-tab-content">'
			+ '<div class="layui-tab-item layui-show">'
			+ modeSelectHtml
			+ '</div>'
			+ '<div class="layui-tab-item">'
			+ superManagerLimitsProgressHtml
			+ '</div>'
			+ '</div>'
			+ '</div></div>';
	layer.msg(superModeManagerHtml, {
		time : 0,
		shade : 0.7,
		area : '800px',
		shadeClose : true,
		btn : false,
		success : function(layero, index) {
			layui.use('element', function() {
				var elem = layui.element;
				elem.init();
				elem.on('tab(superManagerTag)', function(data) {
					if (data.index == 1) {
						// 生成程序必须的权限模块
						// 先清空
						elem.progress('limitProgress', '0%');
						// 再重新赋值
						setTimeout(function() {
							var hasLimiteProgessVal = hasLimitsCount
									/ allLimitsCount * 100;
							if (hasLimiteProgessVal == 100) {
								$('#limitFillA').hide();
							} else {
								$('#limitFillA').show();
							}
							elem.progress('limitProgress', hasLimiteProgessVal
									+ '%');
						}, 0);
					}
				});
			});
		}
	});
}

// 重新加载当行区域（超级管理员调用此方法）
function reloadNavigationArea(selectMode) {
	if (superModelSelect != selectMode) {
		return loadNavigationMenuData(true);
		if (selectMode == 1) { // 以总后台权限查看
			$('#workspace').attr("src", "http://www.baidu.com");
			$('#superChangeMode').html("");
		} else if (selectMode == 3) { // 以代理商权限查看
			$('#workspace').attr("src", "http://www.taobao.com");
			$('#superChangeMode').html("已切换为代理商方式，");
		} else if (selectMode == 4) { // 以专家权限查看
			$('#workspace').attr("src", "https://www.sogou.com/");
			$('#superChangeMode').html("已切换为专家方式，");
		} else if (selectMode == 5) { // 以会员权限查看
			$('#workspace').attr("src", "http://xs.sogou.com/");
			$('#superChangeMode').html("已切换为会员方式，");
		}
		superModelSelect = selectMode;
	}
}

function loadNavigationMenuData(isSuperFlag) {
	var json = null;
	// json = $.parseJSON(getIndexMenuJson(superModelSelect));
	if (navigationJson && navigationJson != 'empty') {
		if (isSuperFlag) {
			// 超级管理员登陆
			json = $.parseJSON(navigationJson);
		} else {
			// 非超级管理员登陆
			json = $.parseJSON(navigationJson);
		}
	} else {
		if (loginUserType == 10) {
			if (!loginUserName) {
				$("#tTree").html(
						"<li><a onclick=\"showCardJiActive('"
								+ loginUserCardNum
								+ "');\">您的卡片尚未激活，请先激活后使用</a></li>");
				$('#workspace').attr("src",
						"toCommPage.action?page=noPermission&type=cardActive");
			} else {
				$("#tTree").html("<li><a>您没有任何权限，请先联系管理员添加</a></li>");
				$('#workspace').attr("src",
						"toCommPage.action?page=noPermission");
			}
		} else {
			$("#tTree").html("<li><a>您没有任何权限，请先联系管理员添加</a></li>");
			$('#workspace').attr("src", "toCommPage.action?page=noPermission");
		}
		return false;
	}
	var h = '', hh = '', hhh = '', html = '';
	// 如果菜单项大于8个，则其他的通过更多显示
	var moreArr = [];
	$(json)
			.each(
					function(index, i) {
						if (index < 8) {
							var url = i.url;
							if (url) {
								h += '<li><a href="' + i.url + '"  target="'
										+ i.target + '">' + i.name + ' </a>';
							} else {
								h += '<li><a>' + i.name + ' </a>';
							}
							var arr = i.arr;
							if (arr) {
								if (arr.length > 0) {
									hh = '<ul class="dropdown-menu" style="background: #414141; border: 1px solid #2B2B2B; border-radius: 5px; box-shadow: 1px 2px 8px rgba(0, 0, 0, 0.8);">';
									for (var j = 0; j < arr.length; j++) {
										hh += '<li onclick=\'hideP(this)\'><a href="'
												+ arr[j]['url']
												+ '" target="'
												+ arr[j]['target']
												+ '">'
												+ arr[j]['name'] + '</a></li>';
									}
									hh += '</ul>';
								}
							}
							h += hh + '</li>';
							html += h;
							hh = '', h = '';
						} else {
							moreArr.push(i);
						}
					});
	if (moreArr.length > 0) {
		h += '<li><a href="javascript:void(0);">更多&nbsp;<i class="layui-icon">&#xe625;</i></a>';
		hh = '<ul class="dropdown-menu" style="background: #414141; border: 1px solid #2B2B2B; border-radius: 5px; box-shadow: 1px 2px 8px rgba(0, 0, 0, 0.8);">';
		for (var j = 0; j < moreArr.length; j++) {
			hh += '<li><a href="javascript:void(0);"><i class="layui-icon">&#xe62e;</i>&nbsp;&nbsp;'
					+ moreArr[j].name + '</a>';
			var arr = moreArr[j].arr;
			if (arr) {
				if (arr.length > 0) {
					hhh = '<ul class="dropdown-menu" style="background: #414141; border: 1px solid #2B2B2B; border-radius: 5px; box-shadow: 1px 2px 8px rgba(0, 0, 0, 0.8);">';
					for (var m = 0; m < arr.length; m++) {
						hhh += '<li onclick=\'hideP(this)\'><a href="'
								+ arr[m]['url'] + '" target="'
								+ arr[m]['target'] + '">' + arr[m]['name']
								+ '</a></li>';
					}
					hhh += '</ul>';
				}
			}
			hh += hhh + '</li>';
		}
		hh += '</ul>';
		h += hh + '</li>';
		html += h;
		hhh = '', hh = '', h = '';
	}
	$("#tTree").html(html);

	var topbar;
	$(function() {
		topbar = $('#sui_nav').SuiNav({});
		$('.MenuToggle').click(function() {
			console.log("toggle");
			topbar.toggle();
		});
		$('.MenuOpen').click(function() {
			console.log("open");
			topbar.show();
		});
	});
	return true;
}

function hideP(obj) {
	showLoading();
	obj.parentNode.style.display = "none";
}

function loadMainWorkSpance(url) {
	showLoading();
	$('#workspace').attr("src", url);
}

function skipToRoot() {
	showLoading();
	window.location.href = "skipIndex";
}

var loadingIndex = null;
function showLoading(zIndexNoFlag) {
	if (zIndexNoFlag) {
		loadingIndex = layer.load(1, {
			shade : [ 0.1, '#1FADEF' ]
		});
	} else {
		loadingIndex = layer.load(1, {
			shade : [ 0.1, '#1FADEF' ],
			zIndex : 2000
		});
	}
}

function closeLoading() {
	if (loadingIndex) {
		layer.close(loadingIndex);
	}
}

function ceilToIndex() {
	$.ajax({
		type : "post",
		url : "clearDefaultAction.action",
		dataType : "html",
		async : true,
		success : function(data) {
			window.location.href = "skipIndex";
		}
	});
}

function showCardJiActive(userCardNum) {
	layer.open({
		type : 2,
		title : '学生卡『' + userCardNum + '』激活',
		area : [ '640px', '660px' ],
		offset : '20%',
		shadeClose : false,
		anim : 2,
		closeBtn : 0,
		move : false,
		content : [
				'skipStudentCardActive?activeCardId=' + loginUserCardId
						+ '&cardNum=' + userCardNum, 'no' ],
		end : function() {
			skipToRoot();
		}
	});
}

function doLimitsPerfect() {
	layui.use('element', function() {
		var elem = layui.element;
		elem.progress('limitProgress', '0%');
		showLoading(true);
		$.ajax({
			type : "post",
			url : "fillLimitPer.action",
			dataType : "html",
			async : true,
			success : function(data) {
				closeLoading();
				var limitCountArr = eval(data);
				var hasLimiteProgessVal = limitCountArr[0] / limitCountArr[1]
						* 100;
				hasLimitsCount = limitCountArr[0];
				allLimitsCount = limitCountArr[1];
				elem.progress('limitProgress', hasLimiteProgessVal + '%');
				if (hasLimiteProgessVal == 100) {
					$('#limitFillA').fadeOut();
					// 提示用戶權限補充完成，需要修改用戶權限后，重新登錄
					setTimeout(function() {
						layer.msg('必要权限补充完成，请修改用户权限后，重新登录');
					}, 500);
				} else {
					$('#limitFillA').show();
				}
			}
		});
	});
}
