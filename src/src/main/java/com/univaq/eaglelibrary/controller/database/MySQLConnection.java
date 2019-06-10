package com.univaq.eaglelibrary.controller.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.univaq.eaglelibrary.controller.exceptions.DatabaseException;

public class MySQLConnection extends Database {

	private static final Logger log = LoggerFactory.getLogger(MySQLConnection.class);

	private Connection db;

	private String user;
	private String password;
	private String host;
	private int port;
	private String db_name;

	/**
	 * 
	 * @param user
	 * @param password
	 * @param host
	 * @param port
	 * @param db_name
	 */
	public MySQLConnection(String user, String password, String host, int port, String db_name) {
		this.user = user;
		this.password = password;
		this.host = host;
		this.port = port;
		this.db_name = db_name;
	}

	/**
	 * This method allows connection to database
	 */
	@Override
	public void connect() throws DatabaseException {
		Properties connProps = new Properties();
		connProps.put("user", user);
		connProps.put("password", password);

		try {
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
			this.db = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + db_name + "?serverTimezone=UTC", connProps);
		} catch (Exception e) {
			throw new DatabaseException();
		}
		log.debug("Hello");
	}

	@Override
	protected ResultSet select(String table, String condition, String order) throws SQLException {
		Statement s = this.db.createStatement();
		ResultSet records = s.executeQuery("SELECT * FROM " + table + " WHERE " + condition + " ORDER BY " + order);
		return records;
	}

	@Override
	protected ResultSet select(String table, String order) throws SQLException {
		return this.select(table, "'1'='1'", order);
	}

	@Override
	protected void insert(String table, Map<String, Object> data) throws SQLException {
		Statement s = this.db.createStatement();
		String query = "INSERT INTO " + table + " SET ";
		for (Map.Entry<String, Object> entry : data.entrySet()) {

			query = query + entry.getKey().replaceAll("'", "''") + " = '"
					+ entry.getValue().toString().replaceAll("'", "''") + "', ";
		}
		query = query.substring(0, query.length() - 2);
		s.executeUpdate(query);
	}

	@Override
	public void update(String table, Map<String, Object> data, String condition) throws SQLException {
		Statement s = this.db.createStatement();
		String query = "UPDATE " + table + " SET ";
		for (Map.Entry<String, Object> entry : data.entrySet()) {
			query = query + entry.getKey() + " = '" + entry.getValue() + "', ";
		}
		query = query.substring(0, query.length() - 2);
		query = query + " WHERE " + condition;
		s.executeUpdate(query);
	}

}
