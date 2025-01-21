package ua.epam.mishchenko.ticketbooking.service.impl;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ua.epam.mishchenko.ticketbooking.dto.UserDto;
import ua.epam.mishchenko.ticketbooking.model.mongo.UserMongo;
import ua.epam.mishchenko.ticketbooking.repository.mongo.UserMongoRepository;
import ua.epam.mishchenko.ticketbooking.service.UserService;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Profile(value = "mongo")
@Service
@RequiredArgsConstructor
public class UserMongoServiceImpl implements UserService {

    private final UserMongoRepository userRepository;

    @Override
    public UserDto getUserById(String userId) {
        log.info("Finding a user by id: {}", userId);
        try {
            UserMongo user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("Can not to get a user by id: " + userId));
            log.info("The user with id {} successfully found ", userId);
            return UserDto.buildFromMongoUser(user);
        } catch (RuntimeException e) {
            log.warn("Can not to get an user by id: " + userId);
            return null;
        }
    }

    @Override
    public UserDto getUserByEmail(String email) {
        log.info("Finding a user by email: {}", email);
        try {
            if (email.isEmpty()) {
                log.warn("The email can not be null");
                return null;
            }
            var user = userRepository.getByEmail(email)
                    .orElseThrow(() -> new RuntimeException("Can not to get an user by email: " + email));
            log.info("The user with email {} successfully found ", email);
            return UserDto.buildFromMongoUser(user);
        } catch (RuntimeException e) {
            log.warn("Can not to get an user by email: " + email);
            return null;
        }
    }

    @Override
    public List<UserDto> getUsersByName(String name, int pageSize, int pageNum) {
        log.info("Finding all users by name {} with page size {} and number of page {}", name, pageSize, pageNum);
        try {
            if (name.isEmpty()) {
                log.warn("The name can not be null");
                return new ArrayList<>();
            }
            var usersByName = userRepository.getAllByName(PageRequest.of(pageNum - 1, pageSize), name);
            if (!usersByName.hasContent()) {
                log.warn("Can not to find a list of users by name '{}'", name);
            }
            log.info("All users successfully found by name {} with page size {} and number of page {}",
                    name, pageSize, pageNum);
            return usersByName.getContent()
                    .stream()
                    .map(UserDto::buildFromMongoUser)
                    .toList();
        } catch (RuntimeException e) {
            log.warn("Can not to find a list of users by name '{}'", name, e);
            return new ArrayList<>();
        }
    }

    @Override
    public UserDto createUser(UserDto user) {
        log.info("Start creating an user: {}", user);
        try {
            if (isUserNull(user)) {
                log.warn("The user can not be a null");
                return null;
            }
            if (userExistsByEmail(user)) {
                log.debug("This email already exists");
            }

            var savedUser = userRepository.save(UserDto.toMongoUser(user));
            log.info("Successfully updating of the user: {}", user);
            return UserDto.buildFromMongoUser(savedUser);
        } catch (RuntimeException e) {
            log.warn("Can not to create an user: {}", user, e);
            return null;
        }
    }


    private boolean userExistsById(UserDto user) {
        return userRepository.existsById(user.getId());
    }

    private boolean userExistsByEmail(UserDto user) {
        return userRepository.existsByEmail(user.getEmail());
    }

    /**
     * Is user null boolean.
     *
     * @param user the user
     * @return the boolean
     */
    private boolean isUserNull(UserDto user) {
        return user == null;
    }

    @Override
    public UserDto updateUser(UserDto user) {
        log.info("Start updating an user: {}", user);
        try {
            if (isUserNull(user)) {
                log.warn("The user can not be a null");
                return null;
            }
            if (!userExistsById(user)) {
                throw new RuntimeException("This user does not exist");
            }
            if (userExistsByEmail(user)) {
                throw new RuntimeException("This email already exists");
            }

            var savedUser = userRepository.save(UserDto.toMongoUser(user));
            log.info("Successfully updating of the user: {}", user);
            return UserDto.buildFromMongoUser(savedUser);
        } catch (RuntimeException e) {
            log.warn("Can not to update an user: {}", user, e);
            return null;
        }
    }

    @Override
    public boolean deleteUser(String userId) {
        log.info("Start deleting an user with id: {}", userId);
        try {
            userRepository.deleteById(userId);
            log.info("Successfully deletion of the user with id: {}", userId);
            return true;
        } catch (RuntimeException e) {
            log.warn("Can not to delete an user with id: {}", userId, e);
            return false;
        }
    }
}
