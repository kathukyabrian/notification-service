package tech.kitucode.notification.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import tech.kitucode.notification.domain.Event;
import tech.kitucode.notification.repository.EventRepository;
import tech.kitucode.notification.service.dto.RawMessageDTO;

import javax.mail.MessagingException;
import java.time.LocalDateTime;

@Service
public class NotificationService {
    
    private final Logger logger = LoggerFactory.getLogger(NotificationService.class);
    
    private final MailService mailService;

    private final EventService eventService;

    private final EventRepository eventRepository;

    public NotificationService(MailService mailService, EventService eventService, EventRepository eventRepository) {
        this.mailService = mailService;
        this.eventService = eventService;
        this.eventRepository = eventRepository;
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

    public void sendSpecialEventEmail(Event event){
        /*
          this is a special event email eg birthday emails
          we need to schedule these events for it to happen.....
          say we want to send an email to a friend there are things we need to know...
          e need to know when the special event date or time, we need to know when to send the email. and whether to
          re-schedule the event ->
        */

        String subject = "Special Message from Brian";

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


    }



    public void validateInput(String input, String key) throws Exception {
        if(input==null || input.isEmpty()){
            throw new Exception(key+" cannot be null or empty");
        }
    }
}
