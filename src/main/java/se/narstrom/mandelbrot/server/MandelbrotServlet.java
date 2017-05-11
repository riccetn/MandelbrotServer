package se.narstrom.mandelbrot.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
			PrintWriter out = response.getWriter();
			this.parseURI(request.getRequestURI());

			//response.setContentType("application/x-portable-graymap");
			response.setContentType("text/plain");
			out.println("P2"); // Magic for "Portable Gray Map" (PGM)
			out.printf("%d %d%n", width, height);
			out.println("255"); // No of shades of gray (and no there is not fifty)
			for(int r = 0; r < height; ++r) {
				for(int c = 0; c < width; ++c) {
					if(c != 0)
						out.write(' ');
					out.print((r+c)%255);
				}
				out.println();
			}
		} catch(HttpError err) {
			response.sendError(err.getStatus(), err.getMessage());
		}
	}
}
