package com.bank.core.services.interfaces;

import com.bank.domain.requests.UserRequest;
import com.bank.domain.responses.UserResponse;

public interface IUserService {
    UserResponse createUser(UserRequest request);
    UserResponse getUser(UserRequest request);
    UserResponse getUser(Integer id);
    UserResponse updateUser(Integer id, UserRequest request);
    Boolean deactivateUser(Integer id);
}
