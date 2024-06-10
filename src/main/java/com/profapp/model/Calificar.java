package com.profapp.model;

import com.profapp.util.EstadoCalificacion;
import jakarta.persistence.*;

@Entity
public class Calificar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Alumno alumno;

    @ManyToOne
    private Asignatura asignatura;

    @Enumerated(EnumType.STRING)
    private EstadoCalificacion estado;
    // Constructor vacío
    public Calificar() {}

    // Constructor con alumno, asignatura y nota
    public Calificar(Alumno alumno, Asignatura asignatura, EstadoCalificacion estado) {
        this.alumno = alumno;
        this.asignatura = asignatura;
        this.estado = estado;
    }

    // Getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Alumno getAlumno() {
        return alumno;
    }

    public void setAlumno(Alumno alumno) {
        this.alumno = alumno;
    }

    public Asignatura getAsignatura() {
        return asignatura;
    }

    public void setAsignatura(Asignatura asignatura) {
        this.asignatura = asignatura;
    }

    public EstadoCalificacion getEstado() {
        return estado;
    }

    public void setEstado(EstadoCalificacion estado) {
        this.estado = estado;
    }
}
