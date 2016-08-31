package org.yeastrc.compgo.geoduck.analysis;

import java.text.DecimalFormat;

public class HypergeometricPValue {
	
	public int getA() {
		return a;
	}
	public void setA(int a) {
		this.a = a;
	}
	public int getB() {
		return b;
	}
	public void setB(int b) {
		this.b = b;
	}
	public int getI() {
		return i;
	}
	public void setI(int i) {
		this.i = i;
	}
	public double getPvalue() {
		return pValue;
	}
	public void setPvalue(double pValue) {
		this.pValue = pValue;
	}
	public int getT() {
		return t;
	}
	public void setT(int t) {
		this.t = t;
	}
	public int getInputTotal() {
		return inputTotal;
	}
	public void setInputTotal(int inputTotal) {
		this.inputTotal = inputTotal;
	}
	public String getPvalueString() {
		return (new DecimalFormat("0.##E0")).format( this.getPvalue() );
	}


	private int a;
	private int b;
	private int i;
	private int t;
	
	private int inputTotal;
	
	
	private double pValue;
	
}
