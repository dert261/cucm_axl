package ru.obelisk.cucmaxl.ftp.database;

public interface BaseJDBCService<T> {
	public void execute(String query);
}
