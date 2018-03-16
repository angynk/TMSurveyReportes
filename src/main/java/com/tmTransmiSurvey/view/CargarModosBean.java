package com.tmTransmiSurvey.view;

import com.tmTransmiSurvey.controller.servicios.ConfiguracionServicio;
import com.tmTransmiSurvey.controller.util.TipoEncuesta;
import com.tmTransmiSurvey.model.entity.apoyo.Modo;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import java.util.List;

@ManagedBean(name = "cargarModos")
@ViewScoped
public class CargarModosBean {

    private List<Modo> modosRecords;
    private Modo modoSelected;
    private Modo modoNuevo;

    @ManagedProperty(value="#{ConfigService}")
    private ConfiguracionServicio configuracionServicio;

    @ManagedProperty("#{MessagesView}")
    private MessagesView messagesView;


    @PostConstruct
    public void init(){
        modosRecords = configuracionServicio.getModosAll();
    }

    public CargarModosBean() {
    }

    public void crearNuevoModo(){
        modoNuevo = new Modo();
    }

    public void actualizarModo(){
        if(configuracionServicio.updateModo(modoSelected)){
            messagesView.info("Proceso Exitoso","Modo Actualizado");
        }else{
            messagesView.error("Proceso Fallido","Verifique la información actualizada");
        }
    }

    public void crearModo(){
           if(camposCompletos()){
               if(configuracionServicio.crearModo(modoNuevo)){
                   messagesView.info("Proceso Exitoso","Modo Creado");
               }else{
                   messagesView.error("Proceso Fallido","Verifique la información actualizada");
               }
           }else{
               messagesView.error("Proceso Fallido","Complete todos los campos");
           }
    }

    private boolean camposCompletos() {

        if(modoNuevo.getAbreviatura()!=null && modoNuevo.getNombre()!=null) return true;
        return false;
    }

    public List<Modo> getModosRecords() {
        return modosRecords;
    }

    public void setModosRecords(List<Modo> modosRecords) {
        this.modosRecords = modosRecords;
    }

    public Modo getModoSelected() {
        return modoSelected;
    }

    public void setModoSelected(Modo modoSelected) {
        this.modoSelected = modoSelected;
    }

    public Modo getModoNuevo() {
        return modoNuevo;
    }

    public void setModoNuevo(Modo modoNuevo) {
        this.modoNuevo = modoNuevo;
    }

    public ConfiguracionServicio getConfiguracionServicio() {
        return configuracionServicio;
    }

    public void setConfiguracionServicio(ConfiguracionServicio configuracionServicio) {
        this.configuracionServicio = configuracionServicio;
    }

    public MessagesView getMessagesView() {
        return messagesView;
    }

    public void setMessagesView(MessagesView messagesView) {
        this.messagesView = messagesView;
    }
}
