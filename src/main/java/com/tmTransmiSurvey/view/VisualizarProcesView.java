package com.tmTransmiSurvey.view;

import com.tmTransmiSurvey.controller.PathFiles;
import com.tmTransmiSurvey.controller.TipoEncuesta;
import com.tmTransmiSurvey.controller.Util;
import com.tmTransmiSurvey.controller.processor.EncuestaADAbordoProcessor;
import com.tmTransmiSurvey.controller.processor.VisualizarEstudiosProcessor;
import com.tmTransmiSurvey.model.entity.Estudio;
import org.primefaces.context.RequestContext;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@ManagedBean(name="viewProView")
@ViewScoped
public class VisualizarProcesView {


    private String encuesta;
    private List<String> encuestas;
    private String modo;
    private List<String> modos;
    private Boolean tablaDatos;
    private List<Estudio> estudios;
    private Estudio selectedEstudio;
    private boolean visibleDescarga;
    private String nombreArchivo;

    @ManagedProperty(value="#{VisualizarEstudiosProcessor}")
    private VisualizarEstudiosProcessor visualizarEstudiosProcessor;



    public VisualizarProcesView() {

    }

    @PostConstruct
    public void init() {

        encuestas = TipoEncuesta.listaEncuestas();
        encuesta = TipoEncuesta.ENCUESTA_ASC_DESC_ABORDO;
        modos = TipoEncuesta.listaModos();
        modo = TipoEncuesta.MODO_TRONCAL;
        estudios = new ArrayList<>();
        visibleDescarga = false;
        tablaDatos = false;
        nombreArchivo = null;
    }

    public void buscar(){
        tablaDatos = true;
        estudios = visualizarEstudiosProcessor.getEstudios(encuesta,modo);
    }

    public boolean esVisible(){
        if(nombreArchivo!=null) return true;
        return false;
    }

    public void procesarExcel(){
        nombreArchivo = null;
        if(selectedEstudio!=null){

            nombreArchivo = visualizarEstudiosProcessor.exportarEstudio(selectedEstudio);
            if(nombreArchivo!=null){
                visibleDescarga = true;
            }
        }


    }



    public void descargar(){
//        RequestContext.getCurrentInstance().execute("PF('dlg2').hide();");
        if(nombreArchivo!=null){
            String path = PathFiles.PATH+""+ nombreArchivo;
            try {
                Util.descargarArchivo(path,nombreArchivo);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        RequestContext.getCurrentInstance().execute("PF('dlg3').hide();");
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

    public Boolean getTablaDatos() {
        return tablaDatos;
    }

    public void setTablaDatos(Boolean tablaDatos) {
        this.tablaDatos = tablaDatos;
    }

    public List<Estudio> getEstudios() {
        return estudios;
    }

    public void setEstudios(List<Estudio> estudios) {
        this.estudios = estudios;
    }

    public VisualizarEstudiosProcessor getVisualizarEstudiosProcessor() {
        return visualizarEstudiosProcessor;
    }

    public void setVisualizarEstudiosProcessor(VisualizarEstudiosProcessor visualizarEstudiosProcessor) {
        this.visualizarEstudiosProcessor = visualizarEstudiosProcessor;
    }

    public Estudio getSelectedEstudio() {
        return selectedEstudio;
    }

    public void setSelectedEstudio(Estudio selectedEstudio) {
        this.selectedEstudio = selectedEstudio;
    }

    public boolean isVisibleDescarga() {
        return visibleDescarga;
    }

    public void setVisibleDescarga(boolean visibleDescarga) {
        this.visibleDescarga = visibleDescarga;
    }
}
