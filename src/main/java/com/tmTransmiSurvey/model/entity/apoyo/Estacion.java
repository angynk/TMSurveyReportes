package com.tmTransmiSurvey.model.entity.apoyo;


import javax.persistence.*;
import java.util.List;


@Entity
@Table(name="ts_estacion")
public class Estacion {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator="EstacionGenerator")
    @SequenceGenerator(name="EstacionGenerator", sequenceName = "ts_estacion_id_seq",allocationSize=1)
    @Column(name = "id")
    private long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "codigo")
    private String codigo;

    @Column(name = "modo")
    private String modo;

    @Column(name = "zona")
    private String zona;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "estacion", cascade = CascadeType.REMOVE)
    private List<ServicioEstacion> registros;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "estacion", cascade = CascadeType.REMOVE)
    private List<FovCodigos> codigos;

    public Estacion() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public List<ServicioEstacion> getRegistros() {
        return registros;
    }

    public void setRegistros(List<ServicioEstacion> registros) {
        this.registros = registros;
    }

    public String getModo() {
        return modo;
    }

    public void setModo(String modo) {
        this.modo = modo;
    }

    public String getZona() {
        return zona;
    }

    public void setZona(String zona) {
        this.zona = zona;
    }

    public List<FovCodigos> getCodigos() {
        return codigos;
    }

    public void setCodigos(List<FovCodigos> codigos) {
        this.codigos = codigos;
    }
}
