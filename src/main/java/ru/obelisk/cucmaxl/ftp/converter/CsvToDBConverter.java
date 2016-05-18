package ru.obelisk.cucmaxl.ftp.converter;

public interface CsvToDBConverter<T, V> {
	public V convert (T item);
}
