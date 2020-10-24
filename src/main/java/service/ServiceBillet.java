/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import accesdb.AccesDb;
import dao.DBConnect;
import java.sql.Connection;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import modele.Billet;
import modele.ConditionTarif;
import modele.Constant;
import modele.Reduction;
import modele.Remboursement;
import modele.Tarif;
import modele.Trajet;
import modele.Vol_detail;

/**
 *
 * @author tahiana
 */
public class ServiceBillet {
    public ServiceBillet(){}
    
    //achat
    public void Achat(Billet billet) throws Exception {
        AccesDb ac = new DBConnect().accesDb();
        Connection connex = ac.connect();
        try{
            connex.setAutoCommit(false);
            double montant = 0;
            double prix = 0.0;
            double  pourC_M = 0;
            double pourC_R = 0;
             double reduction =100;
            //age negatif
            if(billet.getAge() < 0){
                throw new Exception("Age negatif");
            }
            //verif vol
            Vol_detail[] vl = new Vol_detail().getInBase(billet.getIdVol(),null,null," etat != '"+Constant.ETAT_SUPPRIMER+"'", connex);
            if(vl.length > 0){
                prix = vl[0].getPrix();
                //verification etat
                if(vl[0].getEtat() == Constant.ETAT_VALIDER){
                    throw new Exception("Vol déja effectué.");
                }          
                //verification place
                if(vl[0].getReste_place() <= 0){
                      throw new Exception("Place saturé");
                }
            }else{
                throw new Exception("Vol introuvable");
            }
            //reduction
            Reduction[] red = new Reduction().getInBase(null,null," age_max > '"+billet.getAge()+"' and age_min <= '"+billet.getAge()+"' ", connex);
            if(red.length >= 0){
                   reduction = red[0].getPourcentage();
            }
        
            ConditionTarif modifiable = new ConditionTarif().getModifiable(null);
            ConditionTarif remboursable = new ConditionTarif().getRemboursable(null);
                if(modifiable.getDescription().equals("modifiable")){
                    pourC_M = modifiable.getPourcentage();
                    
                }
                if(remboursable.getDescription().equals("remboursable")){
                    pourC_R = remboursable.getPourcentage();
                }
            
            //condition tarif
            double pourcebtage_Ct = 0;
            if(billet.getModifiable() == 0){
                pourcebtage_Ct += pourC_M;
            }
            if(billet.getRemboursable() == 0){
                pourcebtage_Ct += pourC_R;
            }
            montant = prix*(reduction/100);
            montant = montant - (montant*(pourcebtage_Ct/100));
            billet.setMontant(montant);
            billet.setEtat(0);
            billet.setDateCreation(new Date(new java.util.Date().getTime()));
            billet.insert(connex);
            connex.commit();
        }
        catch(Exception ex){
            connex.rollback();
            throw ex;
        }finally{
            connex.close();
        }
    }
    
     //Rembourser
    public void Rembourser(String idBillet,String date) throws Exception {
        AccesDb ac = new DBConnect().accesDb();
        Connection connex = ac.connect();
        try{
            connex.setAutoCommit(false);
            Billet[] b = new Billet().getInBase(idBillet,null,"",connex);
            if(b.length <=0 ){
                 throw new Exception("Billet introuvable.");
            }
            if(b[0].getEtat() == Constant.ETAT_VALIDER){
                throw new Exception("Billet déja validé.");
            }
            if(b[0].getRemboursable() != 1){
                 throw new Exception("Billet non remboursable.");
            }
            ConditionTarif ct = new ConditionTarif().getPenalite(connex);
            //test date
            Date date_remb = Date.valueOf(date);
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");  
            String strDate= formatter.format(new java.util.Date());  
            Date act = Date.valueOf(strDate);
            if(act.compareTo(date_remb) > 0){
                throw new Exception("Date incoherent.");
            }
             double montant_payer = b[0].getMontant()* ct.getPourcentage()/100;
            Remboursement rembours = new Remboursement("",idBillet,montant_payer,Constant.ETAT_CREE);
            b[0].Rembourser(connex);
            rembours.insert(connex);
            connex.commit();
        }
        catch(Exception ex){
            connex.rollback();
            throw ex;
        }finally{
            connex.close();
        }
    }
}
