package com.tmTransmiSurvey.view;

import com.tmTransmiSurvey.controller.servicios.CargaDatosServicios;
import com.tmTransmiSurvey.controller.servicios.ConfiguracionServicio;
import com.tmTransmiSurvey.controller.util.TipoEncuesta;
import com.tmTransmiSurvey.controller.util.Util;
import com.tmTransmiSurvey.model.entity.apoyo.Modo;
import org.primefaces.model.UploadedFile;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

@ManagedBean(name = "cargaBean")
@ViewScoped
public class CargaDatosBean {

    private UploadedFile file;
    private boolean cargaVisible;
    private Modo modo;
    private List<Modo> modos;
    private String tipoDato;
    private List<String> tipoDatos;

    @ManagedProperty(value="#{CargaDatosServicios}")
    private CargaDatosServicios cargaDatosServicios;

    @ManagedProperty("#{MessagesView}")
    private MessagesView messagesView;

    @ManagedProperty(value="#{ConfigService}")
    private ConfiguracionServicio configuracionServicio;


    public CargaDatosBean() {
    }

    @PostConstruct
    public void init(){
        cargaVisible =true;
        modos = configuracionServicio.getModosAll();

    }

    public void actualizar(){
        if(file!=null){
            try {
                String nombre = cargaDatosServicios.copyFile(file.getFileName(),file.getInputstream());
                cargaDatosServicios.procesarFile(nombre, modo.getAbreviatura());
                cargaVisible = false;
                updateAPIServicios();
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

    private void updateAPIServicios() throws IOException {

        String url = "http://35.226.255.51:8080/TmAPI/config/updateServicios/?modo="+modo.getAbreviatura();


        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // optional default is GET
        con.setRequestMethod("GET");

        int responseCode = con.getResponseCode();

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        System.out.println("Updated");
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

    public Modo getModo() {
        return modo;
    }

    public void setModo(Modo modo) {
        this.modo = modo;
    }

    public List<Modo> getModos() {
        return modos;
    }

    public void setModos(List<Modo> modos) {
        this.modos = modos;
    }

    public String getTipoDato() {
        return tipoDato;
    }

    public void setTipoDato(String tipoDato) {
        this.tipoDato = tipoDato;
    }

    public List<String> getTipoDatos() {
        return tipoDatos;
    }

    public void setTipoDatos(List<String> tipoDatos) {
        this.tipoDatos = tipoDatos;
    }

    public ConfiguracionServicio getConfiguracionServicio() {
        return configuracionServicio;
    }

    public void setConfiguracionServicio(ConfiguracionServicio configuracionServicio) {
        this.configuracionServicio = configuracionServicio;
    }
}
