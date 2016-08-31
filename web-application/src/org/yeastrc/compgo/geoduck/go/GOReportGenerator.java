package org.yeastrc.compgo.geoduck.go;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.yeastrc.compgo.geoduck.analysis.HypergeometricPValue;
import org.yeastrc.compgo.geoduck.protein.Protein;
import org.yeastrc.compgo.geoduck.protein.ProteinGOSearcher;

public class GOReportGenerator {

	public static GOReportGenerator makeInstance() { return new GOReportGenerator(); }

	public Collection<ReportNode> getGOGraphReport( Map<GONode, HypergeometricPValue> goTree1,
												  Map<GONode, HypergeometricPValue> goTree2,
												  Collection<Protein> proteinSet1,
												  Collection<Protein> proteinSet2,
												  double cutoff,
												  String aspect
												) throws Exception {

		Collection<ReportNode> reportNodes = new ArrayList<ReportNode>();

		Set<GONode> gonodes = new HashSet<GONode>();
		
		// add all go nodes from goTree1 that meet our p-value cutoff
		if( goTree1 != null ) {
			for( GONode gonode : goTree1.keySet() ) {
				if( goTree1.get( gonode ).getPvalue() <= cutoff )
					gonodes.add( gonode );
			}
		}
		
		// add all go nodes from goTree2 that meet our p-value cutoff
		if( goTree2 != null ) {
			for( GONode gonode : goTree2.keySet() ) {
				if( goTree2.get( gonode ).getPvalue() <= cutoff )
					gonodes.add( gonode );
			}
		}

		// loop over all nodes, create subsets of proteinSet1 and proteinSet2 where proteins in the subsets are 
		// annotated with this go term
		for( GONode gonode : gonodes ) {
			
			ReportNode rn = new ReportNode();
			rn.setNode( gonode );
			rn.setHpv1( goTree1.get( gonode ) );
			rn.setHpv2( goTree2.get( gonode ) );

			if( proteinSet1 != null && proteinSet1.size() > 0 ) {
				Set<Protein> filteredProteinSet = new HashSet<Protein>();
				for( Protein upp : proteinSet1 ) {
					if( ProteinGOSearcher.getInstance().getAllGONodes( upp.getId(), aspect ).contains( gonode ) )
						filteredProteinSet.add( upp );
				}
				rn.setProteins1( filteredProteinSet );
			}
			
			
			if( proteinSet2 != null && proteinSet2.size() > 0 ) {
				Set<Protein> filteredProteinSet = new HashSet<Protein>();
				for( Protein upp : proteinSet2 ) {
					if( ProteinGOSearcher.getInstance().getAllGONodes( upp.getId(), aspect ).contains( gonode ) )
						filteredProteinSet.add( upp );
				}
				rn.setProteins2( filteredProteinSet );
			}
			
			reportNodes.add( rn );
		}
		
		Collections.sort( (List<ReportNode>)reportNodes );
		
		return reportNodes;
	}
	
	
}
