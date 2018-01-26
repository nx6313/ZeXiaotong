package cn.gk.service.comm.impl;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import cn.gk.dao.base.BaseDao;
import cn.gk.dao.comm.FileRecordDao;
import cn.gk.model.comm.FileRecord;
import cn.gk.service.base.impl.BaseServiceImpl;
import cn.gk.service.comm.FileRecordService;

/**
 * 文件上传记录表Service实现
 */
@Service("fileRecordService")
public class FileRecordServiceImpl extends BaseServiceImpl<FileRecord, String> implements FileRecordService {
	@SuppressWarnings("unused")
	private FileRecordDao fileRecordDao;

	@Resource
	@Qualifier("fileRecordDao")
	@Override
	public void setBaseDao(BaseDao<FileRecord, String> baseDao) {
		this.baseDao = baseDao;
		// 注意要强制转换
		this.fileRecordDao = (FileRecordDao) baseDao;
	}

}
