package org.yeastrc.compgo.geoduck.db;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class DBConnectionManager {

    public static final String MAIN_DB = "compgo_geoduck";

    private static DBConnectionManagerIF dbConnectionManagerIF = null;

	public static DBConnectionManagerIF getDbConnectionManagerIF() {
		return dbConnectionManagerIF;
	}



	/**
	 * Set an implementation of DBConnectionManagerIF to use to get database connections
	 * @param dbConnectionManagerIF
	 */
	public static void setDbConnectionManagerIF(
			DBConnectionManagerIF dbConnectionManagerIF) {
		DBConnectionManager.dbConnectionManagerIF = dbConnectionManagerIF;
	}



	/**
	 * @param db
	 * @return
	 * @throws SQLException
	 */
	public static Connection getConnection(String db) throws SQLException {

		if ( dbConnectionManagerIF != null ) {

			return dbConnectionManagerIF.getConnection( db );
		}

		return getConnectionWeb( db );
	}



	/**
	 * Get DataSource from JNDI as setup in Application Server and get database connection from it
	 * @param db
	 * @return
	 * @throws SQLException
	 */
	private static Connection getConnectionWeb(String db) throws SQLException {

		try {
			Context ctx = new InitialContext();
			DataSource ds;
			Connection conn;

			if (db.equals(MAIN_DB)) { ds = (DataSource)ctx.lookup("java:comp/env/jdbc/compgo_geoduck"); }
			else if (db.equals("go")) { ds = (DataSource)ctx.lookup("java:comp/env/jdbc/mygo_201512"); }


			else { throw new SQLException("Invalid database name passed into DBConnectionManager."); }

			if (ds != null) {
				conn = ds.getConnection();
				if (conn != null) { return conn; }
				else { throw new SQLException("Got a null connection..."); }
			}

			throw new SQLException("Got a null DataSource...");
		} catch (NamingException ne) {
			throw new SQLException("Naming exception: " + ne.getMessage());
		}
	}




}