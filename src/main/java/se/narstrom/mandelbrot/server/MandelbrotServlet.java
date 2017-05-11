package se.narstrom.mandelbrot.server;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.imageio.ImageWriter;
import javax.inject.Inject;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.math3.complex.Complex;

/**
 *
 * @author Rickard Närström <rickard@narstrom.se>
 */
@WebServlet("/mandelbrot/*")
public class MandelbrotServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final Pattern uriPattern = Pattern.compile("/mandelbrot/(.*)/(.*)/(.*)/(.*)/(.*)/(.*)/(.*)");

	@Inject
	private MandelbrotGenerator generator;

	private Complex minC, maxC;
	private int width, height;
	private int infN;

	private void parseURI(String uri) throws HttpError {
		String contextPath = this.getServletContext().getContextPath();
		assert uri.startsWith(contextPath);

		uri = uri.substring(contextPath.length());
		Matcher uriMatcher = uriPattern.matcher(uri);
		if(!uriMatcher.matches())
			throw new NotFound();

		try {
			minC = new Complex(Double.parseDouble(uriMatcher.group(1)), Double.parseDouble(uriMatcher.group(2)));
			maxC = new Complex(Double.parseDouble(uriMatcher.group(3)), Double.parseDouble(uriMatcher.group(4)));
			width = Integer.parseInt(uriMatcher.group(5));
			height = Integer.parseInt(uriMatcher.group(6));
			infN = Integer.parseInt(uriMatcher.group(7));
		} catch(NumberFormatException ex) {
			throw new BadRequest(ex);
		}
	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		try {
			this.parseURI(request.getRequestURI());

			response.setContentType("image/png");
			BufferedImage img = generator.generate(minC.getReal(), maxC.getReal(), minC.getImaginary(), maxC.getImaginary(), width, height, infN);
			ImageWriter writer = ImageIO.getImageWritersByMIMEType("image/png").next();
			writer.setOutput(ImageIO.createImageOutputStream(response.getOutputStream()));
			writer.write(img);
		} catch(HttpError err) {
			response.sendError(err.getStatus(), err.getMessage());
		}
	}
}
