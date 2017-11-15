package com.tmTransmiSurvey.model.entity;

import javax.persistence.*;

@Entity
@Table(name="ts_registro_ascdes_troncal")
public class RegistroEncuestaAscDesc {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator="RegistroGenerator")
    @SequenceGenerator(name="RegistroGenerator", sequenceName = "ts_registro_ascdes_troncal_id_seq",allocationSize=1)
    @Column(name = "id")
    private long id;

    @Column(name = "estacion")
    private String estacion;
    @Column(name = "hora_llegada")
    private String hora_llegada;
    @Column(name = "bajan")
    private int bajan;
    @Column(name = "suben")
    private int suben;
    @Column(name = "quedan")
    private int quedan;
    @Column(name = "hora_salida")
    private String hora_salida;
    @Column(name = "observacion")
    private String observacion;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "cuadro_encuesta", nullable = false)
    private CuadroEncuesta cuadroEncuesta;

    public RegistroEncuestaAscDesc() {
    }

    public String getEstacion() {
        return estacion;
    }

    public void setEstacion(String estacion) {
        this.estacion = estacion;
    }

    public String getHora_llegada() {
        return hora_llegada;
    }

    public void setHora_llegada(String hora_llegada) {
        this.hora_llegada = hora_llegada;
    }

    public int getBajan() {
        return bajan;
    }

    public void setBajan(int bajan) {
        this.bajan = bajan;
    }

    public int getSuben() {
        return suben;
    }

    public void setSuben(int suben) {
        this.suben = suben;
    }

    public int getQuedan() {
        return quedan;
    }

    public void setQuedan(int quedan) {
        this.quedan = quedan;
    }

    public String getHora_salida() {
        return hora_salida;
    }

    public void setHora_salida(String hora_salida) {
        this.hora_salida = hora_salida;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public CuadroEncuesta getCuadroEncuesta() {
        return cuadroEncuesta;
    }

    public void setCuadroEncuesta(CuadroEncuesta cuadroEncuesta) {
        this.cuadroEncuesta = cuadroEncuesta;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }
}
