package ua.epam.mishchenko.ticketbooking.model.mongo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Reference;
import org.springframework.data.mongodb.core.mapping.Document;
import ua.epam.mishchenko.ticketbooking.model.Category;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "tickets")
public class TicketMongo {

    @Id
    private String id;

    @Reference
    private EventMongo event;

    @Reference
    private UserMongo user;
    private int place;
    private Category category;

}
