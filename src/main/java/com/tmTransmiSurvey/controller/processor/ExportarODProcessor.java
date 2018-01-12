package com.tmTransmiSurvey.controller.processor;

import com.tmTransmiSurvey.controller.processor.excelDEF.EncuestaAscDescDEF;
import com.tmTransmiSurvey.controller.processor.excelDEF.EncuestaOdDEF;
import com.tmTransmiSurvey.controller.servicios.ODencuestaServicio;
import com.tmTransmiSurvey.controller.servicios.ServicioEstacionServicio;
import com.tmTransmiSurvey.controller.util.ExcelUtilProcessor;
import com.tmTransmiSurvey.controller.util.PathFiles;
import com.tmTransmiSurvey.model.entity.apoyo.Estacion;
import com.tmTransmiSurvey.model.entity.apoyo.ServicioTs;
import com.tmTransmiSurvey.model.entity.base.*;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service("ExportarODProcessor")
public class ExportarODProcessor {

    @Autowired
    public ServicioEstacionServicio servicioEstacionServicio;

    @Autowired
    public ODencuestaServicio oDencuestaServicio;


    public List<Estacion> encontrarTodosLasEstaciones(String modo) {
        return servicioEstacionServicio.encontrarTodasLasEstaciones(modo);
    }

    public boolean exportarDatosEncuesta(Date fechaInicio, Date fechaFin, String estacion) {
        List<ODEncuesta> encuestas = oDencuestaServicio.encuestasbyFechaEstacion(fechaInicio,fechaFin,estacion);
        createExcelDiaADia(encuestas);
        return true;
    }

    private void createExcelDiaADia(List<ODEncuesta> encuestas) {
        try {
            File file = new File(PathFiles.PATH+""+PathFiles.OD_ENCUESTA);
            file.createNewFile();
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet worksheet = workbook.createSheet("Datos");
            crearRowsHeader(worksheet);
            crearRowsIniciales(worksheet);
            crearRowsContenido(worksheet,workbook,encuestas);
            FileOutputStream outFile =new FileOutputStream(PathFiles.PATH+""+PathFiles.OD_ENCUESTA);
            workbook.write(outFile);
            outFile.close();
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        } catch (Exception e) {
        }
    }

    private void crearRowsHeader(HSSFSheet worksheet) {
        Row rowInfo1 = worksheet.createRow(0);
        ExcelUtilProcessor.createCellResultados(rowInfo1, EncuestaOdDEF.informacionBase, EncuestaOdDEF.col_informacionBase);
        worksheet.addMergedRegion(CellRangeAddress.valueOf(EncuestaOdDEF.rango_informacionBase));
        ExcelUtilProcessor.createCellResultados(rowInfo1, EncuestaOdDEF.origenDestino, EncuestaOdDEF.col_origenDestino);
        worksheet.addMergedRegion(CellRangeAddress.valueOf(EncuestaOdDEF.rango_origenDestino));
        ExcelUtilProcessor.createCellResultados(rowInfo1, EncuestaOdDEF.transbordosA, EncuestaOdDEF.col_transbordosA);
        worksheet.addMergedRegion(CellRangeAddress.valueOf(EncuestaOdDEF.rango_transbordosA));
        ExcelUtilProcessor.createCellResultados(rowInfo1, EncuestaOdDEF.transbordosB, EncuestaOdDEF.col_transbordosB);
        worksheet.addMergedRegion(CellRangeAddress.valueOf(EncuestaOdDEF.rango_transbordosB));
        ExcelUtilProcessor.createCellResultados(rowInfo1, EncuestaOdDEF.infoComplemento, EncuestaOdDEF.col_infoComplemento);
        worksheet.addMergedRegion(CellRangeAddress.valueOf(EncuestaOdDEF.rango_infoComplemento));
    }

    private void crearRowsContenido(HSSFSheet worksheet, HSSFWorkbook workbook, List<ODEncuesta> encuestas) {
        int rows = 2;
        for(int x=0; x<encuestas.size();x++){
            ODEncuesta encuesta = encuestas.get(x);
            List<ODRegistro> registros = oDencuestaServicio.getRegistrosByEncuesta(encuesta);
            if(registros.size()>0){
                rows = crearRowsRegistros(registros,encuesta,rows,worksheet);
            }
        }
    }

