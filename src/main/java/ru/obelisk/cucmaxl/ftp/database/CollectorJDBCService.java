package ru.obelisk.cucmaxl.ftp.database;

import java.util.List;
import ru.obelisk.cucmaxl.ftp.database.entity.Collector;

public interface CollectorJDBCService {
	public List<Collector> findAll();
	public List<Collector> findAllWithFtpConfigRelation();
}
