package com.tmTransmiSurvey.model.dao;

import com.tmTransmiSurvey.model.entity.ADPuntoEncuesta;
import com.tmTransmiSurvey.model.entity.ADabordoRegProcesada;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;

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
}
