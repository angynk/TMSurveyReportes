package com.tmTransmiSurvey.model.dao.apoyo;

import com.tmTransmiSurvey.model.entity.apoyo.Estacion;
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

    public List<Estacion> encontrarTodasLasEstaciones(String modo) {
        Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(Estacion.class);
        criteria.add(Restrictions.eq("modo",modo));
        criteria.addOrder(Order.asc("nombre"));
        return  criteria.list();
    }

    public Estacion encontrarEstacionByNombre(String estacion) {
        Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(Estacion.class);
        criteria.add(Restrictions.eq("nombre",estacion));
        return (Estacion) criteria.uniqueResult();
    }

    public void deleteAll(String modo) {
        getSessionFactory().getCurrentSession().createSQLQuery("DELETE FROM ts_estacion WHERE modo= '"+modo+"'").executeUpdate();
    }

    public void addEstacion(Estacion estacion) {
        getSessionFactory().getCurrentSession().save(estacion);
    }

    public Estacion encontrarEstacionByNombreAndModo(String nombre, String modo) {
        Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(Estacion.class);
        criteria.add(Restrictions.eq("nombre",nombre));
        criteria.add(Restrictions.eq("modo",modo));
        return (Estacion) criteria.uniqueResult();
    }

    public void updateEstacion(Estacion estacionSeleccionado) {
        getSessionFactory().getCurrentSession().update(estacionSeleccionado);
    }

    public void deleteEstacion(Estacion estacionSeleccionado) {
        getSessionFactory().getCurrentSession().delete(estacionSeleccionado);
    }
}
