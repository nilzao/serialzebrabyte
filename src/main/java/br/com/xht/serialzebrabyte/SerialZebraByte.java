package br.com.xht.serialzebrabyte;

import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import java.util.HashMap;

import org.apache.http.util.ByteArrayBuffer;

public class SerialZebraByte {

	private static final int BUFFER_LIMIT = 2048;

	private HashMap<String, Field> fields = new HashMap<>();

	public byte[] serialize() throws Exception {
		ByteArrayBuffer byteArrayBuffer = new ByteArrayBuffer(BUFFER_LIMIT);
		Field[] declaredFields = this.getClass().getDeclaredFields();
		Field.setAccessible(declaredFields, Boolean.TRUE);
		for (Field field : declaredFields) {
			byte[] fieldValueToByteArray = fieldValueToByteArray(field);
			if (fieldValueToByteArray != null) {
				byte[] fieldName = field.getName().getBytes();
				byteArrayBuffer.append(fieldName, 0, fieldName.length);
				byteArrayBuffer.append(fieldValueToByteArray, 0, fieldValueToByteArray.length);
			}
		}
		return byteArrayBuffer.toByteArray();
	}

	private byte[] fieldValueToByteArray(Field field) throws Exception {
		byte[] byteTmp = null;
		Object objectTmp = field.get(this);
		if (objectTmp instanceof String) {
			return stringToByteArray((String) objectTmp);
		} else if (objectTmp instanceof Integer) {
			return intToByteArray((Integer) objectTmp);
		} else if (field.getType() == byte[].class) {
			return byteArrayToByteArray((byte[]) objectTmp);
		} else if (objectTmp instanceof Short) {
			return shortToByteArray((short) objectTmp);
		} else if (objectTmp instanceof Byte) {
			return new byte[] { (byte) objectTmp };
		}
		return byteTmp;
	}

	private byte[] byteArrayToByteArray(byte[] objectTmp) {
		ByteBuffer byteBuffer = ByteBuffer.allocate(objectTmp.length + 1);
		byteBuffer.put((byte) objectTmp.length);
		byteBuffer.put(objectTmp);
		return byteBuffer.array();
	}

	private byte[] stringToByteArray(String stringTmp) {
		byte length = (byte) stringTmp.length();
		ByteBuffer byteBuffer = ByteBuffer.allocate(length + 1);
		byteBuffer.put(length);
		byteBuffer.put(stringTmp.getBytes());
		return byteBuffer.array();
	}

	private byte[] shortToByteArray(short shortTmp) {
		return ByteBuffer.allocate(2).putShort(shortTmp).array();
	}

	private byte[] intToByteArray(Integer intTmp) {
		return ByteBuffer.allocate(4).putInt(intTmp).array();
	}

	private void loadFields() {
		Field[] declaredFields = this.getClass().getDeclaredFields();
		Field.setAccessible(declaredFields, Boolean.TRUE);
		for (Field field : declaredFields) {
			fields.put(field.getName(), field);
		}
	}

	public void deserialize(byte[] data) throws Exception {
		if (data != null && data.length > 0) {
			loadFields();
			int startFrom = 0;
			while ((startFrom = parseData(data, startFrom)) != -1) {
				// nada
			}
		}
	}

	private int parseData(byte[] data, int start) throws Exception {
		if (start >= data.length) {
			return -1;
		}
		byte[] fieldNameByte = { data[start] };
		String fieldName = new String(fieldNameByte);
		Field field = fields.get(fieldName);
		return parseDataBytes(data, start, field);
	}

	private int parseDataBytes(byte[] data, int start, Field field) throws Exception {
		Class<?> type = field.getType();
		if (type.isAssignableFrom(String.class)) {
			int byteTmpSize = data[start + 1];
			byte[] bytesTmp = new byte[byteTmpSize];
			System.arraycopy(data, start + 2, bytesTmp, 0, byteTmpSize);
			String string = new String(bytesTmp);
			field.set(this, string);
			return start + byteTmpSize + 2;
		} else if (type.isAssignableFrom(Integer.class) || type == int.class) {
			byte[] bytesTmp = new byte[4];
			System.arraycopy(data, start + 1, bytesTmp, 0, 4);
			ByteBuffer wrapped = ByteBuffer.wrap(bytesTmp);
			int int1 = wrapped.getInt();
			field.set(this, int1);
			return start + 5;
		} else if (type == byte[].class) {
			int byteTmpSize = data[start + 1];
			byte[] bytesTmp = new byte[byteTmpSize];
			System.arraycopy(data, start + 2, bytesTmp, 0, byteTmpSize);
			field.set(this, bytesTmp);
			return start + byteTmpSize + 2;
		} else if (type.isAssignableFrom(Short.class) || type == short.class) {
			byte[] bytesTmp = new byte[2];
			System.arraycopy(data, start + 1, bytesTmp, 0, 2);
			ByteBuffer wrapped = ByteBuffer.wrap(bytesTmp);
			short short1 = wrapped.getShort();
			field.set(this, short1);
			return start + 3;
		} else if (type.isAssignableFrom(Byte.class) || type == byte.class) {
			byte byteTmp = data[start + 1];
			field.set(this, byteTmp);
			return start + 2;
		}
		return -1;
	}

	public static void main(String[] args) throws Exception {
		ExemploTeste exemploTeste = new ExemploTeste();
		exemploTeste.setA("string qualquer");
		exemploTeste.setB(222);
		byte[] byteArray = { (byte) 0xa0, (byte) 0xb1 };
		exemploTeste.setC(byteArray);
		exemploTeste.setD((short) 999);
		exemploTeste.setE((byte) 0xae);
		byte[] data = exemploTeste.serialize();
		for (byte b : data) {
			System.out.print(String.format("%02x", b) + ":");
		}
		System.out.println("");
		ExemploTeste exemploTeste2 = new ExemploTeste();
		exemploTeste2.deserialize(data);
		System.out.println(exemploTeste2.getA());
		System.out.println(exemploTeste2.getB());
		byte[] c = exemploTeste2.getC();
		for (byte b : c) {
			System.out.print(String.format("%02x", b) + ":");
		}
		System.out.println("");
		System.out.println(exemploTeste2.getD());
		System.out.println(String.format("%02x", exemploTeste2.getE()));

	}

}
