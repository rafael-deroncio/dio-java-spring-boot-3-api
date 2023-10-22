package com.bank.api.controllers;

import com.bank.core.services.BankService;
import com.bank.domain.responses.AgencyDetailsResponse;
import com.bank.domain.responses.AgencyResponse;
import com.bank.domain.responses.BankDetailsResponse;
import com.bank.domain.responses.ExceptionResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/bank")
@Tag(name = "Bank Controller", description = "Controller for bank details context")
public class BankController {
    private final BankService _bankService;

    public BankController(BankService bankService){
        this._bankService = bankService;
    }
    @GetMapping()
    @Operation(summary = "Get a bank details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(array = @ArraySchema(schema = @Schema(implementation = BankDetailsResponse.class)))),
            @ApiResponse(responseCode = "422", description = "Unprocessable Entity", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ExceptionResponse.class)))),
            @ApiResponse(responseCode = "500", description = "Internal error", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ExceptionResponse.class)))) })
    public ResponseEntity<BankDetailsResponse> getBankDetails(){
        return ResponseEntity.ok(_bankService.getBankDetails());
    }

    @GetMapping("/agency/{number}")
    @Operation(summary = "Get a agency details by number")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(array = @ArraySchema(schema = @Schema(implementation = AgencyResponse.class)))),
            @ApiResponse(responseCode = "400", description = "agency not found", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ExceptionResponse.class)))),
            @ApiResponse(responseCode = "422", description = "Unprocessable Entity", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ExceptionResponse.class)))),
            @ApiResponse(responseCode = "500", description = "Internal error", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ExceptionResponse.class)))) })
    public  ResponseEntity<AgencyDetailsResponse> getAgency(@PathVariable Integer number){
        return ResponseEntity.ok(_bankService.getAgencyDetails(number));
    }
}
