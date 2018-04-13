package com.tmTransmiSurvey.model.entity.base;



import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="ts_registro_tiempos_recorrido")
public class RegistroTRecorridos implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator="TiemposGenerator")
    @SequenceGenerator(name="TiemposGenerator", sequenceName = "ts_registro_tiempos_recorrido_id_seq",allocationSize=1)
    @Column(name = "id")
    private long id;

    @Column(name = "estacion")
    private String estacion;
    @Column(name = "hora_llegada")
    private String hora_llegada;
    @Column(name = "hora_salida")
    private String hora_salida;
    @Column(name = "observacion")
    private String observacion;
    @Column(name = "es_primera_zona_des")
    private boolean primera_zon_destino;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "tiempo_recorrido", nullable = false)
    private TRecorridosEncuesta tRecorridosEncuesta;

    public RegistroTRecorridos() {
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

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public boolean isPrimera_zon_destino() {
        return primera_zon_destino;
    }

    public void setPrimera_zon_destino(boolean primera_zon_destino) {
        this.primera_zon_destino = primera_zon_destino;
    }

    public TRecorridosEncuesta gettRecorridosEncuesta() {
        return tRecorridosEncuesta;
    }

    public void settRecorridosEncuesta(TRecorridosEncuesta tRecorridosEncuesta) {
        this.tRecorridosEncuesta = tRecorridosEncuesta;
    }
}
