package com.tmTransmiSurvey.controller.processor;

import com.tmTransmiSurvey.controller.util.ExcelUtilProcessor;
import com.tmTransmiSurvey.controller.util.PathFiles;
import com.tmTransmiSurvey.controller.processor.excelDEF.EncuestaFrecOcuDEF;
import com.tmTransmiSurvey.controller.servicios.EncuestaAscDescServicio;
import com.tmTransmiSurvey.model.entity.base.FOcupacionEncuesta;
import com.tmTransmiSurvey.model.entity.base.RegistroEncuestaFOcupacion;
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

@Service("ExportarFrecOcupacion")
public class ExportarFrecOcupacion {

    @Autowired
    public EncuestaAscDescServicio encuestaAscDescServicio;

    public boolean exportarDatosEncuesta(Date fechaInicio, Date fechaFin){
        List<FOcupacionEncuesta> encuestas = encuestaAscDescServicio.getEncuestasFrecuenciaOcupacion(fechaInicio,fechaFin);
        createExcelDiaADia(encuestas);
        return true;
    }

    private void createExcelDiaADia(List<FOcupacionEncuesta> encuestas){
        try {
            File file = new File(PathFiles.PATH+""+PathFiles.FREC_OCU);
            file.createNewFile();
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet worksheet = workbook.createSheet("Datos");
            crearHeader(worksheet);
            crearRowsIniciales(worksheet);
            crearRowsContenido(worksheet,workbook,encuestas);
            FileOutputStream outFile =new FileOutputStream(PathFiles.PATH+""+PathFiles.FREC_OCU);
            workbook.write(outFile);
            outFile.close();
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        } catch (Exception e) {
        }
    }

    private void crearRowsContenido(HSSFSheet worksheet, HSSFWorkbook workbook, List<FOcupacionEncuesta> encuestas) {
        int rows = 1;
        for(int x=0; x<encuestas.size();x++){
            FOcupacionEncuesta encuesta = encuestas.get(x);
            List<RegistroEncuestaFOcupacion> registros = encuestaAscDescServicio.getRegistrosFrecOcuByEncuesta(encuesta);
            if(registros.size()>0){
                rows = crearRowsRegistros(registros,encuesta,rows,worksheet);
            }
        }
    }

    private int crearRowsRegistros(List<RegistroEncuestaFOcupacion> registros, FOcupacionEncuesta encuesta, int rows, HSSFSheet worksheet) {
        SimpleDateFormat sdfDate = new SimpleDateFormat("dd/MM/yyyy");
        for(RegistroEncuestaFOcupacion registro : registros) {
            Row rowInfo1 = worksheet.createRow(rows);
            ExcelUtilProcessor.createCellResultados(rowInfo1, sdfDate.format(encuesta.getFecha_encuesta()), EncuestaFrecOcuDEF.col_fecha);
            ExcelUtilProcessor.createCellResultados(rowInfo1, encuesta.getAforador(), EncuestaFrecOcuDEF.col_aforador);
            ExcelUtilProcessor.createCellResultados(rowInfo1, encuesta.getDia_semana(), EncuestaFrecOcuDEF.col_dia_semana);
            ExcelUtilProcessor.createCellResultados(rowInfo1, encuesta.getZona(), EncuestaFrecOcuDEF.col_zona);
            ExcelUtilProcessor.createCellResultados(rowInfo1, encuesta.getEstacion(), EncuestaFrecOcuDEF.col_estacion);
            ExcelUtilProcessor.createCellResultados(rowInfo1, encuesta.getSentido(), EncuestaFrecOcuDEF.col_sentido);
            ExcelUtilProcessor.createCellResultados(rowInfo1, registro.getHora_paso(), EncuestaFrecOcuDEF.col_hora_paso);
            ExcelUtilProcessor.createCellResultados(rowInfo1, registro.getCodigo(), EncuestaFrecOcuDEF.col_codigo);
            ExcelUtilProcessor.createCellResultados(rowInfo1, registro.getOcupacion()+"", EncuestaFrecOcuDEF.col_ocupacion);
            rows++;
        }
        return rows;
    }

    private void crearRowsIniciales(HSSFSheet worksheet) {
        Row rowInfo1 = worksheet.createRow(0);
        ExcelUtilProcessor.createCellResultados(rowInfo1,EncuestaFrecOcuDEF.fecha,EncuestaFrecOcuDEF.col_fecha);
        ExcelUtilProcessor.createCellResultados(rowInfo1,EncuestaFrecOcuDEF.aforador,EncuestaFrecOcuDEF.col_aforador);
        ExcelUtilProcessor.createCellResultados(rowInfo1,EncuestaFrecOcuDEF.dia_semana,EncuestaFrecOcuDEF.col_dia_semana);
        ExcelUtilProcessor.createCellResultados(rowInfo1,EncuestaFrecOcuDEF.zona,EncuestaFrecOcuDEF.col_zona);
        ExcelUtilProcessor.createCellResultados(rowInfo1,EncuestaFrecOcuDEF.estacion,EncuestaFrecOcuDEF.col_estacion);
        ExcelUtilProcessor.createCellResultados(rowInfo1,EncuestaFrecOcuDEF.sentido,EncuestaFrecOcuDEF.col_sentido);
        ExcelUtilProcessor.createCellResultados(rowInfo1,EncuestaFrecOcuDEF.hora_paso,EncuestaFrecOcuDEF.col_hora_paso);
        ExcelUtilProcessor.createCellResultados(rowInfo1,EncuestaFrecOcuDEF.codigo,EncuestaFrecOcuDEF.col_codigo);
        ExcelUtilProcessor.createCellResultados(rowInfo1,EncuestaFrecOcuDEF.ocupacion,EncuestaFrecOcuDEF.col_ocupacion);
    }

    private void crearHeader(HSSFSheet worksheet) {

    }


}
