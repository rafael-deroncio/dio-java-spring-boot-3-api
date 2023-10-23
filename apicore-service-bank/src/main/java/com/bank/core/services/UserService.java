package com.bank.core.services;

import com.bank.core.repositories.interfaces.IUserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.bank.core.services.interfaces.IUserService;
import com.bank.domain.requests.UserRequest;
import com.bank.domain.responses.UserResponse;

@Service
public class UserService implements IUserService{

    private final IUserRepository _userRepository;
    private final ModelMapper _mapper;


    public UserService(IUserRepository userRepository, ModelMapper mapper) {
        _mapper = mapper;
        _userRepository = userRepository;
    }

    @Override
    public UserResponse createUser(UserRequest request) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createUser'");
    }

    @Override
    public UserResponse getUser(String username) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getUser'");
    }

    @Override
    public UserResponse getUser(Integer id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getUser'");
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
    
}
