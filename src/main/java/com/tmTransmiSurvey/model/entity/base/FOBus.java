package com.tmTransmiSurvey.model.entity.base;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="ts_fo_bus")
public class FOBus implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator="tsFOBusGenerator")
    @SequenceGenerator(name="tsFOBusGenerator", sequenceName = "ts_fo_bus_id_seq",allocationSize=1)
    @Column(name = "id")
    private long id;

    @Column(name = "estacion")
    private String estacion;

    @Column(name = "sentido")
    private String sentido;

    @Column(name = "aforador")
    private String aforador;

    @Column(name = "fecha_encuesta")
    private Date fecha_encuesta;

    @Column(name = "dia_semana")
    private String dia_semana;


    @OneToMany(fetch = FetchType.LAZY, mappedBy = "idFoBus", cascade = CascadeType.REMOVE)
    private List<RegistroFOBus> registros;


    public FOBus() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getDia_semana() {
        return dia_semana;
    }

    public void setDia_semana(String dia_semana) {
        this.dia_semana = dia_semana;
    }

    public List<RegistroFOBus> getRegistros() {
        return registros;
    }

    public void setRegistros(List<RegistroFOBus> registros) {
        this.registros = registros;
    }
}
