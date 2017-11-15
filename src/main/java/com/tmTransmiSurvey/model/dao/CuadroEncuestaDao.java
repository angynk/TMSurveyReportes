package com.tmTransmiSurvey.model.dao;

import com.tmTransmiSurvey.model.entity.CuadroEncuesta;
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
}
