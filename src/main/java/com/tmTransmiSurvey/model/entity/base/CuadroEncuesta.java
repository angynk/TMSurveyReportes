package com.tmTransmiSurvey.model.entity.base;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Time;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="ts_cuadro_encuesta")
public class CuadroEncuesta implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator="tsCuadroEncuestaGenerator")
    @SequenceGenerator(name="tsCuadroEncuestaGenerator", sequenceName = "ts_cuadro_encuesta_id_seq",allocationSize=1)
    @Column(name = "id")
    private long id;

    @Column(name = "nombre_encuesta")
    private String nombre_encuesta;
    @Column(name = "fecha_encuesta")
    private Date fecha_encuesta;
    @Column(name = "dia_semana")
    private String dia_semana;
    @Column(name = "servicio")
    private String servicio;
    @Column(name = "num_bus")
    private String num_bus;
    @Column(name = "aforador")
    private String aforador;
    @Column(name = "recorrido")
    private int recorrido;
    @Column(name = "num_puerta")
    private int num_puerta;

    @Column(name = "hora_inicio")
    private Time horaInicio;

    @Transient
    private int id_realm;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "cuadroEncuesta", cascade = CascadeType.REMOVE)
    private List<RegistroEncuestaAscDesc> registros;


//    private Set<Usuario> usuariosRecords= new HashSet<Usuario>(0);

    public CuadroEncuesta() {
    }

    public String getNombre_encuesta() {
        return nombre_encuesta;
    }

    public void setNombre_encuesta(String nombre_encuesta) {
        this.nombre_encuesta = nombre_encuesta;
    }


    public String getDia_semana() {
        return dia_semana;
    }

    public void setDia_semana(String dia_semana) {
        this.dia_semana = dia_semana;
    }

    public String getServicio() {
        return servicio;
    }

    public void setServicio(String servicio) {
        this.servicio = servicio;
    }

    public String getNum_bus() {
        return num_bus;
    }

    public void setNum_bus(String num_bus) {
        this.num_bus = num_bus;
    }

    public int getRecorrido() {
        return recorrido;
    }

    public void setRecorrido(int recorrido) {
        this.recorrido = recorrido;
    }

    public int getNum_puerta() {
        return num_puerta;
    }

    public void setNum_puerta(int num_puerta) {
        this.num_puerta = num_puerta;
    }

    public List<RegistroEncuestaAscDesc> getRegistros() {
        return registros;
    }

    public void setRegistros(List<RegistroEncuestaAscDesc> registros) {
        this.registros = registros;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getId_realm() {
        return id_realm;
    }

    public void setId_realm(int id_realm) {
        this.id_realm = id_realm;
    }

    public Date getFecha_encuesta() {
        return fecha_encuesta;
    }

    public void setFecha_encuesta(Date fecha_encuesta) {
        this.fecha_encuesta = fecha_encuesta;
    }

    public String getAforador() {
        return aforador;
    }

    public void setAforador(String aforador) {
        this.aforador = aforador;
    }

    public Time getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(Time horaInicio) {
        this.horaInicio = horaInicio;
    }
}
