package com.tmTransmiSurvey.controller.servicios;

import com.tmTransmiSurvey.model.dao.base.FOBusDao;
import com.tmTransmiSurvey.model.dao.base.RegistroFOBusDao;
import com.tmTransmiSurvey.model.entity.base.FOBus;
import com.tmTransmiSurvey.model.entity.base.RegistroFOBus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class FOBusServicio {

    @Autowired
    FOBusDao foBusDao;

    @Autowired
    RegistroFOBusDao registroFOBusDao;


    public FOBusServicio() {
    }


    public void addFoBus(FOBus fr_bus) {
        foBusDao.addFOBus(fr_bus);
    }

    public void addFOBusRegistro(RegistroFOBus registroFOBus) {
        registroFOBusDao.addRegistroFOBus(registroFOBus);
    }

    public List<FOBus> getDatosFrecOcupa(Date fechaInicio, Date fechaFin) {
        return foBusDao.getDatosFrecOcupa(fechaInicio,fechaFin);
    }

    public List<RegistroFOBus> getRegistrosFrecOcuByEncuesta(FOBus encuesta) {
        return registroFOBusDao.getRegistrosFrecOcuByEncuesta(encuesta);
    }
}
