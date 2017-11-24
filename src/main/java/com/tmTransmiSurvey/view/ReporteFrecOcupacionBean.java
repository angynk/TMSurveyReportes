package com.tmTransmiSurvey.view;

import com.tmTransmiSurvey.controller.PathFiles;
import com.tmTransmiSurvey.controller.Util;
import com.tmTransmiSurvey.controller.processor.ExportarFrecOcupacion;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import java.io.IOException;
import java.util.Date;

@ManagedBean(name="BaseFrecOcu")
@ViewScoped
public class ReporteFrecOcupacionBean {

    private Date fechaInicio;
    private Date fechaFin;
    private boolean visibleDescarga;

    @ManagedProperty(value="#{ExportarFrecOcupacion}")
    private ExportarFrecOcupacion exportarDatosProcessor;

    @ManagedProperty("#{MessagesView}")
    private MessagesView messagesView;

    public ReporteFrecOcupacionBean() {
    }

    @PostConstruct
    public void init() {
        visibleDescarga = false;
    }

    public void exportarDatosEncuesta(){
        if(genracionValida()){
            boolean resultado= exportarDatosProcessor.exportarDatosEncuesta(fechaInicio,fechaFin);
            if(resultado) visibleDescarga = true;
        }else{

        }
    }

    private boolean genracionValida() {
        if(fechaInicio!=null && fechaFin!=null) return true;
        return false;
    }

    public void descargar(){
        String path = PathFiles.PATH+""+ PathFiles.FREC_OCU;
        try {
            Util.descargarArchivo(path,"frecuenciaOcupacion.xls");
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

    public ExportarFrecOcupacion getExportarDatosProcessor() {
        return exportarDatosProcessor;
    }

    public void setExportarDatosProcessor(ExportarFrecOcupacion exportarDatosProcessor) {
        this.exportarDatosProcessor = exportarDatosProcessor;
    }

    public MessagesView getMessagesView() {
        return messagesView;
    }

    public void setMessagesView(MessagesView messagesView) {
        this.messagesView = messagesView;
    }
}
