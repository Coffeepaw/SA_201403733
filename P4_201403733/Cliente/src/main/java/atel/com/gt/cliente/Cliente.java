package atel.com.gt.cliente;

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
public class Cliente {

    static Logger log = LogManager.getRootLogger();
    public static String restaurante="http://192.168.56.1:7002/Restaurante/restaurante/service";
    public static String repartidor = "http://192.168.56.1:7002/Repartidor/repartidor/service";
    @Path("/enviar")
    @POST
    @Consumes("application/x-www-form-urlencoded")
    @Produces("text/plain")
    public Response enviarPedido(@QueryParam("orden") String ordenCliente) {
        Response response = null;

        
        InputStream in = null;

        try {
            String url= restaurante + "/envio?orden="+ordenCliente;
            HttpClient client = new HttpClient();
            PostMethod method = new PostMethod(url);

            //Add any parameter if u want to send it with Post req.
            int statusCode = client.executeMethod(method);

            if (statusCode != -1) {
                in = method.getResponseBodyAsStream();
            }
            
            response = Response.ok("Respuesta restaurante \n"+in).build();
            

        } catch (Exception e) {
            response = Response.serverError().build();
        }
        
        return response;
    }
    
    @Path("/restaurante")
    @GET
    @Produces("text/plain")
    public Response getStateRestaurante(@QueryParam("orden") String ordenCliente) {
        Response response = null;

        
        InputStream in = null;

        try {
            String url= restaurante + "/state?orden="+ordenCliente;
            HttpClient client = new HttpClient();
            GetMethod method = new GetMethod(url);

            //Add any parameter if u want to send it with Post req.
            int statusCode = client.executeMethod(method);

            if (statusCode != -1) {
                in = method.getResponseBodyAsStream();
            }
            
            response = Response.ok("Respuesta Restaurante \n"+in).build();
            

        } catch (Exception e) {
            response = Response.serverError().build();
        }
        
        return response;
    }
    
    @Path("/repartidor")
    @GET
    @Produces("text/plain")
    public Response getStateRepartidor(@QueryParam("orden") String ordenCliente) {
        Response response = null;

        
        InputStream in = null;

        try {
            String url= repartidor + "/state?orden="+ordenCliente;
            HttpClient client = new HttpClient();
            GetMethod method = new GetMethod(url);

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
