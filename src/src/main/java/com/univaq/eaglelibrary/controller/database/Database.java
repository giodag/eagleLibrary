package com.univaq.eaglelibrary.controller.database;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import com.univaq.eaglelibrary.controller.PersistenceService;
import com.univaq.eaglelibrary.controller.exceptions.DatabaseException;

public abstract  class Database implements PersistenceService {
	
	public void save(String table, Map<String, Object> data) throws DatabaseException {
		try {
			this.insert(table, data);
		} catch (Exception e) {
			throw new DatabaseException(e.getMessage(), e.getCause());
		}
	}
	
	public Set<Map<String, String>> search(String table, String condition) throws DatabaseException {
		return search(table, condition, 0);
	}

	public Set<Map<String, String>> search(String table, String condition, int count) throws DatabaseException {
		try {

			ResultSet results;
			if (count == 0) {
				results = this.select(table, condition, "'id' DESC");
			} else {
				results = this.select(table, condition, "'id' DESC LIMIT " + count);
			}

			Set<Map<String, String>> rows = new LinkedHashSet<Map<String, String>>();

			// Recupero nomi colonne (in array)
			ResultSetMetaData metadata = results.getMetaData();
			int column_count = metadata.getColumnCount();

			String column_labels[] = new String[column_count];

			for (int i = 1; i <= column_count; i++) {
				column_labels[i - 1] = metadata.getColumnLabel(i);
			}

			while (results.next()) {
				Map<String, String> row = new HashMap<String, String>();

				for (int i = 1; i <= column_count; i++) {
					row.put(column_labels[i - 1], results.getString(i));
				}
				rows.add(row);
			}
			return rows;
		} catch (Exception e) {
			throw new DatabaseException(e.getMessage(), e.getCause());
		}
	}

	public void update(String table, Map<String, Object> data, String condition) throws DatabaseException, SQLException {
		try {
			this.update(table, data, condition);
		} catch (Exception e) {
			throw new DatabaseException(e.getMessage(), e.getCause());
		}
	}

	public Set<Map<String, String>> getGroup(String table, String condition, String group) throws DatabaseException {
		try {
			return this.search(table, condition + " GROUP BY " + group);
		} catch (Exception e) {
			throw new DatabaseException(e.getMessage(), e.getCause());
		}
	}

	/**
	 * This method provides an interface to sorted select for database tables
	 * 
	 * @param table
	 * @param condition
	 * @param order
	 * @return ResultSet of selected records
	 * @throws Exception
	 */
	protected abstract ResultSet select(String table, String condition, String order) throws Exception;

	/**
	 * This method provides an interface to select for database tables
	 * 
	 * @param table
	 * @param order
	 * @return
	 * @throws Exception
	 */
	protected abstract ResultSet select(String table, String order) throws Exception;

	/**
	 * This method provides an interface to insert for database tables
	 * 
	 * @param table
	 * @param data
	 * @throws Exception
	 */
	protected abstract void insert(String table, Map<String, Object> data) throws Exception;

	public void connect() throws DatabaseException {
		// TODO Auto-generated method stub
		
	}

}
