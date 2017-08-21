package org.lab.insurance.core.serialization;

import java.lang.reflect.Type;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Serializer {

	private final static Logger LOG = LoggerFactory.getLogger(Serializer.class);

	private final Gson serializer;
	private final GsonBuilder gsonBuilder;

	public Serializer() {
		LOG.info("Initializing serializer");
		gsonBuilder = new GsonBuilder();
		gsonBuilder.addSerializationExclusionStrategy(new DefaultExclusionStrategy());
		gsonBuilder.registerTypeHierarchyAdapter(Date.class,
				new DateSerializer("yyyy-MM-dd'T'HH:mm:ss.SSSZ", "dd/MM/yyyy", "ddMMyyyy"));
		serializer = gsonBuilder.create();
	}

	public String toJson(Object src, Type typeOfSrc) {
		return serializer.toJson(src, typeOfSrc);
	}

	public String toJson(Object src) {
		return serializer.toJson(src);
	}

	public <T> T fromJson(String json, Class<T> classOfT) {
		return serializer.fromJson(json, classOfT);
	}

	public <T> T fromJson(String json, Type type) {
		return serializer.fromJson(json, type);
	}

	public Gson get() {
		return serializer;
	}

	public GsonBuilder getBuilder() {
		return gsonBuilder;
	}
}