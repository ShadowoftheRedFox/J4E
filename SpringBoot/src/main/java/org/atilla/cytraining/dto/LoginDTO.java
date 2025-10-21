package org.atilla.cytraining.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * Login form.
 */
public class LoginDTO {

    @NotEmpty
    @NotNull
    @Size(min = 3, max = 20, message = "Identifiants incorrects")
    @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "Identifiants incorrects")
    private String username;

    @NotEmpty
    @NotNull
    private String password;

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
}
