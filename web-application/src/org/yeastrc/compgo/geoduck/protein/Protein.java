package org.yeastrc.compgo.geoduck.protein;


public class Protein {
	
	public int hashCode() {
		return this.id.hashCode();
	}
	
	public boolean equals( Object o ) {
		if( o == null ) return false;
		if( o == this) return true;
		try {
			if( ((Protein)o).getId().equals( this.id ) ) return true;
		} catch( Exception e ) { ; }
		
		return false;
	}
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSymbol() {
		return symbol;
	}
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	private String id;
	private String name;
	private String symbol;
	
}
