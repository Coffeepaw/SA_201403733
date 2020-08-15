/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Objetos;

/**
 * CLASE DE LA ENTIDAD CONTACTO
 * @author JUA3909AT
 */
public class Contact {

    private int id;
    private int access;
    private int featured;
    private String language;
    private String name;
    private int ordering;

    public Contact() {
    }

    public Contact(int id, int access, int featured, String language, String name, int ordering) {
        this.id = id;
        this.access = access;
        this.featured = featured;
        this.language = language;
        this.name = name;
        this.ordering = ordering;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAccess() {
        return access;
    }

    public void setAccess(int access) {
        this.access = access;
    }

    public int getFeatured() {
        return featured;
    }

    public void setFeatured(int featured) {
        this.featured = featured;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOrdering() {
        return ordering;
    }

    public void setOrdering(int ordering) {
        this.ordering = ordering;
    }



}
