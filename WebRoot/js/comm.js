var commColorArr = [ "#FF5722", "#009688", "#1FB4E5", "#E51FA1", "#E53B1F", "#E58F1F", "#73E51F", "#1FE598", "#5FB878" ];

/**
 * 补全单元格
 */
var getReplenishTr = function(replenishCount, tdCount) {
	var returnTrHtml = "";
	if (replenishCount && tdCount) {
		for (var rth = 0; rth < replenishCount; rth++) {
			returnTrHtml += "<tr>";
			for (var dth = 0; dth < tdCount; dth++) {
				returnTrHtml += "<td>&nbsp;</td>"
			}
			returnTrHtml += "</tr>"
		}
	}
	return returnTrHtml;
}

/**
 * 补全数据单元格 link 格式的以“@#|”区分跳转链接、点击方法和内容（正常数据格式）
 * 
 * @param dataTrArr
 *            每一行数据集
 * @param fullDataObj
 *            每一行完整数据集
 * @param activeAreaJson
 *            每一行操作区域按钮集
 */
var getMainListDataTr = function(dataTrArr, fullDataObj, activeAreaJson) {
	var dataTrHtml = "";
	if(dataTrArr){
		dataTrHtml += "<tr>";
		$.each(dataTrArr, function(index, dataObj) {
			if(String(dataObj).indexOf('lineSelected:') == 0) {
				let choseType = String(dataObj).split(':')[1];
				if(index == 0 && fullDataObj && !$.isEmptyObject(fullDataObj)){
					dataTrHtml += "<td><input class='fullDataObj' type='hidden' value='" + JSON.stringify(fullDataObj) + "' />";
					if(choseType == '1') {
						dataTrHtml += '<input type="checkbox" name="selectItem"></td>';
					} else {
						dataTrHtml += '<input type="radio" name="selectItem"></td>';
					}
				}else{
					if(choseType == '1') {
						dataTrHtml += '<td><input type="checkbox" name="selectItem"></td>';
					} else {
						dataTrHtml += '<td><input type="radio" name="selectItem"></td>';
					}
				}
			}else if(String(dataObj).indexOf('img:') == 0){
				let imgSrcData = String(dataObj).substr(4);
				let imgWHStyle = null;
				if(String(dataObj).indexOf('[') > 0 && String(dataObj).indexOf(']') > 0) {
					imgSrcData = String(dataObj).substr(String(dataObj).indexOf(']') + 1);
					let imgWhOpts = String(dataObj).substring(5, String(dataObj).indexOf(']')).split(',');
					imgWHStyle = 'width: ' + imgWhOpts[0].trim() + 'px; height: ' + imgWhOpts[1].trim() + 'px;';
				}
				if(index == 0 && fullDataObj && !$.isEmptyObject(fullDataObj)){
					dataTrHtml += "<td><input class='fullDataObj' type='hidden' value='" + JSON.stringify(fullDataObj) + "' />";
					if(imgWHStyle) {
						dataTrHtml += "<div style='display: inline-block; " + imgWHStyle + "'><div style='width: 100%; height: 100%; background: url(" + imgSrcData + "); background-repeat: no-repeat; background-size: 100% 100%;'></div></div></td>";
					} else {
						dataTrHtml += "<img src='" + imgSrcData + "'/></td>";
					}
				}else{
					if(imgWHStyle) {
						dataTrHtml += "<td><div style='display: inline-block; " + imgWHStyle + "'><div style='width: 100%; height: 100%; background: url(" + imgSrcData + "); background-repeat: no-repeat; background-size: 100% 100%;'></div></div></td>";
					} else {
						dataTrHtml += "<td><img src='" + imgSrcData + "'/></td>";
					}
				}
			}else if($.isArray(dataObj)){
				if(String(dataObj[0]).indexOf('link:') == 0){
					if(index == 0 && fullDataObj && !$.isEmptyObject(fullDataObj)){
						dataTrHtml += "<td><input class='fullDataObj' type='hidden' value='" + JSON.stringify(fullDataObj) + "' /><a class='tableTdLink' href='javascript: void(0);' onclick='" + String(dataObj[0]).substr(5) + "(" + JSON.stringify(fullDataObj) + ")'>" +
							dataObj[1] + "&nbsp;</a></td>";
					}else{
						dataTrHtml += "<td><a class='tableTdLink' href='javascript: void(0);' onclick='" + String(dataObj[0]).substr(5) + "(" + JSON.stringify(fullDataObj) + ")'>" + dataObj[1] + "&nbsp;</a></td>";
					}
				}
			}else {
				if(index == 0 && fullDataObj && !$.isEmptyObject(fullDataObj)){
					dataTrHtml += "<td><input class='fullDataObj' type='hidden' value='" + JSON.stringify(fullDataObj) + "' />" +
							dataObj + "&nbsp;</td>";
				}else{
					dataTrHtml += "<td>" + dataObj + "&nbsp;</td>";
				}
			}
		});
		// 操作区域按钮
		if(activeAreaJson) {
			dataTrHtml += "<td>";
			let activeIndex = 0;
			$.each(activeAreaJson, function(activeKey, activeFn) {
				let iconfontCode = '';
				let btnText = '';
				if(activeKey.indexOf('&#') == 0) {
					iconfontCode = activeKey.split(':')[0];
					btnText = activeKey.split(':')[1];
				}else {
					btnText = activeKey;
				}
				dataTrHtml += "<button style='background: " + commColorArr[activeIndex] + ";' " +
						"class='layui-btn layui-btn-mini' " +
						"onclick='" + activeFn + "(" + JSON.stringify(fullDataObj) + ")'>" + 
							(iconfontCode ? "<i class='layui-icon'>" + iconfontCode + ";</i>" : "") + btnText + "</button>";
				activeIndex++;
			});
			dataTrHtml += "&nbsp;</td>";
		}
		dataTrHtml += "</tr>";
	}
	return dataTrHtml;
}

/**
 * 补全数据单元格 link 格式的以“@#|”区分跳转链接、点击方法和内容（以合并单元格方式）
 * 
 * @param dataTrArr
 *            每一合并行数据集
 * @param fullDataObj
 *            每一合并行完整数据集
 * @param activeAreaJson
 *            每一合并行操作区域按钮集
 * @param activeAreaMergeFlag
 *            每一合并行操作区域是否需要合并
 */
