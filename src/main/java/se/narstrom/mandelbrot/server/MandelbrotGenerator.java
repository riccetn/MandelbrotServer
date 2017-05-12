package se.narstrom.mandelbrot.server;

import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;

import org.apache.commons.math3.complex.Complex;

/**
 * Mandelbrot set image generator.
 *
 * <p>
 * Used to generate an gray scale image of the mandelbrot set.
 * </p>
 *
 * @author Rickard Närström &lt;rickard@narstrom.se&gt;
 * @see <a href="https://en.wikipedia.org/wiki/Mandelbrot_set">Mandelbrot Set on Wikipedia</a>
 */
public class MandelbrotGenerator {
	/**
	 * Do the actual image generation.
	 *
	 * @param minReal			The lower end of the real (horizontal) axis
	 * @param maxReal			The higher end of the real (horizontal) axis
	 * @param minImag			The lower end of the imaginary (vertical) axis
	 * @param maxImag			The higher end of the imaginary (vertical) axis
	 * @param width				Horizontal resolution of the output image
	 * @param height			Vertical resolution of the output image
	 * @param nMaxIterations	Maximum number of iterations before giving up
	 * @return The generated image
	 */
	public BufferedImage generate(double minReal, double maxReal, double minImag, double maxImag, int width, int height, int nMaxIterations) {
		if(minReal > maxReal)
			throw new IllegalArgumentException();
		if(minImag > maxImag)
			throw new IllegalArgumentException();
		if(width <= 0 || height <= 0)
			throw new IllegalArgumentException();
		if(nMaxIterations <= 0)
			throw new IllegalArgumentException();

		WritableRaster raster = Raster.createInterleavedRaster(DataBuffer.TYPE_BYTE, width, height, 1, null);
		for(int row = 0; row < height; ++row) {
			for(int col = 0; col < width; ++col) {
				Complex c = new Complex(
						minReal + col*(maxReal - minReal)/width,
						minImag + row*(maxImag - minImag)/height);
				Complex z = Complex.ZERO;
				int nIterations = 0;
				while(nIterations < nMaxIterations && z.abs() <= 4) {
					z = z.multiply(z).add(c);
					++nIterations;
				}
				raster.setSample(col, row, 0, nIterations%256);
			}
		}

		BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
		img.setData(raster);
		return img;
	}
}
