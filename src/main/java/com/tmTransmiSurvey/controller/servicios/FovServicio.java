package com.tmTransmiSurvey.controller.servicios;


import com.tmTransmiSurvey.model.dao.apoyo.IntervalosProgramacionDao;
import com.tmTransmiSurvey.model.dao.base.FOcupacionEncuestaDao;
import com.tmTransmiSurvey.model.dao.base.RegistroEncuestaFOcupacionDao;
import com.tmTransmiSurvey.model.dao.procesamiento.EstudioDao;
import com.tmTransmiSurvey.model.dao.procesamiento.FocupacionProcesadaDao;
import com.tmTransmiSurvey.model.dao.procesamiento.FocupacionRegProcesadaDao;
import com.tmTransmiSurvey.model.entity.base.FOcupacionEncuesta;
import com.tmTransmiSurvey.model.entity.base.RegistroEncuestaFOcupacion;
import com.tmTransmiSurvey.model.entity.procesamiento.Estudio;
import com.tmTransmiSurvey.model.entity.procesamiento.FocupacionProcesada;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class FovServicio {

    @Autowired
    private FOcupacionEncuestaDao fOcupacionEncuestaDao;
    @Autowired
    private RegistroEncuestaFOcupacionDao registroEncuestaFOcupacionDao;

    @Autowired
    private FocupacionProcesadaDao focupacionProcesadaDao;
    @Autowired
    private FocupacionRegProcesadaDao focupacionRegProcesadaDao;
    @Autowired
    private IntervalosProgramacionDao intervalosProgramacionDao;
    @Autowired
    private EstudioDao estudioDao;


    public FovServicio() {
    }


    public List<FOcupacionEncuesta> obtenerEncuestasFOVByFecha(Date fechaInicio, Date fechaFin) {
        return fOcupacionEncuestaDao.getInfoBase(fechaInicio,fechaFin);
    }

    public void addEstudio(Estudio estudio) {
        estudioDao.addEstudio(estudio);
    }

    public void addFovcupacionProcesada(FocupacionProcesada focupacionProcesada) {
        focupacionProcesadaDao.addFocupacionProcesada(focupacionProcesada);
    }

    public List<RegistroEncuestaFOcupacion> obtenerRegistrosByFov(FOcupacionEncuesta encuesta) {
        return registroEncuestaFOcupacionDao.getRegistrosByEncuesta(encuesta);
    }
}
