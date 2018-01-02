package com.tmTransmiSurvey.controller.processor;

import com.tmTransmiSurvey.controller.servicios.FovServicio;
import com.tmTransmiSurvey.controller.servicios.ServicioEstacionServicio;
import com.tmTransmiSurvey.model.entity.apoyo.Estacion;
import com.tmTransmiSurvey.model.entity.apoyo.FovCodigos;
import com.tmTransmiSurvey.model.entity.apoyo.ServicioTs;
import com.tmTransmiSurvey.model.entity.apoyo.Tipologia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("FovCodigosProcessor")
public class FovCodigosProcessor {

    @Autowired
    public ServicioEstacionServicio servicioEstacionServicio;

    @Autowired
    public FovServicio fovServicio;


    public FovCodigosProcessor() {
    }

    public List<Estacion> encontrarTodosLasEstaciones(String modo){
        return servicioEstacionServicio.encontrarTodasLasEstaciones(modo);
    }

    public List<FovCodigos> encontrarFovByEstacion(String estacion) {
        return fovServicio.encontrarFovByEstacion(estacion);
    }

    public List<String> encontrarTodasLosServicios(String modo) {
        List<ServicioTs> servicioTs = servicioEstacionServicio.encontrarTodosLosServicios(modo);
        List<String> servicios = new ArrayList<>();
        for(ServicioTs servicioTs1:servicioTs){
            servicios.add(servicioTs1.getNombre());
        }

        return servicios;
    }

    public void eliminarFovCodigo(FovCodigos selectedFov) {
        fovServicio.eliminarFovCodigo(selectedFov);
    }

    public ServicioTs encontrarServicioByNombre(String servicio) {
        return servicioEstacionServicio.encontrarServicioByNombre(servicio);
    }

    public Estacion encontrarEstacionByNombre(String estacion) {
        return servicioEstacionServicio.encontrarEstacionByNombre(estacion);
    }

    public void agregarFovCodigo(FovCodigos nuevoFov) {
        fovServicio.agregarFovCodigo(nuevoFov);
    }

    public List<String> encontrarTipologias() {

        List<Tipologia> tipologias = servicioEstacionServicio.encontrarTipologias();
        List<String> tipos = new ArrayList<>();
        for(Tipologia tipologia:tipologias){
            tipos.add(tipologia.getNombre());
        }
        return tipos;
    }


    public Tipologia encontrarTipologiaByNombre(String tipologia) {
        return servicioEstacionServicio.encontrarTipologiaByNombre(tipologia);
    }
}
