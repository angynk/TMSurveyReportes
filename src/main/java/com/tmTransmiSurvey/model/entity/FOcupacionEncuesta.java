package com.tmTransmiSurvey.model.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="ts_focupacion_encuesta")
public class FOcupacionEncuesta implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator="tsFOcupacionGenerator")
    @SequenceGenerator(name="tsFOcupacionGenerator", sequenceName = "ts_focupacion_encuesta_id_seq",allocationSize=1)
    @Column(name = "id")
    private long id;

    @Column(name = "aforador")
    private String aforador;

    @Column(name = "fecha_encuesta")
    private Date fecha_encuesta;

    @Column(name = "zona")
    private String zona;

    @Column(name = "estacion")
    private String estacion;

    @Column(name = "sentido")
    private String sentido;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "fOcupacionEncuesta", cascade = CascadeType.REMOVE)
    private List<RegistroEncuestaFOcupacion> registros;

    public FOcupacionEncuesta() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAforador() {
        return aforador;
    }

    public void setAforador(String aforador) {
        this.aforador = aforador;
    }

    public Date getFecha_encuesta() {
        return fecha_encuesta;
    }

    public void setFecha_encuesta(Date fecha_encuesta) {
        this.fecha_encuesta = fecha_encuesta;
    }

    public String getZona() {
        return zona;
    }

    public void setZona(String zona) {
        this.zona = zona;
    }

    public String getEstacion() {
        return estacion;
    }

    public void setEstacion(String estacion) {
        this.estacion = estacion;
    }

    public String getSentido() {
        return sentido;
    }

    public void setSentido(String sentido) {
        this.sentido = sentido;
    }

    public List<RegistroEncuestaFOcupacion> getRegistros() {
        return registros;
    }

    public void setRegistros(List<RegistroEncuestaFOcupacion> registros) {
        this.registros = registros;
    }
}
