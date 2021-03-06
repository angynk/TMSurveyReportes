package com.tmTransmiSurvey.model.dao.procesamiento;

import com.tmTransmiSurvey.model.entity.procesamiento.Estudio;
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
public class EstudioDao {

    @Autowired
    private SessionFactory sessionFactory;


    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void addEstudio(Estudio estudio) {
        Serializable save = getSessionFactory().getCurrentSession().save(estudio);

    }

    public List<Estudio> getEstudios(String encuesta, String modo) {
        Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(Estudio.class);
        criteria.add(Restrictions.eq("tipoEncuesta", encuesta));
        criteria.add(Restrictions.eq("modo", modo));
        return (List<Estudio>) criteria.list();
    }

    public void eliminarEstudio(Estudio selectedEstudio) {
        getSessionFactory().getCurrentSession().delete(selectedEstudio);
    }
}
