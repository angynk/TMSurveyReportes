package com.tmTransmiSurvey.model.dao.base;

import com.tmTransmiSurvey.model.entity.base.ADPuntoEncuesta;
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
public class ADPuntoEncuestaDao {


    @Autowired
    private SessionFactory sessionFactory;


    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void addADPuntoEncuesta(ADPuntoEncuesta cuadroEncuesta) {
        Serializable save = getSessionFactory().getCurrentSession().save(cuadroEncuesta);

    }

    public List<ADPuntoEncuesta> getEncuestasByFechaAndEstacion(Date fechaInicio, Date fechaFin, String estacion) {
            Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(ADPuntoEncuesta.class);
            criteria.add(Restrictions.between("fecha_encuesta", fechaInicio,fechaFin));
            if(!estacion.equals("Todos")){
                criteria.add(Restrictions.eq("estacion",estacion));
            }
            return (List<ADPuntoEncuesta>) criteria.list();
    }
}
