package com.bank.core.services;

import com.bank.core.enums.ErrorResponseType;
import com.bank.core.exceptions.AccountBusinessRuleException;
import com.bank.core.exceptions.ClientBusinessRuleException;
import com.bank.core.exceptions.UserBusinessRuleException;
import com.bank.core.models.*;
import com.bank.core.repositories.ClientRepository;
import com.bank.core.repositories.UserRepository;
import com.bank.core.responses.CepDetailsResponse;
import com.bank.core.utils.FormatterUtil;
import com.bank.core.utils.PasswordHasherUtils;
import com.bank.domain.requests.ClientRequest;
import com.bank.domain.responses.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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
    try {
        String cpf = _formatter.formatCpf(request.getClientDetails().getCpfDocument());
        String username = _formatter.formatUsername(request.getUsername());

        request.setUsername(username);
        ClientRequest clientDetails = request.getClientDetails();
        clientDetails.setCpfDocument(cpf);
        request.setClientDetails(clientDetails);

        return saveUser(request);

    }catch (DataIntegrityViolationException e) {
        throw new UserBusinessRuleException(
            String.format("username '%s' or cpf '%s' are already registered", request.getUsername(), request.getClientDetails().getCpfDocument()),
            HttpStatus.NOT_ACCEPTABLE, ErrorResponseType.Error);
        }
    }

    @Override
    public UserResponse getUser(String username) {
        UserModel userModel = _userRepository.getUser(
                _formatter.formatUsername(username));

        if (userModel == null) {
            throw new UserBusinessRuleException(
                    String.format("username '%s' not registered", username),
                    HttpStatus.BAD_REQUEST, ErrorResponseType.Error);
        }

        userModel.setClient(_clientRepository.getClient(userModel.getId()));
        return getUserResponse(userModel);
    }

    @Override
    public UserResponse getUser(Integer id) {
        UserModel userModel = _userRepository.getUser(id);

        if (userModel == null) {
            throw new UserBusinessRuleException(
                    String.format("username '%s' not registered", id),
                    HttpStatus.BAD_REQUEST, ErrorResponseType.Error);
        }

        userModel.setClient(_clientRepository.getClient(userModel.getId()));

        return getUserResponse(userModel);
    }

    @Override
    public UserResponse updateUser(Integer id, UserRequest request) {
        try {
            UserModel user = _userRepository.getUser(id);

            if (user == null) {
                throw new UserBusinessRuleException(
                        String.format("user with id '%s' not found", id),
                        HttpStatus.BAD_REQUEST, ErrorResponseType.Error);
            }

            if (!_passwordHasher.verifyPassword(request.getPassword().trim(), user.getPasswordHash())) {
                String password = _passwordHasher.encryptPassword(request.getPassword().trim());
                user.setPasswordHash(password);
            }

            if (user.getUsername() != request.getUsername()){
                String username = _formatter.formatUsername(request.getUsername());
                user.setUsername(username);
            }

            if (user.getClient().getCpf() != request.getClientDetails().getCpfDocument()) {
                String cpf = _formatter.formatCpf(request.getClientDetails().getCpfDocument());
                ClientModel client = user.getClient();
                client.setCpf(cpf);
                user.setClient(client);
            }

            String email = _formatter.formatAndValidateEmail(request.getClientDetails().getEmail());
            if (user.getClient().getEmail() != email) {
                ClientModel client = user.getClient();
                client.setEmail(email);
                user.setClient(client);
            }

            ClientModel client = user.getClient();
            client.setCpf(request.getClientDetails().getCpfDocument());

            List<ClientTelephoneModel> telephones = getNewClientTelephones(request, client);
            List<ClientAddressModel> addresses = getNewClientAddress(request, client);

            client.setUser(user);
            client.setTelephones(telephones);
            client.setAddresses(addresses);

            user.setClient(client);

            return getUserResponse(_userRepository.saveUser(user));

        }catch (DataIntegrityViolationException e) {
            throw new UserBusinessRuleException(
                    String.format("username '%s' or cpf '%s' are already registered", request.getUsername(), request.getClientDetails().getCpfDocument()),
                    HttpStatus.NOT_ACCEPTABLE, ErrorResponseType.Error);
        } catch (UserBusinessRuleException e) {
            throw e;
        }
    }

    @Override
    public Boolean deleteUser(Integer id) {
        UserModel user = _userRepository.getUser(id);
        if(user == null) {
            throw new UserBusinessRuleException(
                    String.format("user with id '%s' not found", id),
                    HttpStatus.BAD_REQUEST, ErrorResponseType.Error);
        }
        return _userRepository.deleteUser(user);
    }

    private UserResponse saveUser(UserRequest request) {
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

        return getUserResponse(newUser);
    }

    private UserResponse getUserResponse(UserModel user) {
        UserResponse userResponse = new UserResponse();
        userResponse.setId(user.getId());
        userResponse.setUsername(user.getUsername());
        userResponse.setCreatedDate(user.getCreatedDate());

        ClientResponse clientResponse = new ClientResponse();
        String fullName = user.getClient().getFirstName().toUpperCase() + " " + user.getClient().getLastName().toUpperCase();
        clientResponse.setName(fullName);
        clientResponse.setDocument(user.getClient().getCpf());
        clientResponse.setEmail(user.getClient().getEmail());

        TelephoneContactResponse telephoneContactResponse = _mapper.map(user.getClient().getTelephones().get(0), TelephoneContactResponse.class);
        AddressResponse addressResponse = _mapper.map(user.getClient().getAddresses().get(0), AddressResponse.class);

        AccountResponse accountResponse = new AccountResponse();

        try {
            accountResponse = _bankService.getAccountClient(user.getClient());
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
        newClientAddress.setNumber(request.getClientDetails().getHouseNumber());
        CepDetailsResponse cepDetailsResponse = _viaCepService.getCep(cep);

        if(cepDetailsResponse.getCep() == null) {
            throw new ClientBusinessRuleException(String.format("zipcode '%s' invalid", cep),
                    HttpStatus.NOT_ACCEPTABLE, ErrorResponseType.Error);
        }

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
