package com.tmTransmiSurvey.view;

import com.tmTransmiSurvey.controller.TipoEncuesta;
import com.tmTransmiSurvey.controller.processor.ExportarADPuntoProcessor;
import com.tmTransmiSurvey.model.entity.Estacion;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ManagedBean(name="procesView")
@ViewScoped
public class ProcesamientoView {

    private String tipoProcesamiento;
    private String encuesta;
    private List<String> encuestas;
    private String modo;
    private List<String> modos;
    private Boolean adAVisible;
    private Boolean botonHabilitado;

    private String estacion;
    private List<String> estacionesRecords;
    private Date fechaInicio;
    private Date fechaFin;

    @ManagedProperty(value="#{ExportarADPuntoProcessor}")
    private ExportarADPuntoProcessor exportarDatosProcessor;

    public ProcesamientoView() {
    }

    @PostConstruct
    public void init() {

        encuestas = TipoEncuesta.listaEncuestas();
        encuesta = TipoEncuesta.ENCUESTA_ASC_DESC_ABORDO;
        modos = TipoEncuesta.listaModos();
        modo = TipoEncuesta.MODO_TRONCAL;

        adAVisible = false;
        botonHabilitado = true;
    }

    public String getTipoProcesamiento() {
        return tipoProcesamiento;
    }

    public void setTipoProcesamiento(String tipoProcesamiento) {
        this.tipoProcesamiento = tipoProcesamiento;
    }

    public void habilitarTipoProcesamiento(){
        if(encuesta.equals(TipoEncuesta.ENCUESTA_ASC_DESC_ABORDO)){
            adAVisible = true;
            botonHabilitado = false;
            estacionesRecords = convertStringList (exportarDatosProcessor.encontrarTodosLasEstaciones());
        }

    }

    private List<String> convertStringList(List<Estacion> estaciones) {
        List<String> lista = new ArrayList<>();
        for(Estacion ser:estaciones){
            lista.add(ser.getNombre());
        }
        return lista;
    }

    public void procesarDatosEncuesta(){

    }

    public String getEncuesta() {
        return encuesta;
    }

    public void setEncuesta(String encuesta) {
        this.encuesta = encuesta;
    }

    public List<String> getEncuestas() {
        return encuestas;
    }

    public void setEncuestas(List<String> encuestas) {
        this.encuestas = encuestas;
    }

    public String getModo() {
        return modo;
    }

    public void setModo(String modo) {
        this.modo = modo;
    }

    public List<String> getModos() {
        return modos;
    }

    public void setModos(List<String> modos) {
        this.modos = modos;
    }

    public Boolean getAdAVisible() {
        return adAVisible;
    }

    public void setAdAVisible(Boolean adAVisible) {
        this.adAVisible = adAVisible;
    }

    public String getEstacion() {
        return estacion;
    }

    public void setEstacion(String estacion) {
        this.estacion = estacion;
    }

    public List<String> getEstacionesRecords() {
        return estacionesRecords;
    }

    public void setEstacionesRecords(List<String> estacionesRecords) {
        this.estacionesRecords = estacionesRecords;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public ExportarADPuntoProcessor getExportarDatosProcessor() {
        return exportarDatosProcessor;
    }

    public void setExportarDatosProcessor(ExportarADPuntoProcessor exportarDatosProcessor) {
        this.exportarDatosProcessor = exportarDatosProcessor;
    }

    public Boolean getBotonHabilitado() {
        return botonHabilitado;
    }

    public void setBotonHabilitado(Boolean botonHabilitado) {
        this.botonHabilitado = botonHabilitado;
    }
}
