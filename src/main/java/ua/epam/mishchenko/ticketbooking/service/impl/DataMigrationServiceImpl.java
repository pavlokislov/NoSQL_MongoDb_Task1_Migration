package ua.epam.mishchenko.ticketbooking.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import ua.epam.mishchenko.ticketbooking.model.Event;
import ua.epam.mishchenko.ticketbooking.dto.EventDto;
import ua.epam.mishchenko.ticketbooking.dto.TicketDto;
import ua.epam.mishchenko.ticketbooking.model.Ticket;
import ua.epam.mishchenko.ticketbooking.model.mongo.EventMongo;
import ua.epam.mishchenko.ticketbooking.model.mongo.TicketMongo;
import ua.epam.mishchenko.ticketbooking.model.mongo.UserAccountMongo;
import ua.epam.mishchenko.ticketbooking.model.mongo.UserMongo;
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

        List<EventMongo> mongoEvents = new ArrayList<>();

        events.forEach(event -> {
            EventMongo mongoEvent = makeMongoEvent(event);
            mongoEvents.add(mongoEvent);
            mongoTemplate.save(mongoEvent, "events");
        });
        //here save in Db don't work
        log.info("End of database migration");

    }

    private EventMongo makeMongoEvent(Event sqlEvent) {
        EventMongo mongoEvent = new EventMongo();
        mongoEvent.setTitle(sqlEvent.getTitle());
        mongoEvent.setTicketPrice(sqlEvent.getTicketPrice());
        mongoEvent.setDate(sqlEvent.getDate());
        mongoEvent.setTicketPrice(sqlEvent.getTicketPrice());
        mongoTemplate.insert(mongoEvent, "events");

        var mongoTickets = sqlEvent.getTickets().stream()
                .map(sqlTicket -> makeMongoTicket(sqlTicket, mongoEvent))
                .toList();

        mongoTemplate.insert(mongoTickets, "tickets");
        mongoEvent.setTickets(mongoTickets);
        return mongoEvent;
    }

    private TicketMongo makeMongoTicket(Ticket sqlTicket, EventMongo event) {
        var mongoTicket = new TicketMongo();
        mongoTicket.setEvent(event);
        mongoTicket.setPlace(sqlTicket.getPlace());
        mongoTicket.setCategory(sqlTicket.getCategory());

        var user = sqlTicket.getUser();
        var mongoUser = new UserMongo();
        mongoUser.setName(user.getName());
        mongoUser.setEmail(user.getEmail());

        mongoTicket.setUser(mongoUser);

        var userAccount = Optional.ofNullable(user.getUserAccount());
        if (userAccount.isPresent()) {
            var mongoUserAccount = new UserAccountMongo();
            mongoUserAccount.setMoney(userAccount.get().getMoney());
            mongoUser.setUserAccount(mongoUserAccount);
        }
        mongoTemplate.insert(mongoUser, "users");
        return mongoTicket;
    }
}
