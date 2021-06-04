package tech.kitucode.notification.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import tech.kitucode.notification.domain.Event;
import tech.kitucode.notification.service.EventService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class EventResource {

    private final Logger logger = LoggerFactory.getLogger(EventResource.class);

    private final EventService eventService;

    public EventResource(EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping("/event")
    public Event createEvent(@RequestBody Event event) {
        logger.debug("REST request to save event");

        return eventService.create(event);
    }

    @PutMapping("/event")
    public Event updateEvent(@RequestBody Event event) throws Exception {
        logger.debug("REST request to update event");

        return eventService.update(event);
    }

    @GetMapping("/event")
    public List<Event> getAll(){
        logger.debug("REST request to get all events");

        return eventService.getAll();
    }

    @GetMapping("/event/{id}")
    public Event getOne(@PathVariable Long id) throws Exception {
        logger.debug("REST request to get event with id : {}", id);

        return eventService.getOne(id);
    }

    @DeleteMapping("/event/{id}")
    public void deleteOne(@PathVariable Long id) throws Exception {
        logger.debug("REST request to delete event with id : {}", id);

        eventService.deleteOne(id);
    }
}
