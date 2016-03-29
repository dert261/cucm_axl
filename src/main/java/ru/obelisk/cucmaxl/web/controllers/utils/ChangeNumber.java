package ru.obelisk.cucmaxl.web.controllers.utils;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.obelisk.cucmaxl.database.models.entity.CucmAxlPort;
import ru.obelisk.cucmaxl.database.models.entity.Job;
import ru.obelisk.cucmaxl.database.models.entity.UploadFile;

@ToString
public class ChangeNumber {
	@Getter
	@Setter
	private String partition;
	
	@Getter
	@Setter
	private UploadFile datasource;
	
	@Getter
	@Setter
	private CucmAxlPort destination;
	
	@Getter
	@Setter
	private Job job;
}
