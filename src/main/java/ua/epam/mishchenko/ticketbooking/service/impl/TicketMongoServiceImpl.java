package ua.epam.mishchenko.ticketbooking.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import ua.epam.mishchenko.ticketbooking.dto.EventDto;
import ua.epam.mishchenko.ticketbooking.dto.TicketDto;
import ua.epam.mishchenko.ticketbooking.dto.UserDto;
import ua.epam.mishchenko.ticketbooking.model.Category;
import ua.epam.mishchenko.ticketbooking.model.mongo.EventMongo;
import ua.epam.mishchenko.ticketbooking.model.mongo.TicketMongo;
import ua.epam.mishchenko.ticketbooking.model.mongo.UserAccountMongo;
import ua.epam.mishchenko.ticketbooking.model.mongo.UserMongo;
import ua.epam.mishchenko.ticketbooking.repository.mongo.EventMongoRepository;
import ua.epam.mishchenko.ticketbooking.repository.mongo.TicketCustomMongoRepository;
import ua.epam.mishchenko.ticketbooking.repository.mongo.TicketMongoRepository;
import ua.epam.mishchenko.ticketbooking.repository.mongo.UserAccountCustomMongoRepository;
import ua.epam.mishchenko.ticketbooking.repository.mongo.UserMongoRepository;
import ua.epam.mishchenko.ticketbooking.service.TicketService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Profile(value = "mongo")
@Service
@RequiredArgsConstructor
public class TicketMongoServiceImpl implements TicketService {

    private final UserMongoRepository userRepository;

    private final EventMongoRepository eventRepository;

    private final TicketMongoRepository ticketRepository;

    private final TicketCustomMongoRepository ticketCustomMongoRepository;