var getMainListDataTrByGroup = function(dataTrArr, fullDataObj, activeAreaJson, activeAreaMergeFlag) {
	var dataTrHtml = "";
	if(dataTrArr){
		let hasMergeTdArr = [];
		let maxMergeNum = fullDataObj.length;
		for(var trC = 0; trC < maxMergeNum; trC++) {
			dataTrHtml += "<tr>";
			$.each(dataTrArr, function(index, dataObj) {
				if($.isArray(dataObj)) {
					if(String(dataObj[trC]).indexOf('lineSelected:') == 0) {
						let choseType = String(dataObj[trC]).split(':')[1];
						if(index == 0 && fullDataObj && !$.isEmptyObject(fullDataObj)){
							dataTrHtml += "<td><input class='fullDataObj' type='hidden' value='" + JSON.stringify(fullDataObj) + "' />";
							if(choseType == '1') {
								dataTrHtml += '<input type="checkbox" name="selectItem"></td>';
							} else {
								dataTrHtml += '<input type="radio" name="selectItem"></td>';
							}
						}else{
							if(choseType == '1') {
								dataTrHtml += '<td><input type="checkbox" name="selectItem"></td>';
							} else {
								dataTrHtml += '<td><input type="radio" name="selectItem"></td>';
							}
						}
					}else if(String(dataObj[trC]).indexOf('img:') == 0){
						let imgSrcData = String(dataObj[trC]).substr(4);
						let imgWHStyle = null;
						if(String(dataObj[trC]).indexOf('[') > 0 && String(dataObj[trC]).indexOf(']') > 0) {
							imgSrcData = String(dataObj[trC]).substr(String(dataObj[trC]).indexOf(']') + 1);
							let imgWhOpts = String(dataObj[trC]).substring(5, String(dataObj[trC]).indexOf(']')).split(',');
							imgWHStyle = 'width: ' + imgWhOpts[0].trim() + 'px; height: ' + imgWhOpts[1].trim() + 'px;';
						}
						if(index == 0 && fullDataObj && !$.isEmptyObject(fullDataObj)){
							dataTrHtml += "<td><input class='fullDataObj' type='hidden' value='" + JSON.stringify(fullDataObj) + "' />";
							if(imgWHStyle) {
								dataTrHtml += "<div style='display: inline-block; " + imgWHStyle + "'><div style='width: 100%; height: 100%; background: url(" + imgSrcData + "); background-repeat: no-repeat; background-size: 100% 100%;'></div></div></td>";
							} else {
								dataTrHtml += "<img src='" + imgSrcData + "'/></td>";
							}
						}else{
							if(imgWHStyle) {
								dataTrHtml += "<td><div style='display: inline-block; " + imgWHStyle + "'><div style='width: 100%; height: 100%; background: url(" + imgSrcData + "); background-repeat: no-repeat; background-size: 100% 100%;'></div></div></td>";
							} else {
								dataTrHtml += "<td><img src='" + imgSrcData + "'/></td>";
							}
						}
					}else if($.isArray(dataObj[trC])) {
						if(String(dataObj[trC][0]).indexOf('link:') == 0){
							if(index == 0 && fullDataObj && !$.isEmptyObject(fullDataObj)){
								dataTrHtml += "<td><input class='fullDataObj' type='hidden' value='" + JSON.stringify(fullDataObj) + "' /><a class='tableTdLink' href='javascript: void(0);' onclick='" + String(dataObj[trC][0]).substr(5) + "(" + JSON.stringify(fullDataObj[trC]) + ")'>" +
									dataObj[trC][1] + "&nbsp;</a></td>";
							}else{
								dataTrHtml += "<td><a class='tableTdLink' href='javascript: void(0);' onclick='" + String(dataObj[trC][0]).substr(5) + "(" + JSON.stringify(fullDataObj[trC]) + ")'>" + dataObj[trC][1] + "&nbsp;</a></td>";
							}
						}
					}else {
						if(index == 0 && fullDataObj && !$.isEmptyObject(fullDataObj)){
							dataTrHtml += "<td><input class='fullDataObj' type='hidden' value='" + JSON.stringify(fullDataObj) + "' />" +
								dataObj[trC] + "&nbsp;</td>";
						}else{
							dataTrHtml += "<td>" + dataObj[trC] + "&nbsp;</td>";
						}
					}
				} else {
					let tdDataName = '';
					let tdDataValue = '';
					if(String(dataObj).indexOf('###') >= 0) {
						tdDataName = String(dataObj).split('###')[0];
						tdDataValue = String(dataObj).split('###')[1];
					} else {
						tdDataName = String(dataObj);
						tdDataValue = String(dataObj);
					}
					if(maxMergeNum > 1) {
						if($.inArray(tdDataName, hasMergeTdArr) < 0) {
							hasMergeTdArr.push(tdDataName);
							if(String(tdDataValue).indexOf('lineSelected:') == 0) {
								let choseType = String(tdDataValue).split(':')[1];
								if(index == 0 && fullDataObj && !$.isEmptyObject(fullDataObj)){
									dataTrHtml += "<td rowspan='" + maxMergeNum + "'><input class='fullDataObj' type='hidden' value='" + JSON.stringify(fullDataObj) + "' />";
									if(choseType == '1') {
										dataTrHtml += '<input type="checkbox" name="selectItem"></td>';
									} else {
										dataTrHtml += '<input type="radio" name="selectItem"></td>';
									}
								}else{
									if(choseType == '1') {
										dataTrHtml += "<td rowspan='" + maxMergeNum + "'><input type='checkbox' name='selectItem'></td>";
									} else {
										dataTrHtml += "<td rowspan='" + maxMergeNum + "'><input type='radio' name='selectItem'></td>";
									}
								}
							}else if(String(tdDataValue).indexOf('img:') == 0){
								let imgSrcData = String(tdDataValue).substr(4);
								let imgWHStyle = null;
								if(String(tdDataValue).indexOf('[') > 0 && String(tdDataValue).indexOf(']') > 0) {
									imgSrcData = String(tdDataValue).substr(String(tdDataValue).indexOf(']') + 1);
									let imgWhOpts = String(tdDataValue).substring(5, String(tdDataValue).indexOf(']')).split(',');
									imgWHStyle = 'width: ' + imgWhOpts[0].trim() + 'px; height: ' + imgWhOpts[1].trim() + 'px;';
								}
								if(index == 0 && fullDataObj && !$.isEmptyObject(fullDataObj)){
									dataTrHtml += "<td rowspan='" + maxMergeNum + "'><input class='fullDataObj' type='hidden' value='" + JSON.stringify(fullDataObj) + "' />";
									if(imgWHStyle) {
										dataTrHtml += "<div style='display: inline-block; " + imgWHStyle + "'><div style='width: 100%; height: 100%; background: url(" + imgSrcData + "); background-repeat: no-repeat; background-size: 100% 100%;'></div></div></td>";
									} else {
										dataTrHtml += "<img src='" + imgSrcData + "'/></td>";
									}
								}else{
									if(imgWHStyle) {
										dataTrHtml += "<td rowspan='" + maxMergeNum + "'><div style='display: inline-block; " + imgWHStyle + "'><div style='width: 100%; height: 100%; background: url(" + imgSrcData + "); background-repeat: no-repeat; background-size: 100% 100%;'></div></div></td>";
									} else {
										dataTrHtml += "<td rowspan='" + maxMergeNum + "'><img src='" + imgSrcData + "'/></td>";
									}
								}
							}else if(String(tdDataValue).indexOf('link:') == 0){
								let linkDataArr = String(tdDataValue).substr(5).split('@#|');
								if(index == 0 && fullDataObj && !$.isEmptyObject(fullDataObj)){
									dataTrHtml += "<td rowspan='" + maxMergeNum + "'><input class='fullDataObj' type='hidden' value='" + JSON.stringify(fullDataObj) + "' /><a class='tableTdLink' href='javascript: void(0);' onclick='" + linkDataArr[0] + "(" + JSON.stringify(fullDataObj[trC]) + ")'>" +
										linkDataArr[1] + "&nbsp;</a></td>";
								}else{
									dataTrHtml += "<td rowspan='" + maxMergeNum + "'><a class='tableTdLink' href='javascript: void(0);' onclick='" + linkDataArr[0] + "(" + JSON.stringify(fullDataObj[trC]) + ")'>" + linkDataArr[1] + "&nbsp;</a></td>";
								}
							}else {
								if(index == 0 && fullDataObj && !$.isEmptyObject(fullDataObj)){
									dataTrHtml += "<td rowspan='" + maxMergeNum + "'><input class='fullDataObj' type='hidden' value='" + JSON.stringify(fullDataObj) + "' />" +
										tdDataValue + "&nbsp;</td>";
								}else{
									dataTrHtml += "<td rowspan='" + maxMergeNum + "'>" + tdDataValue + "&nbsp;</td>";
								}
							}
						}
					} else {
						if(String(tdDataValue).indexOf('lineSelected:') == 0) {
							let choseType = String(tdDataValue).split(':')[1];
							if(index == 0 && fullDataObj && !$.isEmptyObject(fullDataObj)){
								dataTrHtml += "<td><input class='fullDataObj' type='hidden' value='" + JSON.stringify(fullDataObj) + "' />";
								if(choseType == '1') {
									dataTrHtml += '<input type="checkbox" name="selectItem"></td>';
								} else {
									dataTrHtml += '<input type="radio" name="selectItem"></td>';
								}
							}else{
								if(choseType == '1') {
									dataTrHtml += "<td><input type='checkbox' name='selectItem'></td>";
								} else {
									dataTrHtml += "<td><input type='radio' name='selectItem'></td>";
								}
							}
						}else if(String(tdDataValue).indexOf('img:') == 0){
							let imgSrcData = String(tdDataValue).substr(4);
							let imgWHStyle = null;
							if(String(tdDataValue).indexOf('[') > 0 && String(tdDataValue).indexOf(']') > 0) {
								imgSrcData = String(tdDataValue).substr(String(tdDataValue).indexOf(']') + 1);
								let imgWhOpts = String(tdDataValue).substring(5, String(tdDataValue).indexOf(']')).split(',');
								imgWHStyle = 'width: ' + imgWhOpts[0].trim() + 'px; height: ' + imgWhOpts[1].trim() + 'px;';
							}
							if(index == 0 && fullDataObj && !$.isEmptyObject(fullDataObj)){
								dataTrHtml += "<td><input class='fullDataObj' type='hidden' value='" + JSON.stringify(fullDataObj) + "' />";
								if(imgWHStyle) {
									dataTrHtml += "<div style='display: inline-block; " + imgWHStyle + "'><div style='width: 100%; height: 100%; background: url(" + imgSrcData + "); background-repeat: no-repeat; background-size: 100% 100%;'></div></div></td>";
								} else {
									dataTrHtml += "<img src='" + imgSrcData + "'/></td>";
								}
							}else{
								if(imgWHStyle) {
									dataTrHtml += "<td><div style='display: inline-block; " + imgWHStyle + "'><div style='width: 100%; height: 100%; background: url(" + imgSrcData + "); background-repeat: no-repeat; background-size: 100% 100%;'></div></div></td>";
								} else {
									dataTrHtml += "<td><img src='" + imgSrcData + "'/></td>";
								}
							}
						}else if(String(tdDataValue).indexOf('link:') == 0){
							let linkDataArr = String(tdDataValue).substr(5).split('@#|');
							if(index == 0 && fullDataObj && !$.isEmptyObject(fullDataObj)){
								dataTrHtml += "<td><input class='fullDataObj' type='hidden' value='" + JSON.stringify(fullDataObj) + "' /><a class='tableTdLink' href='javascript: void(0);' onclick='" + linkDataArr[0] + "(" + JSON.stringify(fullDataObj[trC]) + ")'>" +
									linkDataArr[1] + "&nbsp;</a></td>";
							}else{
								dataTrHtml += "<td><a class='tableTdLink' href='javascript: void(0);' onclick='" + linkDataArr[0] + "(" + JSON.stringify(fullDataObj[trC]) + ")'>" + linkDataArr[1] + "&nbsp;</a></td>";
							}
						}else {
							if(index == 0 && fullDataObj && !$.isEmptyObject(fullDataObj)){
								dataTrHtml += "<td><input class='fullDataObj' type='hidden' value='" + JSON.stringify(fullDataObj) + "' />" +
									tdDataValue + "&nbsp;</td>";
							}else{
								dataTrHtml += "<td>" + tdDataValue + "&nbsp;</td>";
							}
						}
					}
				}
			});
			// 操作区域按钮
			if(activeAreaJson) {
				if(activeAreaMergeFlag && maxMergeNum > 1) {
					if($.inArray('activeArea', hasMergeTdArr) < 0) {
						hasMergeTdArr.push('activeArea');
						dataTrHtml += "<td rowspan='" + maxMergeNum + "'>";
						let activeIndex = 0;
						$.each(activeAreaJson, function(activeKey, activeFn) {
							let iconfontCode = '';
							let btnText = '';
							if(activeKey.indexOf('&#') == 0) {
								iconfontCode = activeKey.split(':')[0];
								btnText = activeKey.split(':')[1];
							}else {
								btnText = activeKey;
							}
							dataTrHtml += "<button style='background: " + commColorArr[activeIndex] + ";' " +
									"class='layui-btn layui-btn-mini' " +
									"onclick='" + activeFn + "(" + JSON.stringify(fullDataObj) + ")'>" + 
										(iconfontCode ? "<i class='layui-icon'>" + iconfontCode + ";</i>" : "") + btnText + "</button>";
							activeIndex++;
						});
						dataTrHtml += "&nbsp;</td>";
					}
				} else {
					dataTrHtml += "<td>";
					let activeIndex = 0;
					$.each(activeAreaJson, function(activeKey, activeFn) {
						let iconfontCode = '';
						let btnText = '';
						if(activeKey.indexOf('&#') == 0) {
							iconfontCode = activeKey.split(':')[0];
							btnText = activeKey.split(':')[1];
						}else {
							btnText = activeKey;
						}
						dataTrHtml += "<button style='background: " + commColorArr[activeIndex] + ";' " +
								"class='layui-btn layui-btn-mini' " +
								"onclick='" + activeFn + "(" + JSON.stringify(fullDataObj[trC]) + ")'>" + 
									(iconfontCode ? "<i class='layui-icon'>" + iconfontCode + ";</i>" : "") + btnText + "</button>";
						activeIndex++;
					});
					dataTrHtml += "&nbsp;</td>";
				}
			}
			dataTrHtml += "</tr>";
		}
	}
	return dataTrHtml;
}

