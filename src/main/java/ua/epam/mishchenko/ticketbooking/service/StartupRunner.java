package ua.epam.mishchenko.ticketbooking.service;


import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ua.epam.mishchenko.ticketbooking.model.mongo.EventMongo;
import ua.epam.mishchenko.ticketbooking.model.mongo.TicketMongo;
import ua.epam.mishchenko.ticketbooking.repository.mongo.EventMongoRepository;
import ua.epam.mishchenko.ticketbooking.repository.mongo.TicketCustomMongoRepository;
import ua.epam.mishchenko.ticketbooking.repository.mongo.TicketMongoRepository;
import ua.epam.mishchenko.ticketbooking.repository.mongo.UserAccountCustomMongoRepository;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class StartupRunner implements CommandLineRunner {

    private final DatabaseMigrationService databaseMigrationService;

    private final EventMongoRepository eventMongoRepo;
    private final TicketCustomMongoRepository ticketCustomMongoRepo;
    private final TicketMongoRepository ticketMongoRepository;

    private final UserAccountCustomMongoRepository userAccountCustomMongoRepository;

    @Transactional
    @Override
    public void run(String... args) {
        databaseMigrationService.migrate();

        List<EventMongo> all = eventMongoRepo.findAll();
        EventMongo firstEvent = all.get(0);
        shouldExist(eventMongoRepo.getAllByTitle(PageRequest.of(0, 5), firstEvent.getTitle()));
        shouldExist(eventMongoRepo.getAllByDate(PageRequest.of(0, 5), firstEvent.getDate()));
        shouldExist(eventMongoRepo.existsByTitleAndDate(firstEvent.getTitle(), firstEvent.getDate()));

        TicketMongo firstTicket = ticketMongoRepository.findAll().get(0);
        shouldExist(ticketCustomMongoRepo.getAllByUserId(PageRequest.of(0,5), firstTicket.getUser().getId()));
        shouldExist(ticketCustomMongoRepo.getAllByEventId(PageRequest.of(0,5), firstTicket.getEvent().getId()));
        shouldExist(ticketCustomMongoRepo.existsByEventAndPlaceAndCategory(firstTicket.getEvent().getId(), firstTicket.getPlace(), firstTicket.getCategory() ));

        shouldExist(userAccountCustomMongoRepository.findByUserId(firstTicket.getUser().getId()).isPresent());

        System.out.printf("");
    }

    public void shouldExist(Page<?> page) {
        if (page.getContent().isEmpty()) {
            throw new RuntimeException("Error method");
        }
    }

    public void shouldExist(Boolean value) {
        if (Boolean.FALSE.equals(value)) {
            throw new RuntimeException("Error method");
        }
    }

    public void shouldExist(Optional<?> value) {
        if (value.isEmpty()) {
            throw new RuntimeException("Error method");
        }
    }
}