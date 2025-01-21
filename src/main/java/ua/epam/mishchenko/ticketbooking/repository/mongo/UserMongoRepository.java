package ua.epam.mishchenko.ticketbooking.repository.mongo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import ua.epam.mishchenko.ticketbooking.model.mongo.UserMongo;

import java.util.Optional;

public interface UserMongoRepository extends MongoRepository<UserMongo, String> {

    Optional<UserMongo> getByEmail(String email);

    Page<UserMongo> getAllByName(Pageable pageable, String name);

    Boolean existsByEmail(String email);

}
