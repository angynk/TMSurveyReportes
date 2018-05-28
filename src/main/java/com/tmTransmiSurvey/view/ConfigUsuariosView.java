package com.tmTransmiSurvey.view;


import com.tmTransmiSurvey.controller.servicios.ConfiguracionServicio;
import com.tmTransmiSurvey.controller.util.Util;
import com.tmTransmiSurvey.model.entity.apoyo.Role;
import com.tmTransmiSurvey.model.entity.apoyo.Usuario;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import java.util.List;

@ManagedBean(name = "configUser")
@ViewScoped
public class ConfigUsuariosView {

    private List<Usuario> usuariosRecords;
    private List<Role> roleRecords;
    private Role role;
    private String roleName;
    private long roleID;
    private Usuario usuarioSelected;
    private Usuario usuarioNuevo;
    private String nuevaContrasena;

    @ManagedProperty(value="#{ConfigService}")
    private ConfiguracionServicio configuracionServicio;

    @ManagedProperty(value="#{MessagesView}")
    private MessagesView messagesView;



    public ConfigUsuariosView() {
    }

    @PostConstruct
    public void init(){
            roleRecords = configuracionServicio.getRoleAll();
            usuariosRecords = configuracionServicio.getUsuarioAll();
    }

    public void desactivarUsuario(){
            usuarioSelected.setActivo(false);
            configuracionServicio.updateUsuario(usuarioSelected);
            messagesView.info(Messages.MENSAJE_EXITOSA,Messages.DESACTIVACION);
    }

    public void actualizarUsuario(){
        Role nuevoRol = configuracionServicio.getRoleById(usuarioSelected.getRole().getIdNuevo());
        if(nuevoRol!=null){
            usuarioSelected.setRole(nuevoRol);
            configuracionServicio.updateUsuario(usuarioSelected);
            messagesView.info(Messages.MENSAJE_EXITOSA,Messages.ACTUALIZACION_USUARIO);
        }

    }

    public void actualizarPass(){
        if(nuevaContrasena!=null){
            if(!nuevaContrasena.equals("")){
                usuarioSelected.setContrasena(Util.md5(nuevaContrasena));
                configuracionServicio.updateUsuario(usuarioSelected);
                messagesView.info(Messages.MENSAJE_EXITOSA,Messages.ACTUALIZACION_USUARIO);
            }
        }else{
            messagesView.error(Messages.MENSAJE_FALLIDA,"Digite la nueva contraseña");
        }

    }

    public void crearNuevoUsuario(){
        usuarioNuevo = new Usuario();
    }

    public void crearUsuario(){
        if(usuarioNuevo!= null){
            if(datosCompletos()){
                Role nuevoRol = configuracionServicio.getRoleById(roleID);
                if(nuevoRol!=null){
                    if(configuracionServicio.usuarioNoExiste(usuarioNuevo.getUsuario())){
                        usuarioNuevo.setRole(nuevoRol);
                        usuarioNuevo.setActivo(true);
                        usuarioNuevo.setContrasena(Util.md5(usuarioNuevo.getContrasena()));
                        configuracionServicio.addUsuario(usuarioNuevo);
                        messagesView.info(Messages.MENSAJE_EXITOSA,Messages.CREACION_USUARIO);
                        usuariosRecords = configuracionServicio.getUsuarioAll();
                    }else{
                        messagesView.error(Messages.MENSAJE_FALLIDA,"Ya existe un usuario con ese nombre de usuario");
                    }

                }
            }else{
                messagesView.error(Messages.MENSAJE_FALLIDA,"Complete todos los campos");
            }



        }
    }

    private boolean datosCompletos() {

        if(!usuarioNuevo.getUsuario().equals("") && !usuarioNuevo.getArea().equals("") && !usuarioNuevo.getEmail().equals("")
                && !usuarioNuevo.getContrasena().equals("") ) return true;
        return false;
    }

    public void cancelar(){
        usuarioNuevo = new Usuario();
    }

    public List<Usuario> getUsuariosRecords() {
        return usuariosRecords;
    }

    public void setUsuariosRecords(List<Usuario> usuariosRecords) {
        this.usuariosRecords = usuariosRecords;
    }

    public Usuario getUsuarioSelected() {
        return usuarioSelected;
    }

    public void setUsuarioSelected(Usuario usuarioSelected) {
        this.usuarioSelected = usuarioSelected;
    }

    public Usuario getUsuarioNuevo() {
        return usuarioNuevo;
    }

    public void setUsuarioNuevo(Usuario usuarioNuevo) {
        this.usuarioNuevo = usuarioNuevo;
    }

    public List<Role> getRoleRecords() {
        return roleRecords;
    }

    public void setRoleRecords(List<Role> roleRecords) {
        this.roleRecords = roleRecords;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public ConfiguracionServicio getConfiguracionServicio() {
        return configuracionServicio;
    }

    public void setConfiguracionServicio(ConfiguracionServicio configuracionServicio) {
        this.configuracionServicio = configuracionServicio;
    }

    public MessagesView getMessagesView() {
        return messagesView;
    }

    public void setMessagesView(MessagesView messagesView) {
        this.messagesView = messagesView;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public long getRoleID() {
        return roleID;
    }

    public void setRoleID(long roleID) {
        this.roleID = roleID;
    }

    public String getNuevaContrasena() {
        return nuevaContrasena;
    }

    public void setNuevaContrasena(String nuevaContrasena) {
        this.nuevaContrasena = nuevaContrasena;
    }
}
