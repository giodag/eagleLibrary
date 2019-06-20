package com.univaq.eaglelibrary.persistence;

import java.sql.SQLException;
import java.util.Map;
import java.util.Set;

import com.univaq.eaglelibrary.persistence.exceptions.DatabaseException;

public interface PersistenceService {
	
	
	public void save(String kind, Map<String, Object> data) throws DatabaseException;
	
	public Set<Map<String, String>> search(String kind, String condition) throws DatabaseException;
	
	public Set<Map<String, String>> search(String kind, String condition, int count) throws DatabaseException;
	
	public void update(String kind, Map<String,Object> data, String condition) throws DatabaseException, SQLException;

	public Set<Map<String, String>> getGroup(String kind, String condition, String group) throws DatabaseException;

	public void connect()throws DatabaseException;

}