var getDataArrFromJsonObj = function(jsonObj, aboutKey, jointStr, dataFilter, groupThisflag) {
	if(!groupThisflag) {
		let resultDataArr = [];
		$.each(jsonObj, function(jsonObjIndex, jsonObjVal) {
			if(jointStr) {
				if(dataFilter) {
					resultDataArr.push([ jointStr, getTdDataByFilter(jsonObjVal[aboutKey], dataFilter) ]);
				} else {
					resultDataArr.push([ jointStr, jsonObjVal[aboutKey] ]);
				}
			} else {
				if(dataFilter) {
					resultDataArr.push(getTdDataByFilter(jsonObjVal[aboutKey], dataFilter));
				} else {
					resultDataArr.push(jsonObjVal[aboutKey]);
				}
			}
		});
		return resultDataArr;
	} else {
		let resultDataStr = '';
		$.each(jsonObj, function(jsonObjIndex, jsonObjVal) {
			let tdDataVal = jsonObj[0][aboutKey];
			if(dataFilter) {
				tdDataVal = getTdDataByFilter(tdDataVal, dataFilter);
			}
			if(jointStr) {
				resultDataStr = aboutKey + '###' + jointStr + '@#|' + tdDataVal;
			} else {
				resultDataStr = aboutKey + '###' + tdDataVal;
			}
			return false;
		});
		return resultDataStr;
	}
}

/**
 * 获取时间差
 */
var getDateTimeDiff = function(dateTimeStart, dateTimeEnd) {
	var timeStart = new Date(dateTimeStart);
	var timeEnd = new Date();
	if(dateTimeEnd){
		timeEnd = new Date(dateTimeEnd);
	}
	var timeDiffStamp = timeEnd.getTime() - timeStart.getTime();
	var diffTimeResult = '';
	var days = 0;
	var hours = 0;
	var minite = 0;
	if(timeDiffStamp >= (24 * 60 * 60 * 1000)){
		days = Math.floor(timeDiffStamp / (24 * 60 * 60 * 1000));
		diffTimeResult += days + ' 天 ';
	}
	if((timeDiffStamp - days * (24 * 60 * 60 * 1000)) >= (60 * 60 * 1000)){
		hours = Math.floor((timeDiffStamp - days * (24 * 60 * 60 * 1000)) / (60 * 60 * 1000));
		diffTimeResult += hours + ' 小时 ';
	}
	if(days == 0 && (timeDiffStamp - hours * (60 * 60 * 1000)) >= (60 * 1000)){
		minite = Math.floor((timeDiffStamp - hours * (60 * 60 * 1000)) / (60 * 1000));
		diffTimeResult += minite + ' 分钟 ';
	}
	return diffTimeResult + '~';
}

// 对Date的扩展，将 Date 转化为指定格式的String
// 月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符，
// 年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字)
// 例子：
// (new Date()).format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423
// (new Date()).format("yyyy-M-d h:m:s.S") ==> 2006-7-2 8:9:4.18
Date.prototype.format = function(fmt){
	var o = {
	 "M+" : this.getMonth()+1,                 // 月份
	 "d+" : this.getDate(),                    // 日
	 "h+" : this.getHours(),                   // 小时
	 "m+" : this.getMinutes(),                 // 分
	 "s+" : this.getSeconds(),                 // 秒
	 "q+" : Math.floor((this.getMonth()+3)/3), // 季度
	 "S"  : this.getMilliseconds()             // 毫秒
	};
	if(/(y+)/.test(fmt)){
		fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
	}
	for(var k in o){
		if(new RegExp("("+ k +")").test(fmt)){
			fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
		}
	}
	return fmt;
}

