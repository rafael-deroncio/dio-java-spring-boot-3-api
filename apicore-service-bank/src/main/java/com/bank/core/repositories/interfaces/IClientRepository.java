package com.bank.core.repositories.interfaces;

import com.bank.core.models.ClientAddressModel;
import com.bank.core.models.ClientModel;
import com.bank.core.models.ClientTelephoneModel;

public interface IClientRepository {
    ClientModel saveClient(ClientModel client);
    ClientTelephoneModel saveClientTelephone(ClientTelephoneModel clientTelephone);
    ClientAddressModel saveClientAddress(ClientAddressModel clientAddress);

}
