package fr.cyu.controller.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class NewPayslipDTO {
    @NotNull
    @NotEmpty
    @Size(min = 3, max = 50)
    private String name;

    private String status;

    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }
}