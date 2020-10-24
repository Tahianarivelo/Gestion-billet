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
import modele.Constant;
import modele.Trajet;
import modele.Trajet_detail;
import modele.Ville;
import modele.Vol_detail;
import outil.UG;

/**
 *
 * @author tahiana
 */
public class ServiceTrajet {
    
    public ServiceTrajet(){}
    
    //Ajout
    public void Ajout(Trajet trajet) throws Exception {
        AccesDb ac = new DBConnect().accesDb();
        Connection connex = ac.connect();
        try{
            connex.setAutoCommit(false);
            //Varification exist trajet
            Trajet[] t = new Trajet().getInBase(null,trajet.getVille_dep(),trajet.getVille_arr()," etat !='"+Constant.ETAT_SUPPRIMER+"'",connex);
            if(t.length > 0){
                throw new Exception("Ce trajet existe deja.");
            }
            //meme ville
            if(trajet.getVille_dep().equals(trajet.getVille_arr())){
                throw new Exception("meme ville");
            }
            //Existe ville dep et arr
            Ville[] vd = new Ville().getInBase(trajet.getVille_dep(),null," etat !='"+Constant.ETAT_SUPPRIMER+"'", connex);
            Ville[] va = new Ville().getInBase(trajet.getVille_arr(),null," etat !='"+Constant.ETAT_SUPPRIMER+"'", connex);
            if(vd.length <= 0 || va.length <= 0){
                throw new Exception("ville introuvable");
            }
            //distance diff 0
            if(trajet.getDistance() < 0){
                trajet.setDistance(0);
                throw new Exception("distance negatif");
            }
            trajet.setDateCreation(new Date(new java.util.Date().getTime()));
            trajet.setEtat(0);
            trajet.insert(connex);
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
    public void Modif(Trajet trajet) throws Exception {
        AccesDb ac = new DBConnect().accesDb();
        Connection connex = ac.connect();
        try{
            connex.setAutoCommit(false);
           //Varification exist trajet
            Trajet[] t = new Trajet().getInBase(trajet.getId(),null,null," etat !='"+Constant.ETAT_SUPPRIMER+"'",connex);
            if(t.length <= 0){
                throw new Exception("Trajet introuvable.");
            }
             //Varification exist trajet
            Trajet[] tr = new Trajet().getInBase(null,trajet.getVille_dep(),trajet.getVille_arr()," etat !='"+Constant.ETAT_SUPPRIMER+"'",connex);
            if(tr.length > 0){
                throw new Exception("Ce trajet existe deja.");
            }
            //meme ville
            if(trajet.getVille_dep().equals(trajet.getVille_arr())){
                throw new Exception("meme ville");
            }
            //Existe ville dep et arr
            Ville[] vd = new Ville().getInBase(trajet.getVille_dep(),null," etat !='"+Constant.ETAT_SUPPRIMER+"'", connex);
            Ville[] va = new Ville().getInBase(trajet.getVille_arr(),null," etat !='"+Constant.ETAT_SUPPRIMER+"'", connex);
            if(vd.length <= 0 || va.length <= 0){
                throw new Exception("ville introuvable");
            }
            //distance diff 0
            if(trajet.getDistance() < 0){
                throw new Exception("distance negatif");
            }
            trajet.setDateCreation(new Date(new java.util.Date().getTime()));
            trajet.setEtat(Constant.ETAT_CREE);
            trajet.update(connex);
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
    public void Suppre(String idtrajet) throws Exception {
        AccesDb ac = new DBConnect().accesDb();
        Connection connex = ac.connect();
        try{
            connex.setAutoCommit(false);
            //Varification exist trajet
            Trajet[] t = new Trajet().getInBase(idtrajet,null,null," etat != '"+Constant.ETAT_SUPPRIMER+"'",connex);
            if(t.length <= 0){
                throw new Exception("Trajet introuvable.");
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
    public Object[] ListPaginationTrajet(String ApreWhere,int page,int nb_elem_show,String colonne) throws Exception {
        AccesDb ac = new DBConnect().accesDb();
        Connection connex = ac.connect();
         Trajet_detail[] vl = new Trajet_detail[0];
         Object[] res = new Object[2];
        try{
            //non supprimer
            ApreWhere += " and etat != '"+Constant.ETAT_SUPPRIMER+"'";
            
            int nb_data = UG.Count("trajet_detail",ApreWhere,null);
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
            
           Object[] o = ac.find(new Trajet_detail(),"trajet_detail","and "+ApreWhere,nb_elem_show,deb, false, connex);
           vl = (Trajet_detail[]) o;
           res[0] = vl;
        }
        catch(Exception ex){
            throw ex;
        }finally{
            connex.close();
        }
        return res;
    }
    
    //list pagination
    public Object[] ListPaginationTrajet_tout(String ApreWhere,int page,int nb_elem_show,String colonne) throws Exception {
        AccesDb ac = new DBConnect().accesDb();
        Connection connex = ac.connect();
         Trajet_detail[] vl = new Trajet_detail[0];
         Object[] res = new Object[2];
        try{
            //non supprimer
            ApreWhere += " and etat != '"+Constant.ETAT_SUPPRIMER+"'";
            
            int nb_data = UG.Count("trajet_detail_tout",ApreWhere,null);
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
            
           Object[] o = ac.find(new Trajet_detail(),"trajet_detail_tout","and "+ApreWhere,nb_elem_show,deb, false, connex);
           vl = (Trajet_detail[]) o;
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
