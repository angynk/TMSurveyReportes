package com.tmTransmiSurvey.model.entity.base;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="ts_fo_bus_registro")
public class RegistroFOBus implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator="tsFOBusRegistroGenerator")
    @SequenceGenerator(name="tsFOBusRegistroGenerator", sequenceName = "ts_fo_bus_registro_id_seq",allocationSize=1)
    @Column(name = "id")
    private long id;

    @Column(name = "codigo")
    private String codigo;

    @Column(name = "hora_paso")
    private String hora_paso;

    @Column(name = "num_bus")
    private Integer num_bus;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id_encuesta", nullable = false)
    private FOBus idFoBus;

    public RegistroFOBus() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getHora_paso() {
        return hora_paso;
    }

    public void setHora_paso(String hora_paso) {
        this.hora_paso = hora_paso;
    }

    public Integer getNum_bus() {
        return num_bus;
    }

    public void setNum_bus(Integer num_bus) {
        this.num_bus = num_bus;
    }

    public FOBus getIdFoBus() {
        return idFoBus;
    }

    public void setIdFoBus(FOBus idFoBus) {
        this.idFoBus = idFoBus;
    }
}
