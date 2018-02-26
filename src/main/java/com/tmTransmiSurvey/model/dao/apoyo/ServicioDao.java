package com.tmTransmiSurvey.model.dao.apoyo;

import com.tmTransmiSurvey.model.entity.apoyo.ServicioTs;
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
public class ServicioDao {

    @Autowired
    private SessionFactory sessionFactory;


    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List<ServicioTs> encontrarTodosLosServicios(){
        Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(ServicioTs.class);
        criteria.addOrder(Order.asc("nombre"));
        return  criteria.list();
    }

    public List<ServicioTs> encontrarTodosLosServicios(String modo){
        Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(ServicioTs.class);
        criteria.add(Restrictions.eq("tipo", modo));
        criteria.addOrder(Order.asc("nombre"));
        return  criteria.list();
    }

    public ServicioTs encontrarServicioByNombre(String servicio) {
        Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(ServicioTs.class);
        criteria.add(Restrictions.eq("nombre", servicio));
        return (ServicioTs) criteria.uniqueResult();
    }

    public void deleteAll(String modo) {
        getSessionFactory().getCurrentSession().createSQLQuery("DELETE * FROM ts_servicio WHERE tipo = "+modo).executeUpdate();
    }

    public void addServicio(ServicioTs servicioTs) {
        getSessionFactory().getCurrentSession().save(servicioTs);
    }
}
