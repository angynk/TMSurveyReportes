package com.tmTransmiSurvey.view;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import java.io.IOException;

@ManagedBean(name="menu")
@SessionScoped
public class MenuBean {

    public void refreshAscDescAbordo(){
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        try {
            ec.redirect(ec.getRequestContextPath()
                    + "/secured/visualizarDatosEncuestas.xhtml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void refreshAscDescPunto(){
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        try {
            ec.redirect(ec.getRequestContextPath()
                    + "/secured/visualizarAscDescPunto.xhtml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void refreshFrecNumBus(){
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        try {
            ec.redirect(ec.getRequestContextPath()
                    + "/secured/visualizarFOBus.xhtml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void refreshConteoDespachos(){
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        try {
            ec.redirect(ec.getRequestContextPath()
                    + "/secured/visualizarConteoDespachos.xhtml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void refreshOrigenDestino(){
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        try {
            ec.redirect(ec.getRequestContextPath()
                    + "/secured/visualizarODEncuestas.xhtml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void refreshFrecuenciaOcupacion(){
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        try {
            ec.redirect(ec.getRequestContextPath()
                    + "/secured/visualizarFrecOcupacion.xhtml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void refreshProcesamientoDatos(){
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        try {
            ec.redirect(ec.getRequestContextPath()
                    + "/secured/procesarDatosEncuestas.xhtml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void refreshFovCodigos(){
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        try {
            ec.redirect(ec.getRequestContextPath()
                    + "/secured/fovCodigos.xhtml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void refreshCargaDatos(){
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        try {
            ec.redirect(ec.getRequestContextPath()
                    + "/secured/cargarDatosBase.xhtml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void refreshBVistaUsuarios(){
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        try {
            ec.redirect(ec.getRequestContextPath()
                    + "/secured/configUsuarios.xhtml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void refreshBVistaRoles(){
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        try {
            ec.redirect(ec.getRequestContextPath()
                    + "/secured/configRoles.xhtml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void refreshDatosProcesados(){
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        try {
            ec.redirect(ec.getRequestContextPath()
                    + "/secured/visualizarEncuestasProcesadas.xhtml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void refreshVisualizarReporte(){
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        try {
            ec.redirect(ec.getRequestContextPath()
                    + "/secured/visualizarDatosEncuestas.xhtml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }






}
