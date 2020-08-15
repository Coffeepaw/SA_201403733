/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WebService;

import Objetos.Contact;
import Objetos.Envelope;
import Procesos.ProcesarRespuesta;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * PROCESOS PARA MANEJAR LAS LLAMADAS Y LA PARAMETRIZACION
 *
 * @author juanr
 */
public class Service {

    private static final String URL = "https://api.softwareavanzado.world/index.php?";
    public static final String LISTAR = "LISTAR";
    public static final String CREAR = "CREAR";

    private ProcesarRespuesta procesar;
    public List<Contact> contactos;
    public Contact nuevoContacto;

    public Service() {
        contactos = new ArrayList<>();
        procesar = new ProcesarRespuesta();
    }

    public static void main(String[] args) throws IOException {
    }

    private static String generarAutenticacion() throws MalformedURLException, IOException {
        String cliente = "sa";
        String secret = "fb5089840031449f1a4bf2c91c2bd2261d5b2f122bd8754ffe23be17b107b8eb103b441de3771745";
        String grant = "client_credentials";
        String url = Service.URL + "option=token&api=oauth2";

        String urlParameters = "grant_type=" + grant + "&client_id=" + cliente + "&client_secret=" + secret;

        byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
        int postDataLength = postData.length;

        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setDoOutput(true);
        connection.setInstanceFollowRedirects(false);
        connection.setRequestMethod("POST");

        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        connection.setRequestProperty("charset", "utf-8");
        connection.setRequestProperty("Content-Length", Integer.toString(postDataLength));
        connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
        connection.setUseCaches(false);

        try (DataOutputStream wr = new DataOutputStream(connection.getOutputStream())) {
            wr.write(postData);
        }

        InputStream inputStream = connection.getErrorStream();
        if (inputStream == null) {
            inputStream = connection.getInputStream();
        }
        BufferedReader responseReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = responseReader.readLine()) != null) {
            response.append(inputLine);
        }
        responseReader.close();
        connection.disconnect();
        return response.toString();

    }

    private String generateURL() throws UnsupportedEncodingException {
        String charset = "UTF-8";  // Or in Java 7 and later, use the constant: java.nio.charset.StandardCharsets.UTF_8.name()
        String param1 = "administrator";
        String param2 = "1.0.0";
        String param3 = "contact";
        String param4 = "hal";
        String param5 = "0";
        String query = String.format("webserviceClient=%s&webserviceVersion=%s&option=%s&api=%s&list[limi]=%s",
                URLEncoder.encode(param1, charset),
                URLEncoder.encode(param2, charset),
                URLEncoder.encode(param3, charset),
                URLEncoder.encode(param4, charset),
                URLEncoder.encode(param5, charset));
        return URL + query;

    }

    private String llamarWebService(Envelope envelope, String endpoint) throws MalformedURLException, IOException, Exception {

        if (envelope.getAction().equals("LISTAR")) {
            return null;
        } else {
            String token = "";
            String jsonToken = generarAutenticacion();
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(jsonToken);
            token = jsonNode.at("/token_type").asText() + " " + jsonNode.at("/access_token").asText();

            String urlParameters = "name="+envelope.getXml();

            byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
            int postDataLength = postData.length;

            HttpURLConnection connection = (HttpURLConnection) new URL(endpoint).openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Authorization", token);
            connection.setRequestProperty("charset", "UTF-8");
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
            connection.setInstanceFollowRedirects(false);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Length", Integer.toString(postDataLength));
            try (DataOutputStream wr = new DataOutputStream(connection.getOutputStream())) {
                wr.write(postData);
            }
            

            /*try (DataOutputStream wr = new DataOutputStream(connection.getOutputStream())) {
            wr.write(postData);
        }*/
            InputStream inputStream = connection.getErrorStream();
            if (inputStream == null) {
                inputStream = connection.getInputStream();
            }
            BufferedReader responseReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = responseReader.readLine()) != null) {
                response.append(inputLine);
            }
            responseReader.close();
            connection.disconnect();
            return response.toString();
        }

    }

    public void fireService(String tipo) {
        try {
            System.out.println("INICIA LA LLAMADA A LA ACCION " + tipo);
            Envelope envelope = getAction(tipo);
            String urlConstruido = generateURL();
            String respuesta = llamarWebService(envelope, urlConstruido);
            // procesarResultado(respuesta, tipo);
        } catch (UnsupportedEncodingException e) {
            System.out.println("NO SE PUDO REALIZAR LA CONSULTA");
        } catch (IOException ex) {
            System.out.println("ERROR EN LA CONECCION" + ex.getMessage());
            ex.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("TERMINA LA LLAMADA A LA ACCION " + tipo);
        }
    }

    private Envelope getAction(String action) {
        Envelope envelope = new Envelope();
        switch (action) {
            case "LISTAR":
                envelope.setAction(action);
                envelope.setXml("<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:nsl=\"https://api.softwareavanzado.world/media/redcore/webservices/joomla/administrator.contact.1.0.0.wsdl\"><SOAP-ENV:Header/><SOAP-ENV:Body><nsl:readList><limit>0</limit></nsl:readList></SOAP-ENV:Body></SOAP-ENV:Envelope>");
                break;
            case "CREAR":
                envelope.setAction(action);
                envelope.setXml(nuevoContacto.getName());
                break;
        }
        return envelope;
    }

    private void procesarResultado(String response, String tipo) {
        try {
            switch (tipo) {
                case "LISTAR":
                    contactos = procesar.traducirLista(response);
                    break;
                case "CREAR":
                    System.out.println("SE FINALIZO EL PROCESO DE CREADO");
                    break;
            }
        } catch (Exception e) {
            System.out.println("NO FUE POSIBLE PROCESAR LA RESPUESTA");
        }
    }

}
