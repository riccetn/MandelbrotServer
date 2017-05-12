package se.narstrom.mandelbrot.server;

import javax.servlet.http.HttpServletResponse;

public class BadRequest extends HttpError {
	private static final long serialVersionUID = 1L;

	public BadRequest(Throwable cause) {
		super(HttpServletResponse.SC_BAD_REQUEST, cause);
	}
}
