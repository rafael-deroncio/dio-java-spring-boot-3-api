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

import java.util.stream.Collectors;

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
    public ClientModel getClient(ClientModel client) {
        return _clientContextRepository.findById(client.getId()).orElse(null);
    }

    @Override
    public ClientModel getClient(Integer userId) {
        ClientModel client = this._clientContextRepository.getClientByUserId(userId);
        client.setAddresses(this._clientAddressContextRepository.getClientAddress(client.getId()));
        return client;
    }


    @Override
    public ClientModel getClient(String cpf) {
        return null;
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
