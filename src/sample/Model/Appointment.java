package sample.Model;


import javax.persistence.*;

import java.sql.Date;
import java.sql.Time;

@Entity
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    @Column(nullable = false)
    private Long serviceId;

    @Column(nullable = false)
    private Long customerId;

    @Column(nullable = false)
    private Long beuticanId;
    @Column(nullable = false)
    private Time timeFrom;
@Column(nullable = false)
    private Time timeTo;
    @Column(nullable = false)
    private Date date;

    public Appointment(Long serviceId, Long customerId, Long beuticanId, Time timeFrom, Time timeTo,Date date) {
        this.serviceId = serviceId;
        this.customerId = customerId;
        this.beuticanId = beuticanId;
        this.timeTo=timeTo;
        this.timeFrom = timeFrom;
        this.date = date;
    }

    public Appointment() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getBeuticanId() {
        return beuticanId;
    }

    public void setBeuticanId(Long beuticanId) {
        this.beuticanId = beuticanId;
    }

    public Time getTimeFrom() {
        return timeFrom;
    }

    public void setTimeFrom(Time timeFrom) {
        this.timeFrom = timeFrom;
    }

    public Time getTimeTo() {
        return timeTo;
    }

    public void setTimeTo(Time timeTo) {
        this.timeTo = timeTo;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