    private int crearRowsRegistros(List<ODRegistro> registros, ODEncuesta encuesta, int rows, HSSFSheet worksheet) {
        SimpleDateFormat sdfDate = new SimpleDateFormat("dd/MM/yyyy");
        for(ODRegistro registro : registros){
            List<ODTransbordo> transbordos = oDencuestaServicio.getTransbordosByRegistro(registro);
            Row rowInfo1 = worksheet.createRow(rows);
            ExcelUtilProcessor.createCellResultados(rowInfo1,sdfDate.format(encuesta.getFecha_encuesta()), EncuestaOdDEF.col_fecha);
            ExcelUtilProcessor.createCellResultados(rowInfo1,encuesta.getAforador(), EncuestaOdDEF.col_aforador);
            ExcelUtilProcessor.createCellResultados(rowInfo1,encuesta.getDia_semana(), EncuestaOdDEF.col_dia_semana);
            ExcelUtilProcessor.createCellResultados(rowInfo1, encuesta.getTipo(), EncuestaOdDEF.col_tipo_encuesta);
            ExcelUtilProcessor.createCellResultados(rowInfo1,encuesta.getEstacion(), EncuestaOdDEF.col_estacion);
            ExcelUtilProcessor.createCellResultados(rowInfo1,registro.getHora_encuesta(), EncuestaOdDEF.col_hora);
            ExcelUtilProcessor.createCellResultados(rowInfo1, registro.getEstacion_origen(), EncuestaOdDEF.col_estacion_origen);
            ExcelUtilProcessor.createCellResultados(rowInfo1, registro.getEstacion_destino(), EncuestaOdDEF.col_estacion_destino);
            ExcelUtilProcessor.createCellResultados(rowInfo1, registro.getServicio_origen(), EncuestaOdDEF.col_servicio_origen);
            ExcelUtilProcessor.createCellResultados(rowInfo1, registro.getMas_transbordos().toString(), EncuestaOdDEF.col_varios_transbordos);
            ExcelUtilProcessor.createCellNumberResultados(rowInfo1, registro.getCant_viaje(), EncuestaOdDEF.col_cantidad_veces);
            ExcelUtilProcessor.createCellResultados(rowInfo1, registro.getModo_llegada(), EncuestaOdDEF.col_modo_llegada);
            if(transbordos.size()>0){
                ExcelUtilProcessor.createCellResultados(rowInfo1, transbordos.get(0).getEstacion(), EncuestaOdDEF.col_transbordo_estacionA);
                ExcelUtilProcessor.createCellResultados(rowInfo1, transbordos.get(0).getServicio(), EncuestaOdDEF.col_transbordo_servicioA);
                if(transbordos.size()>1){
                    ExcelUtilProcessor.createCellResultados(rowInfo1, transbordos.get(1).getEstacion(), EncuestaOdDEF.col_transbordo_estacionB);
                    ExcelUtilProcessor.createCellResultados(rowInfo1, transbordos.get(1).getEstacion(), EncuestaOdDEF.col_transbordo_servicioB);

                }
            }
            rows++;


        }
        return rows;
    }

    private void crearRowsIniciales(HSSFSheet worksheet) {
        Row rowInfo1 = worksheet.createRow(1);
        ExcelUtilProcessor.createCellResultados(rowInfo1, EncuestaOdDEF.fecha, EncuestaOdDEF.col_fecha);
        ExcelUtilProcessor.createCellResultados(rowInfo1, EncuestaOdDEF.aforador, EncuestaOdDEF.col_aforador);
        ExcelUtilProcessor.createCellResultados(rowInfo1, EncuestaOdDEF.diaSemana, EncuestaOdDEF.col_dia_semana);
        ExcelUtilProcessor.createCellResultados(rowInfo1, EncuestaOdDEF.tipoEncuesta, EncuestaOdDEF.col_tipo_encuesta);
        ExcelUtilProcessor.createCellResultados(rowInfo1, EncuestaOdDEF.estacion, EncuestaOdDEF.col_estacion);
        ExcelUtilProcessor.createCellResultados(rowInfo1, EncuestaOdDEF.hora, EncuestaOdDEF.col_hora);
        ExcelUtilProcessor.createCellResultados(rowInfo1, EncuestaOdDEF.estacionOrigen, EncuestaOdDEF.col_estacion_origen);
        ExcelUtilProcessor.createCellResultados(rowInfo1, EncuestaOdDEF.estaciónDestino, EncuestaOdDEF.col_estacion_destino);
        ExcelUtilProcessor.createCellResultados(rowInfo1, EncuestaOdDEF.servicioOrigen, EncuestaOdDEF.col_servicio_origen);
        ExcelUtilProcessor.createCellResultados(rowInfo1, EncuestaOdDEF.transbordoEstacionA, EncuestaOdDEF.col_transbordo_estacionA);
        ExcelUtilProcessor.createCellResultados(rowInfo1, EncuestaOdDEF.transbordoServicioA, EncuestaOdDEF.col_transbordo_servicioA);
        ExcelUtilProcessor.createCellResultados(rowInfo1, EncuestaOdDEF.transbordoEstacionB, EncuestaOdDEF.col_transbordo_estacionB);
        ExcelUtilProcessor.createCellResultados(rowInfo1, EncuestaOdDEF.transbordoServicioB, EncuestaOdDEF.col_transbordo_servicioB);
        ExcelUtilProcessor.createCellResultados(rowInfo1, EncuestaOdDEF.variosTransbordos, EncuestaOdDEF.col_varios_transbordos);
        ExcelUtilProcessor.createCellResultados(rowInfo1, EncuestaOdDEF.cantidadVeces, EncuestaOdDEF.col_cantidad_veces);
        ExcelUtilProcessor.createCellResultados(rowInfo1, EncuestaOdDEF.modoLlegada, EncuestaOdDEF.col_modo_llegada);
    }
}
