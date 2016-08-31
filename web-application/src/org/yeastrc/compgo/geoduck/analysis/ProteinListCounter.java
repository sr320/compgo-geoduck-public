package org.yeastrc.compgo.geoduck.analysis;

import java.util.*;

import org.yeastrc.compgo.geoduck.go.*;
import org.yeastrc.compgo.geoduck.protein.*;

public class ProteinListCounter {

	private static final ProteinListCounter INSTANCE = new ProteinListCounter();
	private ProteinListCounter() { }
	
	public static ProteinListCounter getInstance() { return INSTANCE; }
	
	/**
	 * Get the total number of proteins that are annotated with any GO term of the supplied aspect
	 * @param proteins
	 * @param aspect
	 * @return
	 * @throws Exception
	 */
	public int getTotalProteinAnnotatedInList(Collection<String> proteins, String aspect) throws Exception {
		int total = 0;
		
		for( String protein : proteins ) {
			if( ProteinGOSearcher.getInstance().getDirectGONodes( protein, aspect ).size() > 0 ) total++;
		}
		
		return total;
	}
	
	/**
	 * Get total number of proteins in the supplied list of proteins that are annotated with the
	 * supplied go term
	 * @param proteins
	 * @param node
	 * @return
	 * @throws Exception
	 */
	public int getTotalProteinAnnotatedWithTermInList( Collection<String> proteins, GONode node, String aspect ) throws Exception {
		int total = 0;
		
		for( String protein: proteins ) {
			
			if( this.proteinParentNodes == null )
				this.proteinParentNodes = new HashMap<String, Map<String, Collection<GONode>>>();
			
			if( !this.proteinParentNodes.containsKey( aspect ) )
				this.proteinParentNodes.put( aspect, new HashMap<String, Collection<GONode>>() );
			
			
			if( !this.proteinParentNodes.get( aspect ).containsKey( protein ) )
				this.proteinParentNodes.get(aspect).put( protein, ProteinGOSearcher.getInstance().getAllGONodes( protein, aspect ) );
			
			if( this.proteinParentNodes.get( aspect ).get(protein).contains( node ) )
				total++;
		}
		
		
		return total;
	}
	
	private Map<String, Map<String, Collection<GONode>>> proteinParentNodes;
	
}
