package fr.cyu.controller.dto;

import java.util.List;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class NewDepartmentDTO extends SessionDTO {
    @NotNull
    @NotEmpty
    @Size(min = 3, max = 50)
    private String name;

    @NotNull
    private List<Integer> employees;

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the employees
     */
    public List<Integer> getEmployees() {
        return employees;
    }
}