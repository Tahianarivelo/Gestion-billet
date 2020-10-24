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
import outil.UG;

/**
 *
 * @author tahiana
 */
@Table(nom="ConditionTarif")
public class ConditionTarif {
    @Colonne(colonne = "id",sequence = "seq_ConditionTarif",identifiant = true,prefixe = "CT")
    String id;
    @Colonne(colonne = "description")
    String description;
    @Colonne(colonne = "pourcentage")
    double pourcentage;

    public ConditionTarif(){}
    public ConditionTarif(String id, String description, double pourcentage) {
        this.id = id;
        this.description = description;
        this.pourcentage = pourcentage;
    }

     //get In base
    public ConditionTarif[] getInBase(String id,String description,String afterWhere,Connection connex) throws Exception  {
        AccesDb ac = new DBConnect().accesDb();
        ConditionTarif[] res = new ConditionTarif[0];
        boolean test = false;
        if(connex == null){
            test = true;
            connex = ac.connect();
        }
        try{    
            String apWhere = " id "+UG.transform(id)+" and description"+UG.transform(description);
            if(!afterWhere.equals("")){
                apWhere = apWhere+" and "+afterWhere;
            }
             res =(ConditionTarif[]) ac.find(this,"ConditionTarif"," and " +apWhere, 0, 0,false,connex);
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
    
    //get pourCentage modifiable
    public ConditionTarif getModifiable(Connection connex) throws Exception  {
        AccesDb ac = new DBConnect().accesDb();
        ConditionTarif res = null;
        boolean test = false;
        if(connex == null){
            test = true;
            connex = ac.connect();
        }
        try{    
           ConditionTarif[] ct = this.getInBase(null,"modifiable","", connex);
            if(ct.length > 0){
                res = ct[0];
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
    
     //get pourCentage modifiable
    public ConditionTarif getRemboursable(Connection connex) throws Exception  {
        AccesDb ac = new DBConnect().accesDb();
        ConditionTarif res = null; 
        boolean test = false;
        if(connex == null){
            test = true;
            connex = ac.connect();
        }
        try{    
          ConditionTarif[] ct = this.getInBase(null,"remboursable","", connex);
            if(ct.length > 0){
                res = ct[0];
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
    
     //get pourCentage modifiable
    public ConditionTarif getPenalite(Connection connex) throws Exception  {
        AccesDb ac = new DBConnect().accesDb();
        ConditionTarif res = null; 
        boolean test = false;
        if(connex == null){
            test = true;
            connex = ac.connect();
        }
        try{    
          ConditionTarif[] ct = this.getInBase(null,"penalite","", connex);
            if(ct.length > 0){
                res = ct[0];
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPourcentage() {
        return pourcentage;
    }

    public void setPourcentage(double pourcentage) {
        this.pourcentage = pourcentage;
    }
        
    
}
