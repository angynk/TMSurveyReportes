package com.tmTransmiSurvey.model.entity;

import javax.persistence.*;
import java.sql.Time;

@Entity
@Table(name="ts_adabordo_reg_procesada")
public class ADabordoRegProcesada {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator="AbordoRegGenerator")
    @SequenceGenerator(name="AbordoRegGenerator", sequenceName = "ts_adabordo_reg_procesada_id_seq",allocationSize=1)
    @Column(name = "id")
    private long id;

    @Column(name = "estacion")
    private String estacion;

    @Column(name = "franja")
    private String franja;

    @Column(name = "hora_llegada")
    private Time horaLlegada;

    @Column(name = "hora_salida")
    private Time horaSalida;

    @Column(name = "pas_bus")
    private Integer pasBus;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "ad_base", nullable = false)
    private ADabordoProcesada adBase;

    public ADabordoRegProcesada() {
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

    public Time getHoraLlegada() {
        return horaLlegada;
    }

    public void setHoraLlegada(Time horaLlegada) {
        this.horaLlegada = horaLlegada;
    }

    public Time getHoraSalida() {
        return horaSalida;
    }

    public void setHoraSalida(Time horaSalida) {
        this.horaSalida = horaSalida;
    }

    public Integer getPasBus() {
        return pasBus;
    }

    public void setPasBus(Integer pasBus) {
        this.pasBus = pasBus;
    }

    public ADabordoProcesada getAdBase() {
        return adBase;
    }

    public void setAdBase(ADabordoProcesada adBase) {
        this.adBase = adBase;
    }

    public String getFranja() {
        return franja;
    }

    public void setFranja(String franja) {
        this.franja = franja;
    }
}
