package com.tmTransmiSurvey.controller.servicios;


import com.tmTransmiSurvey.model.dao.base.RegistroTRecorridoDao;
import com.tmTransmiSurvey.model.dao.base.TRecorridosEncuestaDao;
import com.tmTransmiSurvey.model.entity.base.CuadroEncuesta;
import com.tmTransmiSurvey.model.entity.base.RegistroTRecorridos;
import com.tmTransmiSurvey.model.entity.base.TRecorridosEncuesta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class EncuestaTiemposRecorridoServicio {

    @Autowired
    private RegistroTRecorridoDao registroTRecorridoDao;

    @Autowired
    private TRecorridosEncuestaDao tRecorridosEncuestaDao;

    public EncuestaTiemposRecorridoServicio() {
    }


    public void addTiempoRecorridos(TRecorridosEncuesta t_recorridos) {
        tRecorridosEncuestaDao.addTRecorridoEncuesta(t_recorridos);
    }

    public void addRegistroTiempoRecorridos(RegistroTRecorridos registro) {
        registroTRecorridoDao.addRegistroTRecorridoEncuesta(registro);
    }

    public List<TRecorridosEncuesta> getEncuestasByFechaAndServicio(Date fechaInicio, Date fechaFin, String servicio) {
        return tRecorridosEncuestaDao.getEncuestasByFechaAndServicio(fechaInicio,fechaFin,servicio);
    }

    public List<RegistroTRecorridos> getRegistrosByEncuesta(TRecorridosEncuesta encuesta) {
        return registroTRecorridoDao.getRegistrosByEncuesta(encuesta);
    }
}
