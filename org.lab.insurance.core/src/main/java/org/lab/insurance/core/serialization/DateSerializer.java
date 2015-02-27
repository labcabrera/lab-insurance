package org.lab.insurance.core.serialization;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

/**
 * Serializador de fechas que permite especificar vía constructor varios formatos posibles de entrada (deserialización) Para los datos de
 * salida (serialización) se emplea el primer formato de fechas pasado en el constructor.
 */
public class DateSerializer implements JsonSerializer<Date>, JsonDeserializer<Date> {

	private List<DateTimeFormatter> dateFormats;

	public DateSerializer() {
		this("dd/MM/yyyy");
	}

	public DateSerializer(String... stringFormats) {
		dateFormats = new ArrayList<DateTimeFormatter>();
		for (String format : stringFormats) {
			dateFormats.add(DateTimeFormat.forPattern(format));
		}
	}

	@Override
	public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
		if (json != null) {
			for (DateTimeFormatter format : dateFormats) {
				try {
					return format.parseDateTime(json.getAsString()).toDate();
				} catch (Exception e) {
				}
			}
		}
		return null;
	}

	@Override
	public JsonElement serialize(Date src, Type typeOfSrc, JsonSerializationContext context) {
		return src == null ? null : new JsonPrimitive(dateFormats.iterator().next().print(src.getTime()));
	}
}