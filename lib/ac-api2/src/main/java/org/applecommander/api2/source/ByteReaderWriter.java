package org.applecommander.api2.source;

interface ByteReaderWriter {
    public byte[] read();
    public default boolean canWrite() { return true; }
    public void write(byte[] data);
}