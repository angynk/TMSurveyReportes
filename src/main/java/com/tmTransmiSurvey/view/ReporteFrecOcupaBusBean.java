package com.tmTransmiSurvey.view;

import com.tmTransmiSurvey.controller.processor.ExportarFOBusProcessor;
import com.tmTransmiSurvey.controller.processor.ExportarFrecOcupacion;
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

@ManagedBean(name="FrecOcuBus")
@ViewScoped
public class ReporteFrecOcupaBusBean {

    private Date fechaInicio;
    private Date fechaFin;
    private boolean visibleDescarga;
    private String modo;
    private List<String> modos;
    private String estacion;
    private List<String> estacionesRecords;

    @ManagedProperty(value="#{ExportarFOBusProcessor}")
    private ExportarFOBusProcessor exportarFOBusProcessor;

    @ManagedProperty("#{MessagesView}")
    private MessagesView messagesView;

    public ReporteFrecOcupaBusBean() {
    }

    @PostConstruct
    public void init() {
        visibleDescarga = false;
        modos = TipoEncuesta.listaModosCarga();
        modo = TipoEncuesta.MODO_TRONCAL;
        estacionesRecords = convertStringList (exportarFOBusProcessor.encontrarTodosLasEstaciones(Util.findModo(modo)));
    }

    public void exportarDatosEncuesta(){
        if(genracionValida()){
            boolean resultado= exportarFOBusProcessor.exportarDatosEncuesta(fechaInicio,fechaFin);
            if(resultado) visibleDescarga = true;
        }else{

        }
    }

    public void updateEstaciones(){
        estacionesRecords = convertStringList (exportarFOBusProcessor.encontrarTodosLasEstaciones(Util.findModo(modo)));
    }

    private boolean genracionValida() {
        if(fechaInicio!=null && fechaFin!=null) return true;
        return false;
    }

    public void descargar(){
        String path = PathFiles.PATH+""+ PathFiles.FO_BUS;
        try {
            Util.descargarArchivo(path,"frecuenciaOcupacionBus.xls");
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    public ExportarFOBusProcessor getExportarFOBusProcessor() {
        return exportarFOBusProcessor;
    }

    public void setExportarFOBusProcessor(ExportarFOBusProcessor exportarFOBusProcessor) {
        this.exportarFOBusProcessor = exportarFOBusProcessor;
    }

    public MessagesView getMessagesView() {
        return messagesView;
    }

    public void setMessagesView(MessagesView messagesView) {
        this.messagesView = messagesView;
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

    private List<String> convertStringList(List<Estacion> estaciones) {
        List<String> lista = new ArrayList<>();
        for(Estacion ser:estaciones){
            lista.add(ser.getNombre());
        }
        return lista;
    }
}
