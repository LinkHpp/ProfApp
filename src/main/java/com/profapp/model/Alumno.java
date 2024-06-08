package com.profapp.model;

import jakarta.persistence.*;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

@Entity
@Table(name = "alumnos")
public class Alumno {

    @Id
    @Column(name = "NIA")
    private int NIA; // Custom primary key field named 'NIA'

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "birthday")
    private String birthday;

    @Column(name = "image_path", nullable = false)
    private String imagePath;

    // Default constructor required by Hibernate
    public Alumno() {
    }

    // Parameterized constructor
    public Alumno(String name, String lastName) {
        this.name = name;
        this.lastName = lastName;
        this.imagePath = "Link.jpg"; // Default value
    }

    public Alumno(int NIA, String name, String lastName) {
        this.NIA = NIA;
        this.name = name;
        this.lastName = lastName;
        this.imagePath = "Link.jpg"; // Default value
    }

    // Getters and setters for JavaFX properties

    public int getNIA() {
        return NIA;
    }

    public void setNIA(int NIA) {
        this.NIA = NIA;
    }
    public IntegerProperty NIAProperty() {
        return new SimpleIntegerProperty(NIA);
    }


    public String getName() {
        return name;
    }

    public StringProperty nameProperty() {
        return new SimpleStringProperty(name);
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public StringProperty lastNameProperty() {
        return new SimpleStringProperty(lastName);
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getBirthday() {
        return birthday;
    }

    public StringProperty birthdayProperty() {
        return new SimpleStringProperty(birthday);
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getImagePath() {
        return imagePath;
    }

    public StringProperty imagePathProperty() {
        return new SimpleStringProperty(imagePath);
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
