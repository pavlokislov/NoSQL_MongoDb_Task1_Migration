package ua.epam.mishchenko.ticketbooking.repository.mongo;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;
import ua.epam.mishchenko.ticketbooking.model.mongo.UserAccountMongo;

import java.util.Optional;


@Repository
@RequiredArgsConstructor
public class UserAccountCustomMongoRepository {

    private final MongoTemplate mongoTemplate;

    public Optional<UserAccountMongo> findByUserId(String userId) {
        Aggregation agg = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("_id").is(userId)),
                Aggregation.project("userAccount.money")
        );

        AggregationResults<UserAccountMongo> results =
                mongoTemplate.aggregate(agg, "users", UserAccountMongo.class);

        UserAccountMongo userAccount = results.getUniqueMappedResult();

        return Optional.ofNullable(userAccount);



    }
}
