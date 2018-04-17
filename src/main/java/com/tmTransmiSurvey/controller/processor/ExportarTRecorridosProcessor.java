package com.tmTransmiSurvey.controller.processor;

import com.tmTransmiSurvey.controller.processor.excelDEF.EncuestaAscDescDEF;
import com.tmTransmiSurvey.controller.processor.excelDEF.EncuestaTRecorridoDEF;
import com.tmTransmiSurvey.controller.servicios.EncuestaTiemposRecorridoServicio;
import com.tmTransmiSurvey.controller.servicios.ServicioEstacionServicio;
import com.tmTransmiSurvey.controller.util.ExcelUtilProcessor;
import com.tmTransmiSurvey.controller.util.PathFiles;
import com.tmTransmiSurvey.controller.util.Util;
import com.tmTransmiSurvey.model.entity.apoyo.ServicioTs;
import com.tmTransmiSurvey.model.entity.base.CuadroEncuesta;
import com.tmTransmiSurvey.model.entity.base.RegistroEncuestaAscDesc;
import com.tmTransmiSurvey.model.entity.base.RegistroTRecorridos;
import com.tmTransmiSurvey.model.entity.base.TRecorridosEncuesta;
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

@Service("ExportarTRecorridosProcessor")
public class ExportarTRecorridosProcessor {

    @Autowired
    public ServicioEstacionServicio servicioEstacionServicio;

    @Autowired
    public EncuestaTiemposRecorridoServicio encuestaTiemposRecorridoServicio;


    public ExportarTRecorridosProcessor() {
    }

    public boolean exportarDatosEncuesta(Date fechaInicio, Date fechaFin, String servicio){
        List<TRecorridosEncuesta> encuestasByFechaAndServicio = encuestaTiemposRecorridoServicio.getEncuestasByFechaAndServicio(fechaInicio, fechaFin, servicio);
        createExcelDiaADia(encuestasByFechaAndServicio);
        return true;
    }


    private void createExcelDiaADia(List<TRecorridosEncuesta> encuestas){
        try {
            File file = new File(PathFiles.PATH+""+PathFiles.TI_RECORRIDO);
            file.createNewFile();
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet worksheet = workbook.createSheet("Datos");
            crearRowsIniciales(worksheet);
            crearRowsContenido(worksheet,workbook,encuestas);
            FileOutputStream outFile =new FileOutputStream(PathFiles.PATH+""+PathFiles.TI_RECORRIDO);
            workbook.write(outFile);
            outFile.close();
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        } catch (Exception e) {
        }
    }

    private void crearRowsContenido(HSSFSheet worksheet, HSSFWorkbook workbook, List<TRecorridosEncuesta> encuestas) {
        int rows = 1;
        for(int x=0; x<encuestas.size();x++){
            TRecorridosEncuesta encuesta = encuestas.get(x);
            List<RegistroTRecorridos> registros = encuestaTiemposRecorridoServicio.getRegistrosByEncuesta(encuesta);
            if(registros.size()>0){
                rows = crearRowsRegistros(registros,encuesta,rows,worksheet);
            }
        }
    }

    private int crearRowsRegistros(List<RegistroTRecorridos> registros, TRecorridosEncuesta encuesta, int rows, HSSFSheet worksheet) {
        SimpleDateFormat sdfDate = new SimpleDateFormat("dd/MM/yyyy");
        for(RegistroTRecorridos registro : registros){
            Row rowInfo1 = worksheet.createRow(rows);
            ExcelUtilProcessor.createCellResultados(rowInfo1,sdfDate.format(encuesta.getFecha_encuesta()), EncuestaTRecorridoDEF.col_fecha);
            ExcelUtilProcessor.createCellResultados(rowInfo1,encuesta.getAforador(), EncuestaTRecorridoDEF.col_aforador);
            ExcelUtilProcessor.createCellResultados(rowInfo1,encuesta.getDia_semana(), EncuestaTRecorridoDEF.col_dia_semana);
            ExcelUtilProcessor.createCellResultados(rowInfo1,encuesta.getServicio(), EncuestaTRecorridoDEF.col_servicio);
            ExcelUtilProcessor.createCellNumberResultados(rowInfo1,encuesta.getRecorrido(), EncuestaTRecorridoDEF.col_recorrido);
            ExcelUtilProcessor.createCellResultados(rowInfo1,encuesta.getNum_bus(), EncuestaTRecorridoDEF.col_num_bus);
            ExcelUtilProcessor.createCellResultados(rowInfo1,registro.getEstacion(), EncuestaTRecorridoDEF.col_estacion);
            ExcelUtilProcessor.createCellResultados(rowInfo1,registro.getHora_llegada(), EncuestaTRecorridoDEF.col_hora_llegada);
            ExcelUtilProcessor.createCellResultados(rowInfo1,registro.getHora_salida(), EncuestaTRecorridoDEF.col_hora_salida);
            ExcelUtilProcessor.createCellResultados(rowInfo1, Util.convertBooleanToString(registro.isPrimera_zon_destino()), EncuestaTRecorridoDEF.col_primer_zona_destino);
            ExcelUtilProcessor.createCellResultados(rowInfo1,registro.getObservacion(), EncuestaTRecorridoDEF.col_observacion);
            rows++;
        }
        return rows;
    }


    private void crearRowsIniciales(HSSFSheet worksheet) {
        Row rowInfo1 = worksheet.createRow(0);
        ExcelUtilProcessor.createCellResultados(rowInfo1, EncuestaTRecorridoDEF.fecha, EncuestaTRecorridoDEF.col_fecha);
        ExcelUtilProcessor.createCellResultados(rowInfo1, EncuestaTRecorridoDEF.aforador, EncuestaTRecorridoDEF.col_aforador);
        ExcelUtilProcessor.createCellResultados(rowInfo1, EncuestaTRecorridoDEF.diaSemana, EncuestaTRecorridoDEF.col_dia_semana);
        ExcelUtilProcessor.createCellResultados(rowInfo1, EncuestaTRecorridoDEF.servicio, EncuestaTRecorridoDEF.col_servicio);
        ExcelUtilProcessor.createCellResultados(rowInfo1, EncuestaTRecorridoDEF.recorrido, EncuestaTRecorridoDEF.col_recorrido);
        ExcelUtilProcessor.createCellResultados(rowInfo1, EncuestaTRecorridoDEF.NoBus, EncuestaTRecorridoDEF.col_num_bus);
        ExcelUtilProcessor.createCellResultados(rowInfo1, EncuestaTRecorridoDEF.estacion, EncuestaTRecorridoDEF.col_estacion);
        ExcelUtilProcessor.createCellResultados(rowInfo1, EncuestaTRecorridoDEF.horaLlegada, EncuestaTRecorridoDEF.col_hora_llegada);
        ExcelUtilProcessor.createCellResultados(rowInfo1, EncuestaTRecorridoDEF.horaSalida, EncuestaTRecorridoDEF.col_hora_salida);
        ExcelUtilProcessor.createCellResultados(rowInfo1, EncuestaTRecorridoDEF.primerZonaDestino, EncuestaTRecorridoDEF.col_primer_zona_destino);
        ExcelUtilProcessor.createCellResultados(rowInfo1, EncuestaTRecorridoDEF.observacion, EncuestaTRecorridoDEF.col_observacion);

    }


    public List<ServicioTs> encontrarTodosLosServicios(String modo){
        return servicioEstacionServicio.encontrarTodosLosServicios(modo);
    }
}
