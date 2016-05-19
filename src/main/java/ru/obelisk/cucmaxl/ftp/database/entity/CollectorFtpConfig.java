package ru.obelisk.cucmaxl.ftp.database.entity;

import lombok.Data;

@Data
public class CollectorFtpConfig {
	private int id;
    private String host;
    private int port = 21;
    private String username;
    private String password;
    private String directory = "/";
    private boolean recursive = false;
 	private long consumerDelay = 60000;
 	private String includePattern;
    private String excludePattern;
    private int threadpoolSize = 1;
    private int threadpoolMaxSize = 2;
}
