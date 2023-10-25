package com.bank.core.services.interfaces;

import com.bank.core.models.ClientModel;

public interface IClientService {

    /**
     * Retrieves a client by user ID.
     *
     * @param userId The unique user ID of the client.
     * @return The client model associated with the given user ID, or null if not found.
     */
    ClientModel getClient(Integer userId);

}
