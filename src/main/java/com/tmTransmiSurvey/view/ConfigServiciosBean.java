package com.tmTransmiSurvey.view;


import com.tmTransmiSurvey.controller.processor.ExportarADPuntoProcessor;
import com.tmTransmiSurvey.controller.servicios.ConfiguracionServicio;
import com.tmTransmiSurvey.controller.servicios.ServicioEstacionServicio;
import com.tmTransmiSurvey.controller.util.ProcessorUtils;
import com.tmTransmiSurvey.controller.util.Util;
import com.tmTransmiSurvey.model.entity.apoyo.Estacion;
import com.tmTransmiSurvey.model.entity.apoyo.Modo;
import com.tmTransmiSurvey.model.entity.apoyo.ServicioEstacion;
import com.tmTransmiSurvey.model.entity.apoyo.ServicioTs;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@ManagedBean(name = "configServicios")
@SessionScoped
public class ConfigServiciosBean {

    private String modo;
    private Modo modoObjeto;
    private List<String> modos;
    private List<Modo> modosObj;
    private boolean visiblePanel;
    private List<ServicioTs> serviciosRecords;
    private List<ServicioTs> filteredServiciosRecords;
    private ServicioTs servicioNuevo;
    private ServicioTs servicioSeleccionado;
    private List<ServicioEstacion> serviciosEstacionesRecords;
    private List<ServicioEstacion> filteredServiciosEstacionesRecords;
    private ServicioEstacion servicioEstacionSeleccionado;
    private ServicioEstacion servicioEstacionNuevo;
    private String estacion;
    private List<String> estacionesRecords;

    @ManagedProperty(value="#{ConfigService}")
    private ConfiguracionServicio configuracionServicio;


    @ManagedProperty(value="#{ServicioEstacionServicio}")
    private ServicioEstacionServicio servicioEstacionServicio;

    @ManagedProperty(value="#{ExportarADPuntoProcessor}")
    private ExportarADPuntoProcessor exportarDatosProcessor;

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
                filteredServiciosRecords = new ArrayList<>();
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

    public void actualizarDatos(){
        try {
            ProcessorUtils.updateAPIServicios(modoObjeto.getAbreviatura());
            messagesView.info("Proceso Exitoso","Los datos base fueron actualizados");
        } catch (IOException e) {
            messagesView.error("Proceso Fallido","Los datos base no pudieron ser actualizados");

        }
    }

    public void verDetalleServicios(){
        filteredServiciosEstacionesRecords = new ArrayList<>();
        serviciosEstacionesRecords =servicioEstacionServicio.estacionesDelServicio(servicioSeleccionado);
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        try {
            ec.redirect(ec.getRequestContextPath()
                    + "/secured/configServiciosEstaciones.xhtml");

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void eliminarEstacion(){
        if(servicioEstacionSeleccionado!=null){
            servicioEstacionServicio.eliminarServicioEstacion(servicioEstacionSeleccionado);
            messagesView.info("Proceso Exitoso","La estación fue eliminada");
            serviciosEstacionesRecords =servicioEstacionServicio.estacionesDelServicio(servicioSeleccionado);
        }else{
            messagesView.error("Proceso Fallido","Seleccione la estación a eliminar");
        }

    }

    public void crearNuevoServicioEstacion(){

        if(ordenNoExisteOEstacion()){
            servicioEstacionServicio.addNuevoServicioEstacion(estacion,servicioEstacionNuevo,servicioSeleccionado);
            messagesView.info("Proceso Exitoso","Nueva Estación Agregada");
            serviciosEstacionesRecords =servicioEstacionServicio.estacionesDelServicio(servicioSeleccionado);
        }else{
            messagesView.error("Proceso Fallido","Ya existe la estación y/o el orden");
        }

    }

    private boolean ordenNoExisteOEstacion() {
        for(ServicioEstacion servicioEstacion:serviciosEstacionesRecords){
            if(servicioEstacion.getEstacion().getNombre().equals(estacion)){
                return false;
            }
            if(servicioEstacion.getOrden()==servicioEstacionNuevo.getOrden()){
                return false;
            }
        }
        return true;
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
        servicioEstacionNuevo = new ServicioEstacion();
        estacionesRecords = convertStringList (exportarDatosProcessor.encontrarTodosLasEstaciones(modoObjeto.getAbreviatura()));
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

    public ServicioEstacion getServicioEstacionNuevo() {
        return servicioEstacionNuevo;
    }

    public void setServicioEstacionNuevo(ServicioEstacion servicioEstacionNuevo) {
        this.servicioEstacionNuevo = servicioEstacionNuevo;
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

    public ExportarADPuntoProcessor getExportarDatosProcessor() {
        return exportarDatosProcessor;
    }

    public void setExportarDatosProcessor(ExportarADPuntoProcessor exportarDatosProcessor) {
        this.exportarDatosProcessor = exportarDatosProcessor;
    }

    public List<ServicioTs> getFilteredServiciosRecords() {
        return filteredServiciosRecords;
    }

    public void setFilteredServiciosRecords(List<ServicioTs> filteredServiciosRecords) {
        this.filteredServiciosRecords = filteredServiciosRecords;
    }

    public List<ServicioEstacion> getFilteredServiciosEstacionesRecords() {
        return filteredServiciosEstacionesRecords;
    }

    public void setFilteredServiciosEstacionesRecords(List<ServicioEstacion> filteredServiciosEstacionesRecords) {
        this.filteredServiciosEstacionesRecords = filteredServiciosEstacionesRecords;
    }
}
