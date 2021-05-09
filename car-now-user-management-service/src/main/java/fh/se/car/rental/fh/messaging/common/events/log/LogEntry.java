package fh.se.car.rental.fh.messaging.common.events.log;

import fh.se.car.rental.fh.messaging.common.events.common.CarNowMessage;
import fh.se.car.rental.fh.messaging.common.enums.MySeverity;

import java.io.Serializable;
import java.util.Date;

public class LogEntry extends CarNowMessage implements Serializable {
    private Date creationDate;
    private String microserviceName;
    private String message;
    private MySeverity severity;

    public LogEntry(Date creationDate, String microserviceName, String message, MySeverity severity) {
        this.creationDate = creationDate;
        this.microserviceName = microserviceName;
        this.message = message;
        this.severity = severity;
    }

    public LogEntry() {
    }


    public Date getCreationDate() {
        return creationDate;
    }

    public LogEntry setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public String getMicroserviceName() {
        return microserviceName;
    }

    public LogEntry setMicroserviceName(String microserviceName) {
        this.microserviceName = microserviceName;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public LogEntry setMessage(String message) {
        this.message = message;
        return this;
    }

    public MySeverity getSeverity() {
        return severity;
    }

    public LogEntry setSeverity(MySeverity severity) {
        this.severity = severity;
        return this;
    }
}
