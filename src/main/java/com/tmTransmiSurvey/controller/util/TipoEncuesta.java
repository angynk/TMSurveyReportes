package com.tmTransmiSurvey.controller.util;

import java.util.ArrayList;
import java.util.List;


public class TipoEncuesta {

    public static String ENCUESTA_ASC_DESC_ABORDO = "Ascensos Descensos Abordo";
    public static String ENCUESTA_ASC_DESC_PUNTO = "Ascensos Descensos Punto";
    public static String ENCUESTA_FREC_OCUPACION = "Frecuencia Ocupación";

    public static String MODO_TRONCAL = "Troncal";
    public static String MODO_ALIMENTADOR = "Alimentador";


    public static List<String> listaEncuestas(){
        List<String> encuestas = new ArrayList<>();
        encuestas.add(ENCUESTA_ASC_DESC_ABORDO);
        encuestas.add(ENCUESTA_ASC_DESC_PUNTO);
        encuestas.add(ENCUESTA_FREC_OCUPACION);
        return encuestas;
    }

    public static List<String> listaModos(){
        List<String> modos = new ArrayList<>();
        modos.add(MODO_TRONCAL);
        modos.add(MODO_ALIMENTADOR);
        return modos;
    }

}
