package ua.epam.mishchenko.ticketbooking.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.epam.mishchenko.ticketbooking.model.Event;
import ua.epam.mishchenko.ticketbooking.model.mongo.EventMongo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;


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

    public static EventDto fromSqlEventToEventDto(Event event) {
        EventDto eventDto = new EventDto();
        String id = Optional.ofNullable(event)
                .map(Event::getId)
                .map(String::valueOf)
                .orElse(null);

        eventDto.setId(id);
        eventDto.setTitle(event.getTitle());
        eventDto.setDate(event.getDate());
        eventDto.setTicketPrice(event.getTicketPrice());
        return eventDto;
    }

    public static EventDto fromEventMongoToEventDto(EventMongo event) {
        EventDto eventDto = new EventDto();
        eventDto.setId(event.getId());
        eventDto.setTitle(event.getTitle());
        eventDto.setDate(event.getDate());
        eventDto.setTicketPrice(event.getTicketPrice());
        return eventDto;
    }

    public static Event toEventDtoToEvent(EventDto eventDto) {
        Event event = new Event();
        var id = Optional.ofNullable(eventDto)
                .map(EventDto::getId)
                .map(Long::parseLong)
                .orElse(null);
        event.setId(id);
        event.setDate(eventDto.getDate());
        event.setTitle(eventDto.getTitle());
        event.setTicketPrice(eventDto.getTicketPrice());
        return event;

    }

    public static EventMongo fromEventDtoToEventMongo(EventDto eventDto) {
        var event = new EventMongo();
        event.setId(eventDto.getId());
        event.setDate(eventDto.getDate());
        event.setTitle(eventDto.getTitle());
        event.setTicketPrice(eventDto.getTicketPrice());
        return event;

    }
}
