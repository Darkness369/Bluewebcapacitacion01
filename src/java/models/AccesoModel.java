/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import data.PoolDB;
import responses.AccessResponse;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;
import responses.Response;
import clases.Acceso;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Blueweb
 */
public class AccesoModel {

    public AccessResponse conectarLista() {
        //instanciar clase de respuesta acceso
        AccessResponse respuestaAcceso = new AccessResponse();
        Response claseRespuesta = new Response();
        String query = "";//declaramos el query
        List<Acceso> lista = new ArrayList();//creas la lista

        PoolDB pool = new PoolDB();//creamos el objeto pooldb para conectarse al pooldb
        Connection con = null;//declaras la conexion y la inicias en null 

        try {

            con = pool.getConnection("Activa");//aqui se conecta 

            query = "SELECT * FROM S_ACCESOS";//query para hacer la consulta
            PreparedStatement consulta = con.prepareStatement(query); // hacer la conexion mandando llamar al query de arriba/preparedstatment/llamar procedimientos almacenados  
            ResultSet rs = consulta.executeQuery();//el rs de tipo resulset se va a usar para traer los datos

            while (rs.next()) {
                Acceso acceso = new Acceso();//declaramos el objeto acceso
                acceso.setId_acceso(rs.getInt("ID_ACCESO"));
                acceso.setNombre_acceso(rs.getString("NOMBRE_ACCESO"));
                acceso.setOrden(rs.getInt("ORDEN"));
                acceso.setActivo(rs.getBoolean("ACTIVO"));
                String estado = rs.getBoolean("ACTIVO") ? "ACTIVO" : "INACTIVO";
                acceso.setEstado(estado);
                acceso.setFecha_servidor(rs.getDate("FECHA_SERVIDOR"));
                lista.add(acceso);

            }

            rs.close();//cerramos todas las conexiones
            consulta.close();
            con.close();

            if (!lista.isEmpty()) {
                claseRespuesta.setId(0);//mandamos los datos al obejto respuesta
                claseRespuesta.setMensaje("exitoso");

                respuestaAcceso.setListaAcceso(lista);

            } else {
                claseRespuesta.setId(1);//mandamos los datos al obejto respuesta
                claseRespuesta.setMensaje("Advertencia");
            }
            //return lista;//regresamos la lista, para esto el metodo no puede ser void 

        } catch (SQLException | NamingException e) {
            claseRespuesta.setId(-1);//mandamos los datos al obejto respuesta
            claseRespuesta.setMensaje("Error");
            Logger.getLogger(AccesoModel.class.getName()).log(Level.SEVERE, null, e);

        } finally {
            respuestaAcceso.setRespuesta(claseRespuesta);
        }
        return respuestaAcceso;//en caso de no poder retornar la lista, debe retornar algo, por eso el return null
    }

    public AccessResponse addAccess(Acceso access) {
        AccessResponse respuestaAcceso = new AccessResponse();
        Response claseRespuesta = new Response();
        String query = "";//declaramos el query
        PoolDB pool = new PoolDB();//creamos el objeto pooldb para conectarse al pooldb
        Connection con = null;//declaras la conexion y la inicias en null 
        int bandera = 0;
        try {

            con = pool.getConnection("Activa");

            query = "INSERT INTO S_ACCESOS (NOMBRE_ACCESO, ORDEN, ACTIVO, FECHA_SERVIDOR) VALUES (?,?,?,SYSDATETIME()) ";
            PreparedStatement consulta = con.prepareStatement(query); 
            consulta.setString(1, access.getNombre_acceso());
            consulta.setInt(2, access.getOrden());
            consulta.setBoolean(3, access.getActivo());

            bandera = consulta.executeUpdate();
 
            consulta.close();
            con.close();

            if (bandera != 0) {
//                FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/faces/template.xhtml");

                
                claseRespuesta.setId(0);//mandamos los datos al obejto respuesta
                claseRespuesta.setMensaje("Registro agregado correctamente");

            } else {
                claseRespuesta.setId(1);//mandamos los datos al obejto respuesta
                claseRespuesta.setMensaje("Advise");
            }
            
        } catch (SQLException | NamingException e) {
            claseRespuesta.setId(-1);//mandamos los datos al obejto respuesta
            claseRespuesta.setMensaje("Error");
            Logger.getLogger(AccesoModel.class.getName()).log(Level.SEVERE, null, e);

        } finally {
            respuestaAcceso.setRespuesta(claseRespuesta);
        }

        return respuestaAcceso;
    }
    public AccessResponse deleteAccess(Acceso access) {
        AccessResponse respuestaAcceso = new AccessResponse();
        Response claseRespuesta = new Response();
        String query = "";//declaramos el query
        PoolDB pool = new PoolDB();//creamos el objeto pooldb para conectarse al pooldb
        Connection con = null;//declaras la conexion y la inicias en null 
        int bandera = 0;
        try {

            con = pool.getConnection("Activa");

            query = "DELETE FROM S_ACCESOS WHERE ID_ACCESO = ? ";
            PreparedStatement consulta = con.prepareStatement(query); 
            consulta.setInt(1, access.getId_acceso());

            bandera = consulta.executeUpdate();
 
            consulta.close();
            con.close();

            if (bandera != 0) {
                
                claseRespuesta.setId(0);//mandamos los datos al obejto respuesta
                claseRespuesta.setMensaje("Registro Eliminado correctamente");

            } else {
                claseRespuesta.setId(1);//mandamos los datos al obejto respuesta
                claseRespuesta.setMensaje("Advise");
            }
            
        } catch (SQLException | NamingException e) {
            claseRespuesta.setId(-1);//mandamos los datos al obejto respuesta
            claseRespuesta.setMensaje("Error");
            Logger.getLogger(AccesoModel.class.getName()).log(Level.SEVERE, null, e);

        } finally {
            respuestaAcceso.setRespuesta(claseRespuesta);
        }

        return respuestaAcceso;
    }
    public AccessResponse updateAccess(Acceso access) {
        AccessResponse respuestaAcceso = new AccessResponse();
        Response claseRespuesta = new Response();
        String query = "";//declaramos el query
        PoolDB pool = new PoolDB();//creamos el objeto pooldb para conectarse al pooldb
        Connection con = null;//declaras la conexion y la inicias en null 
        int bandera = 0;
        try {

            con = pool.getConnection("Activa");

            query = "UPDATE S_ACCESOS SET NOMBRE_ACCESO = ?, ORDEN = ?, ACTIVO = ? WHERE ID_ACCESO = ? ";      
            PreparedStatement consulta = con.prepareStatement(query); 
            consulta.setString(1, access.getNombre_acceso());
            consulta.setInt(2, access.getOrden());
            consulta.setBoolean(3, access.getActivo());
            consulta.setInt(4, access.getId_acceso());

            bandera = consulta.executeUpdate();
 
            consulta.close();
            con.close();

            if (bandera != 0) {
                
                claseRespuesta.setId(0);//mandamos los datos al obejto respuesta
                claseRespuesta.setMensaje("Registro Actualizado correctamente");

            } else {
                claseRespuesta.setId(1);//mandamos los datos al obejto respuesta
                claseRespuesta.setMensaje("Advise");
            }
            
        } catch (SQLException | NamingException e) {
            claseRespuesta.setId(-1);//mandamos los datos al obejto respuesta
            claseRespuesta.setMensaje("Error");
            Logger.getLogger(AccesoModel.class.getName()).log(Level.SEVERE, null, e);

        } finally {
            respuestaAcceso.setRespuesta(claseRespuesta);
        }

        return respuestaAcceso;
    }

    
    
}