    private final UserAccountCustomMongoRepository userAccountCustomRepository;

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
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public TicketDto bookTicket(long userId, long eventId, int place, Category category) {
        log.info("Start booking a ticket for user with id {}, event with id event {}, place {}, category {}",
                userId, eventId, place, category);
        try {
            return processBookingTicket(userId, eventId, place, category);
        } catch (RuntimeException e) {
            log.warn("Can not to book a ticket for user with id {}, event with id {}, place {}, category {}",
                    userId, eventId, place, category, e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            log.warn("Transaction rollback");
            return null;
        }
    }

    private TicketDto processBookingTicket(long userId, long eventId, int place, Category category) {
        throwRuntimeExceptionIfUserNotExist(userId);
        throwRuntimeExceptionIfEventNotExist(eventId);
        throwRuntimeExceptionIfTicketAlreadyBooked(eventId, place, category);
        UserAccountMongo userAccount = getUserAccount(userId);
        EventDto event = getEvent(eventId);
        throwRuntimeExceptionIfUserNotHaveEnoughMoney(userAccount, event);
        buyTicket(userAccount, event);
        TicketDto ticket = saveBookedTicket(userId, eventId, place, category);
        log.info("Successfully booking of the ticket: {}", ticket);
        return ticket;
    }

    private TicketDto saveBookedTicket(long userId, long eventId, int place, Category category) {
        return TicketDto.fromMongoTicket(ticketRepository.save(createNewTicket(userId, eventId, place, category)));
    }

    private void buyTicket(UserAccountMongo userAccount, EventDto event) {
        userAccount.setMoney(subtractTicketPriceFromUserMoney(userAccount, event));
    }

    private BigDecimal subtractTicketPriceFromUserMoney(UserAccountMongo userAccount, EventDto event) {
        return userAccount.getMoney().subtract(event.getTicketPrice());
    }

    private void throwRuntimeExceptionIfUserNotHaveEnoughMoney(UserAccountMongo userAccount, EventDto event) {
        if (!userHasEnoughMoneyForTicket(userAccount, event)) {
            throw new RuntimeException(
//                    "The user with id " + userAccount..getId() +
//                            " does not have enough money for ticket with event id " + event.getId()
            );
        }
    }

    private void throwRuntimeExceptionIfTicketAlreadyBooked(long eventId, int place, Category category) {
        if (ticketCustomMongoRepository.existsByEventAndPlaceAndCategory(String.valueOf(eventId), place, category)) {
            throw new RuntimeException("This ticket already booked");
        }
    }

    private EventDto getEvent(long eventId) {
        return eventRepository.findById(String.valueOf(eventId))
                .map(EventDto::createFromEventMongo)
                .orElseThrow(() -> new RuntimeException("Can not to find an event by id: " + eventId));
    }

    private UserAccountMongo getUserAccount(long userId) {
        return userAccountCustomRepository.findByUserId(String.valueOf(userId))
                .orElseThrow(() -> new RuntimeException("Can not to find a user account by user id: " + userId));
    }

    private void throwRuntimeExceptionIfEventNotExist(long eventId) {
        if (!eventRepository.existsById(String.valueOf(eventId))) {
            throw new RuntimeException("The event with id " + eventId + " does not exist");
        }
    }

    private void throwRuntimeExceptionIfUserNotExist(long userId) {
        if (!userRepository.existsById(String.valueOf(userId))) {
            throw new RuntimeException("The user with id " + userId + " does not exist");
        }
    }

    private boolean userHasEnoughMoneyForTicket(UserAccountMongo userAccount, EventDto event) {
        return userAccount.getMoney().compareTo(event.getTicketPrice()) > -1;
    }

    /**
     * Create new ticket.
     *
     * @param userId   the user id
     * @param eventId  the event id
     * @param place    the place
     * @param category the category
     * @return the ticket
     */
    private TicketMongo createNewTicket(long userId, long eventId, int place, Category category) {
        UserMongo user = userRepository.findById(String.valueOf(userId)).get();
        EventMongo event = eventRepository.findById(String.valueOf(eventId)).get();
        return new TicketMongo(user, event, place, category);
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
        log.info("Finding all booked tickets by user {} with page size {} and number of page {}",
                user, pageSize, pageNum);
        try {
            if (isUserNull(user)) {
                log.warn("The user can not be a null");
                return new ArrayList<>();
            }
            System.out.println(ticketRepository.findAll());
            Page<TicketDto> ticketsByUser = ticketCustomMongoRepository.getAllByUserId(
                    PageRequest.of(pageNum - 1, pageSize),
                    (user.getId()));
            if (!ticketsByUser.hasContent()) {
                throw new RuntimeException("Can not to fina a list of booked tickets by user with id: " + user.getId());
            }
            log.info("All booked tickets successfully found by user {} with page size {} and number of page {}",
                    user, pageSize, pageNum);
            return ticketsByUser.getContent();
        } catch (RuntimeException e) {
            log.warn("Can not to find a list of booked tickets by user '{}'", user, e);
            return new ArrayList<>();
        }
    }

    /**
     * Is user null boolean.
     *
     * @param user the user
     * @return the boolean
     */
    private boolean isUserNull(UserDto user) {
        return user == null;
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
        log.info("Finding all booked tickets by event {} with page size {} and number of page {}",
                event, pageSize, pageNum);
        try {
            if (isEventNull(event)) {
                log.warn("The event can not be a null");
                return new ArrayList<>();
            }
            Page<TicketDto> ticketsByEvent = ticketCustomMongoRepository.getAllByEventId(
                    PageRequest.of(pageNum - 1, pageSize), event.getId());
            if (!ticketsByEvent.hasContent()) {
                throw new RuntimeException("Can not to fina a list of booked tickets by event with id: " + event.getId());
            }
            log.info("All booked tickets successfully found by event {} with page size {} and number of page {}",
                    event, pageSize, pageNum);
            return ticketsByEvent.getContent();
        } catch (RuntimeException e) {
            log.warn("Can not to find a list of booked tickets by event '{}'", event, e);
            return new ArrayList<>();
        }
    }

    /**
     * Is event null boolean.
     *
     * @param event the event
     * @return the boolean
     */
    private boolean isEventNull(EventDto event) {
        return event == null;
    }

    /**
     * Cancel ticket boolean.
     *
     * @param ticketId the ticket id
     * @return the boolean
     */
    @Override
    public boolean cancelTicket(long ticketId) {
        log.info("Start canceling a ticket with id: {}", ticketId);
        try {
            ticketRepository.deleteById(String.valueOf(ticketId));
            log.info("Successfully canceling of the ticket with id: {}", ticketId);
            return true;
        } catch (RuntimeException e) {
            log.warn("Can not to cancel a ticket with id: {}", ticketId, e);
            return false;
        }
    }
}
