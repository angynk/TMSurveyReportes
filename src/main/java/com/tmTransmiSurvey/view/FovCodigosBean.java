package com.tmTransmiSurvey.view;

import com.tmTransmiSurvey.controller.processor.ExportarConteoProcessor;
import com.tmTransmiSurvey.controller.processor.FovCodigosProcessor;
import com.tmTransmiSurvey.controller.util.LogDatos;
import com.tmTransmiSurvey.controller.util.TipoEncuesta;
import com.tmTransmiSurvey.controller.util.Util;
import com.tmTransmiSurvey.model.entity.apoyo.*;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import java.util.ArrayList;
import java.util.List;

@ManagedBean(name = "fovCodBean")
@SessionScoped
public class FovCodigosBean {

    private String modo;
    private List<String> modos;
    private String estacion;
    private String servicio;
    private String tipologia;
    private List<String> estacionesRecords;
    private List<String> servicios;
    private List<String> sentidos;
    private List<String> tipologias;
    private boolean visibleDatos;
    private List<FovCodigos> fovCodigosServicio;
    private List<FovCodigos> fovCodigosServicioFiltered;
    private FovCodigos selectedFov;
    private FovCodigos nuevoFov;

    @ManagedProperty(value="#{FovCodigosProcessor}")
    private FovCodigosProcessor fovCodigosProcessor;

    @ManagedProperty("#{MessagesView}")
    private MessagesView messagesView;

    public FovCodigosBean() {
    }

    @PostConstruct
    public void init() {
        modos = TipoEncuesta.listaModos();
        modo = TipoEncuesta.MODO_TRONCAL;
        estacionesRecords = convertStringList(fovCodigosProcessor.encontrarTodosLasEstaciones(Util.findModo(modo)));
        visibleDatos = false;
        sentidos = Util.obtenerSentidos();
        servicios = fovCodigosProcessor.encontrarTodasLosServicios(Util.findModo(modo));
        tipologias = fovCodigosProcessor.encontrarTipologias();
    }

    public void updateEstaciones(){
        estacionesRecords = convertStringList (fovCodigosProcessor.encontrarTodosLasEstaciones(Util.findModo(modo)));
    }

    public void buscar(){
        visibleDatos = true;
        fovCodigosServicio = fovCodigosProcessor.encontrarFovByEstacion(estacion);

    }

    public void incluirServicio(){
        nuevoFov = new FovCodigos();
    }

    public void eliminarFov(){
        fovCodigosProcessor.eliminarFovCodigo(selectedFov);
        messagesView.info("Código FOV eliminado","");
        buscar();
    }

    public void nuevofovCodigo(){
//        ServicioTs servicioTs = fovCodigosProcessor.encontrarServicioByNombre(servicio);
//        Estacion estacionTs = fovCodigosProcessor.encontrarEstacionByNombre(estacion);
        Tipologia tipologiaTs = fovCodigosProcessor.encontrarTipologiaByNombre(tipologia);
        if(servicio!=null && estacion!=null && tipologiaTs!=null){
            nuevoFov.setServicio(servicio);
            nuevoFov.setEstacion(estacion);
            nuevoFov.setTipologia(tipologiaTs);
            fovCodigosProcessor.agregarFovCodigo(nuevoFov);
            buscar();
            messagesView.info("Código FOV agregado","");
        }else{
            messagesView.error("Información incorrrecta, intente de nuevo","");
        }
    }

    public void cancelar(){

    }

    private List<String> convertStringList(List<Estacion> estaciones) {
        List<String> lista = new ArrayList<>();
        for(Estacion ser:estaciones){
            lista.add(ser.getNombre());
        }
        return lista;
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

    public FovCodigosProcessor getFovCodigosProcessor() {
        return fovCodigosProcessor;
    }

    public void setFovCodigosProcessor(FovCodigosProcessor fovCodigosProcessor) {
        this.fovCodigosProcessor = fovCodigosProcessor;
    }

    public boolean isVisibleDatos() {
        return visibleDatos;
    }

    public void setVisibleDatos(boolean visibleDatos) {
        this.visibleDatos = visibleDatos;
    }

    public List<FovCodigos> getFovCodigosServicio() {
        return fovCodigosServicio;
    }

    public void setFovCodigosServicio(List<FovCodigos> fovCodigosServicio) {
        this.fovCodigosServicio = fovCodigosServicio;
    }

    public List<FovCodigos> getFovCodigosServicioFiltered() {
        return fovCodigosServicioFiltered;
    }

    public void setFovCodigosServicioFiltered(List<FovCodigos> fovCodigosServicioFiltered) {
        this.fovCodigosServicioFiltered = fovCodigosServicioFiltered;
    }

    public FovCodigos getSelectedFov() {
        return selectedFov;
    }

    public void setSelectedFov(FovCodigos selectedFov) {
        this.selectedFov = selectedFov;
    }

    public FovCodigos getNuevoFov() {
        return nuevoFov;
    }

    public void setNuevoFov(FovCodigos nuevoFov) {
        this.nuevoFov = nuevoFov;
    }

    public String getServicio() {
        return servicio;
    }

    public void setServicio(String servicio) {
        this.servicio = servicio;
    }

    public List<String> getServicios() {
        return servicios;
    }

    public void setServicios(List<String> servicios) {
        this.servicios = servicios;
    }


    public List<String> getSentidos() {
        return sentidos;
    }

    public void setSentidos(List<String> sentidos) {
        this.sentidos = sentidos;
    }

    public MessagesView getMessagesView() {
        return messagesView;
    }

    public void setMessagesView(MessagesView messagesView) {
        this.messagesView = messagesView;
    }

    public String getTipologia() {
        return tipologia;
    }

    public void setTipologia(String tipologia) {
        this.tipologia = tipologia;
    }

    public List<String> getTipologias() {
        return tipologias;
    }

    public void setTipologias(List<String> tipologias) {
        this.tipologias = tipologias;
    }
}
