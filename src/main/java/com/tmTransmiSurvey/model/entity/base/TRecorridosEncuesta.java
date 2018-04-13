package com.tmTransmiSurvey.model.entity.base;



import javax.persistence.*;
import java.io.Serializable;
import java.sql.Time;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="ts_tiempo_recorrido")
public class TRecorridosEncuesta implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator="tsTRegistroGenerator")
    @SequenceGenerator(name="tsTRegistroGenerator", sequenceName = "ts_tiempo_recorrido_id_seq",allocationSize=1)
    @Column(name = "id")
    private long id;

    @Column(name = "fecha_encuesta")
    private Date fecha_encuesta;
    @Column(name = "dia_semana")
    private String dia_semana;
    @Column(name = "servicio")
    private String servicio;
    @Column(name = "num_bus")
    private String num_bus;
    @Column(name = "recorrido")
    private int recorrido;
    @Column(name = "aforador")
    private String aforador;

    @Column(name = "hora_inicio")
    private Time horaInicio;


    @OneToMany(fetch = FetchType.LAZY, mappedBy = "tRecorridosEncuesta", cascade = CascadeType.REMOVE)
    private List<RegistroTRecorridos> registros;

    public TRecorridosEncuesta() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getFecha_encuesta() {
        return fecha_encuesta;
    }

    public void setFecha_encuesta(Date fecha_encuesta) {
        this.fecha_encuesta = fecha_encuesta;
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

    public List<RegistroTRecorridos> getRegistros() {
        return registros;
    }

    public void setRegistros(List<RegistroTRecorridos> registros) {
        this.registros = registros;
    }

    public int getRecorrido() {
        return recorrido;
    }

    public void setRecorrido(int recorrido) {
        this.recorrido = recorrido;
    }
}
