package com.bank.domain.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class UserRequest {
    @Schema(description = "Username to login", example = "john.doe", required = true)
    @Size(min = 5, max = 50, message = "Invalid username: '${validatedValue}'")
    @NotNull(message = "Username cannot be null")
    private String username;

    @Schema(description = "Password to login", example = "password123", required = true)
    @Size(min = 8, max = 20, message = "Invalid password: '${validatedValue}'")
    @NotNull(message = "Password cannot be null")
    private String password;

    @Schema(description = "Client details", required = true)
    private ClientRequest clientDetails;

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

    public ClientRequest getClientDetails() {
        return clientDetails;
    }

    public void setClientDetails(ClientRequest clientDetails) {
        this.clientDetails = clientDetails;
    }
}
