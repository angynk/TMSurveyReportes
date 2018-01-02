package com.tmTransmiSurvey.controller.servicios;

import com.tmTransmiSurvey.model.dao.apoyo.EstacionDao;
import com.tmTransmiSurvey.model.dao.apoyo.ServicioDao;
import com.tmTransmiSurvey.model.dao.apoyo.ServicioEstacionDao;
import com.tmTransmiSurvey.model.dao.apoyo.TipologiaDao;
import com.tmTransmiSurvey.model.entity.apoyo.Estacion;
import com.tmTransmiSurvey.model.entity.apoyo.ServicioTs;
import com.tmTransmiSurvey.model.entity.apoyo.Tipologia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicioEstacionServicio {

    @Autowired
    private ServicioDao servicioDao;

    @Autowired
    private EstacionDao estacionDao;

    @Autowired
    private ServicioEstacionDao servicioEstacionDao;

    @Autowired
    private TipologiaDao tipologiaDao;


    public ServicioEstacionServicio() {
    }

    public ServicioDao getServicioDao() {
        return servicioDao;
    }

    public void setServicioDao(ServicioDao servicioDao) {
        this.servicioDao = servicioDao;
    }

    public EstacionDao getEstacionDao() {
        return estacionDao;
    }

    public void setEstacionDao(EstacionDao estacionDao) {
        this.estacionDao = estacionDao;
    }

    public ServicioEstacionDao getServicioEstacionDao() {
        return servicioEstacionDao;
    }

    public void setServicioEstacionDao(ServicioEstacionDao servicioEstacionDao) {
        this.servicioEstacionDao = servicioEstacionDao;
    }

    public List<ServicioTs> encontrarTodosLosServicios(){
       return servicioDao.encontrarTodosLosServicios();
    }

    public List<Estacion> encontrarTodasLasEstaciones() {
        return estacionDao.encontrarTodasLasEstaciones();
    }

    public List<Estacion> encontrarTodasLasEstaciones(String modo) {
        return estacionDao.encontrarTodasLasEstaciones(modo);
    }

    public List<ServicioTs> encontrarTodosLosServicios(String modo) {
        return servicioDao.encontrarTodosLosServicios(modo);
    }

    public ServicioTs encontrarServicioByNombre(String servicio) {
        return servicioDao.encontrarServicioByNombre(servicio);
    }

    public Estacion encontrarEstacionByNombre(String estacion) {
        return estacionDao.encontrarEstacionByNombre(estacion);
    }

    public List<Tipologia> encontrarTipologias() {
        return tipologiaDao.getTipologiaAll();
    }

    public Tipologia encontrarTipologiaByNombre(String tipologia) {
        return tipologiaDao.getTipologiaByNombre(tipologia);
    }
}
