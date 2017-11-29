package com.tmTransmiSurvey.controller.processor;

import com.tmTransmiSurvey.controller.util.LogDatos;
import com.tmTransmiSurvey.model.dao.apoyo.IntervalosProgramacionDao;
import com.tmTransmiSurvey.model.dao.procesamiento.FocupacionProcesadaDao;
import com.tmTransmiSurvey.model.dao.procesamiento.FocupacionRegProcesadaDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service("EncuestaFOVProcessor")
public class EncuestaFOVProcessor {

    @Autowired
    private FocupacionProcesadaDao focupacionProcesadaDao;
    @Autowired
    private FocupacionRegProcesadaDao focupacionRegProcesadaDao;
    @Autowired
    private IntervalosProgramacionDao intervalosProgramacionDao;

    public EncuestaFOVProcessor() {
    }


    public List<LogDatos> procesarDatosEncuesta(Date fechaInicio, Date fechaFin, String identificadorEstudio) {
        return null;
    }
}
