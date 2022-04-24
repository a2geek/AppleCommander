package org.applecommander.api2.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Optional;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class GZIP {
	private GZIP() {
		// Prevent construction
	}
	
	public static byte[] decompress(byte[] source) throws IOException {
        try (GZIPInputStream inputStream = new GZIPInputStream(new ByteArrayInputStream(source))) {
            return inputStream.readAllBytes();
        }
	}
	public static Optional<byte[]> decompressOptional(byte[] source) {
		try {
			return Optional.of(decompress(source));
		} catch (IOException cause) {
			return Optional.empty();
		}
	}
	
	public static byte[] compress(byte[] data) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (GZIPOutputStream outputStream = new GZIPOutputStream(baos)) {
            outputStream.write(data);
        } catch (IOException cause) {
            throw new UncheckedIOException(cause);
        }
        return baos.toByteArray();
	}
}
