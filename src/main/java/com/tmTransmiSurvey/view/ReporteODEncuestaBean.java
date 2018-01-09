package com.tmTransmiSurvey.view;

import com.tmTransmiSurvey.controller.processor.ExportarODProcessor;
import com.tmTransmiSurvey.controller.util.PathFiles;
import com.tmTransmiSurvey.controller.util.TipoEncuesta;
import com.tmTransmiSurvey.controller.util.Util;
import com.tmTransmiSurvey.model.entity.apoyo.Estacion;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ManagedBean(name="odView")
@ViewScoped
public class ReporteODEncuestaBean {

    private String estacion;
    private List<String> estacionesRecords;
    private Date fechaInicio;
    private Date fechaFin;
    private boolean visibleDescarga;
    private String modo;
    private List<String> modos;

    @ManagedProperty("#{MessagesView}")
    private MessagesView messagesView;

    @ManagedProperty(value="#{ExportarODProcessor}")
    private ExportarODProcessor exportarODProcessor;

    @PostConstruct
    public void init() {
        visibleDescarga = false;
        estacion = "s";
        modos = TipoEncuesta.listaModos();
        modo = TipoEncuesta.MODO_TRONCAL;
        estacionesRecords = convertStringList (exportarODProcessor.encontrarTodosLasEstaciones(validarModo(modo)));

    }

    public void updateEstaciones(){
        estacionesRecords = convertStringList (exportarODProcessor.encontrarTodosLasEstaciones(validarModo(modo)));
    }

    public void exportarDatosEncuesta(){
        if(genracionValida()){
            boolean resultado= exportarODProcessor.exportarDatosEncuesta(fechaInicio,fechaFin,estacion);
            if(resultado){
                visibleDescarga = true;
            }
        }else{

        }
    }

    private boolean genracionValida() {
        if(fechaInicio!=null && fechaFin!=null) return true;
        return false;
    }

    public void descargar(){
        String path = PathFiles.PATH+""+ PathFiles.OD_ENCUESTA;
        try {
            Util.descargarArchivo(path,"OrigenDestino.xls");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String validarModo(String modo) {
        if(modo.equals(TipoEncuesta.MODO_TRONCAL)) return "tro";
        return "ali";
    }

    public ReporteODEncuestaBean() {
    }

    private List<String> convertStringList(List<Estacion> servicioTs) {
        List<String> lista = new ArrayList<>();
        for(Estacion ser:servicioTs){
            lista.add(ser.getNombre());
        }
        return lista;
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

    public boolean isVisibleDescarga() {
        return visibleDescarga;
    }

    public void setVisibleDescarga(boolean visibleDescarga) {
        this.visibleDescarga = visibleDescarga;
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

    public MessagesView getMessagesView() {
        return messagesView;
    }

    public void setMessagesView(MessagesView messagesView) {
        this.messagesView = messagesView;
    }

    public ExportarODProcessor getExportarODProcessor() {
        return exportarODProcessor;
    }

    public void setExportarODProcessor(ExportarODProcessor exportarODProcessor) {
        this.exportarODProcessor = exportarODProcessor;
    }
}
