package tech.kitucode.notification.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import tech.kitucode.notification.service.dto.RawMessageDTO;

import javax.mail.MessagingException;

@Service
public class NotificationService {
    
    private final Logger logger = LoggerFactory.getLogger(NotificationService.class);
    
    private final MailService mailService;

    public NotificationService(MailService mailService) {
        this.mailService = mailService;
    }
    
    public void sendRawEmail(RawMessageDTO rawMessageDTO) throws Exception {

        // check for null values
        if(rawMessageDTO.getEmailTo()==null || rawMessageDTO.getEmailTo().isEmpty()){
            throw new Exception("Destination email cannot be null or empty");
        }

        if(rawMessageDTO.getMessage()==null || rawMessageDTO.getMessage().isEmpty()){
            throw new Exception("Message cannot be null or empty");
        }

        if(rawMessageDTO.getSubject()==null || rawMessageDTO.getSubject().isEmpty()){
            throw new Exception("Message cannot be null or empty");
        }

        try{
            mailService.sendRawMessage(rawMessageDTO.getEmailTo(), rawMessageDTO.getSubject(), rawMessageDTO.getMessage());
        }catch(MessagingException e){
            e.printStackTrace();
        }

    }
}
