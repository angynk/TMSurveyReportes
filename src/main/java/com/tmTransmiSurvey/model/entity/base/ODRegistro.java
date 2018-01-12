package com.tmTransmiSurvey.model.entity.base;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name="ts_od_registro")
public class ODRegistro implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator="tsODRegistroGenerator")
    @SequenceGenerator(name="tsODRegistroGenerator", sequenceName = "ts_od_registro_id_seq",allocationSize=1)
    @Column(name = "id")
    private long id;

    @Column(name = "estacion_origen")
    private String estacion_origen;

    @Column(name = "estacion_destino")
    private String estacion_destino;

    @Column(name = "servicio_origen")
    private String servicio_origen;

    @Column(name = "hora_encuesta")
    private String hora_encuesta;

    @Column(name = "mas_transbordos")
    private Boolean mas_transbordos;

    @Column(name = "cant_viaje")
    private Integer cant_viaje;

    @Column(name = "modo_llegada")
    private String modo_llegada;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "odRegistro", cascade = CascadeType.REMOVE)
    private List<ODTransbordo> transbordos;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id_encuesta", nullable = false)
    private ODEncuesta idEncuesta;

    public ODRegistro() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEstacion_origen() {
        return estacion_origen;
    }

    public void setEstacion_origen(String estacion_origen) {
        this.estacion_origen = estacion_origen;
    }

    public String getEstacion_destino() {
        return estacion_destino;
    }

    public void setEstacion_destino(String estacion_destino) {
        this.estacion_destino = estacion_destino;
    }

    public String getServicio_origen() {
        return servicio_origen;
    }

    public void setServicio_origen(String servicio_origen) {
        this.servicio_origen = servicio_origen;
    }

    public String getHora_encuesta() {
        return hora_encuesta;
    }

    public void setHora_encuesta(String hora_encuesta) {
        this.hora_encuesta = hora_encuesta;
    }

    public List<ODTransbordo> getTransbordos() {
        return transbordos;
    }

    public void setTransbordos(List<ODTransbordo> transbordos) {
        this.transbordos = transbordos;
    }

    public ODEncuesta getIdEncuesta() {
        return idEncuesta;
    }

    public void setIdEncuesta(ODEncuesta idEncuesta) {
        this.idEncuesta = idEncuesta;
    }

    public Boolean getMas_transbordos() {
        return mas_transbordos;
    }

    public void setMas_transbordos(Boolean mas_transbordos) {
        this.mas_transbordos = mas_transbordos;
    }

    public Integer getCant_viaje() {
        return cant_viaje;
    }

    public void setCant_viaje(Integer cant_viaje) {
        this.cant_viaje = cant_viaje;
    }

    public String getModo_llegada() {
        return modo_llegada;
    }

    public void setModo_llegada(String modo_llegada) {
        this.modo_llegada = modo_llegada;
    }
}
