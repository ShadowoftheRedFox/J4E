package fr.cyu.utils;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JSONUtil {
    private static ObjectMapper json = new ObjectMapper();
    public static ResponseEntity<String> SERVER_ERROR = ResponseEntity.internalServerError()
            .body("{\"status\":500,\"error\":\"unknown error\"}");
    public static ResponseEntity<String> NOT_FOUND_ERROR = ResponseEntity.status(404)
            .body("{\"status\":404,\"error\":\"page not found\"}");
    public static ResponseEntity<String> BAD_REQUEST_ERROR = ResponseEntity.badRequest()
            .body("{\"status\":400,\"error\":\"bad request\"}");
    public static ResponseEntity<String> UNAUTHORIZED_ERROR = ResponseEntity.badRequest()
            .body("{\"status\":401,\"error\":\"unauthorized\"}");
    public static ResponseEntity<String> OK = ResponseEntity.ok("{\"status\":200,\"message\":\"ok\"}");

    private JSONUtil() {
    }

    public static String stringify(Map<String, ? extends Object> data) {
        try {
            return json.writeValueAsString(data);
        } catch (JsonProcessingException e) {
            throw new Error(e);
        }
    }

    public static String stringify(Collection<? extends Object> data) {
        try {
            return json.writeValueAsString(data);
        } catch (JsonProcessingException e) {
            System.err.println(e);
            throw new Error(e);
        }
    }

    public static String stringify(Object data) {
        try {
            return json.writeValueAsString(data);
        } catch (JsonProcessingException e) {
            System.err.println(e);
            throw new Error(e);
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
