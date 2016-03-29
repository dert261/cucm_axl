package ru.obelisk.cucmaxl.database.models.service;

import java.util.List;

import ru.obelisk.cucmaxl.database.models.entity.CucmDevice;
import ru.obelisk.cucmaxl.database.models.entity.CucmDeviceLine;
import ru.obelisk.cucmaxl.database.models.entity.CucmLine;

public interface CucmDeviceLineService {

	CucmDeviceLine addDeviceLine(CucmDeviceLine deviceLine);

	CucmDeviceLine editDeviceLine(CucmDeviceLine formDeviceLine);

	void deleteDeviceLine(int id);

	List<CucmDeviceLine> getAllDeviceLine();

	CucmDeviceLine findByDevice(CucmDevice device);
	CucmDeviceLine findByLine(CucmLine line);
	CucmDeviceLine findByDeviceAndLine(CucmDevice device, CucmLine line);
}