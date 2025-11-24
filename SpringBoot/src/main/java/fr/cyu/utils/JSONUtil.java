package fr.cyu.utils;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import fr.cyu.data.department.Department;
import fr.cyu.data.employee.Employee;
import fr.cyu.data.payslip.Payslip;
import fr.cyu.data.project.Project;

public class JSONUtil {
    private static ObjectMapper json = new ObjectMapper();

    static {
        SimpleModule m = new SimpleModule();
        m.addSerializer(Employee.class, new EmployeeModule());
        m.addSerializer(Payslip.class, new PayslipModule());
        m.addSerializer(Department.class, new DepartmentModule());
        m.addSerializer(Project.class, new ProjectModule());
        json.registerModule(m);
    }

    public static ResponseEntity<String> SERVER_ERROR = ResponseEntity.internalServerError()
            .body("{\"status\":500,\"error\":\"unknown error\"}");
    public static ResponseEntity<String> NOT_YET_IMPLEMENTED = ResponseEntity.status(501)
            .body("{\"status\":501,\"error\":\"not yet implemented\"}");
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
