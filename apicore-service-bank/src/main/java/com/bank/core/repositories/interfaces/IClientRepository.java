package com.bank.core.repositories.interfaces;

import com.bank.core.models.ClientAddressModel;
import com.bank.core.models.ClientModel;
import com.bank.core.models.ClientTelephoneModel;

public interface IClientRepository {
    ClientModel saveClient(ClientModel client);
    ClientModel getClient(ClientModel client);
    ClientModel getClient(Integer userId);
    ClientModel getClient(String cpf);
    ClientTelephoneModel saveClientTelephone(ClientTelephoneModel clientTelephone);
    ClientAddressModel saveClientAddress(ClientAddressModel clientAddress);

}
