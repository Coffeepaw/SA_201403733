/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Procesos;

import Objetos.Contact;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * CLASE PARA PROCESAR RESPUESTAS
 * @author JUA3909AT
 */
public class ProcesarRespuesta {

    public ProcesarRespuesta() {

    }

    public List<Contact> traducirLista(String xml) throws ParserConfigurationException, SAXException, IOException {
        System.out.println("LECTURA DEL XML DE LISTAR INICIADO");
        List<Contact> contactos = new ArrayList<>();

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;
        try {
            //Create DocumentBuilder with default configuration
            builder = factory.newDocumentBuilder();

            //Parse the content to Document object
            Document doc = builder.parse(new InputSource(new StringReader(xml)));
            doc.normalizeDocument();
            NodeList nList = doc.getElementsByTagName("item");

            for (int temp = 0; temp < nList.getLength(); temp++) {
                try {
                    Node node = nList.item(temp);
                    if (node.getNodeType() == Node.ELEMENT_NODE) {
                        Element eElement = (Element) node;
                        Contact contacto = new Contact();
                        contacto.setId(Integer.parseInt(eElement.getElementsByTagName("id").item(0).getTextContent()));
                        contacto.setAccess(Integer.parseInt(eElement.getElementsByTagName("access").item(0).getTextContent()));
                        contacto.setFeatured(Integer.parseInt(eElement.getElementsByTagName("featured").item(0).getTextContent()));
                        contacto.setLanguage(eElement.getElementsByTagName("language").item(0).getTextContent());
                        contacto.setName(eElement.getElementsByTagName("name").item(0).getTextContent());
                        contacto.setOrdering(Integer.parseInt(eElement.getElementsByTagName("ordering").item(0).getTextContent()));
                        contactos.add(contacto);
                    }
                } catch (Exception e) {
                    System.out.println("ERROR EN LA OBTENCION DE UN CONTACTO");
                }
            }
            return contactos;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("LECTURA DE XML DE LISTAR FINALIZADO");
        }
        return null;
    }
}
