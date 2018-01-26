package cn.gk.model.comm;

import cn.gk.model.base.BaseClass;

public class FileRecord extends BaseClass {
	private static final long serialVersionUID = 1L;
	private String id;
	/**
	 * 新文件名
	 */
	private String fileName;
	/**
	 * 旧文件名
	 */
	private String oldFileName;
	/**
	 * 文件扩展名
	 */
	private String fileExtName;
	/**
	 * 文件大小
	 */
	private long fileSize;
	/**
	 * 文件路径
	 */
	private String filePath;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getOldFileName() {
		return oldFileName;
	}

	public void setOldFileName(String oldFileName) {
		this.oldFileName = oldFileName;
	}

	public String getFileExtName() {
		return fileExtName;
	}

	public void setFileExtName(String fileExtName) {
		this.fileExtName = fileExtName;
	}

	public long getFileSize() {
		return fileSize;
	}

	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
}
