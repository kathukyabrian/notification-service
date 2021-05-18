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
        if(rawMessageDTO.getEmailTo()!=null && rawMessageDTO.getMessage()!=null && rawMessageDTO.getSubject()!=null ){
            mailService.sendRawMessage(rawMessageDTO.getEmailTo(), rawMessageDTO.getSubject(), rawMessageDTO.getMessage());
        }else{
            throw new Exception("Null values are not allowed");
        }
    }
}
