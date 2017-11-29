package com.tmTransmiSurvey.model.entity.procesamiento;

import com.tmTransmiSurvey.model.entity.procesamiento.ADabordoProcesada;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="ts_estudio")
public class Estudio {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator="EstudioGenerator")
    @SequenceGenerator(name="EstudioGenerator", sequenceName = "ts_estudio_id_seq",allocationSize=1)
    @Column(name = "id")
    private long id;

    @Column(name = "fecha_estudio")
    private Date fechaEstudio;

    @Column(name = "tipo_encuesta")
    private String tipoEncuesta;

    @Column(name = "identificador")
    private String identificador;



    @Column(name = "modo")
    private String modo;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "estudio", cascade = CascadeType.REMOVE)
    private List<ADabordoProcesada> registros;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "estudio", cascade = CascadeType.REMOVE)
    private List<FocupacionProcesada> registrosFov;

    @Transient
    private String fechaFormatted;


    public Estudio() {
    }



    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getFechaEstudio() {
        return fechaEstudio;
    }

    public void setFechaEstudio(Date fechaEstudio) {
        this.fechaEstudio = fechaEstudio;
    }

    public String getTipoEncuesta() {
        return tipoEncuesta;
    }

    public void setTipoEncuesta(String tipoEncuesta) {
        this.tipoEncuesta = tipoEncuesta;
    }

    public List<ADabordoProcesada> getRegistros() {
        return registros;
    }

    public void setRegistros(List<ADabordoProcesada> registros) {
        this.registros = registros;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public String getFechaFormatted() {
        if(fechaEstudio!=null){
            SimpleDateFormat dt1 = new SimpleDateFormat("dd-MM-yyyy");
            return dt1.format(fechaEstudio);
        }
        return "";
    }

    public void setFechaFormatted(String fechaFormatted) {
        this.fechaFormatted = fechaFormatted;
    }

    public String getModo() {
        return modo;
    }

    public void setModo(String modo) {
        this.modo = modo;
    }

    public List<FocupacionProcesada> getRegistrosFov() {
        return registrosFov;
    }

    public void setRegistrosFov(List<FocupacionProcesada> registrosFov) {
        this.registrosFov = registrosFov;
    }
}
