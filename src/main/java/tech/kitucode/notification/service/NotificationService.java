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

        validateInput(rawMessageDTO.getEmailTo(),"recipient address");

        validateInput(rawMessageDTO.getMessage(), "message");

        validateInput(rawMessageDTO.getSubject(), "subject");

        try{
            mailService.sendRawMessage(rawMessageDTO.getEmailTo(), rawMessageDTO.getCc(), rawMessageDTO.getSubject(), rawMessageDTO.getMessage());
        }catch(MessagingException e){
            e.printStackTrace();
        }

    }

    public void validateInput(String input, String key) throws Exception {
        if(input==null || input.isEmpty()){
            throw new Exception(key+" cannot be null or empty");
        }
    }
}
