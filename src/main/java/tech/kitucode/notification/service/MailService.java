package tech.kitucode.notification.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import tech.kitucode.notification.config.ApplicationProperties;
import tech.kitucode.notification.util.TemplateUtil;

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
    public void sendRawMessage(String to,String cc, String subject, String body) throws MessagingException {
        logger.debug("About to send email to : {}",to);

        sendEmail(to, cc, subject,body,false,false);
    }

    @Async
    public void sendSpecialMessage(String to, String subject, String owner, String message) throws MessagingException{
        logger.debug("About to send special message to : {}",to);

        String body = TemplateUtil.sendSpecialMessage(owner, message);

        sendEmail(to, subject, body, false,true );
    }

    @Async
    public void sendEmail(String to, String cc, String subject, String body, boolean isMultiPart, boolean isHtml) throws MessagingException{
        logger.info("Sending email....");

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, isMultiPart);

        mimeMessageHelper.setSubject(subject);

        mimeMessageHelper.setTo(to);

        // allow null values for cc
        if(cc!=null && !cc.isEmpty()){
            mimeMessageHelper.setCc(cc);
        }

        mimeMessageHelper.setText(body, isHtml);

        javaMailSender.send(mimeMessage);
    }

    @Async
    public void sendEmail(String to, String subject, String body, boolean isMultiPart, boolean isHtml) throws MessagingException{
        logger.info("Sending email....");

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, isMultiPart);

        mimeMessageHelper.setSubject(subject);

        mimeMessageHelper.setTo(to);

        mimeMessageHelper.setText(body, isHtml);

        javaMailSender.send(mimeMessage);
    }
}
