package cn.gk.dao.comm.impl;

import org.directwebremoting.annotations.RemoteProxy;
import org.directwebremoting.spring.SpringCreator;
import org.springframework.stereotype.Repository;

import cn.gk.dao.base.impl.BaseDaoImpl;
import cn.gk.dao.comm.FileRecordDao;
import cn.gk.model.comm.FileRecord;

@Repository("fileRecordDao")
@RemoteProxy(creator = SpringCreator.class)
public class FileRecordDaoImpl extends BaseDaoImpl<FileRecord, String> implements FileRecordDao {

}
