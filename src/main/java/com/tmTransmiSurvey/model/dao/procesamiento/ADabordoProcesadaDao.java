package com.tmTransmiSurvey.model.dao.procesamiento;

import com.tmTransmiSurvey.model.entity.procesamiento.ADabordoProcesada;
import com.tmTransmiSurvey.model.entity.procesamiento.Estudio;
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
public class ADabordoProcesadaDao {

    @Autowired
    private SessionFactory sessionFactory;


    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void addADabordoProcesada(ADabordoProcesada aDabordoProcesada) {
        Serializable save = getSessionFactory().getCurrentSession().save(aDabordoProcesada);

    }

    public List<ADabordoProcesada> getRecorridosByEstudio(Estudio estudio) {
        Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(ADabordoProcesada.class);
        criteria.add(Restrictions.eq("estudio", estudio));
        return criteria.list();
    }
}
