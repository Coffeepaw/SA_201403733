/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WebService;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Scanner;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;

/**
 *
 * @author juanr
 */
public class Service {

    // SAAJ - SOAP Client Testing
    public static void main(String args[]) throws Exception {
        String url = "https://api.softwareavanzado.world/administrator/index.php?";
        String xml = "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:nsl=\"https://api.softwareavanzado.world/media/redcore/webservices/joomla/administrator.contact.1.0.0.wsdl\"><SOAP-ENV:Header/><SOAP-ENV:Body><nsl:readList/></SOAP-ENV:Body></SOAP-ENV:Envelope>";
        String charset = "UTF-8";  // Or in Java 7 and later, use the constant: java.nio.charset.StandardCharsets.UTF_8.name()
        String param1 = "administrator";
        String param2 = "1.0.0";
        String param3 = "contact";
        String param4 = "soap";
        String query = String.format("webserviceClient=%s&webserviceVersion=%s&option=%s&api=%s",
                URLEncoder.encode(param1, charset),
                URLEncoder.encode(param2, charset),
                URLEncoder.encode(param3, charset),
                URLEncoder.encode(param4, charset));
        System.out.println(query);

        HttpURLConnection connection = (HttpURLConnection) new URL(url+"?"+query).openConnection();
        // setDoOutput(true) implicitly set's the request type to POST
        connection.setDoOutput(true);
        connection.setRequestProperty("Content-type", "application/xml");
        connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");

        // Write to the connection
        OutputStream output = connection.getOutputStream();
        output.write(xml.getBytes(charset));
        output.close();

        // Check the error stream first, if this is null then there have been no issues with the request
        InputStream inputStream = connection.getErrorStream();
        if (inputStream == null) {
            inputStream = connection.getInputStream();
        }

        // Read everything from our stream
        BufferedReader responseReader = new BufferedReader(new InputStreamReader(inputStream, charset));

        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = responseReader.readLine()) != null) {
            response.append(inputLine);
        }
        responseReader.close();
        
        System.out.println(response.toString());

    }

}
