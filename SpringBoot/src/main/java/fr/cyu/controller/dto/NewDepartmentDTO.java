package fr.cyu.controller.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class NewDepartmentDTO {
    @NotNull
    @NotEmpty
    @Size(min = 3, max = 50)
    private String name;

    public String getName() {
        return name;
    }
}