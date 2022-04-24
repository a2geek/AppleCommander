package org.applecommander.api2.source;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.Test;

public class ByteSourceTest {
	@Test
	public void testOperations() {
		final byte[] expected = "Hello World!".getBytes();
		final byte[] modified = "Hello Folks!".getBytes();
		
		ByteSource src = ByteSource.create(expected);
		
		assertThat(expected, equalTo(src.read(0, src.size())));
		
		src.write(6, "Folks".getBytes());
		assertThat(modified, equalTo(src.read(0, src.size())));
	}
	
	@Test
	public void testFromPath() throws IOException {
		final byte[] expected = "Hello World!".getBytes();
		
		Path path = Files.createTempFile("test", "tmp");
		Files.write(path, expected);
		
		ByteSource src = ByteSource.from(path);
		
		assertThat(expected, equalTo(src.read(0, src.size())));
	}
	
	@Test
	public void testFromURL() throws IOException {
		final byte[] expected = "Hello World!".getBytes();
		
		Path path = Files.createTempFile("test", "tmp");
		Files.write(path, expected);
		
		ByteSource src = ByteSource.from(path.toUri().toURL());
		
		assertThat(expected, equalTo(src.read(0, src.size())));
	}
}
