package org.lab.insurance.core.serialization;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

public class DefaultExclusionStrategy implements ExclusionStrategy {

	@Override
	public boolean shouldSkipField(FieldAttributes f) {
		return f.getAnnotation(NotSerializable.class) != null;
	}

	@Override
	public boolean shouldSkipClass(Class<?> clazz) {
		return clazz.getAnnotation(NotSerializable.class) != null;
	}

}
