package ua.epam.mishchenko.ticketbooking.model.mongo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.epam.mishchenko.ticketbooking.model.Category;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ticket {

    private User user;
    private int place;
    private Category category;
}
