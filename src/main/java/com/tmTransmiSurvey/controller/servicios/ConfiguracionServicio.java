package com.tmTransmiSurvey.controller.servicios;


import com.tmTransmiSurvey.model.dao.apoyo.RoleDao;
import com.tmTransmiSurvey.model.dao.apoyo.UsuarioDao;
import com.tmTransmiSurvey.model.entity.apoyo.Role;
import com.tmTransmiSurvey.model.entity.apoyo.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("ConfigService")
public class ConfiguracionServicio {

    @Autowired
    public UsuarioDao usuarioDao;

    @Autowired
    public RoleDao roleDao;

    public void addUsuario(Usuario usuario) {
        usuarioDao.addUsuario(usuario);
    }

    public void deleteUsuario(Usuario usuario) {
        usuarioDao.deleteUsuario(usuario);
    }


    public void updateUsuario(Usuario usuario) {
        usuarioDao.updateUsuario(usuario);
    }


    public List<Usuario> getUsuarioAll() {
        return usuarioDao.getUsuarioAll();
    }

    public Usuario encontrarUsuarioByNombreUsuario(String user){
        return usuarioDao.encontrarUsuarioByNombreUsuario(user);
    }

    public void addURole(Role role) {
        roleDao.addURole(role);
    }

    public void deleteRole(Role role) {
        roleDao.deleteRole(role);
    }


    public void updateRole(Role role) {
        roleDao.updateRole(role);
    }


    public List<Role> getRoleAll() {
       return roleDao.getRoleAll();
    }

    public Role getRoleById(long id) {
        return roleDao.getRoleById(id);
    }
}
