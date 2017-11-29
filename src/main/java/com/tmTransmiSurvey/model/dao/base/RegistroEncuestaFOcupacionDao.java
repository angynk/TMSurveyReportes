package com.tmTransmiSurvey.model.dao.base;

import com.tmTransmiSurvey.model.entity.base.FOcupacionEncuesta;
import com.tmTransmiSurvey.model.entity.base.RegistroEncuestaFOcupacion;
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
public class RegistroEncuestaFOcupacionDao {

    @Autowired
    private SessionFactory sessionFactory;


    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void addRegFOcupacionEncuesta(RegistroEncuestaFOcupacion regFOcupacion) {
        Serializable save = getSessionFactory().getCurrentSession().save(regFOcupacion);

    }


    public List<RegistroEncuestaFOcupacion> getRegistrosByEncuesta(FOcupacionEncuesta encuesta) {
        Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(RegistroEncuestaFOcupacion.class);
        criteria.add(Restrictions.eq("fOcupacionEncuesta",encuesta));
        return (List<RegistroEncuestaFOcupacion>) criteria.list();
    }
}
