package ru.obelisk.cucmaxl.database.models.service;

import java.util.List;

import ru.obelisk.cucmaxl.database.models.entity.CucmAxlPort;
import ru.obelisk.cucmaxl.database.models.entity.CucmLine;

public interface CucmLineService {

	CucmLine addLine(CucmLine line);

	CucmLine editLine(CucmLine formLine);

	void deleteLine(int id);

	List<CucmLine> getAllLine();

	CucmLine findByPkID(String pkid);
	
	List<CucmLine> findByPkCucmAxlPort(CucmAxlPort axlPort); 
}