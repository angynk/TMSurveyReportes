package com.tmTransmiSurvey.controller.processor;

import com.tmTransmiSurvey.controller.TipoEncuesta;
import com.tmTransmiSurvey.controller.Util;
import com.tmTransmiSurvey.controller.servicios.ADabordoServicio;
import com.tmTransmiSurvey.controller.servicios.EncuestaAscDescServicio;
import com.tmTransmiSurvey.model.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service("EncuestaADAbordoProcessor")
public class EncuestaADAbordoProcessor {


    @Autowired
    public EncuestaAscDescServicio encuestaAscDescServicio;


    @Autowired
    public ADabordoServicio aDabordoServicio;

    private List<TipoFranja> franjas;


    public boolean procesarDatosEncuesta(Date fechaInicio, Date fechaFin, String servicio, String modo, String identificadorEstudio) {
//        List<CuadroEncuesta> encuestas = encuestaAscDescServicio.getEncuestasByFechaAndServicio(fechaInicio, servicio);
          franjas = aDabordoServicio.obtenerFranjas();
          int recorridoID = 1;
          List<AuxNumBus> recorridos =  encuestaAscDescServicio.getNumBusGroupBy(fechaInicio,fechaFin, servicio);
          Map<Integer,List<CuadroEncuesta>> paquetesEncuesta = new HashMap<Integer,List<CuadroEncuesta>>();
          for(AuxNumBus numBus: recorridos){
              paquetesEncuesta = encontrarPaqueteDatosEncuesta(fechaInicio,fechaFin,servicio,numBus,paquetesEncuesta);
          }

       return    procesarPaquetesDatos(paquetesEncuesta,fechaInicio,modo,identificadorEstudio);
    }

    private boolean procesarPaquetesDatos(Map<Integer, List<CuadroEncuesta>> paquetesEncuesta, Date fechaInicio, String modo, String identificadorEstudio) {

        if(paquetesEncuesta.size()>0){
            Estudio estudio = crearEstudio(fechaInicio,modo,identificadorEstudio);
            if(estudio!=null){
                for (Map.Entry<Integer, List<CuadroEncuesta>> entry : paquetesEncuesta.entrySet())
                {
                    int recorrido = entry.getKey();
                    List<CuadroEncuesta> encuestas =entry.getValue();
                    procesarRecorrido(recorrido,encuestas,estudio);
                }
            }else{
                return false;
            }
        }

        return true;

    }

    private void procesarRecorrido(int recorrido, List<CuadroEncuesta> encuestas, Estudio estudio) {
        // Informacion Base encuesta
        ADabordoProcesada aDabordoProcesada = new ADabordoProcesada();
        CuadroEncuesta cuadroEncuesta = encuestas.get(0);
        aDabordoProcesada.setRecorrido(recorrido);
        aDabordoProcesada.setNumBus(cuadroEncuesta.getNum_bus());
        aDabordoProcesada.setServicio(cuadroEncuesta.getServicio());
        aDabordoProcesada.setEstudio(estudio);
        aDabordoProcesada.setDiaSemana(cuadroEncuesta.getDia_semana());
        aDabordoProcesada.setFecha(cuadroEncuesta.getFecha_encuesta());
        aDabordoServicio.addADabordoProcesada(aDabordoProcesada);

        // Procesamiento
        List<List<RegistroEncuestaAscDesc>> mapaAProcesar = cargarInformacionRegistros(encuestas);
        if(mapaAProcesar.size()>0){
            List<RegistroEncuestaAscDesc> listaBase = mapaAProcesar.get(0);
            ADabordoRegProcesada registroAnterior = null;
            for(int x=0; x< listaBase.size();x++){
                if( x == 0 ){
                    registroAnterior = calcularPrimerRegistro(x,mapaAProcesar,aDabordoProcesada);
                }else{
                    registroAnterior = calcularRegistro(x,listaBase.get(x),mapaAProcesar,registroAnterior.getPasBus(),aDabordoProcesada);
                }
            }
        }


    }

    private ADabordoRegProcesada calcularRegistro(int pos, RegistroEncuestaAscDesc base, List<List<RegistroEncuestaAscDesc>> mapaAProcesar, Integer pasBus, ADabordoProcesada aDabordoProcesada) {

        for(int x=0; x< mapaAProcesar.size();x++){
            pasBus = pasBus + mapaAProcesar.get(x).get(pos).getSuben() - mapaAProcesar.get(x).get(pos).getBajan();
        }

        ADabordoRegProcesada nuevoRegistro = insertarRegistro(base.getEstacion(),
                base.getHora_llegada(),
                base.getHora_salida(),
                aDabordoProcesada, pasBus);

        return nuevoRegistro;

    }

    private ADabordoRegProcesada calcularPrimerRegistro(int valor, List<List<RegistroEncuestaAscDesc>> mapaAProcesar, ADabordoProcesada aDabordoProcesada) {

        int numPasajeros = 0;
        for(int x=0; x< mapaAProcesar.size();x++){
          numPasajeros = numPasajeros + mapaAProcesar.get(x).get(valor).getSuben();
        }
        ADabordoRegProcesada nuevoRegistro = insertarRegistro(mapaAProcesar.get(0).get(0).getEstacion(),
                mapaAProcesar.get(0).get(0).getHora_llegada(),
                mapaAProcesar.get(0).get(0).getHora_salida(),
                aDabordoProcesada, numPasajeros);
        nuevoRegistro.setFranja(obtenerFranjaHoraria(mapaAProcesar.get(0).get(0).getHora_salida()));

        return nuevoRegistro;
    }

