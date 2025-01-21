package ua.epam.mishchenko.ticketbooking.repository.mongo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import ua.epam.mishchenko.ticketbooking.model.Category;
import ua.epam.mishchenko.ticketbooking.model.mongo.EventMongo;
import ua.epam.mishchenko.ticketbooking.model.mongo.TicketMongo;
import ua.epam.mishchenko.ticketbooking.model.mongo.UserMongo;

public interface TicketMongoRepository extends MongoRepository<TicketMongo, String> {

    Page<TicketMongo> findByEvent(EventMongo event, Pageable pageable);

    Page<TicketMongo> findByUser(UserMongo user, Pageable pageable);

    Boolean existsByEventAndPlaceAndCategory(EventMongo event, Integer place, Category category);


}
