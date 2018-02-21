package com.tmTransmiSurvey.model.entity.procesamiento;

import javax.persistence.*;
import java.sql.Time;

@Entity
@Table(name="ts_fobus_reg_procesada")
public class FoBusRegProcesada {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator="fobRegProcGenerator")
    @SequenceGenerator(name="fobRegProcGenerator", sequenceName = "ts_fobus_reg_procesada_id_seq",allocationSize=1)
    @Column(name = "id")
    private long id;

    @Column(name = "servicio")
    private String servicio;

    @Column(name = "tipologia")
    private String tipologia;

    @Column(name = "hora")
    private Time hora;

    @Column(name = "num_bus")
    private Integer numBus;


}
