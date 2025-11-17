package fr.cyu.utils;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import fr.cyu.data.payslip.Payslip;

public class PayslipModule extends StdSerializer<Payslip> {

    public PayslipModule() {
        this(null);
    }

    public PayslipModule(Class<Payslip> t) {
        super(t);
    }

    @Override
    public void serialize(
            Payslip value, JsonGenerator jgen, SerializerProvider provider)
            throws IOException, JsonProcessingException {

        jgen.writeStartObject();
        jgen.writeNumberField("id", value.getId());
        jgen.writeNumberField("employee", value.getEmployee().getId());
        jgen.writeNumberField("wage", value.getWage());
        jgen.writeNumberField("hour", value.getHour());
        jgen.writeNumberField("bonus", value.getBonus());
        jgen.writeNumberField("malus", value.getMalus());
        jgen.writeNumberField("date", value.getDate().getTime());

        jgen.writeEndObject();
    }
}