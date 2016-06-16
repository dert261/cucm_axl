package ru.obelisk.cucmaxl.cucm.service;

import java.util.Set;

import ru.obelisk.cucmaxl.web.databinding.AjaxOperationResult;
import ru.obelisk.database.models.entity.cme.CmeDevice;
import ru.obelisk.database.models.entity.cme.CmeGlobal;
import ru.obelisk.database.models.entity.cme.CmeRouter;

public interface CmeService {
	public CmeGlobal importGlobalState(CmeRouter router) throws Exception;
	public Set<CmeDevice> importDevices(CmeRouter router) throws Exception;
	public AjaxOperationResult importCmeRouter(CmeRouter router);
}