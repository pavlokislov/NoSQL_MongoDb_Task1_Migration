package ua.epam.mishchenko.ticketbooking.service;

import ua.epam.mishchenko.ticketbooking.dto.UserDto;

import java.util.List;

/**
 * The interface User service.
 */
public interface UserService {

    /**
     * Gets user by id.
     *
     * @param userId the user id
     * @return the user by id
     */
    UserDto getUserById(String userId);

    /**
     * Gets user by email.
     *
     * @param email the email
     * @return the user by email
     */
    UserDto getUserByEmail(String email);

    /**
     * Gets users by name.
     *
     * @param name     the name
     * @param pageSize the page size
     * @param pageNum  the page num
     * @return the users by name
     */
    List<UserDto> getUsersByName(String name, int pageSize, int pageNum);

    /**
     * Create user user.
     *
     * @param user the user
     * @return the user
     */
    UserDto createUser(UserDto user);

    /**
     * Update user user.
     *
     * @param user the user
     * @return the user
     */
    UserDto updateUser(UserDto user);

    /**
     * Delete user boolean.
     *
     * @param userId the user id
     * @return the boolean
     */
    boolean deleteUser(String userId);
}
