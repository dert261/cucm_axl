package ru.obelisk.cucmaxl.database.models.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ru.obelisk.cucmaxl.database.models.entity.CucmAxlPort;
import ru.obelisk.cucmaxl.database.models.entity.CucmDevice;


public interface CucmDeviceRepository extends JpaRepository<CucmDevice, Integer> {

	@Query("SELECT cucmDevice FROM CucmDevice cucmDevice LEFT JOIN FETCH cucmDevice.lines WHERE cucmDevice.pkid = :pkid")
	CucmDevice findByPkID(@Param("pkid") String pkid);
	
	@Query("SELECT cucmDevice FROM CucmDevice cucmDevice LEFT JOIN FETCH cucmDevice.lines WHERE cucmDevice.cucmAxlPort = :cucmAxlPort")
	List<CucmDevice> findAllByCucm(@Param("cucmAxlPort") CucmAxlPort cucmAxlPort);
	
	@Query("SELECT cucmDevice FROM CucmDevice cucmDevice"
			+ " LEFT JOIN FETCH cucmDevice.lines devLines"
			+ " LEFT JOIN FETCH devLines.line line"
			
			+ " LEFT JOIN FETCH cucmDevice.cucmAxlPort"
			+ " LEFT JOIN FETCH cucmDevice.userId"
						
			+ " WHERE cucmDevice.cucmAxlPort = :cucmAxlPort")
	List<CucmDevice> findAllByCucmWithRelational(@Param("cucmAxlPort") CucmAxlPort cucmAxlPort);
}
