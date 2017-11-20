package com.tmTransmiSurvey.model.dao;

import com.tmTransmiSurvey.model.entity.Estacion;
import com.tmTransmiSurvey.model.entity.ServicioTs;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class EstacionDao {

    @Autowired
    private SessionFactory sessionFactory;


    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List<Estacion> encontrarTodasLasEstaciones() {
        Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(Estacion.class);
        criteria.addOrder(Order.asc("nombre"));
        return  criteria.list();
    }
}
