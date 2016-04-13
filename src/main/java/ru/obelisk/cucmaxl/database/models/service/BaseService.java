package ru.obelisk.cucmaxl.database.models.service;

import java.util.List;

public interface BaseService<T> {
	public T add(T item);
	public T edit(T item);
	public void delete(int item);
	
	public T findById(int id);
	public List<T> findAll();
}
