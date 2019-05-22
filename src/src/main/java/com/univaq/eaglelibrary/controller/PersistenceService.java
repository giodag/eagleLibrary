package com.univaq.eaglelibrary.controller;

import java.util.Map;
import java.util.Set;

import com.univaq.eaglelibrary.controller.exceptions.DatabaseException;

public interface PersistenceService {
	
	public void connect() throws DatabaseException; 
	
	public void save(String kind, Map<String, Object> data) throws DatabaseException;
	
	public Set<Map<String, String>> search(String kind, String condition) throws DatabaseException;
	
	public Set<Map<String, String>> search(String kind, String condition, int count) throws DatabaseException;
	
	public void update(String kind, Map<String,Object> data, String condition) throws DatabaseException;

	public Set<Map<String, String>> getGroup(String kind, String condition, String group) throws DatabaseException;

}
