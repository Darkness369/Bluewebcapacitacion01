/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sesiones;

import clases.Usuario;
import java.util.Map;
import javax.faces.context.FacesContext;

/**
 *
 * @author Blueweb
 */
public class Sesion {

    private Usuario sesion;

    public Usuario getSesion(String key) {
        this.sesion = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(key);
        return sesion;
    }

    public void setSesion(Usuario user, String key) {
        Map<String, Object> session = FacesContext.getCurrentInstance()
                .getExternalContext().getSessionMap();
        session.put(key, user);
    }

}