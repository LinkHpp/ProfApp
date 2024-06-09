package com.profapp.model;

import jakarta.persistence.*;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.List;

@Entity
@Table(name = "alumnos")
public class Alumno {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "ID")
    private int ID;

    @Column(name = "NIA")
    private int NIA;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "apellido", nullable = false)
    private String apellido;

    @Column(name = "fecha_nacimiento")
    private String fechaNacimiento;

    @Column(name = "localidad")
    private String localidad;

    @Column(name = "domicilio")
    private String domicilio;

    @Column(name = "quien_arreplega")
    private String quienArreplega;

    @Column(name = "foto", length = 125829120)
    private byte[] foto;

    // Información de contacto familiar
    @Column(name = "nombre_familiar1")
    private String nombreFamiliar1;

    @Column(name = "telefono_familiar1")
    private String telefonoFamiliar1;

    @Column(name = "correo_familiar1")
    private String correoFamiliar1;

    @Column(name = "nombre_familiar2")
    private String nombreFamiliar2;

    @Column(name = "telefono_familiar2")
    private String telefonoFamiliar2;

    @Column(name = "correo_familiar2")
    private String correoFamiliar2;

    @Column(name = "numero_hermanos")
    private int numeroHermanos;

    @Column(name = "situacion_familiar")
    private String situacionFamiliar;

    @Column(name = "observaciones")
    private String observaciones;

    @Column(name = "enfermedades_alergias")
    private String enfermedadesAlergias;

    @OneToMany(mappedBy = "alumno")
    private List<Tutoria> tutorias;

    // Default constructor required by Hibernate
    public Alumno() {
    }

    public Alumno(int NIA, String nombre, String apellido, String fechaNacimiento, String localidad, String domicilio, String quienArreplega, byte[] foto, String nombreFamiliar1, String telefonoFamiliar1, String correoFamiliar1, String nombreFamiliar2, String telefonoFamiliar2, String correoFamiliar2, int numeroHermanos,String situacionFamiliar, String observaciones, String enfermedadesAlergias) {
        this.NIA = NIA;
        this.nombre = nombre;
        this.apellido = apellido;
        this.fechaNacimiento = fechaNacimiento;
        this.localidad = localidad;
        this.domicilio = domicilio;
        this.quienArreplega = quienArreplega;
        this.foto = foto;
        this.nombreFamiliar1 = nombreFamiliar1;
        this.telefonoFamiliar1 = telefonoFamiliar1;
        this.correoFamiliar1 = correoFamiliar1;
        this.nombreFamiliar2 = nombreFamiliar2;
        this.telefonoFamiliar2 = telefonoFamiliar2;
        this.correoFamiliar2 = correoFamiliar2;
        this.numeroHermanos = numeroHermanos;
        this.situacionFamiliar = situacionFamiliar;
        this.observaciones = observaciones;
        this.enfermedadesAlergias = enfermedadesAlergias;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setNIA(int NIA) {
        this.NIA = NIA;
    }

    public int getNIA(){
        return NIA;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public String getQuienArreplega() {
        return quienArreplega;
    }

    public void setQuienArreplega(String quienArreplega) {
        this.quienArreplega = quienArreplega;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public String getNombreFamiliar1() {
        return nombreFamiliar1;
    }

    public void setNombreFamiliar1(String nombreFamiliar1) {
        this.nombreFamiliar1 = nombreFamiliar1;
    }

    public String getTelefonoFamiliar1() {
        return telefonoFamiliar1;
    }

    public void setTelefonoFamiliar1(String telefonoFamiliar1) {
        this.telefonoFamiliar1 = telefonoFamiliar1;
    }

    public String getCorreoFamiliar1() {
        return correoFamiliar1;
    }

    public void setCorreoFamiliar1(String correoFamiliar1) {
        this.correoFamiliar1 = correoFamiliar1;
    }

    public String getNombreFamiliar2() {
        return nombreFamiliar2;
    }

    public void setNombreFamiliar2(String nombreFamiliar2) {
        this.nombreFamiliar2 = nombreFamiliar2;
    }

    public String getTelefonoFamiliar2() {
        return telefonoFamiliar2;
    }

    public void setTelefonoFamiliar2(String telefonoFamiliar2) {
        this.telefonoFamiliar2 = telefonoFamiliar2;
    }

    public String getCorreoFamiliar2() {
        return correoFamiliar2;
    }

    public void setCorreoFamiliar2(String correoFamiliar2) {
        this.correoFamiliar2 = correoFamiliar2;
    }

    public int getNumeroHermanos() {
        return numeroHermanos;
    }

    public void setNumeroHermanos(int numeroHermanos) {
        this.numeroHermanos = numeroHermanos;
    }

    public String getSituacionFamiliar() {
        return situacionFamiliar;
    }

    public void setSituacionFamiliar(String situacionFamiliar) {
        this.situacionFamiliar = situacionFamiliar;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getEnfermedadesAlergias() {
        return enfermedadesAlergias;
    }

    public void setEnfermedadesAlergias(String enfermedadesAlergias) {
        this.enfermedadesAlergias = enfermedadesAlergias;
    }

    // Métodos que devuelven propiedades (Property) para JavaFX
    public StringProperty nombreProperty() {
        return new SimpleStringProperty(nombre);
    }
    // Método que devuelve la propiedad (Property) para JavaFX del NIA
    public IntegerProperty NIAProperty() {
        return new SimpleIntegerProperty(NIA);
    }


    public StringProperty apellidoProperty() {
        return new SimpleStringProperty(apellido);
    }
}
