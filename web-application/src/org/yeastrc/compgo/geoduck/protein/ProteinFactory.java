package org.yeastrc.compgo.geoduck.protein;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.yeastrc.compgo.geoduck.db.DBConnectionManager;

public class ProteinFactory {

	private static final ProteinFactory INSTANCE = new ProteinFactory();
	private ProteinFactory() { }
	
	public static ProteinFactory getInstance() { return INSTANCE; }
	
	/**
	 * Get the UniProt Protein for the given id (uniprot accession string)
	 * @param id uniprot accession string
	 * @return
	 * @throws Exception
	 */
	public Protein getUniProtProtein( String id ) throws Exception {
		
		Protein upp = null;
				
		// Get our connection to the database.
		Connection conn = DBConnectionManager.getConnection("compgo_geoduck");
		PreparedStatement stmt = null;
		ResultSet rs = null;
				
		try {
			// Our SQL statement
			String sqlStr =  "SELECT db_object_id, db_object_name FROM go_annotation WHERE db_object_id = ? LIMIT 1";
			stmt = conn.prepareStatement(sqlStr);
			stmt.setString( 1, id );

			// Our results
			rs = stmt.executeQuery();

			if( rs.next() ) {
				upp = new Protein();
				upp.setId( id );
				upp.setSymbol( rs.getString( "db_object_id" ) );
				upp.setName( rs.getString( "db_object_name" ) );
			}
					
			rs.close(); rs = null;
			stmt.close(); stmt = null;
			conn.close(); conn = null;
		}
		finally {

			// Always make sure result sets and statements are closed,
			// and the connection is returned to the pool
			if (rs != null) {
				try { rs.close(); } catch (SQLException e) { ; }
				rs = null;
			}
			if (stmt != null) {
				try { stmt.close(); } catch (SQLException e) { ; }
				stmt = null;
			}
			if (conn != null) {
				try { conn.close(); } catch (SQLException e) { ; }
				conn = null;
			}
		}
		
		return upp;
	}
	
	
}
