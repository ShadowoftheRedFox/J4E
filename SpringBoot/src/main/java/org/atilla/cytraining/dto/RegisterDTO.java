package org.atilla.cytraining.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * Registration form.
 */
public class RegisterDTO {

    @NotEmpty
    @NotNull
    @Size(min = 3, max = 20, message = "Identifiants incorrects")
    @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "Identifiants incorrects")
    private String username;

    @NotNull
    @NotEmpty
    @Size(min = 8, max = 64, message = "Identifiants incorrects")
    private String password;

    @NotNull
    @NotEmpty
    @Size(min = 8, max = 64, message = "Identifiants incorrects")
    private String passwordConfirm;

    @NotNull
    private boolean creator;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    public boolean isPasswordConfirmed() {
        return password.equals(passwordConfirm);
    }

    public boolean isCreator() {
        return creator;
    }

    public void setCreator(boolean creator) {
        this.creator = creator;
    }

}
