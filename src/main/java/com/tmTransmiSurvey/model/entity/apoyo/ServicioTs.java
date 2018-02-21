package com.tmTransmiSurvey.model.entity.apoyo;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="ts_servicio")
public class ServicioTs {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator="ServicioGenerator")
    @SequenceGenerator(name="ServicioGenerator", sequenceName = "ts_servicio_id_seq",allocationSize=1)
    @Column(name = "id")
    private long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "identificador")
    private String identificador;

    @Column(name = "tipo")
    private String tipo;



    @OneToMany(fetch = FetchType.LAZY, mappedBy = "servicio", cascade = CascadeType.REMOVE)
    private List<ServicioEstacion> registros;



    public ServicioTs() {
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

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public List<ServicioEstacion> getRegistros() {
        return registros;
    }

    public void setRegistros(List<ServicioEstacion> registros) {
        this.registros = registros;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

}
