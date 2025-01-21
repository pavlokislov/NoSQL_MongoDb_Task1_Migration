package ua.epam.mishchenko.ticketbooking.model.mongo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Reference;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "events")
public class EventMongo {

    @Id
    private String id;

    private String title;

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm")
    private Date date;
    private BigDecimal ticketPrice;

    @Reference
    private List<TicketMongo> tickets = new ArrayList<>();
}