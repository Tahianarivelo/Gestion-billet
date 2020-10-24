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
@Table(nom="Ville")
public class Ville {
    @Colonne(colonne = "id",sequence = "seq_ville",prefixe = "VI",identifiant = true)
    String id;
    @Colonne(colonne = "nom")
    String nom;
    @Colonne(colonne = "datecreation")
    Date dateCreation;
    @Colonne(colonne = "etat")
    Integer etat;

    public Ville(){}
    public Ville(String id, String nom, Date dateCreation, Integer etat) {
        this.id = id;
        this.nom = nom;
        this.dateCreation = dateCreation;
        this.etat = etat;
    }
    
    //get In base
    public Ville[] getInBase(String id,String nom,String afterWhere,Connection connex) throws Exception  {
        AccesDb ac = new DBConnect().accesDb();
        Ville[] res = new Ville[0];
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
             res =(Ville[]) ac.find(this,"Ville"," and " +apWhere, 0, 0,false,connex);
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
