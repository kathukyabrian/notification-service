package tech.kitucode.notification.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import tech.kitucode.notification.domain.Event;
import tech.kitucode.notification.repository.EventRepository;
import tech.kitucode.notification.service.dto.RawMessageDTO;

import javax.annotation.PostConstruct;
import javax.mail.MessagingException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
public class NotificationService {
    
    private final Logger logger = LoggerFactory.getLogger(NotificationService.class);
    
    private final MailService mailService;

    private final EventService eventService;

    private final EventRepository eventRepository;

    private final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

    public NotificationService(MailService mailService, EventService eventService, EventRepository eventRepository) {
        this.mailService = mailService;
        this.eventService = eventService;
        this.eventRepository = eventRepository;
    }

    @PostConstruct
    public void init(){
        this.executorService.scheduleWithFixedDelay(this::sendSpecialEventEmail,1,1440, TimeUnit.MINUTES);
    }
    
    public void sendRawEmail(RawMessageDTO rawMessageDTO) throws Exception {

        validateInput(rawMessageDTO.getEmailTo(),"recipient address");

        validateInput(rawMessageDTO.getMessage(), "message");

        validateInput(rawMessageDTO.getSubject(), "subject");

        try{
            mailService.sendRawMessage(rawMessageDTO.getEmailTo(), rawMessageDTO.getCc(), rawMessageDTO.getSubject(), rawMessageDTO.getMessage());
        }catch(MessagingException e){
            e.printStackTrace();
        }

        logger.debug("Email was successfully sent to : {}",rawMessageDTO.getEmailTo());

    }

    public void sendSpecialEventEmail(){
        /*
          this is a special event email eg birthday emails
          we need to schedule these events for it to happen.....
          say we want to send an email to a friend there are things we need to know...
          e need to know when the special event date or time, we need to know when to send the email. and whether to
          re-schedule the event ->
        */

        String subject = "Special Message from Brian";

        Iterable<Event> events = eventRepository.findAll();

        for(Event event:events){
            if(event.getEventDate().equals(LocalDate.now())){

                logger.debug("About to send email for event : {}",event.getEventName());

                try{
                    // send message
                    mailService.sendSpecialMessage(event.getOwnerEmail(),subject,event.getOwner(),event.getMessage());

                    // update the event -> reschedule
                    if(event.getReschedule()==Boolean.TRUE){
                        // reschedule
                        Event event1 = eventService.reschedule(event);

                        // set current time as last notification time
                        event1.setLastNotificationTime(LocalDateTime.now());

                        // save
                        eventRepository.save(event1);
                    }
                }catch (MessagingException e){
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else{
                logger.debug("No event to send email for");
            }
        }

    }



    public void validateInput(String input, String key) throws Exception {
        if(input==null || input.isEmpty()){
            throw new Exception(key+" cannot be null or empty");
        }
    }
}
