package com.tmTransmiSurvey.controller.processor;

import com.tmTransmiSurvey.controller.TipoEncuesta;
import com.tmTransmiSurvey.controller.servicios.ADabordoServicio;
import com.tmTransmiSurvey.controller.servicios.EncuestaAscDescServicio;
import com.tmTransmiSurvey.model.entity.ADabordoProcesada;
import com.tmTransmiSurvey.model.entity.AuxNumBus;
import com.tmTransmiSurvey.model.entity.CuadroEncuesta;
import com.tmTransmiSurvey.model.entity.Estudio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service("EncuestaADAbordoProcessor")
public class EncuestaADAbordoProcessor {


    @Autowired
    public EncuestaAscDescServicio encuestaAscDescServicio;


    @Autowired
    public ADabordoServicio aDabordoServicio;

    private String identificadorEstudio;


    public boolean procesarDatosEncuesta(Date fechaInicio, Date horaInicio, Date horaFin, String servicio) {
//        List<CuadroEncuesta> encuestas = encuestaAscDescServicio.getEncuestasByFechaAndServicio(fechaInicio, servicio);
          int recorridoID = 1;
          List<AuxNumBus> recorridos =  encuestaAscDescServicio.getNumBusGroupBy(fechaInicio, servicio);
          Map<Integer,List<CuadroEncuesta>> paquetesEncuesta = new HashMap<Integer,List<CuadroEncuesta>>();
          for(AuxNumBus numBus: recorridos){
              paquetesEncuesta = encontrarPaqueteDatosEncuesta(fechaInicio,servicio,numBus,paquetesEncuesta);
          }

          procesarPaquetesDatos(paquetesEncuesta,fechaInicio);

        return false;
    }

    private boolean procesarPaquetesDatos(Map<Integer, List<CuadroEncuesta>> paquetesEncuesta, Date fechaInicio) {

        if(paquetesEncuesta.size()>0){
            Estudio estudio = crearEstudio(fechaInicio);
            if(estudio!=null){
                for (Map.Entry<Integer, List<CuadroEncuesta>> entry : paquetesEncuesta.entrySet())
                {
                    int recorrido = entry.getKey();
                    List<CuadroEncuesta> encuestas =entry.getValue();
                    procesarRecorrido(recorrido,encuestas);

                }
            }else{
                return false;
            }
        }

        return true;

    }

    private void procesarRecorrido(int recorrido, List<CuadroEncuesta> encuestas) {
        // Informacion Base encuesta
        ADabordoProcesada aDabordoProcesada = new ADabordoProcesada();
        CuadroEncuesta cuadroEncuesta = encuestas.get(0);
        aDabordoProcesada.setRecorrido(recorrido);
        aDabordoProcesada.setNumBus(cuadroEncuesta.getNum_bus());
        aDabordoProcesada.setServicio(cuadroEncuesta.getServicio());
        aDabordoServicio.addADabordoProcesada(aDabordoProcesada);

        // Procesamiento



    }

    private Estudio crearEstudio(Date fechaInicio) {
        try{
            Estudio estudio = new Estudio();
            estudio.setFechaEstudio(fechaInicio);
            estudio.setIdentificador(identificadorEstudio);
            estudio.setTipoEncuesta(TipoEncuesta.ENCUESTA_ASC_DESC_ABORDO);
            aDabordoServicio.addEstudio(estudio);
            return estudio;
        }catch (Exception e){
            return null;
        }
    }


    private Map<Integer, List<CuadroEncuesta>> encontrarPaqueteDatosEncuesta(Date fechaInicio, String servicio, AuxNumBus numBus, Map<Integer, List<CuadroEncuesta>> paquetesEncuesta) {
        int idPaquete = paquetesEncuesta.size()+1;
        List<CuadroEncuesta> encuestas = encuestaAscDescServicio.getEncuestasByFechaAndServicio(fechaInicio, servicio,numBus.getNumBus(),numBus.getRecorrido());
        if(encuestas.size()>0){
            identificadorEstudio = encuestas.get(0).getDia_semana()+" - "+encuestas.get(0).getServicio();
        }
        if(noSeRepitePuerta(encuestas)){
            paquetesEncuesta.put(idPaquete,encuestas);
        }else{
                //Anñadir logica cuando se repite

        }
        return paquetesEncuesta;
    }

    private boolean noSeRepitePuerta(List<CuadroEncuesta> encuestas) {
        if(encuestas.size()>0){
            CuadroEncuesta encuA = encuestas.get(0);
            for(int i=1;i<encuestas.size();i++){
                CuadroEncuesta encuB = encuestas.get(1);
                if(encuA.getNum_puerta()== encuB.getNum_puerta()){
                    return false;
                }
                encuA = encuB;
            }
        }

        return true;
    }
}
