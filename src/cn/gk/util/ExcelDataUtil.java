package cn.gk.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import cn.gk.model.comm.FileRecord;
import cn.gk.model.student.Academy;
import cn.gk.service.comm.FileRecordService;

public class ExcelDataUtil {

	/**
	 * 读取excel数据到指定实体中
	 * 
	 * @param excelFile
	 * @param startReadRow
	 * @param clas
	 * @param request
	 * @param fileRecordService
	 * @param loginUserId
	 */
	public static List<?> readExcelData(File excelFile, String excelFileName, int startReadRow, Class<?> clas,
			HttpServletRequest request, FileRecordService fileRecordService, String loginUserId) {
		try {
			if (excelFile.exists()) {
				String excelFileExtName = FilenameUtils.getExtension(excelFileName);

				InputStream excelStream = new FileInputStream(excelFile);
				Workbook book = null;
				if (excelFileExtName.equals("xls")) {
					book = new HSSFWorkbook(excelStream);
				} else if (excelFileExtName.equals("xlsx")) {
					book = new XSSFWorkbook(excelStream);
				}

				if (book != null) {
					Sheet sheet = book.getSheetAt(0);

					if (clas.equals(Academy.class)) {
						List<Academy> excelDataList = new ArrayList<>();
						for (Row row : sheet) {
							int rowNum = row.getRowNum();
							if (rowNum < startReadRow) {
								continue;
							}
							Academy academy = (Academy) clas.newInstance();
							for (Cell cell : row) {
								String cellContent = null;
								if (cell.getCellTypeEnum().equals(CellType._NONE)
										|| cell.getCellTypeEnum().equals(CellType.BLANK)
										|| cell.getCellTypeEnum().equals(CellType.ERROR)) {
									cellContent = "";
								} else if (cell.getCellTypeEnum().equals(CellType.STRING)) {
									cellContent = cell.getStringCellValue();
								} else if (cell.getCellTypeEnum().equals(CellType.BOOLEAN)) {
									cellContent = String.valueOf(cell.getBooleanCellValue());
								} else if (cell.getCellTypeEnum().equals(CellType.FORMULA)) {
									cellContent = cell.getStringCellValue();
								} else if (cell.getCellTypeEnum().equals(CellType.NUMERIC)) {
									if (HSSFDateUtil.isCellDateFormatted(cell)) {
										cellContent = DateFormatUtil.dateToStr(
												HSSFDateUtil.getJavaDate(cell.getNumericCellValue()),
												DateFormatUtil.TYPE);
									} else {
										cellContent = String.valueOf(cell.getNumericCellValue());
									}
								}
								if (ComFun.strNull(cellContent)) {
									cellContent = cellContent.trim();
								}
								int colIndex = cell.getColumnIndex();
								switch (colIndex) {
								case 0:
									// 院校Logo
									File schoolLogoFile = new File(cellContent);
									if (schoolLogoFile.exists()) {
										String fileName = FilenameUtils.getName(schoolLogoFile.getName());
										FileRecord fileRecord = ComFun.saveUploadImg(request, schoolLogoFile, fileName,
												null, null);
										if (ComFun.strNull(fileRecord)) {
											fileRecord.setCreateInfo(loginUserId);
											fileRecordService.save(fileRecord);
											academy.setSchoolLogo(fileRecord.getFilePath());
										}
									}
									break;
								case 1:
									// 院校名称
									if (ComFun.strNull(cellContent)) {
										academy.setSchoolName(cellContent);
									}
									break;
								case 2:
									// 院校类型
									if (ComFun.strNull(cellContent)) {
										academy.setSchoolType(cellContent);
									}
									break;
								case 3:
									// 所在地
									if (ComFun.strNull(cellContent)) {
										academy.setLocation(cellContent);
									}
									break;
								case 4:
									// 学历层次
									if (ComFun.strNull(cellContent)) {
										academy.setEducation(cellContent);
									}
									break;
								case 5:
									// 学历类型
									if (ComFun.strNull(cellContent)) {
										academy.setEducationType(cellContent);
									}
									break;
								case 6:
									// 招生办电话
									if (ComFun.strNull(cellContent)) {
										academy.setAdmissionOfficePhone(cellContent);
									}
									break;
								case 7:
									// 电子邮箱
									if (ComFun.strNull(cellContent)) {
										academy.setEmail(cellContent);
									}
									break;
								case 8:
									// 通讯地址
									if (ComFun.strNull(cellContent)) {
										academy.setAddress(cellContent);
									}
									break;
								case 9:
									// 招生网址
									if (ComFun.strNull(cellContent)) {
										academy.setAdmissionNet(cellContent);
									}
									break;
								case 10:
									// 全球排名
									if (ComFun.strNull(cellContent)) {
										academy.setGlobalHeat(cellContent);
									}
									break;
								case 11:
									// 类别排名
									if (ComFun.strNull(cellContent)) {
										academy.setClassHeat(cellContent);
									}
									break;
								case 12:
									// 院校简介
									if (ComFun.strNull(cellContent)) {
										academy.setIntro(cellContent);
									}
									break;
								case 13:
									// 就业情况
									if (ComFun.strNull(cellContent)) {
										academy.setEmployment(cellContent);
									}
									break;
								default:
									break;
								}
							}
							excelDataList.add(academy);
						}
						return excelDataList;
					} else {
						System.out.println("需代码补充 " + clas.getSimpleName() + " 导入类型");
					}
					book.close();
				} else {
					System.out.println("Excel文件【" + excelFileExtName + "】格式不支持");
				}
			} else {
				System.out.println("未找到指定Excel文件");
			}
		} catch (InstantiationException | IllegalAccessException e) {
		} catch (Exception e) {
		}
		return null;
	}
}
