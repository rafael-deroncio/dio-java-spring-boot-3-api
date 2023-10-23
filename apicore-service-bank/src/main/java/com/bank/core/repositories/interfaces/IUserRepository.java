package com.bank.core.repositories.interfaces;

import com.bank.core.models.ClientModel;
import com.bank.core.models.UserModel;

public interface IUserRepository {
    UserModel saveUser(UserModel user);
    UserModel getUser(UserModel user);
    UserModel updateUser(UserModel user);
    UserModel deleteUser(UserModel user);
}
