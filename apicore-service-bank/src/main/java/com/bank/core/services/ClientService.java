package com.bank.core.services;

import com.bank.core.models.ClientModel;
import com.bank.core.repositories.ClientRepository;
import com.bank.core.services.interfaces.IClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientService implements IClientService {

    @Autowired
    ClientRepository _clientRepository;

    @Override
    public ClientModel getClient(Integer userId) {
        return _clientRepository.getClient(userId);
    }
}
