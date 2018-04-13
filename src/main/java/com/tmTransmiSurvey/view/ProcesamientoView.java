package com.tmTransmiSurvey.view;

import com.tmTransmiSurvey.controller.processor.*;
import com.tmTransmiSurvey.controller.util.LogDatos;
import com.tmTransmiSurvey.controller.util.TipoEncuesta;
import com.tmTransmiSurvey.controller.util.TipoLog;
import com.tmTransmiSurvey.controller.util.Util;
import com.tmTransmiSurvey.model.entity.apoyo.ServicioTs;
import com.tmTransmiSurvey.model.entity.apoyo.Tipologia;

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
    private Boolean frecOcuVisual;
    private Boolean frecOcuBus;
    private Boolean botonHabilitado;
    private List<LogDatos> logDatos;
    private boolean resultadosVisibles;

    private String identificadorEstudio;
    private String identificadorEstudioFOV;
    private String identificadorEstudioFOB;
    private String estacion;
    private String estacionFOB;
    private String servicio;
    private List<String> estacionesRecords;
    private List<String> serviciosRecords;
    private Date fechaInicio;
    private Date fechaInicioFOV;
    private Date fechaInicioFOB;
    private Date fechaFin;
    private Date fechaFinFOV;
    private Date fechaFinFOB;

    @ManagedProperty(value="#{ExportarADPuntoProcessor}")
    private ExportarADPuntoProcessor exportarDatosProcessor;

    @ManagedProperty(value="#{EncuestaADAbordoProcessor}")
    private EncuestaADAbordoProcessor encuestaADAbordoProcessor;

    @ManagedProperty(value="#{VisualizarEstudiosProcessor}")
    private VisualizarEstudiosProcessor visualizarEstudiosProcessor;

    @ManagedProperty(value="#{EncuestaFOVProcessor}")
    private EncuestaFOVProcessor encuestaFOVProcessor;

    @ManagedProperty(value="#{EncuestaFOBProcessor}")
    private EncuestaFOBProcessor encuestaFOBProcessor;



    @ManagedProperty("#{MessagesView}")
    private MessagesView messagesView;

    public ProcesamientoView() {
    }

    @PostConstruct
    public void init() {

        encuestas = TipoEncuesta.listaEncuestas();
        encuesta = TipoEncuesta.ENCUESTA_ASC_DESC_ABORDO;
        modos = TipoEncuesta.listaModosCarga();
        modo = TipoEncuesta.MODO_TRONCAL;
        logDatos = new ArrayList<LogDatos>();
        resultadosVisibles = false;
        adAVisible = false;
        frecOcuVisual = false;
        frecOcuBus = false;
        botonHabilitado = false;
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
            frecOcuVisual = false;
            botonHabilitado = true;
            serviciosRecords = Util.convertStringList (exportarDatosProcessor.encontrarTodosLosServicios());
        }else if (encuesta.equals(TipoEncuesta.ENCUESTA_FREC_OCUPACION)){
            adAVisible = false;
            frecOcuVisual = true;
            estacionesRecords = Util.convertStringListEstaciones (exportarDatosProcessor.encontrarTodosLasEstaciones(Util.findModo(modo)));
        }

    }




    public void procesarDatosEncuesta(){
        boolean procesamientoExitoso = false;
        if(encuesta.equals(TipoEncuesta.ENCUESTA_ASC_DESC_ABORDO)){
            if(datosCompletos()){
                logDatos =  encuestaADAbordoProcessor.procesarDatosEncuesta(fechaInicio,fechaFin,servicio,modo,identificadorEstudio);
                procesamientoExitoso = encuestaADAbordoProcessor.isProcesamientoExitoso();
            }

        }else if(encuesta.equals(TipoEncuesta.ENCUESTA_FREC_OCUPACION)){
            if(datosCompletos()){
                logDatos = encuestaFOVProcessor.procesarDatosEncuesta(fechaInicioFOV,fechaFinFOV,identificadorEstudioFOV,modo,estacion);
                procesamientoExitoso = encuestaFOVProcessor.isProcesamientoValido();
            }

        }else if (encuesta.equals(TipoEncuesta.ENCUESTA_FREC_OCUPACION_NUM_BUS)){
            if(datosCompletos()){
                logDatos = encuestaFOBProcessor.procesarDatosEncuesta(fechaInicioFOB,fechaFinFOB,identificadorEstudioFOB,modo,estacion);
            }
        }

        if(procesamientoExitoso){
            messagesView.info("Procesamiento Existoso","");
        }else{
            messagesView.error("Procesamiento Fallo","Revisar Log");
        }
        resultadosVisibles = true;
    }

    private boolean datosCompletos() {
        if(encuesta.equals(TipoEncuesta.ENCUESTA_ASC_DESC_ABORDO) ){
            if( fechaInicio!=null && fechaFin!=null && identificadorEstudio!=null) return true;
        }else if (  encuesta.equals(TipoEncuesta.ENCUESTA_FREC_OCUPACION) ){
            if( fechaInicioFOV!=null && fechaFinFOV!=null && identificadorEstudioFOV!=null) return true;
        }else if ( encuesta.equals(TipoEncuesta.ENCUESTA_FREC_OCUPACION_NUM_BUS)){
            if( fechaFinFOB!=null && fechaFinFOB!=null && identificadorEstudioFOB!=null) return true;
        }
        logDatos.add(new LogDatos("Información incompleta", TipoLog.ERROR));
        return false;
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

    public EncuestaADAbordoProcessor getEncuestaADAbordoProcessor() {
        return encuestaADAbordoProcessor;
    }

    public void setEncuestaADAbordoProcessor(EncuestaADAbordoProcessor encuestaADAbordoProcessor) {
        this.encuestaADAbordoProcessor = encuestaADAbordoProcessor;
    }


    public MessagesView getMessagesView() {
        return messagesView;
    }

    public void setMessagesView(MessagesView messagesView) {
        this.messagesView = messagesView;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getIdentificadorEstudio() {
        return identificadorEstudio;
    }

    public void setIdentificadorEstudio(String identificadorEstudio) {
        this.identificadorEstudio = identificadorEstudio;
    }

    public List<LogDatos> getLogDatos() {
        return logDatos;
    }

    public void setLogDatos(List<LogDatos> logDatos) {
        this.logDatos = logDatos;
    }

    public boolean isResultadosVisibles() {
        return resultadosVisibles;
    }

    public void setResultadosVisibles(boolean resultadosVisibles) {
        this.resultadosVisibles = resultadosVisibles;
    }

    public VisualizarEstudiosProcessor getVisualizarEstudiosProcessor() {
        return visualizarEstudiosProcessor;
    }

    public void setVisualizarEstudiosProcessor(VisualizarEstudiosProcessor visualizarEstudiosProcessor) {
        this.visualizarEstudiosProcessor = visualizarEstudiosProcessor;
    }

    public EncuestaFOVProcessor getEncuestaFOVProcessor() {
        return encuestaFOVProcessor;
    }

    public void setEncuestaFOVProcessor(EncuestaFOVProcessor encuestaFOVProcessor) {
        this.encuestaFOVProcessor = encuestaFOVProcessor;
    }

    public Boolean getFrecOcuVisual() {
        return frecOcuVisual;
    }

    public void setFrecOcuVisual(Boolean frecOcuVisual) {
        this.frecOcuVisual = frecOcuVisual;
    }

    public Date getFechaInicioFOV() {
        return fechaInicioFOV;
    }

    public void setFechaInicioFOV(Date fechaInicioFOV) {
        this.fechaInicioFOV = fechaInicioFOV;
    }

    public String getIdentificadorEstudioFOV() {
        return identificadorEstudioFOV;
    }

    public void setIdentificadorEstudioFOV(String identificadorEstudioFOV) {
        this.identificadorEstudioFOV = identificadorEstudioFOV;
    }

    public Date getFechaFinFOV() {
        return fechaFinFOV;
    }

    public void setFechaFinFOV(Date fechaFinFOV) {
        this.fechaFinFOV = fechaFinFOV;
    }

    public String getServicio() {
        return servicio;
    }

    public void setServicio(String servicio) {
        this.servicio = servicio;
    }

    public List<String> getServiciosRecords() {
        return serviciosRecords;
    }

    public void setServiciosRecords(List<String> serviciosRecords) {
        this.serviciosRecords = serviciosRecords;
    }

    public Boolean getFrecOcuBus() {
        return frecOcuBus;
    }

    public void setFrecOcuBus(Boolean frecOcuBus) {
        this.frecOcuBus = frecOcuBus;
    }

    public Date getFechaInicioFOB() {
        return fechaInicioFOB;
    }

    public void setFechaInicioFOB(Date fechaInicioFOB) {
        this.fechaInicioFOB = fechaInicioFOB;
    }

    public Date getFechaFinFOB() {
        return fechaFinFOB;
    }

    public void setFechaFinFOB(Date fechaFinFOB) {
        this.fechaFinFOB = fechaFinFOB;
    }

    public String getIdentificadorEstudioFOB() {
        return identificadorEstudioFOB;
    }

    public void setIdentificadorEstudioFOB(String identificadorEstudioFOB) {
        this.identificadorEstudioFOB = identificadorEstudioFOB;
    }

    public String getEstacionFOB() {
        return estacionFOB;
    }

    public void setEstacionFOB(String estacionFOB) {
        this.estacionFOB = estacionFOB;
    }

    public EncuestaFOBProcessor getEncuestaFOBProcessor() {
        return encuestaFOBProcessor;
    }

    public void setEncuestaFOBProcessor(EncuestaFOBProcessor encuestaFOBProcessor) {
        this.encuestaFOBProcessor = encuestaFOBProcessor;
    }
}
