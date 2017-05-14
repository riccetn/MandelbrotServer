package se.narstrom.mandelbrot.server;

import java.awt.image.BufferedImage;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

/**
 * REST resource provider that handles HTTP requests on the '{context}/rest/mandelbrot' URI prefix.
 * Calls arriving via HTTP are forwarded to <code>MandelbrotGenerator</code> that will generate an
 * image of the requested portion of the mandelbrot set.
 *
 * @author Rickard Närström &lt;rickard@narstrom.se&gt;
 */
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
			@Valid @Min(1) @PathParam("width") int width,
			@Valid @Min(1) @PathParam("height") int height,
			@Valid @Min(1) @PathParam("nMaxIterations") int nMaxIterations)
	{
		if(realMin > realMax || imagMin > imagMax)
			throw new BadRequestException();
		return generator.generate(realMin, realMax, imagMin, imagMax, width, height, nMaxIterations);
	}
}
