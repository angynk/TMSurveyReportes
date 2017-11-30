package com.tmTransmiSurvey.controller.processor;


import com.tmTransmiSurvey.controller.processor.excelDEF.ProcesadoFovDEF;
import com.tmTransmiSurvey.controller.servicios.FovServicio;
import com.tmTransmiSurvey.controller.util.ExcelUtilProcessor;
import com.tmTransmiSurvey.controller.util.PathFiles;
import com.tmTransmiSurvey.controller.processor.excelDEF.ProcesadoAscDesADEF;
import com.tmTransmiSurvey.controller.servicios.ADabordoServicio;
import com.tmTransmiSurvey.controller.util.TipoEncuesta;
import com.tmTransmiSurvey.model.entity.procesamiento.*;
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
import java.util.List;

@Service("VisualizarEstudiosProcessor")
public class VisualizarEstudiosProcessor {

    @Autowired
    public ADabordoServicio aDabordoServicio;
    @Autowired
    public FovServicio fovServicio;

    public List<Estudio> getEstudios(String encuesta, String modo) {
        return aDabordoServicio.getEstudios(encuesta,modo);
    }

    public String exportarEstudio(Estudio selectedEstudio) {
        if(selectedEstudio!=null){
            String nombreEstudio = selectedEstudio.getFechaFormatted()+"-"+selectedEstudio.getIdentificador()+".xls";
            if(selectedEstudio.getTipoEncuesta().equals(TipoEncuesta.ENCUESTA_ASC_DESC_ABORDO)){
                nombreEstudio = createExcelDiaADia(selectedEstudio,nombreEstudio); 
            }else if (selectedEstudio.getTipoEncuesta().equals(TipoEncuesta.ENCUESTA_FREC_OCUPACION)){
                nombreEstudio = createExcelFOV(selectedEstudio,nombreEstudio);
            }
            
            return nombreEstudio;
        }

        return null;
    }

    private String createExcelFOV(Estudio selectedEstudio, String nombreEstudio) {
        try {
            File file = new File(PathFiles.PATH+""+nombreEstudio);
            file.createNewFile();
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet worksheet = workbook.createSheet("Datos");
            List<FocupacionProcesada> recorridos = fovServicio.getRegistrosPorEstudio(selectedEstudio);
            crearRowsInicialesFOV(worksheet);
            crearRowsContenidoFOV(worksheet,workbook,recorridos);
            FileOutputStream outFile =new FileOutputStream(PathFiles.PATH+""+nombreEstudio);
            workbook.write(outFile);
            outFile.close();
            return nombreEstudio;
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        } catch (Exception e) {
        }

        return null;
    }

    private String createExcelDiaADia(Estudio estudio,String nombreArchivo){
        try {
            File file = new File(PathFiles.PATH+""+nombreArchivo);
            file.createNewFile();
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet worksheet = workbook.createSheet("Datos");
            List<ADabordoProcesada> recorridos = aDabordoServicio.getRecorridosByEstudio(estudio);
            crearRowsIniciales(worksheet);
            crearRowsContenido(worksheet,workbook,recorridos);
            FileOutputStream outFile =new FileOutputStream(PathFiles.PATH+""+nombreArchivo);
            workbook.write(outFile);
            outFile.close();
            return nombreArchivo;
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        } catch (Exception e) {
        }

        return null;
    }

    private void crearRowsContenidoFOV(HSSFSheet worksheet, HSSFWorkbook workbook, List<FocupacionProcesada> recorridos) {
        int rowNumber = 1;
        for(FocupacionProcesada recorrido:recorridos){
            List<FocupacionRegProcesada> registrosEstaciones = fovServicio.getRegistrosByEstacion(recorrido);
            for(int y=0;y<registrosEstaciones.size();y++){
                FocupacionRegProcesada registro = registrosEstaciones.get(y);
               rowNumber = crearRowsRegistroContenido(registro,worksheet,rowNumber, y+1,recorrido);
            }

        }

    }

