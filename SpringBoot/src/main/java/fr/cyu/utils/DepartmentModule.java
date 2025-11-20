package fr.cyu.utils;

import java.io.IOException;
import java.util.Iterator;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import fr.cyu.data.department.Department;
import fr.cyu.data.employee.Employee;

public class DepartmentModule extends StdSerializer<Department> {

    public DepartmentModule() {
        this(null);
    }

    public DepartmentModule(Class<Department> t) {
        super(t);
    }

    @Override
    public void serialize(
            Department value, JsonGenerator jgen, SerializerProvider provider)
            throws IOException, JsonProcessingException {

        jgen.writeStartObject();
        jgen.writeNumberField("id", value.getId());
        jgen.writeStringField("name", value.getName());

        String r = ",\"employees\":[";
        Iterator<Employee> ir = value.getEmployees().iterator();
        while (ir.hasNext()) {
            r += ir.next().getId();
            if (ir.hasNext()) {
                r += ",";
            }
        }
        jgen.writeRaw(r + "]");
        jgen.writeEndObject();
    }
}