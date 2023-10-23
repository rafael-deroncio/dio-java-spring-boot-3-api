package com.bank.core.repositories;

import com.bank.core.models.ClientAddressModel;
import com.bank.core.models.ClientModel;
import com.bank.core.models.ClientTelephoneModel;
import com.bank.core.repositories.contexts.ClientAddressContextRepository;
import com.bank.core.repositories.contexts.ClientContextRepository;
import com.bank.core.repositories.contexts.ClientTelephoneContextRepository;
import com.bank.core.repositories.interfaces.IClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ClientRepository implements IClientRepository {

    @Autowired
    ClientContextRepository _clientContextRepository;

    @Autowired
    ClientAddressContextRepository _clientAddressContextRepository;

    @Autowired
    ClientTelephoneContextRepository _clientTelephoneContextRepository;

    @Override
    public ClientModel saveClient(ClientModel client) {
        return this._clientContextRepository.save(client);
    }

    @Override
    public ClientTelephoneModel saveClientTelephone(ClientTelephoneModel clientTelephone) {
        return this._clientTelephoneContextRepository.save(clientTelephone);
    }

    @Override
    public ClientAddressModel saveClientAddress(ClientAddressModel clientAddress) {
        return this._clientAddressContextRepository.save(clientAddress);
    }
}
