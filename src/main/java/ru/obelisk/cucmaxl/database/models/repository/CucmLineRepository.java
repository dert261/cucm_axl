package ru.obelisk.cucmaxl.database.models.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ru.obelisk.cucmaxl.database.models.entity.CucmAxlPort;
import ru.obelisk.cucmaxl.database.models.entity.CucmLine;

public interface CucmLineRepository extends JpaRepository<CucmLine, Integer> {

	@Query("SELECT cucmLine FROM CucmLine cucmLine LEFT JOIN FETCH cucmLine.devices WHERE cucmLine.pkid = :pkid")
	CucmLine findByPkID(@Param("pkid") String pkid);
	
	
	@Query("SELECT DISTINCT cucmLine FROM CucmLine cucmLine"
			+ " LEFT JOIN cucmLine.devices devLine"
			+ " LEFT JOIN devLine.device device"
			+ " WHERE device.cucmAxlPort = :axlPort")
	List<CucmLine> findByPkCucmAxlPort(@Param("axlPort") CucmAxlPort axlPort);
}
