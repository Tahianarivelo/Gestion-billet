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
@Table(nom="trajet")
public class Trajet {
     @Colonne(colonne = "id",sequence = "seq_trajet",prefixe = "TR",identifiant = true)
    String id;
     @Colonne(colonne = "ville_dep")
    String ville_dep;
     @Colonne(colonne = "ville_arr")
    String ville_arr;
     @Colonne(colonne = "distance")
    double distance;
     @Colonne(colonne = "datecreation")
    Date dateCreation;
     @Colonne(colonne = "etat")
    Integer etat;

    public Trajet(){}
    public Trajet(String id, String ville_dep, String ville_arr, double distance, Date dateCreation, Integer etat) {
        this.id = id;
        this.ville_dep = ville_dep;
        this.ville_arr = ville_arr;
        this.distance = distance;
        this.dateCreation = dateCreation;
        this.etat = etat;
    }

    //insert 
    public void insert(Connection connex) throws Exception  {
        AccesDb ac = new DBConnect().accesDb();
        boolean test = false;
        if(connex == null){
            test = true;
            connex = ac.connect();
        }
        try{
            if(test) connex.setAutoCommit(false);
           ac.insert(this,true,connex);
            if(test) connex.commit();
        }
        catch(Exception ex){
            if(test) connex.rollback();
            throw ex;
        }
        finally{
            if(connex != null && test == true){
                connex.close();
            }
        }
    }
    
    //update 
    public void update(Connection connex) throws Exception  {
        AccesDb ac = new DBConnect().accesDb();
        boolean test = false;
        if(connex == null){
            test = true;
            connex = ac.connect();
        }
        try{
            if(test) connex.setAutoCommit(false);
            ac.update(this,"",connex);
            if(test) connex.commit();
        }
        catch(Exception ex){
            if(test) connex.rollback();
            throw ex;
        }
        finally{
            if(connex != null && test == true){
                connex.close();
            }
        }
    }
    
    //suppre 
    public void Suppre(Connection connex) throws Exception  {
        AccesDb ac = new DBConnect().accesDb();
        boolean test = false;
        if(connex == null){
            test = true;
            connex = ac.connect();
        }
        try{
            if(test) connex.setAutoCommit(false);
//            ac.setValeurParDefautDouble(-1.0);
            Trajet t = new Trajet(this.id,null,null,0,null,Constant.ETAT_SUPPRIMER);
            ac.update(t,"",connex);
            if(test) connex.commit();
        }
        catch(Exception ex){
            if(test) connex.rollback();
            throw ex;
        }
        finally{
            if(connex != null && test == true){
                connex.close();
            }
        }
    }
    
    //get In base
    public Trajet[] getInBase(String id,String ville_dep,String ville_arr,String afterWhere,Connection connex) throws Exception  {
        AccesDb ac = new DBConnect().accesDb();
        Trajet[] res = new Trajet[0];
        boolean test = false;
        if(connex == null){
            test = true;
            connex = ac.connect();
        }
        try{    
            String apWhere = " id "+UG.transform(id)+" and ville_dep"+UG.transform(ville_dep)+" and ville_arr"+UG.transform(ville_arr);
            if(!afterWhere.equals("")){
                apWhere = apWhere+" and "+afterWhere;
            }
             res =(Trajet[]) ac.find(this,"trajet"," and " +apWhere, 0, 0,false,connex);
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

    public String getVille_dep() {
        return ville_dep;
    }

    public void setVille_dep(String ville_dep) {
        this.ville_dep = ville_dep;
    }

    public String getVille_arr() {
        return ville_arr;
    }

    public void setVille_arr(String ville_arr) {
        this.ville_arr = ville_arr;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
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
