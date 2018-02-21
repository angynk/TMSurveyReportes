package com.tmTransmiSurvey.model.entity.apoyo;

import javax.persistence.*;

@Entity
@Table(name="ts_fov_codigos")
public class FovCodigos {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator="FovCodigosGenerator")
    @SequenceGenerator(name="FovCodigosGenerator", sequenceName = "ts_fov_codigos_id_seq",allocationSize=1)
    @Column(name = "id")
    private long id;


    @Column(name = "sentido")
    private String sentido;

    @Column(name = "codigo")
    private String codigo;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "tipologia", nullable = false)
    private Tipologia tipologia;

    @Column(name = "servicio")
    private String servicio;

    @Column(name = "estacion")
    private String estacion;

    public FovCodigos() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSentido() {
        return sentido;
    }

    public void setSentido(String sentido) {
        this.sentido = sentido;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getServicio() {
        return servicio;
    }

    public void setServicio(String servicio) {
        this.servicio = servicio;
    }

    public String getEstacion() {
        return estacion;
    }

    public void setEstacion(String estacion) {
        this.estacion = estacion;
    }

    public Tipologia getTipologia() {
        return tipologia;
    }

    public void setTipologia(Tipologia tipologia) {
        this.tipologia = tipologia;
    }
}
