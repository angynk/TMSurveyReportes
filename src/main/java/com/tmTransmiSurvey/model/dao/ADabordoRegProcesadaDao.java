package com.tmTransmiSurvey.model.dao;

import com.tmTransmiSurvey.model.entity.ADPuntoEncuesta;
import com.tmTransmiSurvey.model.entity.ADabordoProcesada;
import com.tmTransmiSurvey.model.entity.ADabordoRegProcesada;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

@Repository
@Transactional
public class ADabordoRegProcesadaDao {

    @Autowired
    private SessionFactory sessionFactory;


    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void addADabordoRegProcesada(ADabordoRegProcesada aDabordoRegProcesada) {
        Serializable save = getSessionFactory().getCurrentSession().save(aDabordoRegProcesada);

    }

    public List<ADabordoRegProcesada> getRegistrosByRecorrido(ADabordoProcesada recorrido) {
        Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(ADabordoRegProcesada.class);
        criteria.add(Restrictions.eq("adBase", recorrido));
        return criteria.list();
    }
}
