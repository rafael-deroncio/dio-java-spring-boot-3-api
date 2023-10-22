package com.bank.api.controllers;

import com.bank.core.services.UserService;
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
    private final UserService _userService;

    public UserController(UserService userService) {
        this._userService = userService;
    }

    @PostMapping
    @Operation(summary = "Create a new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(array = @ArraySchema(schema = @Schema(implementation = UserResponse.class)))),
            @ApiResponse(responseCode = "404", description = "client not found", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ExceptionResponse.class)))),
            @ApiResponse(responseCode = "422", description = "unprocessable Entity", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ExceptionResponse.class)))),
            @ApiResponse(responseCode = "500", description = "internal error", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ExceptionResponse.class)))) })
    public ResponseEntity<UserResponse> createUser(
            @RequestBody UserRequest request) {
        return ResponseEntity.ok(this._userService.createUser(request));
    }

    @GetMapping("/{username}")
    @Operation(summary = "Get a user details by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(array = @ArraySchema(schema = @Schema(implementation = UserResponse.class)))),
            @ApiResponse(responseCode = "404", description = "client not found", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ExceptionResponse.class)))),
            @ApiResponse(responseCode = "422", description = "unprocessable Entity", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ExceptionResponse.class)))),
            @ApiResponse(responseCode = "500", description = "internal error", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ExceptionResponse.class)))) })
    public ResponseEntity<UserResponse> getUser(
            @Valid
            @Parameter(description = "Username registered for login", required = true)
            @PathVariable String username) {
        return ResponseEntity.ok(this._userService.getUser(username));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a user details by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(array = @ArraySchema(schema = @Schema(implementation = UserResponse.class)))),
            @ApiResponse(responseCode = "404", description = "client not found", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ExceptionResponse.class)))),
            @ApiResponse(responseCode = "422", description = "unprocessable Entity", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ExceptionResponse.class)))),
            @ApiResponse(responseCode = "500", description = "internal error", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ExceptionResponse.class)))) })
    public ResponseEntity<UserResponse> getUser(
            @Valid
            @Parameter(description = "Identifier generated after registration", required = true)
            @PathVariable Integer id) {
        return ResponseEntity.ok(this._userService.getUser(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a user details by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "successful operation", content = @Content(array = @ArraySchema(schema = @Schema(implementation = UserResponse.class)))),
            @ApiResponse(responseCode = "404", description = "client not found", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ExceptionResponse.class)))),
            @ApiResponse(responseCode = "422", description = "unprocessable Entity", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ExceptionResponse.class)))),
            @ApiResponse(responseCode = "500", description = "internal error", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ExceptionResponse.class)))) })
    public ResponseEntity<UserResponse> updateUser(
            @Valid
            @RequestBody UserRequest request,
            @Parameter(description = "Identifier generated after registration", required = true)
            @PathVariable Integer id) {
        return ResponseEntity.ok(this._userService.updateUser(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Update a user details by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "successful operation", content = @Content(array = @ArraySchema(schema = @Schema(implementation = UserResponse.class)))),
            @ApiResponse(responseCode = "404", description = "client not found", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ExceptionResponse.class)))),
            @ApiResponse(responseCode = "422", description = "unprocessable Entity", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ExceptionResponse.class)))),
            @ApiResponse(responseCode = "500", description = "internal error", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ExceptionResponse.class)))) })
    public ResponseEntity<Boolean> deleteUser(
            @Parameter(description = "Identifier generated after registration", required = true)
            @PathVariable Integer id) {
        return ResponseEntity.ok(this._userService.deactivateUser(id));
    }
}
