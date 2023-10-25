package com.bank.core.repositories.interfaces;

import com.bank.core.models.ClientAddressModel;
import com.bank.core.models.ClientModel;
import com.bank.core.models.ClientTelephoneModel;

/**
 * This interface defines the methods for interacting with client data.
 */
public interface IClientRepository {

    /**
     * Retrieves a client by user ID.
     *
     * @param userId The unique user ID of the client.
     * @return The client model associated with the given user ID, or null if not found.
     */
    ClientModel getClient(Integer userId);

    /**
     * Retrieves a client by CPF (Cadastro de Pessoas Físicas) number.
     *
     * @param cpf The CPF number of the client.
     * @return The client model associated with the given CPF, or null if not found.
     */
    ClientModel getClientById(Integer cpf);

}

