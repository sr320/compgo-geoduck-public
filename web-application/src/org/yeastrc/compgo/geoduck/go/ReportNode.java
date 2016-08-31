package org.yeastrc.compgo.geoduck.go;

import java.util.Collection;

import org.yeastrc.compgo.geoduck.analysis.HypergeometricPValue;
import org.yeastrc.compgo.geoduck.protein.Protein;

public class ReportNode implements Comparable<ReportNode> {

	private GONode node;
	private HypergeometricPValue hpv1;
	private HypergeometricPValue hpv2;
	private Collection<Protein> proteins1;
	private Collection<Protein> proteins2;
	
	 public int compareTo(ReportNode rn) {
		 
		 final int BEFORE = -1;
		 final int EQUAL = 0;
		 final int AFTER = 1;
		 
		 if( this.hpv1 == null && rn.hpv1 == null && this.hpv2 == null && rn.hpv2 == null ) return EQUAL;

		 double thisValue;
		 double thatValue;
		 
		 if( this.hpv1 != null ) {
			 thisValue = this.hpv1.getPvalue();
		 } else if( this.hpv2 != null ) {
			 thisValue = this.hpv2.getPvalue();
		 } else {
			 
			 // this should never happen
			 thisValue = 1;
		 }
		 
		 if( rn.getHpv1() != null ) {
			 thatValue = rn.getHpv1().getPvalue();
		 } else if( rn.getHpv2() != null ) {
			 thatValue = rn.getHpv2().getPvalue();
		 } else {
			 
			 // this should never happen
			 thatValue = 1;
		 }
		
		 if( thisValue == thatValue ) return EQUAL;
		 if( thisValue < thatValue ) return BEFORE;
		 return AFTER;
	}

	public GONode getNode() {
		return node;
	}

	public void setNode(GONode node) {
		this.node = node;
	}

	public HypergeometricPValue getHpv1() {
		return hpv1;
	}

	public void setHpv1(HypergeometricPValue hpv1) {
		this.hpv1 = hpv1;
	}

	public HypergeometricPValue getHpv2() {
		return hpv2;
	}

	public void setHpv2(HypergeometricPValue hpv2) {
		this.hpv2 = hpv2;
	}

	public Collection<Protein> getProteins1() {
		return proteins1;
	}

	public void setProteins1(Collection<Protein> proteins1) {
		this.proteins1 = proteins1;
	}

	public Collection<Protein> getProteins2() {
		return proteins2;
	}

	public void setProteins2(Collection<Protein> proteins2) {
		this.proteins2 = proteins2;
	}
	
	 
	
}