    private void crearRowsContenido(HSSFSheet worksheet, HSSFWorkbook workbook, List<ADabordoProcesada> recorridos) {
        int rowNumber = 1;
        for(ADabordoProcesada recorrido:recorridos){
            List<ADabordoRegProcesada> registrosEstaciones = aDabordoServicio.getRegistrosByRecorrido(recorrido);
            for(int y=0;y<registrosEstaciones.size();y++){
                ADabordoRegProcesada registro = registrosEstaciones.get(y);
                rowNumber = crearRowsRegistroContenido(registro,worksheet,rowNumber, y+1,recorrido);
            }

        }

    }

    private int crearRowsRegistroContenido(FocupacionRegProcesada registro, HSSFSheet worksheet, int rowNumber, int regNumber, FocupacionProcesada recorrido) {
        Row rowInfo1 = worksheet.createRow(rowNumber);
        SimpleDateFormat sdfDate = new SimpleDateFormat("HH:mm:ss");
        ExcelUtilProcessor.createCellResultados(rowInfo1, recorrido.getFecha().toString(), ProcesadoFovDEF.col_fecha);
        ExcelUtilProcessor.createCellResultados(rowInfo1, recorrido.getZona(), ProcesadoFovDEF.col_zona);
        ExcelUtilProcessor.createCellResultados(rowInfo1, recorrido.getEstacion(), ProcesadoFovDEF.col_estacion);
        ExcelUtilProcessor.createCellResultados(rowInfo1, recorrido.getSentido(), ProcesadoFovDEF.col_sentido);
        ExcelUtilProcessor.createCellResultados(rowInfo1, sdfDate.format(registro.getHora()), ProcesadoFovDEF.col_hora_paso);
        ExcelUtilProcessor.createCellResultados(rowInfo1, registro.getServicio(), ProcesadoFovDEF.col_servicio);
        ExcelUtilProcessor.createCellNumberResultados(rowInfo1, registro.getNumOcupacion(), ProcesadoFovDEF.col_num_ocupacion);
        ExcelUtilProcessor.createCellNumberResultados(rowInfo1, registro.getPorOcupacion(), ProcesadoFovDEF.col_por_ocupacion);
        ExcelUtilProcessor.createCellResultados(rowInfo1, registro.getIntervalo().getInicio().toString()+"-"+registro.getIntervalo().getFin().toString(), ProcesadoFovDEF.col_franja);
        return rowNumber+1;
    }

    private int crearRowsRegistroContenido(ADabordoRegProcesada registro, HSSFSheet worksheet, int rowNumber, int regNumber, ADabordoProcesada recorrido) {
        Row rowInfo1 = worksheet.createRow(rowNumber);
        SimpleDateFormat sdfDate = new SimpleDateFormat("HH:mm:ss");
        ExcelUtilProcessor.createCellNumberResultados(rowInfo1, regNumber, ProcesadoAscDesADEF.col_orden);
        ExcelUtilProcessor.createCellResultados(rowInfo1, registro.getEstacion(), ProcesadoAscDesADEF.col_estacion);
        ExcelUtilProcessor.createCellResultados(rowInfo1, registro.getFranja(), ProcesadoAscDesADEF.col_franja);
        ExcelUtilProcessor.createCellResultados(rowInfo1, sdfDate.format(registro.getHoraLlegada()), ProcesadoAscDesADEF.col_hora_llegada);
        ExcelUtilProcessor.createCellNumberResultados(rowInfo1, registro.getPasBus(), ProcesadoAscDesADEF.col_pas_bus);
        ExcelUtilProcessor.createCellResultados(rowInfo1, sdfDate.format(registro.getHoraSalida()), ProcesadoAscDesADEF.col_hora_salida);
        ExcelUtilProcessor.createCellResultados(rowInfo1, recorrido.getServicio(), ProcesadoAscDesADEF.col_servicio);
        ExcelUtilProcessor.createCellNumberResultados(rowInfo1, recorrido.getRecorrido(), ProcesadoAscDesADEF.col_recorrido);
        ExcelUtilProcessor.createCellResultados(rowInfo1, recorrido.getNumBus(), ProcesadoAscDesADEF.col_num_bus);
        ExcelUtilProcessor.createCellResultados(rowInfo1, registro.getAdBase().getFechaFormatted(), ProcesadoAscDesADEF.col_fecha);
        ExcelUtilProcessor.createCellResultados(rowInfo1, registro.getAdBase().getDiaSemana(), ProcesadoAscDesADEF.col_dia);
        return rowNumber+1;
    }

