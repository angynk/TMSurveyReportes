package com.tmTransmiSurvey.model.entity.procesamiento;

import com.tmTransmiSurvey.model.entity.apoyo.IntervalosProgramacion;

import javax.persistence.*;
import java.sql.Time;

@Entity
@Table(name="ts_focupacion_reg_procesada")
public class FocupacionRegProcesada {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator="fovRegProcGenerator")
    @SequenceGenerator(name="fovRegProcGenerator", sequenceName = "ts_focupacion_reg_procesada_id_seq",allocationSize=1)
    @Column(name = "id")
    private long id;

    @Column(name = "servicio")
    private String servicio;

    @Column(name = "hora")
    private Time hora;

    @Column(name = "num_ocupacion")
    private Integer numOcupacion;

    @Column(name = "por_ocupacion")
    private Double porOcupacion;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "intervalo", nullable = false)
    private IntervalosProgramacion intervalo;


    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "focupacion", nullable = false)
    private FocupacionProcesada focupacionProcesada;


    public FocupacionRegProcesada() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getServicio() {
        return servicio;
    }

    public void setServicio(String servicio) {
        this.servicio = servicio;
    }

    public Time getHora() {
        return hora;
    }

    public void setHora(Time hora) {
        this.hora = hora;
    }

    public Integer getNumOcupacion() {
        return numOcupacion;
    }

    public void setNumOcupacion(Integer numOcupacion) {
        this.numOcupacion = numOcupacion;
    }

    public Double getPorOcupacion() {
        return porOcupacion;
    }

    public void setPorOcupacion(Double porOcupacion) {
        this.porOcupacion = porOcupacion;
    }

    public IntervalosProgramacion getIntervalo() {
        return intervalo;
    }

    public void setIntervalo(IntervalosProgramacion intervalo) {
        this.intervalo = intervalo;
    }

    public FocupacionProcesada getFocupacionProcesada() {
        return focupacionProcesada;
    }

    public void setFocupacionProcesada(FocupacionProcesada focupacionProcesada) {
        this.focupacionProcesada = focupacionProcesada;
    }
}
