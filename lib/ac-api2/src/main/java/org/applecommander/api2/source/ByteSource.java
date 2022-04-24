package org.applecommander.api2.source;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URL;
import java.nio.file.Path;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class ByteSource {
    public static ByteSource from(Path path) {
        return new ByteSource(new PathReaderWriter(path));
    }
    public static ByteSource from(URL url) {
        return new ByteSource(new URLReaderWriter(url));
    }
    
    
    private ByteReaderWriter rw;
    private byte[] data;
    private boolean isGzipCompressed;
    
    private ByteSource(ByteReaderWriter rw) {
        this.rw = rw;
        this.data = rw.read();
        
        try (GZIPInputStream inputStream = new GZIPInputStream(new ByteArrayInputStream(this.data))) {
            this.data = inputStream.readAllBytes();
            this.isGzipCompressed = true;
        } catch (IOException ignored) {
            // Intentionally ignoring the exception
        }
    }
    
    public byte[] read(int offset, int length) {
        byte[] chunk = new byte[length];
        System.arraycopy(data, offset, chunk, 0, length);
        return chunk;
    }
    public void write(int offset, byte[] chunk) {
        System.arraycopy(chunk, 0, data, offset, chunk.length);
    }
    
    public boolean canSave() {
        return rw.canWrite();
    }
    public void save() {
        if (isGzipCompressed) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            try (GZIPOutputStream outputStream = new GZIPOutputStream(baos)) {
                outputStream.write(data);
                outputStream.flush();
            } catch (IOException cause) {
                throw new UncheckedIOException(cause);
            }
            rw.write(baos.toByteArray());
        }
        else {
            rw.write(data);
        }
    }
    public void saveAs(Path newPath) {
        rw = new PathReaderWriter(newPath);
        save();
    }
}
