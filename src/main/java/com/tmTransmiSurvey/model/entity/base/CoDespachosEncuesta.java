package com.tmTransmiSurvey.model.entity.base;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="ts_conteo_despachos")
public class CoDespachosEncuesta implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator="tsConteoDespachosGenerator")
    @SequenceGenerator(name="tsConteoDespachosGenerator", sequenceName = "ts_conteo_despachos_id_seq",allocationSize=1)
    @Column(name = "id")
    private long id;

    @Column(name = "fecha_encuesta")
    private Date fecha_encuesta;
    @Column(name = "dia_semana")
    private String dia_semana;
    @Column(name = "servicio")
    private String servicio;
    @Column(name = "estacion")
    private String estacion;
    @Column(name = "aforador")
    private String aforador;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "conteoDespacho", cascade = CascadeType.REMOVE)
    private List<RegistroConteoDespacho> registros;

    public CoDespachosEncuesta() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getServicio() {
        return servicio;
    }

    public void setServicio(String servicio) {
        this.servicio = servicio;
    }

    public String getEstacion() {
        return estacion;
    }

    public void setEstacion(String estacion) {
        this.estacion = estacion;
    }

    public String getAforador() {
        return aforador;
    }

    public void setAforador(String aforador) {
        this.aforador = aforador;
    }

    public List<RegistroConteoDespacho> getRegistros() {
        return registros;
    }

    public void setRegistros(List<RegistroConteoDespacho> registros) {
        this.registros = registros;
    }
}