    private void crearRowsInicialesFOV(HSSFSheet worksheet) {
        Row rowInfo1 = worksheet.createRow(0);
        ExcelUtilProcessor.createCellResultados(rowInfo1, ProcesadoFovDEF.fecha, ProcesadoFovDEF.col_fecha);
        ExcelUtilProcessor.createCellResultados(rowInfo1, ProcesadoFovDEF.zona, ProcesadoFovDEF.col_zona);
        ExcelUtilProcessor.createCellResultados(rowInfo1, ProcesadoFovDEF.estacion, ProcesadoFovDEF.col_estacion);
        ExcelUtilProcessor.createCellResultados(rowInfo1, ProcesadoFovDEF.sentido, ProcesadoFovDEF.col_sentido);
        ExcelUtilProcessor.createCellResultados(rowInfo1, ProcesadoFovDEF.hora_paso, ProcesadoFovDEF.col_hora_paso);
        ExcelUtilProcessor.createCellResultados(rowInfo1, ProcesadoFovDEF.servicio, ProcesadoFovDEF.col_servicio);
        ExcelUtilProcessor.createCellResultados(rowInfo1, ProcesadoFovDEF.num_ocupacion, ProcesadoFovDEF.col_num_ocupacion);
        ExcelUtilProcessor.createCellResultados(rowInfo1, ProcesadoFovDEF.por_ocupacion, ProcesadoFovDEF.col_por_ocupacion);
        ExcelUtilProcessor.createCellResultados(rowInfo1, ProcesadoFovDEF.franja, ProcesadoFovDEF.col_franja);
    }

    private void crearRowsIniciales(HSSFSheet worksheet) {
        Row rowInfo1 = worksheet.createRow(0);
        ExcelUtilProcessor.createCellResultados(rowInfo1, ProcesadoAscDesADEF.orden, ProcesadoAscDesADEF.col_orden);
        ExcelUtilProcessor.createCellResultados(rowInfo1, ProcesadoAscDesADEF.estacion, ProcesadoAscDesADEF.col_estacion);
        ExcelUtilProcessor.createCellResultados(rowInfo1, ProcesadoAscDesADEF.franja, ProcesadoAscDesADEF.col_franja);
        ExcelUtilProcessor.createCellResultados(rowInfo1, ProcesadoAscDesADEF.hora_llegada, ProcesadoAscDesADEF.col_hora_llegada);
        ExcelUtilProcessor.createCellResultados(rowInfo1, ProcesadoAscDesADEF.pas_bus, ProcesadoAscDesADEF.col_pas_bus);
        ExcelUtilProcessor.createCellResultados(rowInfo1, ProcesadoAscDesADEF.hora_salida, ProcesadoAscDesADEF.col_hora_salida);
        ExcelUtilProcessor.createCellResultados(rowInfo1, ProcesadoAscDesADEF.servicio, ProcesadoAscDesADEF.col_servicio);
        ExcelUtilProcessor.createCellResultados(rowInfo1, ProcesadoAscDesADEF.recorrido, ProcesadoAscDesADEF.col_recorrido);
        ExcelUtilProcessor.createCellResultados(rowInfo1, ProcesadoAscDesADEF.num_bus, ProcesadoAscDesADEF.col_num_bus);
        ExcelUtilProcessor.createCellResultados(rowInfo1, ProcesadoAscDesADEF.fecha, ProcesadoAscDesADEF.col_fecha);
        ExcelUtilProcessor.createCellResultados(rowInfo1, ProcesadoAscDesADEF.dia, ProcesadoAscDesADEF.col_dia);
    }
}
