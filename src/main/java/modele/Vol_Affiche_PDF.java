/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;

/**
 *
 * @author tahiana
 */
public class Vol_Affiche_PDF {
    String num;
    String origin;
    String destination;
    String datedepart;
    String horaire;
    String avion;

    public Vol_Affiche_PDF(String num, String origin, String destination, String datedepart, String horaire, String avion) {
        this.num = num;
        this.origin = origin;
        this.destination = destination;
        this.datedepart = datedepart;
        this.horaire = horaire;
        this.avion = avion;
    }

   
    
    
    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getAvion() {
        return avion;
    }

    public void setAvion(String avion) {
        this.avion = avion;
    }

    public String getDatedepart() {
        return datedepart;
    }

    public void setDatedepart(String datedepart) {
        this.datedepart = datedepart;
    }

    public String getHoraire() {
        return horaire;
    }

    public void setHoraire(String horaire) {
        this.horaire = horaire;
    }
    
    
}
