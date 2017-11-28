package com.tmTransmiSurvey.controller.processor;

import com.tmTransmiSurvey.controller.LogDatos;
import com.tmTransmiSurvey.controller.TipoEncuesta;
import com.tmTransmiSurvey.controller.TipoLog;
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
    private boolean procesamientoExitoso;


    public List<LogDatos> procesarDatosEncuesta(Date fechaInicio, Date fechaFin, String servicio, String modo, String identificadorEstudio) {
          procesamientoExitoso = true;
          List<LogDatos> logDatos = new ArrayList<>();
          logDatos.add(new LogDatos("<<Procesamiento Encuesta "+TipoEncuesta.ENCUESTA_ASC_DESC_ABORDO+">>", TipoLog.INFO));
          franjas = aDabordoServicio.obtenerFranjas();
          if(franjas.size()==0){
              logDatos.add(new LogDatos("No existe informaciòn sobre franja horarias", TipoLog.ERROR));
          }
          List<AuxNumBus> recorridos =  encuestaAscDescServicio.getNumBusGroupBy(fechaInicio,fechaFin, servicio);
          Map<Integer,List<CuadroEncuesta>> paquetesEncuesta = new HashMap<Integer,List<CuadroEncuesta>>();
          for(AuxNumBus numBus: recorridos){
              paquetesEncuesta = encontrarPaqueteDatosEncuesta(fechaInicio,fechaFin,servicio,numBus,paquetesEncuesta);
          }
        logDatos = procesarPaquetesDatos(paquetesEncuesta,fechaInicio,modo,identificadorEstudio,logDatos);
        logDatos.add(new LogDatos("<<Finalizaciòn del procesamiento>>", TipoLog.INFO));
       return logDatos;
    }

    private List<LogDatos> procesarPaquetesDatos(Map<Integer, List<CuadroEncuesta>> paquetesEncuesta, Date fechaInicio, String modo, String identificadorEstudio, List<LogDatos> logDatos) {

        if(paquetesEncuesta.size()>0){
            Estudio estudio = crearEstudio(fechaInicio,modo,identificadorEstudio);
            if(estudio!=null){
                for (Map.Entry<Integer, List<CuadroEncuesta>> entry : paquetesEncuesta.entrySet())
                {
                    int recorrido = entry.getKey();
                    List<CuadroEncuesta> encuestas =entry.getValue();
                    procesarRecorrido(recorrido,encuestas,estudio,logDatos);
                }
            }else{
                procesamientoExitoso = false;
                logDatos.add(new LogDatos("No se pudo crear el estudio", TipoLog.ERROR));
                return logDatos;
            }
        }else{
            procesamientoExitoso = false;
            logDatos.add(new LogDatos("No se encontro información sobre el estudio seleccionado", TipoLog.ERROR));
        }

        return logDatos;

    }

    private void procesarRecorrido(int recorrido, List<CuadroEncuesta> encuestas, Estudio estudio, List<LogDatos> logDatos) {
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
        }else{
            procesamientoExitoso = false;
            logDatos.add(new LogDatos("No hay registros sobre el estudio seleccionado", TipoLog.ERROR));
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

        return nuevoRegistro;
    }

    private String obtenerFranjaHoraria(Time horaSalida) {
        for(TipoFranja franja:franjas){
            Time horaInicio = Util.obtenerFecha(franja.getHoraInicio());
            Time horaFin = Util.obtenerFecha(franja.getHoraFin());
            if(despuesHoraInicio(horaSalida,horaInicio) && antesHoraFin(horaSalida,horaFin)){
                return franja.getNombre();
            }
        }
        return "NA";

    }

    private boolean antesHoraFin(Date horaSalida, Date horaFin) {
        if(horaSalida.compareTo(horaFin) == 0 || horaSalida.before(horaFin)) return true;
        return false;
    }

    private boolean despuesHoraInicio(Date horaSalida, Date horaInicio) {
        if(horaSalida.compareTo(horaInicio) == 0 || horaSalida.after(horaInicio)) return true;
        return false;
    }

    private ADabordoRegProcesada insertarRegistro(String estacion, String horaLLegada,String horaSalida, ADabordoProcesada aDabordoProcesada, int numPasajeros) {
        ADabordoRegProcesada nuevoRegistro = new ADabordoRegProcesada();
        nuevoRegistro.setPasBus(numPasajeros);
        nuevoRegistro.setEstacion(estacion);
        nuevoRegistro.setHoraLlegada(convertirATime(horaLLegada));
        nuevoRegistro.setHoraSalida(convertirATime(horaSalida));
        nuevoRegistro.setAdBase(aDabordoProcesada);
        nuevoRegistro.setFranja(obtenerFranjaHoraria(nuevoRegistro.getHoraSalida()));

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

    public boolean isProcesamientoExitoso() {
        return procesamientoExitoso;
    }

    public void setProcesamientoExitoso(boolean procesamientoExitoso) {
        this.procesamientoExitoso = procesamientoExitoso;
    }
}
