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
@Table(nom="billet")
public class Billet {
    @Colonne(colonne = "id",sequence = "seq_billet",prefixe = "BI",identifiant = true)
    String id;
    @Colonne(colonne = "nomClient")
    String nomClient;
    @Colonne(colonne = "age")
    Integer age;
    @Colonne(colonne = "idvol")
    String idVol;
    @Colonne(colonne = "modifiable")
    Integer modifiable;
    @Colonne(colonne = "remboursable")
    Integer remboursable;
    @Colonne(colonne = "montant")
    double montant;
    @Colonne(colonne = "datecreation")
    Date dateCreation;
    @Colonne(colonne = "etat")
    Integer etat;

    public Billet(){}
    public Billet(String id, String nomClient, Integer age, String idVol, Integer modifiable, Integer remboursable, double montant, Date dateCreation, Integer etat) {
        this.id = id;
        this.nomClient = nomClient;
        this.age = age;
        this.idVol = idVol;
        this.modifiable = modifiable;
        this.remboursable = remboursable;
        this.montant = montant;
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
    
    //Valider
    public void Valider(Connection connex) throws Exception  {
        AccesDb ac = new DBConnect().accesDb();
        boolean test = false;
        if(connex == null){
            test = true;
            connex = ac.connect();
        }
        try{
            if(test) connex.setAutoCommit(false);
            this.setEtat(Constant.ETAT_VALIDER);
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
    
    //en attente
    public void EnAttente(Connection connex) throws Exception  {
        AccesDb ac = new DBConnect().accesDb();
        boolean test = false;
        if(connex == null){
            test = true;
            connex = ac.connect();
        }
        try{
            if(test) connex.setAutoCommit(false);
            this.setEtat(Constant.ETAT_EN_ATTENTE);
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
    
    //en attente
    public void Rembourser(Connection connex) throws Exception  {
        AccesDb ac = new DBConnect().accesDb();
        boolean test = false;
        if(connex == null){
            test = true;
            connex = ac.connect();
        }
        try{
            if(test) connex.setAutoCommit(false);
            this.setEtat(Constant.ETAT_REMBOURSE);
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
    
    //modifier
    public void Modifier(String idVol,Connection connex) throws Exception  {
        AccesDb ac = new DBConnect().accesDb();
        boolean test = false;
        if(connex == null){
            test = true;
            connex = ac.connect();
        }
        try{
            if(test) connex.setAutoCommit(false);
            this.setIdVol(idVol);
            this.setEtat(Constant.ETAT_CREE);
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
    
    //get vol
    public Vol_detail getVol_detail(Connection connex) throws Exception  {
        AccesDb ac = new DBConnect().accesDb();
        Vol_detail res = new Vol_detail();
        boolean test = false;
        if(connex == null){
            test = true;
            connex = ac.connect();
        }
        try{    
            res = new Vol_detail().getInBase(this.idVol,null,null," etat != '"+Constant.ETAT_SUPPRIMER+"'", connex)[0];
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
    public Billet[] getInBase(String id,String idVol,String afterWhere,Connection connex) throws Exception  {
        AccesDb ac = new DBConnect().accesDb();
        Billet[] res = new Billet[0];
        boolean test = false;
        if(connex == null){
            test = true;
            connex = ac.connect();
        }
        try{    
            String apWhere = " id "+UG.transform(id)+" and idVol"+UG.transform(idVol);
            if(!afterWhere.equals("")){
                apWhere = apWhere+" and "+afterWhere;
            }
             res =(Billet[]) ac.find(this,"Billet"," and " +apWhere, 0, 0,false,connex);
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

    public String getNomClient() {
        return nomClient;
    }

    public void setNomClient(String nomClient) {
        this.nomClient = nomClient;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getIdVol() {
        return idVol;
    }

    public void setIdVol(String idVol) {
        this.idVol = idVol;
    }

    public Integer getModifiable() {
        return modifiable;
    }

    public void setModifiable(Integer modifiable) {
        this.modifiable = modifiable;
    }

    public Integer getRemboursable() {
        return remboursable;
    }

    public void setRemboursable(Integer remboursable) {
        this.remboursable = remboursable;
    }

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
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
