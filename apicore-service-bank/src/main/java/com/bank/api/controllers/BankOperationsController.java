package com.bank.api.controllers;

import com.bank.core.services.BankOperationsService;
import com.bank.domain.requests.MoneyTransferRequest;
import com.bank.domain.requests.NewAccountRequest;
import com.bank.domain.requests.PixTransferRequest;
import com.bank.domain.responses.*;
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
@RequestMapping("api/bank/operation")
@Tag(name = "Bank Operations Controller", description = "Controller for banking operations context")
public class BankOperationsController {
    private final BankOperationsService _operationsService;

    public BankOperationsController(BankOperationsService operationsService){
        this._operationsService = operationsService;
    }

    @PostMapping("/agency/{number}/account/open")
    @Operation(summary = "Get a agency details by number")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(array = @ArraySchema(schema = @Schema(implementation = NewAccountResponse.class)))),
            @ApiResponse(responseCode = "412", description = "precondition failed", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ExceptionResponse.class)))),
            @ApiResponse(responseCode = "422", description = "unprocessable entity", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ExceptionResponse.class)))),
            @ApiResponse(responseCode = "500", description = "internal error", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ExceptionResponse.class)))) })
    public ResponseEntity<NewAccountResponse> openAccount(
            @Valid
            @RequestBody NewAccountRequest request,
            @Parameter(description = "Agency number to open a new account", required = true)
            @PathVariable Integer number){
        return ResponseEntity.ok(this._operationsService.openAccount(number, request));
    }


    @PostMapping("/agency/{agencyNumber}/account/{accountNumber}/transfer")
    @Operation(summary = "Get a agency details by number")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(array = @ArraySchema(schema = @Schema(implementation = MoneyTransferResponse.class)))),
            @ApiResponse(responseCode = "412", description = "precondition failed", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ExceptionResponse.class)))),
            @ApiResponse(responseCode = "422", description = "unprocessable entity", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ExceptionResponse.class)))),
            @ApiResponse(responseCode = "500", description = "internal error", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ExceptionResponse.class)))) })
    public ResponseEntity<MoneyTransferResponse> moneyTransfer(
            @Valid
            @RequestBody MoneyTransferRequest request,
            @Parameter(description = "agency origem number to transfer operation", required = true)
            @PathVariable Integer agencyNumber,
            @Parameter(description = "account origem number to transfer operation", required = true)
            @PathVariable Integer accountNumber) {
        return ResponseEntity.ok(this._operationsService.moneyTransfer(agencyNumber, accountNumber, request));
    }

    @PostMapping("/agency/{agencyNumber}/account/{accountNumber}/pix/transfer")
    @Operation(summary = "Get a agency details by number")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(array = @ArraySchema(schema = @Schema(implementation = PixTransferResponse.class)))),
            @ApiResponse(responseCode = "412", description = "precondition failed", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ExceptionResponse.class)))),
            @ApiResponse(responseCode = "422", description = "unprocessable entity", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ExceptionResponse.class)))),
            @ApiResponse(responseCode = "500", description = "internal error", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ExceptionResponse.class)))) })
    public ResponseEntity<PixTransferResponse> pixTransfer(
            @Valid
            @RequestBody PixTransferRequest request,
            @Parameter(description = "agency origem number to transfer operation", required = true)
            @PathVariable Integer agencyNumber,
            @Parameter(description = "account origem number to transfer operation", required = true)
            @PathVariable Integer accountNumber) {
        return ResponseEntity.ok(this._operationsService.pixTransfer(agencyNumber, accountNumber, request));
    }

    @PostMapping("/account/{number}/investment")
    @Operation(summary = "Get a agency details by number")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(array = @ArraySchema(schema = @Schema(implementation = NewInvestmentResponse.class)))),
            @ApiResponse(responseCode = "412", description = "precondition failed", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ExceptionResponse.class)))),
            @ApiResponse(responseCode = "422", description = "unprocessable entity", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ExceptionResponse.class)))),
            @ApiResponse(responseCode = "500", description = "internal error", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ExceptionResponse.class)))) })
   public ResponseEntity<NewInvestmentResponse> invest(
            @Valid
            @RequestBody NewInvestmentRequest request,
            @Parameter(description = "account origem number to pix transfer operation", required = true)
            @PathVariable Integer number){
        return ResponseEntity.ok(this._operationsService.invest(number, request));
    }
}
