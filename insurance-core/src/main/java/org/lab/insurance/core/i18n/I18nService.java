package org.lab.insurance.core.i18n;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@Deprecated
public class I18nService {

	private static final String DEFAULT_LANG = "it";
	private static final Logger LOG = LoggerFactory.getLogger(I18nService.class);

	private final Map<String, String> availableResources;
	private final Map<String, JsonObject> jsonMap;

	public I18nService() {
		availableResources = new HashMap<String, String>();
		availableResources.put("it", "i18n/it.json");
		availableResources.put("es", "i18n/es.json");
		availableResources.put("en", "i18n/en.json");
		jsonMap = new HashMap<String, JsonObject>();
		for (String key : availableResources.keySet()) {
			String resourcePath = availableResources.get(key);
			try {
				InputStream inputStream = Thread.currentThread().getContextClassLoader()
						.getResourceAsStream(resourcePath);
				Reader reader = new InputStreamReader(inputStream, Charset.forName("UTF8"));
				JsonParser parser = new JsonParser();
				JsonElement jsonElement = parser.parse(reader);
				jsonMap.put(key, jsonElement.getAsJsonObject());
			}
			catch (Exception ex) {
				LOG.error("Cant load JSON resource {}", resourcePath, ex);
			}
		}
	}

	public String get(String key) {
		return get(key, DEFAULT_LANG);
	}

	public String get(String key, String locale) {
		String result = String.format("{%s}", key);
		if (jsonMap.containsKey(locale)) {
			JsonObject json = jsonMap.get(locale);
			if (json.get(key) != null) {
				result = json.get(key).getAsString();
			}
		}
		return result;
	}

	public InputStream getJson() {
		return getJson(DEFAULT_LANG);
	}

	public InputStream getJson(String locale) {
		InputStream inputStream = null;
		if (availableResources.containsKey(locale)) {
			String resource = availableResources.get(locale);
			inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(resource);
			Validate.notNull(inputStream, "Missing resource " + resource);
		}
		return inputStream;
	}
}
