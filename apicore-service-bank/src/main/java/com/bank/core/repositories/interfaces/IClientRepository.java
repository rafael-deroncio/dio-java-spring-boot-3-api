package com.bank.core.repositories.interfaces;

import com.bank.core.models.ClientAddressModel;
import com.bank.core.models.ClientModel;
import com.bank.core.models.ClientTelephoneModel;

/**
 * This interface defines the methods for interacting with client data.
 */
public interface IClientRepository {

    /**
     * Saves a client record in the repository.
     *
     * @param client The client model to be saved.
     * @return The saved client model.
     */
    ClientModel saveClient(ClientModel client);

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

    /**
     * Saves a client's telephone information in the repository.
     *
     * @param clientTelephone The client telephone model to be saved.
     * @return The saved client telephone model.
     */
    ClientTelephoneModel saveClientTelephone(ClientTelephoneModel clientTelephone);

    /**
     * Saves a client's address information in the repository.
     *
     * @param clientAddress The client address model to be saved.
     * @return The saved client address model.
     */
    ClientAddressModel saveClientAddress(ClientAddressModel clientAddress);
}

