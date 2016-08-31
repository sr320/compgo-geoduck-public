package org.yeastrc.compgo.geoduck.go;

public class GOUtils {

	public static String BIOLOGICAL_PROCESS = "P";
	public static String CELLULAR_COMPONENT = "C";
	public static String MOLECULAR_FUNCTION = "F";
	
	public static String getAspect( String aspect_string ) {
		if( aspect_string.equals( "biological_process" ) ) return "P";
		if( aspect_string.equals( "molecular_function" ) ) return "F";
		if( aspect_string.equals( "cellular_component" ) ) return "C";
		
		return "unknown";
	}
	
	public static GONode getAllNode() throws Exception {
		if( allNode == null )
			allNode = GONodeFactory.getInstance().getGONode( "all" );
		
		return allNode;
	}
	private static GONode allNode;
}
