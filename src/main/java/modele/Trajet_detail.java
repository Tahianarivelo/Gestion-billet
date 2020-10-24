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
@Table(nom="trajet_detail")
public class Trajet_detail  extends Trajet{
   @Colonne(colonne = "origin")
  String origin;
   @Colonne(colonne = "destination")
  String destination;
   @Colonne(colonne = "prix")
  double prix;

    public Trajet_detail(){};
    public Trajet_detail(String origin, String destination, double prix, String id, String ville_dep, String ville_arr, double distance, Date dateCreation, Integer etat) {
        super(id, ville_dep, ville_arr, distance, dateCreation, etat);
        this.origin = origin;
        this.destination = destination;
        this.prix = prix;
    }

     //get In base
    public Trajet_detail[] getInBase(String id,String ville_dep,String ville_arr,String afterWhere,Connection connex) throws Exception  {
        AccesDb ac = new DBConnect().accesDb();
        Trajet_detail[] res = new Trajet_detail[0];
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
             res =(Trajet_detail[]) ac.find(this,"trajet_detail"," and " +apWhere, 0, 0,false,connex);
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
    
     //get In base
    public Trajet_detail[] getInBaseTout(String id,String ville_dep,String ville_arr,String afterWhere,Connection connex) throws Exception  {
        AccesDb ac = new DBConnect().accesDb();
        Trajet_detail[] res = new Trajet_detail[0];
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
             res =(Trajet_detail[]) ac.find(this,"trajet_detail_tout"," and " +apWhere, 0, 0,false,connex);
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
}
