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
import java.sql.Timestamp;
import outil.UG;

/**
 *
 * @author tahiana
 */
@Table(nom="tarif")
public class Tarif {
    @Colonne(colonne = "id",sequence = "seq_tarif",prefixe = "TF",identifiant = true)
    String id;
    @Colonne(colonne = "idTrajet")
    String idTrajet;
    @Colonne(colonne = "prix")
    double prix;
    @Colonne(colonne = "dateApplication")
    Timestamp dateApplication;
    @Colonne(colonne = "etat")
    Integer etat;

    public Tarif(){}
    public Tarif(String id, String idTrajet,double prix,Timestamp dateApplication, Integer etat) {
        this.id = id;
        this.idTrajet = idTrajet;
        this.prix = prix;
        this.dateApplication = dateApplication;
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
            this.setDateApplication(new Timestamp(new java.util.Date().getTime()));
            ac.update(this,"", connex);
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
            Tarif t = new Tarif(id,null,0,null,Constant.ETAT_SUPPRIMER);
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
    public Tarif[] getInBase(String id,String idTrajet,String afterWhere,Connection connex) throws Exception  {
        AccesDb ac = new DBConnect().accesDb();
        Tarif[] res = new Tarif[0];
        boolean test = false;
        if(connex == null){
            test = true;
            connex = ac.connect();
        }
        try{    
            String apWhere = " id "+UG.transform(id)+" and idtrajet"+UG.transform(idTrajet);
            if(!afterWhere.equals("")){
                apWhere = apWhere+" and "+afterWhere;
            }
             res =(Tarif[]) ac.find(this,"Tarif"," and " +apWhere, 0, 0,false,connex);
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

    public String getIdTrajet() {
        return idTrajet;
    }

    public void setIdTrajet(String idTrajet) {
        this.idTrajet = idTrajet;
    }

    public Timestamp getDateApplication() {
        return dateApplication;
    }

    public void setDateApplication(Timestamp dateApplication) {
        this.dateApplication = dateApplication;
    }

    public Integer getEtat() {
        return etat;
    }

    public void setEtat(Integer etat) {
        this.etat = etat;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }
    
    
}
