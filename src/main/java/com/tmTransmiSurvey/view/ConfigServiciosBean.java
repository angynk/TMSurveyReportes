package com.tmTransmiSurvey.view;


import com.tmTransmiSurvey.controller.servicios.ConfiguracionServicio;
import com.tmTransmiSurvey.controller.servicios.ServicioEstacionServicio;
import com.tmTransmiSurvey.model.entity.apoyo.Modo;
import com.tmTransmiSurvey.model.entity.apoyo.ServicioEstacion;
import com.tmTransmiSurvey.model.entity.apoyo.ServicioTs;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@ManagedBean(name = "configServicios")
@ViewScoped
public class ConfigServiciosBean {

    private String modo;
    private Modo modoObjeto;
    private List<String> modos;
    private List<Modo> modosObj;
    private boolean visiblePanel;
    private List<ServicioTs> serviciosRecords;
    private ServicioTs servicioNuevo;
    private ServicioTs servicioSeleccionado;
    private List<ServicioEstacion> serviciosEstacionesRecords;
    private ServicioEstacion servicioEstacionSeleccionado;

    @ManagedProperty(value="#{ConfigService}")
    private ConfiguracionServicio configuracionServicio;


    @ManagedProperty(value="#{ServicioEstacionServicio}")
    private ServicioEstacionServicio servicioEstacionServicio;

    @ManagedProperty("#{MessagesView}")
    private MessagesView messagesView;

    public ConfigServiciosBean() {
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

    public void buscarServicios(){
            if(modo!=null){
                modoObjeto = obtenerModo();
                serviciosRecords = servicioEstacionServicio.encontrarTodosLosServicios(modoObjeto.getAbreviatura());
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

    public void verDetalleServicios(){
           serviciosEstacionesRecords =servicioEstacionServicio.estacionesDelServicio(servicioSeleccionado);
    }

    public void eliminarEstacion(){

    }

    public void atras(){

        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        try {
            ec.redirect(ec.getRequestContextPath()
                    + "/secured/configServicios.xhtml");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public void crearServicio(){
        servicioNuevo = new ServicioTs();
    }

    public void actualizarServicio(){
            servicioEstacionServicio.updateServicio(servicioSeleccionado);
        messagesView.info("Proceso Exitoso","El servicio fue actualizado");
    }

    public void eliminarServicio(){
            if(!servicioEstacionServicio.elServicioEstaAsociado(servicioSeleccionado)){
                servicioEstacionServicio.eliminarServicio(servicioSeleccionado);
                serviciosRecords = servicioEstacionServicio.encontrarTodosLosServicios(modoObjeto.getAbreviatura());
                messagesView.info("Proceso Exitoso","El servicio fue eliminado");
            }else{
                messagesView.error("Proceso Fallido","El servicio esta asociado a una estación , elimine primero las asociaciones");
            }
    }

    public void crearNuevoServicio(){
        servicioNuevo.setTipo(modoObjeto.getAbreviatura());
        servicioEstacionServicio.addServicio(servicioNuevo);
        serviciosRecords = servicioEstacionServicio.encontrarTodosLosServicios(modoObjeto.getAbreviatura());
        messagesView.info("Proceso Exitoso","El servicio fue creado");
    }

    public void agregarEstacion(){

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

    public List<ServicioTs> getServiciosRecords() {
        return serviciosRecords;
    }

    public void setServiciosRecords(List<ServicioTs> serviciosRecords) {
        this.serviciosRecords = serviciosRecords;
    }

    public ServicioTs getServicioNuevo() {
        return servicioNuevo;
    }

    public void setServicioNuevo(ServicioTs servicioNuevo) {
        this.servicioNuevo = servicioNuevo;
    }

    public ServicioTs getServicioSeleccionado() {
        return servicioSeleccionado;
    }

    public void setServicioSeleccionado(ServicioTs servicioSeleccionado) {
        this.servicioSeleccionado = servicioSeleccionado;
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

    public List<ServicioEstacion> getServiciosEstacionesRecords() {
        return serviciosEstacionesRecords;
    }

    public void setServiciosEstacionesRecords(List<ServicioEstacion> serviciosEstacionesRecords) {
        this.serviciosEstacionesRecords = serviciosEstacionesRecords;
    }

    public ServicioEstacion getServicioEstacionSeleccionado() {
        return servicioEstacionSeleccionado;
    }

    public void setServicioEstacionSeleccionado(ServicioEstacion servicioEstacionSeleccionado) {
        this.servicioEstacionSeleccionado = servicioEstacionSeleccionado;
    }
}
