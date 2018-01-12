package com.tmTransmiSurvey.view;

import com.tmTransmiSurvey.controller.processor.ExportarFOBusProcessor;
import com.tmTransmiSurvey.controller.processor.ExportarFrecOcupacion;
import com.tmTransmiSurvey.controller.util.PathFiles;
import com.tmTransmiSurvey.controller.util.Util;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import java.io.IOException;
import java.util.Date;

@ManagedBean(name="FrecOcuBus")
@ViewScoped
public class ReporteFrecOcupaBusBean {

    private Date fechaInicio;
    private Date fechaFin;
    private boolean visibleDescarga;

    @ManagedProperty(value="#{ExportarFOBusProcessor}")
    private ExportarFOBusProcessor exportarFOBusProcessor;

    @ManagedProperty("#{MessagesView}")
    private MessagesView messagesView;

    public ReporteFrecOcupaBusBean() {
    }

    @PostConstruct
    public void init() {
        visibleDescarga = false;
    }

    public void exportarDatosEncuesta(){
        if(genracionValida()){
            boolean resultado= exportarFOBusProcessor.exportarDatosEncuesta(fechaInicio,fechaFin);
            if(resultado) visibleDescarga = true;
        }else{

        }
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
}
