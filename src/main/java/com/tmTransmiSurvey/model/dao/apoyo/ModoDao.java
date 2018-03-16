package com.tmTransmiSurvey.model.dao.apoyo;

import com.tmTransmiSurvey.model.entity.apoyo.Modo;
import com.tmTransmiSurvey.model.entity.apoyo.Role;
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
public class ModoDao {

    @Autowired
    private SessionFactory sessionFactory;


    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void addModo(Modo modo) {
        Serializable save = getSessionFactory().getCurrentSession().save(modo);

    }

    public void deleteModo(Modo modo) {
        getSessionFactory().getCurrentSession().delete(modo);
    }


    public void updateModo(Modo modo) {
        getSessionFactory().getCurrentSession().update(modo);
    }


    public List<Modo> getModoAll() {
        List list = getSessionFactory().getCurrentSession().createQuery("from  Modo ").list();
        return list;
    }

    public boolean existeAbreviaturaYNombre(Modo modoSelected) {
        Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(Modo.class);
        criteria.add(Restrictions.eq("nombre",modoSelected.getNombre()));
        criteria.add(Restrictions.eq("abreviatura",modoSelected.getAbreviatura()));
        if(criteria.uniqueResult()!=null){
            return true;
        }

        return false ;
    }
}
