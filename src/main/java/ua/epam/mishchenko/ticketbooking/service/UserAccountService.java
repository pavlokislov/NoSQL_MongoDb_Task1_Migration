package ua.epam.mishchenko.ticketbooking.service;

import ua.epam.mishchenko.ticketbooking.dto.UserAccountDTO;

import java.math.BigDecimal;

public interface UserAccountService {

    UserAccountDTO refillAccount(long userId, BigDecimal money);
}
