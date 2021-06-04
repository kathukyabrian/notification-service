package tech.kitucode.notification.domain;

import tech.kitucode.notification.domain.enumerations.ReschedulePeriod;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name="tbl_events")
public class Event {

    /**
     * Event name is the name of the event in question eg Mike's Birthday
     * Owner carries who owns the event in question eg Mike
     * The event date is the date when the event shall happen
     * The message carries the message that shall be delivered to the owner at that time
     * The notification time is the time when the owner shall receive the notification
     * The reschedule is a boolean value that sets whether or not to reschedule the event/not
     * ReschedulePeriod is the period after which the event shall be rescheduled, could be ANNUAL, WEEKLY
     * LastNotificationDate checks when the event was last observed
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String eventName;

    @Column(nullable = false)
    private String owner;

    @Column(nullable = false)
    private String message;

    @Column(nullable = false)
    private LocalDate eventDate;

    @Column(nullable = false)
    private Boolean reschedule;

    @Column(nullable = false)
    private String ownerEmail;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ReschedulePeriod reschedulePeriod;

    private LocalDateTime lastNotificationTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDate getEventDate() {
        return eventDate;
    }

    public void setEventDate(LocalDate eventDate) {
        this.eventDate = eventDate;
    }

    public Boolean getReschedule() {
        return reschedule;
    }

    public void setReschedule(Boolean reschedule) {
        this.reschedule = reschedule;
    }

    public ReschedulePeriod getReschedulePeriod() {
        return reschedulePeriod;
    }

    public void setReschedulePeriod(ReschedulePeriod reschedulePeriod) {
        this.reschedulePeriod = reschedulePeriod;
    }

    public LocalDateTime getLastNotificationTime() {
        return lastNotificationTime;
    }

    public void setLastNotificationTime(LocalDateTime lastNotificationTime) {
        this.lastNotificationTime = lastNotificationTime;
    }

    public String getOwnerEmail() {
        return ownerEmail;
    }

    public void setOwnerEmail(String ownerEmail) {
        this.ownerEmail = ownerEmail;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", eventName='" + eventName + '\'' +
                ", owner='" + owner + '\'' +
                ", message='" + message + '\'' +
                ", eventDate=" + eventDate +
                ", reschedule=" + reschedule +
                ", ownerEmail='" + ownerEmail + '\'' +
                ", reschedulePeriod=" + reschedulePeriod +
                ", lastNotificationTime=" + lastNotificationTime +
                '}';
    }
}
