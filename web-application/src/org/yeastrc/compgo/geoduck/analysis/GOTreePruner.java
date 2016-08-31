package org.yeastrc.compgo.geoduck.analysis;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.yeastrc.compgo.geoduck.go.GONode;
import org.yeastrc.compgo.geoduck.go.GOSearchUtils;

public class GOTreePruner {
	
	public static GOTreePruner makeInstance() { return new GOTreePruner(); }
	
	public void pruneGOTree ( Map<GONode, HypergeometricPValue> goTree, double cutoff ) throws Exception {
		
		boolean pruned = true;
		
		// iterate pruning over and over until we've pruned nothing (all leaf nodes have good p-values)
		while( pruned ) {
			
			pruned = false;
			
			// where we store the go nodes we want to prune off 
			Set<GONode> removalSet = new HashSet<GONode>();

			// loop over all the go nodes in the tree and find leaves w/ bad p-values for pruning
			for( GONode node : goTree.keySet() ) {

				// p-value is good, keep
				if( goTree.get( node ).getPvalue() <= cutoff ) continue;
				
				// test if it's a leave (if it has children in our go tree, it's not)
				boolean foundChild = false;
				for( GONode childNode : GOSearchUtils.getDirectChildNodes( node ) ) {
					if( goTree.keySet().contains( childNode ) ) {
						foundChild = true;
						break;
					}
				}
				
				// it has children, keep
				if( foundChild ) continue;
				
				// it has a bad p-value and has no children, add to removal set
				removalSet.add( node );
			}
			
			// remove the nodes
			if( removalSet.size() > 0 ) {
				for( GONode rnode : removalSet ) {
					goTree.remove( rnode );
				}
				pruned = true;
			}

			
			
		}
		
	}

	
}
