package org.applecommander.api2.util;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

public class GZIPTest {
	@Test
	public void testCompressDecompress() throws IOException {
		final byte[] original = "Hello World!".getBytes();
		
		final byte[] compressed = GZIP.compress(original);
		final byte[] decompressed = GZIP.decompress(compressed);

		assertThat(original, equalTo(decompressed));
		assertThat(original, not(equalTo(compressed)));
	}
	
	@Test
	public void testDecompressOptional() {
		final byte[] original = "Hello World!".getBytes();
		
		final byte[] compressed = GZIP.compress(original);

		// We expect output
		GZIP.decompressOptional(compressed).ifPresentOrElse(actual -> {
			assertThat(original, equalTo(actual));
		}, () -> {
			Assert.fail("Expected output from decompression.");
		});
		
		// We do not expect output
		GZIP.decompressOptional(original).ifPresentOrElse(actual -> {
			Assert.fail("Unexpected output from uncompressed text.");
		}, () -> {
			// Success
		});
	}
}
