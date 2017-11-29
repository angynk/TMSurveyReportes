package com.tmTransmiSurvey.controller.processor;

import com.tmTransmiSurvey.controller.servicios.FovServicio;
import com.tmTransmiSurvey.controller.util.LogDatos;
import com.tmTransmiSurvey.controller.util.TipoEncuesta;
import com.tmTransmiSurvey.controller.util.TipoLog;
import com.tmTransmiSurvey.model.entity.base.FOcupacionEncuesta;
import com.tmTransmiSurvey.model.entity.base.RegistroEncuestaFOcupacion;
import com.tmTransmiSurvey.model.entity.procesamiento.Estudio;
import com.tmTransmiSurvey.model.entity.procesamiento.FocupacionProcesada;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("EncuestaFOVProcessor")
public class EncuestaFOVProcessor {


    @Autowired
    private FovServicio fovServicio;
    private boolean procesamientoValido;

    public EncuestaFOVProcessor() {
    }


    public List<LogDatos> procesarDatosEncuesta(Date fechaInicio, Date fechaFin, String identificadorEstudio,String modo) {
        List<LogDatos> logDatos = new ArrayList<>();
        List<FOcupacionEncuesta> encuestas = fovServicio.obtenerEncuestasFOVByFecha(fechaInicio,fechaFin);// TODO Incluir Modo
        if(encuestas.size()>0){
            Estudio estudio = crearEstudio(fechaInicio,identificadorEstudio,modo);
            for(FOcupacionEncuesta encuesta:encuestas){
                 logDatos = procesarEncuestaFOV(encuesta,estudio,logDatos);
            }
        }else{
            logDatos.add(new LogDatos("No existe información FOV para las fechas seleccionadas", TipoLog.ERROR));
            procesamientoValido = false;
        }
        return logDatos;
    }

    private List<LogDatos> procesarEncuestaFOV(FOcupacionEncuesta encuesta, Estudio estudio, List<LogDatos> logDatos) {
        List<RegistroEncuestaFOcupacion> registros = fovServicio.obtenerRegistrosByFov(encuesta);
        if(registros.size()>0){
            FocupacionProcesada focupacionProcesada = crearRegistroProcesado(encuesta,estudio);
            if(focupacionProcesada!=null){
                for(RegistroEncuestaFOcupacion registro:registros){
                    logDatos = procesarRegistro(logDatos,focupacionProcesada,registro);
                }
            }else{
                logDatos.add(new LogDatos("Fallo en el registro de informacion para estacion "+encuesta.getEstacion(), TipoLog.ERROR));
            }
        }else{
            logDatos.add(new LogDatos("No existen registros de información para estación "+encuesta.getEstacion(), TipoLog.ERROR));
        }


        return logDatos;
    }

    private List<LogDatos> procesarRegistro(List<LogDatos> logDatos, FocupacionProcesada focupacionProcesada, RegistroEncuestaFOcupacion registro) {
        //1. Encontrar Servicio por codigo y sentido

        //2. Validar que el servicio pase por la estacion
        //3. Encontrar ocupación del bus de acuerdo a la tipologia del bus
        //4. Definir porcentaje de ocupacion
        //5. Clasificar hora en intervalo de programacion
        return logDatos;
    }

    private FocupacionProcesada crearRegistroProcesado(FOcupacionEncuesta encuesta, Estudio estudio) {
        try{
            FocupacionProcesada focupacionProcesada = new FocupacionProcesada();
            focupacionProcesada.setEstacion(encuesta.getEstacion());
            focupacionProcesada.setEstudio(estudio);
            focupacionProcesada.setFecha(encuesta.getFecha_encuesta());
            focupacionProcesada.setZona(encuesta.getZona());
            fovServicio.addFovcupacionProcesada(focupacionProcesada);
            return focupacionProcesada;
        }catch (Exception e){

        }

        return null;
    }

    private Estudio crearEstudio(Date fechaInicio, String identificadorEstudio,String modo) {
        Estudio estudio = new Estudio();
        estudio.setFechaEstudio(fechaInicio);
        estudio.setIdentificador(identificadorEstudio);
        estudio.setTipoEncuesta(TipoEncuesta.ENCUESTA_FREC_OCUPACION);
        estudio.setModo(modo);
        fovServicio.addEstudio(estudio);
        return estudio;
    }

    public boolean isProcesamientoValido() {
        return procesamientoValido;
    }

    public void setProcesamientoValido(boolean procesamientoValido) {
        this.procesamientoValido = procesamientoValido;
    }
}
