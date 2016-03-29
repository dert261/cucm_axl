package ru.obelisk.cucmaxl.database.models.service;

import java.util.List;

import ru.obelisk.cucmaxl.database.models.entity.CucmAxlPort;
import ru.obelisk.cucmaxl.database.models.entity.CucmDevice;

public interface CucmDeviceService {

	CucmDevice addDevice(CucmDevice device);

	CucmDevice editDevice(CucmDevice formDevice);

	void deleteDevice(int id);

	List<CucmDevice> getAllDevice();
	
	List<CucmDevice> getAllDeviceByCucm(CucmAxlPort cucmAxlPort);
	
	List<CucmDevice> findAllWithRelational(CucmAxlPort axlPort);

	CucmDevice findByPkID(String pkid);
}