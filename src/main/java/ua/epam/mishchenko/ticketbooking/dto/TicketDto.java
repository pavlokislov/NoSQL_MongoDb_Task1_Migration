package ua.epam.mishchenko.ticketbooking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.epam.mishchenko.ticketbooking.model.Category;
import ua.epam.mishchenko.ticketbooking.model.Ticket;
import ua.epam.mishchenko.ticketbooking.model.mongo.TicketMongo;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TicketDto {

    private String id;
    private UserDto user;
    private EventDto event;
    private int place;
    private Category category;


    public TicketDto(Long id, UserDto user, EventDto event, int place, Category category) {
        this.id = String.valueOf(id);
        this.user = user;
        this.event = event;
        this.place = place;
        this.category = category;
    }

    public static TicketDto fromSqlTicket(Ticket ticket) {
        TicketDto ticketDto = new TicketDto();
        ticketDto.setId(String.valueOf(ticket.getId()));
        ticketDto.setUser(UserDto.buildFromSqlUser(ticket.getUser())); // Assuming you have a method fromSqlUser
        ticketDto.setEvent(EventDto.createFromSqlEvent(ticket.getEvent()));
        ticketDto.setPlace(ticket.getPlace());
        ticketDto.setCategory(ticket.getCategory());
        return ticketDto;
    }

    public static TicketDto fromMongoTicket(TicketMongo ticket) {
        TicketDto ticketDto = new TicketDto();
        ticketDto.setId(String.valueOf(ticket.getId()));
        ticketDto.setUser(UserDto.buildFromMongoUser(ticket.getUser())); // Assuming you have a method fromSqlUser
        ticketDto.setEvent(EventDto.createFromEventMongo(ticket.getEvent()));
        ticketDto.setPlace(ticket.getPlace());
        ticketDto.setCategory(ticket.getCategory());
        return ticketDto;
    }

}