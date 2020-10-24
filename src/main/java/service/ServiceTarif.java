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
import modele.Constant;
import modele.Tarif;
import modele.Trajet;
import modele.Ville;
import outil.UG;

/**
 *
 * @author tahiana
 */
public class ServiceTarif {
    public ServiceTarif(){}
    
     //Ajout
    public void Ajout(Tarif tarif) throws Exception {
        AccesDb ac = new DBConnect().accesDb();
        Connection connex = ac.connect();
        try{
            connex.setAutoCommit(false);
             //Varification exist trajet
            Trajet[] t = new Trajet().getInBase(tarif.getIdTrajet(),null,null," etat !='"+Constant.ETAT_SUPPRIMER+"'",connex);
            if(t.length <= 0){
                throw new Exception("Trajet introuvable");
            }
            //Varification exist tarif
            Tarif[] tar = new Tarif().getInBase(null,tarif.getIdTrajet()," etat !='"+Constant.ETAT_SUPPRIMER+"'",connex);
            if(tar.length > 0){
                throw new Exception("Ce tarif existe deja.");
            }
            //prix negatif
            if(tarif.getPrix() < 0){
                     tarif.setPrix(0);
                throw new Exception("prix negatif");
            }
            tarif.setDateApplication(new Timestamp(new java.util.Date().getTime()));
            tarif.setEtat(0);
            tarif.insert(connex);
            connex.commit();
        }
        catch(Exception ex){
            connex.rollback();
            throw ex;
        }finally{
            connex.close();
        }
    }
    
     //modif
    public void Modif(Tarif tarif) throws Exception {
        AccesDb ac = new DBConnect().accesDb();
        Connection connex = ac.connect();
        try{
            connex.setAutoCommit(false);
            //Varification exist tarif
            Tarif[] tar = new Tarif().getInBase(tarif.getId(),null,"etat !='"+Constant.ETAT_SUPPRIMER+"'",connex);
            if(tar.length <= 0){
                throw new Exception("Tarif introuvable.");
            }
            //verif trajet
            Trajet[] t = new Trajet().getInBase(tarif.getIdTrajet(),null,null," etat !='"+Constant.ETAT_SUPPRIMER+"'",connex);
            if(t.length <= 0){
                throw new Exception("Trajet introuvable");
            }
            //prix negatif
            if(tarif.getPrix() < 0){
                tarif.setPrix(0);
                throw new Exception("prix negatif");
            }
            tarif.setDateApplication(new Timestamp(new java.util.Date().getTime()));
            tarif.setEtat(0);
            tarif.update(connex);
            connex.commit();
        }
        catch(Exception ex){
            connex.rollback();
            throw ex;
        }finally{
            connex.close();
        }
    }
    
    //suppre
    public void Suppre(String idtarif) throws Exception {
        AccesDb ac = new DBConnect().accesDb();
        Connection connex = ac.connect();
        try{
            connex.setAutoCommit(false);
            //Varification exist trajet
            Tarif[] t = new Tarif().getInBase(idtarif,null," etat !='"+Constant.ETAT_SUPPRIMER+"'",connex);
            if(t.length <= 0){
                throw new Exception("Tarif introuvable.");
            }
            else{
                t[0].Suppre(connex);
            }
            connex.commit();
        }
        catch(Exception ex){
            connex.rollback();
            throw ex;
        }finally{
            connex.close();
        }
    }
    
    //list pagination
    public Object[] ListPaginationTarif(String ApreWhere,int page,int nb_elem_show,String colonne) throws Exception {
        AccesDb ac = new DBConnect().accesDb();
        Connection connex = ac.connect();
         Tarif[] vl = new Tarif[0];
         Object[] res = new Object[2];
        try{
            //non supprimer
            ApreWhere += " and etat != '"+Constant.ETAT_SUPPRIMER+"'";
            
            int nb_data = UG.Count("Tarif",ApreWhere,null);
            res[1] = nb_data;

            //tri par colonne
            ApreWhere += " order by "+colonne;
            
            //Pagination  
            int nb_page_max = nb_data / nb_elem_show;
            if((nb_data%nb_elem_show) > 0){
                nb_page_max++;
            }
            //verification page
            if(page > nb_page_max || page <= 0){
                throw new Exception("page introuvable");
            }
            int deb = (page - 1)*nb_elem_show;
            
           Object[] o = ac.find(new Tarif(),"Tarif","and "+ApreWhere,nb_elem_show,deb, false, connex);
           vl = (Tarif[]) o;
           res[0] = vl;
        }
        catch(Exception ex){
            throw ex;
        }finally{
            connex.close();
        }
        return res;
    }
}
