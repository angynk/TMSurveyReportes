package com.tmTransmiSurvey.view;

import com.tmTransmiSurvey.controller.processor.ExportarConteoProcessor;
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

@ManagedBean(name="conteoBean")
@ViewScoped
public class ReporteConteoBean {

    private String estacion;
    private List<String> estacionesRecords;
    private Date fechaInicio;
    private Date fechaFin;
    private boolean visibleDescarga;
    private String modo;
    private List<String> modos;


    @ManagedProperty(value="#{ExportarConteoProcessor}")
    private ExportarConteoProcessor exportarConteoProcessor;

    @ManagedProperty("#{MessagesView}")
    private MessagesView messagesView;


    public ReporteConteoBean() {
    }

    @PostConstruct
    public void init() {
        visibleDescarga = false;
        modos = TipoEncuesta.listaModosCarga();
        modo = TipoEncuesta.MODO_TRONCAL;
        estacionesRecords = convertStringList (exportarConteoProcessor.encontrarTodosLasEstaciones(Util.findModo(modo)));
        visibleDescarga = false;

    }

    public void updateServicios(){
        estacionesRecords = convertStringList (exportarConteoProcessor.encontrarTodosLasEstaciones(Util.findModo(modo)));
    }

    private List<String> convertStringList(List<Estacion> estaciones) {
        List<String> lista = new ArrayList<>();
        for(Estacion ser:estaciones){
            lista.add(ser.getNombre());
        }
        return lista;
    }

    public void exportarDatosEncuesta(){
        if(genracionValida()){
            boolean resultado= exportarConteoProcessor.exportarDatosEncuesta(fechaInicio,fechaFin,estacion);
            if(resultado) visibleDescarga = true;
        }else{
            messagesView.error("Generación Invalida","Complete todos los campos");
        }
    }

    private boolean genracionValida() {
        if(fechaInicio!=null && fechaFin!=null && estacion!=null) return true;
        return false;
    }

    public void descargar(){
        String path = PathFiles.PATH+""+ PathFiles.CO_DESPACHO;
        try {
            Util.descargarArchivo(path,"ConteoDespachos.xls");
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    public ExportarConteoProcessor getExportarConteoProcessor() {
        return exportarConteoProcessor;
    }

    public void setExportarConteoProcessor(ExportarConteoProcessor exportarConteoProcessor) {
        this.exportarConteoProcessor = exportarConteoProcessor;
    }

    public MessagesView getMessagesView() {
        return messagesView;
    }

    public void setMessagesView(MessagesView messagesView) {
        this.messagesView = messagesView;
    }
}
