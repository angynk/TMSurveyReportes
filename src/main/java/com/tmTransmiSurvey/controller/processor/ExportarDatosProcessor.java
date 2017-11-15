package com.tmTransmiSurvey.controller.processor;

import com.tmTransmiSurvey.controller.PathFiles;
import com.tmTransmiSurvey.controller.servicios.EncuestaAscDescServicio;
import com.tmTransmiSurvey.controller.servicios.ServicioEstacionServicio;
import com.tmTransmiSurvey.model.entity.CuadroEncuesta;
import com.tmTransmiSurvey.model.entity.RegistroEncuestaAscDesc;
import com.tmTransmiSurvey.model.entity.ServicioTs;
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

@Service("ExportarProcessor")
public class ExportarDatosProcessor {

    @Autowired
    public EncuestaAscDescServicio encuestaAscDescServicio;

    @Autowired
    public ServicioEstacionServicio servicioEstacionServicio;

    public boolean exportarDatosEncuesta(Date fechaInicio, Date fechaFin, String servicio){
        List<CuadroEncuesta> encuestasByFechaAndServicio = encuestaAscDescServicio.getEncuestasByFechaAndServicio(fechaInicio, fechaFin, servicio);
        createExcelDiaADia(encuestasByFechaAndServicio);
        return true;
    }



    private void createExcelDiaADia(List<CuadroEncuesta> encuestas){
        try {
            File file = new File(PathFiles.PATH+""+PathFiles.ASC_DES_TRONCAL);
            file.createNewFile();
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet worksheet = workbook.createSheet("Datos");
            crearHeader(worksheet);
            crearRowsIniciales(worksheet);
            crearRowsContenido(worksheet,workbook,encuestas);
            FileOutputStream outFile =new FileOutputStream(PathFiles.PATH+""+PathFiles.ASC_DES_TRONCAL);
            workbook.write(outFile);
            outFile.close();
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        } catch (Exception e) {
        }
    }

    private void crearRowsContenido(HSSFSheet worksheet, HSSFWorkbook workbook, List<CuadroEncuesta> encuestas) {
        int rows = 1;
        for(int x=0; x<encuestas.size();x++){
            CuadroEncuesta encuesta = encuestas.get(x);
            List<RegistroEncuestaAscDesc> registros = encuestaAscDescServicio.getRegistrosByEncuesta(encuesta);
            if(registros.size()>0){
               rows = crearRowsRegistros(registros,encuesta,rows,worksheet);
            }
        }
    }

    private int crearRowsRegistros(List<RegistroEncuestaAscDesc> registros, CuadroEncuesta encuesta, int rows, HSSFSheet worksheet) {
        SimpleDateFormat sdfDate = new SimpleDateFormat("dd/MM/yyyy");
        for(RegistroEncuestaAscDesc registro : registros){
            Row rowInfo1 = worksheet.createRow(rows);
            ExcelUtilProcessor.createCellResultados(rowInfo1,sdfDate.format(encuesta.getFecha_encuesta()), EncuestaAscDescDEF.col_fecha);
            ExcelUtilProcessor.createCellResultados(rowInfo1,encuesta.getAforador(), EncuestaAscDescDEF.col_aforador);
            ExcelUtilProcessor.createCellResultados(rowInfo1,encuesta.getDia_semana(), EncuestaAscDescDEF.col_dia_semana);
            ExcelUtilProcessor.createCellResultados(rowInfo1,encuesta.getServicio(), EncuestaAscDescDEF.col_servicio);
            ExcelUtilProcessor.createCellNumberResultados(rowInfo1,encuesta.getRecorrido(), EncuestaAscDescDEF.col_recorrido);
            ExcelUtilProcessor.createCellResultados(rowInfo1,encuesta.getNum_bus(), EncuestaAscDescDEF.col_num_bus);
            ExcelUtilProcessor. createCellNumberResultados(rowInfo1,encuesta.getNum_puerta(), EncuestaAscDescDEF.col_num_puerta);
            ExcelUtilProcessor.createCellResultados(rowInfo1,"N", EncuestaAscDescDEF.col_observador);
            ExcelUtilProcessor.createCellResultados(rowInfo1,registro.getEstacion(), EncuestaAscDescDEF.col_estacion);
            ExcelUtilProcessor.createCellResultados(rowInfo1,registro.getHora_llegada(), EncuestaAscDescDEF.col_hora_llegada);
            ExcelUtilProcessor.createCellNumberResultados(rowInfo1,registro.getBajan(), EncuestaAscDescDEF.col_pas_bajan);
            ExcelUtilProcessor.createCellNumberResultados(rowInfo1,registro.getSuben(), EncuestaAscDescDEF.col_pas_suben);
            ExcelUtilProcessor.createCellNumberResultados(rowInfo1,registro.getQuedan(), EncuestaAscDescDEF.col_pas_quedan);
            ExcelUtilProcessor.createCellResultados(rowInfo1,registro.getHora_salida(), EncuestaAscDescDEF.col_hora_salida);
            ExcelUtilProcessor.createCellResultados(rowInfo1,registro.getObservacion(), EncuestaAscDescDEF.col_observacion);
            rows++;
        }
        return rows;
    }

