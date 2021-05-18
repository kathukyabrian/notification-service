package tech.kitucode.notification.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import tech.kitucode.notification.config.ApplicationProperties;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class MailService {

    private final Logger logger = LoggerFactory.getLogger(MailService.class);

    private final JavaMailSender javaMailSender;

    private final ApplicationProperties applicationProperties;

    public MailService(JavaMailSender javaMailSender, ApplicationProperties applicationProperties) {
        this.javaMailSender = javaMailSender;
        this.applicationProperties = applicationProperties;
    }


    @Async
    public void sendRawMessage(String to, String subject, String body) throws MessagingException {
        logger.debug("About to send email to : {}",to);

        sendEmail(to, subject,body,false,false);
    }

    @Async
    public void sendEmail(String to, String subject, String body, boolean isMultiPart, boolean isHtml) throws MessagingException{
        logger.info("Request to send email to : {}",to);

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, isMultiPart);

        mimeMessageHelper.setSubject(subject);

        mimeMessageHelper.setTo(to);

        mimeMessageHelper.setText(body, isHtml);

        javaMailSender.send(mimeMessage);
    }
}
