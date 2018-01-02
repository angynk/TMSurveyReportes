package com.tmTransmiSurvey.model.dao.apoyo;

import com.tmTransmiSurvey.model.entity.apoyo.ServicioTs;
import com.tmTransmiSurvey.model.entity.apoyo.Tipologia;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class TipologiaDao {


    @Autowired
    private SessionFactory sessionFactory;


    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void addTipologia(Tipologia tipologia) {
        getSessionFactory().getCurrentSession().save(tipologia);
    }

    public void deleteTipologia(Tipologia tipologia) {
        getSessionFactory().getCurrentSession().delete(tipologia);
    }


    public void updateTipologia(Tipologia tipologia) {
        getSessionFactory().getCurrentSession().update(tipologia);
    }


    public List<Tipologia> getTipologiaAll() {
        Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(Tipologia.class);
        criteria.addOrder(Order.asc("nombre"));
        return  criteria.list();
    }

    public Tipologia getTipologiaByNombre(String nombre){
        Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(Tipologia.class);
        criteria.add(Restrictions.eq("nombre", nombre));
        return (Tipologia) criteria.uniqueResult();
    }
}
