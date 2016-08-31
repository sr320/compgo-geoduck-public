package org.yeastrc.compgo.geoduck.go;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashSet;

import org.yeastrc.compgo.geoduck.db.DBConnectionManager;

public class GOSearchUtils {
	
	/**
	 * Get direct parents of this go node (i.e., get more general terms connected directly to this term)
	 * @param gnode
	 * @return
	 * @throws Exception
	 */
	public static Collection<GONode> getDirectParentNodes( GONode gnode ) throws Exception {
		Collection<GONode> nodes = new HashSet<GONode>();
		
		// Get our connection to the database.
		Connection conn = DBConnectionManager.getConnection("go");
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			// Our SQL statement, relationship_type = 1 means that it is a "is_a" relationship
			String sqlStr =  "SELECT term1_id FROM term2term WHERE term2_id = ? AND relationship_type_id = 1 AND term1_id <> term2_id";
			stmt = conn.prepareStatement(sqlStr);
			stmt.setInt( 1, gnode.getId() );

			// Our results
			rs = stmt.executeQuery();
		
			while( rs.next() ) {
				nodes.add( GONodeFactory.getInstance().getGONode( rs.getInt( 1 ) ) );
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
		
		nodes.remove( GOUtils.getAllNode() );
		return nodes;
	}
	
	/**
	 * Get all parents of this go node (i.e., get more general terms connected directly and indirectly to this term)
	 * @param gnode
	 * @return
	 * @throws Exception
	 */
	public static Collection<GONode> getAllParentNodes( GONode gnode ) throws Exception {
		Collection<GONode> nodes = new HashSet<GONode>();
		
		// Get our connection to the database.
		Connection conn = DBConnectionManager.getConnection("go");
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			// Our SQL statement, relationship_type = 1 means that it is a "is_a" relationship
			String sqlStr =  "SELECT term1_id FROM graph_path WHERE term2_id = ? AND relationship_type_id = 1 AND term1_id <> term2_id";
			stmt = conn.prepareStatement(sqlStr);
			stmt.setInt( 1, gnode.getId() );

			// Our results
			rs = stmt.executeQuery();
		
			while( rs.next() ) {
				nodes.add( GONodeFactory.getInstance().getGONode( rs.getInt( 1 ) ) );
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
		
		nodes.remove( GOUtils.getAllNode() );
		return nodes;
	}

	/**
	 * Get direct children of this go node (i.e., get more specific terms connected directly to this term)
	 * @param gnode
	 * @return
	 * @throws Exception
	 */
	public static Collection<GONode> getDirectChildNodes( GONode gnode ) throws Exception {
		Collection<GONode> nodes = new HashSet<GONode>();
		
		// Get our connection to the database.
		Connection conn = DBConnectionManager.getConnection("go");
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			// Our SQL statement, relationship_type = 1 means that it is a "is_a" relationship
			String sqlStr =  "SELECT term2_id FROM term2term WHERE term1_id = ? AND relationship_type_id = 1 AND term1_id <> term2_id";
			stmt = conn.prepareStatement(sqlStr);
			stmt.setInt( 1, gnode.getId() );

			// Our results
			rs = stmt.executeQuery();
		
			while( rs.next() ) {
				nodes.add( GONodeFactory.getInstance().getGONode( rs.getInt( 1 ) ) );
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
		
		return nodes;
	}
	
	/**
	 * Get all children of this go node (i.e., get more specific terms connected directly and indirectly to this term)
	 * @param gnode
	 * @return
	 * @throws Exception
	 */
	public static Collection<GONode> getAllChildNodes( GONode gnode ) throws Exception {
		Collection<GONode> nodes = new HashSet<GONode>();
		
		// Get our connection to the database.
		Connection conn = DBConnectionManager.getConnection("go");
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			// Our SQL statement, relationship_type = 1 means that it is a "is_a" relationship
			String sqlStr =  "SELECT term2_id FROM graph_path WHERE term1_id = ? AND relationship_type_id = 1 AND term1_id <> term2_id";
			stmt = conn.prepareStatement(sqlStr);
			stmt.setInt( 1, gnode.getId() );

			// Our results
			rs = stmt.executeQuery();
		
			while( rs.next() ) {
				nodes.add( GONodeFactory.getInstance().getGONode( rs.getInt( 1 ) ) );
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
		
		return nodes;
	}
	
	
}
