package br.com.xht.serialzebrabyte;

public class ExemploTeste extends SerialZebraByte {

	@ByteField(fieldId = (byte) 0x1a)
	private String a;

	@ByteField(fieldId = (byte) 0x1b)
	private int b;

	@ByteField(fieldId = (byte) 0x1c)
	private byte[] c;

	@ByteField(fieldId = (byte) 0x1d)
	private short d;

	@ByteField(fieldId = (byte) 0x1e)
	private byte e;

	public String getA() {
		return a;
	}

	public void setA(String a) {
		this.a = a;
	}

	public int getB() {
		return b;
	}

	public void setB(int b) {
		this.b = b;
	}

	public byte[] getC() {
		return c;
	}

	public void setC(byte[] c) {
		this.c = c;
	}

	public short getD() {
		return d;
	}

	public void setD(short d) {
		this.d = d;
	}

	public byte getE() {
		return e;
	}

	public void setE(byte e) {
		this.e = e;
	}

}
