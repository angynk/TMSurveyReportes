package com.tmTransmiSurvey.controller.processor;

import com.tmTransmiSurvey.controller.processor.excelDEF.EncuestaConteoDEF;
import com.tmTransmiSurvey.controller.servicios.EncuestaAscDescServicio;
import com.tmTransmiSurvey.controller.servicios.ServicioEstacionServicio;
import com.tmTransmiSurvey.controller.util.ExcelUtilProcessor;
import com.tmTransmiSurvey.controller.util.PathFiles;
import com.tmTransmiSurvey.model.entity.apoyo.Estacion;
import com.tmTransmiSurvey.model.entity.apoyo.ServicioTs;
import com.tmTransmiSurvey.model.entity.base.CoDespachosEncuesta;
import com.tmTransmiSurvey.model.entity.base.RegistroConteoDespacho;
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

@Service("ExportarConteoProcessor")
public class ExportarConteoProcessor {


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
        List<CoDespachosEncuesta> encuestasByFechaAndServicio = encuestaAscDescServicio.getConteoEncuestasByFechaAndEstacion(fechaInicio, fechaFin, estacion);
        createExcelDiaADia(encuestasByFechaAndServicio);
        return true;
    }



    private void createExcelDiaADia(List<CoDespachosEncuesta> encuestas){
        try {
            File file = new File(PathFiles.PATH+""+PathFiles.CO_DESPACHO);
            file.createNewFile();
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet worksheet = workbook.createSheet("Datos");
            crearHeader(worksheet);
            crearRowsIniciales(worksheet);
            crearRowsContenido(worksheet,workbook,encuestas);
            FileOutputStream outFile =new FileOutputStream(PathFiles.PATH+""+PathFiles.CO_DESPACHO);
            workbook.write(outFile);
            outFile.close();
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        } catch (Exception e) {
        }
    }

    private void crearRowsContenido(HSSFSheet worksheet, HSSFWorkbook workbook, List<CoDespachosEncuesta> encuestas) {
        int rows = 1;
        for(int x=0; x<encuestas.size();x++){
            CoDespachosEncuesta encuesta = encuestas.get(x);
            List<RegistroConteoDespacho> registros = encuestaAscDescServicio.getRegistrosConteoByEncuesta(encuesta);
            if(registros.size()>0){
                rows = crearRowsRegistros(registros,encuesta,rows,worksheet);
            }
        }
    }

    private int crearRowsRegistros(List<RegistroConteoDespacho> registros, CoDespachosEncuesta encuesta, int rows, HSSFSheet worksheet) {
        SimpleDateFormat sdfDate = new SimpleDateFormat("dd/MM/yyyy");
        for(RegistroConteoDespacho registro : registros){
            Row rowInfo1 = worksheet.createRow(rows);
            ExcelUtilProcessor.createCellResultados(rowInfo1,sdfDate.format(encuesta.getFecha_encuesta()), EncuestaConteoDEF.col_fecha);
            ExcelUtilProcessor.createCellResultados(rowInfo1,encuesta.getAforador(), EncuestaConteoDEF.col_aforador);
            ExcelUtilProcessor.createCellResultados(rowInfo1,encuesta.getDia_semana(), EncuestaConteoDEF.col_dia_semana);
            ExcelUtilProcessor.createCellResultados(rowInfo1,encuesta.getEstacion(), EncuestaConteoDEF.col_estacion);
            ExcelUtilProcessor.createCellResultados(rowInfo1,encuesta.getServicio(), EncuestaConteoDEF.col_servicio);
            ExcelUtilProcessor.createCellResultados(rowInfo1,registro.getNum_bus(), EncuestaConteoDEF.col_num_bus);
            ExcelUtilProcessor.createCellResultados(rowInfo1,registro.getHora_despacho(), EncuestaConteoDEF.col_hora_despacho);
            rows++;
        }
        return rows;
    }

    private void crearHeader(HSSFSheet worksheet) {

    }

    private void crearRowsIniciales(HSSFSheet worksheet) {
        Row rowInfo1 = worksheet.createRow(0);
        ExcelUtilProcessor.createCellResultados(rowInfo1, EncuestaConteoDEF.fecha, EncuestaConteoDEF.col_fecha);
        ExcelUtilProcessor.createCellResultados(rowInfo1, EncuestaConteoDEF.aforador, EncuestaConteoDEF.col_aforador);
        ExcelUtilProcessor.createCellResultados(rowInfo1, EncuestaConteoDEF.diaSemana, EncuestaConteoDEF.col_dia_semana);
        ExcelUtilProcessor.createCellResultados(rowInfo1, EncuestaConteoDEF.estacion, EncuestaConteoDEF.col_estacion);
        ExcelUtilProcessor.createCellResultados(rowInfo1, EncuestaConteoDEF.servicio, EncuestaConteoDEF.col_servicio);
        ExcelUtilProcessor.createCellResultados(rowInfo1, EncuestaConteoDEF.NoBus, EncuestaConteoDEF.col_num_bus);
        ExcelUtilProcessor.createCellResultados(rowInfo1, EncuestaConteoDEF.horaDespacho, EncuestaConteoDEF.col_hora_despacho);

    }
}
