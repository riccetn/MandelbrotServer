package se.narstrom.mandelbrot.server;

import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;

import org.apache.commons.math3.complex.Complex;

public class MandelbrotGenerator {
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
				while(nIterations < nMaxIterations && z.abs() <= c.abs()) {
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