/**
 * 分页获取数据 相关参数：
 * 
 * @param actionUri
 *            请求数据的地址 ， 默认 null
 * @param cur
 *            当前数据页码 ， 默认 1
 * @param params
 *            查询数据的相关条件参数 ， 默认 null
 * @param choice
 *            是否开启表格数据行前选框 ， 默认 false
 * @param choiceType
 *            表格数据行前选框类型 [ 可选值：1 复选框、2 单选框 ] ， 默认 1
 * @param serialNumber
 *            是否开启表格数据行前序号 ， 默认 true
 * @param dataTrArr
 *            需要显示的表格数据-数据成功后使用 ， 默认 null（数组格式）
 * @param dataTrFilter
 *            需要显示的表格数据过滤器-数据成功后使用 ， 默认 null（JSON对象格式: 键 -> 对应字段名、值 -> 具体一个值 或者
 *            一个数组，数组格式 [ '>10?A', '<10?B', '=10?C', '10?D', ... ] 或者 DATE 或者
 *            DATE[具体时间格式字符串] 或者 LINK 或者 LINK[xxx_SELF_xxx] 或者 IMG 或者
 *            IMG[width,height] 或者 IMG[xxx_SELF_xxx:width,height]） 其中，DATE为默认
 *            年月日 格式
 * @param activeAreaJson
 *            需要显示的表格数据相关操作按钮-数据成功后使用 ， 默认 null
 * @param groupFlag
 *            否是进行分组（将会进行数据单元格合并）groupBy不为空时有效 ， 默认 false
 * @param groupBy
 *            进行分组的数据列数组（groupFlag为true时有效） ， 默认 null
 * @param activeAreaMergeFlag
 *            是否对表格数据相关操作区域进行分组合并 ， 默认 false
 * @param mainDataWarp
 *            绑定单元格数据DOM Id值 ， 默认 'mainDataWarp'
 * @param pageElement
 *            绑定分页数据DOM Id值 ， 默认 'pageElement'
 * @param template
 *            数据单元模板 ， 默认 null，存在模板时，按照模板数据，不按照表格数据
 * @param wrapUl
 *            包裹UL层级 ， 默认 false
 * @param wrapUlClassName
 *            包裹UL层级的class名，可用于定义样式引用 ， 默认 ''
 * @param masonryFlag
 *            开启数据单元瀑布式布局 ， 默认 false
 * @param searchWrapElm
 *            查询区域Elm范围 ， 默认 null，从 body 范围查找
 * @param searchItemClass
 *            查询区域查询项的统一 class 名称前缀 ， 默认 ''
 * @param searchIgnoreVals
 *            查询区域将被忽略掉的输入值数组 ， 默认 []
 * @param searchDoElm
 *            触发查询的 Elm 元素 ， 默认 null ， 禁用查询功能
 * @param debug
 *            开启debug模式 ， 默认 false
 */
