package ua.epam.mishchenko.ticketbooking.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ua.epam.mishchenko.ticketbooking.model.Category;
import ua.epam.mishchenko.ticketbooking.model.Ticket;

@Repository
public interface TicketRepository extends CrudRepository<Ticket, Long> {


    Page<Ticket> getAllByUserId(Pageable pageable, Long userId);

    Page<Ticket> getAllByEventId(Pageable pageable, Long eventId);

    Boolean existsByEventIdAndPlaceAndCategory(Long eventId, Integer place, Category category);
}
