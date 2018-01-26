package cn.gk.dao.comm;

import cn.gk.dao.base.BaseDao;
import cn.gk.model.comm.FileRecord;

public interface FileRecordDao extends BaseDao<FileRecord, String> {
	public static final String HQL_LIST = "from FileRecord where state > 0 ";
	public static final String HQL_LIST_DELETE = "from FileRecord where state = 0 ";
	public static final String HQL_LIST_COUNT = "select count(f) from FileRecord f where state > 0";
	
}
