package cn.gk.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import cn.gk.model.comm.FileRecord;
import cn.gk.model.comm.PermissionC;
import cn.gk.model.comm.PermissionP;
import cn.gk.model.comm.StudentCard;
import cn.gk.model.comm.UserInfo;
import cn.gk.service.comm.PermissionCService;
import cn.gk.service.comm.PermissionPService;

import com.fr.json.JSONException;
import com.fr.json.JSONObject;

public class ComFun {

	/**
	 * 判断对象不为空
	 * 
	 * @param str
	 * @param flags
	 *            为可选参数，如果需要进一步判断List/Map/Array中的数量是否为0，可传入true；否则可不传（参数一为List/
	 *            Map/Array类型时有效）
	 * @return
	 */
	public static boolean strNull(Object str, boolean... flags) {
		if (str != null && str != "" && !str.equals("")) {
			if (flags != null && flags.length > 0 && flags[0]) {
				if (ArrayList.class.isInstance(str)) {
					if ((((List<?>) str).size() == 0)) {
						return false;
					}
				} else if (HashMap.class.isInstance(str)) {
					if ((((Map<?, ?>) str).size() == 0)) {
						return false;
					}
				} else if (str.getClass().isArray()) {
					try {
						if (JSONObject.valueToString(str).equals("[]")) {
							return false;
						}
					} catch (JSONException e) {
						return true;
					}
				}
			}
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 检查对象是否在对象数组中
	 * 
	 * @param objArr
	 * @param obj
	 * @return
	 */
	public static Boolean checkObjInArr(Object[] objArr, Object obj) {
		if (strNull(objArr, true)) {
			for (Object o : objArr) {
				if (o.equals(obj)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 根据用户权限获取用户导航栏JSON格式字符串
	 * 
	 * @param userPermissionMap
	 * @return
	 */
	public static String getNavigationJsonByUserPermission(List<Object[]> userPerList,
			PermissionPService permissionPService, PermissionCService permissionCService) {
		String navigationStr = "empty";
		List<Object> navigationList = null;
		JSONObject jsob = null;
		if (strNull(userPerList, true)) {
			navigationList = new ArrayList<>();
			// 根据用户权限生成导航菜单数据
			for (int i = 0; i < userPerList.size(); i++) {
				if (Integer.parseInt(userPerList.get(i)[2].toString()) == 1) {
					// 查询详细权限信息数据
					String perPId = (String) userPerList.get(i)[0];
					String perCId = (String) userPerList.get(i)[1];
					String[] perCIdArr = new String[] {};
					if (strNull(perCId)) {
						perCIdArr = perCId.split(",");
					}
					PermissionP permissionP = permissionPService.get(perPId);
					if (strNull(permissionP) && permissionP.getPermissionStatus() == 1) {
						if (strNull(perCIdArr, true)) {
							jsob = new JSONObject();
							try {
								jsob.put("name", permissionP.getPermissionName());
								// 编辑父级权限下的子级权限数组
								List<Object> second = new ArrayList<>();
								for (String cId : perCIdArr) {
									PermissionC permissionC = permissionCService.get(cId);
									second.add(
											new JSONObject("{ name: '" + permissionC.getPermissionName() + "', url: '"
													+ permissionC.getPermissionLink() + "', target: 'workspace' }"));
								}
								// 父级权限名称及对应子级权限
								jsob.put("arr", second.toArray());
							} catch (JSONException e) {
							}
							navigationList.add(jsob.toString());
						} else {
							List<PermissionC> permissionCs = permissionCService.getPermissionCListByPId(perPId, true);
							if (strNull(permissionCs, true)) {
								jsob = new JSONObject();
								try {
									jsob.put("name", permissionP.getPermissionName());
									// 编辑父级权限下的子级权限数组
									List<Object> second = new ArrayList<>();
									for (int j = 0; j < permissionCs.size(); j++) {
										second.add(new JSONObject("{ name: '" + permissionCs.get(j).getPermissionName()
												+ "', url: '" + permissionCs.get(j).getPermissionLink()
												+ "', target: 'workspace' }"));
									}
									// 父级权限名称及对应子级权限
									jsob.put("arr", second.toArray());
								} catch (JSONException e) {
								}
								navigationList.add(jsob.toString());
							}
						}
					}
				}
			}
			if (strNull(navigationList, true)) {
				navigationStr = Arrays.toString(navigationList.toArray());
			}
		}
		return navigationStr;
	}

	/**
	 * 获取用户信息
	 * 
	 * @param user
	 * @return
	 */
	public static String getUserInfo(UserInfo user) {
		return "登录账号: " + user.getUserName() + " =========== 卡类型: " + user.getUserCardType() + " =========== 手机号: "
				+ user.getMobile() + " ======== " + DateFormatUtil.dateToStr(new Date(), DateFormatUtil.TYPE);
	}

	/**
	 * 获取用户信息
	 * 
	 * @param stCard
	 * @return
	 */
	public static String getStudentCardInfo(StudentCard stCard) {
		return "学生卡登录账号: " + stCard.getCardNum() + " =========== 卡级别: " + stCard.getCardLevel() + " =========== 手机号: "
				+ stCard.getPhoneNum() + " ======== " + DateFormatUtil.dateToStr(new Date(), DateFormatUtil.TYPE);
	}

	/**
	 * 设置用户默认显示的页面信息
	 * 
	 * @param type
	 * @return
	 */
	public static String getUserDefaultPage(HttpSession session, Integer type, StudentCard studentCardInfo) {
		String defaulrPageAction = "";
		switch (type) {
		case 0: // 超级管理员
			defaulrPageAction = "toPermissionManager";
			break;
		case 1: // 普通管理员
			defaulrPageAction = "toPermissionManager";
			break;
		case 2: // 代理用户
			defaulrPageAction = "toStudentCardManager";
			break;
		case 3: // 专家用户
			defaulrPageAction = "toStudentCardManager";
			break;
		case 10: // 学生会员卡用户
			defaulrPageAction = "toSchoolRefer";
			break;
		default:
			defaulrPageAction = "";
			break;
		}
		// 如果是學生卡用戶登錄，判斷改卡是否需要激活
		if (type == 10 && ComFun.strNull(studentCardInfo.getUserName())) {
			session.setAttribute(Constants.DEFAULT_ACTION_SESSION, defaulrPageAction);
		} else {
			if (strNull(session.getAttribute(Constants.DEFAULT_ACTION_SESSION))) {
				defaulrPageAction = (String) session.getAttribute(Constants.DEFAULT_ACTION_SESSION);
			}
		}
		return defaulrPageAction;
	}

	/**
	 * 获取相关列表的页面值
	 * 
	 * @param session
	 * @param aboutStr
	 * @param toPageIndex
	 * @return
	 */
	public static Integer getAboutPageIndex(HttpSession session, String aboutStr, String toPageIndex) {
		if (strNull(session) && strNull(aboutStr)) {
			if (strNull(toPageIndex)) {
				int toIndex = Integer.parseInt(toPageIndex);
				if (Integer.parseInt(toPageIndex) < 1) {
					toIndex = 1;
				}
				session.setAttribute(aboutStr, toIndex);
				return toIndex;
			} else {
				Object aboutPageIndex = session.getAttribute(aboutStr);
				if (strNull(aboutPageIndex)) {
					return (Integer) aboutPageIndex;
				}
			}
		}
		return 1;
	}

	public static Map<String, String[]> getUserBasePerByType(Integer groupType) {
		Map<String, String[]> basePerMap = null;
		switch (groupType) {
		case 0:
			basePerMap = Constants.basePerSetMap.get("superManager");
			break;
		case 1:
			basePerMap = Constants.basePerSetMap.get("normalManager");
			break;
		case 2:
			basePerMap = Constants.basePerSetMap.get("agencyMan");
			break;
		case 3:
			basePerMap = Constants.basePerSetMap.get("expertMan");
			break;
		case 10:
			basePerMap = Constants.basePerSetMap.get("studentMan");
			break;
		default:
			break;
		}
		return basePerMap;
	}

	public static String createStudentCardNum(int numLength) {
		char[] allChar = new char[] { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p',
				'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A',
				'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V',
				'W', 'X', 'Y', 'Z' };
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String timeStamp = sdf.format(new Date());
		String randomNum = MD5andKL.MD5(timeStamp);
		char[] randomCharArr = randomNum.toCharArray();
		Random random = new Random();
		StringBuilder getRan = new StringBuilder();
		for (int by = 0; by < (numLength - 3); by++) {
			int index = random.nextInt(randomCharArr.length);
			char b = randomCharArr[index];
			getRan.append(b);
		}
		Random random2 = new Random();
		for (int j = 0; j < 3; j++) {
			int index = random2.nextInt(allChar.length);
			getRan.append(allChar[index]);
		}
		return getRan.toString();
	}

	/**
	 * 获取页面查询参数
	 * 
	 * @param request
	 * @category 限定的页面查询参数传递为：searchVal
	 * @return
	 */
	public static JSONObject getSearchParams(HttpServletRequest request) {
		String searchVal = request.getParameter("searchVal");
		JSONObject searchValJson = null;
		try {
			if (strNull(searchVal)) {
				searchValJson = new JSONObject(searchVal);
			}
		} catch (JSONException e) {
		}
		return searchValJson;
	}

	/**
	 * 获取院校类型
	 * 
	 * @return
	 */
	public static Map<String, String> getSchoolTypeMap() {
		Map<String, String> schoolTypeMap = new LinkedHashMap<>();
		schoolTypeMap.put("zonghe", "综合类");
		schoolTypeMap.put("ligong", "理工类");
		schoolTypeMap.put("nonglin", "农林类");
		schoolTypeMap.put("yiyao", "医药类");
		schoolTypeMap.put("shifan", "师范类");
		schoolTypeMap.put("yuyan", "语言类");
		schoolTypeMap.put("caijing", "财经类");
		schoolTypeMap.put("zhengfa", "政法类");
		schoolTypeMap.put("tiyu", "体育类");
		schoolTypeMap.put("yishu", "艺术类");
		schoolTypeMap.put("minzu", "民族类");
		schoolTypeMap.put("junshi", "军事类");
		schoolTypeMap.put("qita", "其他");
		return schoolTypeMap;
	}

	/**
	 * 获取院校所在省份
	 * 
	 * @return
	 */
	public static Map<String, String> getSchoolLocationMap() {
		Map<String, String> schoolLocationMap = new LinkedHashMap<>();
		schoolLocationMap.put("beijing", "北京");
		schoolLocationMap.put("tianjin", "天津");
		schoolLocationMap.put("hebei", "河北");
		schoolLocationMap.put("henan", "河南");
		schoolLocationMap.put("shandong", "山东");
		schoolLocationMap.put("shanxi1", "山西");
		schoolLocationMap.put("shanxi2", "陕西");
		schoolLocationMap.put("neimenggu", "内蒙古");
		schoolLocationMap.put("liaoning", "辽宁");
		schoolLocationMap.put("jilin", "吉林");
		schoolLocationMap.put("heilongjiang", "黑龙江");
		schoolLocationMap.put("shanghai", "上海");
		schoolLocationMap.put("jiangsu", "江苏");
		schoolLocationMap.put("anhui", "安徽");
		schoolLocationMap.put("jiangxi", "江西");
		schoolLocationMap.put("hubei", "湖北");
		schoolLocationMap.put("hunan", "湖南");
		schoolLocationMap.put("chongqing", "重庆");
		schoolLocationMap.put("sichuan", "四川");
		schoolLocationMap.put("guizhou", "贵州");
		schoolLocationMap.put("yunnan", "云南");
		schoolLocationMap.put("guangdong", "广东");
		schoolLocationMap.put("guangxi", "广西");
		schoolLocationMap.put("fujian", "福建");
		schoolLocationMap.put("gansu", "甘肃");
		schoolLocationMap.put("ninxia", "宁夏");
		schoolLocationMap.put("xinjiang", "新疆");
		schoolLocationMap.put("xizang", "西藏");
		schoolLocationMap.put("hainan", "海南");
		schoolLocationMap.put("zhejiang", "浙江");
		schoolLocationMap.put("qinghai", "青海");
		schoolLocationMap.put("xianggang", "香港");
		schoolLocationMap.put("aomen", "澳门");
		return schoolLocationMap;
	}

	/**
	 * 获取院校学历层次
	 * 
	 * @return
	 */
	public static Map<String, String> getSchoolEducationMap() {
		Map<String, String> schoolEducationMap = new LinkedHashMap<>();
		schoolEducationMap.put("ptbk", "普通本科");
		schoolEducationMap.put("dlxy", "独立学院");
		schoolEducationMap.put("gzgz", "高职高专");
		schoolEducationMap.put("zwhzbx", "中外合作办学");
		schoolEducationMap.put("ycjyxy", "远程教育学院");
		schoolEducationMap.put("hndxm", "HND项目");
		schoolEducationMap.put("crjy", "成人教育");
		schoolEducationMap.put("qt", "其他");
		return schoolEducationMap;
	}

	/**
	 * 获取院校学历类型
	 * 
	 * @return
	 */
	public static Map<String, String> getSchoolEducationTypeMap() {
		Map<String, String> schoolEducationTypeMap = new LinkedHashMap<>();
		schoolEducationTypeMap.put("benke", "本科");
		schoolEducationTypeMap.put("zhuanke", "专科");
		return schoolEducationTypeMap;
	}

	/**
	 * 保存上传的图片
	 * 
	 * @param file
	 */
	public static FileRecord saveUploadImg(HttpServletRequest request, File file, String fileName,
			String fileContentType, Map<String, Object> resultData) {
		String filePath = request.getServletContext().getRealPath("/uploadFile/images/");
		File fileLocation = new File(filePath);
		if (!fileLocation.exists()) {
			fileLocation.mkdirs();
		}
		String fileBaseName = FilenameUtils.getBaseName(fileName);
		String fileExtName = FilenameUtils.getExtension(fileName);
		long fileSize = FileUtils.sizeOf(file);
		String newFileName = getStrByTime();

		FileRecord fileRecord = new FileRecord();
		fileRecord.setFileName(newFileName);
		fileRecord.setOldFileName(fileBaseName);
		fileRecord.setFileExtName(fileExtName);
		fileRecord.setFileSize(fileSize);
		fileRecord.setFilePath(
				request.getServletContext().getContextPath() + "/uploadFile/images/" + newFileName + "." + fileExtName);
		if (strNull(resultData)) {
			resultData.put("filePath", fileRecord.getFilePath());
		}

		try {
			InputStream in = new FileInputStream(file);
			File uploadFile = new File(fileLocation, newFileName + "." + fileExtName);
			OutputStream out = new FileOutputStream(uploadFile);
			byte[] buffer = new byte[1024 * 1024];
			int length;
			while ((length = in.read(buffer)) > 0) {
				out.write(buffer, 0, length);
			}
			in.close();
			out.close();
			if (strNull(resultData)) {
				resultData.put("code", Constants.UPLOAD_SUCCESS);
			}
		} catch (FileNotFoundException e) {
			if (strNull(resultData)) {
				resultData.put("code", Constants.UPLOAD_FAILT);
			}
			return null;
		} catch (IOException e) {
			if (strNull(resultData)) {
				resultData.put("code", Constants.UPLOAD_FAILT);
			}
			return null;
		}
		return fileRecord;
	}

	/**
	 * 根据日期时间获取字符串（用于文件重命名）
	 * 
	 * @return
	 */
	public static String getStrByTime() {
		return DateFormatUtil.dateToStr(new Date(), DateFormatUtil.TYPE7);
	}

}