    private String obtenerFranjaHoraria(String horaSalidaTexto) {

        Date horaSalida = Util.obtenerFecha(horaSalidaTexto);
        for(TipoFranja franja:franjas){
            Date horaInicio = Util.obtenerFecha(franja.getHoraInicio());
            Date horaFin = Util.obtenerFecha(franja.getHoraFin());

        }
        return "AM";

    }

    private ADabordoRegProcesada insertarRegistro(String estacion, String horaLLegada,String horaSalida, ADabordoProcesada aDabordoProcesada, int numPasajeros) {
        ADabordoRegProcesada nuevoRegistro = new ADabordoRegProcesada();
        nuevoRegistro.setPasBus(numPasajeros);
        nuevoRegistro.setEstacion(estacion);
        nuevoRegistro.setHoraLlegada(convertirATime(horaLLegada));
        nuevoRegistro.setHoraSalida(convertirATime(horaSalida));
        nuevoRegistro.setAdBase(aDabordoProcesada);

        aDabordoServicio.addADabordoRegProcesada(nuevoRegistro);

        return nuevoRegistro;
    }

    public static Time convertirATime(String stringCellValue) {
        SimpleDateFormat parser = new SimpleDateFormat("HH:mm:ss");
        if(!stringCellValue.equals("")){
            try {
                Date date = parser.parse(stringCellValue);
                return new Time(date.getTime());
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    private List<List<RegistroEncuestaAscDesc>> cargarInformacionRegistros(List<CuadroEncuesta> encuestas) {
        List<List<RegistroEncuestaAscDesc>> datos = new ArrayList<List<RegistroEncuestaAscDesc>>();
        for(CuadroEncuesta encuesta:encuestas){
            List<RegistroEncuestaAscDesc> registrosByEncuesta = encuestaAscDescServicio.getRegistrosByEncuesta(encuesta);
            datos.add(registrosByEncuesta);
        }
        encuestas.clear();// Limipar memoria
        return datos;
    }


    private Estudio crearEstudio(Date fechaInicio, String modo, String identificadorEstudio) {
        try{
            Estudio estudio = new Estudio();
            estudio.setFechaEstudio(fechaInicio);
            estudio.setIdentificador(identificadorEstudio);
            estudio.setTipoEncuesta(TipoEncuesta.ENCUESTA_ASC_DESC_ABORDO);
            estudio.setModo(modo);
            aDabordoServicio.addEstudio(estudio);
            return estudio;
        }catch (Exception e){
            return null;
        }
    }


    private Map<Integer, List<CuadroEncuesta>> encontrarPaqueteDatosEncuesta(Date fechaInicio,Date fechaFin, String servicio, AuxNumBus numBus, Map<Integer, List<CuadroEncuesta>> paquetesEncuesta) {
        int idPaquete = paquetesEncuesta.size()+1;
        List<CuadroEncuesta> encuestas = encuestaAscDescServicio.getEncuestasByFechaAndServicio(fechaInicio,fechaFin, servicio,numBus.getNumBus(),numBus.getRecorrido());
        if(noSeRepitePuerta(encuestas)){
            paquetesEncuesta.put(idPaquete,encuestas);
        }else{
                //Anñadir logica cuando se repite
            List<CuadroEncuesta> encuestasOrdenadas = encuestaAscDescServicio.getEncuestasByFechaAndServicioOrderTime(fechaInicio,fechaFin, servicio,numBus.getNumBus(),numBus.getRecorrido());
            List<CuadroEncuesta> seleccionEncuestas = new ArrayList<>();
            List<Integer> numeroPuertas = new ArrayList<>();
            for(int x =0; x<encuestasOrdenadas.size();x++){
                CuadroEncuesta cuadro = encuestasOrdenadas.get(x);
                    if(!numeroPuertas.contains(cuadro.getNum_puerta())){
                        numeroPuertas.add(cuadro.getNum_puerta());
                        seleccionEncuestas.add(cuadro);
                        if(x+1 == encuestasOrdenadas.size() ){
                            paquetesEncuesta.put(idPaquete,copiarLista(seleccionEncuestas));
                        }
                    }else{
                        paquetesEncuesta.put(idPaquete,copiarLista(seleccionEncuestas));
                        idPaquete = idPaquete +1;
                        numeroPuertas.clear();
                        seleccionEncuestas.clear();
                        x--;
                    }
            }
        }
        return paquetesEncuesta;
    }

    private List<CuadroEncuesta> copiarLista(List<CuadroEncuesta> seleccionEncuestas) {
        List<CuadroEncuesta> nuevaLista = new ArrayList<>();
        for(CuadroEncuesta cuadroEncuesta:seleccionEncuestas){
            nuevaLista.add(cuadroEncuesta);
        }
        return nuevaLista;
    }

    private boolean noSeRepitePuerta(List<CuadroEncuesta> encuestas) {
        if(encuestas.size()>0){
            CuadroEncuesta encuA = encuestas.get(0);
            for(int i=1;i<encuestas.size();i++){
                CuadroEncuesta encuB = encuestas.get(i);
                if(encuA.getNum_puerta()== encuB.getNum_puerta()){
                    return false;
                }
                encuA = encuB;
            }
        }

        return true;
    }
}
