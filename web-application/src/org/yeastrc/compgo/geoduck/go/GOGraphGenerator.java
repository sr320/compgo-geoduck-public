/*
 * GOGraphUtils.java
 * Created on Jul 7, 2004
 * Created by Michael Riffle <mriffle@u.washington.edu>
 */

package org.yeastrc.compgo.geoduck.go;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.yeastrc.compgo.geoduck.analysis.HypergeometricPValue;

import y.base.Node;
import y.io.JPGIOHandler;
import y.layout.OrientationLayouter;
import y.layout.hierarchic.HierarchicLayouter;
import y.view.Arrow;
import y.view.EdgeRealizer;
import y.view.Graph2D;
import y.view.Graph2DView;
import y.view.NodeLabel;
import y.view.PolyLineEdgeRealizer;
import y.view.ShapeNodeRealizer;

import java.math.*;
import java.util.*;

/**
 * Description of class goes here.
 * 
 * @author Michael Riffle <mriffle@u.washington.edu>
 * @version Jul 7, 2004
 */

public class GOGraphGenerator {

	public GOGraphGenerator() {
		this.seedNodes = new HashSet();
	}
	
	public static GOGraphGenerator makeInstance() { return new GOGraphGenerator(); }
	
	private String getLabel( GONode node, Map<GONode, HypergeometricPValue> goMap, Map<GONode, HypergeometricPValue> goMap2 ) {
		
		String label = node.toString();
		
		if( goMap != null && goMap.containsKey( node ) )
			label += "\npvalue(1): " + (new DecimalFormat("0.##E0")).format( goMap.get( node ).getPvalue() );

		if( goMap2 != null && goMap2.containsKey( node ) )
			label += "\npvalue(2): " + (new DecimalFormat("0.##E0")).format( goMap2.get( node ).getPvalue() );
		
		return label;
	}
	
	public BufferedImage getGOGraphImage(Map<GONode, HypergeometricPValue> goMap, Map<GONode, HypergeometricPValue> goMap2) throws Exception {
		Graph2D graph = new Graph2D();
		Map<GONode, Node> addedNodes = new HashMap<GONode, Node>();
		
		if( goMap2 != null && goMap2.size() < 1 )
			goMap2 = null;
		
		ShapeNodeRealizer realizer = new ShapeNodeRealizer();

		realizer.setShapeType(ShapeNodeRealizer.ROUND_RECT);
		
		NodeLabel nl = new NodeLabel();
		nl.setFontSize(12);
		realizer.setLabel(nl);		
		
		graph.setDefaultNodeRealizer(realizer);

		EdgeRealizer er = new PolyLineEdgeRealizer();
		er.setArrow(Arrow.SHORT);

		graph.setDefaultNodeRealizer(realizer);
		graph.setDefaultEdgeRealizer(er);
		
		Collection<GONode> gonodes = new HashSet<GONode>();
		gonodes.addAll( goMap.keySet() );

		if( goMap2 != null ) {
			System.out.println( "merging nodes: (" + goMap.keySet().size() + ") (" + goMap2.keySet().size() + ")" );
			gonodes.addAll( goMap2.keySet() );
		}
		
		Iterator<GONode> iter = gonodes.iterator();
		while (iter.hasNext()) {
			GONode gnode = (GONode)(iter.next());
			
			this.modifyRealize( realizer, gnode, goMap, goMap2 );
			
			
			Node node = null;
			if (addedNodes.containsKey(gnode))
				node = (Node)(addedNodes.get(gnode));
			else {
				node = graph.createNode();

				String label = getLabel( gnode, goMap, goMap2 );
				label = label.replaceAll(" ", "\n");
				
				graph.setLabelText(node, label);
				
				graph.setSize(node, graph.getLabelLayout(node)[0].getBox().width + 10, graph.getLabelLayout(node)[0].getBox().height + 5);
				
				addedNodes.put(gnode, node);
			}
			
			Collection<GONode> parents = GOSearchUtils.getDirectParentNodes( gnode ) ;
			if (parents != null) {
				Iterator<GONode> piter = parents.iterator();
				while (piter.hasNext()) {
					GONode gparent = (GONode)(piter.next());
					Node parent = null;

					this.modifyRealize(realizer, gparent, goMap, goMap2);
					
					if (addedNodes.containsKey(gparent))
						parent = (Node)(addedNodes.get(gparent));
					else {
						parent = graph.createNode();

						String label = getLabel( gparent, goMap, goMap2 );
						label = label.replaceAll(" ", "\n");
					
						graph.setLabelText(parent, label);
					
						graph.setSize(parent, graph.getLabelLayout(parent)[0].getBox().width + 10, graph.getLabelLayout(parent)[0].getBox().height + 5);
					
						addedNodes.put(gparent, parent);
					}
				
					// Create the directed edge in the graph
					graph.createEdge(parent, node);
				}
			}
		}
		
		//SmartOrganicLayouter layouter = new SmartOrganicLayouter();
		//layouter.setCompactness(0.25);
		//layouter.setQualityTimeRatio(1.0);
		//layouter.setMinimalNodeDistance(20);
		
		//BalloonLayouter layouter = new BalloonLayouter();
		HierarchicLayouter layouter = new HierarchicLayouter();
		//OrganicLayouter layouter = new OrganicLayouter();
		layouter.setOrientationLayouter(new OrientationLayouter(OrientationLayouter.TOP_TO_BOTTOM));
		//layouter.setOrientationLayouter(new OrientationLayouter(OrientationLayouter.LEFT_TO_RIGHT));
		layouter.setLayoutStyle(HierarchicLayouter.TREE);
	    layouter.doLayout(graph);	    
	    
	    JPGIOHandler jpg = new JPGIOHandler();
	    jpg.setQuality((float)(7.0));
	    Graph2DView view = jpg.createDefaultGraph2DView(graph);
	    BufferedImage bi = (BufferedImage)(view.getImage());
		
		return bi;
	}
	
