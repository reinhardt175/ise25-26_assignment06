package de.seuhd.campuscoffee.domain.ports;

import de.seuhd.campuscoffee.domain.exceptions.NotFoundException;
import de.seuhd.campuscoffee.domain.model.User;
import org.jspecify.annotations.NonNull;
import java.util.List;

public interface UserService {
    //TODO: Define user service interface
    /**
     * Clears all user data from the data store.
     * This is typically used for testing or administrative purposes.
     * Warning: This operation is destructive and cannot be undone.
     */
    void clear();

    /**
     * Retrieves all user entities from the data store and returns them as domain objects.
     *
     * @return a list of all user entities; never null, but may be empty
     */
    @NonNull List<User> getAll();

    /**
     * Retrieves a single user entity by its unique identifier and returns it as a domain object.
     *
     * @param id the unique identifier of the user to retrieve; must not be null
     * @return the user with the specified ID; never null
     * @throws NotFoundException if no user exists with the given ID
     */
    @NonNull User getById(@NonNull Long id);

    /**
     * Retrieves a single user entity by its unique login name and returns it as a domain object.
     *
     * @param loginName the login name of the user to retrieve; must not be null
     * @return the user with the specified login name; never null
     * @throws NotFoundException if no user exists with the given login name
     */
    @NonNull User getByName(@NonNull String loginName);
    /**
     * Creates a new user in the system.
     *
     * @param user the user to create (ID should be null)
     * @return the created user with assigned ID and timestamps
     * @throws de.seuhd.campuscoffee.domain.exceptions.DuplicationException if a user with the same login name or email already exists
     */
    @NonNull User create(@NonNull User user);
    /**
     * Updates an existing user in the system.
     *
     * @param user the user to update (must have valid ID)
     * @return the updated user with new timestamp
     * @throws de.seuhd.campuscoffee.domain.exceptions.NotFoundException if no user with the specified ID exists
     * @throws de.seuhd.campuscoffee.domain.exceptions.DuplicationException if the update would create a duplicate login name or email
     */
    @NonNull User update(@NonNull User user);
    /**
     * Deletes a user by their unique identifier.
     *
     * @param id the ID of the user to delete
     * @throws de.seuhd.campuscoffee.domain.exceptions.NotFoundException if no user with the specified ID exists
     */
    void delete(@NonNull Long id);

}
