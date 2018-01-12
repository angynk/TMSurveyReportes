package com.tmTransmiSurvey.controller.processor.excelDEF;

/**
 * Created by user on 09/01/2018.
 */
public class EncuestaOdDEF {

    //Columns
    public static int col_fecha = 0;
    public static int col_aforador = 1;
    public static int col_dia_semana = 2;
    public static int col_tipo_encuesta = 3;
    public static int col_estacion = 4;
    public static int col_hora = 5;
    public static int col_estacion_origen = 6;
    public static int col_estacion_destino = 7;
    public static int col_servicio_origen = 8;
    public static int col_transbordo_estacionA = 9;
    public static int col_transbordo_servicioA = 10;
    public static int col_transbordo_estacionB = 11;
    public static int col_transbordo_servicioB = 12;
    public static int col_varios_transbordos = 13;
    public static int col_cantidad_veces = 14;
    public static int col_modo_llegada = 15;

    //Valores Defecto
    public static String fecha = "Fecha";
    public static String aforador = "aforador";
    public static String diaSemana = "Dia Semana";
    public static String tipoEncuesta = "Tipo";
    public static String estacion = "Estación";
    public static String hora = "Hora";
    public static String estacionOrigen = "Origen";
    public static String estaciónDestino = "Destino";
    public static String servicioOrigen = "Servicio";
    public static String transbordoEstacionA = "Estación";
    public static String transbordoServicioA = "Servicio";
    public static String transbordoEstacionB = "Estación";
    public static String transbordoServicioB = "Servicio";
    public static String variosTransbordos = "Más de dos transbordos";
    public static String cantidadVeces = "Veces/Semana";
    public static String modoLlegada = "Modo Llegada";


    // Celdas Combinadas
    public static String informacionBase = "Información Base";
    public static String origenDestino = "Origen Destino";
    public static String transbordosA = "Transbordo No.1";
    public static String transbordosB = "Transbordo No.2";
    public static String infoComplemento = "Información Complemento";

    public static int col_informacionBase = 0;
    public static String rango_informacionBase = "A1:F1";
    public static int col_origenDestino = 6;
    public static String rango_origenDestino = "G1:I1";
    public static int col_transbordosA = 9;
    public static String rango_transbordosA = "J1:K1";
    public static int col_transbordosB = 11;
    public static String rango_transbordosB = "L1:M1";
    public static int col_infoComplemento = 13;
    public static String rango_infoComplemento = "N1:P1";
}
