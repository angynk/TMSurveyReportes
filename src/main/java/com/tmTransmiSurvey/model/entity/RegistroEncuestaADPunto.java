package com.tmTransmiSurvey.model.entity;

import javax.persistence.*;

@Entity
@Table(name="ts_registro_ascdes_punto")
public class RegistroEncuestaADPunto {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator="tsADPuntoRegGenerator")
    @SequenceGenerator(name="tsADPuntoRegGenerator", sequenceName = "ts_registro_ascdes_punto_id_seq",allocationSize=1)
    @Column(name = "id")
    private long id;

    @Column(name = "num_bus")
    private String num_bus;
    @Column(name = "hora_llegada")
    private String hora_llegada;
    @Column(name = "hora_salida")
    private String hora_salida;
    @Column(name = "pas_bajan")
    private int pas_bajan;
    @Column(name = "pas_suben")
    private int pas_suben;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "ad_punto", nullable = false)
    private ADPuntoEncuesta adPuntoEncuesta;

    public RegistroEncuestaADPunto() {
    }

    public String getNum_bus() {
        return num_bus;
    }

    public void setNum_bus(String num_bus) {
        this.num_bus = num_bus;
    }

    public String getHora_llegada() {
        return hora_llegada;
    }

    public void setHora_llegada(String hora_llegada) {
        this.hora_llegada = hora_llegada;
    }

    public String getHora_salida() {
        return hora_salida;
    }

    public void setHora_salida(String hora_salida) {
        this.hora_salida = hora_salida;
    }

    public int getPas_bajan() {
        return pas_bajan;
    }

    public void setPas_bajan(int pas_bajan) {
        this.pas_bajan = pas_bajan;
    }

    public int getPas_suben() {
        return pas_suben;
    }

    public void setPas_suben(int pas_suben) {
        this.pas_suben = pas_suben;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ADPuntoEncuesta getAdPuntoEncuesta() {
        return adPuntoEncuesta;
    }

    public void setAdPuntoEncuesta(ADPuntoEncuesta adPuntoEncuesta) {
        this.adPuntoEncuesta = adPuntoEncuesta;
    }
}
