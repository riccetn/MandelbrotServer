package se.narstrom.mandelbrot.server;

public class NotFound extends HttpError {
	private static final long serialVersionUID = 1L;

	public NotFound() {
		super(404, "Not Found");
	}
}
