package org.yeastrc.compgo.geoduck.analysis;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.yeastrc.compgo.geoduck.db.DBConnectionManager;
import org.yeastrc.compgo.geoduck.go.GONode;
import org.yeastrc.compgo.geoduck.protein.GOProteinCountDBCache;
import org.yeastrc.compgo.geoduck.protein.GOProteinCountFromDB;

public class TotalAnnotatedProteinCounter {

	private static final TotalAnnotatedProteinCounter INSTANCE = new TotalAnnotatedProteinCounter();
	private TotalAnnotatedProteinCounter() { }
	
	public static TotalAnnotatedProteinCounter getInstance() { return INSTANCE; }
	
	private Map<String, Integer> totalAnnotatedProteinsForAspect;	
	
	/**
	 * Get the total number of human proteins that have been annotated with any go term of
	 * the given aspect
	 * @param aspect C, F, or P
	 * @return
	 * @throws Exception
	 */
	public int getTotalAnnotatedProteinsForAspect( String aspect ) throws Exception {

		if( this.totalAnnotatedProteinsForAspect == null )
			this.totalAnnotatedProteinsForAspect = new HashMap<String, Integer>();
		
		if( !this.totalAnnotatedProteinsForAspect.containsKey( aspect ) ) {
		
			// Get our connection to the database.
			Connection conn = DBConnectionManager.getConnection("compgo_geoduck");
			PreparedStatement stmt = null;
			ResultSet rs = null;
					
			try {
				// Our SQL statement
				String sqlStr =  "select count(distinct db_object_id) from go_annotation where aspect = ?";
				stmt = conn.prepareStatement(sqlStr);
				stmt.setString( 1, aspect );
	
				// Our results
				rs = stmt.executeQuery();
	
				rs.next();
				this.totalAnnotatedProteinsForAspect.put( aspect, rs.getInt( 1 ) );
			
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
		
		return this.totalAnnotatedProteinsForAspect.get( aspect );
		
	}

	public Map<String, Integer> getTotalAnnotatedProteinsForAspect() {
		return totalAnnotatedProteinsForAspect;
	}

	public void setTotalAnnotatedProteinsForAspect(
			Map<String, Integer> totalAnnotatedProteinsForAspect) {
		this.totalAnnotatedProteinsForAspect = totalAnnotatedProteinsForAspect;
	}

	/**
	 * Get the total number of human proteins that have been annotated with the given go term or
	 * any of its children
	 * @param node the go term
	 * @return
	 * @throws Exception
	 */
	public int getTotalAnnotatedProteinsForTerm( GONode node ) throws Exception {

		int count = GOProteinCountDBCache.getInstance().getCountFromCache( node );
		if( count != -1 ) return count;
		
		count = GOProteinCountFromDB.getInstance().getTotalAnnotatedProteinsFromDB( node );
		GOProteinCountDBCache.getInstance().setCountInCache( node, count );
		
		return count;
	}
	
	
}
