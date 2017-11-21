package com.tmTransmiSurvey.controller.processor;

import com.tmTransmiSurvey.controller.servicios.ADabordoServicio;
import com.tmTransmiSurvey.controller.servicios.EncuestaAscDescServicio;
import com.tmTransmiSurvey.model.entity.AuxNumBus;
import com.tmTransmiSurvey.model.entity.CuadroEncuesta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service("EncuestaADAbordoProcessor")
public class EncuestaADAbordoProcessor {


    @Autowired
    public EncuestaAscDescServicio encuestaAscDescServicio;

    @Autowired
    public ADabordoServicio aDabordoServicio;


    public boolean procesarDatosEncuesta(Date fechaInicio, Date horaInicio, Date horaFin, String servicio) {
//        List<CuadroEncuesta> encuestas = encuestaAscDescServicio.getEncuestasByFechaAndServicio(fechaInicio, servicio);
          int recorridoID = 1;
          List<AuxNumBus> recorridos =  encuestaAscDescServicio.getNumBusGroupBy(fechaInicio, servicio);
          Map<Integer,List<CuadroEncuesta>> paquetesEncuesta = new HashMap<Integer,List<CuadroEncuesta>>();
          for(AuxNumBus numBus: recorridos){
              paquetesEncuesta = encontrarPaqueteDatosEncuesta(fechaInicio,servicio,numBus,paquetesEncuesta);
          }

          procesarPaquetesDatos(paquetesEncuesta);

        return false;
    }

    private void procesarPaquetesDatos(Map<Integer, List<CuadroEncuesta>> paquetesEncuesta) {

    }

    private Map<Integer, List<CuadroEncuesta>> encontrarPaqueteDatosEncuesta(Date fechaInicio, String servicio, AuxNumBus numBus, Map<Integer, List<CuadroEncuesta>> paquetesEncuesta) {
        int idPaquete = paquetesEncuesta.size()+1;
        List<CuadroEncuesta> encuestas = encuestaAscDescServicio.getEncuestasByFechaAndServicio(fechaInicio, servicio,numBus.getNumBus(),numBus.getRecorrido());
        if(noSeRepitePuerta(encuestas)){
            paquetesEncuesta.put(idPaquete,encuestas);
        }else{


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
