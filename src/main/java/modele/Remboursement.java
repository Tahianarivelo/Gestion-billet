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

/**
 *
 * @author tahiana
 */
@Table(nom="remboursement")
public class Remboursement {
    @Colonne(colonne = "id",sequence = "seq_remboursement",prefixe = "RB",identifiant = true)
    String id;
    @Colonne(colonne = "idbillet")
    String idBillet;
    @Colonne(colonne = "montant")
    double montant;
    @Colonne(colonne = "etat")
    Integer etat;

    public Remboursement(){}
    public Remboursement(String id, String idBillet, double montant, Integer etat) {
        this.id = id;
        this.idBillet = idBillet;
        this.montant = montant;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdBillet() {
        return idBillet;
    }

    public void setIdBillet(String idBillet) {
        this.idBillet = idBillet;
    }

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

    public Integer getEtat() {
        return etat;
    }

    public void setEtat(Integer etat) {
        this.etat = etat;
    }
    
    
}
