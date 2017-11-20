package com.tmTransmiSurvey.model.dao;


import com.tmTransmiSurvey.model.entity.ADPuntoEncuesta;
import com.tmTransmiSurvey.model.entity.CuadroEncuesta;
import com.tmTransmiSurvey.model.entity.RegistroEncuestaADPunto;
import com.tmTransmiSurvey.model.entity.RegistroEncuestaAscDesc;
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
public class RegistroEncuestaAscDesPuntoDao {

    @Autowired
    private SessionFactory sessionFactory;


    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void addADRegPuntoEncuesta(RegistroEncuestaADPunto registroEncuestaADPunto) {
        Serializable save = getSessionFactory().getCurrentSession().save(registroEncuestaADPunto);

    }



    public List<RegistroEncuestaADPunto> getRegistrosByEncuesta(ADPuntoEncuesta encuesta) {
        Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(RegistroEncuestaADPunto.class);
        criteria.add(Restrictions.eq("adPuntoEncuesta",encuesta));
        return (List<RegistroEncuestaADPunto>) criteria.list();
    }
}
