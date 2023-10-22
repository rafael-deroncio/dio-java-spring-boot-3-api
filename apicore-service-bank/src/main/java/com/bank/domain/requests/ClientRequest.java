package com.bank.domain.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.util.Date;

public class ClientRequest {
    @NotBlank
    @Size(max = 50)
    @Schema(description = "First name of the client", required = true)
    private String firstName;

    @NotBlank
    @Size(max = 50)
    @Schema(description = "Last name of the client", required = true)
    private String lastName;

    @NotBlank
    @Pattern(regexp = "\\d{11}", message = "Invalid CPF document: '${validatedValue}'")
    @Schema(description = "CPF document of the client (11 digits)", required = true)
    private String cpfDocument;

    @NotNull
    @Past(message = "Birth date must be in the past: '${validatedValue}'")
    @Schema(description = "Date of birth of the client", required = true)
    private Date birthDate;

    @NotBlank
    @Email(message = "Invalid email address: '${validatedValue}'")
    @Size(max = 100)
    @Schema(description = "Email address of the client", required = true)
    private String email;

    @NotBlank
    @Size(max = 15)
    @Schema(description = "Cell phone number of the client", required = true)
    private String cellPhoneNumber;

    @NotBlank
    @Size(max = 10)
    @Schema(description = "Zip code of the client", required = true)
    private String zipCode;

    @NotNull
    @Schema(description = "House number of the client", required = true)
    private Integer houseNumber;

    @Schema(description = "House complement of the client")
    private Integer houseComplement;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCpfDocument() {
        return cpfDocument;
    }

    public void setCpfDocument(String cpfDocument) {
        this.cpfDocument = cpfDocument;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCellPhoneNumber() {
        return cellPhoneNumber;
    }

    public void setCellPhoneNumber(String cellPhoneNumber) {
        this.cellPhoneNumber = cellPhoneNumber;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public Integer getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(Integer houseNumber) {
        this.houseNumber = houseNumber;
    }

    public Integer getHouseComplement() {
        return houseComplement;
    }

    public void setHouseComplement(Integer houseComplement) {
        this.houseComplement = houseComplement;
    }
}

