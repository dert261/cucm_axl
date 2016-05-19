package ru.obelisk.cucmaxl.ftp.database.entity;

import lombok.Data;

@Data
public class Collector {
	 private int id;
	 private String name;
	 private String description;
	 private int sourceAxlPortId;
	 private String type;
	 private String resourceSourceType;
	 private CollectorFtpConfig ftpConfig;

}
