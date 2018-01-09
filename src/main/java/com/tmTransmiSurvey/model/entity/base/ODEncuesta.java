package com.tmTransmiSurvey.model.entity.base;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="ts_od_encuesta")
public class ODEncuesta implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator="tsODEncuestaGenerator")
    @SequenceGenerator(name="tsODEncuestaGenerator", sequenceName = "ts_od_encuesta_id_seq",allocationSize=1)
    @Column(name = "id")
    private long id;

    @Column(name = "aforador")
    private String aforador;

    @Column(name = "fecha_encuesta")
    private Date fecha_encuesta;

    @Column(name = "dia_semana")
    private String dia_semana;

    @Column(name = "estacion")
    private String estacion;

    @Column(name = "tipo_encuesta")
    private String tipo;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "idEncuesta", cascade = CascadeType.REMOVE)
    private List<ODRegistro> registros;

    public ODEncuesta() {
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

    public String getDia_semana() {
        return dia_semana;
    }

    public void setDia_semana(String dia_semana) {
        this.dia_semana = dia_semana;
    }

    public String getEstacion() {
        return estacion;
    }

    public void setEstacion(String estacion) {
        this.estacion = estacion;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public List<ODRegistro> getRegistros() {
        return registros;
    }

    public void setRegistros(List<ODRegistro> registros) {
        this.registros = registros;
    }
}
