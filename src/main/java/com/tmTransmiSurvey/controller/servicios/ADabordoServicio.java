package com.tmTransmiSurvey.controller.servicios;

import com.tmTransmiSurvey.model.dao.procesamiento.ADabordoProcesadaDao;
import com.tmTransmiSurvey.model.dao.procesamiento.ADabordoRegProcesadaDao;
import com.tmTransmiSurvey.model.dao.procesamiento.EstudioDao;
import com.tmTransmiSurvey.model.dao.apoyo.TipoFranjaDao;
import com.tmTransmiSurvey.model.entity.procesamiento.ADabordoProcesada;
import com.tmTransmiSurvey.model.entity.procesamiento.ADabordoRegProcesada;
import com.tmTransmiSurvey.model.entity.procesamiento.Estudio;
import com.tmTransmiSurvey.model.entity.apoyo.TipoFranja;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ADabordoServicio {

    @Autowired
    private EstudioDao estudioDao;
    @Autowired
    private ADabordoProcesadaDao aDabordoProcesadaDao;
    @Autowired
    private ADabordoRegProcesadaDao aDabordoRegProcesadaDao;
    @Autowired
    private TipoFranjaDao tipoFranjaDao;

    public ADabordoServicio() {
    }

    public void addEstudio(Estudio estudio) {
       estudioDao.addEstudio(estudio);
    }

    public void addADabordoProcesada(ADabordoProcesada aDabordoProcesada) {
        aDabordoProcesadaDao.addADabordoProcesada(aDabordoProcesada);
    }

    public void addADabordoRegProcesada(ADabordoRegProcesada aDabordoRegProcesada) {
        aDabordoRegProcesadaDao.addADabordoRegProcesada(aDabordoRegProcesada);
    }

    public EstudioDao getEstudioDao() {
        return estudioDao;
    }

    public void setEstudioDao(EstudioDao estudioDao) {
        this.estudioDao = estudioDao;
    }

    public ADabordoProcesadaDao getaDabordoProcesadaDao() {
        return aDabordoProcesadaDao;
    }

    public void setaDabordoProcesadaDao(ADabordoProcesadaDao aDabordoProcesadaDao) {
        this.aDabordoProcesadaDao = aDabordoProcesadaDao;
    }

    public ADabordoRegProcesadaDao getaDabordoRegProcesadaDao() {
        return aDabordoRegProcesadaDao;
    }

    public void setaDabordoRegProcesadaDao(ADabordoRegProcesadaDao aDabordoRegProcesadaDao) {
        this.aDabordoRegProcesadaDao = aDabordoRegProcesadaDao;
    }

    public List<Estudio> getEstudios(String encuesta, String modo) {
        return estudioDao.getEstudios(encuesta,modo);
    }

    public List<ADabordoProcesada> getRecorridosByEstudio(Estudio estudio) {
        return aDabordoProcesadaDao.getRecorridosByEstudio(estudio);
    }

    public List<ADabordoRegProcesada> getRegistrosByRecorrido(ADabordoProcesada recorrido) {
        return aDabordoRegProcesadaDao.getRegistrosByRecorrido(recorrido);
    }


    public List<TipoFranja> obtenerFranjas() {
        return tipoFranjaDao.getTipoFranjaAll();
    }
}
