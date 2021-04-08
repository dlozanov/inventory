package com.example.inventory.model.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "logs")
public class LogEntity extends BaseEntity{

    private String message;

    public LogEntity() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
