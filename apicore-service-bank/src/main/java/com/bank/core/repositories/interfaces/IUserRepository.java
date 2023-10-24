package com.bank.core.repositories.interfaces;

import com.bank.core.models.ClientModel;
import com.bank.core.models.UserModel;

/**
 * This interface defines the methods for interacting with user-related data.
 */
public interface IUserRepository {

    /**
     * Saves a user record in the repository.
     *
     * @param user The user model to be saved.
     * @return The saved user model.
     */
    UserModel saveUser(UserModel user);

    /**
     * Retrieves a user by their unique ID.
     *
     * @param id The unique ID of the user.
     * @return The user model associated with the given ID, or null if not found.
     */
    UserModel getUser(Integer id);

    /**
     * Retrieves a user by their username.
     *
     * @param username The username of the user.
     * @return The user model associated with the given username, or null if not found.
     */
    UserModel getUser(String username);

    /**
     * Deletes a user from the repository.
     *
     * @param user The user model to be deleted.
     * @return True if the user was successfully deleted, false otherwise.
     */
    Boolean deleteUser(UserModel user);
}

