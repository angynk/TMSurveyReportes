package com.tmTransmiSurvey.model.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="ts_adabordo_procesada")
public class ADabordoProcesada {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator="AbordoProcGenerator")
    @SequenceGenerator(name="AbordoProcGenerator", sequenceName = "ts_adabordo_procesada_id_seq",allocationSize=1)
    @Column(name = "id")
    private long id;

    @Column(name = "num_bus")
    private String numBus;

    @Column(name = "servicio")
    private String servicio;

    @Column(name = "recorrido")
    private int recorrido;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "estudio", nullable = false)
    private Estudio estudio;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "adBase", cascade = CascadeType.REMOVE)
    private List<ADabordoRegProcesada> registros;


    public ADabordoProcesada() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNumBus() {
        return numBus;
    }

    public void setNumBus(String numBus) {
        this.numBus = numBus;
    }

    public String getServicio() {
        return servicio;
    }

    public void setServicio(String servicio) {
        this.servicio = servicio;
    }

    public int getRecorrido() {
        return recorrido;
    }

    public void setRecorrido(int recorrido) {
        this.recorrido = recorrido;
    }

    public Estudio getEstudio() {
        return estudio;
    }

    public void setEstudio(Estudio estudio) {
        this.estudio = estudio;
    }

    public List<ADabordoRegProcesada> getRegistros() {
        return registros;
    }

    public void setRegistros(List<ADabordoRegProcesada> registros) {
        this.registros = registros;
    }
}
