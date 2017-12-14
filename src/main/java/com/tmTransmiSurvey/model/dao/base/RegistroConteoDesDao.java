package com.tmTransmiSurvey.model.dao.base;

import com.tmTransmiSurvey.model.entity.base.CoDespachosEncuesta;
import com.tmTransmiSurvey.model.entity.base.RegistroConteoDespacho;
import com.tmTransmiSurvey.model.entity.base.RegistroEncuestaADPunto;
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
public class RegistroConteoDesDao {

    @Autowired
    private SessionFactory sessionFactory;


    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void addRegistroConteoDespacho(RegistroConteoDespacho registroConteoDespacho) {
        Serializable save = getSessionFactory().getCurrentSession().save(registroConteoDespacho);
    }

    public List<RegistroConteoDespacho> getRegistrosConteoByEncuesta(CoDespachosEncuesta encuesta) {
        Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(RegistroConteoDespacho.class);
        criteria.add(Restrictions.eq("conteoDespacho",encuesta));
        return (List<RegistroConteoDespacho>) criteria.list();
    }
}
