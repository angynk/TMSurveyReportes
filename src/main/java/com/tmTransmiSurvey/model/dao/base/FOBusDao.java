package com.tmTransmiSurvey.model.dao.base;

import com.tmTransmiSurvey.model.entity.base.FOBus;
import com.tmTransmiSurvey.model.entity.base.ODEncuesta;
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
public class FOBusDao {
    @Autowired
    private SessionFactory sessionFactory;


    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void addFOBus(FOBus foBus) {
        Serializable save = getSessionFactory().getCurrentSession().save(foBus);

    }

    public List<FOBus> getDatosFrecOcupa(Date fechaInicio, Date fechaFin) {
        Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(FOBus.class);
        criteria.add(Restrictions.between("fecha_encuesta", fechaInicio,fechaFin));
        return (List<FOBus>) criteria.list();
    }
}
