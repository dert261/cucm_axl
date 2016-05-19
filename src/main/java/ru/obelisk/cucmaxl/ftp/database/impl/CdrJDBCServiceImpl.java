package ru.obelisk.cucmaxl.ftp.database.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.obelisk.cucmaxl.ftp.database.CdrJDBCService;

@Service
public class CdrJDBCServiceImpl implements CdrJDBCService{

	@Autowired JdbcTemplate jdbcTemplate;
	
	@Override
	@Transactional
	public void execute(String query) {
		jdbcTemplate.execute(query);
	}
}
