package se.narstrom.mandelbrot.server;

import java.awt.image.BufferedImage;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

@Path("mandelbrot")
public class MandelbrotResource {
	@Inject
	private MandelbrotGenerator generator;

	@GET
	@Path("{realMin}/{realMax}/{imagMin}/{imagMax}/{width}/{height}/{nMaxIterations}")
	@Produces("application/x-portable-graymap")
	public BufferedImage getMandelbrot(
			@PathParam("realMin") double realMin,
			@PathParam("realMax") double realMax,
			@PathParam("imagMin") double imagMin,
			@PathParam("imagMax") double imagMax,
			@PathParam("width") int width,
			@PathParam("height") int height,
			@PathParam("nMaxIterations") int nMaxIterations)
	{
		return generator.generate(realMin, realMax, imagMin, imagMax, width, height, nMaxIterations);
	}
}
