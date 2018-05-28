package com.tmTransmiSurvey.model.dao.apoyo;

import com.tmTransmiSurvey.model.entity.apoyo.Usuario;
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
public class UsuarioDao {
    @Autowired
    private SessionFactory sessionFactory;


    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void addUsuario(Usuario usuario) {
        Serializable save = getSessionFactory().getCurrentSession().save(usuario);

    }

    public void deleteUsuario(Usuario usuario) {
        getSessionFactory().getCurrentSession().delete(usuario);
    }


    public void updateUsuario(Usuario usuario) {
        getSessionFactory().getCurrentSession().update(usuario);
    }


    public List<Usuario> getUsuarioAll() {
        List list = getSessionFactory().getCurrentSession().createQuery("from  Usuario ").list();
        return list;
    }

    public Usuario encontrarUsuarioByNombreUsuario(String user){
        Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(Usuario.class);
        criteria.add(Restrictions.eq("usuario", user));
        criteria.add(Restrictions.eq("activo", true));
        return (Usuario) criteria.uniqueResult();
    }

}
