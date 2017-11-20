package com.tmTransmiSurvey.controller.servicios;

import com.tmTransmiSurvey.model.dao.EstacionDao;
import com.tmTransmiSurvey.model.dao.ServicioDao;
import com.tmTransmiSurvey.model.dao.ServicioEstacionDao;
import com.tmTransmiSurvey.model.entity.Estacion;
import com.tmTransmiSurvey.model.entity.ServicioTs;
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
}
