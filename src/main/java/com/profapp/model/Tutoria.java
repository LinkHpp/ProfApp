package com.profapp.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.time.LocalDate;

public class Tutoria {

    private ObjectProperty<LocalDate> dateProperty;
    private StringProperty  notas;

    public Tutoria(LocalDate date, String notas) {
        this.dateProperty = new SimpleObjectProperty<>(date);
        this.notas = new SimpleStringProperty(notas);
    }

    public LocalDate getDateProperty() {
        return dateProperty.get();
    }

    public ObjectProperty<LocalDate> datePropertyProperty() {
        return dateProperty;
    }

    public void setDateProperty(LocalDate dateProperty) {
        this.dateProperty.set(dateProperty);
    }

    public String getNotas() {
        return notas.get();
    }

    public StringProperty notasProperty() {
        return notas;
    }

    public void setNotas(String notas) {
        this.notas.set(notas);
    }
}
