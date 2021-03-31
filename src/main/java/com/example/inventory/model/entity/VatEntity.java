package com.example.inventory.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "vats")
public class VatEntity extends BaseEntity{

    private char letter;
    private double percent;

    public VatEntity() {
    }

    @Column(name = "letter", nullable = false, unique = true)
    public char getLetter() {
        return letter;
    }

    public void setLetter(char letter) {
        this.letter = letter;
    }

    @Column(name = "percent", nullable = false)
    public double getPercent() {
        return percent;
    }

    public void setPercent(double percent) {
        this.percent = percent;
    }
}
