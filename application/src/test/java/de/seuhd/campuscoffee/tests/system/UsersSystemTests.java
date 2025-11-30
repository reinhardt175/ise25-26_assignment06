package de.seuhd.campuscoffee.tests.system;

import de.seuhd.campuscoffee.api.dtos.UserDto;
import de.seuhd.campuscoffee.api.mapper.UserDtoMapper;
import de.seuhd.campuscoffee.domain.model.User;
import de.seuhd.campuscoffee.domain.tests.TestFixtures;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import java.util.List;
import static de.seuhd.campuscoffee.tests.SystemTestUtils.Requests.userRequests;
import static org.assertj.core.api.Assertions.assertThat;

public class UsersSystemTests extends AbstractSysTest {

    //TODO: Uncomment once user endpoint is implemented
    @Test
    void createUser() {
        User userToCreate = TestFixtures.getUserListForInsertion().getFirst();
        User createdUser = userDtoMapper.toUser(userRequests.create(List.of(userDtoMapper.toUserDto(userToCreate))).getFirst());
        assertEqualsIgnoringIdAndTimestamps(createdUser, userToCreate);
    }

    @Test
    void getAllCreatedUsers() {
        List<User> createdUserList = TestFixtures.createUsers(userService);

        // Retrieve all users via API

        List<User> retrievedUsers = userRequests.retrieveAll()

                .stream()
                .map(userDtoMapper::toUser)
                .toList();


        // Assert that all created users are retrieved

        assertEqualsIgnoringTimestamps(retrievedUsers, createdUserList);
    }

    @Test
    void getUserById() {
        List<User> createdUserList = TestFixtures.createUsers(userService);
        User createdUser = createdUserList.getFirst();

        // Retrieve the first user by ID via API

        User retrievedUser = userDtoMapper.toUser(
                userRequests.retrieveById(createdUser.id())
        );

        // Assert that the retrieved user matches the created one
        assertEqualsIgnoringTimestamps(retrievedUser, createdUser);
    }

    @Test
    void getUserName() {

        // Create test users using the service
        List<User> createdUserList = TestFixtures.createUsers(userService);
        User createdUser = createdUserList.get(1); // Use second user
        // Retrieve user by login name via API filter endpoint
        User retrievedUser = userDtoMapper.toUser(
                userRequests.retrieveByFilter("name", createdUser.loginName())
        );
        // Assert that the retrieved user matches the created one
        assertEqualsIgnoringTimestamps(retrievedUser, createdUser);
    }

    @Test
    void updateUser() {
        // Create a test user
        User originalUser = TestFixtures.createUsers(userService).getFirst();
        // Modify the user data
        User userToUpdate = originalUser.toBuilder()
                .firstName("UpdatedFirst")
                .lastName("UpdatedLast")
                .emailAddress("updated.email@example.com")
                .build();

        //TODO: Add at least two additional tests for user operations


        // Update via API
        User updatedUser = userDtoMapper.toUser(
                userRequests.update(List.of(userDtoMapper.toUserDto(userToUpdate))).getFirst()
        );

        // Assert that the update was successful
        assertThat(updatedUser.firstName()).isEqualTo("UpdatedFirst");
        assertThat(updatedUser.lastName()).isEqualTo("UpdatedLast");
        assertThat(updatedUser.emailAddress()).isEqualTo("updated.email@example.com");
        assertThat(updatedUser.loginName()).isEqualTo(originalUser.loginName()); // Should not change
        assertThat(updatedUser.id()).isEqualTo(originalUser.id()); // Should not change
    }

}