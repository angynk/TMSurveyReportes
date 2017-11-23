package com.tmTransmiSurvey.controller.processor;


import com.tmTransmiSurvey.controller.servicios.ADabordoServicio;
import com.tmTransmiSurvey.model.entity.Estudio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("VisualizarEstudiosProcessor")
public class VisualizarEstudiosProcessor {

    @Autowired
    public ADabordoServicio aDabordoServicio;

    public List<Estudio> getEstudios(String encuesta, String modo) {
        return aDabordoServicio.getEstudios(encuesta,modo);
    }
}
