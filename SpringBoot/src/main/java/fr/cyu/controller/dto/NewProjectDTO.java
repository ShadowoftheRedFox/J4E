package fr.cyu.controller.dto;

import java.util.List;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class NewProjectDTO extends SessionDTO {
    @NotNull
    @NotEmpty
    @Size(min = 3, max = 50)
    private String name;

    private String status;

    @NotNull
    private List<Integer> employees;

    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }

    /**
     * @return the employees
     */
    public List<Integer> getEmployees() {
        return employees;
    }
}