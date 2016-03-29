package ru.obelisk.cucmaxl.database.models.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ru.obelisk.cucmaxl.database.models.entity.CucmDevice;
import ru.obelisk.cucmaxl.database.models.entity.CucmDeviceLine;
import ru.obelisk.cucmaxl.database.models.entity.CucmLine;

public interface CucmDeviceLineRepository extends JpaRepository<CucmDeviceLine, Integer> {

	@Query("SELECT cucmDeviceLine FROM CucmDeviceLine cucmDeviceLine WHERE cucmDeviceLine.device = :device ORDER BY cucmDeviceLine.lineIndex")
	CucmDeviceLine findByDevice(@Param("device") CucmDevice device);
	
	@Query("SELECT cucmDeviceLine FROM CucmDeviceLine cucmDeviceLine WHERE cucmDeviceLine.line = :line ORDER BY cucmDeviceLine.lineIndex")
	CucmDeviceLine findByLine(@Param("line") CucmLine line);
	
	@Query("SELECT cucmDeviceLine FROM CucmDeviceLine cucmDeviceLine"
			+ " LEFT JOIN FETCH cucmDeviceLine.device"
			+ " LEFT JOIN FETCH cucmDeviceLine.line"
			+ " WHERE cucmDeviceLine.device = :device"
			+ " AND cucmDeviceLine.line = :line"
			+ " ORDER BY cucmDeviceLine.lineIndex")
	CucmDeviceLine findByDeviceAndLine(@Param("device") CucmDevice device, @Param("line") CucmLine line);
}
