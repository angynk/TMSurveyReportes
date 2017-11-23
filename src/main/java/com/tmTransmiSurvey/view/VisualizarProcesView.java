package com.tmTransmiSurvey.view;

import com.tmTransmiSurvey.controller.TipoEncuesta;
import com.tmTransmiSurvey.controller.processor.EncuestaADAbordoProcessor;
import com.tmTransmiSurvey.controller.processor.VisualizarEstudiosProcessor;
import com.tmTransmiSurvey.model.entity.Estudio;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
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

        tablaDatos = false;
    }

    public void buscar(){
        tablaDatos = true;
        estudios = visualizarEstudiosProcessor.getEstudios(encuesta,modo);
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
}
