package com.tmTransmiSurvey.model.entity.apoyo;

import com.tmTransmiSurvey.model.entity.procesamiento.FocupacionRegProcesada;

import javax.persistence.*;
import java.sql.Time;
import java.util.List;

@Entity
@Table(name="intervalos_programacion")
public class IntervalosProgramacion {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator="IntervalosGenerator")
    @SequenceGenerator(name="IntervalosGenerator", sequenceName = "intervalos_programacion_id_seq",allocationSize=1)
    @Column(name = "id")
    private long id;

    @Column(name = "inicio")
    private Time inicio;

    @Column(name = "fin")
    private Time fin;

    @Column(name = "orden")
    private int orden;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "tipo_franja", nullable = false)
    private TipoFranja tipoFranja;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "intervalo", cascade = CascadeType.REMOVE)
    private List<FocupacionRegProcesada> registros;



    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Time getInicio() {
        return inicio;
    }

    public void setInicio(Time inicio) {
        this.inicio = inicio;
    }

    public Time getFin() {
        return fin;
    }

    public void setFin(Time fin) {
        this.fin = fin;
    }

    public TipoFranja getTipoFranja() {
        return tipoFranja;
    }

    public void setTipoFranja(TipoFranja tipoFranja) {
        this.tipoFranja = tipoFranja;
    }

    public int getOrden() {
        return orden;
    }

    public void setOrden(int orden) {
        this.orden = orden;
    }

    public List<FocupacionRegProcesada> getRegistros() {
        return registros;
    }

    public void setRegistros(List<FocupacionRegProcesada> registros) {
        this.registros = registros;
    }
}
