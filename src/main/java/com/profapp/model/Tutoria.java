package com.profapp.model;

import jakarta.persistence.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "tutorias")
public class Tutoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int ID;

    @ManyToOne
    @JoinColumn(name = "alumno_id")
    private Alumno alumno;

    @Column(name = "fecha")
    private LocalDate fecha;

    @Column(name = "notas")
    private String notas;

    public Tutoria(){
    }

    // Constructor
    public Tutoria(LocalDate fecha, String notas, Alumno alumno) {
        this.fecha = fecha;
        this.notas = notas;
        this.alumno = alumno;
    }

    // Getters y setters
    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public Alumno getAlumno() {
        return alumno;
    }

    public void setAlumno(Alumno alumno) {
        this.alumno = alumno;
    }

    public String getNomCompAlumno(){
        return getAlumno().getNombre() + " " + getAlumno().getApellido();
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public StringProperty fechaNacimientoProperty() {
        // Define the date format you want
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        // Format the LocalDate to a string
        String formattedDate = fecha.format(formatter);

        // Return as StringProperty
        return new SimpleStringProperty(formattedDate);
    }

    public StringProperty alumnoProperty() {
        Alumno alumno = getAlumno();
        return new SimpleStringProperty(alumno.getNombre() + " " + alumno.getApellido());
    }

    public String getNotas() {
        return notas;
    }

    public void setNotas(String notas) {
        this.notas = notas;
    }
}