package com.tmTransmiSurvey.view;

import com.tmTransmiSurvey.controller.servicios.CargaDatosServicios;
import org.primefaces.model.UploadedFile;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import java.io.IOException;

@ManagedBean(name = "cargaBean")
@SessionScoped
public class CargaDatosBean {

    private UploadedFile file;

    @ManagedProperty(value="#{CargaDatosServicios}")
    private CargaDatosServicios cargaDatosServicios;

    @ManagedProperty("#{MessagesView}")
    private MessagesView messagesView;


    public CargaDatosBean() {
    }

    public void actualizar(){
        if(file!=null){
            try {
                String nombre = cargaDatosServicios.copyFile(file.getFileName(),file.getInputstream());
                cargaDatosServicios.procesarFile(nombre);
            } catch (IOException e) {
                messagesView.error("Error en la carga del archivo",e.getMessage());
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
}
