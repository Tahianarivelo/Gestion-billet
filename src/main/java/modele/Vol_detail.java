/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;

import accesdb.AccesDb;
import annotation.Colonne;
import annotation.Table;
import dao.DBConnect;
import java.sql.Connection;
import java.sql.Date;
import java.time.LocalTime;
import outil.UG;

/**
 *
 * @author tahiana
 */
@Table(nom="vol_detail")
public class Vol_detail extends Vol{
    @Colonne(colonne = "origin")
    String origin;
    @Colonne(colonne = "destination")
    String destination;
    @Colonne(colonne = "prix")
    double prix;
    @Colonne(colonne = "nom_avion")
    String nom_avion;
    @Colonne(colonne = "nb_place")
    Integer nb_place;
    @Colonne(colonne = "nb_billet")
    Integer nb_billet;
    @Colonne(colonne="reste_place")
    Integer reste_place;
    @Colonne(colonne = "etat_string")
    String etat_string;

    public Vol_detail(){};
    public Vol_detail(String origin, String destination, double prix, String nom_avion, Integer nb_place, Integer nb_billet, Integer reste_place, String id, String num, String idTrajet, String idAvion, Date dateDep, LocalTime horaire, Integer etat,String etat_string) {
        super(id, num, idTrajet, idAvion, dateDep, horaire, etat);
        this.origin = origin;
        this.destination = destination;
        this.prix = prix;
        this.nom_avion = nom_avion;
        this.nb_place = nb_place;
        this.nb_billet = nb_billet;
        this.reste_place = reste_place;
        this.etat_string = etat_string;
    }
    
      //get In base
    public Vol_detail[] getInBase(String id,String idTrajet,String idAvion,String afterWhere,Connection connex) throws Exception  {
        AccesDb ac = new DBConnect().accesDb();
        Vol_detail[] res = new Vol_detail[0];
        boolean test = false;
        if(connex == null){
            test = true;
            connex = ac.connect();
        }
        try{    
            String apWhere = " id "+UG.transform(id)+" and idtrajet"+UG.transform(idTrajet)+" and idavion"+UG.transform(idAvion);
            if(!afterWhere.equals("")){
                apWhere = apWhere+" and "+afterWhere;
            }
             res =(Vol_detail[]) ac.find(this,"Vol_detail"," and " +apWhere, 0, 0,false,connex);
        }
        catch(Exception ex){
            throw ex;
        }
        finally{
            if(connex != null && test == true){
                connex.close();
            }
        }
        return res;
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

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public String getNom_avion() {
        return nom_avion;
    }

    public void setNom_avion(String nom_avion) {
        this.nom_avion = nom_avion;
    }

    public Integer getNb_place() {
        return nb_place;
    }

    public void setNb_place(Integer nb_place) {
        this.nb_place = nb_place;
    }

    public Integer getNb_billet() {
        return nb_billet;
    }

    public void setNb_billet(Integer nb_billet) {
        this.nb_billet = nb_billet;
    }

    public Integer getReste_place() {
        return reste_place;
    }

    public void setReste_place(Integer reste_place) {
        this.reste_place = reste_place;
    }

    public String getEtat_string() {
        return etat_string;
    }

    public void setEtat_string(String etat_string) {
        this.etat_string = etat_string;
    }

}
