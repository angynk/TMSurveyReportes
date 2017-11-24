package com.tmTransmiSurvey.controller;

import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;


public class Util {

    public static HttpSession getSession(){
        return (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
    }

    public static HttpServletRequest getRequest(){
        return (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
    }

    public static List<String> listaDePeriocidad(){
        List<String> periocidad = new ArrayList<>();
        periocidad.add("Habil");
        periocidad.add("Festivo");
        periocidad.add("Sabado");
        return periocidad;
    }

    public static List<String> listaDeTipologia(){
        List<String> tipologia = new ArrayList<>();
        tipologia.add("ART");
        tipologia.add("BI");
        tipologia.add("DEF");
        return tipologia;
    }

    public static List<String> listaDeTipoGrafica(){
        List<String> tipoGrafica = new ArrayList<>();
        tipoGrafica.add("Con Saltos");
        tipoGrafica.add("Con Repeticiones");
        return tipoGrafica;
    }

    public static void descargarArchivo(String path,String nombreFile) throws IOException {
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpServletResponse response = (HttpServletResponse) fc.getExternalContext().getResponse();
        File file = new File(path);
        file.createNewFile();
        FileInputStream fileIn = new FileInputStream(file);
        ServletOutputStream out = response.getOutputStream();
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment; filename="+nombreFile);


        byte[] outputByte = new byte[4096];
        while(fileIn.read(outputByte, 0, 4096) != -1)
        {
            out.write(outputByte, 0, 4096);
        }
        fileIn.close();
        out.flush();
        out.close();
        out.flush();
        try {
            if (out != null) {
                out.close();
            }
            FacesContext.getCurrentInstance().responseComplete();
        } catch (IOException e) {

        }
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
