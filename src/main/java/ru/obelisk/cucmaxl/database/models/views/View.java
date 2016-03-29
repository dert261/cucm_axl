package ru.obelisk.cucmaxl.database.models.views;

import ru.obelisk.datatables.mapping.DataTablesOutput;

public class View{
	public interface Datatable extends DataTablesOutput.View{}
	public interface LdapCustomFilter extends Datatable {}
	public interface LdapDirSyncParameters extends Datatable {}
	public interface CucmAxlPort extends Datatable {}
	public interface EndUser extends Datatable {}
	public interface EndUserPhoneBook extends Datatable {}
	public interface User extends Datatable {}
	public interface ScheduleJob extends Datatable {}
	public interface ScheduleCron extends Datatable {}
	public interface CmeLocation extends Datatable {}
	
	public interface CmeRouter extends Datatable {}
	public interface CmeRouterWithOutRelations extends Datatable {}
	public interface CmeGlobal extends Datatable {}
	public interface CmeUrlService extends Datatable {}
	
	public interface CmeDevice extends Datatable {}
	public interface CmeExtension extends Datatable {}
	
	public interface CmeVoiceHuntGroup extends Datatable {}
	public interface CmeVoiceHuntGroupNumber extends Datatable {}
	
	public interface CmeSipGlobal extends Datatable {}
	public interface CmeSipDevice extends Datatable {}
	public interface CmeSipExtension extends Datatable {}
	
	public interface CmeCustomDevice extends Datatable {}
	public interface CmeCustomExtension extends Datatable {}
	
	public interface CmeCustomSipDevice extends Datatable {}
	public interface CmeCustomSipExtension extends Datatable {}
	
	public interface RouterExportDetail extends Datatable {}
	
	public interface Job extends Datatable {}
	
	
}
