package com.tmTransmiSurvey.controller.servicios;

import com.tmTransmiSurvey.model.dao.ADabordoProcesadaDao;
import com.tmTransmiSurvey.model.dao.ADabordoRegProcesadaDao;
import com.tmTransmiSurvey.model.dao.EstudioDao;
import com.tmTransmiSurvey.model.entity.ADabordoProcesada;
import com.tmTransmiSurvey.model.entity.ADabordoRegProcesada;
import com.tmTransmiSurvey.model.entity.Estudio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ADabordoServicio {

    @Autowired
    private EstudioDao estudioDao;
    @Autowired
    private ADabordoProcesadaDao aDabordoProcesadaDao;
    @Autowired
    private ADabordoRegProcesadaDao aDabordoRegProcesadaDao;

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
}