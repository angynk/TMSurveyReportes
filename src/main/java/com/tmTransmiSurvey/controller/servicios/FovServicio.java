package com.tmTransmiSurvey.controller.servicios;


import com.tmTransmiSurvey.model.dao.apoyo.FovCodigosDao;
import com.tmTransmiSurvey.model.dao.apoyo.IntervalosProgramacionDao;
import com.tmTransmiSurvey.model.dao.base.FOcupacionEncuestaDao;
import com.tmTransmiSurvey.model.dao.base.RegistroEncuestaFOcupacionDao;
import com.tmTransmiSurvey.model.dao.procesamiento.EstudioDao;
import com.tmTransmiSurvey.model.dao.procesamiento.FocupacionProcesadaDao;
import com.tmTransmiSurvey.model.dao.procesamiento.FocupacionRegProcesadaDao;
import com.tmTransmiSurvey.model.entity.apoyo.FovCodigos;
import com.tmTransmiSurvey.model.entity.apoyo.IntervalosProgramacion;
import com.tmTransmiSurvey.model.entity.base.FOcupacionEncuesta;
import com.tmTransmiSurvey.model.entity.base.RegistroEncuestaFOcupacion;
import com.tmTransmiSurvey.model.entity.procesamiento.Estudio;
import com.tmTransmiSurvey.model.entity.procesamiento.FocupacionProcesada;
import com.tmTransmiSurvey.model.entity.procesamiento.FocupacionRegProcesada;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Time;
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
    @Autowired
    private FovCodigosDao fovCodigosDao;


    public FovServicio() {
    }


    public List<FOcupacionEncuesta> obtenerEncuestasFOVByFecha(Date fechaInicio, Date fechaFin,String estacion) {
        return fOcupacionEncuestaDao.getInfoBase(fechaInicio,fechaFin,estacion);
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

    public FovCodigos obtenerServicioPorCodigoEstacionSentido(String codigo, String estacion, String sentido) {
        return fovCodigosDao.obtenerServicioPorCodigoEstacionSentido(codigo,estacion,sentido);
    }

    public IntervalosProgramacion obtenerIntervalo(Time horaPaso) {
        return intervalosProgramacionDao.getIntervaloForDate(horaPaso);
    }

    public void addFocupacionRegProcesada(FocupacionRegProcesada regProcesada) {
        focupacionRegProcesadaDao.addAFocupacionRegProcesada(regProcesada);
    }

    public List<FocupacionProcesada> getRegistrosPorEstudio(Estudio selectedEstudio) {
        return focupacionProcesadaDao.getFOVByEstudio(selectedEstudio);
    }

    public List<FocupacionRegProcesada> getRegistrosByEstacion(FocupacionProcesada recorrido) {
        return focupacionRegProcesadaDao.getRegistrosByFov(recorrido);
    }

    public List<FovCodigos> encontrarFovByEstacion(String estacion) {
        return fovCodigosDao.encontrarFovByEstacion(estacion);
    }

    public void eliminarFovCodigo(FovCodigos selectedFov) {
        fovCodigosDao.eliminarFovCodigo(selectedFov);
    }

    public void agregarFovCodigo(FovCodigos nuevoFov) {
        fovCodigosDao.agregarFovCodigo(nuevoFov);
    }

    public void eliminarEstudio(Estudio selectedEstudio) {
        estudioDao.eliminarEstudio(selectedEstudio);
    }
}
