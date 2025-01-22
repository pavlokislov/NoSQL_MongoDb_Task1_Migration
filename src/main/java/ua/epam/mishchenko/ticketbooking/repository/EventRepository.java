package ua.epam.mishchenko.ticketbooking.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ua.epam.mishchenko.ticketbooking.dto.EventDto;
import ua.epam.mishchenko.ticketbooking.model.Event;

import java.util.Date;

@Repository
public interface EventRepository extends CrudRepository<Event, Long> {

    @Query("SELECT new ua.epam.mishchenko.ticketbooking.dto.EventDto(e.id, e.title, e.date, e.ticketPrice) FROM Event e WHERE e.title = :title")
    Page<EventDto> getAllByTitle(Pageable pageable, String title);

    @Query("SELECT new ua.epam.mishchenko.ticketbooking.dto.EventDto(e.id, e.title, e.date, e.ticketPrice) FROM Event e WHERE e.date = :day")
    Page<EventDto> getAllByDate(Pageable pageable, Date day);

    Boolean existsByTitleAndDate(String title, Date date);
}
