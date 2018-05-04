package com.tmTransmiSurvey.view;


import com.tmTransmiSurvey.controller.servicios.ConfiguracionServicio;
import com.tmTransmiSurvey.controller.servicios.ServicioEstacionServicio;
import com.tmTransmiSurvey.model.entity.apoyo.Estacion;
import com.tmTransmiSurvey.model.entity.apoyo.Modo;
import com.tmTransmiSurvey.model.entity.apoyo.ServicioTs;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import java.util.ArrayList;
import java.util.List;

@ManagedBean(name = "configEstaciones")
@ViewScoped
public class ConfigEstacionesBean {

    private String modo;
    private Modo modoObjeto;
    private List<String> modos;
    private List<Modo> modosObj;
    private boolean visiblePanel;
    private List<Estacion> estacionesRecords;
    private Estacion estacionNuevo;
    private Estacion estacionSeleccionado;


    @ManagedProperty(value="#{ConfigService}")
    private ConfiguracionServicio configuracionServicio;


    @ManagedProperty(value="#{ServicioEstacionServicio}")
    private ServicioEstacionServicio servicioEstacionServicio;

    @ManagedProperty("#{MessagesView}")
    private MessagesView messagesView;

    public ConfigEstacionesBean() {
    }

    @PostConstruct
    public void init(){
        modosObj = configuracionServicio.getModosAll();
        modos = convertirLista(modosObj);
        visiblePanel=false;
    }

    private List<String> convertirLista(List<Modo> modosObj) {
        List<String> lista = new ArrayList<>();
        for(Modo modo:modosObj){
            lista.add(modo.getNombre());
        }
        return lista;
    }

    public void buscarEstaciones(){
        if(modo!=null){
            modoObjeto = obtenerModo();
            estacionesRecords = servicioEstacionServicio.encontrarTodasLasEstaciones(modoObjeto.getAbreviatura());
            visiblePanel =true;
        }

    }

    private Modo obtenerModo() {
        for(Modo mod:modosObj){
            if(mod.getNombre().equals(modo)){
                return mod;
            }
        }
        return null;
    }

    public void crearEstacion(){
        estacionNuevo = new Estacion();
    }

    public void actualizarEstacion(){
        servicioEstacionServicio.updateEstacion(estacionSeleccionado);
        messagesView.info("Proceso Exitoso","La estación fue actualizada");
    }

    public void eliminarEstacion(){
        if(!servicioEstacionServicio.laEstacionEstaAsociada(estacionSeleccionado)){
            servicioEstacionServicio.eliminarEstacion(estacionSeleccionado);
            estacionesRecords = servicioEstacionServicio.encontrarTodasLasEstaciones(modoObjeto.getAbreviatura());
            messagesView.info("Proceso Exitoso","La estación fue eliminado");
        }else{
            messagesView.error("Proceso Fallido","La estación esta asociada a una servicio , elimine primero las asociaciones");
        }
    }

    public void crearNuevoEstacion(){
        estacionNuevo.setModo(modoObjeto.getAbreviatura());
        servicioEstacionServicio.addEstacion(estacionNuevo);
        estacionesRecords = servicioEstacionServicio.encontrarTodasLasEstaciones(modoObjeto.getAbreviatura());
        messagesView.info("Proceso Exitoso","La estación fue creada");
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

    public boolean isVisiblePanel() {
        return visiblePanel;
    }

    public void setVisiblePanel(boolean visiblePanel) {
        this.visiblePanel = visiblePanel;
    }

    public ConfiguracionServicio getConfiguracionServicio() {
        return configuracionServicio;
    }

    public void setConfiguracionServicio(ConfiguracionServicio configuracionServicio) {
        this.configuracionServicio = configuracionServicio;
    }

    public Modo getModoObjeto() {
        return modoObjeto;
    }

    public void setModoObjeto(Modo modoObjeto) {
        this.modoObjeto = modoObjeto;
    }

    public List<Modo> getModosObj() {
        return modosObj;
    }

    public void setModosObj(List<Modo> modosObj) {
        this.modosObj = modosObj;
    }

    public List<Estacion> getEstacionesRecords() {
        return estacionesRecords;
    }

    public void setEstacionesRecords(List<Estacion> estacionesRecords) {
        this.estacionesRecords = estacionesRecords;
    }

    public Estacion getEstacionNuevo() {
        return estacionNuevo;
    }

    public void setEstacionNuevo(Estacion estacionNuevo) {
        this.estacionNuevo = estacionNuevo;
    }

    public Estacion getEstacionSeleccionado() {
        return estacionSeleccionado;
    }

    public void setEstacionSeleccionado(Estacion estacionSeleccionado) {
        this.estacionSeleccionado = estacionSeleccionado;
    }

    public ServicioEstacionServicio getServicioEstacionServicio() {
        return servicioEstacionServicio;
    }

    public void setServicioEstacionServicio(ServicioEstacionServicio servicioEstacionServicio) {
        this.servicioEstacionServicio = servicioEstacionServicio;
    }

    public MessagesView getMessagesView() {
        return messagesView;
    }

    public void setMessagesView(MessagesView messagesView) {
        this.messagesView = messagesView;
    }
}
