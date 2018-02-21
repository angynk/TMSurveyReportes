package com.tmTransmiSurvey.model.dao.base;

import com.tmTransmiSurvey.model.entity.base.FOBus;
import com.tmTransmiSurvey.model.entity.base.FOcupacionEncuesta;
import com.tmTransmiSurvey.model.entity.base.RegistroEncuestaFOcupacion;
import com.tmTransmiSurvey.model.entity.base.RegistroFOBus;
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
public class RegistroFOBusDao {

    @Autowired
    private SessionFactory sessionFactory;


    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void addRegistroFOBus(RegistroFOBus registroFOBus) {
        Serializable save = getSessionFactory().getCurrentSession().save(registroFOBus);

    }

    public List<RegistroFOBus> getRegistrosFrecOcuByEncuesta(FOBus encuesta) {
        Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(RegistroFOBus.class);
        criteria.add(Restrictions.eq("idFoBus",encuesta));
        return (List<RegistroFOBus>) criteria.list();
    }



}
