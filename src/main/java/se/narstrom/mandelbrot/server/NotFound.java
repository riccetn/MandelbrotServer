package se.narstrom.mandelbrot.server;

import javax.servlet.http.HttpServletResponse;

public class NotFound extends HttpError {
	private static final long serialVersionUID = 1L;

	public NotFound() {
		super(HttpServletResponse.SC_NOT_FOUND, "Not Found");
	}
}
