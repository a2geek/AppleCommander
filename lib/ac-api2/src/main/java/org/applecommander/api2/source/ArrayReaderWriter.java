package org.applecommander.api2.source;

class ArrayReaderWriter implements ByteReaderWriter {
	private byte[] data;
	
	ArrayReaderWriter(byte[] data) {
		this.data = data;
	}

	@Override
	public byte[] read() {
		return data;
	}
	
	@Override
	public boolean canWrite() {
		return false;
	}

	@Override
	public void write(byte[] data) {
		throw new UnsupportedOperationException("Unable to save new sources.");
	}

}