var getDataByPage = function(paramsForDP) {
	let baseParams = {
		actionUri: null, cur: 1, params: null, choice: false, choiceType: 1, serialNumber: true, dataTrArr: null, dataTrFilter: null, activeAreaJson: null, 
		groupFlag: false, groupBy: null, activeAreaMergeFlag: false, 
		mainDataWarp: 'mainDataWarp', pageElement: 'pageElement', 
		template: null, wrapUl: false, wrapUlClassName: '', masonryFlag: false, masonryItemClass: '', 
		searchWrapElm: null, searchItemClass: '', searchIgnoreVals: [ ], searchValRecovers: { }, searchDoElm: null, debug: false
	};
	if($.isPlainObject(paramsForDP)) {
		$.extend(baseParams, paramsForDP);
		if((baseParams.actionUri && $.isArray(baseParams.dataTrArr)) || 
				(baseParams.template && typeof baseParams.template ==='function' && !baseParams.masonryFlag) || 
				(baseParams.template && typeof baseParams.template ==='function' && baseParams.masonryFlag && baseParams.masonryItemClass && baseParams.masonryItemClass !== '')){
			// 计算表格列数
			let tableLineNum = baseParams.dataTrArr.length;
			if(baseParams.serialNumber) {
				tableLineNum += 1;
				// 在表头添加序号列
				let colgroupWrap = $('#' + baseParams.mainDataWarp).parent().find('colgroup');
				if(colgroupWrap.length == 1) {
					$(colgroupWrap).prepend('<col width="60">');
				}
				let theadWrap = $('#' + baseParams.mainDataWarp).parent().find('thead');
				if(theadWrap.length == 1) {
					let firstTheadTrTd = $(theadWrap).find('tr:eq(0)').children(':first');
					$(firstTheadTrTd).clone().html('序号').prependTo($(theadWrap).find('tr:eq(0)'));
				}
			}
			if(baseParams.choice) {
				tableLineNum += 1;
				// 在表格头部添加全选选框
				let colgroupWrap = $('#' + baseParams.mainDataWarp).parent().find('colgroup');
				if(colgroupWrap.length == 1) {
					$(colgroupWrap).prepend('<col width="60">');
				}
				let theadWrap = $('#' + baseParams.mainDataWarp).parent().find('thead');
				if(theadWrap.length == 1) {
					let firstTheadTrTd = $(theadWrap).find('tr:eq(0)').children(':first');
					if(baseParams.choiceType == 1) {
						$(firstTheadTrTd).clone().html('<input type="checkbox" name="selectAll" />').prependTo($(theadWrap).find('tr:eq(0)'));
					} else {
						$(firstTheadTrTd).clone().html('&nbsp;').prependTo($(theadWrap).find('tr:eq(0)'));
					}
				}
			}
			if(baseParams.activeAreaJson) {
				tableLineNum += 1;
			}
			// 生成查询参数
			let paramsData = { currentPage : baseParams.cur };
			if(baseParams.params){
				$.extend(paramsData, baseParams.params);
			}
			parent.showLoading();
			$.ajax({
				type : "post",
				url : baseParams.actionUri,
				data : paramsData,
				dataType : "html",
				async : true,
				success : function(data) {
					parent.closeLoading();
					if (data) {
						$('#' + baseParams.mainDataWarp).html('');
						var resultDataJson = JSON.parse(data);
						layui.use([ 'laypage' ], function() {
							var laypage = layui.laypage;
							laypage.render({
								elem : baseParams.pageElement,
								count : Number(resultDataJson.dataCount),
								limit : resultDataJson.pageSize,
								curr : resultDataJson.currentPage,
								layout : [ 'count', 'prev', 'page', 'next', 'skip' ],
								jump : function(obj, first) {
									if (!first) {
										baseParams.cur = obj.curr;
										getDataByPage(baseParams);
									}
								}
							});
						});
						if (baseParams.template) {
							if (resultDataJson.child && resultDataJson.child != '') {
								$('#' + baseParams.mainDataWarp).css('text-align', 'center');
								if(baseParams.wrapUl) {
									if(baseParams.wrapUlClassName && baseParams.wrapUlClassName !== ''){
										$('#' + baseParams.mainDataWarp).html('<ul class="' + wrapUlClassName + '"></ul>');
										$('#' + baseParams.mainDataWarp).find('ul').css('text-align', 'center');
									}else{
										$('#' + baseParams.mainDataWarp).html('<ul></ul>');
									}
								}
								$.each(resultDataJson.child, function(resultDataIndex, resultDataObj) {
									let templateHtml = baseParams.template(resultDataObj);
									if(baseParams.wrapUl) {
										$('#' + baseParams.mainDataWarp).find('ul').append(templateHtml);
									} else {
										$('#' + baseParams.mainDataWarp).append(templateHtml);
									}
								});
								if(baseParams.masonryFlag) {
									$('#' + baseParams.mainDataWarp).masonry({
										itemSelector : '.' + baseParams.masonryItemClass
									});
								}
							}
						} else {
							let resultDataGroupMap = {}; // 存放分组数据（用于合并单元格）
							if (resultDataJson.child && resultDataJson.child != '') {
								// 进行分组判断，是否需要合并单元格
								if(baseParams.groupFlag && baseParams.groupBy) {
									// 按照分组字段进行数据分组
									$.each(resultDataJson.child, function(resultDataIndex, resultDataObj) {
										let groupByAllStr = '';
										$.each(baseParams.groupBy, function(groupByIndex, groupByVal) {
											groupByAllStr += resultDataObj[groupByVal];
											if(groupByIndex < baseParams.groupBy.length - 1) {
												groupByAllStr += '|';
											}
										});
										if(!resultDataGroupMap[groupByAllStr]) {
											resultDataGroupMap[groupByAllStr] = [ resultDataObj ];
										} else {
											let resultDataGroupArr = resultDataGroupMap[groupByAllStr];
											resultDataGroupArr.push(resultDataObj);
										}
									});
									let dataIndex = 0;
									$.each(resultDataGroupMap, function(index, obj) {
										let dataTrRealArr = [];
										if(baseParams.choice) {
											dataTrRealArr.push('lineSelected:' + baseParams.choiceType);
										}
										if(baseParams.serialNumber) {
											dataTrRealArr.push((Number(baseParams.cur) - 1) * Number(resultDataJson.pageSize) + dataIndex + 1);
										}
										$.each(baseParams.dataTrArr, function(dataTrSetIndex, dataTrSetVal) {
											if($.isArray(dataTrSetVal)) {
												if($.inArray(dataTrSetVal[1], baseParams.groupBy) < 0) {
													if($.isPlainObject(baseParams.dataTrFilter)) {
														if(baseParams.dataTrFilter[dataTrSetVal[1]]) {
															let dataFilter = baseParams.dataTrFilter[dataTrSetVal[1]];
															dataTrRealArr.push(getDataArrFromJsonObj(obj, dataTrSetVal[1], dataTrSetVal[0], dataFilter));
														} else {
															dataTrRealArr.push(getDataArrFromJsonObj(obj, dataTrSetVal[1], dataTrSetVal[0]));
														}
													} else {
														dataTrRealArr.push(getDataArrFromJsonObj(obj, dataTrSetVal[1], dataTrSetVal[0]));
													}
												} else {
													if($.isPlainObject(baseParams.dataTrFilter)) {
														if(baseParams.dataTrFilter[dataTrSetVal[1]]) {
															let dataFilter = baseParams.dataTrFilter[dataTrSetVal[1]];
															dataTrRealArr.push(getDataArrFromJsonObj(obj, dataTrSetVal[1], dataTrSetVal[0], dataFilter, true));
														} else {
															dataTrRealArr.push(getDataArrFromJsonObj(obj, dataTrSetVal[1], dataTrSetVal[0], null, true));
														}
													} else {
														dataTrRealArr.push(getDataArrFromJsonObj(obj, dataTrSetVal[1], dataTrSetVal[0], null, true));
													}
												}
											} else {
												if($.inArray(dataTrSetVal, baseParams.groupBy) < 0) {
													if($.isPlainObject(baseParams.dataTrFilter)) {
														if(baseParams.dataTrFilter[dataTrSetVal]) {
															let dataFilter = baseParams.dataTrFilter[dataTrSetVal];
															dataTrRealArr.push(getDataArrFromJsonObj(obj, dataTrSetVal, null, dataFilter));
														} else {
															dataTrRealArr.push(getDataArrFromJsonObj(obj, dataTrSetVal));
														}
													} else {
														dataTrRealArr.push(getDataArrFromJsonObj(obj, dataTrSetVal));
													}
												} else {
													if($.isPlainObject(baseParams.dataTrFilter)) {
														if(baseParams.dataTrFilter[dataTrSetVal]) {
															let dataFilter = baseParams.dataTrFilter[dataTrSetVal];
															dataTrRealArr.push(getDataArrFromJsonObj(obj, dataTrSetVal, null, dataFilter, true));
														} else {
															dataTrRealArr.push(getDataArrFromJsonObj(obj, dataTrSetVal, null, null, true));
														}
													} else {
														dataTrRealArr.push(getDataArrFromJsonObj(obj, dataTrSetVal, null, null, true));
													}
												}
											}
										});
										if(baseParams.debug) {
											console.log('开始获取补充单元格Html - 分组方式', dataTrRealArr);
										}
										var dataTrHtml = getMainListDataTrByGroup(dataTrRealArr, obj, baseParams.activeAreaJson, baseParams.activeAreaMergeFlag);
										$('#' + baseParams.mainDataWarp).append(dataTrHtml);
										dataIndex++;
									});
								} else {
									$.each(resultDataJson.child, function(index, obj) {
										let dataTrRealArr = [];
										if(baseParams.choice) {
											dataTrRealArr.push('lineSelected:' + baseParams.choiceType);
										}
										if(baseParams.serialNumber) {
											dataTrRealArr.push((Number(baseParams.cur) - 1) * Number(resultDataJson.pageSize) + index + 1);
										}
										$.each(baseParams.dataTrArr, function(dataTrSetIndex, dataTrSetVal) {
											if($.isArray(dataTrSetVal)) {
												if($.isPlainObject(baseParams.dataTrFilter)) {
													if(baseParams.dataTrFilter[dataTrSetVal[1]]) {
														let dataFilter = baseParams.dataTrFilter[dataTrSetVal[1]];
														dataTrRealArr.push([ dataTrSetVal[0], getTdDataByFilter(obj[ dataTrSetVal[1] ], dataFilter) ]);
													} else {
														dataTrRealArr.push([ dataTrSetVal[0], obj[ dataTrSetVal[1] ] ]);
													}
												} else {
													dataTrRealArr.push([ dataTrSetVal[0], obj[ dataTrSetVal[1] ] ]);
												}
											} else {
												if($.isPlainObject(baseParams.dataTrFilter)) {
													if(baseParams.dataTrFilter[dataTrSetVal]) {
														let dataFilter = baseParams.dataTrFilter[dataTrSetVal];
														dataTrRealArr.push(getTdDataByFilter(obj[ dataTrSetVal ], dataFilter));
													} else {
														dataTrRealArr.push(obj[ dataTrSetVal ]);
													}
												} else {
													dataTrRealArr.push(obj[ dataTrSetVal ]);
												}
											}
										});
										if(baseParams.debug) {
											console.log('开始获取补充单元格Html - 普通方式', dataTrRealArr);
										}
										var dataTrHtml = getMainListDataTr(dataTrRealArr, obj, baseParams.activeAreaJson);
										$('#' + baseParams.mainDataWarp).append(dataTrHtml);
									});
								}
							}
							// 绑定选框点击事件
							if(baseParams.choice) {
								let selectAll = $('#' + baseParams.mainDataWarp).parent().find('thead').find('input[name="selectAll"]');
								$(selectAll).off('click');
								$(selectAll).on('click', function() {
									$('#' + baseParams.mainDataWarp).find('input[name="selectItem"]').prop('checked', $(this).is(':checked'));
								});
							}
							// 补全单元格
							var currentDataLength = 0;
							if(baseParams.groupFlag && baseParams.groupBy) {
								currentDataLength = Object.getOwnPropertyNames(resultDataGroupMap).length;
							} else {
								if (resultDataJson.child) {
									currentDataLength = resultDataJson.child.length;
								}
							}
							var replenishCount = Number(resultDataJson.pageSize) - currentDataLength;
							var trHtml = getReplenishTr(replenishCount, tableLineNum);
							$('#' + baseParams.mainDataWarp).append(trHtml);
						}
					}
				}
			});
		} else {
			if(baseParams.template){
				if(typeof baseParams.template !=='function'){
					console.error('getDataByPage', '数据单元模板不是一个可执行方法：template『' + baseParams.template + '』不正确');
				}
				if(baseParams.masonryFlag && (!baseParams.masonryItemClass || baseParams.masonryItemClass === '')){
					console.error('getDataByPage', '瀑布流指定子项class名不能为空：masonryItemClass『' + baseParams.masonryItemClass + '』不正确');
				}
			}else{
				if(!baseParams.actionUri){
					console.error('getDataByPage', '数据请求地址：actionUri『' + baseParams.actionUri + '』不正确');
				}
				if(!$.isArray(baseParams.dataTrArr)){
					console.error('getDataByPage', '表格数据组：dataTrArr『' + baseParams.dataTrArr + '』不正确');
				}
			}
		}
		// 进行查询区域参数绑定
		if (baseParams.searchDoElm) {
			let oldParams = baseParams.params;
			$(baseParams.searchDoElm).off('click');
			$(baseParams.searchDoElm).on('click', function() {
				// 重置查询条件
				if(baseParams.params == null) {
					baseParams.params = {};
				}
				$.each(baseParams.searchValRecovers, function(searchValRecoverKey, searchValRecover) {
					delete baseParams.params[searchValRecoverKey];
				});
				// 搜索主逻辑
				let searchWrapDom = 'body';
				if(baseParams.searchWrapElm) {
					searchWrapDom = baseParams.searchWrapElm;
				}
				let searchItems = $(searchWrapDom).find('.' + baseParams.searchItemClass);
				var searchItemValJson = {};
				$.each(searchItems, function(searchItemIndex, searchItemObj) {
					let itemNameVal = $(searchItemObj).attr('name');
					let itemDataVal = $(searchItemObj).val();
					if(itemDataVal == '' || $.inArray(itemDataVal, baseParams.searchIgnoreVals) >= 0) {
						return false;
					}
					let searchJsonStr = '{ "' + itemNameVal + '" : "' + itemDataVal + '" }';
					$.extend(searchItemValJson, JSON.parse(searchJsonStr));
				});
				baseParams.searchValRecovers = {};
				$.extend(baseParams.searchValRecovers, searchItemValJson);
				$.extend(baseParams.params, searchItemValJson);
				if(baseParams.debug) {
					console.log('进行分页数据查询 -> 参数：', baseParams.params);
				}
				getDataByPage(baseParams);
			});
		}
	} else {
		console.error('getDataByPage', '配置参数错误');
	}
	
	// 提供方法，返回表格行选中项
	this.getSelect = function() {
		if(baseParams.choice) {
			let selectItemDatas = [];
			let selectItems = $('#' + baseParams.mainDataWarp).find('input[name="selectItem"]');
			$.each(selectItems, function(selectItemIndex, selectItem) {
				if($(selectItem).is(':checked')) {
					let thisTdFullData = $(selectItem).parents('tr').find('td:eq(0)').find('input.fullDataObj').val();
					selectItemDatas.push(thisTdFullData);
				}
			});
			return selectItemDatas;
		} else {
			console.error('获取分页表格数据中选中数据项', '当前分页数据选择栏位未开启『 参数：choice 』');
		}
	};
	// 提供方法，进行分页数据刷新
	// 传值 与 getDataByPage方法参数一致，只需要传有变化的参数即可；可以不传，则参数以最后一次变动为准
	// 注意：每次调用，改变的参数会一直保持改变后的值
	this.ref = function(pageDataRefParams) {
		if($.isPlainObject(pageDataRefParams)) {
			$.extend(baseParams, pageDataRefParams);
		}
		getDataByPage(baseParams);
	};
}

