package com.tmTransmiSurvey.model.entity.apoyo;

import javax.persistence.*;

@Entity
@Table(name="ts_modo")
public class Modo {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator="ModoGenerator")
    @SequenceGenerator(name="ModoGenerator", sequenceName = "ts_modo_id_seq",allocationSize=1)
    @Column(name = "id")
    private long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "abreviatura")
    private String abreviatura;

    @Column(name = "descripcion")
    private String descripcion;

    public Modo() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getAbreviatura() {
        return abreviatura;
    }

    public void setAbreviatura(String abreviatura) {
        this.abreviatura = abreviatura;
    }
}
