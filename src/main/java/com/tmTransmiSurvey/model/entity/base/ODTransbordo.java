package com.tmTransmiSurvey.model.entity.base;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="ts_od_transbordo")
public class ODTransbordo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator="tsODTransbordoGenerator")
    @SequenceGenerator(name="tsODTransbordoGenerator", sequenceName = "ts_od_transbordo_id_seq",allocationSize=1)
    @Column(name = "id")
    private long id;

    @Column(name = "estacion")
    private String estacion;

    @Column(name = "servicio")
    private String servicio;


    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id_registro", nullable = false)
    private ODRegistro odRegistro;

    public ODTransbordo() {
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

    public String getServicio() {
        return servicio;
    }

    public void setServicio(String servicio) {
        this.servicio = servicio;
    }

    public ODRegistro getOdRegistro() {
        return odRegistro;
    }

    public void setOdRegistro(ODRegistro odRegistro) {
        this.odRegistro = odRegistro;
    }
}
