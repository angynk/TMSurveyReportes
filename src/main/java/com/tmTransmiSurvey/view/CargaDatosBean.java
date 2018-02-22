package com.tmTransmiSurvey.view;

import com.tmTransmiSurvey.controller.servicios.CargaDatosServicios;
import org.primefaces.model.UploadedFile;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import java.io.IOException;

@ManagedBean(name = "cargaBean")
@ViewScoped
public class CargaDatosBean {

    private UploadedFile file;
    private boolean cargaVisible;

    @ManagedProperty(value="#{CargaDatosServicios}")
    private CargaDatosServicios cargaDatosServicios;

    @ManagedProperty("#{MessagesView}")
    private MessagesView messagesView;


    public CargaDatosBean() {
    }

    @PostConstruct
    public void init(){
        cargaVisible =true;
    }

    public void actualizar(){
        if(file!=null){
            try {
                String nombre = cargaDatosServicios.copyFile(file.getFileName(),file.getInputstream());
                cargaDatosServicios.procesarFile(nombre);
                cargaVisible = false;
                messagesView.info("Carga de Información Exitosa","");
            } catch (IOException e) {
                messagesView.error("Error en la carga del archivo",e.getMessage());
                cargaVisible = false;
            } catch (Exception e) {
                messagesView.error("Error en la carga del archivo",e.getMessage());
                cargaVisible = false;
            }
        }
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public CargaDatosServicios getCargaDatosServicios() {
        return cargaDatosServicios;
    }

    public void setCargaDatosServicios(CargaDatosServicios cargaDatosServicios) {
        this.cargaDatosServicios = cargaDatosServicios;
    }

    public MessagesView getMessagesView() {
        return messagesView;
    }

    public void setMessagesView(MessagesView messagesView) {
        this.messagesView = messagesView;
    }

    public boolean isCargaVisible() {
        return cargaVisible;
    }

    public void setCargaVisible(boolean cargaVisible) {
        this.cargaVisible = cargaVisible;
    }
}
