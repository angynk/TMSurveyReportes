package com.tmTransmiSurvey.view;

import com.tmTransmiSurvey.controller.PathFiles;
import com.tmTransmiSurvey.controller.Util;
import com.tmTransmiSurvey.controller.processor.ExportarADPuntoProcessor;
import com.tmTransmiSurvey.model.entity.Estacion;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ManagedBean(name="reporteADPBean")
@ViewScoped
public class ReporteAdPuntoBean {

    private String estacion;
    private List<String> estacionesRecords;
    private Date fechaInicio;
    private Date fechaFin;
    private boolean visibleDescarga;



    @ManagedProperty(value="#{ExportarADPuntoProcessor}")
    private ExportarADPuntoProcessor exportarDatosProcessor;

    @ManagedProperty("#{MessagesView}")
    private MessagesView messagesView;

    public ReporteAdPuntoBean() {
    }

    @PostConstruct
    public void init() {
        visibleDescarga = false;
//        estacion = "B11";
        estacionesRecords = convertStringList (exportarDatosProcessor.encontrarTodosLasEstaciones());
        visibleDescarga = false;

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
            boolean resultado= exportarDatosProcessor.exportarDatosEncuesta(fechaInicio,fechaFin,estacion);
            if(resultado) visibleDescarga = true;
        }else{

        }
    }

    private boolean genracionValida() {
        if(fechaInicio!=null && fechaFin!=null) return true;
        return false;
    }

    public void descargar(){
        String path = PathFiles.PATH+""+ PathFiles.ASC_DES_PUNTO;
        try {
            Util.descargarArchivo(path,"AscDescPuntoFijo");
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

    public ExportarADPuntoProcessor getExportarDatosProcessor() {
        return exportarDatosProcessor;
    }

    public void setExportarDatosProcessor(ExportarADPuntoProcessor exportarDatosProcessor) {
        this.exportarDatosProcessor = exportarDatosProcessor;
    }

    public MessagesView getMessagesView() {
        return messagesView;
    }

    public void setMessagesView(MessagesView messagesView) {
        this.messagesView = messagesView;
    }
}