function getTdDataByFilter(aboutData, dataFilter) {
	let resultData = aboutData;
	if(dataFilter) {
		if($.isArray(dataFilter)) {
			$.each(dataFilter, function(filterIndex, dataFilterVal) {
				let filter = dataFilterVal.split('?')[0].trim();
				let filterTo = dataFilterVal.split('?')[1].trim();
				if(!(filter.indexOf('>') == 0 || filter.indexOf('<') == 0 || filter.indexOf('==') == 0)) {
					if(filter.indexOf('=') == 0) {
						filter = '=' + filter;
					} else {
						filter = '==' + filter;
					}
				}
				let filterCode = '==';
				let filterCodeAfter = '';
				if(filter.indexOf('==') == 0) {
					filterCode = '==';
					filterCodeAfter = filter.substr(2);
				} else {
					filterCode = filter.substr(0, 1);
					filterCodeAfter = filter.substr(1);
				}
				let filterStr = "'" + aboutData + "'" + filterCode + "'" + filterCodeAfter + "'";
				
				if(eval(filterStr)) {
					resultData = filterTo;
				}
			});
		} else {
			if(dataFilter == 'DATE' || (dataFilter.indexOf('DATE[') == 0 && dataFilter.indexOf(']') == dataFilter.length - 1)) {
				let tdDateFmt = 'yyyy-MM-dd';
				if(dataFilter != 'DATE') {
					tdDateFmt = dataFilter.substring(5, dataFilter.length - 1);
				}
				try{
					resultData = (new Date(eval(aboutData))).format(tdDateFmt);
				}catch(e){}
			} else if(dataFilter == 'LINK' || (dataFilter.indexOf('LINK[') == 0 && dataFilter.indexOf(']') == dataFilter.length - 1)) {
				let tdDataFmt = '_SELF_';
				if(dataFilter != 'LINK') {
					tdDataFmt = dataFilter.substring(5, dataFilter.length - 1);
				}
				let dataFmtAfter = tdDataFmt.replace(/_SELF_/g, aboutData);
				resultData = 'link:' + dataFmtAfter;
			} else if(dataFilter == 'IMG' || (dataFilter.indexOf('IMG[') == 0 && dataFilter.indexOf(']') == dataFilter.length - 1)) {
				let tdDataFmt = '_SELF_';
				if(dataFilter != 'IMG') {
					tdDataFmt = dataFilter.substring(4, dataFilter.length - 1);
				}
				let dataFmtAfter = tdDataFmt.replace(/_SELF_/g, aboutData);
				let imgWh = '';
				if(tdDataFmt.indexOf(':') > 0) {
					let tdImageData = tdDataFmt.split(':');
					dataFmtAfter = tdImageData[0].replace(/_SELF_/g, aboutData);
					imgWh = '[' + tdImageData[1] + ']';
				} else {
					if(tdDataFmt.indexOf('_SELF_') < 0) {
						dataFmtAfter = aboutData;
						imgWh = '[' + tdDataFmt + ']';
					}
				}
				resultData = 'img:' + imgWh + dataFmtAfter;
			} else {
				resultData = dataFilter;
			}
		}
	}
	return resultData;
}

/**
 * 初始化管理员操作区域按钮
 */
var initSuperManagerDoWrap = function(isManagerLoginFlag, superDoActionUri) {
	if(isManagerLoginFlag) {
		var superManagerDoBtnHtml = '<button class="layui-btn layui-btn-mini" onclick="layerNewPop(\'' + superDoActionUri + '\');"> '
			+ '<i class="layui-icon">&#xe654;</i> '
			+ '新增数据'
			+ '</button>';
		var superManagerDoWrapHtml = '<fieldset id="superManagerDoWrap" class="layui-elem-field">' +
			  '<legend>管理员操作</legend>' +
			  '<div class="layui-field-box">' + superManagerDoBtnHtml + '</div>' +
			'</fieldset>';
		$('div.content-main').prepend(superManagerDoWrapHtml);
	}
}

var layerNewPop = function(superDoActionUri, title) {
	let layoptitle = '添加新数据';
	if(title) {
		layoptitle = title;
	}
	let index = parent.layer.open({
		type : 2,
		title : layoptitle,
		area : [ '60%', '80%' ],
		offset : '10%',
		shadeClose : true,
		anim : 2,
		move : false,
		resize : false,
		content : [superDoActionUri, 'no' ]
	});
	parent.layer.full(index);
}

/**
 * 初始化搜索区域按钮
 */
var initSearchBtnWrap = function(btnJson, selectDataArr) {
	let searchHtml = '';
	let hasSelectFalg = false;
	let selectHtmlIndex = -1;
	$.each(btnJson, function(key, val) {
		searchHtml += '<div style="display: inline-block; height: 38px; line-height: 38px; margin: 0px 4px;"> '
				+ '<div style="display: inline-block; background: #C4D4DE; height: 30px; line-height: 30px; padding: 0px 6px; border-radius: 4px;"> '
				+ '<button class="layui-btn layui-btn-mini" style="display: inline-block; height: 24px; line-height: 24px; vertical-align: middle;"> '
				+ '<i class="layui-icon" style="vertical-align: middle;">&#xe6b2;</i> '
				+ val
				+ '</button> ';
		if(key.indexOf('#') == 0) {
			selectHtmlIndex++;
			hasSelectFalg = true;
			let selectData = JSON.parse(selectDataArr[selectHtmlIndex]);
			searchHtml += '<div class="selectBox noselect"> ' +
						'<a href="javascript:void(0);" class="selectBoxA selectBoxSelectedA">- 请选择 -</a><p> ' +
				    	'<a href="javascript:void(0)" class="selectBoxA occupied currentSelected" value="">- 请选择 -</a> ';
			$.each(selectData, function(selectDataKey, selectDataVal) {
				searchHtml += '<a class="selectBoxA" href="javascript:void(0)" value="' + selectDataVal + '" title="' + selectDataVal + '">' + selectDataVal + '</a> ';
			});
			searchHtml += '</p><input type="hidden" name="search_' + key.substr(1) + '" id="select_' + key.substr(1) + '" value="" /> ' +
				'</div>';
		}else{
			searchHtml += '<input type="text" name="search_'
					+ key
					+ '" class="layui-input" style="height: 24px; line-height: 24px; width: 100px; display: inline-block; vertical-align: middle;" /> ';
		}
		searchHtml += '</div> ' + '</div>';
	});
	$('#searchBtnWrap').html(searchHtml);
	if(hasSelectFalg){
		$('.selectBox').find('p').mCustomScrollbar({
			theme: 'dark-thin',
			autoHideScrollbar: true,
			scrollButtons: { enable: true, scrollType: 'pixels' }
		});
		let selectBoxSelectedAs = $('#searchBtnWrap').find('div.selectBox').find('a.selectBoxSelectedA');
		selectBoxSelectedAs.click(function(e){
			if($(this).hasClass('open')){
				let what = this;
				$(what.nextSibling).slideUp('fast', function(){
					$(what).removeClass('open');
				});
			}else{
				$.each(selectBoxSelectedAs, function(selectBoxSelectedAIndex, selectBoxSelectedAObj) {
					let what = selectBoxSelectedAObj;
					if($(what).hasClass('open')){
					  	$(what.nextSibling).slideUp('fast', function(){
					  		$(what).removeClass('open');
						});
					}
				});
				$(this).addClass('open');
				$(this.nextSibling).slideDown('fast');
			}
			e.stopPropagation();
		});
		$(document).bind("click", function(e) {
			if(e.target.className != 'mCSB_draggerContainer' && e.target.className != 'mCSB_dragger' 
					&& e.target.className != 'mCSB_dragger_bar' && e.target.className != 'mCSB_draggerRail' 
						&& e.target.className != 'mCSB_buttonUp' && e.target.className != 'mCSB_buttonDown'){
				$.each(selectBoxSelectedAs, function(selectBoxSelectedAIndex, selectBoxSelectedAObj) {
					let what = selectBoxSelectedAObj;
				  	$(what.nextSibling).slideUp('fast', function(){
				  		$(what).removeClass('open');
					});
				});
			}
		});
		selectBoxSelectedAs.next().click(function(e){
			var src = e.target;
			if(src.tagName == "A" && src.className != 'mCSB_buttonUp' && src.className != 'mCSB_buttonDown'){
				var PObj = $(src).parents('p');
				PObj.prev().html(src.innerHTML);
				$(src).siblings().removeClass('currentSelected');
				$(src).addClass('currentSelected');
				PObj.next().val(src.getAttribute("value"));
			}
		});
	}
}

