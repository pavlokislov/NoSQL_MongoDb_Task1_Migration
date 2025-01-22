package ua.epam.mishchenko.ticketbooking.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ua.epam.mishchenko.ticketbooking.dto.EventDto;
import ua.epam.mishchenko.ticketbooking.repository.mongo.EventMongoRepository;
import ua.epam.mishchenko.ticketbooking.service.EventService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Profile(value = "mongo")
@Slf4j
@Service
@RequiredArgsConstructor
public class EventMongoServiceImpl implements EventService {

    private final EventMongoRepository eventRepository;


    /**
     * Gets event by id.
     *
     * @param eventId the event id
     * @return the event by id
     */
    @Override
    public EventDto getEventById(String eventId) {
        log.info("Finding an event by id: {}", eventId);
        try {
            var event = eventRepository.findById(eventId)
                    .orElseThrow(() -> new RuntimeException("Can not to find an event by id: " + eventId));
            log.info("Event with id {} successfully found ", eventId);
            return EventDto.fromEventMongoToEventDto(event);
        } catch (RuntimeException e) {
            log.warn("Can not to find an event by id: " + eventId);
            return null;
        }
    }

    /**
     * Gets events by title.
     *
     * @param title    the title
     * @param pageSize the page size
     * @param pageNum  the page num
     * @return the events by title
     */
    @Override
    public List<EventDto> getEventsByTitle(String title, int pageSize, int pageNum) {
        log.warn("Finding all events by title {} with page size {} and number of page {}",
                title, pageSize, pageNum);
        try {
            if (title.isEmpty()) {
                log.warn("The title can not be empty");
                return new ArrayList<>();
            }

            var eventsByTitle = eventRepository.getAllByTitle(PageRequest.of(pageNum - 1, pageSize), title)
                    .getContent()
                    .stream()
                    .map(EventDto::fromEventMongoToEventDto)
                    .toList();
            if (eventsByTitle.isEmpty()) {
                throw new RuntimeException("Can not to find a list of events by title: " + title);
            }
            log.info("All events successfully found by title {} with page size {} and number of page {}",
                    title, pageSize, pageNum);
            return eventsByTitle;
        } catch (RuntimeException e) {
            log.warn("Can not to find a list of events by title {}", title, e);
            return new ArrayList<>();
        }
    }

    /**
     * Gets events for day.
     *
     * @param day      the day
     * @param pageSize the page size
     * @param pageNum  the page num
     * @return the events for day
     */
    @Override
    public List<EventDto> getEventsForDay(Date day, int pageSize, int pageNum) {
        log.info("Finding all events for day {} with page size {} and number of page {}",
                day, pageSize, pageNum);
        try {
            if (day == null) {
                log.warn("The day can not be null");
                return new ArrayList<>();
            }
            var eventsByTitle = eventRepository.getAllByDate(PageRequest.of(pageNum - 1, pageSize), day)
                    .getContent()
                    .stream()
                    .map(EventDto::fromEventMongoToEventDto)
                    .toList();
            if (eventsByTitle.isEmpty()) {
                throw new RuntimeException("Can not to find a list of events for day: " + day);
            }
            log.info("All events successfully found for day {} with page size {} and number of page {}",
                    day, pageSize, pageNum);

            return eventsByTitle;
        } catch (RuntimeException e) {
            log.warn("Can not to find a list of events for day {}", day, e);
            return new ArrayList<>();
        }
    }

    /**
     * Create event.
     *
     * @param event the event
     * @return the event
     */
    @Override
    public EventDto createEvent(EventDto event) {
        log.info("Start creating an event: {}", event);
        try {
            if (isEventNull(event)) {
                log.warn("The event can not be null");
                return null;
            }
            if (eventExistsByTitleAndDay(event)) {
                log.warn("These title and day are already exists for one event");
                return null;
            }
            var savedEvent = eventRepository.save(EventDto.fromEventDtoToEventMongo(event));
            log.info("Successfully creation of the event: {}", event);
            return EventDto.fromEventMongoToEventDto(savedEvent);
        } catch (RuntimeException e) {
            log.warn("Can not to create an event: {}", event, e);
            return null;
        }
    }

    private boolean eventExistsByTitleAndDay(EventDto event) {
        return eventRepository.existsByTitleAndDate(event.getTitle(), event.getDate());
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
     * Update event.
     *
     * @param event the event
     * @return the event
     */
    @Override
    public EventDto updateEvent(EventDto event) {
        log.info("Start updating an event: {}", event);
        try {
            if (isEventNull(event)) {
                throw new RuntimeException("The event can not be null");
            }
            if (!eventExistsById(event)) {
                throw new RuntimeException("This event does not exist");
            }
            if (eventExistsByTitleAndDay(event)) {
                throw new RuntimeException("These title and day are already exists for one event");
            }
            var savedEvent = eventRepository.save(EventDto.fromEventDtoToEventMongo(event));
            log.info("Successfully updated of the event: {}", event);
            return EventDto.fromEventMongoToEventDto(savedEvent);
        } catch (RuntimeException e) {
            log.warn("Can not to update an event: {}", event, e);
            return null;
        }
    }

    private boolean eventExistsById(EventDto event) {
        return eventRepository.existsById(event.getId());
    }

    /**
     * Delete event boolean.
     *
     * @param eventId the event id
     * @return the boolean
     */
    @Override
    public boolean deleteEvent(String eventId) {
        log.info("Start deleting an event with id: {}", eventId);
        try {
            eventRepository.deleteById(eventId);
            log.info("Successfully deletion of the event with id: {}", eventId);
            return true;
        } catch (RuntimeException e) {
            log.warn("Can not to delete an event with id: {}", eventId, e);
            return false;
        }
    }
}
