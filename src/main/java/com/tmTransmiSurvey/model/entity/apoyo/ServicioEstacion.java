package com.tmTransmiSurvey.model.entity.apoyo;


import javax.persistence.*;

@Entity
@Table(name="ts_servicio_estacion")
public class ServicioEstacion {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator="ServicioEstacionGenerator")
    @SequenceGenerator(name="ServicioEstacionGenerator", sequenceName = "ts_servicio_estacion_id_seq",allocationSize=1)
    @Column(name = "id")
    private long id;


    @Column(name = "orden")
    private Integer orden;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "servicio", nullable = false)
    private ServicioTs servicio;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "estacion", nullable = false)
    private Estacion estacion;

    public ServicioEstacion() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Integer getOrden() {
        return orden;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
    }

    public ServicioTs getServicio() {
        return servicio;
    }

    public void setServicio(ServicioTs servicio) {
        this.servicio = servicio;
    }

    public Estacion getEstacion() {
        return estacion;
    }

    public void setEstacion(Estacion estacion) {
        this.estacion = estacion;
    }
}
