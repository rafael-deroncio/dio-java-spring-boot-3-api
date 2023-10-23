package com.bank.core.repositories;

import com.bank.core.models.UserModel;
import com.bank.core.repositories.contexts.UserContextRepository;
import com.bank.core.repositories.interfaces.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository implements IUserRepository {

    @Autowired
    UserContextRepository _userContextRepository;

    @Override
    public UserModel saveUser(UserModel user) {
        return this._userContextRepository.save(user);
    }

    @Override
    public UserModel getUser(UserModel newUser) {
        return null;
    }

    @Override
    public UserModel updateUser(UserModel newUser) {
        return null;
    }

    @Override
    public UserModel deleteUser(UserModel newUser) { return null;
    }
}
