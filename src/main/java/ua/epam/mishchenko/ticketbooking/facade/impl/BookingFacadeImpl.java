package ua.epam.mishchenko.ticketbooking.facade.impl;

import org.springframework.stereotype.Component;
import ua.epam.mishchenko.ticketbooking.dto.EventDto;
import ua.epam.mishchenko.ticketbooking.dto.TicketDto;
import ua.epam.mishchenko.ticketbooking.dto.UserAccountDTO;
import ua.epam.mishchenko.ticketbooking.dto.UserDto;
import ua.epam.mishchenko.ticketbooking.facade.BookingFacade;
import ua.epam.mishchenko.ticketbooking.model.Category;

import ua.epam.mishchenko.ticketbooking.service.EventService;
import ua.epam.mishchenko.ticketbooking.service.TicketService;
import ua.epam.mishchenko.ticketbooking.service.UserAccountService;
import ua.epam.mishchenko.ticketbooking.service.UserService;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * The type Booking facade.
 */
@Component
public class BookingFacadeImpl implements BookingFacade {

    /**
     * The Event service.
     */
    private final EventService eventService;

    /**
     * The Ticket service.
     */
    private final TicketService ticketService;

    /**
     * The User service.
     */
    private final UserService userService;

    /**
     * The User Account service.
     */
    private final UserAccountService userAccountService;

    /**
     * Instantiates a new Booking facade.
     *
     * @param eventService       the event service
     * @param userService        the user service
     * @param ticketService      the ticket service
     * @param userAccountService the user account service
     */
    public BookingFacadeImpl(EventService eventService, UserService userService, TicketService ticketService,
                             UserAccountService userAccountService) {
        this.eventService = eventService;
        this.ticketService = ticketService;
        this.userService = userService;
        this.userAccountService = userAccountService;
    }

    /**
     * Gets event by id.
     *
     * @param eventId the event id
     * @return the event by id
     */
    @Override
    public EventDto getEventById(long eventId) {
        return eventService.getEventById(eventId);
    }

    /**
     * Gets events by title.
     *
     * @param title    the title
     * @param pageSize the page size
     * @param pageNum  the page num
     * @return the events by title
     */
    @Override
    public List<EventDto> getEventsByTitle(String title, int pageSize, int pageNum) {
        return eventService.getEventsByTitle(title, pageSize, pageNum);
    }

    /**
     * Gets events for day.
     *
     * @param day      the day
     * @param pageSize the page size
     * @param pageNum  the page num
     * @return the events for day
     */
    @Override
    public List<EventDto> getEventsForDay(Date day, int pageSize, int pageNum) {
        return eventService.getEventsForDay(day, pageSize, pageNum);
    }

    /**
     * Create event.
     *
     * @param event the event
     * @return the event
     */
    @Override
    public EventDto createEvent(EventDto event) {
        return eventService.createEvent(event);
    }

    /**
     * Update event.
     *
     * @param event the event
     * @return the event
     */
    @Override
    public EventDto updateEvent(EventDto event) {
        return eventService.updateEvent(event);
    }

    /**
     * Delete event boolean.
     *
     * @param eventId the event id
     * @return the boolean
     */
    @Override
    public boolean deleteEvent(long eventId) {
        return eventService.deleteEvent(eventId);
    }

    /**
     * Gets user by id.
     *
     * @param userId the user id
     * @return the user by id
     */
    @Override
    public UserDto getUserById(long userId) {
        return userService.getUserById(userId);
    }

    /**
     * Gets user by email.
     *
     * @param email the email
     * @return the user by email
     */
    @Override
    public UserDto getUserByEmail(String email) {
        return userService.getUserByEmail(email);
    }

    /**
     * Gets users by name.
     *
     * @param name     the name
     * @param pageSize the page size
     * @param pageNum  the page num
     * @return the users by name
     */
    @Override
    public List<UserDto> getUsersByName(String name, int pageSize, int pageNum) {
        return userService.getUsersByName(name, pageSize, pageNum);
    }

    /**
     * Create user.
     *
     * @param user the user
     * @return the user
     */
    @Override
    public UserDto createUser(UserDto user) {
        return userService.createUser(user);
    }

    /**
     * Update user.
     *
     * @param user the user
     * @return the user
     */
    @Override
    public UserDto updateUser(UserDto user) {
        return userService.updateUser(user);
    }

    /**
     * Delete user boolean.
     *
     * @param userId the user id
     * @return the boolean
     */
    @Override
    public boolean deleteUser(long userId) {
        return userService.deleteUser(userId);
    }

    /**
     * Book ticket.
     *
     * @param userId   the user id
     * @param eventId  the event id
     * @param place    the place
     * @param category the category
     * @return the ticket
     */
    @Override
    public TicketDto bookTicket(long userId, long eventId, int place, Category category) {
        return ticketService.bookTicket(userId, eventId, place, category);
    }

    /**
     * Gets booked tickets.
     *
     * @param user     the user
     * @param pageSize the page size
     * @param pageNum  the page num
     * @return the booked tickets
     */
    @Override
    public List<TicketDto> getBookedTickets(UserDto user, int pageSize, int pageNum) {
        return ticketService.getBookedTickets(user, pageSize, pageNum);
    }

    /**
     * Gets booked tickets.
     *
     * @param event    the event
     * @param pageSize the page size
     * @param pageNum  the page num
     * @return the booked tickets
     */
    @Override
    public List<TicketDto> getBookedTickets(EventDto event, int pageSize, int pageNum) {
        return ticketService.getBookedTickets(event, pageSize, pageNum);
    }

    /**
     * Cancel ticket boolean.
     *
     * @param ticketId the ticket id
     * @return the boolean
     */
    @Override
    public boolean cancelTicket(long ticketId) {
        return ticketService.cancelTicket(ticketId);
    }

    public UserAccountDTO refillUserAccount(long userId, BigDecimal money) {
        return userAccountService.refillAccount(userId, money);
    }
}
