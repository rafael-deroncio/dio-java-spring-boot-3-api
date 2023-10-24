package com.bank.core.services.interfaces;

import com.bank.domain.requests.UserRequest;
import com.bank.domain.responses.UserResponse;

/**
 * This interface defines the service methods for user-related operations.
 */
public interface IUserService {

    /**
     * Creates a new user based on the provided user request.
     *
     * @param request The user request containing information for creating a new user.
     * @return A response with details of the newly created user.
     */
    UserResponse createUser(UserRequest request);

    /**
     * Retrieves a user by their username.
     *
     * @param username The username of the user to retrieve.
     * @return A response with user details for the specified username.
     */
    UserResponse getUser(String username);

    /**
     * Retrieves a user by their unique ID.
     *
     * @param id The unique ID of the user to retrieve.
     * @return A response with user details for the specified ID.
     */
    UserResponse getUser(Integer id);

    /**
     * Updates user information based on the provided user request.
     *
     * @param id      The unique ID of the user to update.
     * @param request The user request containing information for updating the user.
     * @return A response with updated user details.
     */
    UserResponse updateUser(Integer id, UserRequest request);

    /**
     * Deletes a user by their unique ID.
     *
     * @param id The unique ID of the user to delete.
     * @return True if the user was successfully deleted, false otherwise.
     */
    Boolean deleteUser(Integer id);
}