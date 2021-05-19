package tech.kitucode.notification.service.dto;

public class RawMessageDTO {

    private String emailTo;

    private String subject;

    private String message;

    private String cc;

    public String getEmailTo() {
        return emailTo;
    }

    public void setEmailTo(String emailTo) {
        this.emailTo = emailTo;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCc() {
        return cc;
    }

    public void setCc(String cc) {
        this.cc = cc;
    }

    @Override
    public String toString() {
        return "RawMessageDTO{" +
                "emailTo='" + emailTo + '\'' +
                ", subject='" + subject + '\'' +
                ", message='" + message + '\'' +
                ", cc='" + cc + '\'' +
                '}';
    }
}
