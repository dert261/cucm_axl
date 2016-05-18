package ru.obelisk.cucmaxl.cucm.service;

import ru.obelisk.cucmaxl.cucm.utils.CucmExportDevice;
import ru.obelisk.database.models.entity.CucmAxlPort;
import ru.obelisk.cucmaxl.web.controllers.cme.viewtypes.RouterExportDetail;
import ru.obelisk.cucmaxl.web.controllers.utils.CsvChangeNumber;

public interface CucmWithDBService {

	/* (non-Javadoc)
	 * @see ru.obelisk.cucmaxl.cucm.service.impl.CucmService#cucmSQLQuery()
	 */
	public void cucmSQLQuery(CucmAxlPort port);
	public void cucmAddPhone(CucmAxlPort port, CucmExportDevice device, RouterExportDetail routerDetail);
	public CsvChangeNumber changeDirectoryNumber(CucmAxlPort port, CsvChangeNumber number, String partition);
}