package ua.epam.mishchenko.ticketbooking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class UserAccountDTO {

    private BigDecimal money;

}
