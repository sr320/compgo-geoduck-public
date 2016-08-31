package org.yeastrc.compgo.geoduck.go;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.yeastrc.compgo.geoduck.db.DBConnectionManager;

public class GONodeFactory {

	private static final GONodeFactory INSTANCE = new GONodeFactory();
	private GONodeFactory() { }
	
	public static GONodeFactory getInstance() { return INSTANCE; }
	
	/**
	 * Get the go term with the given acc
	 * @param acc
	 * @return
	 * @throws Exception
	 */
	public GONode getGONode( String acc ) throws Exception {
		
		// Get our connection to the database.
		Connection conn = DBConnectionManager.getConnection("go");
		PreparedStatement stmt = null;
		ResultSet rs = null;
		GONode gnode;
		
		
		try {
			// Our SQL statement
			String sqlStr =  "SELECT id, name, term_type, acc, is_obsolete, is_root, is_relation FROM term WHERE acc = ?";
			stmt = conn.prepareStatement(sqlStr);
			stmt.setString( 1, acc );

			// Our results
			rs = stmt.executeQuery();

			// Not found, pass our problems off to the caller
			if (!rs.next())
				throw new IllegalArgumentException("Invalid acc: " + acc);
			
			gnode = new GONode();
			gnode.setId( rs.getInt( "id" ) );
			gnode.setName( rs.getString( "name" ) );
			gnode.setTermType( rs.getString( "term_type" ) );
			gnode.setAcc( rs.getString( "acc" ) );
			gnode.setIsObsolete( rs.getInt( "is_obsolete" ) );
			gnode.setIsRoot( rs.getInt( "is_root" ) );
			gnode.setIsRelation( rs.getInt( "is_relation" ) );
			
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
		
		return gnode;
	}
	
	/**
	 * Get the go term with the given id
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public GONode getGONode( int id ) throws Exception {
		
		// Get our connection to the database.
		Connection conn = DBConnectionManager.getConnection("go");
		PreparedStatement stmt = null;
		ResultSet rs = null;
		GONode gnode;
		
		
		try {
			// Our SQL statement
			String sqlStr =  "SELECT id, name, term_type, acc, is_obsolete, is_root, is_relation FROM term WHERE id = ?";
			stmt = conn.prepareStatement(sqlStr);
			stmt.setInt( 1, id );

			// Our results
			rs = stmt.executeQuery();

			// Not found, pass our problems off to the caller
			if (!rs.next())
				throw new IllegalArgumentException("Invalid id: " + id);
			
			gnode = new GONode();
			gnode.setId( rs.getInt( "id" ) );
			gnode.setName( rs.getString( "name" ) );
			gnode.setTermType( rs.getString( "term_type" ) );
			gnode.setAcc( rs.getString( "acc" ) );
			gnode.setIsObsolete( rs.getInt( "is_obsolete" ) );
			gnode.setIsRoot( rs.getInt( "is_root" ) );
			gnode.setIsRelation( rs.getInt( "is_relation" ) );
			
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
		
		return gnode;
	}
	
}
