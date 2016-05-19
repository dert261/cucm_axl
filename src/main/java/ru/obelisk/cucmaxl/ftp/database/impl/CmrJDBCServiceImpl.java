package ru.obelisk.cucmaxl.ftp.database.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.obelisk.cucmaxl.ftp.database.CmrJDBCService;

@Service
public class CmrJDBCServiceImpl implements CmrJDBCService{

	@Autowired JdbcTemplate jdbcTemplate;
	
	@Override
	@Transactional
	public void execute(String query) {
		jdbcTemplate.execute(query);
	}
}
