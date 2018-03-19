package com.tmTransmiSurvey.controller.servicios;


import com.tmTransmiSurvey.model.dao.apoyo.ModoDao;
import com.tmTransmiSurvey.model.dao.apoyo.RoleDao;
import com.tmTransmiSurvey.model.dao.apoyo.UsuarioDao;
import com.tmTransmiSurvey.model.entity.apoyo.Modo;
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

    @Autowired
    public ModoDao modoDao;

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

    public UsuarioDao getUsuarioDao() {
        return usuarioDao;
    }

    public void setUsuarioDao(UsuarioDao usuarioDao) {
        this.usuarioDao = usuarioDao;
    }

    public RoleDao getRoleDao() {
        return roleDao;
    }

    public void setRoleDao(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    public ModoDao getModoDao() {
        return modoDao;
    }

    public void setModoDao(ModoDao modoDao) {
        this.modoDao = modoDao;
    }

    public List<Modo> getModosAll() {
        return modoDao.getModoAll();
    }

    public boolean updateModo(Modo modoSelected) {
            modoDao.updateModo(modoSelected);
            return true;
    }

    public boolean crearModo(Modo modoNuevo) {
        if(!modoDao.existeAbreviaturaYNombre(modoNuevo)){
            modoDao.addModo(modoNuevo);
            return true;
        }
        return false;
    }

    public void deleteModo(Modo modoSelected) {
        modoDao.deleteModo(modoSelected);
    }
}
