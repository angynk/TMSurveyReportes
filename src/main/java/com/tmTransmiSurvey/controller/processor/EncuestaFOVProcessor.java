package com.tmTransmiSurvey.controller.processor;

import com.tmTransmiSurvey.controller.servicios.FovServicio;
import com.tmTransmiSurvey.controller.util.LogDatos;
import com.tmTransmiSurvey.controller.util.TipoEncuesta;
import com.tmTransmiSurvey.controller.util.TipoLog;
import com.tmTransmiSurvey.controller.util.Util;
import com.tmTransmiSurvey.model.dao.procesamiento.FocupacionRegProcesadaDao;
import com.tmTransmiSurvey.model.entity.apoyo.FovCodigos;
import com.tmTransmiSurvey.model.entity.apoyo.IntervalosProgramacion;
import com.tmTransmiSurvey.model.entity.apoyo.Tipologia;
import com.tmTransmiSurvey.model.entity.base.FOcupacionEncuesta;
import com.tmTransmiSurvey.model.entity.base.RegistroEncuestaFOcupacion;
import com.tmTransmiSurvey.model.entity.procesamiento.Estudio;
import com.tmTransmiSurvey.model.entity.procesamiento.FocupacionProcesada;
import com.tmTransmiSurvey.model.entity.procesamiento.FocupacionRegProcesada;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Time;
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


    public List<LogDatos> procesarDatosEncuesta(Date fechaInicio, Date fechaFin, String identificadorEstudio, String modo,String estacion) {
        procesamientoValido = true;
        List<LogDatos> logDatos = new ArrayList<>();
        List<FOcupacionEncuesta> encuestas = fovServicio.obtenerEncuestasFOVByFecha(fechaInicio,fechaFin,estacion);// TODO Incluir Modo
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
        String codigo = registro.getCodigo();
        String estacion = registro.getfOcupacionEncuesta().getEstacion();
        String sentido = registro.getfOcupacionEncuesta().getSentido();
        FovCodigos fovCodigos = fovServicio.obtenerServicioPorCodigoEstacionSentido(codigo, estacion, sentido);
        if (fovCodigos != null) {
            //3. Encontrar ocupación del bus de acuerdo a la tipologia del bus
            Integer ocupacion = registro.getOcupacion();
            ocupacion = calcularOcupacionNumero(ocupacion,fovCodigos);
            //4. Definir porcentaje de ocupacion
            Double porOcupacion = calcularPorcentajeOcupacion(ocupacion,fovCodigos);
            //5. Clasificar hora en intervalo de programacion
            Time horaPaso = Util.obtenerFecha(registro.getHora_paso());
            IntervalosProgramacion intervalosProgramacion = encontrarIntervaloProgramacion(horaPaso);

            FocupacionRegProcesada regProcesada = new FocupacionRegProcesada();
            regProcesada.setFocupacionProcesada(focupacionProcesada);
            regProcesada.setIntervalo(intervalosProgramacion);
            regProcesada.setNumOcupacion(ocupacion);
            regProcesada.setPorOcupacion(porOcupacion);
            regProcesada.setServicio(fovCodigos.getServicio().getNombre());
            regProcesada.setHora(horaPaso);
            regProcesada.setTipologia(fovCodigos.getTipologia().getNombre());
            fovServicio.addFocupacionRegProcesada(regProcesada);

        }else{
            logDatos.add(new LogDatos("No se encontro servicio "+codigo+" - estacion "+estacion+" - sentido "+sentido, TipoLog.ERROR));
        }

        return logDatos;
    }

    private IntervalosProgramacion encontrarIntervaloProgramacion(Time horaPaso) {

        IntervalosProgramacion intervalosProgramacion = fovServicio.obtenerIntervalo(horaPaso);
        return intervalosProgramacion;
    }

    private Double calcularPorcentajeOcupacion(Integer ocupacion, FovCodigos fovCodigos) {
        Double porcentaje = (double) (ocupacion *100)/fovCodigos.getTipologia().getCapacidadPicos();
        return porcentaje;
    }

    private Integer calcularOcupacionNumero(Integer ocupacion, FovCodigos fovCodigos) {
        Tipologia tipologia = fovCodigos.getTipologia();
        ocupacion = (ocupacion*tipologia.getCapacidadPicos())/5;
        return ocupacion;
    }

    private FocupacionProcesada crearRegistroProcesado(FOcupacionEncuesta encuesta, Estudio estudio) {
        try{
            FocupacionProcesada focupacionProcesada = new FocupacionProcesada();
            focupacionProcesada.setEstacion(encuesta.getEstacion());
            focupacionProcesada.setEstudio(estudio);
            focupacionProcesada.setFecha(encuesta.getFecha_encuesta());
            focupacionProcesada.setZona(encuesta.getZona());
            focupacionProcesada.setSentido(encuesta.getSentido());
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
