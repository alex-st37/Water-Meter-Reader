package com.andustoica;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class WaterIndex {

    private LocalDate date;
    private double cold;
    private double hot;

    public WaterIndex(LocalDate date, double cold, double hot) {
        this.date = date;
        this.cold = cold;
        this.hot = hot;
    }

    public LocalDate getDate() {
        return date;
    }

    public double getCold() {
        return cold;
    }

    public double getHot() {
        return hot;
    }

    public String formatterToString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate newDate = date;
        String text = newDate.format(formatter);
        return text;
    }

    @Override
    public String toString() {
        return "Date Of The Reading: " + formatterToString() +
                ", Cold Water Index: " + cold + " m\u00B3" +
                ", Hot Water Index: " + hot + " m\u00B3";
    }
}
