package org.lab.insurance.model.jpa.converters;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class ClassConverter implements AttributeConverter<Class<?>, String> {

	@Override
	public String convertToDatabaseColumn(Class<?> value) {
		return value != null ? value.getName() : null;
	}

	@Override
	public Class<?> convertToEntityAttribute(String value) {
		try {
			return value != null ? Thread.currentThread().getContextClassLoader().loadClass(value) : null;
		} catch (ClassNotFoundException ex) {
			throw new RuntimeException(ex);
		}
	}

}
