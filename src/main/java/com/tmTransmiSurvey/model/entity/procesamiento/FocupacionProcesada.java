package com.tmTransmiSurvey.model.entity.procesamiento;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="ts_focupacion_procesada")
public class FocupacionProcesada {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator="fovProcGenerator")
    @SequenceGenerator(name="fovProcGenerator", sequenceName = "ts_focupacion_procesada_id_seq",allocationSize=1)
    @Column(name = "id")
    private long id;

    @Column(name = "estacion")
    private String estacion;

    @Column(name = "sentido")
    private String sentido;

    @Column(name = "zona")
    private String zona;

    @Column(name = "fecha")
    private Date fecha;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "estudio", nullable = false)
    private Estudio estudio;


    @OneToMany(fetch = FetchType.LAZY, mappedBy = "focupacionProcesada", cascade = CascadeType.REMOVE)
    private List<FocupacionRegProcesada> registros;


    public FocupacionProcesada() {
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

    public String getZona() {
        return zona;
    }

    public void setZona(String zona) {
        this.zona = zona;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Estudio getEstudio() {
        return estudio;
    }

    public void setEstudio(Estudio estudio) {
        this.estudio = estudio;
    }

    public List<FocupacionRegProcesada> getRegistros() {
        return registros;
    }

    public void setRegistros(List<FocupacionRegProcesada> registros) {
        this.registros = registros;
    }

    public String getSentido() {
        return sentido;
    }

    public void setSentido(String sentido) {
        this.sentido = sentido;
    }
}
