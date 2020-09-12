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
public class Restaurante {

    static Logger log = LogManager.getRootLogger();
    public static String repartidor = "http://192.168.56.1:7002/Repartidor/repartidor/service";
    public static String cliente = "http://192.168.56.1:7002/Cliente/cliente/service";
    
    @Path("/envio")
    @POST
    @Consumes("application/x-www-form-urlencoded")
    @Produces("text/plain")
    public Response post(@QueryParam("orden") String ordenCliente) {
        Response response = null;
        response = Response.ok("Orden Ingresada").build();
        return response;
    }
    
    @Path("/state")
    @GET
    @Produces("text/plain")
    public Response getState(@QueryParam("orden") String ordenCliente) {
        int max = 5; 
        int min = 1; 
        int range = max - min + 1;
        int rand = (int)(Math.random() * range) + min; 
        String respuesta;
        switch(rand){
            case 1:
                respuesta="Recibida";
                break;
            case 2:
                respuesta="En preparacion";
                break;
            case 3:
                respuesta="Chequeo";
                break;
            case 4:
                respuesta="En camino";
                break;
            case 5:
                respuesta="Entregada";
                break;
            default:
                respuesta="Error al buscar orden";
                break;
        }
            
        Response response = Response.ok("Estado: "+respuesta).build();
       
        return response;
    }
    
    @Path("/aviso")
    @POST
    @Consumes("application/x-www-form-urlencoded")
    @Produces("text/plain")
    public Response aviso(@QueryParam("orden") String ordenCliente) {
        Response response = null;
        
        int max = 99; 
        int min = 1; 
        int range = max - min + 1;
        int rand = (int)(Math.random() * range) + min; 

        
        InputStream in = null;

        try {
            String url= repartidor + "/recibir?orden="+ordenCliente+"&repartidor="+rand;
            HttpClient client = new HttpClient();
            PostMethod method = new PostMethod(url);

            //Add any parameter if u want to send it with Post req.
            int statusCode = client.executeMethod(method);

            if (statusCode != -1) {
                in = method.getResponseBodyAsStream();
            }
            
            response = Response.ok("Respuesta repartidor \n"+in).build();
            

        } catch (Exception e) {
            response = Response.serverError().build();
        }
        
        return response;
    }

}
