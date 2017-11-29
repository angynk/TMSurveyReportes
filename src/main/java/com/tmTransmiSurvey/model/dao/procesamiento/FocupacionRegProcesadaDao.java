package com.tmTransmiSurvey.model.dao.procesamiento;

import com.tmTransmiSurvey.model.entity.procesamiento.FocupacionProcesada;
import com.tmTransmiSurvey.model.entity.procesamiento.FocupacionRegProcesada;
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
public class FocupacionRegProcesadaDao {

    @Autowired
    private SessionFactory sessionFactory;


    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void addAFocupacionRegProcesada(FocupacionRegProcesada focupacionRegProcesada) {
        Serializable save = getSessionFactory().getCurrentSession().save(focupacionRegProcesada);

    }

    public List<FocupacionRegProcesada> getRegistrosByFov(FocupacionProcesada focupacionProcesada) {
        Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(FocupacionRegProcesada.class);
        criteria.add(Restrictions.eq("focupacion", focupacionProcesada));
        return criteria.list();
    }
}
