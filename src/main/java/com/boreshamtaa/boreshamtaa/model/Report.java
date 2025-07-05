package com.boreshamtaa.boreshamtaa.model;
import java.time.LocalDate;
import java.util.Base64;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;


@Entity
@Table(name = "reports")
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  

    private String description;

    private String status;

    private LocalDate dateSubmitted;

    private String zanId;

    private String ward;

    private String location;
    @Lob
    private byte[] eventphoto;
    
    public Report() {
    }

    public Report(Long id, String description, String status, LocalDate dateSubmitted,
                  String zanId, String ward, String location,byte[] eventphoto) {
        this.id = id;
        this.description = description;
        this.status = status;
        this.dateSubmitted = dateSubmitted;
        this.zanId = zanId;
        this.ward = ward;
        this.location = location;
        this.eventphoto=eventphoto;
    }

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getDateSubmitted() {
        return dateSubmitted;
    }

    public void setDateSubmitted(LocalDate dateSubmitted) {
        this.dateSubmitted = dateSubmitted;
    }

    public String getZanId() {
        return zanId;
    }

    public void setZanId(String zanId) {
        this.zanId = zanId;
    }

    public String getWard() {
        return ward;
    }

    public void setWard(String ward) {
        this.ward = ward;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public byte[] geteventphoto() {
        return eventphoto;
    }

    public void seteventphoto(byte[] eventphoto) {
        this.eventphoto = eventphoto;
    }

    public String getProfileBase64() {
        if (eventphoto != null) {
            return Base64.getEncoder().encodeToString(eventphoto);
        }
        return null;
    }
}
