package com.tmTransmiSurvey.model.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="ts_estudio")
public class Estudio {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator="EstudioGenerator")
    @SequenceGenerator(name="EstudioGenerator", sequenceName = "ts_estudio_id_seq",allocationSize=1)
    @Column(name = "id")
    private long id;

    @Column(name = "fecha_estudio")
    private Date fechaEstudio;

    @Column(name = "tipo_encuesta")
    private String tipoEncuesta;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "estudio", cascade = CascadeType.REMOVE)
    private List<ADabordoProcesada> registros;


    public Estudio() {
    }



    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getFechaEstudio() {
        return fechaEstudio;
    }

    public void setFechaEstudio(Date fechaEstudio) {
        this.fechaEstudio = fechaEstudio;
    }

    public String getTipoEncuesta() {
        return tipoEncuesta;
    }

    public void setTipoEncuesta(String tipoEncuesta) {
        this.tipoEncuesta = tipoEncuesta;
    }

    public List<ADabordoProcesada> getRegistros() {
        return registros;
    }

    public void setRegistros(List<ADabordoProcesada> registros) {
        this.registros = registros;
    }
}
