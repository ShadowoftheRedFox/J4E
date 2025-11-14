package fr.cyu.controller.dto;

import java.util.List;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class NewEmployeeDTO {
    @NotBlank
    @Size(min = 3, max = 50)
    private String username;
    @NotBlank
    @Size(min = 3, max = 50)
    private String firstName;
    @NotBlank
    @Size(min = 3, max = 50)
    private String lastName;
    @NotBlank
    @Size(min = 3, max = 50)
    private String password;
    @NotNull
    @Min(1)
    private Integer department;

    @NotNull
    @Size(min = 1)
    private List<String> ranks;
    @NotNull
    private List<String> permissions;

    public Integer getDepartment() {
        return department;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    /**
     * @return the permissions
     */
    public List<String> getPermissions() {
        return permissions;
    }

    /**
     * @return the ranks
     */
    public List<String> getRanks() {
        return ranks;
    }

    public void setDepartment(Integer departmentId) {
        this.department = departmentId;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @param permissions the permissions to set
     */
    public void setPermissions(List<String> permissions) {
        this.permissions = permissions;
    }

    /**
     * @param ranks the ranks to set
     */
    public void setRanks(List<String> ranks) {
        this.ranks = ranks;
    }
}