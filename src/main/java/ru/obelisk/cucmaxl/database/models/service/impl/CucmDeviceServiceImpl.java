package ru.obelisk.cucmaxl.database.models.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.obelisk.cucmaxl.database.models.entity.CucmAxlPort;
import ru.obelisk.cucmaxl.database.models.entity.CucmDevice;
import ru.obelisk.cucmaxl.database.models.repository.CucmDeviceRepository;
import ru.obelisk.cucmaxl.database.models.service.CucmDeviceService;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
 
@Service
public class CucmDeviceServiceImpl implements CucmDeviceService {
 
    @Autowired
    private CucmDeviceRepository deviceRepository;
    
    @PersistenceContext
    private EntityManager entityManager;
    
    /* (non-Javadoc)
	 * @see ru.obelisk.cucmaxl.database.models.entity.service.impl.DeviceService#addDevice(ru.obelisk.cucmaxl.database.models.entity.Device)
	 */
    @Override
	
    public CucmDevice addDevice(CucmDevice device) {
    	CucmDevice savedDevice = deviceRepository.saveAndFlush(device);
        return savedDevice;
    }
    
    /* (non-Javadoc)
	 * @see ru.obelisk.cucmaxl.database.models.entity.service.impl.DeviceService#editDevice(ru.obelisk.cucmaxl.database.models.entity.Device)
	 */
    @Override
	
    public CucmDevice editDevice(CucmDevice formDevice) {
    	return deviceRepository.saveAndFlush(formDevice);
    }
 
    /* (non-Javadoc)
	 * @see ru.obelisk.cucmaxl.database.models.entity.service.impl.DeviceService#deleteDevice(int)
	 */
    @Override
	
    public void deleteDevice(int id) {
    	deviceRepository.delete(id);
    }

	@Override
	public List<CucmDevice> getAllDevice() {
		return deviceRepository.findAll();
	}
	
	@Override
	public List<CucmDevice> findAllWithRelational(CucmAxlPort axlPort) {
		return deviceRepository.findAllByCucmWithRelational(axlPort);
	}


	@Override
	public CucmDevice findByPkID(String pkid) {
		return deviceRepository.findByPkID(pkid);
	}
	
	@Override
	public List<CucmDevice> getAllDeviceByCucm(CucmAxlPort cucmAxlPort) {
		return deviceRepository.findAllByCucm(cucmAxlPort);
	}
     
    
}