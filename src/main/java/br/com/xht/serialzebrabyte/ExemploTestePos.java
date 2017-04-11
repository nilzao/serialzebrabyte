package br.com.xht.serialzebrabyte;

public class ExemploTestePos extends SerialZebraBytePos {

	@ByteField(start = 0, size = 20)
	private String nome;

	@ByteField(start = 20)
	private int numeroInt;

	@ByteField(start = 24, size = 5)
	private byte[] byteArray;

	@ByteField(start = 29)
	private short numeroShort;

	@ByteField(start = 31)
	private byte byteSimples;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getNumeroInt() {
		return numeroInt;
	}

	public void setNumeroInt(int numeroInt) {
		this.numeroInt = numeroInt;
	}

	public byte[] getByteArray() {
		return byteArray;
	}

	public void setByteArray(byte[] byteArray) {
		this.byteArray = byteArray;
	}

	public short getNumeroShort() {
		return numeroShort;
	}

	public void setNumeroShort(short numeroShort) {
		this.numeroShort = numeroShort;
	}

	public byte getByteSimples() {
		return byteSimples;
	}

	public void setByteSimples(byte byteSimples) {
		this.byteSimples = byteSimples;
	}

}
