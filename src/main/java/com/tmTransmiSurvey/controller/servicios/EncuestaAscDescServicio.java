package com.tmTransmiSurvey.controller.servicios;

import com.tmTransmiSurvey.model.dao.*;
import com.tmTransmiSurvey.model.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class EncuestaAscDescServicio {

    @Autowired
    private CuadroEncuestaDao cuadroEncuestaDao;
    @Autowired
    private RegistroEncuestaAscDescDao registroEncuestaAscDescDao;
    @Autowired
    private FOcupacionEncuestaDao fOcupacionEncuestaDao;
    @Autowired
    private RegistroEncuestaFOcupacionDao registroEncuestaFOcupacionDao;
    @Autowired
    private ADPuntoEncuestaDao adPuntoEncuestaDao;
    @Autowired
    private RegistroEncuestaAscDesPuntoDao registroEncuestaAscDesPuntoDao;


    public EncuestaAscDescServicio() {
    }

    public void addRegistroEncuestaAscDesc(RegistroEncuestaAscDesc registro) {
        registroEncuestaAscDescDao.addRegistroEncuestaAscDesc(registro);
    }

    public void addCuadroEncuesta(CuadroEncuesta cuadroEncuesta) {
        cuadroEncuestaDao.addCuadroEncuesta(cuadroEncuesta);
    }

    public CuadroEncuestaDao getCuadroEncuestaDao() {
        return cuadroEncuestaDao;
    }

    public void setCuadroEncuestaDao(CuadroEncuestaDao cuadroEncuestaDao) {
        this.cuadroEncuestaDao = cuadroEncuestaDao;
    }

    public RegistroEncuestaAscDescDao getRegistroEncuestaAscDescDao() {
        return registroEncuestaAscDescDao;
    }

    public void setRegistroEncuestaAscDescDao(RegistroEncuestaAscDescDao registroEncuestaAscDescDao) {
        this.registroEncuestaAscDescDao = registroEncuestaAscDescDao;
    }

    public List<CuadroEncuesta> getEncuestasByFechaAndServicio(Date fechaInicio, Date fechaFin, String servicio){
        return cuadroEncuestaDao.getEncuestasByFechaAndServicio(fechaInicio,fechaFin,servicio);
    }

    public List<RegistroEncuestaAscDesc> getRegistrosByEncuesta(CuadroEncuesta encuesta){
        return registroEncuestaAscDescDao.getRegistrosByEncuesta(encuesta);
    }


    public List<FOcupacionEncuesta> getEncuestasFrecuenciaOcupacion(Date fechaInicio, Date fechaFin) {
        return fOcupacionEncuestaDao.getInfoBase(fechaInicio,fechaFin);
    }

    public List<RegistroEncuestaFOcupacion> getRegistrosFrecOcuByEncuesta(FOcupacionEncuesta encuesta) {
        return registroEncuestaFOcupacionDao.getRegistrosByEncuesta(encuesta);
    }

    public List<ADPuntoEncuesta> getEncuestasByFechaAndEstacion(Date fechaInicio, Date fechaFin, String estacion) {
        return adPuntoEncuestaDao.getEncuestasByFechaAndEstacion(fechaInicio,fechaFin,estacion);
    }

    public List<RegistroEncuestaADPunto> getRegistrosByEncuesta(ADPuntoEncuesta encuesta){
        return registroEncuestaAscDesPuntoDao.getRegistrosByEncuesta(encuesta);
    }
}