/**
 * 数据查询，点击查询按钮 参数与 -> getDataByPage 方法一致
 */
var zeXiaoDataSearch = function(dataSearchParams) {
	if($.isPlainObject(dataSearchParams)){
		var searchInputArrs = $('#searchBtnWrap').find('input');
		var searchInputValJson = {};
		$.each(searchInputArrs, function(index, searchInputObj) {
			var inputNameVal = $(searchInputObj).attr('name');
			var inputSearchVal = $(searchInputObj).val();
			if(inputSearchVal == ''){
				inputSearchVal = 'NULL';
			}
			var newSearchInputJsonStr = '{ "' + inputNameVal + '" : "' + inputSearchVal + '" }';
			$.extend(searchInputValJson, JSON.parse(newSearchInputJsonStr));
		});
		$.extend(dataSearchParams, { searchVal: JSON.stringify(searchInputValJson) });
		getDataByPage(dataSearchParams);
	} else {
		console.error('dataSearch', '配置参数错误');
	}
}

/**
 * 绑定表格数据行鼠标移动上展示详情的方法 composeStyle :
 * 1[图片左侧，文字右侧排版，适用第一个元素为图片，其他均为文字的情况；固定宽度400px]
 */
var bindTableLineHoverTip = function(tipDataParams) {
	let baseParams = {
		tipDataArr: null, composeStyle: -1, nullShowFlag: false, mainDataWarp: 'mainDataWarp', mainContentDom: $('div.content-main')
	};
	if($.isPlainObject(tipDataParams)) {
		$.extend(baseParams, tipDataParams);
		if($('#tableDataTipHtml').length == 0){
			$(baseParams.mainContentDom).append('<div id="tableDataTipHtml" class="animated flipInX"></div>');
		}
		let dataTbody = $('#' + baseParams.mainDataWarp);
		$(dataTbody).on('mouseenter', 'tr', function(e) {
			let tipDataHtml = '';
			let fullDataObj = $(this).find('input.fullDataObj').val();
			if(baseParams.tipDataArr && fullDataObj) {
				let fullDataObjJson = JSON.parse(fullDataObj);
				if($.isArray(fullDataObjJson)) {
					$.each(fullDataObjJson, function(fullDataObjIndex, fullDataObjVal) {
						tipDataHtml += getTableLineTipHtml(baseParams.tipDataArr, baseParams.composeStyle, fullDataObjVal);
						if(fullDataObjIndex < fullDataObjJson.length - 1) {
							tipDataHtml += '<hr style="background: #A6A6A6;">';
						}
					});
				} else {
					tipDataHtml += getTableLineTipHtml(baseParams.tipDataArr, baseParams.composeStyle, fullDataObjJson);
				}
			}
			if(tipDataHtml == '' && !baseParams.nullShowFlag){
				$('#tableDataTipHtml').hide();
			}
			let thatTr = this;
			let tipTop = $(thatTr).position().top;
			let tipLeft = $(thatTr).position().left;
			canMoveFlag = true;
			moveToTop = tipTop;
			moveToLeft = tipLeft;
			if(tipDataHtml == '' && !baseParams.nullShowFlag){
				canMoveFlag = false;
				moveToTop = 0;
				moveToLeft = 0;
				$('#tableDataTipHtml').fadeOut();
				clearInterval(animMoveTipTimer);
				animMoveTipTimer = null;
			}else{
				animMoveTipArea(tipDataHtml, baseParams.nullShowFlag);
			}
		}).on('mouseleave', 'tr', function(e) {
			canMoveFlag = false;
			moveToTop = 0;
			moveToLeft = 0;
			clearInterval(animMoveTipTimer);
			animMoveTipTimer = null;
		}).on('mouseleave', function(e) {
			canMoveFlag = false;
			moveToTop = 0;
			moveToLeft = 0;
			$('#tableDataTipHtml').fadeOut();
			clearInterval(animMoveTipTimer);
			animMoveTipTimer = null;
		});
	} else {
		console.error('bindTableLineHoverTip', '配置参数错误');
	}
}

function getTableLineTipHtml(tipDataArr, composeStyle, fullDataObjJson) {
	let tipDataHtml = '<div>';
	if(tipDataArr.length > 0 && composeStyle == 1){
		tipDataHtml += '<div style="width: 400px;" class="layui-fluid">';
		tipDataHtml += '<div class="layui-row">';
	}
	$.each(tipDataArr, function(index, dataObj) {
		let tipItemName = '';
		let tipItemIndex = '';
		if(String(dataObj).indexOf(':') >= 0){
			tipItemName = String(dataObj).split(':')[0];
			tipItemIndex = String(dataObj).split(':')[1];
		}else{
			tipItemIndex = String(dataObj);
		}
		if(tipItemName != ''){
			tipItemName = '<strong>' + tipItemName + '</strong>：';
		}
		if(composeStyle == 1){
			if(tipItemIndex.indexOf('img|') == 0){
				tipDataHtml += "<div class='layui-col-xs3 layui-col-sm3 layui-col-md3' style='text-align: center;'><img src='.." + fullDataObjJson[tipItemIndex.substr(4)] + "' style='width: 80px; height: 80px;'/></div>"
				tipDataHtml += "<div class='layui-col-xs9 layui-col-sm9 layui-col-md9'>";
			}else{
				if(tipItemIndex == 'schoolName'){
					tipDataHtml += "<p style='font-size: 16px; font-weight: bold; margin-bottom: 10px;'>" + fullDataObjJson[tipItemIndex] + "</p>"
				}else{
					tipDataHtml += "<p>" + tipItemName + fullDataObjJson[tipItemIndex] + "</p>"
				}
			}
		}else{
			if(tipItemIndex.indexOf('img|') == 0){
				tipDataHtml += "<p><img src='.." + fullDataObjJson[tipItemIndex.substr(4)] + "' style='width: 80px; height: 80px;'/></p>"
			}else{
				tipDataHtml += "<p>" + tipItemName + fullDataObjJson[tipItemIndex] + "</p>"
			}
		}
	});
	if(tipDataArr.length > 0 && composeStyle == 1){
		tipDataHtml += "</div>";
		tipDataHtml += "</div>";
		tipDataHtml += "</div>";
	}
	tipDataHtml += '</div>';
	return tipDataHtml;
}

// 动画移动提示区域
var canMoveFlag = false;
var moveToTop = 0, moveToLeft = 0;
var animMoveTipTimer = null;
function animMoveTipArea(tipDataHtml, nullShowFlag) {
	if(animMoveTipTimer == null){
		animMoveTipTimer = setInterval(function() {
			if(canMoveFlag) {
				if(tipDataHtml == '' && nullShowFlag){
					$('#tableDataTipHtml').html('<i class="layui-icon" style="font-size: 20px; margin-right: 10px;">&#xe629;</i>暂无数据');
				}else if(tipDataHtml != ''){
					$('#tableDataTipHtml').html(tipDataHtml);
				}
				if($('#tableDataTipHtml').is(':visible')){
					canMoveFlag = false;
					let tipHeight = $('#tableDataTipHtml').outerHeight();
					$('#tableDataTipHtml').animate({
						top: (moveToTop - tipHeight) + 'px',
						left: moveToLeft + 'px'
					}, 500);
				}else{
					canMoveFlag = false;
					let tipHeight = $('#tableDataTipHtml').outerHeight();
					$('#tableDataTipHtml').css({
						top: (moveToTop - tipHeight) + 'px',
						left: moveToLeft + 'px'
					});
					$('#tableDataTipHtml').fadeIn();
				}
			}
		}, 300);
	}
}

