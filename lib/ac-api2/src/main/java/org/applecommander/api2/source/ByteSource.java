package org.applecommander.api2.source;

import java.net.URL;
import java.nio.file.Path;

import org.applecommander.api2.util.GZIP;

public class ByteSource {
    public static ByteSource from(Path path) {
        return new ByteSource(new PathReaderWriter(path));
    }
    public static ByteSource from(URL url) {
        return new ByteSource(new URLReaderWriter(url));
    }
    public static ByteSource create(int size) {
    	return create(new byte[size]);
    }
    public static ByteSource create(byte[] data) {
    	return new ByteSource(new ArrayReaderWriter(data));
    }
    
    private ByteReaderWriter rw;
    private byte[] data;
    private boolean isGzipCompressed;
    
    private ByteSource(ByteReaderWriter rw) {
        this.rw = rw;
        this.data = rw.read();
        
        GZIP.decompressOptional(this.data).ifPresent(bytes -> {
        	this.data = bytes;
        	this.isGzipCompressed = true;
        });
    }
    
    public byte[] read(int offset, int length) {
        byte[] chunk = new byte[length];
        System.arraycopy(data, offset, chunk, 0, length);
        return chunk;
    }
    public void write(int offset, byte[] chunk) {
        System.arraycopy(chunk, 0, data, offset, chunk.length);
    }
    public int size() {
    	return data.length;
    }
    
    public boolean canSave() {
        return rw.canWrite();
    }
    public void save() {
        if (isGzipCompressed) {
            rw.write(GZIP.compress(data));
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
