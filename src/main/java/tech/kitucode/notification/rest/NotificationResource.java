package tech.kitucode.notification.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.kitucode.notification.service.NotificationService;
import tech.kitucode.notification.service.dto.RawMessageDTO;

@RestController
@RequestMapping("/api")
public class NotificationResource {

    private final Logger logger = LoggerFactory.getLogger(NotificationResource.class);

    private final NotificationService notificationService;

    public NotificationResource(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PostMapping("/email/raw")
    public void sendRawEmail(@RequestBody RawMessageDTO rawMessageDTO) throws Exception {
        logger.debug("About to send email to : {}",rawMessageDTO.getEmailTo());

        notificationService.sendRawEmail(rawMessageDTO);
    }
}
