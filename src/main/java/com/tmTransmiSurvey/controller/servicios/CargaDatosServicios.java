package com.tmTransmiSurvey.controller.servicios;

import com.tmTransmiSurvey.controller.util.CargaDatosDEF;
import com.tmTransmiSurvey.controller.util.ExcelExtract;
import com.tmTransmiSurvey.controller.util.PathFiles;
import com.tmTransmiSurvey.controller.util.ProcessorUtils;
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


    public void procesarFile(String nombre) throws Exception {
        try {

            FileInputStream fileInputStream = new FileInputStream(nombre);
            HSSFWorkbook workbook = new HSSFWorkbook(fileInputStream);
            borrarDatosBase();
            procesarServicios(workbook);
            procesarEstaciones(workbook);
            procesarServiciosEstaciones(workbook);
            procesarFovCodigos(workbook);

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
                FovCodigos fovCodigos = new FovCodigos();
                fovCodigos.setEstacion(excelExtract.getStringCellValue( row, CargaDatosDEF.fov_estacion));
                fovCodigos.setServicio(excelExtract.getStringCellValue( row, CargaDatosDEF.fov_servicio));
                fovCodigos.setSentido(excelExtract.getStringCellValue( row, CargaDatosDEF.fov_sentido));
                fovCodigos.setCodigo(excelExtract.getStringCellValue( row, CargaDatosDEF.fov_codigo));
                fovCodigos.setTipologia(tipologiaDao.getTipologiaByNombre(excelExtract.getStringCellValue( row, CargaDatosDEF.fov_tipologia)));
                fovCodigosDao.agregarFovCodigo(fovCodigos);
            }else{
                break;
            }
        }
    }

    private void borrarDatosBase() {
        servicioEstacionDao.deleteAll();
        servicioDao.deleteAll();
        estacionDao.deleteAll();
    }

    private void procesarServiciosEstaciones(HSSFWorkbook workbook) {
        HSSFSheet worksheet = workbook.getSheetAt(2);

        Iterator<Row> rowIterator = worksheet.iterator();
        rowIterator.next();
        while (rowIterator.hasNext()) {

            Row row = rowIterator.next();
            if( row.getCell(0) != null ){
                ServicioEstacion servicioEstacion = new ServicioEstacion();
                servicioEstacion.setOrden(excelExtract.getNumericCellValue(row,CargaDatosDEF.esServ_orden));
                servicioEstacion.setServicio(servicioDao.encontrarServicioByNombre(excelExtract.getStringCellValue( row, CargaDatosDEF.esServ_servicio)));
                servicioEstacion.setEstacion(estacionDao.encontrarEstacionByNombre(excelExtract.getStringCellValue( row, CargaDatosDEF.esServ_estacion)));
                if(servicioEstacion.getEstacion()!=null && servicioEstacion.getServicio()!=null){
                    servicioEstacionDao.addServicioEstacion(servicioEstacion);
                }else{
                    System.out.println("Error "+ excelExtract.getStringCellValue( row, CargaDatosDEF.esServ_servicio)+" - "+
                            excelExtract.getStringCellValue( row, CargaDatosDEF.esServ_estacion));
                }


            }else{
                break;
            }
        }
    }

    private void procesarEstaciones(HSSFWorkbook workbook) {
        HSSFSheet worksheet = workbook.getSheetAt(1);

        Iterator<Row> rowIterator = worksheet.iterator();
        rowIterator.next();
        while (rowIterator.hasNext()) {

            Row row = rowIterator.next();
            if( row.getCell(0) != null ){

                Estacion estacion = new Estacion();
                estacion.setNombre(excelExtract.getStringCellValue( row, CargaDatosDEF.estaciones_nombre));
                estacion.setZona(excelExtract.getStringCellValue( row, CargaDatosDEF.estaciones_zona));
                estacion.setModo(excelExtract.getStringCellValue( row, CargaDatosDEF.estaciones_modo));
                estacionDao.addEstacion(estacion);
            }else{
                break;
            }
        }
    }

    private void procesarServicios(HSSFWorkbook workbook) {
        HSSFSheet worksheet = workbook.getSheetAt(0);

        Iterator<Row> rowIterator = worksheet.iterator();
        rowIterator.next();
        while (rowIterator.hasNext()) {

            Row row = rowIterator.next();
            if( row.getCell(0) != null ){

                ServicioTs servicioTs = new ServicioTs();
                servicioTs.setNombre(excelExtract.getStringCellValue( row, CargaDatosDEF.servicios_nombre));
                servicioTs.setTipo(excelExtract.getStringCellValue( row, CargaDatosDEF.servicios_tipo));
                servicioDao.addServicio(servicioTs);
            }else{
                break;
            }
        }
    }
}
