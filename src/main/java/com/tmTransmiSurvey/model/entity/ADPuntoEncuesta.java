package com.tmTransmiSurvey.model.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="ts_adpunto_encuesta")
public class ADPuntoEncuesta {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator="tsADPuntoGenerator")
    @SequenceGenerator(name="tsADPuntoGenerator", sequenceName = "ts_adpunto_encuesta_id_seq",allocationSize=1)
    @Column(name = "id")
    private long id;

    @Column(name = "dia_semana")
    private String dia_semana;
    @Column(name = "servicio")
    private String servicio;
    @Column(name = "estacion")
    private String estacion;

    @Column(name = "aforador")
    private String aforador;
    @Column(name = "fecha_encuesta")
    private Date fecha_encuesta;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "adPuntoEncuesta", cascade = CascadeType.REMOVE)
    private List<RegistroEncuestaADPunto> registros;

    public ADPuntoEncuesta() {

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

    public List<RegistroEncuestaADPunto> getRegistros() {
        return registros;
    }

    public void setRegistros(List<RegistroEncuestaADPunto> registros) {
        this.registros = registros;
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
}
