package org.applecommander.api2.source;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.net.URL;

class URLReaderWriter implements ByteReaderWriter {
    private URL url;
    
    public URLReaderWriter(URL url) {
        this.url = url;
    }

    @Override
    public byte[] read() {
        try {
            try (InputStream inputStream = url.openStream()) {
                return inputStream.readAllBytes();
            }
        } catch (IOException cause) {
            throw new UncheckedIOException(cause);
        }
    }
    @Override
    public boolean canWrite() {
        return false;
    }
    @Override
    public void write(byte[] data) {
        throw new UnsupportedOperationException("Unable to save remote URL sources.");
    }
    
}