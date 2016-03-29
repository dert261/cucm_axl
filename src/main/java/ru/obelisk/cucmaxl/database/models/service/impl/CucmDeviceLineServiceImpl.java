package ru.obelisk.cucmaxl.database.models.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.obelisk.cucmaxl.database.models.entity.CucmDevice;
import ru.obelisk.cucmaxl.database.models.entity.CucmDeviceLine;
import ru.obelisk.cucmaxl.database.models.entity.CucmLine;
import ru.obelisk.cucmaxl.database.models.repository.CucmDeviceLineRepository;
import ru.obelisk.cucmaxl.database.models.service.CucmDeviceLineService;

import java.util.List;
 
@Service
public class CucmDeviceLineServiceImpl implements CucmDeviceLineService {
 
    @Autowired private CucmDeviceLineRepository deviceLineRepository;
        
    /* (non-Javadoc)
	 * @see ru.obelisk.cucmaxl.database.models.entity.service.impl.DeviceLineService#addDeviceLine(ru.obelisk.cucmaxl.database.models.entity.DeviceLine)
	 */
    @Override
	
    public CucmDeviceLine addDeviceLine(CucmDeviceLine deviceLine) {
    	CucmDeviceLine savedDeviceLine = deviceLineRepository.saveAndFlush(deviceLine);
        return savedDeviceLine;
    }
    
    /* (non-Javadoc)
	 * @see ru.obelisk.cucmaxl.database.models.entity.service.impl.DeviceLineService#editDeviceLine(ru.obelisk.cucmaxl.database.models.entity.DeviceLine)
	 */
    @Override
	
    public CucmDeviceLine editDeviceLine(CucmDeviceLine formDeviceLine) {
    	return deviceLineRepository.saveAndFlush(formDeviceLine);
    }
 
    /* (non-Javadoc)
	 * @see ru.obelisk.cucmaxl.database.models.entity.service.impl.DeviceLineService#deleteDeviceLine(int)
	 */
    @Override
	
    public void deleteDeviceLine(int id) {
    	deviceLineRepository.delete(id);
    }

	@Override
	public List<CucmDeviceLine> getAllDeviceLine() {
		return deviceLineRepository.findAll();
	}

	@Override
	public CucmDeviceLine findByDevice(CucmDevice device) {
		return deviceLineRepository.findByDevice(device);
	}

	@Override
	public CucmDeviceLine findByLine(CucmLine line) {
		return deviceLineRepository.findByLine(line);
	}

	@Override
	public CucmDeviceLine findByDeviceAndLine(CucmDevice device, CucmLine line) {
		return deviceLineRepository.findByDeviceAndLine(device, line);
	}

	
     
    
}