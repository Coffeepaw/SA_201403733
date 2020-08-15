/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Objetos;

/**
 * CLASE PARA MANEJAR LOS ENVELOPS
 *
 * @author JUA3909AT
 */
public class Envelope {

    private String xml;
    private String action;

    public Envelope(String xml, String action) {
        this.xml = xml;
        this.action = action;
    }

    public Envelope() {
    }

    public String getXml() {
        return xml;
    }

    public void setXml(String xml) {
        this.xml = xml;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

}
