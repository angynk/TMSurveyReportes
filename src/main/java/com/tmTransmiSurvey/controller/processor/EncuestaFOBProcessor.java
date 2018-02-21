package com.tmTransmiSurvey.controller.processor;

import com.tmTransmiSurvey.controller.servicios.FOBusServicio;
import com.tmTransmiSurvey.controller.util.LogDatos;
import com.tmTransmiSurvey.controller.util.TipoEncuesta;
import com.tmTransmiSurvey.controller.util.TipoLog;
import com.tmTransmiSurvey.model.entity.base.FOBus;
import com.tmTransmiSurvey.model.entity.base.FOcupacionEncuesta;
import com.tmTransmiSurvey.model.entity.base.RegistroEncuestaFOcupacion;
import com.tmTransmiSurvey.model.entity.base.RegistroFOBus;
import com.tmTransmiSurvey.model.entity.procesamiento.Estudio;
import com.tmTransmiSurvey.model.entity.procesamiento.FocupacionProcesada;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("EncuestaFOBProcessor")
public class EncuestaFOBProcessor {

    @Autowired
    public FOBusServicio foBusServicio;
    private boolean procesamientoValido;

    public EncuestaFOBProcessor() {
    }


    public List<LogDatos> procesarDatosEncuesta(Date fechaInicio, Date fechaFin, String identificadorEstudio, String modo, String estacion) {
        procesamientoValido = true;
        List<LogDatos> logDatos = new ArrayList<>();
        List<FOBus> encuestas = foBusServicio.obtenerEncuestasFOByFecha(fechaInicio,fechaFin,estacion);
        if(encuestas.size()>0){
            Estudio estudio = crearEstudio(fechaInicio,identificadorEstudio,modo);
            for(FOBus encuesta:encuestas){
                logDatos = procesarEncuestaFOB(encuesta,estudio,logDatos);
            }
        }

        return logDatos;
    }

    private List<LogDatos> procesarEncuestaFOB(FOBus encuesta, Estudio estudio, List<LogDatos> logDatos) {
//        List<RegistroFOBus> registros = foBusServicio.getRegistrosFrecOcuByEncuesta(encuesta);
//        if(registros.size()>0){
//            FocupacionProcesada focupacionProcesada = crearRegistroProcesado(encuesta,estudio);
//            if(focupacionProcesada!=null){
//                for(RegistroEncuestaFOcupacion registro:registros){
//                    logDatos = procesarRegistro(logDatos,focupacionProcesada,registro);
//                }
//            }else{
//                logDatos.add(new LogDatos("Fallo en el registro de informacion para estacion "+encuesta.getEstacion(), TipoLog.ERROR));
//            }
//        }else{
//            logDatos.add(new LogDatos("No existen registros de información para estación "+encuesta.getEstacion(), TipoLog.ERROR));
//        }
        return logDatos;
    }

    private Estudio crearEstudio(Date fechaInicio, String identificadorEstudio,String modo) {
        Estudio estudio = new Estudio();
        estudio.setFechaEstudio(fechaInicio);
        estudio.setIdentificador(identificadorEstudio);
        estudio.setTipoEncuesta(TipoEncuesta.ENCUESTA_FREC_OCUPACION_NUM_BUS);
        estudio.setModo(modo);
        foBusServicio.addEstudio(estudio);
        return estudio;
    }

}
