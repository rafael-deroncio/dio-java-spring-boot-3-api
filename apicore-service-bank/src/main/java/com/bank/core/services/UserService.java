package com.bank.core.services;

import com.bank.core.enums.ErrorResponseType;
import com.bank.core.exceptions.UserBusinessRuleException;
import com.bank.core.models.ClientAddressModel;
import com.bank.core.models.ClientModel;
import com.bank.core.models.ClientTelephoneModel;
import com.bank.core.models.UserModel;
import com.bank.core.repositories.ClientRepository;
import com.bank.core.repositories.UserRepository;
import com.bank.core.responses.CepDetailsResponse;
import com.bank.core.utils.FormatterUtil;
import com.bank.core.utils.PasswordHasherUtils;
import com.bank.domain.responses.AddressResponse;
import com.bank.domain.responses.ClientResponse;
import com.bank.domain.responses.TelephoneContactResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.bank.core.services.interfaces.IUserService;
import com.bank.domain.requests.UserRequest;
import com.bank.domain.responses.UserResponse;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService implements IUserService{

    @Autowired
    UserRepository _userRepository;

    @Autowired
    ClientRepository _clientRepository;
    @Autowired
    ModelMapper _mapper;

    @Autowired
    FormatterUtil _formatter;

    @Autowired
    PasswordHasherUtils _passwordHasher;

    @Autowired
    ViaCepService _viaCepService;


    @Override
    public UserResponse createUser(UserRequest request) {
        String cpf = _formatter.formatCpf(request.getClientDetails().getCpfDocument());
        String username = _formatter.formatUsername(request.getUsername());

        if(this._userRepository.getUser(username) != null || this._clientRepository.getClient(cpf) != null) {
            throw new UserBusinessRuleException(
                    String.format("username '%s' or cpf '%s' are already registered", username, cpf),
                    HttpStatus.NOT_ACCEPTABLE, ErrorResponseType.Error);
        }

        UserModel newUser = new UserModel();
        newUser.setUsername(username);
        newUser.setPasswordHash(_passwordHasher.encryptPassword(request.getPassword().trim()));

        ClientModel newClient = _mapper.map(request.getClientDetails(), ClientModel.class);
        newClient.setCpf(cpf);

        List<ClientTelephoneModel> telephones = getNewClientTelephones(request, newClient);
        List<ClientAddressModel> addresses = getNewClientAddress(request, newClient);

        newUser.setClient(newClient);
        newClient.setUser(newUser);
        newClient.setTelephones(telephones);
        newClient.setAddresses(addresses);

        newUser = _userRepository.saveUser(newUser);

        return getUserResponse(newUser, username, newClient, cpf);
    }

    @Override
    public UserResponse getUser(String username) {
        username = _formatter.formatUsername(username);
        UserModel userModel = _userRepository.getUser(username);

        if (userModel == null) {
            throw new UserBusinessRuleException(
                    String.format("username '%s' not  registered", username),
                    HttpStatus.BAD_REQUEST, ErrorResponseType.Error);
        }

        ClientModel clientModel = _clientRepository.getClient(userModel.getId());
        return getUserResponse(userModel, username, clientModel, clientModel.getCpf());
    }

    @Override
    public UserResponse getUser(Integer id) {
        UserModel userModel = _userRepository.getUser(id);

        if (userModel == null) {
            throw new UserBusinessRuleException(
                    String.format("username '%s' not  registered", id),
                    HttpStatus.BAD_REQUEST, ErrorResponseType.Error);
        }

        ClientModel clientModel = _clientRepository.getClient(userModel.getId());
        return getUserResponse(userModel, userModel.getUsername(), clientModel, clientModel.getCpf());
    }

    @Override
    public UserResponse updateUser(Integer id, UserRequest request) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateUser'");
    }

    @Override
    public Boolean deactivateUser(Integer id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteUser'");
    }


    private UserResponse getUserResponse(UserModel user, String username, ClientModel client, String cpf) {
        UserResponse userResponse = new UserResponse();
        userResponse.setId(user.getId());
        userResponse.setUsername(username);
        userResponse.setCreatedDate(user.getCreatedDate());

        ClientResponse clientResponse = new ClientResponse();
        clientResponse.setName(client.getFirstName().toUpperCase() + " " + client.getLastName().toUpperCase());
        clientResponse.setDocument(cpf);
        clientResponse.setEmail(client.getEmail());

        TelephoneContactResponse telephoneContactResponse = _mapper.map(client.getTelephones().get(0), TelephoneContactResponse.class);
        AddressResponse addressResponse = _mapper.map(client.getAddresses().get(0), AddressResponse.class);

        clientResponse.setTelephone(telephoneContactResponse);
        clientResponse.setAddress(addressResponse);
        userResponse.setClient(clientResponse);
        return userResponse;
    }

    private List<ClientTelephoneModel> getNewClientTelephones(UserRequest request, ClientModel newClient) {
        List<ClientTelephoneModel> telephones = new ArrayList<>();
        ClientTelephoneModel newClientTelephone = new ClientTelephoneModel();
        newClientTelephone.setPhoneNumber(_formatter.formatTelephone(request.getClientDetails().getCellPhoneNumber()));

        newClientTelephone.setClient(newClient);

        telephones.add(newClientTelephone);
        return telephones;
    }

    private List<ClientAddressModel> getNewClientAddress(UserRequest request, ClientModel newClient) {
        List<ClientAddressModel> address = new ArrayList<>();
        ClientAddressModel newClientAddress = new ClientAddressModel();
        String cep = _formatter.fortamtCep(request.getClientDetails().getZipCode());
        CepDetailsResponse cepDetailsResponse = _viaCepService.getCep(cep);
        newClientAddress.setNumber(request.getClientDetails().getHouseNumber());
        newClientAddress.setComplement(request.getClientDetails().getHouseComplement());
        newClientAddress.setZipcode(cep);
        newClientAddress.setStreet(cepDetailsResponse.getLogradouro());
        newClientAddress.setCity(cepDetailsResponse.getLocalidade());
        newClientAddress.setState(cepDetailsResponse.getUf());
        newClientAddress.setClient(newClient);
        address.add(newClientAddress);
        return address;
    }


}
