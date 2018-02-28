package com.tmTransmiSurvey.controller.processor;


import com.tmTransmiSurvey.controller.processor.excelDEF.EncuestaFOBusDEF;
import com.tmTransmiSurvey.controller.servicios.FOBusServicio;
import com.tmTransmiSurvey.controller.servicios.ServicioEstacionServicio;
import com.tmTransmiSurvey.controller.util.ExcelUtilProcessor;
import com.tmTransmiSurvey.controller.util.PathFiles;
import com.tmTransmiSurvey.model.entity.apoyo.Estacion;
import com.tmTransmiSurvey.model.entity.base.FOBus;
import com.tmTransmiSurvey.model.entity.base.RegistroFOBus;
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

@Service("ExportarFOBusProcessor")
public class ExportarFOBusProcessor {

    @Autowired
    FOBusServicio  foBusServicio;

    @Autowired
    public ServicioEstacionServicio servicioEstacionServicio;


    public boolean exportarDatosEncuesta(Date fechaInicio, Date fechaFin) {
        List<FOBus> encuestas = foBusServicio.getDatosFrecOcupa(fechaInicio,fechaFin);
        createExcelDiaADia(encuestas);
        return true;
    }

    private void createExcelDiaADia(List<FOBus> encuestas) {
        try {
            File file = new File(PathFiles.PATH+""+PathFiles.FO_BUS);
            file.createNewFile();
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet worksheet = workbook.createSheet("Datos");
            crearRowsIniciales(worksheet);
            crearRowsContenido(worksheet,workbook,encuestas);
            FileOutputStream outFile =new FileOutputStream(PathFiles.PATH+""+PathFiles.FO_BUS);
            workbook.write(outFile);
            outFile.close();
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        } catch (Exception e) {
        }
    }

    private void crearRowsContenido(HSSFSheet worksheet, HSSFWorkbook workbook, List<FOBus> encuestas) {
        int rows = 1;
        for(int x=0; x<encuestas.size();x++){
            FOBus encuesta = encuestas.get(x);
            List<RegistroFOBus> registros = foBusServicio.getRegistrosFrecOcuByEncuesta(encuesta);
            if(registros.size()>0){
                rows = crearRowsRegistros(registros,encuesta,rows,worksheet);
            }
        }
    }

    private int crearRowsRegistros(List<RegistroFOBus> registros, FOBus encuesta, int rows, HSSFSheet worksheet) {
        SimpleDateFormat sdfDate = new SimpleDateFormat("dd/MM/yyyy");
        for(RegistroFOBus registro : registros) {
            Row rowInfo1 = worksheet.createRow(rows);
            ExcelUtilProcessor.createCellResultados(rowInfo1, sdfDate.format(encuesta.getFecha_encuesta()), EncuestaFOBusDEF.col_fecha);
            ExcelUtilProcessor.createCellResultados(rowInfo1, encuesta.getAforador(), EncuestaFOBusDEF.col_aforador);
            ExcelUtilProcessor.createCellResultados(rowInfo1, encuesta.getDia_semana(), EncuestaFOBusDEF.col_dia_semana);
            ExcelUtilProcessor.createCellResultados(rowInfo1, encuesta.getEstacion(), EncuestaFOBusDEF.col_estacion);
            ExcelUtilProcessor.createCellResultados(rowInfo1, encuesta.getSentido(), EncuestaFOBusDEF.col_sentido);
            ExcelUtilProcessor.createCellResultados(rowInfo1, registro.getHora_paso(), EncuestaFOBusDEF.col_hora_paso);
            ExcelUtilProcessor.createCellResultados(rowInfo1, registro.getCodigo(), EncuestaFOBusDEF.col_codigo);
            ExcelUtilProcessor.createCellResultados(rowInfo1, registro.getNum_bus()+"", EncuestaFOBusDEF.col_num_bus);
            rows++;
        }
        return rows;
    }

    private void crearRowsIniciales(HSSFSheet worksheet) {
        Row rowInfo1 = worksheet.createRow(0);
        ExcelUtilProcessor.createCellResultados(rowInfo1, EncuestaFOBusDEF.fecha,EncuestaFOBusDEF.col_fecha);
        ExcelUtilProcessor.createCellResultados(rowInfo1,EncuestaFOBusDEF.aforador,EncuestaFOBusDEF.col_aforador);
        ExcelUtilProcessor.createCellResultados(rowInfo1,EncuestaFOBusDEF.dia_semana,EncuestaFOBusDEF.col_dia_semana);
        ExcelUtilProcessor.createCellResultados(rowInfo1,EncuestaFOBusDEF.estacion,EncuestaFOBusDEF.col_estacion);
        ExcelUtilProcessor.createCellResultados(rowInfo1,EncuestaFOBusDEF.sentido,EncuestaFOBusDEF.col_sentido);
        ExcelUtilProcessor.createCellResultados(rowInfo1,EncuestaFOBusDEF.hora_paso,EncuestaFOBusDEF.col_hora_paso);
        ExcelUtilProcessor.createCellResultados(rowInfo1,EncuestaFOBusDEF.codigo,EncuestaFOBusDEF.col_codigo);
        ExcelUtilProcessor.createCellResultados(rowInfo1,EncuestaFOBusDEF.num_bus,EncuestaFOBusDEF.col_num_bus);
    }

    public List<Estacion> encontrarTodosLasEstaciones(String modo) {
        return servicioEstacionServicio.encontrarTodasLasEstaciones(modo);
    }
}
