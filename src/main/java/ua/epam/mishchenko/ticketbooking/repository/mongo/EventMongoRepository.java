package ua.epam.mishchenko.ticketbooking.repository.mongo;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;
import ua.epam.mishchenko.ticketbooking.dto.EventDto;
import ua.epam.mishchenko.ticketbooking.model.mongo.Event;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventMongoRepository {

    private final MongoTemplate mongoTemplate;

    public Page<EventDto> getAllByTitle(Pageable pageable, String title) {
        MatchOperation matchStage = Aggregation.match(new Criteria("title").regex(title, "i"));

        Aggregation aggregation = Aggregation.newAggregation(
                matchStage,
                Aggregation.sort(pageable.getSortOr(Sort.by(Direction.ASC, "_id"))),
                Aggregation.limit(pageable.getPageSize())
        );

        AggregationResults<Event> output = mongoTemplate.aggregate(aggregation, "events", Event.class);

        List<EventDto> dtoList = output.getMappedResults().stream()
                .map(event -> new EventDto(event.getId(), event.getTitle(), event.getDate()))
                .toList();

        return new PageImpl<>(dtoList, pageable, output.getMappedResults().size());
    }

    public Page<EventDto> getAllByDate(Pageable pageable, Date day) {
        MatchOperation matchStage = Aggregation.match(new Criteria("date").is(day));

        Aggregation aggregation = Aggregation.newAggregation(
                matchStage,
                Aggregation.sort(pageable.getSortOr(Sort.by(Direction.ASC, "_id"))),
                Aggregation.skip((long) pageable.getPageNumber() * pageable.getPageSize()),
                Aggregation.limit(pageable.getPageSize())
        );

        AggregationResults<Event> output = mongoTemplate.aggregate(aggregation, "events", Event.class);

        List<EventDto> dtoList = output.getMappedResults().stream()
                .map(event -> new EventDto(event.getId(), event.getTitle(), event.getDate()))
                .toList();

        return new PageImpl<>(dtoList, pageable, output.getMappedResults().size());
    }


    public Boolean existsByTitleAndDate(String title, Date date) {
        MatchOperation matchStage = Aggregation.match(
                new Criteria().andOperator(
                        Criteria.where("title").is(title),
                        Criteria.where("date").is(date)
                )
        );

        Aggregation aggregation = Aggregation.newAggregation(matchStage);

        AggregationResults<Event> output = mongoTemplate.aggregate(aggregation, "events", Event.class);

        return !output.getMappedResults().isEmpty();
    }
}
