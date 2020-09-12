package atel.com.gt.repartidor;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.InputStream;
import javax.ejb.Stateless;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;


/**
 *
 * @author JUA3909AT
 */
@Path("/service")
@Stateless
public class Repartidor {

    static Logger log = LogManager.getRootLogger();
    public static String restaurante="http://192.168.56.1:7002/Restaurante/restaurante/service";
    public static String cliente = "http://192.168.56.1:7002/Cliente/cliente/service";
    
    @Path("/state")
    @GET
    @Produces("text/plain")
    public Response getStaterRestaurante(@QueryParam("orden") String ordenCliente) {
        int max = 3; 
        int min = 1; 
        int range = max - min + 1;
        int rand = (int)(Math.random() * range) + min; 
        String respuesta;
        switch(rand){
            case 1:
                respuesta="En camino";
                break;
            case 2:
                respuesta="Atrasada";
                break;
            case 3:
                respuesta="Entregada";                
                break;
            default:
                respuesta="Error al buscar orden";
                break;
        }
            
        Response response = Response.ok(respuesta).build();
       
        return response;
    }
    
    @Path("/recibir")
    @POST
    @Consumes("application/x-www-form-urlencoded")
    @Produces("text/plain")
    public Response recibirPedido(@QueryParam("orden") String ordenCliente,@QueryParam("repartidor") String repartidor) {
        Response response = null;

        response = Response.ok("Orden "+ ordenCliente +" Ingresada para repartidor "+repartidor).build();
        return response;

    }
    
    @Path("/entrega")
    @POST
    @Consumes("application/x-www-form-urlencoded")
    @Produces("text/plain")
    public Response recibirPedido(@QueryParam("orden") String ordenCliente) {
        Response response = null;

        response = Response.ok("Orden "+ ordenCliente +" entregada").build();
        return response;

    }
    

}
