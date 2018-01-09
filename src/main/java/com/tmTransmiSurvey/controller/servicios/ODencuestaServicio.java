package com.tmTransmiSurvey.controller.servicios;

import com.tmTransmiSurvey.model.dao.base.ODEncuestaDao;
import com.tmTransmiSurvey.model.dao.base.ODRegistroDao;
import com.tmTransmiSurvey.model.dao.base.ODTransbordoDao;
import com.tmTransmiSurvey.model.entity.base.ODEncuesta;
import com.tmTransmiSurvey.model.entity.base.ODRegistro;
import com.tmTransmiSurvey.model.entity.base.ODTransbordo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ODencuestaServicio {

    @Autowired
    private ODEncuestaDao odEncuestaDao;

    @Autowired
    private ODRegistroDao odRegistroDao;

    @Autowired
    private ODTransbordoDao odTransbordoDao;


    public ODencuestaServicio() {
    }


    public void addODEncuesta(ODEncuesta od_encuesta) {
        odEncuestaDao.addODEncuesta(od_encuesta);
    }

    public void addODRegistro(ODRegistro odRegistro) {
        odRegistroDao.addODregistro(odRegistro);
    }

    public void addODTransbordo(ODTransbordo transbordo) {
        odTransbordoDao.addODtransbordo(transbordo);
    }

    public List<ODEncuesta> encuestasbyFechaEstacion(Date fechaInicio, Date fechaFin, String estacion) {
        return odEncuestaDao.encuestasbyFechaEstacion(fechaInicio,fechaFin,estacion);
    }

    public List<ODRegistro> getRegistrosByEncuesta(ODEncuesta encuesta) {
        return odRegistroDao.getRegistrosByEncuesta(encuesta);
    }

    public List<ODTransbordo> getTransbordosByRegistro(ODRegistro registro) {
        return odTransbordoDao.getTransbordosByRegistro(registro);
    }
}
