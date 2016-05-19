package ru.obelisk.cucmaxl.ftp.meta;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public class MetaTable {
	private String tableName;
	private String tableSchema;
	private String tableCatalog;
	
	private List<MetaRecord> records = new ArrayList<MetaRecord>(0);
}
