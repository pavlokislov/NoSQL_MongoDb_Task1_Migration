package ua.epam.mishchenko.ticketbooking.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import ua.epam.mishchenko.ticketbooking.model.mongo.Event;
import ua.epam.mishchenko.ticketbooking.model.mongo.Ticket;
import ua.epam.mishchenko.ticketbooking.repository.EventRepository;
import ua.epam.mishchenko.ticketbooking.service.DatabaseMigrationService;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class DataMigrationServiceImpl implements DatabaseMigrationService {

    @Value("${properties.migration_enabled}")
    private boolean migrationEnabled;
    private final EventRepository eventRepository;
    private final MongoTemplate mongoTemplate;

    @Override
    @Transactional
    public void migrate() {
        if (Boolean.FALSE.equals(migrationEnabled)) {
            return;
        }

        log.info("Start of database migration");
        Iterable<ua.epam.mishchenko.ticketbooking.model.Event> events = eventRepository.findAll();

        List<Event> mongoEvents = new ArrayList<>();

        events.forEach(event -> {
            Event mongoEvent = makeMongoEvent(event);
            mongoEvents.add(mongoEvent);
        });
        //here save in Db don't work
        mongoTemplate.insert(mongoEvents, "events");
        log.info("End of database migration");

    }

    private Event makeMongoEvent(ua.epam.mishchenko.ticketbooking.model.Event event) {
        Event mongoEvent = new Event();
        mongoEvent.setTitle(event.getTitle());
        mongoEvent.setTicketPrice(event.getTicketPrice());
        mongoEvent.setDate(event.getDate());

        var mongoTickets = event.getTickets().stream()
                .map(this::makeMongoTicket)
                .toList();

        mongoEvent.setTickets(mongoTickets);
        return mongoEvent;
    }

    private Ticket makeMongoTicket(ua.epam.mishchenko.ticketbooking.model.Ticket ticket) {
        var mongoTicket = new Ticket();
        mongoTicket.setPlace(ticket.getPlace());
        mongoTicket.setCategory(ticket.getCategory());

        var user = ticket.getUser();
        var mongoUser = new ua.epam.mishchenko.ticketbooking.model.mongo.User();
        mongoUser.setName(user.getName());
        mongoUser.setEmail(user.getEmail());

        mongoTicket.setUser(mongoUser);

        var userAccount = Optional.ofNullable(user.getUserAccount());
        if (userAccount.isPresent()) {
            var mongoUserAccount = new ua.epam.mishchenko.ticketbooking.model.mongo.UserAccount();
            mongoUserAccount.setMoney(userAccount.get().getMoney());
            mongoUser.setUserAccount(mongoUserAccount);
        }

        return mongoTicket;
    }
}
