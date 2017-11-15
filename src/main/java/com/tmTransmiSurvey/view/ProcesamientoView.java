package com.tmTransmiSurvey.view;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean(name="procesView")
@ViewScoped
public class ProcesamientoView {

    private String tipoProcesamiento;

    public ProcesamientoView() {
    }

    public String getTipoProcesamiento() {
        return tipoProcesamiento;
    }

    public void setTipoProcesamiento(String tipoProcesamiento) {
        this.tipoProcesamiento = tipoProcesamiento;
    }

    public void habilitarTipoProcesamiento(){

    }
}
