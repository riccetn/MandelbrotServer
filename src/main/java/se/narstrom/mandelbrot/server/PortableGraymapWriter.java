package se.narstrom.mandelbrot.server;

import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;

@Provider
@Produces("application/x-portable-graymap")
public class PortableGraymapWriter implements MessageBodyWriter<BufferedImage> {

	@Override
	public long getSize(BufferedImage img, Class<?> cls, Type type, Annotation[] annotations, MediaType mediaType) {
		// this method is deprecated since JAX-RS 2.0, the official recommendation is to return -1
		return -1;
	}

	@Override
	public boolean isWriteable(Class<?> cls, Type type, Annotation[] annotations, MediaType mediaType) {
		return cls == BufferedImage.class;
	}

	@Override
	public void writeTo(BufferedImage img, Class<?> cls, Type type, Annotation[] annotations, MediaType mediaType,
			MultivaluedMap<String, Object> headers, OutputStream outStream) throws IOException, WebApplicationException
	{
		if(img.getType() != BufferedImage.TYPE_BYTE_GRAY)
			throw new InternalServerErrorException("image most be of TYPE_BYTE_GRAY");
		PrintWriter out = new PrintWriter(new OutputStreamWriter(outStream, "US-ASCII"));

		Raster raster = img.getData();
		out.println("P2"); // Magic
		out.println(img.getWidth() + " " + img.getHeight());
		out.println("256"); // Shades of gray
		for(int row = 0; row < img.getHeight(); ++row) {
			for(int col = 0; col < img.getWidth(); ++col) {
				if(col != 0)
					out.print(' ');
				out.print(raster.getSample(col, row, 0));
			}
			out.println();
		}
		out.flush();
	}
}
