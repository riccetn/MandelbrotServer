package se.narstrom.mandelbrot.server;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 * This class only exists to carry the <code>{@literal @}ApplicationPath</code> annotation witch
 * tells the JAX-RS servlet the root path of the REST application.
 *
 * @author Rickard Närström &lt;rickard@narstrom.se&gt;
 */
@ApplicationPath("rest")
public class RestApplication extends Application {
}
