package com.bank.core.services;

import com.bank.core.enums.ErrorResponseType;
import com.bank.core.exceptions.AccountBusinessRuleException;
import com.bank.core.exceptions.UserBusinessRuleException;
import com.bank.core.models.*;
import com.bank.core.repositories.ClientRepository;
import com.bank.core.repositories.UserRepository;
import com.bank.core.responses.CepDetailsResponse;
import com.bank.core.utils.FormatterUtil;
import com.bank.core.utils.PasswordHasherUtils;
import com.bank.domain.requests.ClientRequest;
import com.bank.domain.responses.*;
import org.apache.catalina.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.bank.core.services.interfaces.IUserService;
import com.bank.domain.requests.UserRequest;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService implements IUserService{

    @Autowired
    UserRepository _userRepository;

    @Autowired
    ClientRepository _clientRepository;

    @Autowired
    BankService _bankService;


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

        request.setUsername(username);
        ClientRequest clientDetails = request.getClientDetails();
        clientDetails.setCpfDocument(cpf);
        request.setClientDetails(clientDetails);

        return saveUser(request);
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
        return getUserResponse(userModel, username, clientModel);
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
        return getUserResponse(userModel, userModel.getUsername(), clientModel);
    }

    @Override
    public UserResponse updateUser(Integer id, UserRequest request) {
        String cpf = _formatter.formatCpf(request.getClientDetails().getCpfDocument());
        String username = _formatter.formatUsername(request.getUsername());

        ClientModel clientByUsername = this._clientRepository.getClient(cpf);
        ClientModel clientByCpf = this._clientRepository.getClient(cpf);

        if (
                (clientByUsername != null && clientByUsername.getId() != id)
                ||
                (clientByCpf != null && clientByCpf.getId() != id)
        ) {
            throw new UserBusinessRuleException(
                    String.format("username '%s' or cpf '%s' are already registered with another user", username, cpf),
                    HttpStatus.NOT_ACCEPTABLE, ErrorResponseType.Error);
        }

        request.setUsername(username);
        ClientRequest clientDetails = request.getClientDetails();
        clientDetails.setCpfDocument(cpf);
        request.setClientDetails(clientDetails);

        return saveUser(request);
    }

    @Override
    public Boolean deactivateUser(Integer id) {
        UserModel user = _userRepository.getUser(id);
        if(user == null) {
            throw new UserBusinessRuleException(
                    String.format("user with id '%s' not found", id),
                    HttpStatus.BAD_REQUEST, ErrorResponseType.Error);
        }
        return _userRepository.deleteUser(user) != null;
    }

    public UserResponse saveUser(UserRequest request) {
        UserModel newUser = new UserModel();
        newUser.setUsername(request.getUsername());
        newUser.setPasswordHash(_passwordHasher.encryptPassword(request.getPassword().trim()));

        ClientModel newClient = _mapper.map(request.getClientDetails(), ClientModel.class);
        newClient.setCpf(request.getClientDetails().getCpfDocument());

        List<ClientTelephoneModel> telephones = getNewClientTelephones(request, newClient);
        List<ClientAddressModel> addresses = getNewClientAddress(request, newClient);

        newUser.setClient(newClient);
        newClient.setUser(newUser);
        newClient.setTelephones(telephones);
        newClient.setAddresses(addresses);

        newUser = _userRepository.saveUser(newUser);

        return getUserResponse(newUser, null, newClient);
    }

    private UserResponse getUserResponse(UserModel user, String username, ClientModel client) {
        UserResponse userResponse = new UserResponse();
        userResponse.setId(user.getId());
        userResponse.setUsername(user.getUsername());
        userResponse.setCreatedDate(user.getCreatedDate());

        ClientResponse clientResponse = new ClientResponse();
        clientResponse.setName(client.getFirstName().toUpperCase() + " " + client.getLastName().toUpperCase());
        clientResponse.setDocument(client.getCpf());
        clientResponse.setEmail(client.getEmail());

        TelephoneContactResponse telephoneContactResponse = _mapper.map(client.getTelephones().get(0), TelephoneContactResponse.class);
        AddressResponse addressResponse = _mapper.map(client.getAddresses().get(0), AddressResponse.class);

        AccountResponse accountResponse = new AccountResponse();

        try {
            accountResponse = _bankService.gerAccountClient(client);
        } catch (AccountBusinessRuleException e) {
            accountResponse = null;
        }

        clientResponse.setTelephone(telephoneContactResponse);
        clientResponse.setAddress(addressResponse);
        userResponse.setAccount(accountResponse);
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
