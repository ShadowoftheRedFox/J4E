package fr.cyu.utils;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JSONUtil {
    private static ObjectMapper json = new ObjectMapper();
    public static String SERVER_ERROR = "{\"status\":500,\"error\":\"unknown error\"}";
    public static String NOT_FOUND_ERROR = "{\"status\":404,\"error\":\"page not found\"}";

    private JSONUtil() {
    }

    public static String stringify(Map<String, ? extends Object> data) {
        try {
            return json.writeValueAsString(data);
        } catch (JsonProcessingException e) {
            return SERVER_ERROR;
        }
    }

    public static String stringify(Collection<? extends Object> data) {
        try {
            return json.writeValueAsString(data);
        } catch (JsonProcessingException e) {
            System.err.println(e);
            return SERVER_ERROR;
        }
    }

    public static Optional<JsonNode> parseRaw(String data) {
        try {
            return Optional.of(json.readTree(data));
        } catch (JsonProcessingException e) {
            return Optional.empty();
        }
    }

    public static <T> Optional<T> parseClass(String data, Class<T> c) {
        try {
            return Optional.of(json.readValue(data, c));
        } catch (JsonProcessingException e) {
            return Optional.empty();
        }
    }
}
