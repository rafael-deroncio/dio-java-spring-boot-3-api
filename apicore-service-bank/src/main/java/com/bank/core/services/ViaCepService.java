package com.bank.core.services;

import com.bank.core.responses.CepDetailsResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Feign client interface for interacting with the ViaCep API.
 */
@FeignClient(name = "viacep", url = "https://viacep.com.br/ws")
public interface ViaCepService {

    /**
     * Retrieves CEP (Código de Endereçamento Postal) details by a given CEP code.
     *
     * @param cep The CEP code for which to retrieve details.
     * @return A response containing CEP details.
     */
    @GetMapping("/{cep}/json/")
    CepDetailsResponse getCep(@PathVariable("cep") String cep);
}

