package org.yeastrc.compgo.geoduck.analysis;

import java.util.*;

import org.yeastrc.compgo.geoduck.go.*;
import org.yeastrc.compgo.geoduck.protein.ProteinGOSearcher;
import org.yeastrc.compgo.geoduck.stats.StatUtils;

public class AnalyzedGOTreeMaker {

	public static AnalyzedGOTreeMaker makeInstance() {
		return new AnalyzedGOTreeMaker();
	}

	
	/**
	 * Run the main analysis. Takes a list of protein accession strings (must be human UniProt) and a gene ontology
	 * aspect (C, F, or P), and produces a mapping of all represented GO terms and their associated p-value statistics
	 * given this specific list of proteins
	 * @param proteins
	 * @param aspect
	 * @return
	 * @throws Exception
	 */
	public Map<GONode, HypergeometricPValue> getAnalyzedGOTree( Collection<String> proteins, String aspect ) throws Exception {
		Map<GONode, HypergeometricPValue> gonodes = new HashMap<GONode, HypergeometricPValue>();
		
		// first, get a set of all the GO nodes represented by these proteins
		Collection<GONode> allNodes = new HashSet<GONode>();
		for( String protein : proteins ) {
			allNodes.addAll( ProteinGOSearcher.getInstance().getAllGONodes( protein, aspect ) );
		}
		System.out.println( "Got " + allNodes.size() + " GO terms for protein list..." );
		
		// total proteins annotated with the supplied aspect
		int T = TotalAnnotatedProteinCounter.getInstance().getTotalAnnotatedProteinsForAspect( aspect );		
		
		// total proteins IN THIS SET annotated with any term from the supplied aspect
		int A = ProteinListCounter.getInstance().getTotalProteinAnnotatedInList( proteins, aspect );		

		// now, for the per-go term analysis
		for( GONode node : allNodes ) {
						
			// total proteins annotated with this go term
			int B = TotalAnnotatedProteinCounter.getInstance().getTotalAnnotatedProteinsForTerm( node );

			// total number of proteins IN THIS SET annotated with this go term
			int I = ProteinListCounter.getInstance().getTotalProteinAnnotatedWithTermInList( proteins, node, aspect );

			// the p-value given A, B, I, and T
			double pValue = StatUtils.PScore( I, A, B, T );
			
			// perform Bonferroni correction
			double cpValue = pValue * allNodes.size();
			if( cpValue > 1 ) cpValue = 1;
			
			if( cpValue <= 0.01 ) {
				System.out.println( "\nProcessing " + node.getAcc() + "(" + node.getName() + ")" );
				System.out.println( "\tA: " + A );
				System.out.println( "\tB: " + B );
				System.out.println( "\tI: " + I );
				System.out.println( "\tT: " + T );
				System.out.println( "Raw pvalue: " + pValue );
				System.out.println( "Cor pvalue: " + cpValue );
			}

			HypergeometricPValue hpv = new HypergeometricPValue();
			hpv.setA( A );
			hpv.setB( B );
			hpv.setI( I );
			hpv.setT( T );
			hpv.setInputTotal( proteins.size() );
			hpv.setPvalue( cpValue );
			
			gonodes.put( node, hpv );
		}
		
		
		
		return gonodes;
	}
	
	
	
}
