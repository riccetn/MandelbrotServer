package se.narstrom.mandelbrot.server;

public class BadRequest extends HttpError {
	private static final long serialVersionUID = 1L;

	public BadRequest() {
		super(400, "Bad Request");
	}

	public BadRequest(Throwable cause) {
		super(400, cause);
	}
}
