package org.lab.insurance.core.serialization;

import java.lang.reflect.Type;

@Deprecated
public class Serializer {

	public String toJson(Object src, Type typeOfSrc) {
		throw new RuntimeException("deprecated");
		// return serializer.toJson(src, typeOfSrc);
	}

	public String toJson(Object src) {
		throw new RuntimeException("deprecated");
		// return serializer.toJson(src);
	}

	public <T> T fromJson(String json, Class<T> classOfT) {
		throw new RuntimeException("deprecated");
		// return serializer.fromJson(json, classOfT);
	}

	public <T> T fromJson(String json, Type type) {
		throw new RuntimeException("deprecated");
		// return serializer.fromJson(json, type);
	}

}