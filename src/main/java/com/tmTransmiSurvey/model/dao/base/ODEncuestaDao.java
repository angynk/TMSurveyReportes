package com.tmTransmiSurvey.model.dao.base;

import com.tmTransmiSurvey.model.entity.base.CuadroEncuesta;
import com.tmTransmiSurvey.model.entity.base.ODEncuesta;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Repository
@Transactional
public class ODEncuestaDao {

    @Autowired
    private SessionFactory sessionFactory;


    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void addODEncuesta(ODEncuesta odEncuesta) {
        Serializable save = getSessionFactory().getCurrentSession().save(odEncuesta);

    }

    public List<ODEncuesta> encuestasbyFechaEstacion(Date fechaInicio, Date fechaFin, String estacion) {
        Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(ODEncuesta.class);
        criteria.add(Restrictions.between("fecha_encuesta", fechaInicio,fechaFin));
        if(!estacion.equals("Todos")){
            criteria.add(Restrictions.eq("estacion",estacion));
        }
        return (List<ODEncuesta>) criteria.list();
    }
}
