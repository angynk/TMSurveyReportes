package com.tmTransmiSurvey.model.dao.base;


import com.tmTransmiSurvey.model.entity.base.CoDespachosEncuesta;
import com.tmTransmiSurvey.model.entity.base.CuadroEncuesta;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Repository
@Transactional
public class CoDespachosEncuestaDao {

    @Autowired
    private SessionFactory sessionFactory;


    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void addConteoDespacho(CoDespachosEncuesta coDespachosEncuesta) {
        Serializable save = getSessionFactory().getCurrentSession().save(coDespachosEncuesta);
    }

    public List<CoDespachosEncuesta> getConteoEncuestasByFechaAndEstacion(Date fechaInicio, Date fechaFin, String estacion) {
        Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(CoDespachosEncuesta.class);
        criteria.add(Restrictions.between("fecha_encuesta", fechaInicio,fechaFin));
        criteria.add(Restrictions.eq("estacion",estacion));
        criteria.addOrder(Order.asc("servicio"));
        return (List<CoDespachosEncuesta>) criteria.list();
    }
}
