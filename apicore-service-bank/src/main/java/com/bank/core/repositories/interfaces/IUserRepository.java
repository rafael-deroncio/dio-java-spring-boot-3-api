package com.bank.core.repositories.interfaces;

import com.bank.core.models.ClientModel;
import com.bank.core.models.UserModel;

public interface IUserRepository {
    UserModel saveUser(UserModel user);
    UserModel getUser(Integer id);
    UserModel getUser(String username);
    UserModel deleteUser(UserModel user);
}
