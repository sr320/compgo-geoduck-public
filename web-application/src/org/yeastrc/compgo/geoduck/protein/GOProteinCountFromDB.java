package org.yeastrc.compgo.geoduck.protein;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashSet;

import org.yeastrc.compgo.geoduck.db.DBConnectionManager;
import org.yeastrc.compgo.geoduck.go.GONode;
import org.yeastrc.compgo.geoduck.go.GOSearchUtils;

public class GOProteinCountFromDB {

	private static final GOProteinCountFromDB INSTANCE = new GOProteinCountFromDB();
	private GOProteinCountFromDB() { }
	
	public static GOProteinCountFromDB getInstance() { return INSTANCE; }
	
	
	/**
	 * Get the total number of human proteins that have been annotated with the given go term or
	 * any of its children
	 * @param node the go term
	 * @return
	 * @throws Exception
	 */
	public int getTotalAnnotatedProteinsFromDB( GONode node ) throws Exception {

			Collection<GONode> nodes = new HashSet<GONode>();
			nodes.add( node );
			nodes.addAll( GOSearchUtils.getAllChildNodes( node ) );
			
			// uniqe list of proteins annotated w/ thise go term or any of its children
			Collection<String> proteins = new HashSet<String>();
			
			for( GONode tnode : nodes ) {
			
			
				// Get our connection to the database.
				Connection conn = DBConnectionManager.getConnection("compgo_geoduck");
				PreparedStatement stmt = null;
				ResultSet rs = null;
						
				try {
					// Our SQL statement
					String sqlStr =  "select distinct db_object_id from go_annotation where go_id = ?";
					stmt = conn.prepareStatement(sqlStr);
					stmt.setString( 1, tnode.getAcc() );
		
					// Our results
					rs = stmt.executeQuery();
		
					while( rs.next() )
						proteins.add( rs.getString( 1 ) );
				
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
			
			}

			return proteins.size(); 
	}
	
}
