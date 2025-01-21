package ua.epam.mishchenko.ticketbooking.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import ua.epam.mishchenko.ticketbooking.dto.UserAccountDTO;
import ua.epam.mishchenko.ticketbooking.model.mongo.UserAccountMongo;
import ua.epam.mishchenko.ticketbooking.model.mongo.UserMongo;
import ua.epam.mishchenko.ticketbooking.repository.mongo.UserAccountCustomMongoRepository;
import ua.epam.mishchenko.ticketbooking.repository.mongo.UserMongoRepository;
import ua.epam.mishchenko.ticketbooking.service.UserAccountService;

import java.math.BigDecimal;

@Profile(value = "mongo")
@Slf4j
@Service
@RequiredArgsConstructor
public class UserAccountMongoServiceImpl implements UserAccountService {

    private final UserMongoRepository userRepository;

    private final UserAccountCustomMongoRepository userAccountRepository;

    @Override
    public UserAccountDTO refillAccount(long userId, BigDecimal money) {
        log.info("Refilling user account for user with id: {}", userId);
        try {
            thrownRuntimeExceptionIfMoneyLessZero(money);
            throwRuntimeExceptionIfUserNotExist(userId);
            var user = getUserAndRefillIfNotExistCreate(userId, money);
            user = userRepository.save(user);
            log.info("The user account with user id {} successfully refilled", userId);
            return new UserAccountDTO(user.getUserAccount().getMoney());
        } catch (RuntimeException e) {
            log.warn("Can not to refill account with user id: {}", userId);
            return null;
        }
    }

    private void thrownRuntimeExceptionIfMoneyLessZero(BigDecimal money) {
        if (money.compareTo(BigDecimal.ZERO) < 1) {
            throw new RuntimeException("The money can not to be less zero");
        }
    }

    private UserMongo getUserAndRefillIfNotExistCreate(long userId, BigDecimal money) {
        var userAccount = userAccountRepository.findByUserId(String.valueOf(userId)).orElse(null);
        if (userAccount == null) {
            return createNewUserAccount(userId, money);
        }
        BigDecimal money1 = userAccount.getMoney();
        userAccount.setMoney(money1.add(money));
        UserMongo userMongo = userRepository.findById(String.valueOf(userId)).get();
        userMongo.setUserAccount(userAccount);
        return userMongo;
    }

    private UserMongo createNewUserAccount(long userId, BigDecimal money) {
        log.info("The user account with user id {} does not exist", userId);
        log.info("Creating new user account for user with id {}", userId);
        var userAccount = new UserAccountMongo();
        userAccount.setMoney(money);
        UserMongo userMongo = userRepository.findById(String.valueOf(userId)).get();
        userMongo.setUserAccount(userAccount);
        log.info("The user account for user with id {} successfully created", userId);
        return userMongo;
    }

    private void throwRuntimeExceptionIfUserNotExist(long userId) {
        if (!userRepository.existsById(String.valueOf(userId))) {
            throw new RuntimeException("The user with id " + userId + " does not exist");
        }
    }
}