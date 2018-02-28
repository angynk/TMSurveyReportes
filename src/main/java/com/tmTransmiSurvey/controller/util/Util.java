package com.tmTransmiSurvey.controller.util;

import com.tmTransmiSurvey.model.entity.apoyo.Estacion;
import com.tmTransmiSurvey.model.entity.apoyo.ServicioTs;

import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.bind.DatatypeConverter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import static java.nio.charset.StandardCharsets.UTF_8;


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

    public static String obtenerDiaDeLaSemana(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int day = cal.get(Calendar.DAY_OF_WEEK);
        switch (day){
            case Calendar.MONDAY:
                return "Lunes";
            case Calendar.TUESDAY:
                return "Martes";
            case Calendar.WEDNESDAY:
                return "Miercoles";
            case Calendar.THURSDAY:
                return "Jueves";
            case Calendar.FRIDAY:
                return "Viernes";
            case Calendar.SATURDAY:
                return "Sabado";
            default:
                return "Domingo";
        }
    }

    public static Time obtenerFecha(String fechaTexto) {
        SimpleDateFormat parser = new SimpleDateFormat("HH:mm:ss");
        if(!fechaTexto.equals("")){
            try {
                Date date = parser.parse(fechaTexto);
                return new Time(date.getTime());
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        return null;
    }



    public static List<String> convertStringList(List<ServicioTs> estaciones) {
        List<String> lista = new ArrayList<>();
        for(ServicioTs ser:estaciones){
            lista.add(ser.getNombre());
        }
        return lista;
    }

    public static List<String> convertStringListEstaciones(List<Estacion> estacions) {
        List<String> lista = new ArrayList<>();
        for(Estacion ser:estacions){
            lista.add(ser.getNombre());
        }
        return lista;
    }

    public static List<String> obtenerSentidos() {
        List<String> sentidos = new ArrayList<>();
        sentidos.add("NORTE-SUR");
        sentidos.add("SUR-NORTE");
        sentidos.add("OCCIDENTE-ORIENTE");
        sentidos.add("ORIENTE-OCCIDENTE");
        return sentidos;
    }

    public static String md5(String data) {
        // Get the algorithm:
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        // Calculate Message Digest as bytes:
        byte[] digest = md5.digest(data.getBytes(UTF_8));
        // Convert to 32-char long String:
        return DatatypeConverter.printHexBinary(digest);
    }

    public static String findModo(String modo) {
        if(modo.equals(TipoEncuesta.MODO_TRONCAL)){
            return "tro";
        }else if(modo.equals(TipoEncuesta.MODO_TRONCAL_OD)){
            return "tro-od";
        }else if(modo.equals(TipoEncuesta.MODO_ALIMENTADOR)){
            return "ali";
        }else if(modo.equals(TipoEncuesta.MODO_ZONAL)){
            return "zon";
        }
        return "tod";
    }
}
