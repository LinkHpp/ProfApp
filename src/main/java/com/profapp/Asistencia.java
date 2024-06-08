package com.profapp;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class Asistencia {
    private final BooleanProperty asistio;
    private final String nombre;

    public Asistencia(String nombre, boolean asistio) {
        this.nombre = nombre;
        this.asistio = new SimpleBooleanProperty(asistio);
    }

    public boolean isAsistio() {
        return asistio.get();
    }

    public BooleanProperty asistioProperty() {
        return asistio;
    }

    public void setAsistio(boolean asistio) {
        this.asistio.set(asistio);
    }

    public String getNombre() {
        return nombre;
    }
}


