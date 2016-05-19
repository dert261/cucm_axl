package ru.obelisk.cucmaxl.ftp.database.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import ru.obelisk.cucmaxl.ftp.database.CollectorJDBCService;
import ru.obelisk.cucmaxl.ftp.database.entity.Collector;

@Service
public class CollectorJDBCServiceImpl implements CollectorJDBCService{

	@Autowired JdbcTemplate jdbcTemplate;

	@Override
	public List<Collector> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Collector> findAllWithFtpConfigRelation() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
