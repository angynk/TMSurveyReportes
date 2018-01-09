package com.tmTransmiSurvey.model.dao.base;

import com.tmTransmiSurvey.model.entity.base.ODRegistro;
import com.tmTransmiSurvey.model.entity.base.ODTransbordo;
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
public class ODTransbordoDao {

    @Autowired
    private SessionFactory sessionFactory;


    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void addODtransbordo(ODTransbordo odTransbordo) {
        Serializable save = getSessionFactory().getCurrentSession().save(odTransbordo);
    }


    public List<ODTransbordo> getTransbordosByRegistro(ODRegistro registro) {
        Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(ODTransbordo.class);
        criteria.add(Restrictions.eq("odRegistro",registro));
        return criteria.list();
    }
}
