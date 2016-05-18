package ru.obelisk.cucmaxl.cucm.service;

import java.util.Set;

import ru.obelisk.database.models.entity.cme.CmeDevice;
import ru.obelisk.database.models.entity.cme.CmeGlobal;
import ru.obelisk.database.models.entity.cme.CmeRouter;

public interface CmeService {
	public CmeGlobal importGlobalState(CmeRouter router);
	public Set<CmeDevice> importDevices(CmeRouter router);
	public void importCmeRouter(CmeRouter router);
}