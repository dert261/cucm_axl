package ru.obelisk.cucmaxl.ftp.meta;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public class MetaRecord {
	private List<MetaField> fields = new ArrayList<MetaField>(0);
}
