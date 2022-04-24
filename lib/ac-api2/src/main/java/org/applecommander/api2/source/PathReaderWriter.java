package org.applecommander.api2.source;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;

class PathReaderWriter implements ByteReaderWriter {
    private Path path;
    
    public PathReaderWriter(Path path) {
        this.path = path;
    }
    
    public void write(byte[] data) {
        try {
            Files.write(path, data);
        } catch (IOException cause) {
            throw new UncheckedIOException(cause);
        }
    }
    public byte[] read() {
        try {
            return Files.readAllBytes(path);
        } catch (IOException cause) {
            throw new UncheckedIOException(cause);
        }
    }
}