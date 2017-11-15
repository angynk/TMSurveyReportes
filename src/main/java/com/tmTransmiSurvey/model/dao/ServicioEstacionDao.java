package com.tmTransmiSurvey.model.dao;

import com.tmTransmiSurvey.model.entity.ServicioEstacion;
import com.tmTransmiSurvey.model.entity.ServicioTs;
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
public class ServicioEstacionDao {

    @Autowired
    private SessionFactory sessionFactory;


    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    public List<ServicioEstacion> encontrarEstacionesAsociadas(ServicioTs servicioTs) {
        Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(ServicioEstacion.class);
       criteria.add(Restrictions.eq("servicio", servicioTs));
        criteria.addOrder(Order.asc("orden"));
        return  criteria.list();
    }

    public List<ServicioEstacion> encontrarTodo() {
        Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(ServicioEstacion.class);
        criteria.addOrder(Order.asc("servicio"));
        criteria.addOrder(Order.asc("orden"));
        return  criteria.list();
    }
}
