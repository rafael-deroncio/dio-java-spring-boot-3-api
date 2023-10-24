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
        UserModel newUser = this._userContextRepository.save(user);
        return newUser;
    }

    @Override
    public UserModel getUser(Integer id) {
        UserModel user = this._userContextRepository.findUserById(id);
        return user;
    }

    @Override
    public UserModel getUser(String username) {
        UserModel user = this._userContextRepository.findUserByUsername(username);
        return user;
    }

    @Override
    public Boolean deleteUser(UserModel user) {
        this._userContextRepository.delete(user);
        return true;
    }
}
