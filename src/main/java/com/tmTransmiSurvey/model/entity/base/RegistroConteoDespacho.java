package com.tmTransmiSurvey.model.entity.base;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="ts_registro_conteo_despachos")
public class RegistroConteoDespacho implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator="tsCuadroEncuestaGenerator")
    @SequenceGenerator(name="tsCuadroEncuestaGenerator", sequenceName = "ts_cuadro_encuesta_id_seq",allocationSize=1)
    @Column(name = "id")
    private long id;

    @Column(name = "num_bus")
    private String num_bus;

    @Column(name = "hora_despacho")
    private String hora_despacho;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "conteo_despacho", nullable = false)
    private CoDespachosEncuesta conteoDespacho;

    public RegistroConteoDespacho() {
    }



    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNum_bus() {
        return num_bus;
    }

    public void setNum_bus(String num_bus) {
        this.num_bus = num_bus;
    }

    public String getHora_despacho() {
        return hora_despacho;
    }

    public void setHora_despacho(String hora_despacho) {
        this.hora_despacho = hora_despacho;
    }

    public CoDespachosEncuesta getConteoDespacho() {
        return conteoDespacho;
    }

    public void setConteoDespacho(CoDespachosEncuesta conteoDespacho) {
        this.conteoDespacho = conteoDespacho;
    }
}
