package com.tmTransmiSurvey.controller.processor;

import com.tmTransmiSurvey.controller.PathFiles;
import com.tmTransmiSurvey.controller.servicios.EncuestaAscDescServicio;
import com.tmTransmiSurvey.controller.servicios.ServicioEstacionServicio;
import com.tmTransmiSurvey.model.entity.*;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service("ExportarADPuntoProcessor")
public class ExportarADPuntoProcessor {


    @Autowired
    public ServicioEstacionServicio servicioEstacionServicio;

    @Autowired
    public EncuestaAscDescServicio encuestaAscDescServicio;



    public List<Estacion> encontrarTodosLasEstaciones(){
        return servicioEstacionServicio.encontrarTodasLasEstaciones();
    }

    public List<Estacion> encontrarTodosLasEstaciones(String modo){
        return servicioEstacionServicio.encontrarTodasLasEstaciones(modo);
    }

    public List<ServicioTs> encontrarTodosLosServicios(){
        return servicioEstacionServicio.encontrarTodosLosServicios();
    }

    public boolean exportarDatosEncuesta(Date fechaInicio, Date fechaFin, String estacion){
        List<ADPuntoEncuesta> encuestasByFechaAndServicio = encuestaAscDescServicio.getEncuestasByFechaAndEstacion(fechaInicio, fechaFin, estacion);
        createExcelDiaADia(encuestasByFechaAndServicio);
        return true;
    }



    private void createExcelDiaADia(List<ADPuntoEncuesta> encuestas){
        try {
            File file = new File(PathFiles.PATH+""+PathFiles.ASC_DES_PUNTO);
            file.createNewFile();
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet worksheet = workbook.createSheet("Datos");
            crearHeader(worksheet);
            crearRowsIniciales(worksheet);
            crearRowsContenido(worksheet,workbook,encuestas);
            FileOutputStream outFile =new FileOutputStream(PathFiles.PATH+""+PathFiles.ASC_DES_PUNTO);
            workbook.write(outFile);
            outFile.close();
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        } catch (Exception e) {
        }
    }

    private void crearRowsContenido(HSSFSheet worksheet, HSSFWorkbook workbook, List<ADPuntoEncuesta> encuestas) {
        int rows = 1;
        for(int x=0; x<encuestas.size();x++){
            ADPuntoEncuesta encuesta = encuestas.get(x);
            List<RegistroEncuestaADPunto> registros = encuestaAscDescServicio.getRegistrosByEncuesta(encuesta);
            if(registros.size()>0){
                rows = crearRowsRegistros(registros,encuesta,rows,worksheet);
            }
        }
    }

    private int crearRowsRegistros(List<RegistroEncuestaADPunto> registros, ADPuntoEncuesta encuesta, int rows, HSSFSheet worksheet) {
        SimpleDateFormat sdfDate = new SimpleDateFormat("dd/MM/yyyy");
        for(RegistroEncuestaADPunto registro : registros){
            Row rowInfo1 = worksheet.createRow(rows);
            ExcelUtilProcessor.createCellResultados(rowInfo1,sdfDate.format(encuesta.getFecha_encuesta()), EncuestaAscDesPDEF.col_fecha);
            ExcelUtilProcessor.createCellResultados(rowInfo1,encuesta.getAforador(), EncuestaAscDesPDEF.col_aforador);
            ExcelUtilProcessor.createCellResultados(rowInfo1,encuesta.getDia_semana(), EncuestaAscDesPDEF.col_dia_semana);
            ExcelUtilProcessor.createCellResultados(rowInfo1,encuesta.getEstacion(), EncuestaAscDesPDEF.col_estacion);
            ExcelUtilProcessor.createCellResultados(rowInfo1,encuesta.getServicio(), EncuestaAscDesPDEF.col_servicio);
            ExcelUtilProcessor.createCellResultados(rowInfo1,registro.getNum_bus(), EncuestaAscDesPDEF.col_num_bus);
            ExcelUtilProcessor.createCellResultados(rowInfo1,registro.getHora_llegada(), EncuestaAscDesPDEF.col_hora_llegada);
            ExcelUtilProcessor.createCellNumberResultados(rowInfo1,registro.getPas_bajan(), EncuestaAscDesPDEF.col_pas_bajan);
            ExcelUtilProcessor.createCellNumberResultados(rowInfo1,registro.getPas_suben(), EncuestaAscDesPDEF.col_pas_suben);
            ExcelUtilProcessor.createCellNumberResultados(rowInfo1,12, EncuestaAscDesPDEF.col_pas_quedan);
            ExcelUtilProcessor.createCellResultados(rowInfo1,registro.getHora_salida(), EncuestaAscDesPDEF.col_hora_salida);
//            ExcelUtilProcessor.createCellResultados(rowInfo1,registro.getObservacion(), EncuestaAscDesPDEF.col_observacion);
            rows++;
        }
        return rows;
    }

    private void crearHeader(HSSFSheet worksheet) {

    }

    private void crearRowsIniciales(HSSFSheet worksheet) {
        Row rowInfo1 = worksheet.createRow(0);
        ExcelUtilProcessor.createCellResultados(rowInfo1, EncuestaAscDesPDEF.fecha, EncuestaAscDesPDEF.col_fecha);
        ExcelUtilProcessor.createCellResultados(rowInfo1, EncuestaAscDesPDEF.aforador, EncuestaAscDesPDEF.col_aforador);
        ExcelUtilProcessor.createCellResultados(rowInfo1, EncuestaAscDesPDEF.diaSemana, EncuestaAscDesPDEF.col_dia_semana);
        ExcelUtilProcessor.createCellResultados(rowInfo1, EncuestaAscDesPDEF.estacion, EncuestaAscDesPDEF.col_estacion);
        ExcelUtilProcessor.createCellResultados(rowInfo1, EncuestaAscDesPDEF.servicio, EncuestaAscDesPDEF.col_servicio);
        ExcelUtilProcessor.createCellResultados(rowInfo1, EncuestaAscDesPDEF.NoBus, EncuestaAscDesPDEF.col_num_bus);
        ExcelUtilProcessor.createCellResultados(rowInfo1, EncuestaAscDesPDEF.horaLlegada, EncuestaAscDesPDEF.col_hora_llegada);
        ExcelUtilProcessor.createCellResultados(rowInfo1, EncuestaAscDesPDEF.pasBajan, EncuestaAscDesPDEF.col_pas_bajan);
        ExcelUtilProcessor.createCellResultados(rowInfo1, EncuestaAscDesPDEF.pasSuben, EncuestaAscDesPDEF.col_pas_suben);
        ExcelUtilProcessor.createCellResultados(rowInfo1, EncuestaAscDesPDEF.pasQuedan, EncuestaAscDesPDEF.col_pas_quedan);
        ExcelUtilProcessor.createCellResultados(rowInfo1, EncuestaAscDesPDEF.horaSalida, EncuestaAscDesPDEF.col_hora_salida);
        ExcelUtilProcessor.createCellResultados(rowInfo1, EncuestaAscDesPDEF.observacion, EncuestaAscDesPDEF.col_observacion);

    }

}
