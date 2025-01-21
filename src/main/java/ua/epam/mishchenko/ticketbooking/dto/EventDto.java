package ua.epam.mishchenko.ticketbooking.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.epam.mishchenko.ticketbooking.model.Event;
import ua.epam.mishchenko.ticketbooking.model.mongo.EventMongo;

import java.math.BigDecimal;
import java.util.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventDto {

    private String id;
    private String title;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private Date date;
    private BigDecimal ticketPrice;

    public EventDto(Long id, String title, Date date, BigDecimal ticketPrice) {
        this.id = String.valueOf(id);
        this.title = title;
        this.date = date;
        this.ticketPrice = ticketPrice;
    }

    public static EventDto createFromSqlEvent(Event event) {
        EventDto eventDto = new EventDto();
        eventDto.setId(String.valueOf(event.getId()));
        eventDto.setTitle(event.getTitle());
        eventDto.setDate(event.getDate());
        eventDto.setTicketPrice(event.getTicketPrice());
        return eventDto;
    }

    public static EventDto createFromEventMongo(EventMongo event) {
        EventDto eventDto = new EventDto();
        eventDto.setId(event.getId());
        eventDto.setTitle(event.getTitle());
        eventDto.setDate(event.getDate());
        eventDto.setTicketPrice(event.getTicketPrice());
        return eventDto;
    }

    public static Event buildEventFromEventDto(EventDto eventDto) {
        Event event = new Event();
        event.setId(Long.parseLong(eventDto.getId()));
        event.setDate(eventDto.getDate());
        event.setTitle(eventDto.getTitle());
        event.setTicketPrice(eventDto.getTicketPrice());
        return event;

    }

    public static EventMongo buildEventMongoFromEventDto(EventDto eventDto) {
        var event = new EventMongo();
        event.setId(eventDto.getId());
        event.setDate(eventDto.getDate());
        event.setTitle(eventDto.getTitle());
        event.setTicketPrice(eventDto.getTicketPrice());
        return event;

    }
}