/**
 * 初始化页面滚定条
 * 
 * @param theme
 *            滚动条样式
 * @link http://manos.malihu.gr/repository/custom-scrollbar/demo/examples/scrollbar_themes_demo.html
 */
var initPageScrollBar = function(selector, theme) {
	$(selector).mCustomScrollbar({
		theme: theme,
		autoHideScrollbar: true,
		mouseWheelPixels: 80,
		scrollButtons: { enable: true, scrollType: 'pixels' }
	});
}

var downloadFile = function(fileUri) {
	let basePath = $('#requestContextPath').val();
	window.location.href = basePath + '/ServletDownload.do?filename=' + fileUri;
}

/**
 * 排序二叉树
 */
var BinaryTree = function() {
	let Node = function(key) {
		this.key = key;
		this.left = null;
		this.right = null;
	}
	
	let root = null;
	
	let insertNode = function(node, newNode) {
		if(newNode.key < node.key) {
			if(node.left === null) {
				node.left = newNode;
			} else {
				insertNode(node.left, newNode);
			}
		} else {
			if(node.right === null) {
				node.right = newNode;
			} else {
				insertNode(node.right, newNode);
			}
		}
	}
	
	// 插入数据节点
	this.insert = function(key) {
		let newNode = new Node(key);
		if(root === null) {
			root = newNode;
		} else {
			insertNode(root, newNode);
		}
	};
	
	let inOrderTraverseNode = function(node, callback) {
		if(node !== null) {
			inOrderTraverseNode(node.left, callback);
			callback(node.key);
			inOrderTraverseNode(node.right, callback);
		}
	}
	
	// 中序遍历（升序取值）
	this.inOrderTraverse = function(callback) {
		inOrderTraverseNode(root, callback);
	};
	
	let preOrderTraverseNode = function(node, callback) {
		if(node !== null) {
			callback(node.key);
			preOrderTraverseNode(node.left, callback);
			preOrderTraverseNode(node.right, callback);
		}
	}
	
	// 前序遍历（二叉树原顺序）
	this.preOrderTraverse = function(callback) {
		preOrderTraverseNode(root, callback);
	};
	
	let postOrderTraverseNode = function(node, callback) {
		if(node !== null) {
			postOrderTraverseNode(node.left, callback);
			postOrderTraverseNode(node.right, callback);
			callback(node.key);
		}
	}
	
	// 后序遍历
	this.postOrderTraverse = function(callback) {
		postOrderTraverseNode(root, callback);
	};
	
	let minNode = function(node) {
		if(node) {
			while(node && node.left !== null) {
				node = node.left;
			}
			return node.key;
		}
		return null;
	}
	
	// 获取最小值
	this.min = function() {
		return minNode(root);
	};
	
	let maxNode = function(node) {
		if(node) {
			while(node && node.right !== null) {
				node = node.right;
			}
			return node.key;
		}
		return null;
	}
	
	// 获取最大值
	this.max = function() {
		return maxNode(root);
	};
	
	let searchNode = function(node, key) {
		if(node === null) {
			return false;
		}
		if(key < node.key) {
			return searchNode(node.left, key);
		} else if(key > node.key) {
			return searchNode(node.right, key);
		} else {
			return true;
		}
	}
	
	// 查找
	this.search = function(key) {
		return searchNode(root, key);
	};
	
	let findMinNode = function(node) {
		if(node) {
			while(node && node.left !== null) {
				node = node.left;
			}
			return node;
		}
		return null;
	}
	
	let removeNode = function(node, key) {
		if(node === null) {
			return null;
		}
		if(key < node.key) {
			node.left = removeNode(node.left, key);
			return node;
		} else if(key > node.key) {
			node.right = removeNode(node.right, key);
			return node;
		} else {
			if(node.left === null && node.right === null) {
				node = null;
				return node;
			} else if(node.left === null) {
				node = node.right;
				return node;
			} else if(node.right === null) {
				node = node.left;
				return node;
			}
			var aux = findMinNode(node.right);
			node.key = aux.key;
			node.right = removeNode(node.right, aux.key);
			return node;
		}
	}
	
	// 删除
	this.remove = function(key) {
		root = removeNode(root, key);
	}
}

/**
 * 初始化文件上传绑定
 */
var initFileUpload = function(fileUploadParams) {
	let baseParams = {
		fileElmIds: null, fileUrls: null, fields: null, params: null, accepts: null, exts: null, autos: null, bindActions: null, 
		sizes: null, 
		chooseFns: null, beforeFns: null, doneFns: null, errorFns: null
	};
	if($.isPlainObject(fileUploadParams)) {
		$.extend(baseParams, fileUploadParams);
	}
	if($.isArray(baseParams.fileElmIds) && $.isArray(baseParams.fileUrls)) {
		$.each(baseParams.fileElmIds, function(fileElmIndex, fileElmObj) {
			layui.use('upload', function() {
				let upload = layui.upload;
				
				let fileUrlThis = baseParams.fileUrls[fileElmIndex];
				if(!fileUrlThis) {
					console.error('初始化文件上传绑定', '参数：文件上传url地址不正确');
					return false;
				}
				
				let fieldThis = 'file';
				if($.isArray(baseParams.fields) && baseParams.fields[fileElmIndex]) {
					fieldThis = baseParams.fields[fileElmIndex];
				}
				
				let paramsData = { };
				if($.isArray(baseParams.params) && $.isPlainObject(baseParams.params[fileElmIndex])){
					$.extend(paramsData, baseParams.params[fileElmIndex]);
				}
				
				let acceptThis = 'images';
				if($.isArray(baseParams.accepts) && baseParams.accepts[fileElmIndex]){
					acceptThis = baseParams.accepts[fileElmIndex];
				}
				
				let extsThis = 'jpg|png|gif|bmp|jpeg';
				if($.isArray(baseParams.exts) && baseParams.exts[fileElmIndex]){
					extsThis = baseParams.exts[fileElmIndex];
				}
				
				let sizeThis = 0;
				if($.isArray(baseParams.sizes) && $.isNumeric(baseParams.sizes[fileElmIndex])){
					sizeThis = baseParams.sizes[fileElmIndex];
				}
				
				let autoThis = true;
				let bindActionThis = '-';
				if($.isArray(baseParams.autos) && typeof baseParams.autos[fileElmIndex] === 'boolean'
					&& ((!baseParams.autos[fileElmIndex] && $.isArray(baseParams.bindActions) && baseParams.bindActions[fileElmIndex]) || baseParams.autos[fileElmIndex])) {
					autoThis = baseParams.autos[fileElmIndex];
					if(!baseParams.autos[fileElmIndex] && $.isArray(baseParams.bindActions) && baseParams.bindActions[fileElmIndex]) {
						bindActionThis = baseParams.bindActions[fileElmIndex];
					}
				}
				if(bindActionThis != '-') {
					bindActionThis = '#' + bindActionThis;
				}
				
				upload.render({
					elem : '#' + fileElmObj,
					url : fileUrlThis,
					field : fieldThis,
					data : paramsData,
					accept : acceptThis,
					exts : extsThis,
					size : sizeThis,
					auto : autoThis,
					bindAction : bindActionThis,
					choose : function(obj) {
						if($.isArray(baseParams.chooseFns) && typeof baseParams.chooseFns[fileElmIndex] === 'function') {
							baseParams.chooseFns[fileElmIndex](obj);
						}
					},
					before : function(obj) {
						layer.load();
						if($.isArray(baseParams.beforeFns) && typeof baseParams.beforeFns[fileElmIndex] === 'function') {
							baseParams.beforeFns[fileElmIndex](obj);
						}
					},
					done : function(res) {
						console.log("上传完成", res);
						layer.closeAll('loading');
						if($.isArray(baseParams.doneFns) && typeof baseParams.doneFns[fileElmIndex] === 'function') {
							baseParams.doneFns[fileElmIndex](res);
						}
					},
					error : function() {
						layer.closeAll('loading');
						if($.isArray(baseParams.errorFns) && typeof baseParams.errorFns[fileElmIndex] === 'function') {
							baseParams.errorFns[fileElmIndex]();
						}
					}
				});
			});
		});
	} else {
		if(!$.isArray(baseParams.fileElmIds)) {
			console.error('初始化文件上传绑定', '参数：选择文件触发节点dom 不是一个有效数组');
		}
		if(!$.isArray(baseParams.fileUrls)) {
			console.error('初始化文件上传绑定', '参数：文件上传url地址 不是一个有效数组');
		}
	}
};
