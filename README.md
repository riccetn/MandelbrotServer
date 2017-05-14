# Mandelbrot server

[JAX-RS 2.0][JAX-RS] application for generating [portable graymap][WIKI_PGM] images of the [Mandelbrot set][WIKI_MS]. It is
used together with the [Mandelbrot client][CLIENT] to parallelise the the generation of a single image.

As a JAX-RS 2.0 application it needs to run on a Java Enterprise Edition (JavaEE) Application Server (AS),
during development I have been using [WildFly 10][WildFly] for this.

## Building

This project uses [Apache Maven][MVN] as a build tool witch needs to be installed on the build system in
order to build the application.

Once Maven is installed you can build the project by running the following command in a terminal/shell in
the top-level directory of this project (the same directory that contains the pom.xml file). This generates
a war file for deployment that is located in the target directory.

```
mvn package
```

## Deploying to WildFly

This project is already set up to deploy to a WildFly application server running on localhost all that you
need to do is run the following command from a terminal/shell. You may be asked for a WilFly username and
password during deployment, please refer to WildFly documentation on how to set up a user. 

```
mvn wildfly:deply
```

If you are deploying to a remote server you will need to reconfigure the [WildFly Maven Plugin][WildFly_MVN] to inform it
where it can find the server.

[JAX-RS]: https://jax-rs-spec.java.net/
[WIKI_PGM]: https://en.wikipedia.org/wiki/Netpbm_format
[WIKI_MS]: https://en.wikipedia.org/wiki/Mandelbrot_set
[CLIENT]: https://www.github.com/riccetn/MandelbrotClient
[WildFly]: http://wildfly.org/
[MVN]: https://maven.apache.org/
[WildFly_MVN]: https://docs.jboss.org/wildfly/plugins/maven/latest/
