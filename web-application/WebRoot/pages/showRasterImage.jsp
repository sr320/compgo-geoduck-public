<%@ page contentType="image/png" 
    import="java.awt.*,java.awt.image.*,
      com.sun.image.codec.jpeg.*,java.util.*,java.awt.image.renderable.*,javax.media.jai.*,
      javax.media.jai.operator.*"
%><%


try {

// Send back image
	ServletOutputStream sos = response.getOutputStream();
	
//JPEGImageEncoder encoder = 
  //JPEGCodec.createJPEGEncoder(sos);
//encoder.encode((Raster)(request.getAttribute("image")));


	EncodeDescriptor.create( ((RenderedImage)(request.getAttribute("image"))), sos, "png", null, null);

} catch ( Exception e ) {

	System.out.println("Exception rendering image in showRasterImage.jsp, QueryString = " + request.getQueryString() + ", RequestURI = " + request.getRequestURI() );
	
	e.printStackTrace();
	
	throw e;
}

%>