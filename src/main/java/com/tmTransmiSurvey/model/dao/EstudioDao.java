package com.tmTransmiSurvey.model.dao;

import com.tmTransmiSurvey.model.entity.Estudio;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;

@Repository
@Transactional
public class EstudioDao {

    @Autowired
    private SessionFactory sessionFactory;


    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void addEstudio(Estudio estudio) {
        Serializable save = getSessionFactory().getCurrentSession().save(estudio);

    }
}
