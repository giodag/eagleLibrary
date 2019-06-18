package com.univaq.eaglelibrary.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.ConnectionFactory;
import org.apache.commons.dbcp.DriverManagerConnectionFactory;
import org.apache.commons.dbcp.PoolableConnectionFactory;
import org.apache.commons.dbcp.PoolingDataSource;
import org.apache.commons.pool.impl.GenericObjectPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.univaq.eaglelibrary.persistence.exceptions.DatabaseException;

public class MySQLConnection extends Database {

	private static final Logger log = LoggerFactory.getLogger(MySQLConnection.class);

	private Connection db;

	private String user;
	private String password;
	private String host;
	private int port;
	private String db_name;

	// --New property for DB connection--//
	// JDBC Driver Name & Database URL
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String JDBC_DB_URL = "jdbc:mysql://localhost:3306/eaglelibraryapp";
	static final String TIMEZONE = "?serverTimezone=UTC";

	// JDBC Database Credentials
	static final String JDBC_USER = "root";
	static final String JDBC_PASS = "danimetu";

	private static GenericObjectPool gPool = null;
	private static DataSource pool = null;
	// --New property for DB connection--//

	/**
	 * 
	 * @param user
	 * @param password
	 * @param host
	 * @param port
	 * @param db_name
	 * @throws DatabaseException 
	 */
	public MySQLConnection(String user, String password, String host, int port, String db_name) throws DatabaseException {
		this.connect();
	}

	/**
	 * This method allows connection to database
	 * @return 
	 * @return 
	 * @throws ClassNotFoundException 
	 */
	@Override
	public void connect() {
		if (pool == null) {

			try {
				Class.forName(JDBC_DRIVER);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// Creates an Instance of GenericObjectPool That Holds Our Pool of Connections
			// Object!
			gPool = new GenericObjectPool();
			gPool.setMaxActive(5);

			// Creates a ConnectionFactory Object Which Will Be Use by the Pool to Create
			// the Connection Object!
			ConnectionFactory cf = new DriverManagerConnectionFactory(JDBC_DB_URL + TIMEZONE, JDBC_USER, JDBC_PASS);

			// Creates a PoolableConnectionFactory That Will Wraps the Connection Object
			// Created by the ConnectionFactory to Add Object Pooling Functionality!
			PoolableConnectionFactory pcf = new PoolableConnectionFactory(cf, gPool, null, null, false, true);
			pool = new PoolingDataSource(gPool);
		}
	}

	@Override
	protected ResultSet select(String table, String condition, String order) throws SQLException {
		ResultSet records = null;
		Connection connObj = null;
		PreparedStatement pstmtObj = null;
		connObj = pool.getConnection();
		String selectQuery = "SELECT * FROM " + table + " WHERE " + condition + " ORDER BY " + order;
		pstmtObj = connObj.prepareStatement(selectQuery);
		records = pstmtObj.executeQuery();
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
