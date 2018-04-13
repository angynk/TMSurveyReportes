package com.tmTransmiSurvey.model.dao.base;

import com.tmTransmiSurvey.model.entity.base.CuadroEncuesta;
import com.tmTransmiSurvey.model.entity.base.TRecorridosEncuesta;
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
public class TRecorridosEncuestaDao {


    @Autowired
    private SessionFactory sessionFactory;


    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void addTRecorridoEncuesta(TRecorridosEncuesta tRecorridosEncuesta) {
        Serializable save = getSessionFactory().getCurrentSession().save(tRecorridosEncuesta);

    }

    public List<TRecorridosEncuesta> getEncuestasByFechaAndServicio(Date fechaInicio, Date fechaFin, String servicio) {
        Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(TRecorridosEncuesta.class);
        criteria.add(Restrictions.between("fecha_encuesta", fechaInicio,fechaFin));
        if(!servicio.equals("Todos")){
            criteria.add(Restrictions.eq("servicio",servicio));
        }
        return (List<TRecorridosEncuesta>) criteria.list();
    }
}
