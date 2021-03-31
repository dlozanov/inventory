package com.example.inventory.model.service;

public class VatServiceModel {

    private char letter;
    private double percent;

    public VatServiceModel() {
    }

    public char getLetter() {
        return letter;
    }

    public void setLetter(char letter) {
        this.letter = letter;
    }

    public double getPercent() {
        return percent;
    }

    public void setPercent(double percent) {
        this.percent = percent;
    }
}
