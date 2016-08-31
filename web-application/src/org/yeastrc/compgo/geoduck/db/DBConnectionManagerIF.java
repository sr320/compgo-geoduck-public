package org.yeastrc.compgo.geoduck.db;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * An interface that a supplied DB Connection Manager is required to implement.
 * This is used for programs that want to provide their own DB Connection Manager implementations, like for batch processing.
 *
 */
public interface DBConnectionManagerIF {


	/**
	 * @param db
	 * @return
	 * @throws SQLException
	 */
	public Connection getConnection(String db) throws SQLException;


}
