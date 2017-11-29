package com.tmTransmiSurvey.model.dao.base;

import com.tmTransmiSurvey.model.entity.base.CuadroEncuesta;
import com.tmTransmiSurvey.model.entity.base.RegistroEncuestaAscDesc;
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
public class RegistroEncuestaAscDescDao {
    @Autowired
    private SessionFactory sessionFactory;


    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void addRegistroEncuestaAscDesc(RegistroEncuestaAscDesc registro) {
        Serializable save = getSessionFactory().getCurrentSession().save(registro);
    }

    public List<RegistroEncuestaAscDesc> getRegistrosByEncuesta(CuadroEncuesta encuesta){
        Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(RegistroEncuestaAscDesc.class);
            criteria.add(Restrictions.eq("cuadroEncuesta",encuesta));
        return (List<RegistroEncuestaAscDesc>) criteria.list();
    }
}
