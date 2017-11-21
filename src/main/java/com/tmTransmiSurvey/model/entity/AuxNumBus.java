package com.tmTransmiSurvey.model.entity;

/**
 * Created by user on 21/11/2017.
 */
public class AuxNumBus {

    private String numBus;
    private Integer recorrido;

    public AuxNumBus() {
    }

    public AuxNumBus(String numBus, Integer recorrido) {
        this.numBus = numBus;
        this.recorrido = recorrido;
    }

    public String getNumBus() {
        return numBus;
    }

    public void setNumBus(String numBus) {
        this.numBus = numBus;
    }

    public Integer getRecorrido() {
        return recorrido;
    }

    public void setRecorrido(Integer recorrido) {
        this.recorrido = recorrido;
    }
}
