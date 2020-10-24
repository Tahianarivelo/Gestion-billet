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
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;
import outil.UG;

/**
 *
 * @author tahiana
 */
@Table(nom = "Vol")
public class Vol {
    @Colonne(colonne = "id",sequence = "seq_vol",prefixe = "VL",identifiant = true)
    String id;    
    @Colonne(colonne = "num")
    String num;
    @Colonne(colonne = "idtrajet")
    String idTrajet;
    @Colonne(colonne = "idavion")
    String idAvion;
    @Colonne(colonne = "datedep")
    Date dateDep;
    @Colonne(colonne = "horaire")
    LocalTime horaire;
    @Colonne(colonne = "etat")
    Integer etat;
    
    public Vol(){}
    public Vol(String id, String num, String idTrajet, String idAvion, Date dateDep, LocalTime horaire, Integer etat) {
        this.id = id;
        this.num = num;
        this.idTrajet = idTrajet;
        this.idAvion = idAvion;
        this.dateDep = dateDep;
        this.horaire = horaire;
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
            this.setDateDep(null);
            this.setHoraire(null);
            this.setIdAvion(null);
            this.setIdTrajet(null);
            this.setNum(null);
            this.setEtat(Constant.ETAT_SUPPRIMER);
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
    
     //get In base
    public Vol[] getInBase(String id,String idTrajet,String idAvion,String afterWhere,Connection connex) throws Exception  {
        AccesDb ac = new DBConnect().accesDb();
        Vol[] res = new Vol[0];
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
             res =(Vol[]) ac.find(this,"Vol"," and " +apWhere, 0, 0,false,connex);
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
    
    //get billet
    public Billet[] getBillets(Connection connex) throws Exception  {
        AccesDb ac = new DBConnect().accesDb();
        Billet[] res = new Billet[0];
        boolean test = false;
        if(connex == null){
            test = true;
            connex = ac.connect();
        }
        try{    
          res = new Billet().getInBase(null,this.id," etat != '"+Constant.ETAT_REMBOURSE+"' and etat != '"+Constant.ETAT_SUPPRIMER+"'", connex);
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
    
     //get billet
    public Billet_detail[] getBillet_details(Connection connex) throws Exception  {
        AccesDb ac = new DBConnect().accesDb();
        Billet_detail[] res = new Billet_detail[0];
        boolean test = false;
        if(connex == null){
            test = true;
            connex = ac.connect();
        }
        try{    
          res = new Billet_detail().getInBase(null,this.id," etat != '"+Constant.ETAT_REMBOURSE+"' and etat != '"+Constant.ETAT_SUPPRIMER+"'", connex);
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
    
     //get billet_detail_with_remdourse
    public Billet_detail[] getBillet_details_With_Rembourse(Connection connex) throws Exception  {
        AccesDb ac = new DBConnect().accesDb();
        Billet_detail[] res = new Billet_detail[0];
        boolean test = false;
        if(connex == null){
            test = true;
            connex = ac.connect();
        }
        try{    
          res = new Billet_detail().getInBase(null,this.id," etat != '"+Constant.ETAT_SUPPRIMER+"'", connex);
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
    
    //calcule chiffre d'affaire
    public double CalculeChiffreAffaire(Connection connex) throws Exception {
         AccesDb ac = new DBConnect().accesDb();
        double res = 0;
        boolean test = false;
        if(connex == null){
            test = true;
            connex = ac.connect();
        }
        try{   
           double penalite = new ConditionTarif().getPenalite(connex).getPourcentage();
          //data
          Billet_detail[] bt = this.getBillet_details_With_Rembourse(connex);
          //calcule
          for(int i=0;i<bt.length;i++){
              if(bt[i].getEtat() == Constant.ETAT_VALIDER){
                  res += bt[i].getMontant();
              }else if(bt[i].getEtat() == Constant.ETAT_REMBOURSE){
                  res += bt[i].getMontant_remboursement();
              }
              else if(bt[i].getEtat() == Constant.ETAT_EN_ATTENTE){
                  //un autre logique
//                  if(bt[i].getModifiable() == 1 && bt[i].getRemboursable()== 1){
//                  }else{
//                      res += bt[i].getMontant();
//                  }
                  
                  //ma logique
                  if(bt[i].getModifiable() != 1){
                      res += bt[i].getMontant()*(penalite/100);
//                      res += bt[i].getMontant();
                  }
              }
          }
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
    
    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getIdTrajet() {
        return idTrajet;
    }

    public void setIdTrajet(String idTrajet) {
        this.idTrajet = idTrajet;
    }

    public String getIdAvion() {
        return idAvion;
    }

    public void setIdAvion(String idAvion) {
        this.idAvion = idAvion;
    }

    public Date getDateDep() {
        return dateDep;
    }

    public void setDateDep(Date dateDep) {
        this.dateDep = dateDep;
    }

    public LocalTime getHoraire() {
        return horaire;
    }

    public void setHoraire(LocalTime horaire) {
        this.horaire = horaire;
    }

    public Integer getEtat() {
        return etat;
    }

    public void setEtat(Integer etat) {
        this.etat = etat;
    }
    
}
