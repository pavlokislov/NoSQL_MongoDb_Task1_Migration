package ua.epam.mishchenko.ticketbooking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.epam.mishchenko.ticketbooking.model.Category;
import ua.epam.mishchenko.ticketbooking.model.Ticket;
import ua.epam.mishchenko.ticketbooking.model.mongo.EventMongo;
import ua.epam.mishchenko.ticketbooking.model.mongo.TicketMongo;
import ua.epam.mishchenko.ticketbooking.model.mongo.UserMongo;

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
        ticketDto.setUser(UserDto.buildFromSqlUser(ticket.getUser()));
        ticketDto.setEvent(EventDto.createFromSqlEvent(ticket.getEvent()));
        ticketDto.setPlace(ticket.getPlace());
        ticketDto.setCategory(ticket.getCategory());
        return ticketDto;
    }

    public static TicketDto fromMongoTicket(TicketMongo ticket, EventMongo event, UserMongo user) {
        TicketDto ticketDto = new TicketDto();
        ticketDto.setId(String.valueOf(ticket.getId()));
        ticketDto.setUser(UserDto.buildFromMongoUser(user));
        ticketDto.setEvent(EventDto.createFromEventMongo(event));
        ticketDto.setPlace(ticket.getPlace());
        ticketDto.setCategory(ticket.getCategory());
        return ticketDto;
    }

}