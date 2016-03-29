package ru.obelisk.cucmaxl.database.models.entity.enums;

public enum CmeRouterSyncStatus {
	NONSYNC,
	SYNC_GLOBAL_STATUS,
	SYNC_SIP_GLOBAL_STATUS,
	SYNC_SCCP_EXTENSIONS,
	SYNC_SCCP_DEVICES,
	SYNC_SIP_EXTENSIONS,
	SYNC_SIP_DEVICES,
	SYNC_HUNT_GROUPS;
	
	CmeRouterSyncStatus(){	
	}
}