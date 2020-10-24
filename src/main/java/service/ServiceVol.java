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
import java.util.Calendar;
import modele.Avion;
import modele.Billet;
import modele.Billet_detail;
import modele.Constant;
import modele.Tarif;
import modele.Trajet;
import modele.Vol;
import modele.Vol_detail;
import outil.UG;

/**
 *
 * @author tahiana
 */
public class ServiceVol {
    public ServiceVol(){}
    
    //Ajout
    public void Ajout(Vol vol) throws Exception {
        AccesDb ac = new DBConnect().accesDb();
        Connection connex = ac.connect();
        try{
            connex.setAutoCommit(false);
             //verif existe trajet
              Trajet[] t = new Trajet().getInBase(vol.getIdTrajet(),null,null,"",connex);
                if(t.length <= 0){
                    vol.setIdTrajet("");
                    throw new Exception("Trajet introuvable.");
               }
             //verif existe avion
              Avion[] av = new Avion().getInBase(vol.getIdAvion(),null,"", connex);
                if(av.length <= 0){
                    vol.setIdAvion("");
                    throw new Exception("Avion introuvable.");
                }
             //verif date
             Date dateAct = UG.getDate(new Date(new java.util.Date().getTime()),1);
             if(dateAct.compareTo(vol.getDateDep()) >= 1){
                 vol.setDateDep(null);
                 throw new Exception("date invalide. 2 jour avant minimum");
             }
             String nextValSeq = UG.getsequence("seq_numVol", connex);
             vol.setNum("CC"+nextValSeq);
             vol.setEtat(0);
             vol.insert(connex);
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
    public void Modif(Vol vol) throws Exception {
        AccesDb ac = new DBConnect().accesDb();
        Connection connex = ac.connect();
        try{
            connex.setAutoCommit(false);
            //vol deja valider
            if(vol.getEtat() == Constant.ETAT_VALIDER){
                throw new Exception("Vol deja valider.");
            }
            //verif existe trajet
              Trajet[] t = new Trajet().getInBase(vol.getIdTrajet(),null,null,"",connex);
                if(t.length <= 0){
                    vol.setIdTrajet("");
                    throw new Exception("Trajet introuvable.");
               }
             //verif existe avion
              Avion[] av = new Avion().getInBase(vol.getIdAvion(),null,"", connex);
                if(av.length <= 0){
                    vol.setIdAvion("");
                    throw new Exception("Avion introuvable.");
                }
             //verif date
             Date dateAct = UG.getDate(new Date(new java.util.Date().getTime()),1);
//             if(dateAct.compareTo(vol.getDateDep()) >= 1){
//                 vol.setDateDep(null);
//                 throw new Exception("date invalide. 2 jour avant minimum");
//             }
             vol.setNum(null);
             vol.setEtat(0);
             vol.update(connex);
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
    public void Suppre(String idVol) throws Exception {
        AccesDb ac = new DBConnect().accesDb();
        Connection connex = ac.connect();
        try{
            connex.setAutoCommit(false);
            Vol vl = new Vol();
            vl.setId(idVol);
            vl.setEtat(Constant.ETAT_SUPPRIMER);
            vl.Suppre(connex);
            connex.commit();
        }
        catch(Exception ex){
            connex.rollback();
            throw ex;
        }finally{
            connex.close();
        }
    } 
    
    //recherche
    public Object[] Recherche(String date1,String date2,String horaire,String trajet,String avion,int page,int nb_elem_show,String colonne) throws Exception {
        AccesDb ac = new DBConnect().accesDb();
        Connection connex = ac.connect();
         Vol_detail[] vl = new Vol_detail[0];
         Object[] res = new Object[2];
        try{
            String apresWhere = "";
            //si une des dates sont non vide
            if(date1.equals("") == false && date2.equals("") == false){
                Date dateAv = Date.valueOf(date1);
                Date dateAp = Date.valueOf(date2);
                if(dateAv.compareTo(dateAp) >= 1){
                    throw new Exception("date incoherent");
                }
                else{
                    apresWhere +="and datedep >= '"+date1+"' and datedep <= '"+date2+"'";
                }
            }else if(date1.equals("") || date2.equals("")){
                if(date1.equals("") == false){
                    apresWhere += "and datedep >= '"+date1+"'";
                }
                if(date2.equals("") == false){
                    apresWhere += "and datedep <= '"+date2+"'";
                }
            }
            //si horaire non vide
            if(horaire.equals("") == false){
                apresWhere += "and horaire = '"+horaire+"'";
            }
            //si trajet == tout
            if(trajet.equals("Tout") == false){
                apresWhere += "and idtrajet = '"+trajet+"'";
            }
            //si avion == tout
            if(avion.equals("Tout") == false){
                apresWhere += "and idAvion = '"+avion+"'";
            }
          
            int nb_data = UG.Count("vol_detail"," etat != '3' "+apresWhere,null); ;
            res[1] = nb_data;
            //non supprimer
            apresWhere += " and etat != '"+Constant.ETAT_SUPPRIMER+"'";
            //tri par colonne
            apresWhere += " order by "+colonne;
            
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
            
           Object[] o = ac.find(new Vol_detail(),"vol_detail",apresWhere,nb_elem_show,deb, false, connex);
           vl = (Vol_detail[]) o;
           res[0] = vl;
        }
        catch(Exception ex){
            throw ex;
        }finally{
            connex.close();
        }
        return res;
    }
    
    //suppre
    public void Valider(String idVol,String[] billet_personne_present) throws Exception {
        AccesDb ac = new DBConnect().accesDb();
        Connection connex = ac.connect();
        try{
            connex.setAutoCommit(false);
            Vol[] v = new Vol().getInBase(idVol,null,null,"",connex);
            //verfifecation vol
            if(v.length <= 0){
                throw new Exception("Vol introuvable");
            }
            //si vol deja valider
            if(v[0].getEtat() == Constant.ETAT_VALIDER){
                throw new Exception("Vol deja valider");
            }
            Billet[] billet = v[0].getBillets(connex);
            for(int i=0;i<billet.length;i++){
               boolean test = true;
                for(int j=0;j<billet_personne_present.length;j++){
                    if(billet[i].getId().equals(billet_personne_present[j])){
                        billet[i].Valider(connex);
                        test = false;
                        break;
                    }
                }
                if(test){
                    //logique ngam
//                    if(billet[i].getModifiable() == 0 || billet[i].getRemboursable() == 0){
//                        billet[i].Valider(connex);
//                    }else if(billet[i].getModifiable() == 1 && billet[i].getRemboursable() == 1){
//                        billet[i].EnAttente(connex);
//                    }
                   
                    //ma logique
                    if(billet[i].getModifiable() == 1){
                        billet[i].EnAttente(connex);
                    }else{
                        if(billet[i].getRemboursable() == 1){
                            billet[i].EnAttente(connex);
                        }else{
                            billet[i].Valider(connex);
                        }
                    }
                }
                test = true;
            }
            //validation vol
            v[0].Valider(connex);
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
    public Object[] ListPaginationVol(String ApreWhere,int page,int nb_elem_show,String colonne) throws Exception {
        AccesDb ac = new DBConnect().accesDb();
        Connection connex = ac.connect();
         Vol_detail[] vl = new Vol_detail[0];
         Object[] res = new Object[2];
        try{
            //non supprimer
            ApreWhere += " and (etat != '"+Constant.ETAT_SUPPRIMER+"' and etat != '"+Constant.ETAT_VALIDER+"' )";
            
            int nb_data = UG.Count("vol_detail",ApreWhere,null);
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
            
           Object[] o = ac.find(new Vol_detail(),"vol_detail","and "+ApreWhere,nb_elem_show,deb, false, connex);
           vl = (Vol_detail[]) o;
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
