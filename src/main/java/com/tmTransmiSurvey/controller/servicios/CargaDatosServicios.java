package com.tmTransmiSurvey.controller.servicios;

import com.tmTransmiSurvey.controller.util.*;
import com.tmTransmiSurvey.model.dao.apoyo.*;
import com.tmTransmiSurvey.model.entity.apoyo.Estacion;
import com.tmTransmiSurvey.model.entity.apoyo.FovCodigos;
import com.tmTransmiSurvey.model.entity.apoyo.ServicioEstacion;
import com.tmTransmiSurvey.model.entity.apoyo.ServicioTs;
import jdk.nashorn.internal.runtime.ECMAException;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

@Service("CargaDatosServicios")
public class CargaDatosServicios {

    @Autowired
    private ProcessorUtils processorUtils;

    @Autowired
    private ServicioDao servicioDao;

    @Autowired
    private EstacionDao estacionDao;

    @Autowired
    private ServicioEstacionDao servicioEstacionDao;

    @Autowired
    private TipologiaDao tipologiaDao;

    @Autowired
    private FovCodigosDao fovCodigosDao;

    @Autowired
    private ExcelExtract excelExtract;


    public String copyFile(String fileName, InputStream in) {
        processorUtils.copyFile(fileName,in, PathFiles.PATH);
        return PathFiles.PATH + fileName;
    }


    public void procesarFile(String nombre,String modo) throws Exception {
        try {

            FileInputStream fileInputStream = new FileInputStream(nombre);
            HSSFWorkbook workbook = new HSSFWorkbook(fileInputStream);
            borrarDatosBase(modo);
            procesarServicios(workbook,modo);
            procesarEstaciones(workbook,modo);
            procesarServiciosEstaciones(workbook,modo);
            if(modo.equals(TipoEncuesta.MODO_TRONCAL) || modo.equals(TipoEncuesta.MODO_TRONCAL_OD) ){
                procesarFovCodigos(workbook);
            }

            fileInputStream.close();
        } catch (FileNotFoundException e) {
            throw new Exception(e.getMessage());
        } catch (IOException e) {
            throw new Exception(e.getMessage());
        }
    }

    private void procesarFovCodigos(HSSFWorkbook workbook) {
        HSSFSheet worksheet = workbook.getSheetAt(3);

        Iterator<Row> rowIterator = worksheet.iterator();
        rowIterator.next();
        while (rowIterator.hasNext()) {

            Row row = rowIterator.next();
            if( row.getCell(0) != null ){
                if(excelExtract.getStringCellValue(row,0) != ""){
                    FovCodigos fovCodigos = new FovCodigos();
                    fovCodigos.setEstacion(excelExtract.getStringCellValue( row, CargaDatosDEF.fov_estacion));
                    fovCodigos.setServicio(excelExtract.getStringCellValue( row, CargaDatosDEF.fov_servicio));
                    fovCodigos.setSentido(excelExtract.getStringCellValue( row, CargaDatosDEF.fov_sentido));
                    fovCodigos.setCodigo(excelExtract.getStringCellValue( row, CargaDatosDEF.fov_codigo));
                    fovCodigos.setTipologia(tipologiaDao.getTipologiaByNombre(excelExtract.getStringCellValue( row, CargaDatosDEF.fov_tipologia)));
                    fovCodigosDao.agregarFovCodigo(fovCodigos);
                }else {
                    break;
                }

            }else{
                break;
            }
        }
    }

    private void borrarDatosBase(String modo) {
       servicioEstacionDao.deleteAll(modo);
        servicioDao.deleteAll(modo);
        estacionDao.deleteAll(modo);
    }

    private void procesarServiciosEstaciones(HSSFWorkbook workbook, String modo) {
        HSSFSheet worksheet = workbook.getSheetAt(2);

        Iterator<Row> rowIterator = worksheet.iterator();
        rowIterator.next();
        while (rowIterator.hasNext()) {

            Row row = rowIterator.next();
            if( row.getCell(0) != null ){
                if(excelExtract.getStringCellValue(row,0) != "") {
                    ServicioEstacion servicioEstacion = new ServicioEstacion();
                    servicioEstacion.setOrden(excelExtract.getNumericCellValue(row, CargaDatosDEF.esServ_orden));
                    servicioEstacion.setServicio(servicioDao.encontrarServicioByNombreAndModo(excelExtract.getStringCellValue(row, CargaDatosDEF.esServ_servicio),modo));
                    servicioEstacion.setEstacion(estacionDao.encontrarEstacionByNombreAndModo(excelExtract.getStringCellValue(row, CargaDatosDEF.esServ_estacion),modo));
                    if (servicioEstacion.getEstacion() != null && servicioEstacion.getServicio() != null) {
                        servicioEstacionDao.addServicioEstacion(servicioEstacion);
                    } else {
                        System.out.println("Error " + excelExtract.getStringCellValue(row, CargaDatosDEF.esServ_servicio) + " - " +
                                excelExtract.getStringCellValue(row, CargaDatosDEF.esServ_estacion));
                    }
                }else {
                    break;
                }

            }else{
                break;
            }
        }
    }

    private void procesarEstaciones(HSSFWorkbook workbook, String modo) {
        HSSFSheet worksheet = workbook.getSheetAt(1);

        Iterator<Row> rowIterator = worksheet.iterator();
        rowIterator.next();
        while (rowIterator.hasNext()) {

            Row row = rowIterator.next();
            if( row.getCell(0) != null ){
                if(excelExtract.getStringCellValue(row,0) != "") {
                    Estacion estacion = new Estacion();
                    estacion.setNombre(excelExtract.getStringCellValue(row, CargaDatosDEF.estaciones_nombre));
                    estacion.setZona(excelExtract.getStringCellValue(row, CargaDatosDEF.estaciones_zona));
                    estacion.setModo(modo);
                    estacionDao.addEstacion(estacion);
                }else {
                    break;
                }
            }else{
                break;
            }
        }
    }

    private void procesarServicios(HSSFWorkbook workbook, String modo) {
        HSSFSheet worksheet = workbook.getSheetAt(0);

        Iterator<Row> rowIterator = worksheet.iterator();
        rowIterator.next();
        while (rowIterator.hasNext()) {

            Row row = rowIterator.next();
            if( row.getCell(0) != null ){
                if(excelExtract.getStringCellValue(row,0) != "") {
                    ServicioTs servicioTs = new ServicioTs();
                    servicioTs.setNombre(excelExtract.getStringCellValue(row, CargaDatosDEF.servicios_nombre));
                    servicioTs.setTipo(modo);
                    servicioDao.addServicio(servicioTs);
                }else {
                    break;
                }
            }else{
                break;
            }
        }
    }
}
