package com.bank.api.controllers;

import com.bank.domain.requests.UserRequest;
import com.bank.domain.responses.ExceptionResponse;
import com.bank.domain.responses.UserResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/user")
@Tag(name = "User Controller", description = "Controller for user context")
public class UserController {
    @PostMapping
    @Operation(summary = "Create a new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(array = @ArraySchema(schema = @Schema(implementation = UserResponse.class)))),
            @ApiResponse(responseCode = "400", description = "client not found", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ExceptionResponse.class)))),
            @ApiResponse(responseCode = "422", description = "Unprocessable Entity", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ExceptionResponse.class)))),
            @ApiResponse(responseCode = "500", description = "Internal error", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ExceptionResponse.class)))) })
    public ResponseEntity<UserResponse> createUser(
            @RequestBody UserRequest request) {
        return ResponseEntity.ok(new UserResponse());
    }

    @GetMapping("/{username}")
    @Operation(summary = "Get a user details by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(array = @ArraySchema(schema = @Schema(implementation = UserResponse.class)))),
            @ApiResponse(responseCode = "400", description = "Client not found", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ExceptionResponse.class)))),
            @ApiResponse(responseCode = "422", description = "Unprocessable Entity", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ExceptionResponse.class)))),
            @ApiResponse(responseCode = "500", description = "Internal error", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ExceptionResponse.class)))) })
    public ResponseEntity<UserResponse> getUser(
            @Valid
            @Parameter(description = "Username registered for login", required = true)
            @PathVariable String username) {
        return ResponseEntity.ok(new UserResponse());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a user details by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(array = @ArraySchema(schema = @Schema(implementation = UserResponse.class)))),
            @ApiResponse(responseCode = "400", description = "Client not found", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ExceptionResponse.class)))),
            @ApiResponse(responseCode = "422", description = "Unprocessable Entity", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ExceptionResponse.class)))),
            @ApiResponse(responseCode = "500", description = "Internal error", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ExceptionResponse.class)))) })
    public ResponseEntity<UserResponse> getUser(
            @Valid
            @Parameter(description = "Identifier generated after registration", required = true)
            @PathVariable Integer id) {
        return ResponseEntity.ok(new UserResponse());
    }
}