    private void crearHeader(HSSFSheet worksheet) {

    }

    private void crearRowsIniciales(HSSFSheet worksheet) {
        Row rowInfo1 = worksheet.createRow(0);
        ExcelUtilProcessor.createCellResultados(rowInfo1, EncuestaAscDescDEF.fecha, EncuestaAscDescDEF.col_fecha);
        ExcelUtilProcessor.createCellResultados(rowInfo1, EncuestaAscDescDEF.aforador, EncuestaAscDescDEF.col_aforador);
        ExcelUtilProcessor.createCellResultados(rowInfo1, EncuestaAscDescDEF.diaSemana, EncuestaAscDescDEF.col_dia_semana);
        ExcelUtilProcessor.createCellResultados(rowInfo1, EncuestaAscDescDEF.servicio, EncuestaAscDescDEF.col_servicio);
        ExcelUtilProcessor.createCellResultados(rowInfo1, EncuestaAscDescDEF.recorrido, EncuestaAscDescDEF.col_recorrido);
        ExcelUtilProcessor.createCellResultados(rowInfo1, EncuestaAscDescDEF.NoBus, EncuestaAscDescDEF.col_num_bus);
        ExcelUtilProcessor.createCellResultados(rowInfo1, EncuestaAscDescDEF.NoPuerta, EncuestaAscDescDEF.col_num_puerta);
        ExcelUtilProcessor.createCellResultados(rowInfo1, EncuestaAscDescDEF.operador, EncuestaAscDescDEF.col_observador);
        ExcelUtilProcessor.createCellResultados(rowInfo1, EncuestaAscDescDEF.estacion, EncuestaAscDescDEF.col_estacion);
        ExcelUtilProcessor.createCellResultados(rowInfo1, EncuestaAscDescDEF.horaLlegada, EncuestaAscDescDEF.col_hora_llegada);
        ExcelUtilProcessor.createCellResultados(rowInfo1, EncuestaAscDescDEF.pasBajan, EncuestaAscDescDEF.col_pas_bajan);
        ExcelUtilProcessor.createCellResultados(rowInfo1, EncuestaAscDescDEF.pasSuben, EncuestaAscDescDEF.col_pas_suben);
        ExcelUtilProcessor.createCellResultados(rowInfo1, EncuestaAscDescDEF.pasQuedan, EncuestaAscDescDEF.col_pas_quedan);
        ExcelUtilProcessor.createCellResultados(rowInfo1, EncuestaAscDescDEF.horaSalida, EncuestaAscDescDEF.col_hora_salida);
        ExcelUtilProcessor.createCellResultados(rowInfo1, EncuestaAscDescDEF.observacion, EncuestaAscDescDEF.col_observacion);

    }



    public List<ServicioTs> encontrarTodosLosServicios(){
        return servicioEstacionServicio.encontrarTodosLosServicios();
    }
}
