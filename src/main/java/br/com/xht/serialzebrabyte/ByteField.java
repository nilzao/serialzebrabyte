package br.com.xht.serialzebrabyte;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target({ FIELD })
@Inherited
public @interface ByteField {

	public int start() default 0;

	public int size() default 1;

	public byte fieldId() default (byte) 0xff;

}
