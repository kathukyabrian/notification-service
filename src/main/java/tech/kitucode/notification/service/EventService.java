package tech.kitucode.notification.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import tech.kitucode.notification.domain.Event;
import tech.kitucode.notification.domain.enumerations.ReschedulePeriod;
import tech.kitucode.notification.repository.EventRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EventService {

    private final Logger logger = LoggerFactory.getLogger(EventService.class);

    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public Event create(Event event){
        logger.debug("Request to create event : {}",event);

        // Default this to null since we can't have a last notification time at the time of creation
        event.setLastNotificationTime(null);

        // call repository to save the event
        return eventRepository.save(event);
    }

    public Event update(Event event) throws Exception {
        logger.debug("Request to update event with id : {}",event.getId());

        // check if and id was provided
        if(event.getId()==null){
            throw new Exception("An id must be provided to update");
        }

        // proceed and check whether an event exists with the given id
        Optional<Event> optionalEvent = eventRepository.findById(event.getId());

        if(!optionalEvent.isPresent()){
            throw new Exception("An event with the id was not found");
        }else{
            return eventRepository.save(event);
        }
    }

    public List<Event> getAll(){
        logger.debug("Request to get all events");

        ArrayList<Event> eventArrayList = new ArrayList<>();

        // get all events
        for(Event event: eventRepository.findAll()){
            eventArrayList.add(event);
        }

        return eventArrayList;
    }

    public Event getOne(Long id) throws Exception {
        logger.debug("Request to get event with id : {}",id);

        // check for the given event
        Optional<Event> optionalEvent = eventRepository.findById(id);

        if(!optionalEvent.isPresent()){
            throw new Exception("An event with the specified id does not exist");
        }else{
            return optionalEvent.get();
        }
    }

    public void deleteOne(Long id) throws Exception{
        logger.debug("Request to delete event with id : {}",id);

        // check for the given event
        Optional<Event> optionalEvent = eventRepository.findById(id);

        if(!optionalEvent.isPresent()){
            throw new Exception("An event with the specified id does not exist");
        }else{
            eventRepository.delete(optionalEvent.get());
        }
    }

    public Event reschedule(Event event) throws Exception {
        logger.debug("About to reschedule event");

        Optional<Event> optionalEvent = eventRepository.findById(event.getId());

        if(!optionalEvent.isPresent()){
            throw new Exception("An event with the specified id does not exist");
        }else{
            LocalDate nextEventDate = calculateRescheduleDate(event.getEventDate(),event.getReschedulePeriod());
            event.setEventDate(nextEventDate);
        }


        return event;
    }

    public LocalDate calculateRescheduleDate(LocalDate currentDate, ReschedulePeriod reschedulePeriod) throws Exception {

        LocalDate nextScheduleDate = null;

        if(reschedulePeriod == null || currentDate==null){
            throw new Exception("Values cannot be null");
        }

        if(reschedulePeriod==ReschedulePeriod.DAILY){
            nextScheduleDate = currentDate.plusDays(1);
        }else if(reschedulePeriod==ReschedulePeriod.WEEKLY){
            nextScheduleDate = currentDate.plusDays(7);
        }else if(reschedulePeriod==ReschedulePeriod.MONTHLY){
            nextScheduleDate = currentDate.plusMonths(1);
        }else if(reschedulePeriod==ReschedulePeriod.ANNUAL){
            nextScheduleDate = currentDate.plusYears(1);
        }

        return  nextScheduleDate;
    }
}
