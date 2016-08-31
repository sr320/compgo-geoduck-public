package org.yeastrc.compgo.geoduck.protein;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.yeastrc.compgo.geoduck.db.DBConnectionManager;
import org.yeastrc.compgo.geoduck.go.GONode;

public class GOProteinCountDBCache {

	private static final GOProteinCountDBCache INSTANCE = new GOProteinCountDBCache();
	private GOProteinCountDBCache() { }
	
	public static GOProteinCountDBCache getInstance() { return INSTANCE; }
	
	
	public int getCountFromCache( GONode node ) throws Exception {
		int count = -1;
		
		// Get our connection to the database.
		Connection conn = DBConnectionManager.getConnection("compgo_geoduck");
		PreparedStatement stmt = null;
		ResultSet rs = null;
				
		try {
			// Our SQL statement
			String sqlStr =  "SELECT count FROM go_protein_counts WHERE go_acc = ?";
			stmt = conn.prepareStatement(sqlStr);
			stmt.setString( 1, node.getAcc() );

			// Our results
			rs = stmt.executeQuery();
			
			if( rs.next() )
				count = rs.getInt( 1 );
			
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
		
		return count;
	}
	
	public void setCountInCache( GONode node, int count ) throws Exception {
		
		// Get our connection to the database.
		Connection conn = DBConnectionManager.getConnection("compgo_geoduck");
		PreparedStatement stmt = null;
				
		try {
			// Our SQL statement
			String sqlStr =  "INSERT INTO go_protein_counts (go_acc, count) VALUES (?, ?)";
			stmt = conn.prepareStatement(sqlStr);
			stmt.setString( 1, node.getAcc() );
			stmt.setInt( 2, count );

			// Our results
			stmt.executeUpdate();
			
			stmt.close(); stmt = null;
			conn.close(); conn = null;
		}
		finally {

			// Always make sure result sets and statements are closed,
			// and the connection is returned to the pool

			if (stmt != null) {
				try { stmt.close(); } catch (SQLException e) { ; }
				stmt = null;
			}
			if (conn != null) {
				try { conn.close(); } catch (SQLException e) { ; }
				conn = null;
			}
		}
		
	}
	
	
}
