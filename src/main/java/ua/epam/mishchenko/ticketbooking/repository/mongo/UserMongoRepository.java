package ua.epam.mishchenko.ticketbooking.repository.mongo;

import lombok.RequiredArgsConstructor;
import org.bson.Document;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.aggregation.SortOperation;
import org.springframework.data.mongodb.core.aggregation.UnwindOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import ua.epam.mishchenko.ticketbooking.model.mongo.User;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserMongoRepository {

    private final MongoTemplate mongoTemplate;

    public Optional<User> getByEmail(String email) {
        UnwindOperation unwind = Aggregation.unwind("tickets");
        MatchOperation match = Aggregation.match(new Criteria("tickets.user.email").is(email));
        ProjectionOperation project = Aggregation.project("tickets.user");

        Aggregation aggregation = Aggregation.newAggregation(unwind, match, project);

        AggregationResults<Document> output = mongoTemplate.aggregate(aggregation, "events", Document.class);

        List<Document> documents = output.getMappedResults();

        if (!documents.isEmpty()) {
            Document userDoc = documents.get(0).get("user", Document.class);
            User user = mongoTemplate.getConverter().read(User.class, userDoc);
            return Optional.of(user);
        } else {
            return Optional.empty();
        }
    }

    public Page<User> getAllByName(Pageable pageable, String name) {
        MatchOperation matchStage = Aggregation.match(new Criteria("tickets.user.name").regex(name, "i"));
        UnwindOperation unwindStage = Aggregation.unwind("tickets");
        ProjectionOperation projectStage = Aggregation.project("tickets.user");
        GroupOperation groupStage = Aggregation.group("user.email").first("user").as("user");
        SortOperation sortStage = Aggregation.sort(pageable.getSortOr(Sort.by(Direction.ASC, "_id")));

        Aggregation aggregation = Aggregation.newAggregation(
                matchStage,
                unwindStage,
                projectStage,
                groupStage,
                sortStage,
                Aggregation.limit(pageable.getPageSize())
        );

        AggregationResults<User> output = mongoTemplate.aggregate(aggregation, "events", User.class);

        return new PageImpl<>(output.getMappedResults(), pageable, output.getMappedResults().size());
    }

    public Boolean existsByEmail(String email) {
        UnwindOperation unwind = Aggregation.unwind("tickets");
        MatchOperation match = Aggregation.match(new Criteria("tickets.user.email").regex(email, "i"));

        Aggregation aggregation = Aggregation.newAggregation(unwind, match);

        AggregationResults<Document> output = mongoTemplate.aggregate(aggregation, "events", Document.class);
        return !output.getMappedResults().isEmpty();
    }
}
