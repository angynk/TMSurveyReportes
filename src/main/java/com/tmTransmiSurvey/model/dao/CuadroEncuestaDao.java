package com.tmTransmiSurvey.model.dao;

import com.tmTransmiSurvey.model.entity.AuxNumBus;
import com.tmTransmiSurvey.model.entity.CuadroEncuesta;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
@Transactional
public class CuadroEncuestaDao {

    @Autowired
    private SessionFactory sessionFactory;


    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void addCuadroEncuesta(CuadroEncuesta cuadroEncuesta) {
        Serializable save = getSessionFactory().getCurrentSession().save(cuadroEncuesta);

    }

    public List<CuadroEncuesta> getEncuestasByFechaAndServicio(Date fechaInicio, Date fechaFin, String servicio){
        Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(CuadroEncuesta.class);
        criteria.add(Restrictions.between("fecha_encuesta", fechaInicio,fechaFin));
        if(!servicio.equals("Todos")){
            criteria.add(Restrictions.eq("servicio",servicio));
        }
        return (List<CuadroEncuesta>) criteria.list();
    }

    public List<CuadroEncuesta> getEncuestasByFechaAndServicio(Date fechaInicio,  String servicio){
        Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(CuadroEncuesta.class);
        criteria.add(Restrictions.eq("fecha_encuesta", fechaInicio));
        if(!servicio.equals("Todos")){
            criteria.add(Restrictions.eq("servicio",servicio));
        }
        return (List<CuadroEncuesta>) criteria.list();
    }

    public List<AuxNumBus> getNumBusGroupBy(Date fechaInicio, String servicio) {
        List<AuxNumBus> resultados = new ArrayList<>();
        Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(CuadroEncuesta.class);
        criteria.add(Restrictions.eq("fecha_encuesta", fechaInicio));
        criteria.add(Restrictions.eq("servicio",servicio));
        criteria.setProjection( Projections.projectionList()
                .add( Projections.groupProperty("num_bus") )
                .add( Projections.groupProperty("recorrido") )
        );
        List<Object[]> valores = criteria.list();
        for(Object[] valor:valores){
            resultados.add(new AuxNumBus((String)valor[0],(int)valor[1]));
        }
        return resultados;
    }

    public List<CuadroEncuesta> getEncuestasByFechaAndServicio(Date fechaInicio, String servicio, String numBus, Integer recorrido) {
        Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(CuadroEncuesta.class);
        criteria.add(Restrictions.eq("fecha_encuesta", fechaInicio));
        criteria.add(Restrictions.eq("servicio",servicio));
        criteria.add(Restrictions.eq("num_bus",numBus));
        criteria.add(Restrictions.eq("recorrido",recorrido));
        criteria.addOrder(Order.asc("num_puerta"));
        return (List<CuadroEncuesta>) criteria.list();
    }
}