	private Color getColor( GONode node, Map<GONode, HypergeometricPValue> goMap, Map<GONode, HypergeometricPValue> goMap2 ) {
		int base = 180;

		int green = base;
		int blue = base;
		int red = base;
		
		// does not pass cutoff in either set
		if( goMap != null && goMap.containsKey( node ) && goMap.get( node ).getPvalue() > 0.01 ) {
			if( goMap2 != null && goMap2.containsKey( node ) && goMap2.get( node ).getPvalue() > 0.01 )
				return new Color( base, base, base);
		}
		
		
		// is found in both sets
		if( goMap != null && goMap.containsKey( node ) && goMap2 != null && goMap2.containsKey( node ) ) {
			base = 0;
			int max = 200;
			
			green = 0;
			blue = base;
			red = base;
			
			if( goMap != null && goMap.containsKey( node ) ) {
				int modifier = -1 * (int)(Math.log( goMap.get( node ).getPvalue() ) );
				modifier *= modifier;
				
				red += modifier;
				if( red > max ) red = max;
			}
			
			if( goMap2 != null && goMap2.containsKey( node ) ) {
				int modifier = -1 * (int)(Math.log( goMap2.get( node ).getPvalue() ) );
				modifier *= modifier;
				
				blue += modifier;
				if( blue > max ) blue = max;
			}
			
			return new Color(red,green,blue);
		}
		
		// found in set 1, but not 2
		if( goMap != null && goMap.containsKey( node ) ) {
			base = 255;
			int min = 100;
			
			green = 255;
			blue = base;
			red = base;
			
			int modifier = -1 * (int)(Math.log( goMap.get( node ).getPvalue() ) );
			modifier *= modifier;
			
			blue -= modifier;
			if( blue < min ) blue = min;

			green -= modifier;
			if( green < min ) green = min;
			
			return new Color(red,green,blue);
		}
			
		// found in set 2, but not 1
		if( goMap2 != null && goMap2.containsKey( node ) ) {
			base = 255;
			int min = 100;
			
			green = 255;
			blue = base;
			red = base;
			
			int modifier = -1 * (int)(Math.log( goMap2.get( node ).getPvalue() ) );
			modifier *= modifier;
			
			red -= modifier;
			if( red < min ) red = min;

			green -= modifier;
			if( green < min ) green = min;
			
			return new Color(red,green,blue);	
		}
		
		
		return new Color(red,base,blue);
		
	}
	
	private void modifyRealize( ShapeNodeRealizer realizer, GONode node, Map<GONode, HypergeometricPValue> goMap, Map<GONode, HypergeometricPValue> goMap2 ) {
		
		realizer.setFillColor( this.getColor( node, goMap, goMap2 ) );
		
		if( goMap != null && goMap.containsKey( node ) && goMap2 != null && goMap2.containsKey( node ) ) {
			//realizer.setShapeType( ShapeNodeRealizer.RECT_3D );
						
			NodeLabel nl = new NodeLabel();
			nl.setFontSize(11);
			nl.setTextColor( new Color (255, 255, 255) );
			realizer.setLabel(nl);		
			
		} else {
			
			//realizer.setShapeType( ShapeNodeRealizer.ROUND_RECT );
			
			NodeLabel nl = new NodeLabel();
			nl.setFontSize(11);
			realizer.setLabel(nl);
			
		}
	}
	
	
	/**
	 * @return Returns the seedNodes.
	 */
	public Set getSeedNodes() {
		return seedNodes;
	}
	/**
	 * @param seedNodes The seedNodes to set.
	 */
	public void setSeedNodes(Set seedNodes) {
		this.seedNodes = seedNodes;
	}
	private Set seedNodes;
	
}
