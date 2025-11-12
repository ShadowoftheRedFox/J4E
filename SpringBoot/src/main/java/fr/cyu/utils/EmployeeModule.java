package fr.cyu.utils;

import java.io.IOException;
import java.util.Iterator;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import fr.cyu.data.employee.Employee;
import fr.cyu.data.employee.Permission;
import fr.cyu.data.employee.Rank;

public class EmployeeModule extends StdSerializer<Employee> {

    public EmployeeModule() {
        this(null);
    }

    public EmployeeModule(Class<Employee> t) {
        super(t);
    }

    @Override
    public void serialize(
            Employee value, JsonGenerator jgen, SerializerProvider provider)
            throws IOException, JsonProcessingException {

        jgen.writeStartObject();
        jgen.writeNumberField("id", value.getId());
        jgen.writeStringField("username", value.getUsername());
        jgen.writeStringField("firstName", value.getFirstName());
        jgen.writeStringField("lastName", value.getLastName());

        String r = ",\"ranks\":[";
        Iterator<Rank> ir = value.getRanks().iterator();
        while (ir.hasNext()) {
            r += "\"" + ir.next().name() + "\"";
            if (ir.hasNext()) {
                r += ",";
            }
        }
        jgen.writeRaw(r + "]");

        String p = ",\"permissions\":[";
        Iterator<Permission> ip = value.getPermissions().iterator();
        while (ip.hasNext()) {
            p += "\"" + ip.next().name() + "\"";
            if (ip.hasNext()) {
                p += ",";
            }
        }
        jgen.writeRaw(p + "]");

        jgen.writeNumberField("department", value.getDepartment().getId());
        jgen.writeEndObject();
    }
}