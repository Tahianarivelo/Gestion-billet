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
import outil.UG;

/**
 *
 * @author tahiana
 */
@Table(nom = "Avion")
public class Avion {
    @Colonne(colonne = "id",sequence = "seq_avion",prefixe = "AV",identifiant = true)
    String id;
    @Colonne(colonne = "nom")
    String nom;
    @Colonne(colonne = "nb_place")
    Integer nb_place;
    @Colonne(colonne = "dateCreation")
    Date dateCreation;
    @Colonne(colonne = "etat")
    Integer etat;

    public Avion(){}
    public Avion(String id, String nom, Integer nb_place, Date dateCreation, Integer etat) {
        this.id = id;
        this.nom = nom;
        this.nb_place = nb_place;
        this.dateCreation = dateCreation;
        this.etat = etat;
    }
    
    //get In base
    public Avion[] getInBase(String id,String nom,String afterWhere,Connection connex) throws Exception  {
        AccesDb ac = new DBConnect().accesDb();
        Avion[] res = new Avion[0];
        boolean test = false;
        if(connex == null){
            test = true;
            connex = ac.connect();
        }
        try{    
            String apWhere = " id "+UG.transform(id)+" and nom"+UG.transform(nom);
            if(!afterWhere.equals("")){
                apWhere = apWhere+" and "+afterWhere;
            }
             res =(Avion[]) ac.find(this,"Avion"," and " +apWhere, 0, 0,false,connex);
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
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Integer getNb_place() {
        return nb_place;
    }

    public void setNb_place(Integer nb_place) {
        this.nb_place = nb_place;
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    public Integer getEtat() {
        return etat;
    }

    public void setEtat(Integer etat) {
        this.etat = etat;
    }
    
    
}
