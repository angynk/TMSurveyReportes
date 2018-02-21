package com.tmTransmiSurvey.model.dao.apoyo;

import com.tmTransmiSurvey.model.entity.apoyo.Estacion;
import com.tmTransmiSurvey.model.entity.apoyo.FovCodigos;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

@Repository
@Transactional
public class FovCodigosDao {

    @Autowired
    private SessionFactory sessionFactory;


    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public FovCodigos obtenerServicioPorCodigoEstacionSentido(String codigo, String estacion, String sentido) {
        Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(FovCodigos.class);
        criteria.add(Restrictions.eq("codigo",codigo));
        criteria.add(Restrictions.eq("sentido",sentido));
        List<FovCodigos> resultados = criteria.list();
        for(FovCodigos fovCodigo:resultados){
            if(estacion.contains(fovCodigo.getEstacion())){
                return fovCodigo;
            }
        }

        return  null;
    }

    public List<FovCodigos> encontrarFovByEstacion(String estacion) {
        Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(Estacion.class);
        criteria.add(Restrictions.eq("nombre",estacion));
        Estacion estacionObj = (Estacion) criteria.uniqueResult();


        Criteria criteriaFov = getSessionFactory().getCurrentSession().createCriteria(FovCodigos.class);
        criteriaFov.add(Restrictions.eq("estacion",estacionObj));
        return  criteriaFov.list();
    }

    public void eliminarFovCodigo(FovCodigos selectedFov) {
        getSessionFactory().getCurrentSession().delete(selectedFov);
    }

    public void agregarFovCodigo(FovCodigos nuevoFov) {
        Serializable save = getSessionFactory().getCurrentSession().save(nuevoFov);
    }
}
