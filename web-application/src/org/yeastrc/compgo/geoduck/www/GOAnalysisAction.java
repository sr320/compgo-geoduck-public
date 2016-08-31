package org.yeastrc.compgo.geoduck.www;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.yeastrc.compgo.geoduck.analysis.AnalyzedGOTreeMaker;
import org.yeastrc.compgo.geoduck.analysis.GOTreePruner;
import org.yeastrc.compgo.geoduck.analysis.HypergeometricPValue;
import org.yeastrc.compgo.geoduck.go.*;
import org.yeastrc.compgo.geoduck.protein.*;

import java.util.*;

public class GOAnalysisAction extends Action {

	public ActionForward execute( ActionMapping mapping,
			ActionForm actionForm,
			HttpServletRequest request,
			HttpServletResponse response )
	throws Exception {
		
		GOAnalysisForm gaform = (GOAnalysisForm)actionForm;
		
		String aspect = gaform.getAspect();
		String cutoff = gaform.getCutoff();
		String type = gaform.getType();

		boolean prune = false;
		if (cutoff != null) prune = true;
		
		// parse the protein lists
		
		HashSet<String> proteins1 = new HashSet<String>();
		proteins1.addAll( Arrays.asList( gaform.getProteinList1().split("[\\r\\n]+") ) );
		
		HashSet<String> proteins2 = null;
		if( gaform.getProteinList2() != null ) {
			proteins2 = new HashSet<String>();
			proteins2.addAll( Arrays.asList( gaform.getProteinList2().split("[\\r\\n]+") ) );
		}

		
		// create goTree for protein list 1
		System.out.println( "Processing protein list 1 (" + proteins1.size() + ")..." );

		System.out.println( "Attempting GO analysis for " + aspect + "..." );
		Map<GONode, HypergeometricPValue> goTree = AnalyzedGOTreeMaker.makeInstance().getAnalyzedGOTree( proteins1, aspect );
		System.out.println( "Got " + goTree.keySet().size() + " GO terms:" );

		if( prune && type.equals( "image" ) )
			GOTreePruner.makeInstance().pruneGOTree( goTree, Double.valueOf( cutoff ) );
		

		// create goTree for protein list 2
		Map<GONode, HypergeometricPValue> goTree2 = null;
		if( proteins2 != null ) {
			System.out.println( "Processing protein list 2 (" + proteins2.size() + ")..." );

			System.out.println( "Attempting GO analysis for " + aspect + "..." );
			goTree2 = AnalyzedGOTreeMaker.makeInstance().getAnalyzedGOTree( proteins2, aspect );
			System.out.println( "Got " + goTree2.keySet().size() + " GO terms:" );

			if( prune && type.equals( "image" ) )
				GOTreePruner.makeInstance().pruneGOTree( goTree2, Double.valueOf( cutoff ) );
		}
		
		// protein objects to send into the report (w/ protein annotations)
		Collection<Protein> proteinSet1 = new HashSet<Protein>();
		for( String p : proteins1 ) {
			Protein upp = ProteinFactory.getInstance().getUniProtProtein( p );
			
			if( upp != null )
				proteinSet1.add( upp );
		}
		
		// protein object (set2) to send into report (w/ protein annotations)
		Collection<Protein> proteinSet2 = null;
		if( proteins2 != null && proteins2.size() > 0 ) {
			proteinSet2 = new HashSet<Protein>();
			for( String p : proteins2 ) {
				Protein upp = ProteinFactory.getInstance().getUniProtProtein( p );
				
				if( upp != null )
					proteinSet2.add( upp );
			}
		}
		
		if( type.equals( "image" ) ) {
			request.setAttribute( "image", GOGraphGenerator.makeInstance().getGOGraphImage( goTree, goTree2 ) );
			return mapping.findForward( "image" );
		}
		
		request.setAttribute( "reportData", GOReportGenerator.makeInstance().getGOGraphReport( goTree, goTree2, proteinSet1, proteinSet2, Double.parseDouble( cutoff ), aspect ) );
		return mapping.findForward( "report" );
		
	}

}
