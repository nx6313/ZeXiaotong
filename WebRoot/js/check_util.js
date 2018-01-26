/*******************************************************************************
 * 名称：CheckUtil 功能：各种校验
 ******************************************************************************/
function CheckUtil() {
}

// 校验是否为空(先删除两边空格再验证)
CheckUtil.isNull = function(str) {
	if (null == str || "" == $.trim(str)) {
		return true;
	} else {
		return false;
	}
};

// 校验是否全是数字
CheckUtil.isNumber = function(str) {
	str = $.trim(str);
	var patrn = /^\d+$/;
	return patrn.test(str);
};

// 校验是否只包含数字字母
CheckUtil.isNumberOrStr = function(str) {
	var patrn = /[A-Za-z].*[0-9]|[0-9].*[A-Za-z]|[0-9]|[A-Za-z]/;
	return patrn.test(str);
};
// 校验是否是小写字母
CheckUtil.isStr = function(str) {
	var patrn = /^[a-z]+$/;
	return patrn.test(str);
};
// 校验是否是整数
CheckUtil.isInteger = function(str) {
	var patrn = /^([+-]?)(\d+)$/;
	return patrn.test(str);
};

// 校验是否为正整数
CheckUtil.isPlusInteger = function(str) {
	var patrn = /^([+]?)(\d+)$/;
	return patrn.test(str);
};

// 校验是否为负整数
CheckUtil.isMinusInteger = function(str) {
	var patrn = /^-(\d+)$/;
	return patrn.test(str);
};

// 校验是否为正数
CheckUtil.isPlusNumber = function(str) {
	return CheckUtil.isPlusInteger(str) || CheckUtil.isPlusFloat(str);
}

// 校验是否为浮点数
CheckUtil.isFloat = function(str) {
	var patrn = /^([+-]?)\d*\.\d+$/;
	return patrn.test(str);
};

// 校验是否为正浮点数
CheckUtil.isPlusFloat = function(str) {
	var patrn = /^([+]?)\d*\.\d+$/;
	return patrn.test(str);
};

// 校验是否为负浮点数
CheckUtil.isMinusFloat = function(str) {
	var patrn = /^-\d*\.\d+$/;
	return patrn.test(str);
};

// 校验是否仅中文
CheckUtil.isChinese = function(str) {
	var patrn = /[\u4E00-\u9FA5\uF900-\uFA2D]+$/;
	return patrn.test(str);
};

// 校验是否包含汉字
CheckUtil.isExistChinese = function(strValue) {
	var chrCode;
	for ( var iChar = 0; iChar < strValue.length; iChar++) {
		chrCode = strValue.charCodeAt(iChar);
		if (parseInt(chrCode) > 255) {
			return true;
		}
	}
	return false;
};

// 校验是否仅ACSII字符
CheckUtil.isAcsii = function(str) {
	var patrn = /^[\x00-\xFF]+$/;
	return patrn.test(str);
};

// 校验手机号码
CheckUtil.isMobile = function(str) {
	var patrn = /^[1][3458]\d{9}$/;
	return patrn.test(str);
};

// 校验电话号码
CheckUtil.isPhone = function(str) {
	var patrn = /^(0[\d]{2,3}-)?\d{6,8}(-\d{3,4})?$/;
	return patrn.test(str);
};

// 校验URL地址
CheckUtil.isUrl = function(str) {
	var patrn = /^http[s]?:\/\/[\w-]+(\.[\w-]+)+([\w-\.\/?%&=]*)?$/;
	return patrn.test(str);
};

// 校验电邮地址
CheckUtil.isEmail = function(str) {
	var patrn = /^[\w-]+@[\w-]+(\.[\w-]+)+$/;
	return patrn.test(str);
};

// 校验邮编
CheckUtil.isZipCode = function(str) {
	var patrn = /^\d{6}$/;
	return patrn.test(str);
};

// 校验合法时间
CheckUtil.isDate = function(str) {
	if (!/\d{4}(\.|\/|\-)\d{1,2}(\.|\/|\-)\d{1,2}/.test(str)) {
		return false;
	}
	var r = str.match(/\d{1,4}/g);
	if (r == null) {
		return false;
	}
	var d = new Date(r[0], r[1] - 1, r[2]);
	return (d.getFullYear() == r[0] && (d.getMonth() + 1) == r[1] && d
			.getDate() == r[2]);
};

// 校验字符串的长度是否在范围内
CheckUtil.isStringInScope = function(str, start, end) {
	var patrn = /^(\w){start, end}$/;
	return patrn.test(str);
};

// 判断是否为闰年
function isRunNian(str) {
	if (str % 4 != 0) {
		return false;
	} else {
		if (str % 100 != 0) {
			return true;
		} else {
			if (str % 400 == 0) {
				return true;
			} else {
				return false;
			}
		}
	}
}
//判断是否为字母或数字
CheckUtil.isNumOrString = function(str) {
    var Regx = /^[A-Za-z0-9]*$/;
    return Regx.test(str);
}

CheckUtil.inArray = function(arr, obj) {
	var i = arr.length;
	while (i--) {
		if (arr[i].toLowerCase() == obj.toLowerCase()) {
			return true;
		}
	}
	return false;
}
