package com.tmTransmiSurvey.model.entity;

import javax.persistence.*;

@Entity
@Table(name="ts_registro_focupacion")
public class RegistroEncuestaFOcupacion {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator="tsRegFOcupacionGenerator")
    @SequenceGenerator(name="tsRegFOcupacionGenerator", sequenceName = "ts_registro_focupacion_id_seq",allocationSize=1)
    @Column(name = "id")
    private long id;

    @Column(name = "hora_paso")
    private String hora_paso;

    @Column(name = "ocupacion")
    private Integer ocupacion;

    @Column(name = "codigo")
    private String codigo;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "info_base", nullable = false)
    private FOcupacionEncuesta fOcupacionEncuesta;


    public RegistroEncuestaFOcupacion() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getHora_paso() {
        return hora_paso;
    }

    public void setHora_paso(String hora_paso) {
        this.hora_paso = hora_paso;
    }

    public Integer getOcupacion() {
        return ocupacion;
    }

    public void setOcupacion(Integer ocupacion) {
        this.ocupacion = ocupacion;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public FOcupacionEncuesta getfOcupacionEncuesta() {
        return fOcupacionEncuesta;
    }

    public void setfOcupacionEncuesta(FOcupacionEncuesta fOcupacionEncuesta) {
        this.fOcupacionEncuesta = fOcupacionEncuesta;
    }
}
