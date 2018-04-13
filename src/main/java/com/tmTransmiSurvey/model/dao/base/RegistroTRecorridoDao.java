package com.tmTransmiSurvey.model.dao.base;

import com.tmTransmiSurvey.model.entity.base.RegistroEncuestaAscDesc;
import com.tmTransmiSurvey.model.entity.base.RegistroTRecorridos;
import com.tmTransmiSurvey.model.entity.base.TRecorridosEncuesta;
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
public class RegistroTRecorridoDao {

    @Autowired
    private SessionFactory sessionFactory;


    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void addRegistroTRecorridoEncuesta(RegistroTRecorridos registroTRecorridos) {
        Serializable save = getSessionFactory().getCurrentSession().save(registroTRecorridos);

    }

    public List<RegistroTRecorridos> getRegistrosByEncuesta(TRecorridosEncuesta encuesta) {
        Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(RegistroTRecorridos.class);
        criteria.add(Restrictions.eq("tRecorridosEncuesta",encuesta));
        return (List<RegistroTRecorridos>) criteria.list();
    }
}
