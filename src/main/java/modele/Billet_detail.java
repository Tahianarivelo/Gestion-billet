/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;

import accesdb.AccesDb;
import annotation.Colonne;
import dao.DBConnect;
import java.sql.Connection;
import java.sql.Date;
import outil.UG;

/**
 *
 * @author tahiana
 */
public class Billet_detail extends Billet{
    @Colonne(colonne = "num_vol")
    String num_vol;
    @Colonne(colonne = "montant_remboursement")
    double montant_remboursement;

    public Billet_detail(){}
    public Billet_detail(String num_vol, double montant_remboursement, String id, String nomClient, Integer age, String idVol, Integer modifiable, Integer remboursable, double montant, Date dateCreation, Integer etat) {
        super(id, nomClient, age, idVol, modifiable, remboursable, montant, dateCreation, etat);
        this.num_vol = num_vol;
        this.montant_remboursement = montant_remboursement;
    }
    
     //get In base
    public Billet_detail[] getInBase(String id,String idVol,String afterWhere,Connection connex) throws Exception  {
        AccesDb ac = new DBConnect().accesDb();
        Billet_detail[] res = new Billet_detail[0];
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
             res =(Billet_detail[]) ac.find(this,"Billet_detail"," and " +apWhere, 0, 0,false,connex);
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

    public String getNum_vol() {
        return num_vol;
    }

    public void setNum_vol(String num_vol) {
        this.num_vol = num_vol;
    }

    public double getMontant_remboursement() {
        return montant_remboursement;
    }

    public void setMontant_remboursement(double montant_remboursement) {
        this.montant_remboursement = montant_remboursement;
    }
    
}
