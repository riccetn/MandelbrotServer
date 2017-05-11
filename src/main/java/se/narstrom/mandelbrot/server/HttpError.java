package se.narstrom.mandelbrot.server;

public class HttpError extends Exception {
	private static final long serialVersionUID = 1L;
	private int status;

	public HttpError(int status, String message) {
		super(message);
		this.status = status;
	}

	public int getStatus() {
		return this.status;
	}
}
