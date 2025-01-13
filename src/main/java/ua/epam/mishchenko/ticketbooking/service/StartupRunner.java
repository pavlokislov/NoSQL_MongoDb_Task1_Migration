package ua.epam.mishchenko.ticketbooking.service;


import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ua.epam.mishchenko.ticketbooking.repository.mongo.EventMongoRepository;
import ua.epam.mishchenko.ticketbooking.repository.mongo.UserMongoRepository;

@Component
@RequiredArgsConstructor
public class StartupRunner implements CommandLineRunner {

    private final DatabaseMigrationService databaseMigrationService;

    private final UserMongoRepository userMongoService;
    private final EventMongoRepository eventMongoRepository;

    @Transactional
    @Override
    public void run(String... args) {

//        var isUserExistsByEmail = userMongoService.existsByEmail("kate@gmail.com");
//        Page<User> userByName = userMongoService.getAllByName(PageRequest.of(0, 5), "kate");
//        var userByEmail = userMongoService.getByEmail("kate@gmail.com");
//

//        var firstEvent = eventMongoRepository.getAllByTitle(PageRequest.of(0, 5), "First event");

        databaseMigrationService.migrate();

    }
}